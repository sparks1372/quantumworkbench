package info.samratcliff.core.CircuitEvaluator;

import info.samratcliff.core.Algorithms.QuantumAlgorithm;
import info.samratcliff.core.Circuit.Circuit;
import info.samratcliff.jama.Matrix;

import java.io.Serializable;

public interface SuitabilityMeasure extends Serializable {

    /**
     * @param start_state
     * @param final_state
     * @param expected_state
     * @param circuit
     * @param algo
     * @return
     */
    public abstract Suitability evaluate(Matrix start_state, Matrix final_state,
                                         Matrix expected_state, Circuit circuit, QuantumAlgorithm algo);

    /**
     * @return Returns the name.
     * @uml.property name="Name" readOnly="true"
     */
    public String getName();

}
