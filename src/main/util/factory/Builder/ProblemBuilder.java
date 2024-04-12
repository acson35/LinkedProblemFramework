package main.util.factory.Builder;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;


import main.problem.Problem;
import main.solution.solution;

public class ProblemBuilder<S> implements InstanceBuilder<S> {

	
	@SuppressWarnings("static-access")
	public ProblemBuilder() {
	}
	
	
	@SuppressWarnings("unchecked")
	public Problem<S> build(Problem<S> childproblem, solution<?> parentsolution) {
		
		Problem<S> newprob=childproblem;
		
		try {
			
			String problemname = childproblem.getClass().getPackageName()+"."+childproblem.getName();
			newprob = (Problem<S>)Class.forName(problemname).getConstructor(String.class, solution.class).newInstance(childproblem.getFilepath(), parentsolution);
		    } catch (InstantiationException e) {
		    	 e.printStackTrace();
		    } catch (IllegalAccessException e) {
		    	 e.printStackTrace();
		    } catch (InvocationTargetException e) {
		    	 e.printStackTrace();
		    } catch (NoSuchMethodException e) {
		    	  e.printStackTrace();
		    } catch (ClassNotFoundException e) {
		    	 e.printStackTrace();
		    }
		return newprob;
    }
	

	
}
