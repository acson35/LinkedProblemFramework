package main.util.LPDriver;

import java.io.File;
import java.io.IOException;

import Example.JAP;
import Example.KP;
import Example.KP_JAP_Problems;
import Example.KP_PFSP_Problem;
import Example.PFSP_Makespan;
import main.util.model.instance.JAPInstance;
import main.util.model.instance.KPInstance;

public class RunKpPfspExperiments {
	
	public RunKpPfspExperiments() {
		
	}
	
	public void KpPfspExperiments() throws IOException {
		
		File file = new File("C:/Users/akinola/OneDrive - Robert Gordon University/Documents/Datapipe/InputData/KP");
        File[] files = file.listFiles();
        
        File file2 = new File("C:/Users/akinola/OneDrive - Robert Gordon University/Documents/Datapipe/InputData/PFSP");
        File[] files2 = file2.listFiles();

        for(int i = 0; i < files.length; i++) {
        	int count = 0;
        	File kpfile = new File(files[i].toString());
            File[] kpfiles = kpfile.listFiles();
        	
        	for(int j=0; j<kpfiles.length; j++) {
        		String kp_filename = kpfiles[j].toString();
        		for(int k = 0; k < files2.length; k++) {
            		String pfsp_filename = files2[k].toString();
            		KP_PFSP_Problem linkedproblem = new KP_PFSP_Problem(kp_filename,pfsp_filename);
            		
            		KP parentproblem = (KP) linkedproblem.getLinkedProblems().get(1);
            		int problemsize1 = ((KPInstance) parentproblem.getData()).getItemsNum();
            		
            		PFSP_Makespan childproblem = (PFSP_Makespan) linkedproblem.getLinkedProblems().get(2);
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
