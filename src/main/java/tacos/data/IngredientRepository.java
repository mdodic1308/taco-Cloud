package tacos.data;

import org.springframework.data.repository.CrudRepository;

import tacos.Ingredient;

//@Sql("classpath:resources/templates/data.sql")
public interface IngredientRepository extends CrudRepository<Ingredient, String> {
    
}
