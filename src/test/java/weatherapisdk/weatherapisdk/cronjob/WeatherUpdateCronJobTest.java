package weatherapisdk.weatherapisdk.cronjob;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import weatherapisdk.weatherapisdk.factory.WeatherSDKFactory;
import weatherapisdk.weatherapisdk.model.enums.Mode;
import weatherapisdk.weatherapisdk.service.WeatherSDK;

import java.util.Map;
import java.util.Set;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests for WeatherUpdateCronJob")
class WeatherUpdateCronJobTest {

    @InjectMocks
    private WeatherUpdateCronJob weatherUpdateCronJob;

    @Mock
    private WeatherSDKFactory weatherSDKFactory;

    @Mock
    private WeatherSDK weatherSDK;

    @BeforeEach
    void setUp() {
        when(weatherSDK.getMode()).thenReturn(Mode.POLLING);
        when(weatherSDK.getRequestedCities()).thenReturn(Set.of("London", "New York"));
        when(weatherSDKFactory.getAllInstances()).thenReturn(Map.of("apiKey1", weatherSDK));
    }

    @Test
    @DisplayName("Weather updates should be triggered for SDKs in POLLING mode")
    void givenPollingMode_whenPollWeatherUpdates_thenUpdateWeatherForCities() {
        // when
        weatherUpdateCronJob.pollWeatherUpdates();

        // then
        verify(weatherSDK, times(1)).updateWeather("London");
        verify(weatherSDK, times(1)).updateWeather("New York");
    }

    @Test
    @DisplayName("Exceptions during weather updates should be logged")
    void givenUpdateFailure_whenPollWeatherUpdates_thenLogError() {
        // given
        doThrow(new RuntimeException("Update failed"))
                .when(weatherSDK).updateWeather("London");

        // when
        weatherUpdateCronJob.pollWeatherUpdates();

        // then
        verify(weatherSDK, times(1)).updateWeather("London");
        verify(weatherSDK, times(1)).updateWeather("New York");
    }
}
