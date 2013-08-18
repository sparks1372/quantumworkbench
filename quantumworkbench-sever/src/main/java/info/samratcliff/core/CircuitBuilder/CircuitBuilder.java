package info.samratcliff.core.CircuitBuilder;

import info.samratcliff.core.Algorithms.QuantumAlgorithm;
import info.samratcliff.core.Circuit.Circuit;

import java.io.Serializable;

public interface CircuitBuilder extends Serializable {

    public abstract Circuit Build(QuantumAlgorithm quantumAlgorithm,
                                  int num_qubits);

    public abstract Circuit Build(QuantumAlgorithm quantumAlgorithm,
                                  int num_qubits, int[] loopvars);

}
