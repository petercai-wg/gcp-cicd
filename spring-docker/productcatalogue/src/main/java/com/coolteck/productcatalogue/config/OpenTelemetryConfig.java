package com.coolteck.productcatalogue.config;

import io.opentelemetry.api.OpenTelemetry;

import io.opentelemetry.api.GlobalOpenTelemetry;

import io.opentelemetry.sdk.resources.Resource;

import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter;

import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.OpenTelemetrySdkBuilder;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenTelemetryConfig {

        @Bean
        public OpenTelemetry openTelemetry() {
                System.out.println("Configuring OpenTelemetry SDK");

                // 1. Exporter (connects to collector)
                OtlpGrpcSpanExporter exporter = OtlpGrpcSpanExporter.builder()
                                .setEndpoint("http://localhost:4317")
                                .build();

                // 2. Resource (service metadata)
                Resource resource = Resource
                                .getDefault()
                                .merge(Resource.builder().put("service.name", "ProductCatalogService").build());

                // 3. Tracer Provider
                SdkTracerProvider tracerProvider = SdkTracerProvider.builder()
                                .setResource(resource)
                                .addSpanProcessor(
                                                BatchSpanProcessor.builder(exporter).build())
                                .build();

                // 4. Build OpenTelemetry
                OpenTelemetrySdk openTelemetry = OpenTelemetrySdk.builder()
                                .setTracerProvider(tracerProvider)
                                .build();

                // 5. Register globally (important)
                // GlobalOpenTelemetry.set(openTelemetry);

                System.out.println("Register globally OpenTelemetry ");

                return openTelemetry;
        }
}