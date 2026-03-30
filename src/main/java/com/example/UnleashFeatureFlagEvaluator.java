package com.example;

import io.getunleash.DefaultUnleash;
import io.getunleash.Unleash;
import io.getunleash.util.UnleashConfig;
import io.getunleash.UnleashContext;
import io.getunleash.repository.ToggleBootstrapFileProvider;

public class UnleashFeatureFlagEvaluator implements FeatureFlagEvaluator {

    private final Unleash unleash;

    public UnleashFeatureFlagEvaluator(String bootstrapResourceName) {
        String bootstrapPath = "classpath:" + bootstrapResourceName;
        UnleashConfig config = UnleashConfig.builder()
                .appName("offline-app")
                .instanceId("offline-instance")
                .unleashAPI("http://localhost:4242/api/") // Dummy API URL
                .apiKey("development:default.secret")
                .environment("development")
                .synchronousFetchOnInitialisation(false)
                .disablePolling()
                .disableMetrics()
                .toggleBootstrapProvider(new ToggleBootstrapFileProvider(bootstrapPath))
                .subscriber(new io.getunleash.event.UnleashSubscriber() {
                    @Override
                    public void on(io.getunleash.event.UnleashEvent event) {
                        System.out.println("Unleash Event: " + event);
                    }
                })
                .build();

        this.unleash = new DefaultUnleash(config);
    }

    @Override
    public boolean isEnabled(String featureName, UnleashContext context) {
        return unleash.isEnabled(featureName, context);
    }

    public void shutdown() {
        unleash.shutdown();
    }
}
