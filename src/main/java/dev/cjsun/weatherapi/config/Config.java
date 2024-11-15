package dev.cjsun.weatherapi.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableJpaRepositories(basePackages = "dev.cjsun.weatherapi.repository")
@EnableFeignClients(basePackages = "dev.cjsun.weatherapi.proxy")
public class Config {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

//    @Bean
//    public EntityManagerFactory entityManagerFactory() {
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistence");
//        return emf;
//    }
//    @Bean
//    public EntityManager entityManager() {
//        EntityManagerFactory emf = entityManagerFactory();
//        EntityManager em = emf.createEntityManager();
//        return em;
//    }
}
