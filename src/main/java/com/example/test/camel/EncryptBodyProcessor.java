package com.example.test.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;

public class EncryptBodyProcessor implements Processor {

    @Autowired
    private GmdCrypto gmdCrypto;

    public EncryptBodyProcessor() {
    }

    public void process(Exchange exchange) throws Exception {
        String msg = (String)exchange.getMessage().getBody(String.class);
        String encryptedMsg = this.gmdCrypto.encrypt(msg);
        exchange.getMessage().setBody(encryptedMsg);
    }
}
