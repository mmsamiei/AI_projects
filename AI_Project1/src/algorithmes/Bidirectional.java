package algorithmes;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.Vector;

import Problems.Problem;


public class Bidirectional<S, A> {
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

	public Bidirectional(Problem<S, A> problem) {
		super();
		this.problem = problem;
	}
	
	public void graphsearch(){
		Queue<Node<S>> frontQueue = new LinkedList<Node<S>>();
		Queue<Node<S>> backQueue = new LinkedList<Node<S>>();
		Vector<Node<S>> explored = new Vector<Node<S>>();
		frontQueue.add(new Node(problem.initiallState()));
		backQueue.add(new Node(problem.goalState()));
		
		
	}
}
