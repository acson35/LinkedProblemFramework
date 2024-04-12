package main.util.LPDriver;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Example.SFLP;
import Example.SFLP_SPFSP_Problems;
import Example.SPFSP;
import main.algorithm.MultiCriteriaRankingAlgorithm.MCRGALPs;
import main.linkedproblem.LinkedProblem;
import main.operator.crossover.CrossoverOperator;
import main.operator.crossover.CrossoverClasses.PMXCrossover;
import main.operator.crossover.CrossoverClasses.UniformCrossover;
import main.operator.mutation.MutationOperator;
import main.operator.mutation.MutationClasses.BitFlipMutation;
import main.operator.mutation.MutationClasses.PermutationSwapMutation;
import main.operator.selection.SelectionOperator;
import main.solution.solution;
import main.solution.solutionSet;
import main.solution.binarySolution.BinarySolution;
import main.solution.permutationSolution.IntegerPermutationSolution;
import main.util.evaluator.SolutionListEvaluator;
import main.util.evaluator.Implementation.SequentialSolutionListEvaluator;
import main.util.fileoutput.FileOutputContext;
import main.util.fileoutput.Implementation.DefaultFileOutputContext;
import main.util.model.instance.SFLPInstance.SFLPInstance;
import main.util.model.instance.SPFSPInstance;
import main.util.ranking.Criteria;

public class RunMcrgalpSflpSpfsp<S extends solution<?>> {
	

	public RunMcrgalpSflpSpfsp() {
		
	}
	
	@SuppressWarnings("rawtypes")
	public MCRGALPs getAlgorithms(LinkedProblem linkedproblem, double pc1, double mc1, double pc2, double mc2, SelectionOperator<List<solutionSet>, List<solutionSet>> selectionOperator, List<Criteria> criteria){
		
		 CrossoverOperator<BinarySolution> crossoverOperator1 = 
		    		new UniformCrossover(pc1);
		    		
		    		//UniformCrossover(0.9);
		    //HUXCrossover
		    MutationOperator<BinarySolution> mutationOperator1 =
		        new BitFlipMutation(mc1);
		    
		    CrossoverOperator<IntegerPermutationSolution> crossoverOperator2 = 
		    		new PMXCrossover(pc2);
		    MutationOperator<IntegerPermutationSolution> mutationOperator2 =
		        new PermutationSwapMutation(mc2);
		    
		    SolutionListEvaluator<solution<?>> evaluator = 
		    		new SequentialSolutionListEvaluator<solution<?>>();
			
			List<CrossoverOperator> crossoverOperators = new ArrayList<>();
			       
			crossoverOperators.add(0,crossoverOperator1);
			crossoverOperators.add(1,crossoverOperator2);
			
			
			List<MutationOperator> mutationOperators = new ArrayList<>();
			
			mutationOperators.add(0,mutationOperator1);
			mutationOperators.add(1,mutationOperator2);
			
			
			MCRGALPs sflp_spfsp = new MCRGALPs(linkedproblem, 5000, 50, 50, 50, criteria, crossoverOperators, mutationOperators, selectionOperator, evaluator);
			
			//linkedproblem.createsolution();
			
			return sflp_spfsp;	
	}
	
	@SuppressWarnings("rawtypes")
	public void MCRGALP_SFLP_SPFSP_Experiments(String method, 
			SelectionOperator<List<solutionSet>,List<solutionSet>> selectionOperator, 
			List<Criteria> criteria, 
			String[] args_sflp, 
			String[] args2_sflp, 
			String[] args_spfsp, 
			String[] args2_spfsp) throws IOException{
	
	
	 for(int i = 0; i < args_sflp.length; i++) {
		 
		 String sflp_filename = args_sflp[i];
		 
		 SFLP parentproblem = new SFLP(sflp_filename);
		 int problemsize1 = ((SFLPInstance) parentproblem.getData()).getnCustomers();
		 int nfacilities = ((SFLPInstance) parentproblem.getData()).getnFacilities();
		 
		 if(problemsize1 > 1000)continue;
		 
		 if(nfacilities > 30 )continue;
		 
		 String p1name = parentproblem.getName();
		 
		 for(int j = 0; j < args_spfsp.length; j++) {
			 
			 if(args_spfsp[j] == null)continue;
			 if(args_sflp[i] == null)continue;
			 
			 String spfsp_filename = args_spfsp[j];
			 
			 SPFSP childproblem = new SPFSP(spfsp_filename);
			 int problemsize2 = ((SPFSPInstance) childproblem.getData()).getNJobs();
			 int nfac = ((SPFSPInstance) childproblem.getData()).getNFacilities();
			 
			 
			 String p2name = childproblem.getName();
			 			
			 if(nfacilities != nfac)continue;
			 
			
			 
			 if(problemsize1==problemsize2) {
				 
				 SFLP_SPFSP_Problems linkedproblem = new SFLP_SPFSP_Problems(sflp_filename, spfsp_filename);
	        		
				 double pc1 = 0.1 + (0.9-0.1)*Math.random();;
				 double mc1 = 0.1 + (0.9-0.1)*Math.random();
				 
				 pc1 = Double.parseDouble(String.format("%.1f", pc1));
				 mc1 = Double.parseDouble(String.format("%.1f", mc1));
				 System.out.println(pc1 +" "+ mc1);
				 
					
				 LinkedProblemRunner experiment;
		    		experiment = new LinkedProblemRunnerBuilder("MCRGALP_"+ method + "_" +parentproblem.getName()+"_"+childproblem.getName()+"_"+args2_sflp[i].replace(".txt", "")+"_"+ args2_spfsp[j].replace(".txt", ""))
		    			       .setProblemList(linkedproblem)
		    			       .setOutputLinkedProblemFileName("")
		    			       .setIndependentRuns(1)
		    			       .build(); 
		        	
		        	FileOutputContext funContext = new DefaultFileOutputContext("C:/Users/akinola/OneDrive - Robert Gordon University/Documents/Datapipe/OutputData/MCRGALP_" + method +"_Fitness_"+p1name+"_"+p2name+"/"+experiment.getOutputLinkedProblemFileName()+".txt", ",");
		            FileOutputContext varContext = new DefaultFileOutputContext("C:/Users/akinola/OneDrive - Robert Gordon University/Documents/Datapipe/OutputData/MCRGALP_" + method +"_Var_"+p1name+"_"+p2name+"/"+experiment.getOutputLinkedProblemFileName()+".txt", ",");
		           
		          //MCRGALP_Topsis_Fitness_SFLP_SPFSP
		    		//MCRGALP_Topsis_Var_SFLP_SPFSP
		    		//MCRGALP_Promethee_II_Fitness_SFLP_SPFSP
		            
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
		        		
		            	
		    			
		        		experiment = new LinkedProblemRunnerBuilder("MCRGALP_"+ method + "_" +parentproblem.getName()+"_"+childproblem.getName()+"_"+args2_sflp[i]+"_"+ args2_spfsp[j])
		        			       .setProblemList(linkedproblem)
		        			       .setOutputLinkedProblemFileName("")
		        			       .setIndependentRuns(1)
		        			       .build(); 
		        		
		        		MCRGALPs MCRGALP_Algo = (MCRGALPs)getAlgorithms(linkedproblem,pc1,mc1,pc1,pc1, selectionOperator, criteria);
		            	
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
	 }
	
	
}

	public void myprintObjectivesToFile(MCRGALPs proAlgo, BufferedWriter bufferedWriter, solutionSet solutionpair, long computingTime, double pc1, double mc1) {
		  try {
		    if (null != solutionpair) {
		    				    	
		    	//IWSP iwsp = (IWSP) proAlgo.getProblem().getLinkedProblems().get(1);
		    	//VAP vap = (VAP) proAlgo.getProblem().getLinkedProblems().get(2);
		    	//Problem<?> pro = (IWSP)runner.getProblemList().getLinkedProblems().get(1);
		    	
		    	S parentsolution = (S) solutionpair.getSolution(1);
		    	S childsolution = (S) solutionpair.getSolution(2);
		    	
		    	int count = 0;
		    	if(parentsolution.problemname().equalsIgnoreCase("FLP") || parentsolution.problemname().equalsIgnoreCase("SFLP")) {
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
		    	bufferedWriter.write(","+parentsolution.getFitness()[1]);
		    	bufferedWriter.write(","+childsolution.getFitness()[0]);
		    	bufferedWriter.write(","+childsolution.getFitness()[1]);
		    	bufferedWriter.write(","+pc1);
		    	bufferedWriter.write(","+mc1);
		    //	System.out.println(parentsolution.toString());
		    }
		      
		    } catch (IOException e) {
		      throw new RuntimeException("Error printing objectives to file: ", e);
		    }
	}
	
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
	    		  
		      }
		    } catch (IOException e) {
		      throw new RuntimeException("Error writing data ", e);
		    }
	  }
	
}
