package com.example.test.kafka;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.AggregateDefinition;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.processor.aggregate.GroupedBodyAggregationStrategy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.util.Map;

@ConditionalOnProperty(
        name = {"agent-type"},
        havingValue = "consumer"
)
public class KafkaConsumer2 extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("kafka:init-test-002?consumersCount=10")
                .autoStartup(true)
                .unmarshal().json(JsonLibrary.Jackson)
                .process(exchange -> {
                    Map<String, Object> body = exchange.getMessage().getBody(Map.class);
                    body.put("Board_Row", exchange.getExchangeId());
                })
                .aggregate(constant(true), new GroupedBodyAggregationStrategy())
                .completionTimeout(5000L)
                .completionSize(5000)
                .eagerCheckCompletion()
                .to("jdbc:dataSource?useHeadersAsParameters=true&statement=INSERT INTO board VALUES (100,'Hello','Operation',SYSDATE,'나야나','1')")
                .log("Inserted")
                .end();
    }
}