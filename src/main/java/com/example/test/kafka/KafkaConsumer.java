package com.example.test.kafka;

import org.apache.camel.builder.RouteBuilder;

public class KafkaConsumer extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("kafka:SGG-CHC?groupId=sync-syncload-sigungu")
                .log("${header.kafka.TOPIC},${header.kafka.PARTITION},${header.kafka.OFFSET}");

        from("kafka:SGG-WJU?groupId=sync-syncload-sigungu")
                .log("${header.kafka.TOPIC},${header.kafka.PARTITION},${header.kafka.OFFSET}");

        from("kafka:SGG-KAG?groupId=sync-syncload-sigungu")
                .log("${header.kafka.TOPIC},${header.kafka.PARTITION},${header.kafka.OFFSET}");

        from("kafka:SGG-THE?groupId=sync-syncload-sigungu")
                .log("${header.kafka.TOPIC},${header.kafka.PARTITION},${header.kafka.OFFSET}");
    }
}