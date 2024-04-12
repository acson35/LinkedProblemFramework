package main.util.LPDriver;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.algorithm.Algorithm;
import main.problem.Problem;
import main.solution.solution;
import main.solution.binarySolution.BinarySolution;
import main.util.adjacencymatrix.AdjacencyMatrix;
import main.util.constraintHandling.ConstraintHandling;
import main.util.factory.GetProblemFactory;
import main.util.fileoutput.FileOutputContext;
import main.util.fileoutput.LinkedSolutionListOutput;
import main.util.fileoutput.Implementation.DefaultFileOutputContext;

public class ExecuteRandomProblemGeneration<S extends solution<?>> {

	
	private LinkedProblemRunner runner;
	//private Map<S, List<S>> linkedsolutions = new HashMap<>();
	private List<S> solutions;
	String filename;
	String filename2;
	String[] header;
	static ConstraintHandling con = new ConstraintHandling();
	private long initTime;
	private List<Long> totalComputingTime;
	
	public ExecuteRandomProblemGeneration(LinkedProblemRunner runner) {
		 this.runner = runner;
		 this.filename = runner.getOutputLinkedProblemFileName();
		 this.filename2 = runner.getOutputLinkedProblemFileName();
		 header = new String[1001];
		 this.totalComputingTime = new ArrayList<>();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void run(){
		//int count=0;
		String p1name = runner.getProblemList().getLinkedProblems().get(1).getClass().getName();
		String p2name = runner.getProblemList().getLinkedProblems().get(2).getClass().getName();
		
		
		FileOutputContext funContext = new DefaultFileOutputContext("C:/Users/akinola/OneDrive - Robert Gordon University/Documents/Datapipe/OutputData/Results/Fitness"+p1name+p2name+"/"+filename+".txt", ",");
        FileOutputContext varContext = new DefaultFileOutputContext("C:/Users/akinola/OneDrive - Robert Gordon University/Documents/Datapipe/OutputData/Results/Variables"+p1name+p2name+"/"+filename2+".txt", ",");
        BufferedWriter funbufferedWriter = funContext.getFileWriter();
        BufferedWriter varbufferedWriter = varContext.getFileWriter();
        
        try {
        	funbufferedWriter.write("Computing_Time" + ",");
			funbufferedWriter.write("Num_Of_Var");
			for(int i=0; i<getHeaders().length; i++) {
	    		funbufferedWriter.write(","+getHeaders()[i]);
	 	    }
	    	funbufferedWriter.newLine();
		} catch (IOException e1) {
			throw new RuntimeException("Error writing data ", e1);
		}
    	
        
      //  System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism",
	  //        "" + 5);
		
		List<Integer> src = new ArrayList<>();
		for (int i = 0; i < runner.getIndependentRuns(); i++) {
		  src.add(i);
		}
		src.forEach(integer-> {	
			initTime = System.currentTimeMillis();
			runLinks();
			try {
				printObjectivesToFile(funbufferedWriter, solutions, getCurrentComputingTime());
				printVariablesToFile(varbufferedWriter, solutions);
				
				funbufferedWriter.newLine();
				varbufferedWriter.newLine();
			} catch (IOException e) {
				throw new RuntimeException("Error writing data ", e);
			}
			
			});	
		
			
		try {	
			funbufferedWriter.close();
			varbufferedWriter.close();
		} catch (IOException e) {
			throw new RuntimeException("Error writing data ", e);
		}
	//	while (count<runner.getIndependentRuns()) {
	//		runLinks();
	//		count++;
	//	}
		
	//	new LinkedSolutionListOutput(linkedsolutions)
	//	.setHeaderOutputContext(getHeaders())
    //    .setFunFileOutputContext(new DefaultFileOutputContext("C:/Users/akinola/OneDrive - Robert Gordon University/Documents/Datapipe/OutputData/Fitness10000/"+filename+".txt", ","))
    //    .setVarFileOutputContext(new DefaultFileOutputContext("C:/Users/akinola/OneDrive - Robert Gordon University/Documents/Datapipe/OutputData/Variables/"+filename2+".txt", ","))
    //    .setComputingTimeOutputContext(totalComputingTime)
    //    .println();
		
	 }
	
	@SuppressWarnings({ "unchecked", "static-access" })
	public void runLinks() {
		solutions = new ArrayList<>();
		AdjacencyMatrix matrix = runner.getProblemList().getAdjacencyMatrix();
		S parentsolution;
		S childsolution;
		for(int i = 0; i< runner.getProblemList().getLinkedProblems().size(); i++) {
		 Problem<S> parentproblem = (Problem<S>) runner.getProblemList().getLinkedProblems().get(i+1);
		 parentsolution = parentproblem.createsolution(); //generate random solution for parent problem
		 parentproblem.evaluate(parentsolution); // evaluate that random solution
		 
		 for(int j = 0; j< runner.getProblemList().getLinkedProblems().size(); j++) {
			 Problem<S> childproblem =  (Problem<S>) runner.getProblemList().getLinkedProblems().get(j+1);
			 if(matrix.getEdge(i,j)==1) {
				solutions.add(parentsolution);
				GetProblemFactory factory =  new GetProblemFactory(childproblem, parentsolution);
				childproblem = (Problem<S>) factory.getProblemInstance();
				runner.getProblemList().getLinkedProblems().put(j+1, childproblem);
				for(int k=0; k<1000; k++) {
					childsolution = childproblem.createsolution(); // generate random solution for child problem
					childproblem.evaluate(childsolution); // evaluate that random solution
					solutions.add(childsolution);
				}
				//linkedsolutions.put(parentsolution, solutions); 
			 }
		 } 
		}
	}
	
	public long getCurrentComputingTime() {
		long finalTime = System.currentTimeMillis();
		if((finalTime - initTime)<0) {
			return 0;
		}
	    return finalTime - initTime;
	}
	
	public String[] getHeaders() {
		
		header[0] = runner.getProblemList().getLinkedProblems().get(1).getClass().getName();
		for(int i=1; i<header.length; i++) {
			header[i] = runner.getProblemList().getLinkedProblems().get(2).getClass().getName()+"_"+i;
		}
		
		return header;
	}
	
	public void printVariablesToFile(
			BufferedWriter bufferedWriter, List<S> linkedsolutionList) throws IOException {
		     
		    try {
		      if (linkedsolutionList.size() > 0) {
		    	  S solution = linkedsolutionList.get(0);
		    	  int numberofvariables = solution.variables().size();
		    	  for(int i=0;i<solution.variables().size()-1;i++)
	    			  bufferedWriter.write("" + solution.variables().get(i).toString()+",");
	    	
	    		  bufferedWriter.write("" + solution.variables().get(numberofvariables-1).toString());
		      }
		    } catch (IOException e) {
		      throw new RuntimeException("Error writing data ", e);
		    }
	  }
	
	  public void printObjectivesToFile(
			  BufferedWriter bufferedWriter, List<S> linkedsolutionList, long computingTime) {
			  try {
			    if (linkedsolutionList.size() > 0) {
			    	S solution = linkedsolutionList.get(0);
			    	S sol = linkedsolutionList.get(1);
			    	int count = 0;
			    	if(solution.problemname().equalsIgnoreCase("FLP")) {
			    	   BinarySolution binarysol = (BinarySolution) solution;
			    	   for(int i=0;i<binarysol.variables().size();i++) {  
				    	   if(binarysol.variables().get(i).cardinality()>=1) {
				    		   count++;
				    	   }
				       }
			    	}else if(solution.problemname().equalsIgnoreCase("KP")) {
			    	   BinarySolution binarysol = (BinarySolution) solution;
			    	   count = binarysol.variables().get(0).cardinality();
			    	}else {
			    	   count = sol.variables().size();
			    	}
			    	bufferedWriter.write(computingTime + ",");
			    	bufferedWriter.write(count+"");
			    	
			    	for(int i=0; i<linkedsolutionList.size(); i++) 
			       			bufferedWriter.write(","+linkedsolutionList.get(i).getFitness());
			    	  
			        
			    }
			      
			    } catch (IOException e) {
			      throw new RuntimeException("Error printing objectives to file: ", e);
			    }
	  }

}
