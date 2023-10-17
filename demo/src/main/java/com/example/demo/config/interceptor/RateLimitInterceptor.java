package com.example.demo.config.interceptor;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.demo.util.ratelimit.BucketFactory;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RateLimitInterceptor implements HandlerInterceptor {

    @Autowired
    private BucketFactory bucketFactory;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws IOException, InstantiationException, IllegalAccessException, NoSuchMethodException,
            InvocationTargetException {
        String uri = request.getRequestURI();

        Bucket bucket = bucketFactory.getBucket(uri);
        ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(2);

        if (probe.isConsumed()) {
            return true;
        }

        response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
        response.getWriter().write("Rate limit exceeded. \n");
        return false;
    }

}
