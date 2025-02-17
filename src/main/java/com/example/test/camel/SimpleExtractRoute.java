package com.example.test.camel;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.RouteDefinition;

public class SimpleExtractRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        // Timer를 사용하여 주기적으로 SQL 쿼리를 실행하고, 결과를 JSON으로 마샬링 후 Kafka에 전송
        from("timer:test112?repeatCount=1")
                .to("jdbc:dataSource?useHeadersAsParameters=true") // SQL 쿼리 실행
                .marshal().json() // 결과를 JSON으로 마샬링
                .log("${body}") // 로그로 출력
                .to("kafka:simple-extract-001"); // Kafka로 전송
    }
}

