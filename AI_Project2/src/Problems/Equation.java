package Problems;

import java.util.Random;
import java.util.Vector;

import algorithmes.GeneticAlgorithme;

public class Equation extends Problem<Double, Double> {

	@Override
	public Double initiallState() {
		return (double) 1;
	}

	@Override
	public Vector<Double> actions(Double state) {
		return null;
	}

	@Override
	public Double result(Double state, Double action) {
		return action;
	}

	@Override
	public boolean goal(Double state) {
		Double result = state*state - state - Math.sin(state);
		if(state==0)return true;
		return false;
	}

	@Override
	public void print(Double state) {
		System.out.print(state);
	}

	@Override
	public double objective_function(Double state) {
		Double result = state*state - state - Math.sin(state);
		result = Math.abs(result);
		result = 1/result;
		return result;
	}

	@Override
	public Double randomState() {
		Double rand = Math.random()*2.94;
		rand += 0.2;
		return rand;
	}

	@Override
	public Double crossOver(Double x, Double y) {
		return (x+y)/2;
	}

	@Override
	public Double mutation(Double x) {
		// Gaussian Mutation with standard derivation 0.1 and Avg 0
		Random random = new Random();
		double temp = x + random.nextGaussian() * 0.1;
		if(temp > 3.14)
			return 3.14;
		if(temp < 0.2)
			return 0.2;
		return temp;
	}
	public static void main(String[] args) {
		Equation equation = new Equation();
		GeneticAlgorithme<Double, Double> geneticAlgorithme = new GeneticAlgorithme<>(equation, 1000, 100, 0.05);
		geneticAlgorithme.solve();
		
	}
	
}
