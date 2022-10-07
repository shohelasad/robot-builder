package de.tech26.robotfactory.repo;

import de.tech26.robotfactory.model.Component;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;

@Repository
public class StockRepo {
	private static final Log logger = LogFactory.getLog(StockRepo.class);

	private Map<String, Component> stocks = new HashMap<>();

	@PostConstruct
	private void init() throws IOException {
		List<Component> components = new ArrayList<>();
		Component component = new Component("A", 10.28, 9,  "Humanoid Face");
		components.add(component);
		component = new Component("B", 24.07, 7,  "LCD Face");
		components.add(component);
		component = new Component("C", 13.30, 0,  " Steampunk Face");
		components.add(component);
		component = new Component("D", 28.94, 1,  "Arms with Hands");
		components.add(component);
		component = new Component("E", 12.39, 3,  "Arms with Grippers");
		components.add(component);
		component = new Component("F", 30.77, 2,  "Mobility with Wheels");
		components.add(component);
		component = new Component("G", 55.13, 15,  "Mobility with Legs");
		components.add(component);
		component = new Component("H", 50.00, 7,  "Mobility with Tracks");
		components.add(component);
		component = new Component("I", 90.12, 92,  "Material Bioplastic");
		components.add(component);
		component = new Component("J", 82.31, 15,  "Material Metallic");
		components.add(component);

		for(Component c:components) {
			stocks.put(c.getCode(), c);
		}
	}

	public Optional<Component> getComponentByCode(String code) {
		return Optional.of(stocks.get(code));
	}

	public Component updateStocks(String code, Component component) {
		return stocks.put(code,component);
	}

}
