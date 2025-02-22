package weatherapisdk.weatherapisdk.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import weatherapisdk.weatherapisdk.exception.CityNotFoundException;
import weatherapisdk.weatherapisdk.exception.InvalidAPIKeyException;
import weatherapisdk.weatherapisdk.exception.NetworkException;
import weatherapisdk.weatherapisdk.model.WeatherData;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WeatherApiClientTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private WeatherApiClient weatherApiClient;

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(weatherApiClient, "restTemplate", restTemplate);
        ReflectionTestUtils.setField(weatherApiClient, "objectMapper", objectMapper);
    }

    @Test
    void testFetchWeatherData_Success() throws Exception {
        // Given
        String city = "London";
        String apiKey = "test-api-key";
        String url = "https://api.openweathermap.org/data/2.5/weather?q=london&appid=test-api-key";
        String jsonResponse = "{\"weather\":[],\"main\":{}}";
        WeatherData mockWeatherData = new WeatherData();

        when(restTemplate.getForEntity(url, String.class)).thenReturn(ResponseEntity.ok(jsonResponse));
        when(objectMapper.readValue(jsonResponse, WeatherData.class)).thenReturn(mockWeatherData);

        // When
        WeatherData result = weatherApiClient.fetchWeatherData(city, apiKey);

        // Then
        assertNotNull(result);
        verify(restTemplate).getForEntity(url, String.class);
        verify(objectMapper).readValue(jsonResponse, WeatherData.class);
    }

    @Test
    void testFetchWeatherData_CityNotFoundException() {
        // Given
        String city = "UnknownCity";
        String apiKey = "test-api-key";
        String url = "https://api.openweathermap.org/data/2.5/weather?q=unknowncity&appid=test-api-key";

        when(restTemplate.getForEntity(url, String.class))
                .thenThrow(HttpClientErrorException.NotFound.class);

        // Then
        assertThrows(CityNotFoundException.class, () -> weatherApiClient.fetchWeatherData(city, apiKey));
    }

    @Test
    void testFetchWeatherData_InvalidAPIKeyException() {
        // Given
        String city = "London";
        String apiKey = "invalid-key";
        String url = "https://api.openweathermap.org/data/2.5/weather?q=london&appid=invalid-key";

        when(restTemplate.getForEntity(url, String.class))
                .thenThrow(HttpClientErrorException.Unauthorized.class);

        // Then
        assertThrows(InvalidAPIKeyException.class, () -> weatherApiClient.fetchWeatherData(city, apiKey));
    }

    @Test
    void testFetchWeatherData_NetworkException() {
        // Given
        String city = "London";
        String apiKey = "test-api-key";
        String url = "https://api.openweathermap.org/data/2.5/weather?q=london&appid=test-api-key";

        when(restTemplate.getForEntity(url, String.class))
                .thenThrow(new ResourceAccessException("Network error"));

        // Then
        assertThrows(NetworkException.class, () -> weatherApiClient.fetchWeatherData(city, apiKey));
    }

    @Test
    void testFetchWeatherData_RuntimeException() {
        // Given
        String city = "London";
        String apiKey = "test-api-key";
        String url = "https://api.openweathermap.org/data/2.5/weather?q=london&appid=test-api-key";

        when(restTemplate.getForEntity(url, String.class))
                .thenThrow(new RuntimeException("Unexpected error"));

        // Then
        assertThrows(RuntimeException.class, () -> weatherApiClient.fetchWeatherData(city, apiKey));
    }
}
