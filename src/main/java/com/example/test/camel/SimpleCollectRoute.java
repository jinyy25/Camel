package com.example.test.camel;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.RouteDefinition;

public class SimpleCollectRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        // Kafka에서 데이터를 소비하고 JSON을 언마샬링하여 로그 출력 후 SQL로 삽입
        from("kafka:simple-extract-001?autoStartup=true")
                .unmarshal().json()
                .log("${body}")
                .to("jdbc:dataSource?useHeadersAsParameters=true&batch=true")
                .log("Data inserted into database");
    }
}