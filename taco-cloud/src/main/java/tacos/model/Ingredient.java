package tacos.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Data
@RequiredArgsConstructor
//@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Entity
public class Ingredient {
	/*
	About @Enumerated((EnumType.STRING):
		对于java中的Enum类型， hibernate只有两种方式映射： 即映射为int / string;
		int即根据顺序编号， 如该类Type中， WRAP则为0号；
		string 即映射为本名 e.g. WRAP
		而hibernate 默认使用映射为int的方式, 因此此处需指明@Enumerated(EnumType.STRING)
	 */
	@Id
	private final String id;
	private final String name;
	@Enumerated(EnumType.STRING)
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
