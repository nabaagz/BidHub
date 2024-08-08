package com.BidHub.ForwardBidding;

import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;



@Configuration
public class LoadDatabase {
	
	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
	@Bean
	CommandLineRunner initDatabase(CatalogueRepository catalogueRepo, ForwardBiddingRepository bidRepo) {

		return args -> {
			
			catalogueRepo.findAll().forEach(catItem -> log.info("Preloaded " + catItem));
			//adding an item
			Catalogue newCat = new Catalogue("2020 civic", "Forward", 30000, "new car", 123456, "Oct 5, 2023", 3);
			catalogueRepo.save(newCat);
			
			Catalogue newCat1 = new Catalogue("key chain", "Forward", 15, "key chain for your keys", 
					123344, "Feb1, 2024", 5);
			catalogueRepo.save(newCat1);

			//removing an item
			catalogueRepo.delete(newCat1);
			
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
