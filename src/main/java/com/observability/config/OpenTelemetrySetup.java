package com.observability.config;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.SimpleSpanProcessor;
import io.opentelemetry.sdk.trace.export.SpanExporter;
import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

import static io.opentelemetry.semconv.ResourceAttributes.SERVICE_NAME;

@Configuration
public class OpenTelemetrySetup {

//    @PostConstruct
//    public void init() {
//        SpanExporter otlpExporter = OtlpGrpcSpanExporter.builder()
//                .setEndpoint("http://tempo:4319") // ✅ must match Tempo config
//                .setTimeout(Duration.ofSeconds(5))
//                .build();
//
//        SdkTracerProvider tracerProvider = SdkTracerProvider.builder()
//                .addSpanProcessor(SimpleSpanProcessor.create(otlpExporter))
//                .setResource(Resource.create(Attributes.of(SERVICE_NAME, "observability")))
//                .build();
//
//       OpenTelemetrySdk.builder()
//                .setTracerProvider(tracerProvider)
//                .buildAndRegisterGlobal();
//
//        Runtime.getRuntime().addShutdownHook(new Thread(tracerProvider::close));
//    }
}
