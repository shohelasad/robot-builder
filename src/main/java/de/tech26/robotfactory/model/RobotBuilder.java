package de.tech26.robotfactory.model;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;


@org.springframework.stereotype.Component
public class RobotBuilder {
	private List<Component> components = null;
	private Double price;

	@PostConstruct
	public void initialize() {
		components = new ArrayList<>();
	}

	public void apply(Component componet) {
		components.add(componet);
	}

	public Double totalPrice() {
		price = components.stream().mapToDouble(component -> component.getPrice()).sum();
		return price;
	}

}
