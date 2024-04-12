package Example;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import main.problem.binaryProblem.BinaryImplementation.AbstractBinaryProblem;
import main.solution.solution;
import main.solution.binarySolution.BinarySolution;
import main.util.binaryType.BinaryProblemType;
import main.util.createBinary.createBinary;
import main.util.model.instance.KPInstance;
import main.util.problemName.ProblemName;

@SuppressWarnings("serial")
public class KP extends AbstractBinaryProblem {

	BinarySolution updatedSolution;
	private List<Integer> nItems;
	solution<?> parentsolution;
	
	public KP(String datafilename) throws IOException {
		setData(readProblem(datafilename));
		setnVariables(1);
		setnObjectives(1);
		setnConstraints(1);
		setName("KP");
		setFilepath(datafilename);
		nItems = new ArrayList<>();
		this.nItems.add(((KPInstance) getData()).getItemsNum());
		
	}
	
	public KP(String datafilename, solution<?> solution) throws IOException {
		this(datafilename);
		setParentsolution(solution);
		//this.updatedSolution = updatesolution(solution);
	}
	
//	@Override
//	public BinarySolution getUpdatedSolution() {
//		return this.updatedSolution;
//	}
	
	@Override
	public int getBitsFromVariable(int index) {
		if(index>nItems.size()-1) {
			return (Integer) null;
		}else {
			return getnVariableBits().get(index);
		}
	}
	
	@Override
	public void evaluate(BinarySolution solution) {
		
		int[] p = ((KPInstance) getData()).getItemsValue();
		for(int i=0; i<solution.variables().size(); i++) {
			double fitness=0;
			createBinary bits = solution.variables().get(i);
			for(int j=0; j<bits.length(); j++) {
	    		 if(bits.get(j)) {
	    			 fitness+= p[j];
		    		// solution.attributes().put(j, p[j]);
	    		 }
	    	} 
	    	solution.getFitness()[0]=fitness;
		}
		evaluateConstraint(solution);
	}
	
	@Override
	public void evaluateConstraint(BinarySolution solution) {
		
		
		int[] w = ((KPInstance) getData()).getItemsWeight();
		int c = ((KPInstance) getData()).getKnapsackWeight();
		
		double unfitness=0;
		double pickedItemsWeightsum=0;
		for(int i=0; i<solution.variables().size(); i++) {
			createBinary bits = solution.variables().get(i);
			for(int j=0; j<bits.length(); j++) {
	    		 if(bits.get(j))
	    			 pickedItemsWeightsum+= w[j];
	    	} 
		}
		unfitness = Math.max(0, pickedItemsWeightsum - c);
		solution.getConstraints()[0]=unfitness;
	}
	
	
	public KPInstance loadKP(String datafilename) throws IOException {
		
		InputStream in = new FileInputStream(datafilename);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        
        br.readLine();
		String[] tokens = br.readLine().trim().split(" ");
		//if(tokens.length==0)
		
		int itemsNum = Integer.parseInt(tokens[0]);
		
		tokens = br.readLine().trim().split(" ");
		
		int knapsackWeight = Integer.parseInt(tokens[0]);

		br.readLine();
		
		int[] itemsValue = new int[itemsNum];
		int[] itemsWeight = new int[itemsNum];
		
		int elementsRead = 0;
		while (elementsRead < itemsNum) {
			tokens = br.readLine().trim().split(" ");
			if(tokens.length==0)continue;
			itemsValue[elementsRead] = Integer.parseInt(tokens[0]);
			itemsWeight[elementsRead] = Integer.parseInt(tokens[1]);
			
			elementsRead++;
		}
		
		

		KPInstance ins = new KPInstance(datafilename, itemsNum, knapsackWeight, itemsValue, itemsWeight);
		return ins;

	}
	
	public KPInstance readProblem(String datafilename) throws IOException {
		if(datafilename==null) {
			System.out.print("Filepath is not found");
			return null;
		}else {
			return loadKP(datafilename);
		}
	}
	
//	@Override
//	public BinarySolution createsolution() {
//		 return new BinarySolution(getnVariableBits(), getnConstraints(), getName());
 //  }

	@Override
	public List<Integer> getnVariableBits() {
		return this.nItems;
	}

}
