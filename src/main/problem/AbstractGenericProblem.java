package main.problem;

import java.util.Map;

import main.solution.solution;

/**
 * Abstract of problem implementation template. 
 *
 *@author Antonio J. Nebro <antonio@lcc.uma.es>
 *
 *@author Akinola Ogunsemi <a.ogunsemi@rgu.ac.uk>
 *
 *@param <S>
 * 
 */
//AUG2021 - Akinola - Modified to include a parent solution for a linked optimisation.

@SuppressWarnings("serial")
public abstract class AbstractGenericProblem<S> implements Problem<S>{
	private int nVariables;
	private int nObjectives;
	private int nConstraints;
	private String name;
	private String type;
	private String filepath;
	private Object problemdata;
	protected Map<Object, Object> attributes;
	private solution<?> parentsolution;

	/* Getters */
	
	public int getnVariables() {
	  return nVariables ;
	}


	public int getnObjectives() {
	  return nObjectives ;
	}

	
	public int getnConstraints() {
	  return nConstraints ;
	}

	@Override
	public String getName() {
	  return name;
	}
	
	@Override
	public String getProblemType() {
		return type;
	}
	
	@Override
	public String getFilepath() {
		return filepath;
	}
	
	@Override
	public Object getData() {
		 return problemdata;
	}
	
	@Override
	public Map<Object, Object> attributes() {
		return attributes;
	}
	
	@Override
	public solution<?> parentsolution(){
		return parentsolution;
	}
	

	/* Setters */
	protected void setnVariables(int numberOfVariables) {
	  this.nVariables = numberOfVariables;
	}

	protected void setnObjectives(int numberOfObjectives) {
	  this.nObjectives = numberOfObjectives;
	}

	protected void setnConstraints(int numberOfConstraints) {
	  this.nConstraints = numberOfConstraints;
	}

	protected void setName(String name) {
	  this.name = name;
	}
	
	protected void setProblemType(String type) {
		this.type = type;
	}
	
	protected void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	
	protected void setData(Object problemdata) {
		this.problemdata = problemdata;
	}
	
	@Override
	public void setParentsolution(solution<?> parentsolution) {
		this.parentsolution = parentsolution;
	}
}
