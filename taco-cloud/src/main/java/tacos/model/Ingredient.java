package tacos.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Ingredient {
	
	

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
