package tacos.repo;

import org.springframework.data.repository.CrudRepository;
import tacos.model.Taco;

public interface TacoRepository extends CrudRepository<Taco, Long> {
	
	Taco save(Taco design);
}
