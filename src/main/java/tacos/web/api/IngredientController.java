package tacos.web.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import tacos.Ingredient;
import tacos.Taco;
import tacos.data.IngredientRepository;

@RepositoryRestController
@RequestMapping(path="/ingredients", produces="application/json")
//@CrossOrigin(origins="*")
public class IngredientController {
	
	private IngredientRepository ingredientRepo;
	
	@Autowired
	public IngredientController(IngredientRepository repo) {
		this.ingredientRepo=repo;
	}
	
	@GetMapping
	public Resources<IngredientResource> allIngredients(){
		List<Ingredient> ingredients=(List<Ingredient>) ingredientRepo.findAll();
		
		List<IngredientResource> ingredientResourcesList=
				new IngredientResourceAssembler().toResources(ingredients);
		
		Resources<IngredientResource> ingredientResource=
				new Resources<IngredientResource>(ingredientResourcesList);
		
		ingredientResource.add(
				ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(IngredientController.class).allIngredients())
					.withRel("ingredients"));
		return ingredientResource;
	}
}
