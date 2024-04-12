package Example;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import main.problem.permutationProblem.Implementation.AbstractIntegerPermutationProblem;
import main.solution.solution;
import main.solution.integerSolution.IntegerSolution;
import main.solution.permutationSolution.IntegerPermutationSolution;
import main.util.distance.DistImplementation.EuclideanDistanceBetweenTwoLocations.WeightType;
import main.util.model.instance.QAPInstance;
import main.util.model.instance.location;
import main.util.model.instance.IWSPInstance.IWSPInstance;
import main.util.distance.DistImplementation.EuclideanDistanceBetweenTwoLocations;

@SuppressWarnings("serial")
public class TSP extends AbstractIntegerPermutationProblem {

	private List<Integer> nCities;
	WeightType edgeWeightType;
	EuclideanDistanceBetweenTwoLocations euc;
	//IntegerPermutationSolution updatedSolution;
	//solution<?> parentsolution;
	
	
	@SuppressWarnings("unchecked")
	public TSP (String datafilename)  throws IOException {
		setnVariables(1);
		setnObjectives(1);
		setName("TSP");
		setFilepath(datafilename);
		setData(readProblem(datafilename));
		this.nCities = new ArrayList<>();
		this.nCities.add(((List<location>) getData()).size());
		
		
	}
	
	public TSP (String datafilename, solution<?> solution)  throws IOException {
		this(datafilename);
		setParentsolution(solution);
		//this.updatedSolution = updatesolution(solution);	
	}
	
	public TSP (solution<?> solution){
		setParentsolution(solution);
		if(parentsolution().problemname().equals("VAP")) {
			IntegerPermutationSolution sol =  (IntegerPermutationSolution) parentsolution();
			
		}
	}
	
	@Override
	public int getPermutationLength(int index) {
		if(index>nCities.size()-1) {
			return (Integer) null;
		}else {
			return getnSubVariables().get(index);
		}
	}
	
	@Override
	public List<Integer> getnSubVariables(){
		return this.nCities;
	}
	
	//@Override
	//public IntegerPermutationSolution getUpdatedSolution() {
	//	return updatedSolution;
	//}

	/**
	@SuppressWarnings("unchecked")
	@Override
	public void evaluate(IntegerPermutationSolution solution) {
		IntegerPermutationSolution sol=null;
		if(parentsolution!=null) {
		
			@SuppressWarnings("rawtypes")
			ChildEvaluationFactory evaluate = 
					new ChildEvaluationFactory(parentsolution.problemname(), parentsolution.getData());
			sol = (IntegerPermutationSolution)evaluate
					.getEvaluation().childSolutionEvaluation(solution);	
		}else if(parentsolution==null){
			
			@SuppressWarnings("rawtypes")
			ChildEvaluationFactory evaluate = 
					new ChildEvaluationFactory(solution.getData());
			sol = (IntegerPermutationSolution)evaluate
					.getEvaluation().childSolutionEvaluation(solution);
		}
	}**/
	
	
	
	public List<location> readProblem(String datafilename) throws IOException {
		
		
		InputStream in = new FileInputStream(datafilename);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        
        String line;
        int count = 0;
        List<location> locList = new ArrayList();
        while ((line = br.readLine()) != null) {
        	line.trim();
        	String[] cor = line.split(" ");
        	if(cor[0].matches("\\D+"))continue;
        	
        	//if(cor.toString().matches("\\s+"))continue;
        	List<String> val = new ArrayList<>();
        	for(int i =0; i < cor.length; i++) {
        		if(cor[i].isBlank())continue;
        		if(cor[i].matches("\\D+"))continue;
        		val.add(cor[i]);
        	}
        	if(!val.isEmpty()) {
        		location loc = new location((int)Double.parseDouble(val.get(0)), (int)Double.parseDouble(val.get(1)), (int)Double.parseDouble(val.get(2)));
            	locList.add(loc);
        	}
        	
        	count++;
        }  
        return locList;
	}

	//@Override
	//public IntegerPermutationSolution createsolution() {
	//	 return new IntegerPermutationSolution(getnSubVariables(), getName());
  // }

	@SuppressWarnings("unchecked")
	@Override
	public void evaluate(IntegerPermutationSolution solution) {
		double fitness =0;
		EuclideanDistanceBetweenTwoLocations euc = 
				new EuclideanDistanceBetweenTwoLocations(WeightType.CEIL_2D);
		
		if(parentsolution()!=null) {
			
			if(parentsolution().problemname().equals("QAP")) {
				IntegerPermutationSolution sol =  (IntegerPermutationSolution) parentsolution();
				int[][] d = ((QAPInstance) sol.getObject()).getDistances();
				for(int i=0; i<solution.variables().size()-1; i++) {
					if(solution.variables().get(i)==null)continue;
					
					fitness += d[solution.variables().get(i).get(1)][solution.variables().get(i).get(0)];
					fitness += d[solution.variables().get(i).get(0)][solution.variables().get(i+1).get(1)];
					
				}
				int lastpos = solution.variables().size()-1;
				int lastcust = solution.variables().get(lastpos).get(0);
				int lastfac = solution.variables().get(lastpos).get(1);
				fitness += d[lastfac][lastcust];
			    fitness += d[solution.variables().get(0).get(1)][lastcust];
			}else if(parentsolution().problemname().equals("VAP")){
				IntegerSolution sol =  (IntegerSolution) parentsolution();
				double dist=0.0;
				for(int i = 0; i<sol.variables().size(); i++) {
					if(null==sol.variables().get(i))continue;
					Set<Integer> vehicles = (Set<Integer>) sol.variables().get(i);
					
					if(vehicles.contains(0))vehicles.remove(0);
					
					Iterator<Integer> it = vehicles.iterator();
					while(it.hasNext()) {
						for(int j = 0; j < solution.variables().get(it.next()).size()-1; j++) {
							dist += ((IWSPInstance) sol.getObject()).getCustDistance((int)solution.variables().get(it.next()).get(j), (int)solution.variables().get(it.next()).get(j+1));
						}	
						int last = solution.variables().get(it.next()).size()-1;
						dist += ((IWSPInstance) sol.getObject()).getDistance(i, (int)solution.variables().get(it.next()).get(0));
						dist += ((IWSPInstance) sol.getObject()).getDistance(i, (int)solution.variables().get(it.next()).get(last));
					}
				}
			}else {
				
				for(int i=0; i<solution.variables().size(); i++) {
					if(null==solution.variables().get(i))continue;
					if(solution.variables().get(i).isEmpty())continue;
					List<Integer> subcities = solution.variables().get(i);
					
					for (int j = 0; j < subcities.size()-1; j++) {
						//double value = 0.0;
					    int x = (int)solution.variables().get(i).get(j) ;
					    int y = (int)solution.variables().get(i).get(j+1) ;
					    fitness += euc.distance(((List<location>) getData()).get(x), ((List<location>) getData()).get(y)) ;
					  //  fitness +=value;
					   // subfitness.add(value);
					} 
					//solution.attributes().put(i, subfitness);
					int firstLocation = (int)solution.variables().get(i).get(0);
					int lastLocation = (int)solution.variables().get(i).get(subcities.size()-1);
				    fitness += euc.distance(((List<location>) getData()).get(lastLocation), ((List<location>) getData()).get(firstLocation));
				}
			}
			
			
		}else {
			for(int i=0; i<solution.variables().size(); i++) {
				if(null==solution.variables().get(i))continue;
				if(solution.variables().get(i).isEmpty())continue;
				List<Integer> subcities = solution.variables().get(i);
				
				for (int j = 0; j < subcities.size()-1; j++) {
					//double value = 0.0;
				    int x = (int)solution.variables().get(i).get(j) ;
				    int y = (int)solution.variables().get(i).get(j+1) ;
				    fitness += euc.distance(((List<location>) getData()).get(x), ((List<location>) getData()).get(y)) ;
				  //  fitness +=value;
				   // subfitness.add(value);
				} 
				//solution.attributes().put(i, subfitness);
				int firstLocation = (int)solution.variables().get(i).get(0);
				int lastLocation = (int)solution.variables().get(i).get(subcities.size()-1);
			    fitness += euc.distance(((List<location>) getData()).get(lastLocation), ((List<location>) getData()).get(firstLocation));
			}
		}
		
		//System.out.println(solution.toString());
	    solution.getFitness()[0] = fitness;
		
	}

	@Override
	public void evaluateConstraint(IntegerPermutationSolution solution) {
		// TODO Auto-generated method stub
	}

	@Override
	public IntegerPermutationSolution createsolution(Map<Integer, List<Integer>> gurobiSol) {
		// TODO Auto-generated method stub
		return null;
	}

}
