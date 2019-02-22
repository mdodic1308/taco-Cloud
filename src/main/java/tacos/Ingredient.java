package tacos;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Ingredient {



	public Ingredient(String id, String name, Type type) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
	}

	private String id;
	private String name;
	private Type type;
	
	public static enum Type {
		WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
	}


}
