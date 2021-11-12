package tacos.repo;

import static org.hamcrest.CoreMatchers.nullValue;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultSingleSelectionModel;
import javax.swing.ListModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.thymeleaf.standard.expression.Each;

import tacos.model.Ingredient;
import tacos.model.Taco;

public class JdbcTacoRepo implements TacoRepository{
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public JdbcTacoRepo(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	// save the taco first
	// then save relation information into taco-ingredient table
	// when saving the taco, since the taco's database's id column is set as
	// "identity" type, which will auto generate the id for taco. So after saving
	// the taco, a method need to be called to get the auto-generated id
	public Taco save(Taco design) {
		
		design.setCreateAt(new Date());
		long id = saveTaco(design);
		
		List<Ingredient> ingredients = design.getIngredients();
		
		for(Ingredient i : ingredients){
			saveIngredientToTaco(id, i);
		}
		return design;
	}
	
	private long saveTaco(Taco taco) {
		
		// Here the purpose of using PreparedStatementCreator Class is to obtain the autogenerated "id" value.
		// Specifically, fianl step is still using the jdbctemplate to update the database, but when updating it, 
		// we need to pass the psc object and a keyholder instance to the jdbc, so that the id value can be returned through the keyholder.
		// to create the psc, we need to first create the psc factory. The psc factory takes sql query as the parameter, and also TYPES of each columm
		// needed to be decalered manually. Then, use the factory to create a psc instance, where the value of each column is passed
		// Finally create the keyholder instance and pass it to jdbc. Then the id value can be returned.
		
		PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreatorFactory("insert into Taco (name, createdAt values (?, ?))",
				Types.VARCHAR, Types.TIMESTAMP).newPreparedStatementCreator(
						Arrays.asList(
								taco.getName(), new Timestamp(taco.getCreateAt().getTime())));
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(preparedStatementCreator, keyHolder);
		return keyHolder.getKey().longValue();
	}
	
	private void saveIngredientToTaco(long tacoId, Ingredient ingredient) {
		jdbcTemplate.update("insert into Taco-Ingredients (taco, ingredient) values (?, ?)", tacoId, ingredient.getId());
	}

	
}