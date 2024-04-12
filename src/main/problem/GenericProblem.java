package main.problem;

import java.io.Serializable;

public interface GenericProblem<S> extends Serializable{
	S createsolution();
}
