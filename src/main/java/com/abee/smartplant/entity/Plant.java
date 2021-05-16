package com.abee.smartplant.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Plant {

	private String name;
	private String ip;
	private int port;

	public Plant(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Plant plant = (Plant) o;
		return Objects.equals(name, plant.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class State {
		private String time;
		private float humidity;
		private float temperature;
		private int soilHumidity;
		private int waterDepth;
	}
}
