package algorithmes;

import java.util.Vector;

import Problems.Problem;

public class HillClimbing<S, A> {
	Problem<S, A> problem;

	public HillClimbing(Problem<S, A> problem) {
		super();
		this.problem = problem;
	}

	public void simple() {
		int visitedNode = 0;
		int expandedNode = 0;

		S currentState = problem.initiallState();
		S bestNeighbour = null;
		double bestNeighbourFitness = Integer.MAX_VALUE;
		while (true) {
			expandedNode++;
			// Select best neighbour
			for (A action : problem.actions(currentState)) {
				S neighbour = problem.result(currentState, action);
				visitedNode++;
				if ((!problem.maximize && problem.objective_function(neighbour) < bestNeighbourFitness)
						|| (problem.maximize && problem
								.objective_function(neighbour) > bestNeighbourFitness)) {
					bestNeighbour = neighbour;
					bestNeighbourFitness = problem
							.objective_function(bestNeighbour);
				}
			}
			if ((!problem.maximize && bestNeighbourFitness < problem
					.objective_function(currentState))
					|| (problem.maximize && bestNeighbourFitness > problem
							.objective_function(currentState)))
				currentState = bestNeighbour;
			else
				break;
		}
		print(visitedNode, expandedNode, currentState);
	}

	public void Stochastic() {
		int visitedNode = 0;
		int expandedNode = 0;

		S currentState = problem.initiallState();
		S bestNeighbour = null;
		double bestNeighbourFitness = Integer.MAX_VALUE;
		while (true) {
			Vector<S> goodNeighbours = new Vector<S>();
			expandedNode++;
			// Select neighbours that are better than current state
			for (A action : problem.actions(currentState)) {
				S neighbour = problem.result(currentState, action);
				visitedNode++;
				if ((!problem.maximize && problem.objective_function(neighbour) < problem
						.objective_function(currentState))
						|| (problem.maximize && problem
								.objective_function(neighbour) > problem
								.objective_function(currentState))) {
					goodNeighbours.addElement(neighbour);
				}
			}
			if (goodNeighbours.size() == 0)
				break;
			else {
				int random = (int) (Math.random() * goodNeighbours.size());
				currentState = goodNeighbours.elementAt(random);
			}
		}
		print(visitedNode, expandedNode, currentState);
	}

	public void first_choice() {
		int visitedNode = 0;
		int expandedNode = 0;

		S currentState = problem.initiallState();
		S bestNeighbour = null;
		double bestNeighbourFitness = Integer.MAX_VALUE;
		while (true) {
			expandedNode++;
			// Select best neighbour
			Vector<A> actions = problem.actions(currentState);
			boolean[] actionFlags = new boolean[actions.size()];// for checking
																// that do we
																// check all of
																// actions? in
																// order to find
																// that are we
																// in local
																// optimal state
																// or no
			for (int i = 0; i < actionFlags.length; i++)
				actionFlags[i] = false;
			int remainingActions = actions.size();
			while (true && remainingActions != 0) {
				int random = (int) (Math.random() * actions.size());
				S neighbour = problem.result(currentState,
						actions.elementAt(random));
				visitedNode++;
				if ((!problem.maximize && problem.objective_function(neighbour) < problem
						.objective_function(currentState))
						|| (problem.maximize && problem
								.objective_function(neighbour) > problem
								.objective_function(currentState))) {
					currentState = neighbour;
					break;
				}
				if (actionFlags[random] == false) {
					actionFlags[random] = true;
					remainingActions--;
				}
				if (remainingActions == 0)
					break;
			}
			if (problem.goal(currentState) || remainingActions == 0)
				break;
		}
		print(visitedNode, expandedNode, currentState);
	}

	public void random_restart() {
		int visitedNode = 0;
		int expandedNode = 0;

		S currentState = problem.initiallState();
		S bestNeighbour = null;
		double bestNeighbourFitness = Integer.MAX_VALUE;
		while (true) {
			expandedNode++;
			// Select best neighbour
			for (A action : problem.actions(currentState)) {
				S neighbour = problem.result(currentState, action);
				visitedNode++;
				if ((!problem.maximize && problem.objective_function(neighbour) < bestNeighbourFitness)
						|| (problem.maximize && problem
								.objective_function(neighbour) > bestNeighbourFitness)) {
					bestNeighbour = neighbour;
					bestNeighbourFitness = problem
							.objective_function(bestNeighbour);
				}
			}
			if ((!problem.maximize && bestNeighbourFitness < problem
					.objective_function(currentState))
					|| (problem.maximize && bestNeighbourFitness > problem
							.objective_function(currentState)))
				currentState = bestNeighbour;
			else {
				if (problem.goal(currentState))
					break;
				else
					currentState = problem.randomState();
			}
		}
		print(visitedNode, expandedNode, currentState);
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
