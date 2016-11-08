package algorithmes;

import java.util.AbstractQueue;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Vector;

import javax.swing.plaf.nimbus.State;

import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

import Problems.Problem;
import algorithmes.BFS.Node;

public class UniformedCostSearch<S, A> {
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

	public UniformedCostSearch(Problem<S, A> problem) {
		super();
		this.problem = problem;
	}

	public void graphSearch() {
		int observedNodes = 0;
		int expandedNodes = 0;
		int maxQueueSize = 0;
		Node firstNode = new Node<S>(problem.initiallState());
		observedNodes++;
		PriorityQueue<Node<S>> frontier = new PriorityQueue<>(
				new Comparator<Node<S>>() {

					@Override
					public int compare(UniformedCostSearch<S, A>.Node<S> arg0,
							UniformedCostSearch<S, A>.Node<S> arg1) {
						if (arg0.cost > arg1.cost)
							return 1;
						if (arg0.cost < arg1.cost)
							return -1;
						else
							return 0;
					}
				});
		frontier.add(firstNode);
		Vector<S> explored = new Vector<S>();
		while (true) {
			if (frontier.isEmpty()) {
				System.out.println("error");
				System.out.println("I can't find Soluton");
				return;
			} else {
				Node<S> node = frontier.poll();
				if (problem.goal(node.state)) {
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
				explored.addElement(node.state);
				expandedNodes++;
				for (A a : problem.actions(node.state)) {
					Node<S> child = new Node<S>(node, problem.result(
							node.state, a), problem.cost(node.state,
							problem.result(node.state, a), a));
					if (!explored.contains(child.state)
							&& !isInFrontier(frontier, child.state)) {
						frontier.add(child);
						observedNodes++;
					} else {
						for (Node<S> n : frontier) {
							if (n.state.equals(child.state)) {
								if (n.cost > child.cost)
									n = child;
							}
						}
					}
				}
				if (maxQueueSize < frontier.size())
					maxQueueSize = frontier.size();
			}
		}
	}

	public boolean isInFrontier(AbstractQueue<Node<S>> queue, S s) {
		LinkedList<Node<S>> linkedList = new LinkedList<Node<S>>(queue);
		for (Node<S> node : linkedList) {
			if (node.state.equals(s)) {
				return true;
			}
		}
		return false;
	}

	public Node<S> stateInFrontier(AbstractQueue<Node<S>> queue, S s) {
		LinkedList<Node<S>> linkedList = new LinkedList<Node<S>>(queue);
		for (Node<S> node : linkedList) {
			if (node.state.equals(s)) {
				return node;
			}
		}
		return null;
	}

	public void updateFrontier(AbstractQueue<Node<S>> queue, Node<S> replace) {
		LinkedList<Node<S>> linkedList = new LinkedList<Node<S>>(queue);
		for (Node<S> node : linkedList) {
			if (node.state.equals(replace.state)) {
				node.state = replace.state;
				node.parent = replace.parent;
				node.cost = replace.cost;
				return;
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
