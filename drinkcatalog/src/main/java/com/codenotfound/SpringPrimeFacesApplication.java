package com.codenotfound;

import com.codenotfound.drink.Drink;
import com.codenotfound.drink.DrinkRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringPrimeFacesApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringPrimeFacesApplication.class, args);
  }

  @Bean
  CommandLineRunner initDatabase(DrinkRepository repository) {
    return args -> {
      repository.save(new Drink("Mojito", "Muddle mint leaves with sugar and lime juice. Add a splash of soda water and fill the glass with cracked ice. Pour the rum and top with soda water. Garnish and serve with straw.", "Alcoholic"));
      repository.save(new Drink("Long Island Tea","Combine all ingredients (except cola) and pour over ice in a highball glass. Add the splash of cola for color. Decorate with a slice of lemon and serve." ,"Alcoholic"));
      repository.save(new Drink("Negroni","Stir into glass over ice, garnish and serve.","Alcoholic"));
      repository.save(new Drink("Whiskey Sour","Shake with ice. Strain into chilled glass, garnish and serve. If served 'On the rocks', strain ingredients into old-fashioned glass filled with ice.","Alcoholis"));
    };
  }
}
