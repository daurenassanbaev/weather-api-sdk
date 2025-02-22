
# Weather API SDK

The Weather API SDK is a lightweight Java library designed to simplify access to the OpenWeather API. It provides an intuitive interface for retrieving real-time weather data efficiently while handling errors and API responses gracefully.

## Contents

- [Installation](#installation)
- [Configuration](#configuration)
- [Usage Example](#usage-example)

## Installation

### Maven

To install the project, run:

```bash
mvn clean install
```

If you want to use this project as a library, add the following to your `pom.xml`:

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.github.daurenassanbaev</groupId>
        <artifactId>weather-api-sdk</artifactId>
        <version>v1.0.0</version>
    </dependency>
</dependencies>
```

### Gradle

If you use Gradle, add the following to your build configuration:

```groovy
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}

dependencies {
    implementation 'com.github.daurenassanbaev:weather-api-sdk:v1.0.0'
}
```

## Configuration

To use the SDK, obtain an API key from OpenWeather and create an instance of `WeatherSDK`:

```java
// Step 1: Initialize the SDK factory
WeatherSDKFactory wsFactory = new WeatherSDKFactory();

// Step 2: Obtain an instance with your API key
WeatherSDK weatherSDK = wsFactory.getInstance("YOUR_API_KEY", Mode.ON_DEMAND);
```

## Usage Example

Below is an example of retrieving weather data for a city:

```java
// Retrieve weather data for a specific location (e.g., London)
WeatherData weatherData = weatherSDK.getWeather("London");

// Process or display the retrieved weather data
System.out.println(weatherData);
```