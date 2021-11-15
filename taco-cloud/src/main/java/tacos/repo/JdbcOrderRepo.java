package tacos.repo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import tacos.model.Order;
import tacos.model.Taco;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcOrderRepo implements OrderRepository {

    private SimpleJdbcInsert orderInserter;
    private SimpleJdbcInsert tacoOrderInserter;
    private ObjectMapper objectMapper;

    @Autowired
    public JdbcOrderRepo(JdbcTemplate jdbcTemplate){
        this.orderInserter = new SimpleJdbcInsert(jdbcTemplate).
                withTableName("Taco_Order").
                usingGeneratedKeyColumns("id");

        this.tacoOrderInserter = new SimpleJdbcInsert(jdbcTemplate).
                withTableName("Taco_Order_Tacos");

        this.objectMapper = new ObjectMapper();
    }


    @Override
    public Order save(Order order) {
        order.setPlaceAt(new Date());
        long orderId = saveOrder(order);
        order.setId(orderId);
        List<Taco> tacos = order.getTacos();

        for (Taco t : tacos){
            saveTaco(orderId, t);
        }
        return order;
    }

    private void saveTaco(long orderId, Taco t) {
        Map<String, Object> values = new HashMap<>();
        values.put("tacoOrder", orderId);
        values.put("taco", t.getId());
        tacoOrderInserter.execute(values);
    }

    private long saveOrder(Order order) {
        Map<String, Object> values = objectMapper.convertValue(order, Map.class);

        values.put("PLACEDAT", order.getPlaceAt());
        long orderId = orderInserter.executeAndReturnKey(values).longValue();
        return orderId;
    }
}
