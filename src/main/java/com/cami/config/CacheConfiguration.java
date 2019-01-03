package com.cami.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.cami.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(com.cami.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(com.cami.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.cami.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.cami.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.cami.domain.DemandeImportation.class.getName(), jcacheConfiguration);
            cm.createCache(com.cami.domain.DIQteTypeVehicule.class.getName(), jcacheConfiguration);
            cm.createCache(com.cami.domain.Marque.class.getName(), jcacheConfiguration);
            cm.createCache(com.cami.domain.Vehicule.class.getName(), jcacheConfiguration);
            cm.createCache(com.cami.domain.TypeVehicule.class.getName(), jcacheConfiguration);
            cm.createCache(com.cami.domain.Caution.class.getName(), jcacheConfiguration);
            cm.createCache(com.cami.domain.Declaration.class.getName(), jcacheConfiguration);
            cm.createCache(com.cami.domain.RVC.class.getName(), jcacheConfiguration);
            cm.createCache(com.cami.domain.Banque.class.getName(), jcacheConfiguration);
            cm.createCache(com.cami.domain.PieceJointe.class.getName(), jcacheConfiguration);
            cm.createCache(com.cami.domain.Appurement.class.getName(), jcacheConfiguration);
            cm.createCache(com.cami.domain.Employe.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
