package de.tech26.robotfactory.model;

import java.util.List;

public class OrderRequest {
	private List<String> components;

	public List<String> getComponents() {
		return components;
	}

	public void setComponents(List<String> components) {
		this.components = components;
	}

	@Override
	public String toString() {
		return "OrderRequest [components=" + components + "]";
	}
}
