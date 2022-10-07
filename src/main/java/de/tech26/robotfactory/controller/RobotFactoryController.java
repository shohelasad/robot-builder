package de.tech26.robotfactory.controller;

import de.tech26.robotfactory.exception.ValidationException;
import de.tech26.robotfactory.model.OrderRequest;
import de.tech26.robotfactory.model.OrderResponse;
import de.tech26.robotfactory.service.StockService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class RobotFactoryController {
	private static final Log logger = LogFactory.getLog(RobotFactoryController.class);

	@Autowired
	StockService stockService;

	@PostMapping(value = { "/orders" }, consumes = "application/json")
	public ResponseEntity<OrderResponse> orders(@RequestBody OrderRequest orderRequest) {
		logger.info("Processing with order request =" + orderRequest);
		OrderResponse orderResponse = new OrderResponse();
		try {
			List<String> orders = orderRequest.getComponents();//Arrays.asList("I","A","D","F");
			orderResponse = stockService.processOrder(orders);
		} catch (ValidationException exception) {
			throw exception;
		}
		return new ResponseEntity<OrderResponse>(orderResponse, HttpStatus.CREATED);
	}

}
