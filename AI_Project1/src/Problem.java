import java.util.Vector;

public abstract class Problem<State,Action>{
	 abstract State initiallState();
	 abstract Vector<Action> actions(State state);
	 abstract State result(State state,Action action);
	 abstract double cost(State father,State child,Action action);
	 abstract boolean goal(State state);
	 abstract void print(State state);
}
