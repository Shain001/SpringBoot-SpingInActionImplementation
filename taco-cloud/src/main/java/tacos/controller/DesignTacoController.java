package tacos.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.ListModel;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import lombok.extern.slf4j.Slf4j;
import tacos.model.Ingredient;
import tacos.model.Taco;
import tacos.model.Ingredient.Type;
import tacos.model.Order;
import tacos.repo.IngredientRepository;
import tacos.repo.TacoRepository;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {
	
	/*
	Notes:
	1. About @ModelAttribute:
		!! Can be annotated above a method or on a function parameter
	 	!! Only support @RequestMapping annotated controller
	 	!! Function:
	 	when annotated above a method:
	 		"ADD" an attribute to the model. Emphasis on Adding;
	 		Also, mention that @ModelAttribute will be called BEFORE the calling of RequestHandler. e.g. before the calling of
	 		processDesign which is annotated by @PostMapping, the @ModeleAttribute annotated method will be called first, which means
	 		the attribute will be added to the model first.
	 		For example, if one method is aiming to redirect to another page, but before that it need to print out an attribtue of the model.
	 		In this scenario, instead of adding the attribute in this method, we can return the value in the @ModleAttribtue method: like return new Taco("tacoName").
	 		Then we can print taco.name ("tacoName" will show) to the screen
	 	when annotated on a function parameter:
	 		See it as @AutoWired + @Qualifier|@Bean. i.e. "Fetch" the attribute from the model, if it is not found, use the default constructor to create an instance and add it to model.
	 		This is normally used when the Web Page has a form, the form will be packaged to the model to return to the back end controller method, in that method, we can use the returned
	 		attribtue in the model to get the data input from the user.

	 2. About @SessionAttributes:
	 	E.g. @SessionAttributes(values={"name", "age"}, types={String.class})
	 	if we annotated like this, then: 1) the attribute that called name and age will be saved into the Session, which means it can share data accorss pages as long as the session not end;
	 	2) all the attribute whose value type is String will be saved to the session as well. For example, model.addAttribute(name = "notNameNethierAge" value="stringType"), this attribute will
	 	also be saved to the session, although the attribute's name "notNameNethierAge" is not in values={"name", "age"}

	 3. When use them together:
		 Take example of this class's code:
		 (1)look at method "public Taco taco()" which is annotated by @ModelAttribute("taco"). The attribute "taco" will not be added to the session, since "taco" is not specified in @SessionAttributes.
			However, the attribute "order" will be added to session, since it is in the values of @SessionAttribtues.
		 (2)look at method "private String processDesign(@Valid Taco design, Errors errors, @ModelAttribute Order order)";
			this method is accepting a form object form the front end, and at the same time, this attribute "order" will be saved into the session.
			Here the @ModelAttribute is not decalared as @ModelAttribute("order"), because if you do that there will be error. But see it same thing.

	 4. About @Valid:
		 This is to add validation to the front page.
		 In the model class, we have some validation annotations added such as @NotNull. To make it work, we need to use @Valid annotation in the controller
		 method to make the system check the validation.
		 Then, if there is an error, the error will be recorded in the "Error errors" instance, further, this "errors" instance will be passed to the front page, we can use
		 th:if="${#fields.hasErrors('name')}"
		 th:errors="*{name}">CC Num Error</span>
		 to show the message to user. the "name" is the model class's field.

	 */


	private final IngredientRepository ingredientRepo;
	private TacoRepository designRepository;
	
	@Autowired
	public DesignTacoController(IngredientRepository ingredientRepository, TacoRepository tacoRepository) {
		this.ingredientRepo = ingredientRepository;
		this.designRepository = tacoRepository;
	}


	@ModelAttribute(name="taco")
	public Taco taco() {
		return new Taco();
	}
	
	@ModelAttribute(name="order")
	public Order order() {
		return new Order();
	}
	
	@GetMapping
	public String showDesignForm(Model model) {
		
		List<Ingredient> ingredients = new ArrayList<>();
		
		ingredientRepo.findAll().forEach(i -> ingredients.add(i));
		
		Type[] types = Ingredient.Type.values();
		
		for (Type t: types) {
			model.addAttribute(t.toString().toLowerCase(), filterByType(ingredients, t));
		}
		
		//model.addAttribute("design", new Taco());
		return "design";
		
	}
	
	@PostMapping
	private String processDesign(@Valid Taco design, Errors errors, @ModelAttribute Order order) {
		if (errors.hasErrors()) {
			return "design";
		}
		Taco saved = designRepository.save(design);
		order.addDesign(saved);
		// log.info("Process Design: " + design);
		return "redirect:/orders/current";
	}
	
	private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {

	    return ingredients.stream()
	            .filter(x -> x.getType().equals(type))
	            .collect(Collectors.toList());

	}

}
