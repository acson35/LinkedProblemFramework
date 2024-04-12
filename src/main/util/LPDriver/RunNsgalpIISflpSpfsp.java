package main.util.LPDriver;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import Example.SFLP;
import Example.SFLP_SPFSP_Problems;
import Example.SPFSP;
import main.algorithm.MultiObjectiveAlgorithm.NSGALPs;
import main.linkedproblem.LinkedProblem;
import main.operator.crossover.CrossoverOperator;
import main.operator.crossover.CrossoverClasses.HUXCrossover;
import main.operator.crossover.CrossoverClasses.PMXCrossover;
import main.operator.crossover.CrossoverClasses.UniformCrossover;
import main.operator.mutation.MutationOperator;
import main.operator.mutation.MutationClasses.BitFlipMutation;
import main.operator.mutation.MutationClasses.PermutationSwapMutation;
import main.operator.selection.SelectionOperator;
import main.operator.selection.selectionClasses.BinaryTournamentSelection;
import main.solution.solution;
import main.solution.solutionSet;
import main.solution.binarySolution.BinarySolution;
import main.solution.permutationSolution.IntegerPermutationSolution;
import main.util.comparator.DominancePairComparator;
import main.util.evaluator.SolutionListEvaluator;
import main.util.evaluator.Implementation.SequentialSolutionListEvaluator;
import main.util.fileoutput.FileOutputContext;
import main.util.fileoutput.Implementation.DefaultFileOutputContext;
import main.util.model.instance.SPFSPInstance;
import main.util.model.instance.SFLPInstance.SFLPInstance;

public class RunNsgalpIISflpSpfsp<S extends solution<?>> {

	
	public RunNsgalpIISflpSpfsp() {
		
	}
	
	
	@SuppressWarnings("unchecked")
	public NSGALPs getAlgorithms(LinkedProblem linkedproblem, double pc1, double mc1, double pc2, double mc2) {
		//int numberOfCores = 4;
	    CrossoverOperator<BinarySolution> crossoverOperator1 = 
	    		new UniformCrossover(pc1);
	    
	    MutationOperator<BinarySolution> mutationOperator1 =
	        new BitFlipMutation(mc1);
	    
	    CrossoverOperator<IntegerPermutationSolution> crossoverOperator2 = 
	    		new PMXCrossover(pc2);
	    MutationOperator<IntegerPermutationSolution> mutationOperator2 =
	        new PermutationSwapMutation(mc2);
	    
	    @SuppressWarnings("rawtypes")
		SelectionOperator<List<solutionSet>, solutionSet> selectionOperator = 
	    		new BinaryTournamentSelection(new DominancePairComparator<solutionSet>());
	    
	    SolutionListEvaluator<solution<?>> evaluator = 
	    		new SequentialSolutionListEvaluator<solution<?>>();
	    
		List<CrossoverOperator> crossoverOperators = new ArrayList<>();
		
		crossoverOperators.add(0,crossoverOperator1);
		crossoverOperators.add(1,crossoverOperator2);
		
		List<MutationOperator> mutationOperators = new ArrayList<>();
		
		mutationOperators.add(0,mutationOperator1);
		mutationOperators.add(1,mutationOperator2);
        
		NSGALPs sflp_spfsp = new NSGALPs(linkedproblem, 5000, 50, 50, 50, crossoverOperators, mutationOperators, selectionOperator, evaluator);
		
		return sflp_spfsp;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void NSGALP_SflpSpfspExperiments(String[] args_sflp, 
			String[] args2_sflp, 
			String[] args_spfsp, 
			String[] args2_spfsp) throws IOException {
		
		
        for(int i = 0; i < args_sflp.length; i++) {
        	
        	if(args_sflp[i] == null)continue;
        	String sflp_filename = args_sflp[i];
        	SFLP parentproblem = new SFLP(sflp_filename);
		 	int problemsize1 = ((SFLPInstance) parentproblem.getData()).getnCustomers();
		 	int nfacilities = ((SFLPInstance) parentproblem.getData()).getnFacilities();
       		
		 	if(nfacilities == 10)continue;
		 	if(problemsize1 > 1000)continue;
		 	if(nfacilities > 30)continue;
   		 
   		 	for(int j = 0; j < args_spfsp.length; j++) {
   			 
   		 		if(args_spfsp[j] == null)continue;
   		 		
   		 		String spfsp_filename = args_spfsp[j];
   			
   		 		
   		 		SPFSP childproblem = new SPFSP(spfsp_filename);
   		 		int problemsize2 = ((SPFSPInstance) childproblem.getData()).getNJobs();
   		 		int nfac = ((SPFSPInstance) childproblem.getData()).getNFacilities();
   			 
   		 		String p1name = parentproblem.getName();
   		 		String p2name = childproblem.getName();
   			 			
   		 		if(nfacilities != nfac)continue;
   		 		
   		 		String newfile = "C:/Users/akinola/OneDrive - Robert Gordon University/Documents/Datapipe/OutputData/NSGALP_II_Fitness_"+p1name+"_"+p2name+"/"+"NSGALP_II_" + parentproblem.getName()+"_"+childproblem.getName()+"_"+args2_sflp[i].replace(".txt", "")+"_"+ args2_spfsp[j].replace(".txt", "")+".txt";
	 			File myfile = new File(newfile);
	 			if(myfile.exists())continue;
   			 
   		 		if(problemsize1==problemsize2) {
   				 
   		 			SFLP_SPFSP_Problems linkedproblem = new SFLP_SPFSP_Problems(sflp_filename, spfsp_filename);
   	        		
   				 
   		 			//System.out.println(problemsize1 + " " + nfacilities +" "+ problemsize2 + " " + nfac);
   				 
   		 			//System.out.print(args2_sflp[i].replace(".txt", "")+ " ");
   		 			//System.out.println(args2_spfsp[j].replace(".txt", ""));
   				 
   		 			double pc1 = 0.1 + (0.9-0.1)*Math.random();;
   		 			double mc1 = 0.1 + (0.9-0.1)*Math.random();
   				 
   		 			pc1 = Double.parseDouble(String.format("%.1f", pc1));
   		 			mc1 = Double.parseDouble(String.format("%.1f", mc1));
   		 			System.out.println(pc1 +" "+ mc1);
   				 
   		 			LinkedProblemRunner experiment;
   		 			experiment = new LinkedProblemRunnerBuilder("NSGALP_II_" + parentproblem.getName()+"_"+childproblem.getName()+"_"+args2_sflp[i].replace(".txt", "")+"_"+ args2_spfsp[j].replace(".txt", ""))
	    			       .setProblemList(linkedproblem)
	    			       .setOutputLinkedProblemFileName("")
	    			       .setIndependentRuns(1)
	    			       .build(); 
   		 			
   		 			
   		 			
   		 			
   		 			FileOutputContext funContext = new DefaultFileOutputContext("C:/Users/akinola/OneDrive - Robert Gordon University/Documents/Datapipe/OutputData/NSGALP_II_Fitness_"+p1name+"_"+p2name+"/"+experiment.getOutputLinkedProblemFileName()+".txt", ",");
   		 			FileOutputContext varContext = new DefaultFileOutputContext("C:/Users/akinola/OneDrive - Robert Gordon University/Documents/Datapipe/OutputData/NSGALP_II_Var_"+p1name+"_"+p2name+"/"+experiment.getOutputLinkedProblemFileName()+".txt", ",");
	           
	          //NSGALP_II_Fitness_SFLP_SPFSP
	    		//NSGALP_III_Fitness_SFLP_SPFSP
	    		//NSGALP_II_Var_SFLP_SPFSP
	    		//NSGALP_III_Var_SFLP_SPFSP
	            
   		 			BufferedWriter funbufferedWriter = funContext.getFileWriter();
   		 			BufferedWriter varbufferedWriter = varContext.getFileWriter();
   		 			try {
	            	
   		 				funbufferedWriter.write("Computing_Time" + ",");
   		 				funbufferedWriter.write("Num_Of_Var");
   		 				funbufferedWriter.write(","+p1name + "_1");
   		 				funbufferedWriter.write(","+p1name + "_2");
   		 				funbufferedWriter.write(","+p2name + "_1");
   		 				funbufferedWriter.write(","+p2name + "_2");
	    			
   		 				funbufferedWriter.write(","+"Crossover_Rate");
   		 				funbufferedWriter.write(","+"Mutation_Rate");
   		 				funbufferedWriter.newLine();
   		 			} catch (IOException e1) {
   		 				throw new RuntimeException("Error writing data ", e1);
   		 			}
   		 			
   		 			
	            
	        	for(int k=0; k<10;k++) {
		        	
	        		long finalinitial = System.currentTimeMillis();
	        		
	        		linkedproblem = new SFLP_SPFSP_Problems(sflp_filename, spfsp_filename);
	            	
	    			
	        		experiment = new LinkedProblemRunnerBuilder("NSGALP_II_" + parentproblem.getName()+"_"+childproblem.getName()+"_"+args2_sflp[i].replace(".txt", "")+"_"+ args2_spfsp[j].replace(".txt", ""))
	        			       .setProblemList(linkedproblem)
	        			       .setOutputLinkedProblemFileName("")
	        			       .setIndependentRuns(1)
	        			       .build(); 
	        		
	        		NSGALPs NSGALP_Algo = (NSGALPs)getAlgorithms(linkedproblem,pc1,mc1,pc1,mc1);
	            	
	        		NSGALP_Algo.run();
	        		
	        		//IWSP iwsp = (IWSP) NSGALP_Algo.getProblem().getLinkedProblems().get(1);
	    			List<solutionSet> sol2 = NSGALP_Algo.getResult();
	    			     	
	            	try {
	            		long finalTime = System.currentTimeMillis();
	                	
	                	long compTime = finalTime-finalinitial;
	                	
	            		printObjectivesToFiles(NSGALP_Algo, funbufferedWriter, sol2, compTime, pc1, mc1);
	                	printVariablesToFiles(varbufferedWriter, sol2);
	    				
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
        			
   		 }
    		
        }
        
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void printObjectivesToFiles(NSGALPs proAlgo, BufferedWriter bufferedWriter, List<solutionSet> solutionSets, long computingTime, double pc1, double mc1) {
			  try {
			    if (null != solutionSets) {
			    	//IWSP iwsp = (IWSP) proAlgo.getProblem().getLinkedProblems().get(1);
			    	//VAP vap = (VAP) proAlgo.getProblem().getLinkedProblems().get(2);
			    	
			    	bufferedWriter.write(computingTime+"");
			    	int y = solutionSets.size();
			    	for(int x=0; x<y-1; x++) {
			    		
				    	S parentsolution = (S) solutionSets.get(x).getSolutionSet().get(1);
				    	S childsolution = (S) solutionSets.get(x).getSolutionSet().get(2);
				    	
				    	
				    	int count = 0;
				    	if(parentsolution.problemname().equalsIgnoreCase("FLP") || 
				    			 parentsolution.problemname().equalsIgnoreCase("IWSP") ||
				    			parentsolution.problemname().equalsIgnoreCase("SFLP")) {
				    	   BinarySolution binarysol = (BinarySolution)parentsolution;
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
				    	bufferedWriter.write(","+parentsolution.getFitness()[1]);
				    	bufferedWriter.write(","+childsolution.getFitness()[0]);
				    	bufferedWriter.write(","+childsolution.getFitness()[1]);
				    	bufferedWriter.write(","+pc1);
				    	bufferedWriter.write(","+mc1);
				    	
			    	}
			    	
			    	S parentsolution = (S) solutionSets.get(y-1).getSolutionSet().get(1);
			    	S childsolution = (S) solutionSets.get(y-1).getSolutionSet().get(2);
			    	
			    	int count = 0;
			    	if(parentsolution.problemname().equalsIgnoreCase("FLP") || 
			    			parentsolution.problemname().equalsIgnoreCase("IWSP") ||
			    			parentsolution.problemname().equalsIgnoreCase("SFLP")) {
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
			    	bufferedWriter.write(","+parentsolution.getFitness()[1]);
			    	bufferedWriter.write(","+childsolution.getFitness()[0]);
			    	bufferedWriter.write(","+childsolution.getFitness()[1]);
			    	bufferedWriter.write(","+pc1);
			    	bufferedWriter.write(","+mc1);		
			    }
			      
			    } catch (IOException e) {
			      throw new RuntimeException("Error printing objectives to file: ", e);
			    }
	  }
	
	
	@SuppressWarnings("unchecked")
	public void printVariablesToFiles(
			BufferedWriter bufferedWriter, List<solutionSet> linkedsolutions) throws IOException { 
		    try {
		      if (null != linkedsolutions) {
		    	  solutionSet sol = (main.solution.solutionSet) linkedsolutions.get(0);
		    	  S parentsolution = (S) sol.getSolution(1);
		    	  int numberofvariables = parentsolution.variables().size();
		    	  for(int i=0;i<parentsolution.variables().size()-1;i++)
	    			  bufferedWriter.write("" + parentsolution.variables().get(i)+",");
	    	
	    		  bufferedWriter.write("" + parentsolution.variables().get(numberofvariables-1)+",");
	    		  
	    		  S childsolution = (S) sol.getSolution(2);
	    		  int numberofvariables2 = childsolution.variables().size();
	    		  for(int i=0;i<childsolution.variables().size()-1;i++)
	    			  bufferedWriter.write("" + childsolution.variables().get(i)+",");
	    	
	    		  bufferedWriter.write("" + childsolution.variables().get(numberofvariables2-1)); 
	    		  
	    		  
		      }
		    } catch (IOException e) {
		      throw new RuntimeException("Error writing data ", e);
		    }
	  }

	
	
}
