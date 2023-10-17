package com.example.demo.util.ratelimit.BandwidthProvider;

import io.github.bucket4j.Bandwidth;

public interface BandwidthProvider {

    Bandwidth getBandwidth();
}
