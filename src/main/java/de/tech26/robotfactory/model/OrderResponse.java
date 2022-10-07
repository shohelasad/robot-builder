package de.tech26.robotfactory.model;

public class OrderResponse {
	private String order_id;
	private Double total;

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "OrderResponse [order_id=" + order_id + ", total=" + total + "]";
	}

}
