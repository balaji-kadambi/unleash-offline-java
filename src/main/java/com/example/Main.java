package com.example;

import io.getunleash.UnleashContext;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Initializing Unleash Feature Flag Evaluator...");
        UnleashFeatureFlagEvaluator evaluator = new UnleashFeatureFlagEvaluator("features.json");

        try {
            // Wait for initialization (it's async)
            Thread.sleep(2000);

            // Test Case 1: Simple feature with no constraints
            String feature1 = "test_feature_flag";
            boolean isEnabled1 = evaluator.isEnabled(feature1, UnleashContext.builder().build());
            System.out.println("Feature '" + feature1 + "' (no constraints) enabled: " + isEnabled1);

            // Test Case 2: Feature with siteId constraint (Toronto)
            String feature2 = "ff_site_id";
            UnleashContext contextToronto = UnleashContext.builder().addProperty("siteId", "Toronto").build();
            boolean isEnabled2_Toronto = evaluator.isEnabled(feature2, contextToronto);
            System.out.println("Feature '" + feature2 + "' for siteId=Toronto enabled: " + isEnabled2_Toronto);

            // Test Case 3: Feature with siteId constraint (New York)
            UnleashContext contextNY = UnleashContext.builder().addProperty("siteId", "New York").build();
            boolean isEnabled2_NY = evaluator.isEnabled(feature2, contextNY);
            System.out.println("Feature '" + feature2 + "' for siteId=New York enabled: " + isEnabled2_NY);

            // Test Case 4: Feature with providerId constraint (HP)
            String feature3 = "ff_with_context";
            UnleashContext contextHP = UnleashContext.builder().addProperty("providerId", "HP").build();
            boolean isEnabled3_HP = evaluator.isEnabled(feature3, contextHP);
            System.out.println("Feature '" + feature3 + "' for providerId=HP enabled: " + isEnabled3_HP);

            // Test Case 5: Feature with providerId constraint (Nokia)
            UnleashContext contextNokia = UnleashContext.builder().addProperty("providerId", "Nokia").build();
            boolean isEnabled3_Nokia = evaluator.isEnabled(feature3, contextNokia);
            System.out.println("Feature '" + feature3 + "' for providerId=Nokia enabled: " + isEnabled3_Nokia);

        } finally {
            evaluator.shutdown();
        }
    }
}
