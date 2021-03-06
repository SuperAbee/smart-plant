package com.abee.smartplant.container;

import com.abee.smartplant.entity.Plant;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PlantContainer {

	private static Map<Plant, List<Plant.State>> map = new ConcurrentHashMap<>();

	public static void put(Plant plant) {
		put(plant, null);
	}

	public static void put(Plant plant, Plant.State state) {
		if (!map.containsKey(plant)) {
			map.put(plant, new LinkedList<>());
		}
		if (state != null) {
			List<Plant.State> states = map.get(plant);
			states.add(state);
		}
	}

	public static List<Plant.State> get(Plant plant) {
		return map.get(plant);
	}
}
