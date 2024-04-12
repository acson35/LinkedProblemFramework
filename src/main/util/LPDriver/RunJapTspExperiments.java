package main.util.LPDriver;

import java.io.File;
import java.io.IOException;

import Example.FLP;
import Example.FLP_PFSPproblems;
import Example.JAP;
import Example.JAP_TSP_Problems;
import Example.PFSP_Makespan;
import Example.TSP;
import main.util.model.instance.JAPInstance;
import main.util.model.instance.FLPInstance.FLPInstance;

public class RunJapTspExperiments {

		public RunJapTspExperiments() {
			
		}
		
		@SuppressWarnings("rawtypes")
		public void JapTspExperiments() throws IOException {
			
			File file = new File("C:/Users/akinola/OneDrive - Robert Gordon University/Documents/Datapipe/InputData/JAP");
	        File[] files = file.listFiles();
	        
	        File file2 = new File("C:/Users/akinola/OneDrive - Robert Gordon University/Documents/Datapipe/InputData/TSP");
	        File[] files2 = file2.listFiles();

	        for(int i = 0; i < files.length; i++) {
	        	String 	jap_filename = files[i].toString();
	        	//System.out.println(flp_filename);
	        	for(int j = 0; j < files2.length; j++) {
	        		String tsp_filename = files2[j].toString();
	        		JAP_TSP_Problems linkedproblem = new JAP_TSP_Problems(jap_filename,tsp_filename);
	        		
	        		JAP parentproblem = (JAP) linkedproblem.getLinkedProblems().get(1);
            		int problemsize1 = ((JAPInstance) parentproblem.getData()).getJobsNum();
	        		
            		TSP childproblem = (TSP) linkedproblem.getLinkedProblems().get(2);
            		int problemsize2 = childproblem.getTotalNumberOfVariables();
	        		
	        		if(problemsize1==problemsize2) {	
	        			LinkedProblemRunner experiment;
	            		experiment = new LinkedProblemRunnerBuilder("Random_"+parentproblem.getName()+"_"+childproblem.getName()+"_"+files[i].getName()+"_"+files2[j].getName())
	            			       .setProblemList(linkedproblem)
	            			       .setOutputLinkedProblemFileName("_Results")
	            			       .setIndependentRuns(10000)
	            			       .build();   
	            			
	            		ExecuteRandomProblemGeneration execute = new ExecuteRandomProblemGeneration(experiment);
	            		execute.run();
	        		}
	        	}
	        } 
		}
}
