package org.example.message.handler;

import org.example.entity.MessageEntity;
import org.springframework.stereotype.Component;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MultipleMessageParser {

    private static final String START = "02";
    private static final String END = "03";
    private static final int HEADER_LENGTH = 2;
    private final SingleMessageParser singleMessageParser;

    public Map<String, List<MessageEntity>> parse(String messages){

        validateTotalLength(messages);

        int currentIndex = 4;
        Map<String, List<MessageEntity>> messageEntityList = new HashMap<>();

        while(currentIndex < messages.length()){
            String message  = validateAndResolveMessage(currentIndex, messages);

            MessageEntity entity = singleMessageParser.parse(message);
            if(!messageEntityList.containsKey(entity.getKernel())){
                messageEntityList.put(entity.getKernel(), new ArrayList<>());
            }

            messageEntityList.get(entity.getKernel()).add(entity);

            currentIndex += message.length() + 6;
        }

        for(String kernel : messageEntityList.keySet()){
            System.out.println(messageEntityList.get(kernel));
        }

        return messageEntityList;
    }

    private void validateTotalLength(String message) {
        int totalLength = message.length(); //e.g. 0042

        if(totalLength <= 4) {
            throw new InvalidParameterException("Invalid message: length field should be of 2 bytes");
        }

        int lenOfMessage = Integer.parseInt(message.substring(0, 4), 16)*2;

        if(totalLength-4 != lenOfMessage){
            throw new InvalidParameterException("Invalid message: length field does not match the length of the message");
        }
    }

    /*
     * This function is supposed to return the length of the data which relies between the start and end flag.
     * We pass the index where the length of data size should start to appear which is the current index
     * plus the HEADER_LENGTH which is "02". So in our example this function should return "18" but in decimal
     * representation which is "24" and we need to multiply it by 2 as the byte is represented as two digits in the
     * hexadecimal representation.
     * so the message data is "9f2a01029f020201005a0841111111111111115f2a020978"
     */
    private int captureLengthOfMessage(int currentIndex, String message) {
        return Integer.parseInt(message.substring(currentIndex, currentIndex + HEADER_LENGTH), 16)*2;
    }

    /*
     * This function is responsible for snapping the message according to the defined Prefixes
     * Example: Assume we are having that message "02189f2a01029f020201005a0841111111111111115f2a02097803"
     * We'll use this example message to simplify the understanding of the function implementation
     */
    private String validateAndResolveMessage(int currentIndex, String message) {
        /*
         * So basically we know that any message should start with "02" so we have to check
         * that the passed message starts with "02". So we could rely on the HEADER_LENGTH constant
         * as it is always two digits that identifies the start flag.
         */
        String start = message.substring(currentIndex, currentIndex + HEADER_LENGTH);

        if(!start.equals(START)){
            throw new InvalidParameterException("Invalid message: start of the message not found");
        }

        // The length variable should contain the actual character length of the message till the end flag
        int length = captureLengthOfMessage(currentIndex + HEADER_LENGTH, message);
        /*
         * The current index should be after the start flag, the message data size flag and the actual message data.
         * So, we have to add the calculated length to the HEADER_LENGTH multiplied by 2 because of the
         * Start flag "02" and the message data size "18".
         */
        currentIndex += (HEADER_LENGTH * 2) + length;

        // We should capture the end of the message, in order to validate that everything is correct
        String end = message.substring(currentIndex, currentIndex + HEADER_LENGTH);
        if(!end.equals(END)){
            throw new InvalidParameterException("Invalid message: end of the message not found");
        }

        return message.substring(currentIndex-length, currentIndex);
    }
}
