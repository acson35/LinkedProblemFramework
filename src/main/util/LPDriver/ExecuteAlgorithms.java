package main.util.LPDriver;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Example.IWSP;
import main.algorithm.AlgorithmLP;
import main.algorithm.AlgorithmLPs;
import main.algorithm.MultiCriteriaRankingAlgorithm.MCRGALPs;
import main.problem.Problem;
import main.solution.solution;
import main.solution.solutionPair;
import main.solution.solutionSet;
import main.solution.binarySolution.BinarySolution;
import main.util.fileoutput.FileOutputContext;
import main.util.fileoutput.Implementation.DefaultFileOutputContext;

public class ExecuteAlgorithms<S extends solution<?>> {
	
	 private LinkedProblemRunner runner;
	 String filename;
	 String filename2;
	 String filename3;
	 String[] header;
	 private long initTime;
	 //private List<Long> totalComputingTime;
	 private AlgorithmLP<S, solutionSet> linkedproblemAlgo;
	 
	 private MCRGALPs linkedproblemAlgos;
	 
	 public ExecuteAlgorithms(LinkedProblemRunner runner) {
		 this.runner = runner;
		 this.filename = runner.getOutputLinkedProblemFileName();
		 this.filename2 = runner.getOutputLinkedProblemFileName();
		// this.filename3 = runner.getOutputLinkedProblemFileName();
		 header = new String[runner.getProblemList().getLinkedProblems().size()];
		// this.totalComputingTime = new ArrayList<>();
	 } 
	 
	 public ExecuteAlgorithms(LinkedProblemRunner runner, AlgorithmLP<S, solutionSet> linkedproblemAlgo) {
		 this(runner);
		 this.linkedproblemAlgo = linkedproblemAlgo;
	 } 
	 
	 public ExecuteAlgorithms(LinkedProblemRunner runner, MCRGALPs linkedproblemAlgo) {
		 this(runner);
		 this.linkedproblemAlgos = linkedproblemAlgo;
	 } 
	
	@SuppressWarnings("unchecked")
	public void run(){
		
		String p1name = linkedproblemAlgos.getProblem().getLinkedProblems().get(1).getClass().getName();
		String p2name = linkedproblemAlgos.getProblem().getLinkedProblems().get(2).getClass().getName();
		String p3name = linkedproblemAlgos.getProblem().getLinkedProblems().get(3).getClass().getName();
		
		FileOutputContext funContext = new DefaultFileOutputContext("C:/Users/akinola/OneDrive - Robert Gordon University/Documents/Datapipe/OutputData/MCRGALP_Fitness"+p1name+p2name+p3name+"/"+filename+".txt", ",");
        FileOutputContext varContext = new DefaultFileOutputContext("C:/Users/akinola/OneDrive - Robert Gordon University/Documents/Datapipe/OutputData/MCRGALP_Variables"+p1name+p2name+p3name+"/"+filename2+".txt", ",");
        BufferedWriter funbufferedWriter = funContext.getFileWriter();
        BufferedWriter varbufferedWriter = varContext.getFileWriter();
        
        try {
        	funbufferedWriter.write("Computing_Time" + ",");
			funbufferedWriter.write("Num_Of_Var");
			for(int i=0; i<getHeaders().length; i++) {
	    		funbufferedWriter.write(","+getHeaders()[i]);
	 	    }
			funbufferedWriter.write(","+"Inventory_Cost");
			funbufferedWriter.write(","+"Total_Fixed_Cost");
			funbufferedWriter.write(","+"Total_Variable_Cost");
	    	funbufferedWriter.newLine();
		} catch (IOException e1) {
			throw new RuntimeException("Error writing data ", e1);
		}
		
        
       // System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism",
  	   //      "" + 4);
        
		List<Integer> src = new ArrayList<>();
		for (int i = 0; i < runner.getIndependentRuns(); i++) {
		  //src.add(i);
			initTime = System.currentTimeMillis();
			
			linkedproblemAlgos.run();
			
			try {
				
				//List<solutionPair> 
				myprintObjectivesToFile(funbufferedWriter, (main.solution.solutionSet)linkedproblemAlgos.getResult(), getCurrentComputingTime());
				myprintVariablesToFile(varbufferedWriter, (main.solution.solutionSet)linkedproblemAlgos.getResult());
				
				funbufferedWriter.newLine();
				varbufferedWriter.newLine();
			} catch (IOException e) {
				throw new RuntimeException("Error writing data ", e);
			}
		}
		try {	
			funbufferedWriter.close();
			varbufferedWriter.close();
		} catch (IOException e) {
			throw new RuntimeException("Error writing data ", e);
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
		header[1] = runner.getProblemList().getLinkedProblems().get(2).getClass().getName();
		header[2] = runner.getProblemList().getLinkedProblems().get(3).getClass().getName();
		
		return header;
	}
	
	@SuppressWarnings({ "unchecked" })
	public void printVariablesToFile(
			BufferedWriter bufferedWriter, solutionPair linkedsolutions) throws IOException {
		     
		    try {
		      if (null != linkedsolutions) {
		    	  S parentsolution = (S) ((main.solution.solutionPair) linkedsolutions).getParentSolution();
		    	  int numberofvariables = parentsolution.variables().size();
		    	  for(int i=0;i<parentsolution.variables().size()-1;i++)
	    			  bufferedWriter.write("" + parentsolution.variables().get(i)+",");
	    	
	    		  bufferedWriter.write("" + parentsolution.variables().get(numberofvariables-1)+",");
	    		  
	    		  S childsolution = (S) ((main.solution.solutionPair) linkedsolutions).getChildSolution();
	    		  int numberofvariables2 = childsolution.variables().size();
	    		  for(int i=0;i<childsolution.variables().size()-1;i++)
	    			  bufferedWriter.write("" + childsolution.variables().get(i)+",");
	    	
	    		  bufferedWriter.write("" + childsolution.variables().get(numberofvariables2-1));
	    		  
		      }
		    } catch (IOException e) {
		      throw new RuntimeException("Error writing data ", e);
		    }
	  }
	
	@SuppressWarnings("unchecked")
	public void myprintVariablesToFile(
			BufferedWriter bufferedWriter, solutionSet linkedsolutions) throws IOException {
		   // Problem<?> pro = (IWSP)runner.getProblemList().getLinkedProblems().get(1);
		    try {
		      if (null != linkedsolutions) {
		    	  S parentsolution = (S) linkedsolutions.getSolution(1);
		    	  int numberofvariables = parentsolution.variables().size();
		    	  for(int i=0;i<parentsolution.variables().size()-1;i++)
	    			  bufferedWriter.write("" + parentsolution.variables().get(i)+",");
	    	
	    		  bufferedWriter.write("" + parentsolution.variables().get(numberofvariables-1)+",");
	    		  
	    		  S childsolution = (S) linkedsolutions.getSolution(2);
	    		  int numberofvariables2 = childsolution.variables().size();
	    		  for(int i=0;i<childsolution.variables().size()-1;i++)
	    			  bufferedWriter.write("" + childsolution.variables().get(i)+",");
	    	
	    		  bufferedWriter.write("" + childsolution.variables().get(numberofvariables2-1)); 
	    		  
	    		  S childsolution1 = (S) linkedsolutions.getSolution(3);
	    		  int numberofvariables3 = childsolution1.variables().size();
	    		  for(int i=0;i<childsolution1.variables().size()-1;i++)
	    			  bufferedWriter.write("" + childsolution1.variables().get(i)+",");
	    	
	    		  bufferedWriter.write("" + childsolution1.variables().get(numberofvariables3-1));
		      }
		    } catch (IOException e) {
		      throw new RuntimeException("Error writing data ", e);
		    }
	  }
	
	@SuppressWarnings({ "unchecked" })
	public void printVariablesToFile(
			BufferedWriter bufferedWriter, solutionSet linkedsolutions) throws IOException {
		     
		    try {
		      if (null != linkedsolutions) {
		    	  
		    	  S parentsolution = (S) linkedsolutions.getSolution(1);
		    	  int numberofvariables = parentsolution.variables().size();
		    	  for(int i=0;i<parentsolution.variables().size()-1;i++)
	    			  bufferedWriter.write("" + parentsolution.variables().get(i).toString()+",");
	    	
	    		  bufferedWriter.write("" + parentsolution.variables().get(numberofvariables-1).toString()+",");
	    		  
	    		  S childsolution = (S)  linkedsolutions.getSolution(2);
	    		  int numberofvariables2 = childsolution.variables().size();
	    		  for(int i=0;i<childsolution.variables().size()-1;i++)
	    			  bufferedWriter.write("" + childsolution.variables().get(i)+",");
	    	
	    		  bufferedWriter.write("" + childsolution.variables().get(numberofvariables2-1));
	    		  
		      }
		    } catch (IOException e) {
		      throw new RuntimeException("Error writing data ", e);
		    }
	  }

	@SuppressWarnings("unchecked")
	public void printVariablesToFiles(
			BufferedWriter bufferedWriter, List<solutionSet> linkedsolutions) throws IOException { 
		    try {
		      if (null != linkedsolutions) {
		    	  solutionSet sol = (main.solution.solutionSet) linkedsolutions.get(0);
		    	  for(Integer ind: sol.getSolutionSet().keySet()) {
		    		  S parentsolution = (S) ((main.solution.solutionSet) linkedsolutions).getSolutionSet().get(ind);
			    	  int numberofvariables = parentsolution.variables().size();
			    	  for(int i=0;i<parentsolution.variables().size()-1;i++)
		    			  bufferedWriter.write("" + parentsolution.variables().get(i).toString()+",");
		    	
		    		  bufferedWriter.write("" + parentsolution.variables().get(numberofvariables-1).toString()+",");
		    	  }    		  
		      }
		    } catch (IOException e) {
		      throw new RuntimeException("Error writing data ", e);
		    }
	  }
	
	@SuppressWarnings({ "unchecked" })
	public void printObjectivesToFile(
			  BufferedWriter bufferedWriter, solutionSet solutionPair, long computingTime) {
			  try {
			    if (null != solutionPair) {
			    	S parentsolution = (S) solutionPair.getSolution(1);
			    	S childsolution = (S) (S) solutionPair.getSolution(2);
			    	
			    	int count = 0;
			    	if(parentsolution.problemname().equalsIgnoreCase("FLP")) {
			    	   BinarySolution binarysol = (BinarySolution) parentsolution;
			    	   for(int i=0;i<binarysol.variables().size();i++) {  
				    	   if(binarysol.variables().get(i).cardinality()>=1) {
				    		   count++;
				    	   }
				       }
			    	}else if(parentsolution.problemname().equalsIgnoreCase("KP")) {
			    	   BinarySolution binarysol = (BinarySolution) parentsolution;
			    	   count = binarysol.variables().get(0).cardinality();
			    	}else {
			    	   count = childsolution.variables().size();
			    	}
			    	bufferedWriter.write(computingTime + ",");
			    	bufferedWriter.write(count+"");
			    	
			    	bufferedWriter.write(","+parentsolution.getFitness()[0]);
			    	bufferedWriter.write(","+childsolution.getFitness()[0]);
			       			
			    }
			      
			    } catch (IOException e) {
			      throw new RuntimeException("Error printing objectives to file: ", e);
			    }
	  }

	
	
	@SuppressWarnings("unchecked")
	public void myprintObjectivesToFile(
			  BufferedWriter bufferedWriter, solutionSet solutionpair, long computingTime) {
			  try {
			    if (null != solutionpair) {
			    	
			    	Problem<?> pro = (IWSP)runner.getProblemList().getLinkedProblems().get(1);
			    	
			    	S parentsolution = (S) solutionpair.getSolution(1);
			    	S childsolution = (S) solutionpair.getSolution(2);
			    	S childsolution1 = (S) solutionpair.getSolution(3);
			    	
			    	
			    	int count = 0;
			    	if(parentsolution.problemname().equalsIgnoreCase("FLP")) {
			    	   BinarySolution binarysol = (BinarySolution) parentsolution;
			    	   for(int i=0;i<binarysol.variables().size();i++) {  
				    	   if(binarysol.variables().get(i).cardinality()>=1) {
				    		   count++;
				    	   }
				       }
			    	}else if(parentsolution.problemname().equalsIgnoreCase("IWSP")) {
			    		BinarySolution binarysol = (BinarySolution) parentsolution;
				    	   for(int i=0;i<binarysol.variables().size();i++) {  
					    	   if(binarysol.variables().get(i).cardinality()>=1) {
					    		   count++;
					    	   }
					   }
			    	}else if(parentsolution.problemname().equalsIgnoreCase("KP")) {
			    	   BinarySolution binarysol = (BinarySolution) parentsolution;
			    	   count = binarysol.variables().get(0).cardinality();
			    	}else {
			    	   count = childsolution.variables().size();
			    	}
			    	
			    	bufferedWriter.write(computingTime + ",");
			    	bufferedWriter.write(count+"");
			    	bufferedWriter.write(","+parentsolution.getFitness()[0]);
			    	bufferedWriter.write(","+childsolution.getFitness()[0]);
			    	bufferedWriter.write(","+childsolution1.getFitness()[0]);
			    	bufferedWriter.write(","+((IWSP) pro).inventoryPolicy((BinarySolution) parentsolution));
			    	bufferedWriter.write(","+((IWSP) pro).totalFixedCost((BinarySolution) parentsolution));
			    	bufferedWriter.write(","+((IWSP) pro).totalTransportCost((BinarySolution) parentsolution));
			    	System.out.println(parentsolution.toString());
			    }
			      
			    } catch (IOException e) {
			      throw new RuntimeException("Error printing objectives to file: ", e);
			    }
	  }
	
	
	@SuppressWarnings({ "unchecked" })
	public void printObjectivesToFile(
			  BufferedWriter bufferedWriter, List<solutionPair> solutionSets, long computingTime) {
			  try {
			    if (null != solutionSets) {
			    	
			    	bufferedWriter.write(computingTime+"");
			    	int y = solutionSets.size();
			    	for(int x=0; x<y-1; x++) {
			    		S parentsolution = (S) solutionSets.get(x).getParentSolution();
				    	S childsolution = (S) solutionSets.get(x).getChildSolution();
				    	
				    	int count = 0;
				    	if(parentsolution.problemname().equalsIgnoreCase("FLP")) {
				    	   BinarySolution binarysol = (BinarySolution) parentsolution;
				    	   for(int i=0;i<binarysol.variables().size();i++) {  
					    	   if(binarysol.variables().get(i).cardinality()>=1) {
					    		   count++;
					    	   }
					       }
				    	}else if(parentsolution.problemname().equalsIgnoreCase("KP")) {
				    	   BinarySolution binarysol = (BinarySolution) parentsolution;
				    	   count = binarysol.variables().get(0).cardinality();
				    	}else {
				    	   count = childsolution.variables().size();
				    	}
				    	
				    	bufferedWriter.write(","+count);
				    	
				    	bufferedWriter.write(","+parentsolution.getFitness());
				    	bufferedWriter.write(","+childsolution.getFitness());
			    	}
			    	S parentsolution = (S) solutionSets.get(y-1).getParentSolution();
			    	S childsolution = (S) solutionSets.get(y-1).getChildSolution();
			    	int count = 0;
			    	if(parentsolution.problemname().equalsIgnoreCase("FLP")) {
			    	   BinarySolution binarysol = (BinarySolution) parentsolution;
			    	   for(int i=0;i<binarysol.variables().size();i++) {  
				    	   if(binarysol.variables().get(i).cardinality()>=1) {
				    		   count++;
				    	   }
				       }
			    	}else if(parentsolution.problemname().equalsIgnoreCase("KP")) {
			    	   BinarySolution binarysol = (BinarySolution) parentsolution;
			    	   count = binarysol.variables().get(0).cardinality();
			    	}else {
			    	   count = childsolution.variables().size();
			    	}
			    	bufferedWriter.write(","+count);
			    	
			    	bufferedWriter.write(","+parentsolution.getFitness()[0]);
			    	bufferedWriter.write(","+childsolution.getFitness()[0]);  			
			    }
			      
			    } catch (IOException e) {
			      throw new RuntimeException("Error printing objectives to file: ", e);
			    }
	  }
	
	@SuppressWarnings("unchecked")
	public void printObjectivesToFiles(
			  BufferedWriter bufferedWriter, List<solutionSet> solutionSets, long computingTime) {
			  try {
			    if (null != solutionSets) {
			    	Problem<?> pro = (IWSP)runner.getProblemList().getLinkedProblems().get(1);
			    	bufferedWriter.write(computingTime+"");
			    	int y = solutionSets.size();
			    	for(int x=0; x<y-1; x++) {
			    		
				    	S parentsolution = (S) ((main.solution.solutionSet) solutionSets.get(x)).getSolutionSet().get(1);
				    	S childsolution = (S) ((main.solution.solutionSet) solutionSets.get(x)).getSolutionSet().get(2);
				    	S childsolution1 = (S) ((main.solution.solutionSet) solutionSets.get(x)).getSolutionSet().get(3);
				    	
				    	int count = 0;
				    	if(parentsolution.problemname().equalsIgnoreCase("FLP") || parentsolution.problemname().equalsIgnoreCase("IWSP")) {
				    	   BinarySolution binarysol = (BinarySolution) parentsolution;
				    	   for(int i=0;i<binarysol.variables().size();i++) {  
					    	   if(binarysol.variables().get(i).cardinality()>=1) {
					    		   count++;
					    	   }
					       }
				    	}else if(parentsolution.problemname().equalsIgnoreCase("KP")) {
				    	   BinarySolution binarysol = (BinarySolution) parentsolution;
				    	   count = binarysol.variables().get(0).cardinality();
				    	}else {
				    	   count = childsolution.variables().size();
				    	}
				    	
				    	bufferedWriter.write(","+count);
				    	
				    	bufferedWriter.write(","+parentsolution.getFitness());
				    	bufferedWriter.write(","+childsolution.getFitness());
			    	}
			    	S parentsolution = (S) solutionSets.get(y-1).getSolutionSet().get(1);
			    	S childsolution = (S) solutionSets.get(y-1).getSolutionSet().get(2);
			    	S childsolution1 = (S) solutionSets.get(y-1).getSolutionSet().get(3);
			    	int count = 0;
			    	if(parentsolution.problemname().equalsIgnoreCase("FLP") || parentsolution.problemname().equalsIgnoreCase("IWSP")) {
			    	   BinarySolution binarysol = (BinarySolution) parentsolution;
			    	   for(int i=0;i<binarysol.variables().size();i++) {  
				    	   if(binarysol.variables().get(i).cardinality()>=1) {
				    		   count++;
				    	   }
				       }
			    	}else if(parentsolution.problemname().equalsIgnoreCase("KP")) {
			    	   BinarySolution binarysol = (BinarySolution) parentsolution;
			    	   count = binarysol.variables().get(0).cardinality();
			    	}else {
			    	   count = childsolution.variables().size();
			    	}
			    	bufferedWriter.write(","+count);
			    	bufferedWriter.write(","+parentsolution.getFitness()[0]);
			    	bufferedWriter.write(","+childsolution.getFitness()[0]);
			    	bufferedWriter.write(","+childsolution1.getFitness()[0]);
			    	bufferedWriter.write(","+((IWSP) pro).inventoryPolicy((BinarySolution) parentsolution));
			    	bufferedWriter.write(","+((IWSP) pro).totalFixedCost((BinarySolution) parentsolution));
			    	bufferedWriter.write(","+((IWSP) pro).totalTransportCost((BinarySolution) parentsolution));
			       			
			    }
			      
			    } catch (IOException e) {
			      throw new RuntimeException("Error printing objectives to file: ", e);
			    }
	  }

	
	
}
