package main.operator;

import java.io.Serializable;

public interface Operator<Solution, R> extends Serializable {
	 R execute(Solution solution) ;
}
