package com.example.first_club.membership.health;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import org.springframework.boot.actuate.health.Health;

import java.util.Map;

import static java.lang.System.currentTimeMillis;
import static java.time.Instant.ofEpochMilli;
import static java.util.Map.entry;
import static java.util.Map.ofEntries;
import static org.springframework.boot.actuate.health.Status.UP;

@Component

public class CustomHealthIndicator implements HealthIndicator {

    private Long serviceStartTime = 0L;

    @PostConstruct
    public void init() {
        serviceStartTime = currentTimeMillis();
    }

    @Override
    public Health health() {
        Map<String, Object> details = ofEntries(
                entry("since", ofEpochMilli(serviceStartTime).toString()),
                entry("now", ofEpochMilli(currentTimeMillis()).toString()));
        Health.Builder builder = new Health.Builder(UP, details);
        return builder.build();

    }

}
