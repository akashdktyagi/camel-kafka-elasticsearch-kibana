package com.tdoc.listener;

import com.google.common.base.Throwables;
import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.component.ComponentsBuilderFactory;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.builder.RouteBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Slf4j
public class KafkaListener implements ConcurrentEventListener {

    CamelContext camelContext;
    String topicName = "cucumber-scenario-results";
    String brokers = "localhost:9092";

    @Override
    public void setEventPublisher(EventPublisher eventPublisher) {
        eventPublisher.registerHandlerFor(TestRunStarted.class, this::testRunStarted);
        eventPublisher.registerHandlerFor(TestCaseStarted.class, this::testCaseStarted);
        eventPublisher.registerHandlerFor(TestCaseFinished.class, this::testCaseFinish);
        eventPublisher.registerHandlerFor(TestRunFinished.class, this::testRunFinished);
    }

    private void testRunStarted(TestRunStarted event){
        System.out.println("Test Run Started");

        camelContext = new DefaultCamelContext();
        ComponentsBuilderFactory.kafka().register(camelContext, "kafka");
//        + System.getProperty("user.dir") + "/
        try {
            camelContext.addRoutes(new RouteBuilder() {
                @Override
                public void configure() {
                    from("file:results?noop=true")
                            .convertBodyTo(String.class)
                            .to("kafka:"+topicName+"?brokers="+brokers);
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        camelContext.start();
        System.out.println("Camel Context Started");

    }

    private void testCaseStarted(TestCaseStarted event){
        System.out.println("Test Case Started: " + event.getTestCase().getName());
    }

    private void testCaseFinish(TestCaseFinished event){
        System.out.println("Test Case Finished: " + event.getTestCase().getName());
        System.out.println(getTestCaseStepsWithStatusAndErrorLogs(event).toString());
        writeToFiles("results/"+event.getTestCase().getName()+".txt", getTestCaseStepsWithStatusAndErrorLogs(event).toString());
    }

    private void testRunFinished(TestRunFinished event){
        System.out.println("Test Run Finished");
        camelContext.stop();
        System.out.println("Camel Context Stopped");
    }

    private String getTestSteps(TestCaseFinished event){
        List<TestStep> testStepList = event.getTestCase().getTestSteps();
        StringBuffer testStepListString = new StringBuffer();
        testStepList.forEach(x->{
            if (x instanceof PickleStepTestStep){
                testStepListString.append(
                    ((PickleStepTestStep) x).getStep().getKeyword() + " " +
                    ((PickleStepTestStep) x).getStep().getText() + " \n"
                );
                if (((PickleStepTestStep) x).getStep().getArgument() != null) {
                    if (((PickleStepTestStep) x).getStep().getArgument() instanceof DocStringArgument) {

                        testStepListString.append(
                                ("\"\"\"\n") +
                                        (((DocStringArgument) ((PickleStepTestStep) x).getStep().getArgument()).getContent()) +
                                        ("\n\"\"\"\n")
                        );
                    }
                    if (((PickleStepTestStep) x).getStep().getArgument() instanceof DataTableArgument) {

                        List<List<String>> ParamDataTable = (((DataTableArgument) ((PickleStepTestStep) x).getStep().getArgument()).cells());
                        ParamDataTable.forEach(ParamRow -> {
                                    ParamRow.forEach(ParamCol -> {
                                        testStepListString.append(
                                                "|" + ParamCol
                                        );
                                    });
                                    testStepListString.append("|\n");
                                }
                        );
                    }
                }

            }
        });
        return testStepListString.toString();
    }

    private StringBuffer getTestCaseStepsWithStatusAndErrorLogs(TestCaseFinished event){
        StringBuffer testStepListString = new StringBuffer();
        testStepListString.append("Scenario Steps: ");
        testStepListString.append("\n Scenario: " + event.getTestCase().getName() + " \n \n");
        testStepListString.append(getTestSteps(event));

        testStepListString.append("\n \n ----------------------------------------------------------");
        testStepListString.append("\n --------------------  ERROR LOGS  -----------------------");
        testStepListString.append("\n  ----------------------------------------------------------");
        if (event.getResult().getError() !=null){
            testStepListString.append("\n Execution Error Log: \n \n " + Throwables.getStackTraceAsString(event.getResult().getError()));
        }
        return testStepListString;
    }

    private void writeToFiles(String fileName, String content){
        // Create a FileWriter object
        try (FileWriter fileWriter = new FileWriter(fileName, false)) {
            // Write the content to the file
            fileWriter.write(content);
            System.out.println("File written successfully.");
        } catch (IOException e) {
            // Handle any I/O errors
            System.err.println("An IOException was caught: " + e.getMessage());
        }
    }
}
