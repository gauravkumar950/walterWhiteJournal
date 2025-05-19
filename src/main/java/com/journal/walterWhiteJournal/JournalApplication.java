package com.journal.walterWhiteJournal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableMongoRepositories
@EnableTransactionManagement

public class JournalApplication {

	public static void main(String[] args) {
//		SpringApplication.run(JournalApplication.class, args);
		ConfigurableApplicationContext context = SpringApplication.run(JournalApplication.class,args);
		ConfigurableEnvironment env = context.getEnvironment();
		System.out.println("Current Active env is: "+env.getActiveProfiles()[0]);
	}
	@Bean
	MongoTransactionManager transactionManager(MongoDatabaseFactory dbFactory) {
		return new MongoTransactionManager(dbFactory);
	}


	//platformTransaction Manager
	//MongoTransaction Manager
}
