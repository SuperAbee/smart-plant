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

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Plant plant = (Plant) o;
		return port == plant.port &&
				Objects.equals(name, plant.name) &&
				Objects.equals(ip, plant.ip);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, ip, port);
	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class State {
		private String time;
		private int humidity;
		private int temperature;
	}
}
