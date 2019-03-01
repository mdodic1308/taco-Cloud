package tacos.web.api;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import tacos.Taco;
import tacos.data.TacoRepository;

@RepositoryRestController
public class DesignTacoController {

    private TacoRepository tacoRepo;
    
    public DesignTacoController(TacoRepository tacoRepo) {
    	this.tacoRepo=tacoRepo;
    }
    
    @GetMapping(path="tacos/recents", produces="application/hal+json")
    public ResponseEntity<List<Taco>> recentTacoes() {
        	PageRequest page = PageRequest.of(
        			0, 12, Sort.by("createdAt").descending());
        	List<Taco> tacos=tacoRepo.findAll(page).getContent();
        	
        	List<TacoResource> tacoResources = 
        			new TacoResourceAssembler().toResources(tacos);	
        	Resources<TacoResource> recentResources = 
        			new Resources<TacoResource>(tacoResources);
        	recentResources.add(
        			ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(RecentTacosController.class).recentTacos())
        			.withRel("recents"));
        	return new ResponseEntity<>(tacos, HttpStatus.OK);
    }
}
