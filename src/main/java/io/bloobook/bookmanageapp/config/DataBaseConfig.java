package io.bloobook.bookmanageapp.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import javax.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @CreateBy: Bloo
 * @Date: 2021/05/06
 */

@EnableJpaAuditing
@Configuration
public class DataBaseConfig {
    @Bean
    public JPAQueryFactory jpaQueryFactory( EntityManager entityManager) {
        return new JPAQueryFactory(entityManager);
    }
}
