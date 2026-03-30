package com.example;

import io.getunleash.UnleashContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UnleashFeatureFlagEvaluatorTest {

    private UnleashFeatureFlagEvaluator evaluator;

    @BeforeEach
    void setUp() throws InterruptedException {
        evaluator = new UnleashFeatureFlagEvaluator("features.json");
        // Wait for async initialization
        Thread.sleep(2000);
    }

    @AfterEach
    void tearDown() {
        evaluator.shutdown();
    }

    @Test
    void testSimpleFeature() {
        assertTrue(evaluator.isEnabled("test_feature_flag", UnleashContext.builder().build()));
    }

    @Test
    void testSiteIdConstraint() {
        UnleashContext toronto = UnleashContext.builder().addProperty("siteId", "Toronto").build();
        assertTrue(evaluator.isEnabled("ff_site_id", toronto));

        UnleashContext ny = UnleashContext.builder().addProperty("siteId", "New York").build();
        assertFalse(evaluator.isEnabled("ff_site_id", ny));
    }

    @Test
    void testProviderIdConstraint() {
        UnleashContext hp = UnleashContext.builder().addProperty("providerId", "HP").build();
        assertTrue(evaluator.isEnabled("ff_with_context", hp));

        UnleashContext nokia = UnleashContext.builder().addProperty("providerId", "Nokia").build();
        assertFalse(evaluator.isEnabled("ff_with_context", nokia));
    }
    
    @Test
    void testNonExistentFeature() {
        assertFalse(evaluator.isEnabled("non-existent", UnleashContext.builder().build()));
    }
}
