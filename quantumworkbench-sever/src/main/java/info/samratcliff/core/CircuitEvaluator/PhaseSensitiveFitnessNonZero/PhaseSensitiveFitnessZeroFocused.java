package info.samratcliff.core.CircuitEvaluator.PhaseSensitiveFitnessNonZero;

import info.samratcliff.core.Algorithms.QuantumAlgorithm;
import info.samratcliff.core.Circuit.Circuit;
import info.samratcliff.core.CircuitEvaluator.Suitability;
import info.samratcliff.core.CircuitEvaluator.SuitabilityMeasure;
import info.samratcliff.jama.Matrix;
import info.samratcliff.predefined_states;

public class PhaseSensitiveFitnessZeroFocused implements SuitabilityMeasure {
    /**
     *
     */
    private static final long serialVersionUID = 5950330468659360530L;

    public static void main(String[] args) {
        PhaseSensitiveFitnessZeroFocused test = new PhaseSensitiveFitnessZeroFocused(
                "PPSF");

        System.out.println("2 qubits state 00, Pauli X Qubit 1");
        Matrix final_state = predefined_states.get_2q_0();
        Matrix expected_state = predefined_states.get_2q_1();
        final_state.printState();
        expected_state.printState();
        Suitability fitness = test.evaluate(predefined_states.get_2q_0(),
                final_state, expected_state, null, null);
        System.out.println("Fitness : " + fitness.getFitness());

    }

    private final String name;

    /**
     *
     */
    public PhaseSensitiveFitnessZeroFocused(String n) {
        name = n;
    }

    @Override
    public Suitability evaluate(Matrix start_state, Matrix final_state,
                                Matrix expected_state, Circuit circuit, QuantumAlgorithm algo) {

        double fit = 0.0;
        int count = 0;
        double resulting;
        for (int i = 0; i < final_state.getRowDimension(); i++) {

            resulting = Math.abs(final_state.get(i, 0).real()
                    - expected_state.get(i, 0).real())
                    + Math.abs(final_state.get(i, 0).imag()
                    - expected_state.get(i, 0).imag());

            if (Math.abs(expected_state.get(i, 0).mod()) < 0.00001) {
                fit += resulting;
            }
        }
        // int size = circuit.getSize() + algo.getSize();
        // fit = fit * 100;
        // fit = size + fit;
        return new Suitability(fit, count);
    }

    @Override
    public String getName() {
        return name;
    }

}
