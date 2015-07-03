package generator.algorithms;

import generator.models.generation.GenerationInfo;
import generator.models.result.GeneratedObject;

import java.util.List;
import java.util.Random;

public abstract class Algorithm {
	private String name;

	public Algorithm(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
	protected double randomizeDouble(double min, double max) {
		return min + (max - min) * new Random().nextDouble();
	}

	public abstract List<GeneratedObject> generate(GenerationInfo info);

}
