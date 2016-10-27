import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.plaf.nimbus.State;


public class BFS<S, A> {
	Problem<S, A> problem;

	public BFS(Problem<S, A> problem) {

		this.problem = problem;
		treeSearch();
	}

	public void treeSearch() {
		int observedNodes = 0;
		int expandedNodes = 0;
		int maxQueueSize = 0;
		S first = problem.initiallState();
		observedNodes++;
		Queue<Node<S>> queue = new LinkedList<Node<S>>();
		//Queue<Node<S>> queue = new Queue<Node<S>>();
		queue.add(new Node<S>(first));
		while (true) {
			if (queue.isEmpty()) {
				System.out.println("error");
				System.out.println("I can't find Soluton");
			} else {
				Node<S> current;
				current = queue.poll();
				expandedNodes++;
				for (A a : problem.actions(current.state)) {
					Node<S> newNode = new Node<S>(current, problem.result(
							current.state, a), problem.cost(current.state,
							problem.result(current.state, a), a));
					observedNodes++;
					queue.add(newNode);
					if (problem.goal(newNode.state) == true) {
						System.out.println("I have found Solution");
						System.out.println("I have seen " + observedNodes
								+ " nodes");
						System.out.println("I have expanded "
								+ expandedNodes + " nodes");
						System.out.println("the path is:\n");
						printPath(newNode);
						System.out.println("\nthis cost of path is : "
								+ newNode.cost);
						System.out.println("the Max Memory usage is "+maxQueueSize+" nodes\n");
						return;
					}
				}
			}
			if(maxQueueSize<queue.size())
				maxQueueSize=queue.size();
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
