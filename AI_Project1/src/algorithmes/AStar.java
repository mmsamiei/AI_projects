package algorithmes;

import java.util.Comparator;
import java.util.PriorityQueue;

import algorithmes.UniformedCostSearch.Node;
import Problems.Problem;

public class AStar<S, A> {
	class Node<S> {
		Node<S> parent;
		S state;
		double cost;

		public Node(Node<S> parent, S state, double costFromParent) {
			this.parent = parent;
			this.state = state;
			this.cost = parent.cost + costFromParent;
		}

		public Node(S state) {
			this.state = state;
			this.cost = 0;
		}

	}

	Problem<S, A> problem;

	public AStar(Problem<S, A> problem) {
		this.problem = problem;
	}

	public void treeSearch() {
		int observedNodes = 0;
		int expandedNodes = 0;
		int maxQueueSize = 0;
		Node<S> firstNode = new Node<S>(problem.initiallState());
		observedNodes++;
		PriorityQueue<Node<S>> queue = new PriorityQueue<>(
				new Comparator<Node<S>>() {

					@Override
					public int compare(AStar<S, A>.Node<S> arg0,
							AStar<S, A>.Node<S> arg1) {
						if (arg0.cost + problem.heuristic(arg0.state) > arg1.cost
								+ problem.heuristic(arg1.state))
							return 1;
						else if (arg0.cost + problem.heuristic(arg0.state) < arg1.cost
								+ problem.heuristic(arg1.state))
							return -1;
						else
							return 0;
					}
				});
		queue.add(firstNode);
		while (true) {
			if (queue.isEmpty()) {
				System.out.println("Error");
				System.out.println("couldn't find solution");
				return;
			} else {
				Node<S> node = queue.poll();
				if(problem.goal(node.state)){
					System.out.println("find it");
					System.out.println("I have found Solution");
					System.out.println("I have seen " + observedNodes
							+ " nodes");
					System.out.println("I have expanded " + expandedNodes
							+ " nodes");
					System.out.println("the path is:\n");
					printPath(node);
					System.out.println("\nthis cost of path is : " + node.cost);
					System.out.println("the Max Memory usage is "
							+ maxQueueSize + " nodes\n");
					System.out
							.println("\t***********************************************");
					return;
				}
				expandedNodes++;
				for(A a:problem.actions(node.state)){
					Node<S> child = new Node<S>(node, problem.result(
							node.state, a), problem.cost(node.state,
							problem.result(node.state, a), a));
					queue.add(child);
					observedNodes++;
				}
				if (maxQueueSize < queue.size())
					maxQueueSize = queue.size();
			}
		}
	}
	public void printPath(Node<S> node) {
		if (node.parent == null) {
			problem.print(node.state);
		} else {
			printPath(node.parent);
			problem.print(node.state);
		}
	}
}
