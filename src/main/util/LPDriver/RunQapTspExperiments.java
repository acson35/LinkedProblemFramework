package main.util.LPDriver;

import java.io.File;
import java.io.IOException;

import Example.JAP;
import Example.JAP_TSP_Problems;
import Example.KP;
import Example.KP_TSP_Problems;
import Example.QAP;
import Example.QAP_TSP_Problem;
import Example.TSP;
import main.util.model.instance.JAPInstance;
import main.util.model.instance.KPInstance;
import main.util.model.instance.QAPInstance;

public class RunQapTspExperiments {

	public RunQapTspExperiments() {
		
	}
	
	@SuppressWarnings("rawtypes")
	public void QapTspExperiments() throws IOException {
		
		File file = new File("C:/Users/akinola/OneDrive - Robert Gordon University/Documents/Datapipe/InputData/QAP");
        File[] files = file.listFiles();
        
        File file2 = new File("C:/Users/akinola/OneDrive - Robert Gordon University/Documents/Datapipe/InputData/TSP");
        File[] files2 = file2.listFiles();

        for(int i = 0; i < files.length; i++) {
        	String 	qap_filename = files[i].toString();
        	//System.out.println(flp_filename);
        	for(int j = 0; j < files2.length; j++) {
        		String tsp_filename = files2[j].toString();
        		//System.out.println(tsp_filename);
        		QAP_TSP_Problem linkedproblem = new QAP_TSP_Problem(qap_filename,tsp_filename);
        		
        		QAP parentproblem = (QAP) linkedproblem.getLinkedProblems().get(1);
        		int problemsize1 = ((QAPInstance) parentproblem.getData()).getProblemSize();
        		
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
