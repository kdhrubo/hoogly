package com.effectiv.crm;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.effectiv.crm.repository.SimpleBaseRepository;

@Configuration
@EnableJpaRepositories(repositoryBaseClass = SimpleBaseRepository.class)
public class JpaConfiguration {

}
