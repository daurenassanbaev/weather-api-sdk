package weatherapisdk.weatherapisdk.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@ExtendWith(MockitoExtension.class)
@DisplayName("CacheConfig Configuration Test")
class CacheConfigTest {

    @Test
    @DisplayName("Cache manager is created and works correctly")
    void givenCacheConfig_whenGetCacheManager_thenCacheWorksCorrectly() {
        // given
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(CacheConfig.class);
        CacheManager cacheManager = context.getBean(CacheManager.class);

        // then
        assertThat(cacheManager).isNotNull();
        assertThat(cacheManager).isInstanceOf(CaffeineCacheManager.class);

        Cache cache = cacheManager.getCache("testCache");
        assertThat(cache).isNotNull();

        String key = "key";
        String value = "value";
        cache.put(key, value);

        assertThat(cache.get(key, String.class)).isEqualTo(value);

        context.close();
    }
}