package main.algorithm;

public interface AlgorithmBuilderLP<A extends AlgorithmLPs<?, ?>> {
	A build();
}
