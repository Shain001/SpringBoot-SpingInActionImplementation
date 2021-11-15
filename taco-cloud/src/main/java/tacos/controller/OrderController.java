package tacos.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import tacos.model.Order;
import tacos.repo.OrderRepository;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderController {

	private OrderRepository orderRepository;

	@Autowired
	public  OrderController(OrderRepository orderRepository){
		this.orderRepository = orderRepository;
	}

	@GetMapping("/current")
	private String orderForm(Model model, HttpSession session ) {
//		Order order = (Order) session.getAttribute("order");
//		Order newOrder = new Order();
//		newOrder.setTacos(order.getTacos());
//		model.addAttribute("order", newOrder);
		return "orderForm";
	}
	
	@PostMapping
	private String processOrder(@Valid Order order, Errors errors, SessionStatus sessionStatus) {
		if (errors.hasErrors()) {
			return "orderForm";
		}
		//log.info("order submitted: " + order);
		orderRepository.save(order);
		sessionStatus.setComplete();
		return "redirect:/";
	}
	
}
