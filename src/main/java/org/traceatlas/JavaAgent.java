package org.traceatlas;


import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.propagation.W3CTraceContextPropagator;
import io.opentelemetry.context.propagation.ContextPropagators;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor;
import io.opentelemetry.sdk.trace.export.SpanExporter;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import org.traceatlas.exporters.PlatformExporter;

import java.lang.instrument.Instrumentation;

public class JavaAgent {

    public static void premain(String agentArgs, Instrumentation instrumentation) {
        initialize();
    }

    public static void initialize() {
        // Replace these values with your REST API endpoint details
        String endpoint = "http://your-rest-api-endpoint/"; // Change this to your endpoint
        String serviceName = "my-service";

        // Create an exporter for sending spans to the REST API
        SpanExporter exporter = new PlatformExporter.RestExporter(endpoint,serviceName);

        // Create a span processor
        BatchSpanProcessor spanProcessor = BatchSpanProcessor.builder(exporter).build();

        // Configure the SDK
        SdkTracerProvider tracerProvider = SdkTracerProvider.builder()
                .setResource(Resource.create(Resource.getDefault().getAttributes()))
                .addSpanProcessor(spanProcessor)
                .build();

        // Create an OpenTelemetry instance
        OpenTelemetry openTelemetry = OpenTelemetrySdk.builder()
                .setTracerProvider(tracerProvider)
                .setPropagators(ContextPropagators.create(W3CTraceContextPropagator.getInstance()))
                .buildAndRegisterGlobal();

        // Set the OpenTelemetry instance globally
        io.opentelemetry.api.GlobalOpenTelemetry.set(openTelemetry);
    }


    public static void main(String[] args) {
        System.out.println("Trace Atlas Agent loading...");
    }
}