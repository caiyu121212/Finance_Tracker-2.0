package com.github.caiyu121212.finance.tracker.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "com.financetracker.repository")
@EnableJpaAuditing
@EnableTransactionManagement
public class JpaConfig {
}