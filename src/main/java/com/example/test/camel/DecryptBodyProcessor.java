package com.example.test.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class DecryptBodyProcessor implements Processor {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private GmdCrypto gmdCrypto;

    public DecryptBodyProcessor() {
    }

    public void process(Exchange exchange) throws Exception {
        String encryptedMsg = (String)exchange.getMessage().getBody(String.class);
        String decryptedMsg = this.gmdCrypto.decrypt(encryptedMsg);
        exchange.getMessage().setBody(decryptedMsg);
    }
}
