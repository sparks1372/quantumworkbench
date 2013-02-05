package Core.CircuitEvaluator.SimpleFitness;

import Core.Algorithms.QuantumAlgorithm;
import Core.Circuit.Circuit;
import Core.CircuitEvaluator.Suitability;
import Core.CircuitEvaluator.SuitabilityMeasure;
import Jama.Matrix;
import Testing.predefined_states;

public class SimpleFitness implements SuitabilityMeasure {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1531048769612551079L;
	private static final double	ACCURACY			= 0.00000001;

	public static void main(String[] args) {
		SimpleFitness test = new SimpleFitness("Simple Fitness");

		System.out.println("2 qubits state 00, Pauli X Qubit 1");
		Matrix final_state = predefined_states.get_2q_0();
		Matrix expected_state = predefined_states.get_2q_1();
		final_state.printState();
		expected_state.printState();
		Suitability fitness = test.evaluate(predefined_states.get_2q_0(),
				final_state, expected_state, null, null);
		System.out.println("Fitness : " + fitness.getFitness());

	}

	private final String	name;

	/**
	 * 
	 */
	public SimpleFitness(String n) {
		name = n;
	}

	@Override
	public Suitability evaluate(Matrix start_state, Matrix final_state,
			Matrix expected_state, Circuit circuit, QuantumAlgorithm algo) {
		double fit = 0.0;
		int count = 0;
		for (int i = 0; i < final_state.getRowDimension(); i++) {

			double resulting = Math.abs(final_state.get(i, 0).mod()
					- expected_state.get(i, 0).mod());

			if (resulting > ACCURACY) {
				fit += resulting;
			} else {
				count++;
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
