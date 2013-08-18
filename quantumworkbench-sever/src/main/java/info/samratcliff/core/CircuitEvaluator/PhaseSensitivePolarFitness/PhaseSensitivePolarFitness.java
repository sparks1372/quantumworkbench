package info.samratcliff.core.CircuitEvaluator.PhaseSensitivePolarFitness;

import info.samratcliff.core.Algorithms.QuantumAlgorithm;
import info.samratcliff.core.Circuit.Circuit;
import info.samratcliff.core.CircuitEvaluator.Suitability;
import info.samratcliff.core.CircuitEvaluator.SuitabilityMeasure;
import info.samratcliff.jama.Matrix;

public abstract class PhaseSensitivePolarFitness implements SuitabilityMeasure {
    /**
     *
     */
    private static final long serialVersionUID = -2436904514720320119L;
    protected double alpha;

    // public static void main(String[] args) {
    // PhaseSensitivePolarFitness test = new PhaseSensitivePolarFitness("PPSF");
    //
    // System.out.println("2 qubits state 00, Pauli X Qubit 1");
    // Matrix final_state = predefined_states.get_2q_0();
    // Matrix expected_state = predefined_states.get_2q_1();
    // final_state.print_state(0, 0);
    // expected_state.print_state(0, 0);
    // Fitness fitness = test.evaluate(predefined_states.get_2q_0(),
    // final_state, expected_state, null, null);
    // System.out.println("Fitness : " + fitness.getFitness());
    //
    // }

    private final String name;

    /**
     *
     */
    public PhaseSensitivePolarFitness(String n) {
        name = n;
    }

    @Override
    public Suitability evaluate(Matrix start_state, Matrix final_state,
                                Matrix expected_state, Circuit circuit, QuantumAlgorithm algo) {

        double fit = 0.0;
        int count = 0;
        double resulting;
        for (int i = 0; i < final_state.getRowDimension(); i++) {

            resulting = (alpha * Math.abs(final_state.get(i, 0).mod()
                    - expected_state.get(i, 0).mod()))
                    + Math.abs(final_state.get(i, 0).arg()
                    - expected_state.get(i, 0).arg());

            if (resulting > 0.0000001) {
                fit += resulting;
            } else {
                count++;
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
