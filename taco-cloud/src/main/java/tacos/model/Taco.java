package tacos.model;

import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
public class Taco {
	@NotNull
	@Size(min=2, message="Name must be at least 2 charactors")
	private String name;

	@NotEmpty(message="at least 1 ingredient")
	@ManyToMany(targetEntity = Ingredient.class)
	//@Size(min=1, message="You must choose at least 1 ingredient")
	private List<Ingredient> ingredients;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private Date createdAt;

	@PrePersist
	void createAt(){
		this.createdAt = new Date();
	}
}
