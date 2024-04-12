package main.util.LPDriver;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import Example.VAP;
import Example.MTSP;
import Example.IWSP;
import Example.IWSP_VAP_MTSP_Problems;
import main.algorithm.AlgorithmLPs;
import main.algorithm.MultiCriteriaRankingAlgorithm.MCRGALPs;
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
import main.operator.selection.selectionClasses.TopsisSelection;
import main.solution.solution;
import main.solution.solutionSet;
import main.solution.binarySolution.BinarySolution;
import main.solution.integerSolution.IntegerSolution;
import main.solution.permutationSolution.IntegerPermutationSolution;
import main.util.evaluator.SolutionListEvaluator;
import main.util.evaluator.Implementation.SequentialSolutionListEvaluator;
import main.util.fileoutput.FileOutputContext;
import main.util.fileoutput.Implementation.DefaultFileOutputContext;
import main.util.ranking.Criteria;
import main.util.model.instance.IWSPInstance.IWSPInstance;

public class RunMCRGALPiwsp_vap_mtspExperiments<S extends solution<?>> {

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public MCRGALPs getAlgorithms(LinkedProblem linkedproblem, double pc1, double mc1, double pc2, int distributionIndex, double pc3, double mc3){
		
		
		
		CrossoverOperator<BinarySolution> crossoverOperator1 = 
		    		new HUXCrossover(pc1);
		    
		//double mc1 = 0.5 + (0.9-0.5)*Math.random();
		 
		MutationOperator<BinarySolution> mutationOperator1 =
		        new BitFlipMutation(mc1);
		  
		//double pc2 = 0.5 + (0.9-0.5)*Math.random();
		//int distribution_index = 0;
		CrossoverOperator<IntegerSolution> crossoverOperator2 = new IntegerSBXCrossover(pc2, distributionIndex);
		    
		MutationOperator<IntegerSolution> mutationOperator2 =
		        new IntegerPolynomialMutation();
		    
		//double pc3 = 0.5 + (0.9-0.5)*Math.random();
		
		CrossoverOperator<IntegerPermutationSolution> crossoverOperator3 = 
		    		new PMXCrossover(pc3);
		
		MutationOperator<IntegerPermutationSolution> mutationOperator3 =
		        new PermutationSwapMutation(mc3);
		    
		    SolutionListEvaluator<solution<?>> evaluator = 
		    		new SequentialSolutionListEvaluator<solution<?>>();
		    
			
			//IWSP_VAP_MTSP_Problems linkedproblem = new IWSP_VAP_MTSP_Problems("C:/Users/akinola/OneDrive - Robert Gordon University/Documents/IWSP_VAP_MTSP/p50_10_10_0.6_1.txt");
			
		//	List<AlgorithmLPs> algorithmList = new ArrayList<>();
			
			List<CrossoverOperator> crossoverOperators = new ArrayList<>();
			
			crossoverOperators.add(0,crossoverOperator1);
			crossoverOperators.add(1,crossoverOperator2);
			crossoverOperators.add(2,crossoverOperator3);
			
			List<MutationOperator> mutationOperators = new ArrayList<>();
			
			mutationOperators.add(0,mutationOperator1);
			mutationOperators.add(1,mutationOperator2);
			mutationOperators.add(2,mutationOperator3);
	        
			
			List<Criteria> criteria = new ArrayList<>();
			
			criteria.add(new Criteria("iwspObj", 0.25, true));
			criteria.add(new Criteria("iwspConstraint1", 0.05, true));
			criteria.add(new Criteria("iwspConstraint2", 0.05, true));
			criteria.add(new Criteria("vapObj", 0.25, true));
			criteria.add(new Criteria("vapConstraint", 0.15, true));
			criteria.add(new Criteria("mtspObj", 0.25, true));
			
			SelectionOperator<List<solutionSet>, List<solutionSet>> selectionOperator = 
		    		new TopsisSelection<solutionSet>(10, criteria);
			
		    MCRGALPs iwsp_vap_mtsp = new MCRGALPs(linkedproblem, 5000, 50, 50, 50, criteria, crossoverOperators, mutationOperators, selectionOperator, evaluator);
			
			return iwsp_vap_mtsp;
	}
	
	@SuppressWarnings("rawtypes")
	public void MCRGALP_IwspVapMtspExperiments(String[] args, String[] args2) throws IOException {
		
		
		
        //File file2 = new File("C:/Users/akinola/OneDrive - Robert Gordon University/Documents/Datapipe/InputData/TSP");
        //File[] files2 = file2.listFiles();
		
		
        for(int i = 0; i < args.length; i++) {
        	
        	
        	double pc1 = 0.5 + (0.9-0.5)*Math.random();;
    		double mc1 = 0.1 + (0.5-0.1)*Math.random();
    		int distIndex = 25;
    		
    		pc1 = Double.parseDouble(String.format("%.1f", pc1));
    		mc1 = Double.parseDouble(String.format("%.1f", mc1));
    		System.out.println(pc1 +" "+ mc1 + " "+ distIndex);
        	
        	String iwsp_vap_mtsp_filename = args[i].toString();
        	
        	IWSP_VAP_MTSP_Problems linkedproblem = new IWSP_VAP_MTSP_Problems(iwsp_vap_mtsp_filename);
        	IWSP parentproblem = (IWSP) linkedproblem.getLinkedProblems().get(1);
        	VAP childproblem = (VAP) linkedproblem.getLinkedProblems().get(2);
    		MTSP childproblem1 = (MTSP) linkedproblem.getLinkedProblems().get(3);
    		String p1name = parentproblem.getName();
    		String p2name = childproblem.getName();
    		String p3name = childproblem1.getName();
        	        		
    		if(((IWSPInstance)parentproblem.getData()).getnCustomers()>=1000)continue;
    		
        	LinkedProblemRunner experiment;
    		experiment = new LinkedProblemRunnerBuilder("MCRGALPBased_"+parentproblem.getName()+"_"+childproblem.getName()+"_"+ childproblem1.getName()+"_"+args2[i])
    			       .setProblemList(linkedproblem)
    			       .setOutputLinkedProblemFileName("")
    			       .setIndependentRuns(1)
    			       .build(); 
        	
        	FileOutputContext funContext = new DefaultFileOutputContext("C:/Users/akinola/OneDrive - Robert Gordon University/Documents/Datapipe/OutputData/MCRGALP_Fitness"+p1name+p2name+p3name+"/"+experiment.getOutputLinkedProblemFileName()+".txt", ",");
            FileOutputContext varContext = new DefaultFileOutputContext("C:/Users/akinola/OneDrive - Robert Gordon University/Documents/Datapipe/OutputData/MCRGALP_Variables"+p1name+p2name+p3name+"/"+experiment.getOutputLinkedProblemFileName()+".txt", ",");
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
        		
            	
    			
        		experiment = new LinkedProblemRunnerBuilder("MCRGALPBased_"+parentproblem.getName()+"_"+childproblem.getName()+"_"+ childproblem1.getName()+"_"+args2[i])
        			       .setProblemList(linkedproblem)
        			       .setOutputLinkedProblemFileName("")
        			       .setIndependentRuns(1)
        			       .build(); 
        		
        		MCRGALPs MCRGALP_Algo = (MCRGALPs)getAlgorithms(linkedproblem,pc1,mc1,pc1,distIndex,pc1,mc1);
            	
        		MCRGALP_Algo.run();
        		
        		
    			solutionSet sol2 = MCRGALP_Algo.getResult();
    			
    			/**
    			System.out.println(sol2.getSolutionSet().get(1).toString()+ " " + sol2.getSolutionSet().get(1).attributes().values()+ " "+  iwsp.inventoryPolicy((BinarySolution) sol2.getSolutionSet().get(1)) + " "+ iwsp.totalFixedCost((BinarySolution) sol2.getSolutionSet().get(1))+ " "+ iwsp.totalTransportCost((BinarySolution) sol2.getSolutionSet().get(1)));
    			System.out.println(sol2.getSolution(2));
    			System.out.println(sol2.getSolution(3));
        		**/          	
            	try {
            		long finalTime = System.currentTimeMillis();
                	
                	long compTime = finalTime-finalinitial;
                	
            		myprintObjectivesToFile(MCRGALP_Algo, funbufferedWriter, sol2, compTime, pc1,mc1);
                	myprintVariablesToFile(varbufferedWriter, sol2);
    				
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
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void myprintObjectivesToFile(MCRGALPs proAlgo, BufferedWriter bufferedWriter, solutionSet solutionpair, long computingTime, double pc1, double mc1) {
			  try {
			    if (null != solutionpair) {
			    				    	
			    	IWSP iwsp = (IWSP) proAlgo.getProblem().getLinkedProblems().get(1);
			    	VAP vap = (VAP) proAlgo.getProblem().getLinkedProblems().get(2);
			    	//Problem<?> pro = (IWSP)runner.getProblemList().getLinkedProblems().get(1);
			    	
			    	S parentsolution = (S) solutionpair.getSolution(1);
			    	S childsolution = (S) solutionpair.getSolution(2);
			    	S childsolution1 = (S) solutionpair.getSolution(3);
			    	
			    	vap.setParentsolution(childsolution1);
			    	vap.evaluate((IntegerSolution) childsolution);
			    	
			    	iwsp.setParentsolution(childsolution);
			    	iwsp.evaluate((BinarySolution) parentsolution);
			    	
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
			    	bufferedWriter.write(","+((IWSP) iwsp).inventoryPolicy((BinarySolution) parentsolution));
			    	bufferedWriter.write(","+((IWSP) iwsp).totalFixedCost((BinarySolution) parentsolution));
			    	bufferedWriter.write(","+((IWSP) iwsp).totalTransportCost((BinarySolution) parentsolution));
			    	bufferedWriter.write(","+pc1);
			    	bufferedWriter.write(","+mc1);
			    //	System.out.println(parentsolution.toString());
			    }
			      
			    } catch (IOException e) {
			      throw new RuntimeException("Error printing objectives to file: ", e);
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
	
	
}
