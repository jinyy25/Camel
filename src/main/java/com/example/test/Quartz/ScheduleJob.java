package com.example.test.Quartz;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.support.DefaultExchange;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class ScheduleJob implements Job {
    @Autowired
    private ProducerTemplate template;
    @Autowired
    private CamelContext camelContext;

    public ScheduleJob() {
    }

    public void execute(JobExecutionContext context) throws JobExecutionException {
        Exchange exchange = new DefaultExchange(this.camelContext);
        exchange.setProperty("TriggerId", context.getTrigger().getKey().getName());
        exchange.setProperty("TriggerGroup", context.getTrigger().getKey().getGroup());
        exchange.setProperty("JobExecutionContext", context);
        exchange.setProperty("JobData", context.getMergedJobDataMap());
        exchange.setProperty("BaseDate", context.getMergedJobDataMap().get("BaseDate"));
        String routeId = context.getTrigger().getKey().getName();
        Exchange exchange1 = this.template.send("direct:" + routeId, exchange);
    }
}
