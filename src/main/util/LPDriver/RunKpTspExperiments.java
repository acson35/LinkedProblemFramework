package main.util.LPDriver;

import java.io.File;
import java.io.IOException;

import Example.FLP;
import Example.FLP_PFSPproblems;
import Example.KP;
import Example.KP_TSP_Problems;
import Example.PFSP_Makespan;
import Example.TSP;
import main.util.model.instance.KPInstance;
import main.util.model.instance.FLPInstance.FLPInstance;

public class RunKpTspExperiments {
	
	public RunKpTspExperiments() {

	}
	
	
	@SuppressWarnings("rawtypes")
	public void KpTspExperiments() throws IOException {
		File file = new File("C:/Users/akinola/OneDrive - Robert Gordon University/Documents/Datapipe/InputData/KP");
        File[] files = file.listFiles();
        
        File file2 = new File("C:/Users/akinola/OneDrive - Robert Gordon University/Documents/Datapipe/InputData/TSP");
        File[] files2 = file2.listFiles();

        for(int i = 0; i < files.length; i++) {
        	int count = 0;
        	File kpfile = new File(files[i].toString());
            File[] kpfiles = kpfile.listFiles();
        	
        	for(int j=0; j<kpfiles.length; j++) {
        		String kp_filename = kpfiles[j].toString();
        		for(int k = 0; k < files2.length; k++) {
            		String tsp_filename = files2[k].toString();
            		KP_TSP_Problems linkedproblem = new KP_TSP_Problems(kp_filename,tsp_filename);
            		
            		KP parentproblem = (KP) linkedproblem.getLinkedProblems().get(1);
            		int problemsize1 = ((KPInstance) parentproblem.getData()).getItemsNum();
            		
            		TSP childproblem = (TSP) linkedproblem.getLinkedProblems().get(2);
            		int problemsize2 = childproblem.getTotalNumberOfVariables();
            		
            		if(problemsize1==problemsize2) {	
            			LinkedProblemRunner experiment;
                		experiment = new LinkedProblemRunnerBuilder("Random_"+parentproblem.getName()+"_"+childproblem.getName()+"_"+files[i].getName()+"_"+files2[k].getName()+"_"+count)
                			       .setProblemList(linkedproblem)
                			       .setOutputLinkedProblemFileName("_Results")
                			       .setIndependentRuns(10000)
                			       .build();   
                			
                		ExecuteRandomProblemGeneration execute = new ExecuteRandomProblemGeneration(experiment);
                		execute.run();
                		count++;
            		}
            	}

        	}
       }  

	}
}
