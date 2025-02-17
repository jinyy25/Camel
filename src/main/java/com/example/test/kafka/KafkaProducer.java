package com.example.test.kafka;

import com.example.test.camel.EncryptBodyProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.ProcessorDefinition;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.spi.annotations.Component;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

@Component("KafkaProducer")
public class KafkaProducer extends RouteBuilder {

    @Autowired
    private EncryptBodyProcessor encryptor;

    @Override
    public void configure() throws Exception {
        List<String> list = Arrays.asList("SGG-CHC", "SGG-WJU"
              );

        String delMsg = "{\"transformId\":\"SGG-MANUAL-DELETE\",\"target\":\"SGG\",\"table\":\"RSDTH055\",\"operation\":\"Delete\",\"row\":{},\"primaryKey\":{},\"sourceTable\":\"MANUAL\"}";

        from("timer:SGG-KYS?repeatCount=1")
                .setBody().constant(delMsg)
                .process(encryptor)
                .to("kafka:SGG-KYS")
                .log("Produced to Kafka topic named SGG-KYS");
    }
}