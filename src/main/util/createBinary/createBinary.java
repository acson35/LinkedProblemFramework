package main.util.createBinary;

import java.util.BitSet;

@SuppressWarnings("serial")
public class createBinary extends BitSet {
	private int numberOfBits;
	
	public createBinary(int numberOfBits) {
		super(numberOfBits);
		
		this.numberOfBits = numberOfBits;
	}
	
	
	public int getNumberOfBits() {
		return numberOfBits;
	}
	
	@Override
	public String toString() {
	  String binaryresult = "" ;
	  for (int i = 0; i < numberOfBits ; i++) {
	    if (get(i)) {
	      binaryresult += "1" ;
	    }
	    else {
	      binaryresult+= "0" ;
	    }
	  }
	  return binaryresult ;
	}
}
