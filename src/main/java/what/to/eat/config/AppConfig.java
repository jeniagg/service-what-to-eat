package what.to.eat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import what.to.eat.repositories.RecipeRepository;
import what.to.eat.services.RecipeService;

@Configuration
public class AppConfig {

    @Bean(name = "recipeService")
    public RecipeService recipeService() {
        return new RecipeService();
    }

}
