package io.github.jhipster.application.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, io.github.jhipster.application.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, io.github.jhipster.application.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, io.github.jhipster.application.domain.User.class.getName());
            createCache(cm, io.github.jhipster.application.domain.Authority.class.getName());
            createCache(cm, io.github.jhipster.application.domain.User.class.getName() + ".authorities");
            createCache(cm, io.github.jhipster.application.domain.Escuela.class.getName());
            createCache(cm, io.github.jhipster.application.domain.Ciclo.class.getName());
            createCache(cm, io.github.jhipster.application.domain.Subciclo.class.getName());
            createCache(cm, io.github.jhipster.application.domain.Tipoperiodo.class.getName());
            createCache(cm, io.github.jhipster.application.domain.Escolaridad.class.getName());
            createCache(cm, io.github.jhipster.application.domain.Persona.class.getName());
            createCache(cm, io.github.jhipster.application.domain.Jornada.class.getName());
            createCache(cm, io.github.jhipster.application.domain.Financiamiento.class.getName());
            createCache(cm, io.github.jhipster.application.domain.Funcionlaboral.class.getName());
            createCache(cm, io.github.jhipster.application.domain.Motivomov.class.getName());
            createCache(cm, io.github.jhipster.application.domain.Tipomov.class.getName());
            createCache(cm, io.github.jhipster.application.domain.Partida.class.getName());
            createCache(cm, io.github.jhipster.application.domain.Categoria.class.getName());
            createCache(cm, io.github.jhipster.application.domain.Plaza.class.getName());
            createCache(cm, io.github.jhipster.application.domain.Horariolab.class.getName());
            createCache(cm, io.github.jhipster.application.domain.Direccion.class.getName());
            createCache(cm, io.github.jhipster.application.domain.Usuario.class.getName());
            createCache(cm, io.github.jhipster.application.domain.Rol.class.getName());
            createCache(cm, io.github.jhipster.application.domain.Rol.class.getName() + ".funcions");
            createCache(cm, io.github.jhipster.application.domain.Modulo.class.getName());
            createCache(cm, io.github.jhipster.application.domain.Permiso.class.getName());
            createCache(cm, io.github.jhipster.application.domain.Funcion.class.getName());
            createCache(cm, io.github.jhipster.application.domain.Funcion.class.getName() + ".rols");
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cm.destroyCache(cacheName);
        }
        cm.createCache(cacheName, jcacheConfiguration);
    }
}
