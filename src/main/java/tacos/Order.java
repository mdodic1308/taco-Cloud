package tacos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.Digits;

import org.hibernate.validator.constraints.CreditCardNumber;

import lombok.Data;

@Data
@Entity
@Table(name="Taco_Order")
public class Order implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@NotBlank(message="Name is requred")
	private String deliveryName;
	
	@NotBlank(message="Street is requred")
	private String deliveryStreet;
	
	@NotBlank(message="City is requred")
	private String deliveryCity;
	
	@NotBlank(message="State is requred")
	private String deliveryState;
	
	@NotBlank(message="Zip is requred")
	private String deliveryZip;
	
	@CreditCardNumber(message="Not a valid credit card number")
	private String ccNumber;
	
	@Pattern(regexp="^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$",
			message="Must be formated MM/YY")
	private String ccExpiration;
	
	@Digits(integer=3, fraction=0, message="Invalid CVV")
	private String ccCVV;
	
	private Date placedAt;
	
	@PrePersist
	void placedAt() {
		this.placedAt=new Date();
	}
	@ManyToMany(targetEntity=Taco.class)
	private List<Taco> tacos;
	
	@ManyToOne
	private User user;

    public void addDesign(Taco taco) {
        if (tacos == null) {
            tacos = new ArrayList<>();
        }
        tacos.add(taco);
    }

}
