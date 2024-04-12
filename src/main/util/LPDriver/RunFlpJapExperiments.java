package main.util.LPDriver;

import java.io.File;
import java.io.IOException;
import Example.FLP;
import Example.FLP_JAP_Problem;
import Example.JAP;
import main.solution.solution;
import main.util.model.instance.JAPInstance;
import main.util.model.instance.FLPInstance.FLPInstance;

public class RunFlpJapExperiments<S extends solution<?>> {

	
	public RunFlpJapExperiments() {
		
	}   
   
	@SuppressWarnings("rawtypes")
	public void FlpJapExperiments() throws IOException {
		
		File file = new File("C:/Users/akinola/OneDrive - Robert Gordon University/Documents/Datapipe/InputData/FLP");
        File[] files = file.listFiles();
        
        File file2 = new File("C:/Users/akinola/OneDrive - Robert Gordon University/Documents/Datapipe/InputData/JAP");
        File[] files2 = file2.listFiles();

        for(int i = 0; i < files.length; i++) {
        	String 	flp_filename = files[i].toString();
        	//System.out.println(flp_filename);
        	for(int j = 0; j < files2.length; j++) {
        		String jap_filename = files2[j].toString();
        		FLP_JAP_Problem linkedproblem = new FLP_JAP_Problem(flp_filename,jap_filename);
        		
        		FLP parentproblem = (FLP) linkedproblem.getLinkedProblems().get(1);
        		int problemsize1 = ((FLPInstance) parentproblem.getData()).getnCustomers();
        		
        		JAP childproblem = (JAP) linkedproblem.getLinkedProblems().get(2);
        		int problemsize2 = ((JAPInstance) childproblem.getData()).getJobsNum();
        		
        		
        		
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

