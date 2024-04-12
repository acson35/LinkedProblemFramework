package main.util.fileoutput;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import main.solution.solution;
import main.solution.binarySolution.BinarySolution;
import main.util.fileoutput.Implementation.DefaultFileOutputContext;

public class LinkedSolutionListOutput<S extends solution<?>> {
	
	  private FileOutputContext varFileContext;
	  private FileOutputContext funFileContext;
	  private String varFileName;
	  private String funFileName="fitness";
	  private String[] header;
	  private List<List<S>> linkedsolutionList;
	  private Map<S, List<S>> linkedsolutionLists;
	  private List<Long> computingTime;
	

	  public LinkedSolutionListOutput(List<List<S>> linkedsolutionList) {
		  
	    varFileContext = new DefaultFileOutputContext(varFileName);
	    funFileContext = new DefaultFileOutputContext(funFileName);
	    this.header = new String[1];
	    this.linkedsolutionList = linkedsolutionList;
	    this.computingTime = new ArrayList();
	   
	  }
	  
	  public LinkedSolutionListOutput(Map<S, List<S>> linkedsolutionLists) {
		  
		varFileContext = new DefaultFileOutputContext(varFileName);
		funFileContext = new DefaultFileOutputContext(funFileName);
		this.header = new String[1];
		this.linkedsolutionLists = linkedsolutionLists;
		this.computingTime = new ArrayList();
		   
	 }
	  
	  @SuppressWarnings("rawtypes")
	  public LinkedSolutionListOutput setHeaderOutputContext(String[] header) {
		 this.header = header;

		 return this;
	  }

	  @SuppressWarnings("rawtypes")
	  public LinkedSolutionListOutput setVarFileOutputContext(FileOutputContext fileContext) {
	    varFileContext = fileContext;

	    return this;
	  }

	  @SuppressWarnings("rawtypes")
	  public LinkedSolutionListOutput setFunFileOutputContext(FileOutputContext fileContext) {
	    funFileContext = fileContext;

	    return this;
	  }
	  
	  @SuppressWarnings("rawtypes")
	  public LinkedSolutionListOutput setComputingTimeOutputContext(List<Long> computingTime) {
		 this.computingTime = computingTime;

	    return this;
	  }

	  public void print() {
	    printObjectivesToFile(funFileContext, linkedsolutionList);
	    //printVariablesToFile(varFileContext, linkedsolutionList);
	  }
	  
	  public void println() {
		printObjectivesToFile(funFileContext, linkedsolutionLists);
		printVariablesToFile(varFileContext, linkedsolutionLists);
	  }

	  public void printVariablesToFile(
	    FileOutputContext context, List<List<S>> linkedsolutionList) {
	    BufferedWriter bufferedWriter = context.getFileWriter();
	    try {
	      if (linkedsolutionList.size() > 0) {
	    	  for(int i = 0; i < linkedsolutionList.size(); i++) {
	    		int numberOfVariables = linkedsolutionList.get(i).get(0).variables().size();
	  	        for (int j = 0; j < linkedsolutionList.size(); j++) {
	  	          for (int k = 0; k < numberOfVariables - 1; k++) {
	  	            bufferedWriter.write("" + linkedsolutionList.get(i).get(j).variables().get(k) + context.getSeparator());
	  	          }
	  	          bufferedWriter.write(
	  	              "" + linkedsolutionList.get(i).get(j).variables().get(numberOfVariables - 1));

	  	          bufferedWriter.newLine();
	  	        } 
	    	  }
	       
	      }

	      bufferedWriter.close();
	    } catch (IOException e) {
	      throw new RuntimeException("Error writing data ", e);
	    }
	  }
	  
	  public void printVariablesToFile(
			FileOutputContext context, Map<S, List<S>> linkedsolutionList) {
		    BufferedWriter bufferedWriter = context.getFileWriter();

		    try {
		      if (linkedsolutionList.size() > 0) {
		    	  
		    	  
		    	  for (S solution: linkedsolutionList.keySet()) { 
		    		  int numberofvariables = solution.variables().size();
		    		  for(int i=0;i<solution.variables().size()-1;i++)
		    			  bufferedWriter.write("" + solution.variables().get(i).toString()+context.getSeparator());
		    		  //System.out.println(solution.variables());
		    		  bufferedWriter.write("" + solution.variables().get(numberofvariables-1).toString());
		    		  bufferedWriter.newLine();
		    	  }
		    	  
		      }

		      bufferedWriter.close();
		      linkedsolutionList.clear();
		    } catch (IOException e) {
		      throw new RuntimeException("Error writing data ", e);
		    }
	
	  }
	  
	  public void printObjectivesToFile(
			  FileOutputContext context, Map<S, List<S>> linkedsolutionList) {
			  BufferedWriter bufferedWriter = context.getFileWriter();
			   
			  try {
			    if (linkedsolutionList.size() > 0) {
			    	bufferedWriter.write("Computing_Time" + context.getSeparator());
			    	bufferedWriter.write("Num_Of_Var" + context.getSeparator());
			    	for(int i=0; i<header.length; i++) {
			 	    	 bufferedWriter.write(header[i] + context.getSeparator());
			 	    }
			    	bufferedWriter.newLine();
			    	int x=0; 
			    	//System.out.println(computingTime.size());
			    	for (S solution: linkedsolutionList.keySet()) {
			    	 
			    	   List<S> solutionlists = linkedsolutionList.get(solution);
			    	   S sol = solutionlists.get(0);
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
			    	  // System.out.println(computingTime.get(x));
			    	   if(x<computingTime.size()) {
			    		   bufferedWriter.write(computingTime.get(x) + context.getSeparator());
			    	   }
			    	   
			    	   bufferedWriter.write(count + context.getSeparator());
			    	   bufferedWriter.write(solution.getFitness() + context.getSeparator());
			       		for(int i=0; i<solutionlists.size(); i++) {
			       			bufferedWriter.write(solutionlists.get(i).getFitness() + context.getSeparator());
			       		}
			         bufferedWriter.newLine();
			         x++;
			       }
			    }
			      bufferedWriter.close();
			      linkedsolutionList.clear();
			    } catch (IOException e) {
			      throw new RuntimeException("Error printing objectives to file: ", e);
			    }
	  }


	  public void printObjectivesToFile(
	    FileOutputContext context, List<List<S>> linkedsolutionList) {
	    BufferedWriter bufferedWriter = context.getFileWriter();
	   
	    try {
	      if (linkedsolutionList.size() > 0) {
	    	for(int i=0; i<header.length; i++) {
	 	    	 bufferedWriter.write(header[i] + context.getSeparator());
	 	    }
	    	bufferedWriter.newLine();
	    	 
	        for (int i = 0; i < linkedsolutionList.size(); i++) {
	        	for(int j=0; j<linkedsolutionList.get(i).size(); j++)
	        		bufferedWriter.write(linkedsolutionList.get(i).get(j).getFitness() + context.getSeparator());
	          bufferedWriter.newLine();
	        }
	      }

	      bufferedWriter.close();
	    } catch (IOException e) {
	      throw new RuntimeException("Error printing objectives to file: ", e);
	    }
	  }

	  /*
	   * Wrappers for printing with default configuration
	   */
	  public void printObjectivesToFiles(String fileName) {
	    printObjectivesToFile(new DefaultFileOutputContext(fileName), linkedsolutionLists);
	  }
	  
	  public void printObjectivesToFile(String fileName) {
		    printObjectivesToFile(new DefaultFileOutputContext(fileName), linkedsolutionList);
	  }


	  public void printObjectivesToFile(String fileName, String separator) {
	    printObjectivesToFile(new DefaultFileOutputContext(fileName, separator), linkedsolutionList);
	  }
	  
	  public void printObjectivesToFiles(String fileName, String separator) {
		    printObjectivesToFile(new DefaultFileOutputContext(fileName, separator), linkedsolutionLists);
	  }

	  public void printVariablesToFile(String fileName) {
	    printVariablesToFile(new DefaultFileOutputContext(fileName), linkedsolutionList);
	  }

	  public void printVariablesToFile(String fileName, String separator) {
	    printVariablesToFile(new DefaultFileOutputContext(fileName, separator), linkedsolutionList);
	  }
}
