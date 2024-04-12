package main.algorithm;

public interface AlgorithmBuilder<A extends Algorithm<?, ?>> {
	A build();
}
