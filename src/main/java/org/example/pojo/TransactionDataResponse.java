package org.example.pojo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDataResponse implements Serializable {
    String kernel;
    String cardNumber;
    double amount;
    String currency;
}
