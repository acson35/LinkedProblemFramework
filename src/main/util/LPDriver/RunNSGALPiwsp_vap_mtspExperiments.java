package main.util.LPDriver;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Example.IWSP;
import Example.IWSP_VAP_MTSP_Problems;
import Example.MTSP;
import Example.VAP;
import main.algorithm.AlgorithmLPs;
import main.algorithm.MultiCriteriaRankingAlgorithm.MCRGALPs;
import main.algorithm.MultiObjectiveAlgorithm.NSGALPs;
import main.linkedproblem.LinkedProblem;
import main.operator.crossover.CrossoverOperator;
import main.operator.crossover.CrossoverClasses.HUXCrossover;
import main.operator.crossover.CrossoverClasses.IntegerSBXCrossover;
import main.operator.crossover.CrossoverClasses.PMXCrossover;
import main.operator.mutation.MutationOperator;
import main.operator.mutation.MutationClasses.BitFlipMutation;
import main.operator.mutation.MutationClasses.IntegerPolynomialMutation;
import main.operator.mutation.MutationClasses.PermutationSwapMutation;
import main.operator.selection.SelectionOperator;
import main.operator.selection.selectionClasses.BinaryTournamentSelection;
import main.operator.selection.selectionClasses.PairRankingAndCrowdingSelection;
import main.problem.Problem;
import main.solution.solution;
import main.solution.solutionPair;
import main.solution.solutionSet;
import main.solution.binarySolution.BinarySolution;
import main.solution.integerSolution.IntegerSolution;
import main.solution.permutationSolution.IntegerPermutationSolution;
import main.util.comparator.DominancePairComparator;
import main.util.evaluator.SolutionListEvaluator;
import main.util.evaluator.Implementation.SequentialSolutionListEvaluator;
import main.util.fileoutput.FileOutputContext;
import main.util.fileoutput.Implementation.DefaultFileOutputContext;
import main.util.model.instance.JAPInstance;
import main.util.model.instance.IWSPInstance.IWSPInstance;

public class RunNSGALPiwsp_vap_mtspExperiments<S extends solution<?>> {

	
	@SuppressWarnings("unchecked")
	public NSGALPs getAlgorithms(LinkedProblem linkedproblem, double pc1, double mc1, double pc2, int distributionIndex, double pc3, double mc3) {
		//int numberOfCores = 4;
	    CrossoverOperator<BinarySolution> crossoverOperator1 = 
	    		new HUXCrossover(pc1);
	    
	    MutationOperator<BinarySolution> mutationOperator1 =
	        new BitFlipMutation(mc1);
	    
	    CrossoverOperator<IntegerSolution> crossoverOperator2 = new IntegerSBXCrossover(pc2, distributionIndex);
	    
	    MutationOperator<IntegerSolution> mutationOperator2 =
	        new IntegerPolynomialMutation();
	    
	    CrossoverOperator<IntegerPermutationSolution> crossoverOperator3 = 
	    		new PMXCrossover(pc3);
	    MutationOperator<IntegerPermutationSolution> mutationOperator3 =
	        new PermutationSwapMutation(mc3);
	    
	    @SuppressWarnings("rawtypes")
		SelectionOperator<List<solutionSet>, solutionSet> selectionOperator = 
	    		new BinaryTournamentSelection(new DominancePairComparator<solutionSet>());
	    
	    SolutionListEvaluator<solution<?>> evaluator = 
	    		new SequentialSolutionListEvaluator<solution<?>>();
	    
		List<CrossoverOperator> crossoverOperators = new ArrayList<>();
		
		crossoverOperators.add(0,crossoverOperator1);
		crossoverOperators.add(1,crossoverOperator2);
		crossoverOperators.add(2,crossoverOperator3);
		
		List<MutationOperator> mutationOperators = new ArrayList<>();
		
		mutationOperators.add(0,mutationOperator1);
		mutationOperators.add(1,mutationOperator2);
		mutationOperators.add(2,mutationOperator3);
        
		NSGALPs iwsp_vap_mtsp = new NSGALPs(linkedproblem, 5000, 50, 50, 50, crossoverOperators, mutationOperators, selectionOperator, evaluator);
		
		return iwsp_vap_mtsp;
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void NSGALP_IwspVapMtspExperiments(String[] args, String[] args1) throws IOException {
		
		
        for(int i = 0; i < args.length; i++) {
        	
        	double pc1 = 0.5 + (0.9-0.5)*Math.random();;
    		double mc1 = 0.1 + (0.5-0.1)*Math.random();
    		int distIndex = 25;
    		pc1 = Double.parseDouble(String.format("%.1f", pc1));
    		mc1 = Double.parseDouble(String.format("%.1f", mc1));
    		System.out.println(pc1 +" "+ mc1 + " "+ distIndex);
        	
        	String iwsp_vap_mtsp_filename = args[i];
        	
        	IWSP_VAP_MTSP_Problems linkedproblem = new IWSP_VAP_MTSP_Problems(iwsp_vap_mtsp_filename);
        	IWSP parentproblem = (IWSP) linkedproblem.getLinkedProblems().get(1);
        	VAP childproblem = (VAP) linkedproblem.getLinkedProblems().get(2);
    		MTSP childproblem1 = (MTSP) linkedproblem.getLinkedProblems().get(3);
    		String p1name = parentproblem.getName();
    		String p2name = childproblem.getName();
    		String p3name = childproblem1.getName();
    		//IWSPInstance data = ((IWSPInstance) parentproblem.getData());
        //	if(((IWSPInstance) parentproblem.getData()).getnCustomers()>=150)continue;
        		
        	LinkedProblemRunner experiment;
    		experiment = new LinkedProblemRunnerBuilder("NSGALPBased_"+parentproblem.getName()+"_"+childproblem.getName()+"_"+ childproblem1.getName()+"_"+args1[i])
    			       .setProblemList(linkedproblem)
    			       .setOutputLinkedProblemFileName("")
    			       .setIndependentRuns(1)
    			       .build(); 
        	
        	FileOutputContext funContext = new DefaultFileOutputContext("C:/Users/akinola/OneDrive - Robert Gordon University/Documents/Datapipe/OutputData/NSGALP_Fitness"+p1name+p2name+p3name+"/"+experiment.getOutputLinkedProblemFileName()+".txt", ",");
            FileOutputContext varContext = new DefaultFileOutputContext("C:/Users/akinola/OneDrive - Robert Gordon University/Documents/Datapipe/OutputData/NSGALP_Variables"+p1name+p2name+p3name+"/"+experiment.getOutputLinkedProblemFileName()+".txt", ",");
            BufferedWriter funbufferedWriter = funContext.getFileWriter();
            BufferedWriter varbufferedWriter = varContext.getFileWriter();
            try {
            	
            	funbufferedWriter.write("Computing_Time" + ",");
    			funbufferedWriter.write("Num_Of_Var");
    			funbufferedWriter.write(","+p1name);
    			funbufferedWriter.write(","+p2name);
    			funbufferedWriter.write(","+p3name);
    			funbufferedWriter.write(","+"Inventory_Cost");
    			funbufferedWriter.write(","+"Total_Fixed_Cost");
    			funbufferedWriter.write(","+"Total_Variable_Cost");
    			funbufferedWriter.write(","+"Crossover_Rate");
    			funbufferedWriter.write(","+"Mutation_Rate");
    	    	funbufferedWriter.newLine();
    		} catch (IOException e1) {
    			throw new RuntimeException("Error writing data ", e1);
    		}

        	for(int j=0; j<20;j++) {
        	
        		long finalinitial = System.currentTimeMillis();
        		
        		linkedproblem = new IWSP_VAP_MTSP_Problems(iwsp_vap_mtsp_filename);
        		
            	
    			
        		experiment = new LinkedProblemRunnerBuilder("NSGALPBased_"+parentproblem.getName()+"_"+childproblem.getName()+"_"+ childproblem1.getName()+"_"+args1[i])
        			       .setProblemList(linkedproblem)
        			       .setOutputLinkedProblemFileName("")
        			       .setIndependentRuns(1)
        			       .build(); 
        		
        		NSGALPs NSGALP_Algo = (NSGALPs)getAlgorithms(linkedproblem,pc1,mc1,pc1,distIndex,pc1,mc1);
            	
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
	
	
	@SuppressWarnings("unchecked")
	public void printObjectivesToFiles(NSGALPs proAlgo, BufferedWriter bufferedWriter, List<solutionSet> solutionSets, long computingTime, double pc1, double mc1) {
			  try {
			    if (null != solutionSets) {
			    	IWSP iwsp = (IWSP) proAlgo.getProblem().getLinkedProblems().get(1);
			    	VAP vap = (VAP) proAlgo.getProblem().getLinkedProblems().get(2);
			    	
			    	bufferedWriter.write(computingTime+"");
			    	int y = solutionSets.size();
			    	for(int x=0; x<y-1; x++) {
			    		
				    	S parentsolution = (S) ((main.solution.solutionSet) solutionSets.get(x)).getSolutionSet().get(1);
				    	S childsolution = (S) ((main.solution.solutionSet) solutionSets.get(x)).getSolutionSet().get(2);
				    	S childsolution1 = (S) ((main.solution.solutionSet) solutionSets.get(x)).getSolutionSet().get(3);
				    	
				    	vap.setParentsolution(childsolution1);
				    	vap.evaluate((IntegerSolution) childsolution);
				    	iwsp.setParentsolution(childsolution);
				    	iwsp.evaluate((BinarySolution) parentsolution);
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
				    	bufferedWriter.write(","+((IWSP) iwsp).inventoryPolicy((BinarySolution) parentsolution));
				    	bufferedWriter.write(","+((IWSP) iwsp).totalFixedCost((BinarySolution) parentsolution));
				    	bufferedWriter.write(","+((IWSP) iwsp).totalTransportCost((BinarySolution) parentsolution));
				    	bufferedWriter.write(","+pc1);
				    	bufferedWriter.write(","+mc1);
				    	
			    	}
			    	
			    	S parentsolution = (S) solutionSets.get(y-1).getSolutionSet().get(1);
			    	S childsolution = (S) solutionSets.get(y-1).getSolutionSet().get(2);
			    	S childsolution1 = (S) solutionSets.get(y-1).getSolutionSet().get(3);
			    	
			    	vap.setParentsolution(childsolution1);
			    	vap.evaluate((IntegerSolution) childsolution);
			    	iwsp.setParentsolution(childsolution);
			    	iwsp.evaluate((BinarySolution) parentsolution);
			    	
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
			    	bufferedWriter.write(","+((IWSP) iwsp).inventoryPolicy((BinarySolution) parentsolution));
			    	bufferedWriter.write(","+((IWSP) iwsp).totalFixedCost((BinarySolution) parentsolution));
			    	bufferedWriter.write(","+((IWSP) iwsp).totalTransportCost((BinarySolution) parentsolution));
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
	    		  
	    		  S childsolution1 = (S) sol.getSolution(3);
	    		  int numberofvariables3 = childsolution1.variables().size();
	    		  for(int i=0;i<childsolution1.variables().size()-1;i++)
	    			  bufferedWriter.write("" + childsolution1.variables().get(i)+",");
	    		  
	    		  bufferedWriter.write("" + childsolution1.variables().get(numberofvariables3-1)); 
		      }
		    } catch (IOException e) {
		      throw new RuntimeException("Error writing data ", e);
		    }
	  }

}
