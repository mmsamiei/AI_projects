package algorithmes;

import java.util.Comparator;
import java.util.Vector;

import Problems.Problem;

public class GeneticAlgorithme<S, A> {
	Problem<S, A> problem;
	Vector<S> population = new Vector<S>();
	int numberOfPopulation;
	int numberOfGeneration;
	double chanceOfMutation;

	public GeneticAlgorithme(Problem<S, A> problem, int numberOfPopulation,
			int numberOfGeneration, double chanceOfMutation) {
		super();
		this.problem = problem;
		this.numberOfPopulation = numberOfPopulation;
		this.numberOfGeneration = numberOfGeneration;
		this.chanceOfMutation = chanceOfMutation;
	}

	public void solve() {
		
		Vector<Double> worstv=new Vector<>();
		Vector<Double> bestv=new Vector<>();
		Vector<Double> avgv=new Vector<>();
		
		makeInitialPopulation();
		int generationNumber = 0;
		boolean optimal = false;
		while (generationNumber < numberOfGeneration && !optimal) {
			for (int i = 0; i < numberOfPopulation; i++) {
				S x = select();
				S y = select();
				S child = makeChild(x, y);
				double chance = Math.random();
				if (chance < chanceOfMutation) {
					child = mutation(child);
				}
				population.addElement(child);
			}
			selectBests();
			generationNumber++;
			double sum = 0;
			S best = population.elementAt(0);
			S worst = population.elementAt(0);
			for (S s : population) {
				sum += problem.objective_function(s);
				if (problem.objective_function(s) > problem
						.objective_function(best))
					best = s;
				if (problem.objective_function(s) < problem
						.objective_function(worst))
					worst = s;
				if (problem.goal(s))
					optimal = true;
			}
			System.out
					.println("----------------------------------------------------------");
			System.out.println(generationNumber);
			double avg = sum / numberOfPopulation;
			System.out.println("Avg: " + avg);
			System.out.print("best  is:\t");
			problem.print(best);
			System.out.println("\t\twith fitness:\t "
					+ problem.objective_function(best));
			System.out.print("worst is:\t");
			problem.print(worst);
			System.out.println("\t\twith fitness:\t "
					+ problem.objective_function(worst));
			avgv.addElement(avg);
			bestv.addElement(problem.objective_function(best));
			worstv.addElement(problem.objective_function(worst));
		}
		if (optimal)
			System.out.println("we find optimal solution by "
					+ numberOfGeneration + " Generation");
		else
			System.out
					.println("\nwe couldn't  find optimal solution so we finish Algorithme in limit "
							+ numberOfGeneration);
	}

	private void makeInitialPopulation() {
		for (int i = 0; i < numberOfPopulation; i++) {
			population.addElement(problem.randomState());
		}
	}

	private S select() {
		double sum = 0;
		double[] probability = new double[numberOfPopulation];
		for (int i = 0; i < numberOfPopulation; i++) {
			sum += (problem.objective_function(population.elementAt(i)));
		}
		probability[0] = (problem.objective_function(population.elementAt(0)) / sum);
		for (int i = 1; i < numberOfPopulation; i++) {
			probability[i] = probability[i - 1]
					+(problem.objective_function(population.elementAt(i)) / sum);
		}
		double random = Math.random();
		if (random > probability[0] && random < probability[1])
			return population.elementAt(0);
		for (int i = 1; i < numberOfPopulation; i++) {
			if (random > probability[i - 1] && random < probability[i])
				return population.elementAt(i);
		}
		return population.elementAt(0);
	}

	private S makeChild(S x, S y) {
		return problem.crossOver(x, y);
	}

	private S mutation(S state) {
		return problem.mutation(state);
	}

	private void selectBests() {
		population.sort(new Comparator<S>() {

			@Override
			public int compare(S arg0, S arg1) {
				if (problem.objective_function(arg1) > problem
						.objective_function(arg0))
					return 1;
				else if (problem.objective_function(arg1) < problem
						.objective_function(arg0))
					return -1;
				else
					return 0;

			}
		});
		Vector<S> newPopulation = new Vector<>();
		for (int i = 0; i < numberOfPopulation; i++) {
			newPopulation.addElement(population.elementAt(i));
		}
		population.clear();
		for (int i = 0; i < numberOfPopulation; i++) {
			population.addElement(newPopulation.elementAt(i));
		}
	}
}
