package Problems;
import java.util.Vector;

public abstract class Problem<State,Action>{
	 public abstract State initiallState();
	 public abstract Vector<Action> actions(State state);
	 public abstract State result(State state,Action action);
	 public abstract boolean goal(State state);
	 public abstract void print(State state);
	 public abstract double objective_function(State state);
	 public abstract State randomState();
	 public abstract State crossOver(State x,State y);
	 public abstract State mutation(State x);
}
