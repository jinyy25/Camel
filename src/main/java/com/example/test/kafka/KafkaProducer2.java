package com.example.test.kafka;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.ExpressionNode;
import org.apache.camel.model.ProcessorDefinition;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.model.SplitDefinition;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.spi.annotations.Component;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ConditionalOnProperty(
        name = {"agent-type"},
        havingValue = "producer"
)
@Component("KafkaProducer2")
public class KafkaProducer2 extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        List<Map<Integer, String>> list = new ArrayList<>();

        for (int i = 0; i < 999999; ++i) {
            Map<Integer, String> map = new HashMap<>();
            map.put(i, "a");
            list.add(map);
        }

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        from("timer:watch?repeatCount=1")
                .routeId("init")
                .log("start")
                .setBody(constant(list))
                .to("sql:select * from board?outputType=StreamList")
                .split(body()) // Split the list into individual items
                .streaming()
                .parallelProcessing(true)
                .stopOnException()
                .log("start ${exchangeProperty.CamelSplitIndex}")
                .marshal().json(JsonLibrary.Jackson)
                .to("kafka:init-test-002?lingerMs=3000&producerBatchSize=1048576")
                .log("end ${exchangeProperty.CamelSplitIndex}")
                .end()
                .process(exchange -> {
                    stopWatch.stop();
                    System.out.println("Total time: " + stopWatch.getTotalTimeSeconds() + " seconds");
                })
                .log("Complete start extract");
    }
}