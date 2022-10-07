package de.tech26.robotfactory.service;

import de.tech26.robotfactory.component.ComponentInterface;
import de.tech26.robotfactory.component.ComponentType;
import de.tech26.robotfactory.exception.UnProcessableException;
import de.tech26.robotfactory.exception.ValidationException;
import de.tech26.robotfactory.model.Component;
import de.tech26.robotfactory.model.OrderRequest;
import de.tech26.robotfactory.model.OrderResponse;
import de.tech26.robotfactory.model.RobotBuilder;
import de.tech26.robotfactory.repo.StockRepo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StockService {
	private static final Log logger = LogFactory.getLog(StockService.class);

	@Autowired
	StockRepo stockRepo;

	@Autowired
	RobotBuilder robotBuilder;

	public static Set<String> faceComponent = ComponentInterface.faceComponent;
	public static Set<String> materialComponent = ComponentInterface.materialComponent;
	public static Set<String> armsComponent = ComponentInterface.armsComponent;
	public static Set<String> mobilityComponent = ComponentInterface.mobilityComponent;

	public OrderResponse processOrder(List<String> orders) {
		robotBuilder.initialize(); // For each order list should be refreshed

		// Check order request contains all required configurations
		if (orders.size() != ComponentType.values().length)
			throw new ValidationException("Bad Request! Please check configurations");

		// An order will be valid if it contains one, and only one, part of face,
		// material, arms and mobility.
		boolean isValidRequest = validateOrderRequest(orders);
		if (!isValidRequest)
			throw new UnProcessableException("UnProcessable Configuration! Please try with different configurations");

		// Check component availability in stock
		boolean isAllComponentsAvailable = checkComponentAvailability(orders);
		if (!isAllComponentsAvailable)
			throw new UnProcessableException(
					"UnProcessable Configuration! Stock Shortage, Please try with different configurations");

		// apply components and get order price
		Double price = applyCopmonents(orders);

		// prepare response object
		OrderResponse order = new OrderResponse();
		UUID uuid = UUID.randomUUID();
		order.setOrder_id(uuid.toString());
		order.setTotal(price);

		return order;
	}

	private Double applyCopmonents(List<String> components) {
		components.stream().forEach(code -> {
			robotBuilder.apply(stockRepo.getComponentByCode(code).get());
			updateComponentAvailability(code);
		});

		return robotBuilder.totalPrice();
	}

	private boolean validateOrderRequest(List<String> orders) {
		for(String order:orders) {
			String name = stockRepo.getComponentByCode(order).get().getName();
			List<String> parts = Arrays.asList(name.split("\\s"));
			boolean isValidFace = parts.stream().anyMatch(part -> faceComponent.contains(part));
			boolean isValidArm = parts.stream().anyMatch(part -> armsComponent.contains(part));
			boolean isValidMobility = parts.stream().anyMatch(part -> mobilityComponent.contains(part));
			boolean isValidMaterial = parts.stream().anyMatch(part -> materialComponent.contains(part));
			logger.info("isValidFace =" + isValidFace + "isValidArm =" + isValidArm + "isValidMobility =" + isValidMobility
					+ "isValidMaterial =" + isValidMaterial);
			if(isValidFace || isValidArm || isValidMobility || isValidMaterial) {
				continue;
			} else {
				return true;
			}
		}

		return true;
	}

	private boolean checkComponentAvailability(List<String> orders) {

		boolean allComponentsAvailable = orders.stream()
				.allMatch(code -> stockRepo.getComponentByCode(code).get().getAvailable() > 0);

		return allComponentsAvailable;
	}

	private void updateComponentAvailability(String code) {
		Optional<Component> part = stockRepo.getComponentByCode(code);
		if (part.isPresent() && part.get().getAvailable() > 0) {
			part.get().setAvailable(part.get().getAvailable() - 1);
			stockRepo.updateStocks(code, part.get());
		}
	}

}
