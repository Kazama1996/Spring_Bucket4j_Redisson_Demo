package com.example.demo.util.ratelimit.BandwidthProvider.impl;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.util.ratelimit.BucketFactory;
import com.example.demo.util.ratelimit.BandwidthProvider.BandwidthProvider;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Refill;
import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class PrivateBandwidthProvider implements BandwidthProvider {

    @Autowired
    private BucketFactory bucketFactory;

    @PostConstruct
    private void init() {

        bucketFactory.register("/private", this);
        System.out.println("Bucket created for /private: " + this);

    }

    @Override
    public Bandwidth getBandwidth() {
        Refill refill = Refill.intervally(10, Duration.ofSeconds(5));
        return Bandwidth.classic(30, refill).withInitialTokens(30);
    }

}
