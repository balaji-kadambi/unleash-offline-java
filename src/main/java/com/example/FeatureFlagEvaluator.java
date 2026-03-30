package com.example;

import io.getunleash.UnleashContext;

public interface FeatureFlagEvaluator {
    boolean isEnabled(String featureName, UnleashContext context);
}
