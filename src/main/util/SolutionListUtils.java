package main.util;

import java.util.List;

import main.solution.solution;
import main.solution.solutionPair;
import main.solution.solutionSet;
import main.util.archive.Archive;
import main.util.archive.NonDominatedSolutionListArchive;

public class SolutionListUtils {

	
	public static <S extends solutionSet> List<S> getNonDominatedSolutions(List<S> solutionList) {
	    Archive<S> nonDominatedSolutionArchive = new NonDominatedSolutionListArchive<>() ;
	    solutionList.forEach(nonDominatedSolutionArchive::add);

	    return nonDominatedSolutionArchive.getSolutionList();
	  }

}
