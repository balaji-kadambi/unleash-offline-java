# Unleash Offline Java

This project demonstrates how to use the [Unleash Java SDK](https://github.com/Unleash/unleash-client-java) for offline feature flag evaluation. It allows you to load feature flag configurations from a local JSON file and evaluate them without a connection to an Unleash server.

## Features

- **Offline Evaluation**: Load feature flags from a local `features.json` file.
- **Context Awareness**: Evaluate flags based on `UnleashContext` (e.g., `userId`, `siteId`, `providerId`).
- **Constraint Support**: Supports standard Unleash constraints like `IN` for targeted rollout.

## Project Structure

- `src/main/java/com/example/UnleashFeatureFlagEvaluator.java`: The core evaluator class that initializes the Unleash client in offline mode.
- `src/main/resources/features.json`: Local feature flag configuration file.
- `src/test/java/com/example/UnleashFeatureFlagEvaluatorTest.java`: Unit tests demonstrating various evaluation scenarios.

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6+

### Usage

The `UnleashFeatureFlagEvaluator` can be initialized with the name of the bootstrap resource:

```java
UnleashFeatureFlagEvaluator evaluator = new UnleashFeatureFlagEvaluator("features.json");

UnleashContext context = UnleashContext.builder()
        .addProperty("siteId", "Toronto")
        .build();

boolean isEnabled = evaluator.isEnabled("ff_site_id", context);
System.out.println("Feature ff_site_id enabled: " + isEnabled);
```

### Running Tests

To run the unit tests:

```bash
mvn test
```

## Configuration

The feature flags are defined in `src/main/resources/features.json`. You can add new flags or modify existing ones there.

Example flag definition:

```json
{
  "name": "ff_site_id",
  "enabled": true,
  "strategies": [
    {
      "name": "flexibleRollout",
      "parameters": {
        "groupId": "ff_site_id",
        "rollout": "100"
      },
      "constraints": [
        {
          "contextName": "siteId",
          "operator": "IN",
          "values": ["Toronto"]
        }
      ]
    }
  ]
}
```
