package tacos.model;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.CreditCardNumber;

import lombok.Data;

@Data
public class Order {
	@NotBlank(message="Name is required")
	private String name;
	@NotBlank(message="Street is required")
	private String street;
	@NotBlank(message="city is required")
	private String city;
	@NotBlank(message="state is required")
	private String state;
	@NotBlank(message="zip is required")
	private String zip;
	@CreditCardNumber(message="Bank Numbers is required")
	private String ccNumber;
	@Pattern(regexp="^(0[1-9]|1[0-2])([\\\\/])([1-9][0-9])$", message="Must be formateted MM/YY")
	private String ccExpiration;
	@Digits(integer=3, fraction=0, message="invalid cvv")
	private String ccCVV;
	private long id;
	private Date placeAt;
	private List<Taco> tacos;

	public void addDesign(Taco taco){
		this.tacos.add(taco);
	}
}
