//package com.observability.filter;
//
//import io.opentelemetry.api.GlobalOpenTelemetry;
//import io.opentelemetry.api.trace.Span;
//import io.opentelemetry.api.trace.SpanKind;
//import io.opentelemetry.api.trace.Tracer;
//import io.opentelemetry.context.Scope;
//import jakarta.annotation.PostConstruct;
//import jakarta.servlet.*;
//import jakarta.servlet.http.HttpServletRequest;
//import org.slf4j.MDC;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//
//@Component
//public class TracingFilter implements Filter {
//
//    private Tracer tracer;
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//
//        if (tracer == null) {
//            tracer = GlobalOpenTelemetry.getTracer("observability");
//        }
//
//        HttpServletRequest httpRequest = (HttpServletRequest) request;
//
//        Span span = tracer.spanBuilder(httpRequest.getMethod() + " " + httpRequest.getRequestURI())
//                .setSpanKind(SpanKind.SERVER)
//                .startSpan();
//
//        try (Scope scope = span.makeCurrent()) {
//            // Inject trace ID to logs
//            MDC.put("trace_id", span.getSpanContext().getTraceId());
//            MDC.put("span_id", span.getSpanContext().getSpanId());
//
//            chain.doFilter(request, response);
//
//        } finally {
//            MDC.clear();
//            span.end();
//        }
//    }
//}
