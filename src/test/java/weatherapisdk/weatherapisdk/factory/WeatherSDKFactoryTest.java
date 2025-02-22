package weatherapisdk.weatherapisdk.factory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import weatherapisdk.weatherapisdk.model.enums.Mode;
import weatherapisdk.weatherapisdk.service.WeatherSDK;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Tests for WeatherSDKFactory")
class WeatherSDKFactoryTest {

    private WeatherSDKFactory weatherSDKFactory;

    @BeforeEach
    void setUp() {
        weatherSDKFactory = new WeatherSDKFactory();
    }

    @Test
    @DisplayName("Retrieving an SDK instance should create a new one if not present")
    void givenApiKey_whenGetInstance_thenCreateNewInstance() {
        // given
        String apiKey = "test-api-key";
        Mode mode = Mode.POLLING;

        // when
        WeatherSDK sdk = weatherSDKFactory.getInstance(apiKey, mode);

        // then
        assertThat(sdk).isNotNull();
        assertThat(weatherSDKFactory.getAllInstances()).containsKey(apiKey);
    }

    @Test
    @DisplayName("Retrieving an SDK instance should return the existing one if present")
    void givenExistingApiKey_whenGetInstance_thenReturnExistingInstance() {
        // given
        String apiKey = "existing-api-key";
        Mode mode = Mode.POLLING;
        WeatherSDK firstInstance = weatherSDKFactory.getInstance(apiKey, mode);

        // when
        WeatherSDK secondInstance = weatherSDKFactory.getInstance(apiKey, mode);

        // then
        assertThat(firstInstance).isSameAs(secondInstance);
    }

    @Test
    @DisplayName("Removing an SDK instance should delete it from the factory")
    void givenApiKey_whenRemoveInstance_thenInstanceIsRemoved() {
        // given
        String apiKey = "removable-api-key";
        weatherSDKFactory.getInstance(apiKey, Mode.POLLING);

        // when
        weatherSDKFactory.removeInstance(apiKey);

        // then
        assertThat(weatherSDKFactory.getAllInstances()).doesNotContainKey(apiKey);
    }

    @Test
    @DisplayName("Getting all instances should return all stored SDKs")
    void whenGetAllInstances_thenReturnAllStoredSDKs() {
        // given
        weatherSDKFactory.getInstance("api-key-1", Mode.POLLING);
        weatherSDKFactory.getInstance("api-key-2", Mode.POLLING);

        // when
        Map<String, WeatherSDK> instances = weatherSDKFactory.getAllInstances();

        // then
        assertThat(instances).hasSize(2);
        assertThat(instances).containsKeys("api-key-1", "api-key-2");
    }
}