package tacos.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import lombok.extern.slf4j.Slf4j;
import tacos.Order;
import tacos.User;
import tacos.data.OrderRepository;

@Slf4j
@RepositoryRestController
@RequestMapping("/orders")
@SessionAttributes("order")
@CrossOrigin(origins="*")
public class OrderController {

	private OrderRepository orderRepo;
	
    private static final String ORDER_FORM = "orderForm";
    
    @Autowired
	public OrderController(OrderRepository orderRepo) {
		this.orderRepo = orderRepo;
	}

	@GetMapping("/current")
	public String orderForm(Order order, @AuthenticationPrincipal User user) {
        order.setDeliveryName(user.getFullname());
        order.setDeliveryStreet(user.getStreet());
        order.setDeliveryCity(user.getCity());
        order.setDeliveryState(user.getState());
        order.setDeliveryZip(user.getZip());
		return ORDER_FORM;
	}
	
	@PostMapping
	public String processOrder(@Valid Order order, Errors errors,
			SessionStatus sessionStatus, @AuthenticationPrincipal User user) {
		
		if (errors.hasErrors()) {
			return ORDER_FORM;
		}
		order.setUser(user);
		
		orderRepo.save(order);
		sessionStatus.setComplete();
		
		log.info("Order submitted: " + order);
		return "redirect:/";
	}
	
	@PutMapping("/{orderId}")
	public Order putOrder(@RequestBody Order order) {
		return orderRepo.save(order);
	}
	
	@PatchMapping(path="/{orderId}", consumes="application/json")
	public Order patchOrder(@PathVariable("orderId") Long orderId,
							@RequestBody Order patch) {
		Order order=orderRepo.findById(orderId).get();
		if (patch.getDeliveryName()!=null) {
			order.setDeliveryName(patch.getDeliveryName());
		}
		if (patch.getDeliveryStreet()!=null) {
			order.setDeliveryStreet(patch.getDeliveryStreet());
		}
		if (patch.getDeliveryCity()!=null) {
			order.setDeliveryCity(patch.getDeliveryCity());
		}
		if (patch.getDeliveryState()!=null) {
			order.setDeliveryState(patch.getDeliveryState());
		}
		if (patch.getDeliveryZip()!=null) {
			order.setDeliveryZip(patch.getDeliveryZip());
		}
		if (patch.getCcNumber()!=null) {
			order.setCcNumber(patch.getCcNumber());
		}
		if (patch.getCcExpiration()!=null) {
			order.setCcExpiration(patch.getCcExpiration());
		}
		if (patch.getCcCVV()!=null) {
			order.setCcCVV(patch.getCcCVV());
		}
		return orderRepo.save(order);	
		
	}
	
	@DeleteMapping("/{orderId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteOrder(@PathVariable("orderId") Long orderId) {
		try {
			orderRepo.deleteById(orderId);
		} catch (EmptyResultDataAccessException e) {}
	}
}
