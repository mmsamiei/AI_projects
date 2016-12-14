package algorithmes;

import java.util.Vector;

import Problems.Problem;

public class SimulatedAnnealing<S, A> {
	Problem<S, A> problem;

	public SimulatedAnnealing(Problem<S, A> problem) {
		super();
		this.problem = problem;
	}

	public void search(int scheduleMethod) {
		int visitedNodes = 0;
		int expandedNodes = 0;
		S currentState = problem.initiallState();
		for (int t = 1; t <= 200; t++) {
			double T = schedule(t, scheduleMethod);
			if (T == 0) {
				break;
			}
			if (problem.goal(currentState)) {
				break;
			}
			S nextState;
			Vector<A> actions = problem.actions(currentState);
			expandedNodes++;
			int random = (int) (Math.random() * actions.size());
			if (actions.size() == 0)
				break;
			nextState = problem.result(currentState, actions.elementAt(random));
			double deltaE = problem.objective_function(nextState)
					- problem.objective_function(currentState);
			if(!problem.maximize)deltaE*=-1;
			visitedNodes++;
			if (deltaE > 0)
				currentState = nextState;
			else {
				double possibility = Math.random();
				if (possibility < Math.pow((Math.E), (deltaE / T)))
					currentState = nextState;
			}
		}
		print(visitedNodes, expandedNodes, currentState);
	}

	public double schedule(int t, int method) {
		Double T;
		switch (method) {
		case 0:
			T = (double) (100 / (double) t);
			return T;
		case 1:
			T = (double) 1;
			return T;
		case 2:
			T = (double) Integer.MAX_VALUE;
			return T;
		}
		return 200 / t;
	}

	public void print(int visitedNodes, int expandedNodes, S state) {
		System.out.println("Number of Visited Nodes:\t" + visitedNodes);
		System.out.println("Number of expanded Nodes:\t" + expandedNodes);
		System.out.println("Solution:\n");
		problem.print(state);
		System.out.println("solution fitness is :\t"
				+ problem.objective_function(state));
	}

}
