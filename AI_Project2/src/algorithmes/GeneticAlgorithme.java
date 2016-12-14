package algorithmes;

import java.util.Vector;

import Problems.Problem;

public class GeneticAlgorithme<S,A> {
	Problem<S, A> problem;
	Vector<S> population = new Vector<S>();
	int numberOfPopulation;
	int numberOfGeneration;
	public void solve(){
		makeInitialPopulation();
		int generationNumber=0;
		while(generationNumber<numberOfGeneration){
			
			for(int i=0;i<numberOfPopulation;i++){
			S x = select(); 
			S y = select();
			S child = makeChild(x,y);
			mutation(child);
			population.addElement(child);
			}
			selectBests();
			generationNumber++;
		}
	}
	
	private void makeInitialPopulation() {
		for(int i=0;i<numberOfPopulation;i++){
			population.addElement(problem.randomState());
		}
	}
	private S select() {

	}
	private S makeChild(S x, S y) {
		
	}
	private void mutation(S child) {
		
	}
	private void selectBests() {
		
	}
}
