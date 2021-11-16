package tacos.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@RequiredArgsConstructor
//@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Entity
public class Ingredient {
	
	@Id
	private final String id;
	private final String name;
	private final Type type;
	
	public Ingredient() {
		this.id = "";
		this.name = "";
		this.type = null;
		// TODO Auto-generated constructor stub
	}
	
	public static enum Type{
		WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
	}

}
