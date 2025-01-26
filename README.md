
# Little Pay Task

This is intended to be the guide for the submitted task

# Content

* How to start the project
* Major Modifications to make the Project work as expected
* The Stuff I added to complete missing features
* How is the solution exposed on various forms
* Suggestions

## 1- How to start the project

So basically to start the project you have to go to the maven tab "if you are using intellij" and click on <span style="color: red; font-weight: bold;">plugins > spring-boot > spring-boot:run<span>.

Or you can install maven on your local machine and run the following command on the terminal: <span style="color: red; font-weight: bold;">mvn spring-boot:run</span>


## 2- Major Modifications to make the Project work as expected

Basically the project was not working correctly so I had to make a change in the **validateAndResolveMessage** function inside the **MultipleMessageParser** Class.

Also I had to change the way **MessageParserHelper** handles the card number assignation.

## 3- The Stuff I added to complete missing features

At this level I had to integrate the decoding mechanism to give the application the opportunity to handle Base64 content
and transform it to Hex representation. You can find this logic at **Base64Decoder** class.

## 4- How is the solution exposed on various forms

As per the pdf sent with the task I had to expose the feature on two aspects.

- TCP Server
- Rest Endpoint

So, I managed to add both of these functions in addition to the capability to access this feature through WebSockets also.

To test the Rest Endpoint:

- Open Postman
- Select New HTTP Post Request
- Hit on the following http://localhost:8080/transaction/submit
- add body for the request
- the content-type of the body should be "application/json"
- Here is an example for the body:

`{"data": "AEICGJ8qAQKfAgIBAFoIQRERERERERFfKgIJeAMCJJ8qCAQAAAAAAAAAXyoCCCafAgMSNFZaCDeCgiRjEABfnwMBAAM="}`

To test the Rest Endpoint:

- Open Postman
- Select New WS Request
- Hit on the following ws://localhost:8080/websocket
- After successful connection add a base64 content in the message box
- Here is an example for the message:

`AEICGJ8qAQKfAgIBAFoIQRERERERERFfKgIJeAMCJJ8qCAQAAAAAAAAAXyoCCCafAgMSNFZaCDeCgiRjEABfnwMBAAM=`

To test the TCP server:

- Open the terminal and execute the following command. Basically this is a shell script that hits on the TCP server to test the exposed functionality you can pass a Base64 content to test with.

`./send_message.sh "AEICGJ8qAQKfAgIBAFoIQRERERERERFfKgIJeAMCJJ8qCAQAAAAAAAAAXyoCCCafAgMSNFZaCDeCgiRjEABfnwMBAAM="`

## 5- Suggestions

Of course we will discuss that when we meet. But let's state that here:

After checking the code I noticed some stuff:

+ ### **Some code chuncks are useless:**

As in the <span style="color: red; font-weight: bold;">SingleMessageParser</span> Class, the method <span style="color: red; font-weight: bold;">resolveTag</span>
```markdown
while (includeNextByte) {
    int nextTagPart = Integer.parseInt(message.substring(currentIndex, currentIndex + 2), 16);
    if ((nextTagPart & (1 << 7)) == 0) {
        includeNextByte = false;
    }
    length++;
}
```

this While for example should be replaced by if. as it is meaning less to have while here as the contextIndex is not modified in each iteration. 
Moreover from the business requirements perspective, We do not have tags more that 4 digits and this is also apparent at the <span style="color: red; font-weight: bold;">MessageParserHelper</span> class <span style="color: red; font-weight: bold;">addTag</span> Method

Also for this function:

```markdown
private String resolveLength(String message, int currentIndex) {
    int lengthField = Integer.parseInt(message.substring(currentIndex, currentIndex + 2), 16);

    if ((lengthField & (1 << 7)) == 0) {
        return message.substring(currentIndex, currentIndex + 2);
    }

    int lengthOfLengthField = lengthField & 127;

    return message.substring(currentIndex, currentIndex + 2 + lengthOfLengthField * 2);
}
```

Could be refactored as follows:
```markdown
private String resolveLength(String message, int currentIndex) {
    return message.substring(currentIndex, currentIndex + 2);
}
```

+ ### **Code is not clean and complex to read:**

The code should be refactored so that the logic is splitted in smaller easy to be read chunks with convenient method names

The code contains a lot of numeric operations like multiplying by a number or dividing by a number. These numbers should be stored
in a well named constants to make the parsing operation more readable and easy to understand.

+ ### **Exception handling:**

Exception should be handled properly because you might send improper Base64 content or even the Base64 content is not really well encoded

+ ### **More business Awareness:**

in the <span style="color: red; font-weight: bold;">MessageParserHelper</span> class I made assumption based on the provided examples that the tags are always provided in a sequence. So I made the assumption that the kernel will always be provided first. 
If it is not the case. the <span style="color: red; font-weight: bold;">addTag</span> method logic should be modified

+ ### **More Testing Coverage:**
Based on the provided business More test cases should be added to cover all the business corner cases.