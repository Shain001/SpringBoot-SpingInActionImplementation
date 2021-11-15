package tacos.model;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class Taco {
	@NotNull
	@Size(min=2, message="Name must be at least 2 charactors")
	private String name;
	@NotEmpty(message="at least 1 ingredient")
	//@Size(min=1, message="You must choose at least 1 ingredient")
	private List<Ingredient> ingredients;
	
	private long id;
	
	private Date createAt;
}
