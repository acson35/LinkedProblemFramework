package Example;

import java.util.Arrays;
import java.util.List;

import main.problem.binaryProblem.BinaryImplementation.AbstractBinaryProblem;
import main.solution.binarySolution.BinarySolution;
import main.util.binaryType.BinaryProblemType;
import main.util.createBinary.createBinary;
import main.util.problemName.ProblemName;

@SuppressWarnings("serial")
public class OneMax extends AbstractBinaryProblem {

	private List<Integer> nbits;
	public OneMax() {
		this(Arrays.asList(100));
	}
	
	public OneMax(List<Integer> nbits) {
		setnVariables(1);
		setnObjectives(1);
		setName("OneMax");
		this.nbits = nbits;
	}
	
	@Override
	public int getBitsFromVariable(int index) {
		if(index>nbits.size()-1) {
			return (Integer) null;
		}else {
			return getnVariableBits().get(index);
		}
	}
	
	@Override
	public void evaluate(BinarySolution solution) {
	     
	     for(int i=0; i<solution.variables().size(); i++){
	    	 int fitness=0;
	    	 createBinary bits = solution.variables().get(i);
	    	 for(int j=0; j<bits.getNumberOfBits(); j++) {
	    		 if(bits.get(j))
		    		 fitness++;
	    	 } 
	    	 solution.getFitness()[0]=fitness;
	     }   
	}
	
	@Override
	public BinarySolution createsolution() {
		 return new BinarySolution(getnVariableBits(), getName(), getData());
   }

	@Override
	public List<Integer> getnVariableBits() {
		return nbits;
	}

	@Override
	public void evaluateConstraint(BinarySolution solution) {
	}
	
}



