package Core.Circuit;

import Core.Problem.testcase;
import Jama.Matrix;

public interface IQuantumGate {

	public Matrix apply(Matrix start_state, testcase tc);

	public String getlabel();

	public int getTarget();

	public Matrix getUnitary_operation(testcase tc);

	public String toLatex();
}
