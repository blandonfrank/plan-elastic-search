package com.personal.plan.elasticsearch.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.personal.plan.elasticsearch.dao")
@ComponentScan(basePackages = "com.personal.plan.elasticsearch")
public class ElasticConfig {
}
