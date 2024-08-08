package Users.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "Users")
@EnableJpaRepositories(basePackages = "Users.repository")
@EntityScan("Users.entity")
public class UserApplication {

	public static void main(String... args) {
		SpringApplication.run(UserApplication.class, args);
	}
}