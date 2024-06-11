package com.demo.mockservice.config;

import com.demo.mockservice.filter.DynamicResponseFilter;
import org.springframework.boot.actuate.trace.http.HttpExchangeTracer;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.web.trace.servlet.HttpTraceFilter;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Component
// HTTP Trace actuator filter (excludes the "swagger", "actuator" requests)
public class TraceRequestFilter extends HttpTraceFilter {

    public TraceRequestFilter(HttpTraceRepository repository, HttpExchangeTracer tracer) {
        super(repository, tracer);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        if (request.getServletPath().equals("/")) {
            return true;
        }
        return Arrays.stream(DynamicResponseFilter.EXCLUDE_URL_LIST).anyMatch(request.getServletPath()::contains);
    }
}