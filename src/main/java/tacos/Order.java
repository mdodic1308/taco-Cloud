package tacos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Digits;

import org.hibernate.validator.constraints.CreditCardNumber;

import lombok.Data;

@Data
public class Order {

	@NotBlank(message="Name is requred")
	private String name;
	
	@NotBlank(message="Street is requred")
	private String street;
	
	@NotBlank(message="City is requred")
	private String city;
	
	@NotBlank(message="State is requred")
	private String state;
	
	@NotBlank(message="Zip is requred")
	private String zip;
	
	@CreditCardNumber(message="Not a valid credit card number")
	private String ccNumber;
	
	@Pattern(regexp="^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$",
			message="Must be formated MM/YY")
	private String ccExpiration;
	
	@Digits(integer=3, fraction=0, message="Invalid CVV")
	private String ccCVV;
	
	private Long id;
	
	private Date placedAt;
	
	private List<Taco> tacos;
}
