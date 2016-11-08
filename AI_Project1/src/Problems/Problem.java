package Problems;
import java.util.Vector;

public abstract class Problem<State,Action>{
	 public abstract State initiallState();
	 public abstract Vector<Action> actions(State state);
	 public abstract State result(State state,Action action);
	 public abstract double cost(State father,State child,Action action);
	 public abstract boolean goal(State state);
	 public abstract void print(State state);
	 public abstract double heuristic(State state);
	// public abstract State goalState();
}
