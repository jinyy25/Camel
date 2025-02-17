package com.example.test.camel;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.RouteDefinition;
import org.apache.commons.lang3.StringUtils;


public class FileSampleRoute2 extends RouteBuilder {
    private long max = Long.MAX_VALUE;

    @Override
    public void configure() throws Exception {
        from("file:data5")
                .log("${body}")
                .process(this::processFile);
    }

    private void processFile(Exchange exchange) {
        String numStr = exchange.getMessage().getBody(String.class);
        numStr = StringUtils.trim(numStr);
        if (!StringUtils.equals(numStr, "null")) {
            try {
                long num = Long.parseLong(numStr);
                if (num < max) {
                    max = num;
                }
                System.out.println(max);
            } catch (NumberFormatException e) {
                System.err.println("Invalid number format: " + numStr);
            }
        }
    }
}
