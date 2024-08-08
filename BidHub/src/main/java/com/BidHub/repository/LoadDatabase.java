package com.BidHub.repository;

import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.BidHub.entity.*;

@Configuration
class LoadDatabase {

	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

	@Bean
	CommandLineRunner initDatabase(UserRepository userRepository, BidItemRepository bidItemRepository) {

		return args -> {

//			User admin = new User("Nars", "Gumsuv", "admin@gmail.com", "416-736-2100", "4700 Keele St", null, "M3J 1P3",
//					"Toronto", "Canada", "admin", "Admin!Pass24");
//			
//			admin.setRole("ROLE_ADMIN");
//
//			userRepository.save(admin);
//
//			userRepository.save(new User("Bilbo", "Baggins", "bilbo@gmail.com", "123-456-7890", "1 Bagshot Row", null,
//					"H1H 0S0", "Hobbiton", "Shire", "bilbobaggins", "First@User01"));

			userRepository.findAll().forEach(user -> log.info("Preloaded " + user));

//			bidItemRepository.save(new BidItem(userRepository.findByUsername("bilbobaggins").get().getId(), "Iphone 15",
//					"New Iphone 15, not opened from box", true, false, 0, 1000.00, Status.IN_PROGRESS));
//
//			bidItemRepository.save(new BidItem(userRepository.findByUsername("bilbobaggins").get().getId(), "MacBook",
//					"New MacBook 15, not opened from box", false, true, 24.0, 150.00, Status.IN_PROGRESS));

			bidItemRepository.findAll().forEach(bidItem -> log.info("Preloaded " + bidItem));

		};
	}

	@Autowired
	Environment env;

	@Bean
	public DataSource dataSource() {
		final DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
		dataSource.setUrl(env.getProperty("spring.datasource.url"));
		return dataSource;
	}

}