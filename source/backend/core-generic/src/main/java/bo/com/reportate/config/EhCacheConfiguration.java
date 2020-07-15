package bo.com.reportate.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by :Reportate
 * Autor      :Ricardo Laredo
 * Email      :rllayus@gmail.com
 * Date       :12-01-19
 * Project    :reportate
 * Package    :bo.com.reportate.config
 * Copyright  : Reportate
 */

@EnableJpaRepositories(basePackages = "bo.com.reportate.repository")
@Configuration
@EnableCaching
public class EhCacheConfiguration {

    @Bean
    public CacheManager cacheManager(){
        return new EhCacheCacheManager(cacheManagerFactory().getObject());
    }

    @Bean
    public EhCacheManagerFactoryBean cacheManagerFactory() {
        EhCacheManagerFactoryBean bean = new EhCacheManagerFactoryBean();
        bean.setConfigLocation(new ClassPathResource("ehcache.xml"));
        bean.setShared(true);
        return bean;
    }
}
