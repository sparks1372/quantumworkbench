package info.samratcliff.core.CircuitEvaluator.SimpleZeroFocusFitness;

import info.samratcliff.core.Algorithms.QuantumAlgorithm;
import info.samratcliff.core.Circuit.Circuit;
import info.samratcliff.core.CircuitEvaluator.Suitability;
import info.samratcliff.core.CircuitEvaluator.SuitabilityMeasure;
import info.samratcliff.jama.Matrix;
import info.samratcliff.predefined_states;
import info.samratcliff.utils.Complex;

public class SimpleZeroFocusFitness implements SuitabilityMeasure {
    /**
     *
     */
    private static final long serialVersionUID = -3476106261470140520L;

    public static void main(String[] args) {
        SimpleZeroFocusFitness test = new SimpleZeroFocusFitness(
                "Simple Fitness");

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
    public SimpleZeroFocusFitness(String n) {
        name = n;
    }

    @Override
    public Suitability evaluate(Matrix start_state, Matrix final_state,
                                Matrix expected_state, Circuit circuit, QuantumAlgorithm algo) {
        double fit = 0.0;
        int count = 0;
        for (int i = 0; i < final_state.getRowDimension(); i++) {

            Complex resulting = final_state.get(i, 0).minus(
                    expected_state.get(i, 0));
            Complex resulting_phase_flip = final_state.get(i, 0).minus(
                    expected_state.get(i, 0).times(new Complex(-1, 0)));
            resulting = resulting.mod() < resulting_phase_flip.mod() ? resulting
                    : resulting_phase_flip;

            if (Math.abs(expected_state.get(i, 0).mod()) < 0.00001) {
                fit += resulting.mod();
                // } else if ((Math.abs(expected_state.get(i, 0).mod()
                // - start_state.get(i, 0).mod()) < 0.00001)) {
                // count++;
            }
        }

        return new Suitability(fit /** 256 + algo.getValSum() */
                , count);
    }

    @Override
    public String getName() {
        return name;
    }

}
