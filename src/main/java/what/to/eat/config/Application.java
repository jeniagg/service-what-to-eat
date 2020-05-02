package what.to.eat.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"what.to.eat.controllers", "what.to.eat.services","what.to.eat.config"})
@EntityScan({"what.to.eat.entities"})
@EnableJpaRepositories({"what.to.eat.repositories"})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
