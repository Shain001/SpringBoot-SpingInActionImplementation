package tacos.repo;

import tacos.model.Order;

public interface OrderRepository {

	Order save(Order order);
}
