package com.example.test.kafka;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.ProcessorDefinition;

import java.util.HashMap;
import java.util.Map;

public class KafkaRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("hey", "hello");

        from("timer:aaa?repeatCount=1")
                .autoStartup(true)
                .setBody().constant("First")
                .wireTap("direct:test1")
                .setBody().constant(map)
                .log("END ${body}");

        from("direct:test1")
                .log("received ${body}");
    }
}
