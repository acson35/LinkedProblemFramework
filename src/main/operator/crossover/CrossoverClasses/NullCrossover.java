package main.operator.crossover.CrossoverClasses;

import java.util.ArrayList;
import java.util.List;

import main.operator.crossover.CrossoverOperator;
import main.solution.solution;

@SuppressWarnings("serial")
public class NullCrossover<S extends solution<?>>
	implements CrossoverOperator<S> {


	@SuppressWarnings("unchecked")
	@Override public List<S> execute(List<S> solution) {
		
		if(null==solution) {
			throw new NullPointerException();
		}
		if(solution.size() != 2) {
			throw new ArithmeticException("There must be two parents instead of " + solution.size());
		}
		List<S> list = new ArrayList<>() ;
		list.add((S) solution.get(0).copy()) ;
		list.add((S) solution.get(1).copy()) ;

		return list ;
	}

	public int getNumberOfRequiredParents() {
		return 2 ;
	}

	@Override
	public int getNumberOfGeneratedChildren() {
		return 2;
	}

	@Override
	public double getCrossoverProbability() {
		return 1.0;
	}
}
