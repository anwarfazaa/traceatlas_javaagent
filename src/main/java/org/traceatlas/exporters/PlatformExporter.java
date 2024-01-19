package org.traceatlas.exporters;

import com.sun.net.httpserver.Authenticator;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.sdk.common.CompletableResultCode;
import io.opentelemetry.sdk.trace.data.SpanData;
import io.opentelemetry.sdk.trace.export.SpanExporter;
import java.util.Collection;

public class PlatformExporter {

    static String endpoint;

    static String serviceName;

    public PlatformExporter(String endpoint,String serviceName) {
        PlatformExporter.endpoint = endpoint;
        PlatformExporter.serviceName = serviceName;
    }

    public static class RestExporter implements SpanExporter {
        private final String endpoint;
        private final String serviceName;

        public RestExporter(String endpoint, String serviceName) {
            this.endpoint = endpoint;
            this.serviceName = serviceName;
        }

        @Override
        public CompletableResultCode export(Collection<SpanData> spans) {
            // Implement logic to send spans to the REST endpoint
            // Use HTTP client or any other method to send spans to the endpoint
            // 'spans' contains the trace data to be sent

            // Example: Sending spans to the REST endpoint
            // Replace this with your actual implementation
            System.out.println("Sending spans to the endpoint: " + endpoint);
            System.out.println("Service Name: " + serviceName);
            System.out.println("Number of spans: " + spans.size());
            // Your logic to send spans to the REST API

            return CompletableResultCode.ofSuccess();
        }

        @Override
        public CompletableResultCode flush() {
            return null;
        }

        @Override
        public CompletableResultCode shutdown() {
            return null;
        }


        @Override
        public void close() {
            SpanExporter.super.close();
        }
    }
}