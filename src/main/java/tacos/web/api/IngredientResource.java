package tacos.web.api;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import lombok.Getter;
import tacos.Ingredient;
import tacos.Ingredient.Type;

@Relation(value="ingredient", collectionRelation="ingredients")
public class IngredientResource extends ResourceSupport {

	@Getter
	private String name;
	
	@Getter
	private Type type;
	
	public IngredientResource(Ingredient ingredient) {
		this.name=ingredient.getName();
		this.type=ingredient.getType();
	}
}
