package weatherapisdk.weatherapisdk.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import weatherapisdk.weatherapisdk.client.WeatherApiClient;
import weatherapisdk.weatherapisdk.model.WeatherData;
import weatherapisdk.weatherapisdk.model.enums.Mode;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@DisplayName("Tests for WeatherSDK Service")
class WeatherSDKTest {

    private WeatherSDK weatherSDK;

    @Mock
    private WeatherApiClient weatherApiClient;

    @Mock
    private WeatherData mockWeatherData;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        weatherSDK = new WeatherSDK("test-api-key", Mode.POLLING, weatherApiClient);
    }

    @Test
    @DisplayName("Retrieving weather data should return expected WeatherData")
    void givenCity_whenGetWeather_thenReturnsWeatherData() {
        // given
        String city = "Almaty";
        when(weatherApiClient.fetchWeatherData(anyString(), anyString())).thenReturn(mockWeatherData);

        // when
        WeatherData result = weatherSDK.getWeather(city);

        // then
        assertThat(result).isEqualTo(mockWeatherData);
        assertThat(weatherSDK.getRequestedCities()).contains(city.toLowerCase());
    }

    @Test
    @DisplayName("Updating weather data should return updated WeatherData")
    void givenCity_whenUpdateWeather_thenReturnsUpdatedWeatherData() {
        // given
        String city = "Astana";
        when(weatherApiClient.fetchWeatherData(anyString(), anyString())).thenReturn(mockWeatherData);

        // when
        WeatherData result = weatherSDK.updateWeather(city);

        // then
        assertThat(result).isEqualTo(mockWeatherData);
    }

    @Test
    @DisplayName("Get API key should return correct key")
    void whenGetApiKey_thenReturnsCorrectKey() {
        assertThat(weatherSDK.getApiKey()).isEqualTo("test-api-key");
    }

    @Test
    @DisplayName("Get mode should return correct mode")
    void whenGetMode_thenReturnsCorrectMode() {
        assertThat(weatherSDK.getMode()).isEqualTo(Mode.POLLING);
    }

    @Test
    @DisplayName("Requested cities should be stored correctly")
    void whenRequestingWeather_thenCityShouldBeStored() {
        // given
        String city = "London";
        when(weatherApiClient.fetchWeatherData(anyString(), anyString())).thenReturn(mockWeatherData);

        // when
        weatherSDK.getWeather(city);

        // then
        assertThat(weatherSDK.getRequestedCities()).contains(city.toLowerCase());
    }
}