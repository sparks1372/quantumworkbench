package Core.Circuit.GateImplementations;

import Core.Circuit.IQuantumGate;
import Core.CircuitEvolution.multiqubitquantumgate;
import Core.Problem.testcase;
import Jama.Matrix;
import Testing.predefined_states;
import Utils.Complex;
import Utils.MatrixUtils;

public class ControlledU_Gate implements multiqubitquantumgate {
	public static void main(String[] args) {
		ControlledU_Gate cu = new ControlledU_Gate(new Pauli_X(1), 2, 1);
		cu.apply(predefined_states.get_2q_3(), null);
	}

	private final String labelStr;

	private final int ctrl;
	private final int targ;
	private final String latex;
	private IQuantumGate innergate = null;
	private Matrix unitary;
	private final Matrix controlMat = Matrix.identity(2, 2);
	private final Matrix nonControlMat = Matrix.identity(2, 2);

	public ControlledU_Gate(IQuantumGate gate, int t, int c) {
		if ((t != c) && (gate instanceof ControlledU_Gate)) {
			if ((((ControlledU_Gate) gate).getSecondQubit() != t)
					&& (((ControlledU_Gate) gate).getSecondQubit() != c)) {
				labelStr = "C " + ((ControlledU_Gate) gate).getSecondQubit()
						+ " " + gate.getlabel();
				latex = gate.toLatex().substring(0,
						gate.toLatex().indexOf("}&"))
						+ " C "
						+ ((ControlledU_Gate) gate).getSecondQubit()
						+ "}&";
			} else if ((((ControlledU_Gate) gate).getTarget() != t)
					&& (((ControlledU_Gate) gate).getTarget() != c)) {
				labelStr = "T " + ((ControlledU_Gate) gate).getTarget() + " "
						+ gate.getlabel();
				latex = gate.toLatex().substring(0,
						gate.toLatex().indexOf("}&"))
						+ " T " + ((ControlledU_Gate) gate).getTarget() + "}&";
			} else {
				labelStr = gate.getlabel();
				latex = gate.toLatex();
			}
		} else {
			labelStr = gate.getlabel();
			latex = gate.toLatex();
		}
		targ = t;
		ctrl = c;
		controlMat.set(0, 0, new Complex(0, 0));
		nonControlMat.set(1, 1, new Complex(0, 0));
		if (gate instanceof Custom_Gate) {
			innergate = gate;
		} else if (gate instanceof ControlledU_Gate) {
			innergate = gate;
		} else {
			Matrix gateU = gate.getUnitary_operation(null);
			setUnitary(gateU);
		}
	}

	@Override
	public Matrix apply(Matrix start_state, testcase tc) {
		double qubits = Math.log(start_state.getRowDimension()) / Math.log(2);
		double gatesize;
		double padding;
		double upperPadding;
		double lowerPadding;
		if (innergate == null) {
			gatesize = Math.log(unitary.getRowDimension()) / Math.log(2);
			padding = qubits - gatesize;
			upperPadding = ctrl < targ ? qubits - targ : qubits - ctrl;
			lowerPadding = padding - upperPadding;

			Matrix operation = unitary;
			operation = MatrixUtils.tensor_prod(
					operation,
					Matrix.identity((int) Math.pow(2, lowerPadding),
							(int) Math.pow(2, lowerPadding)));
			operation = MatrixUtils.tensor_prod(Matrix.identity(
					(int) (Math.pow(2, upperPadding)),
					(int) (Math.pow(2, upperPadding))), operation);

			try {
				return operation.times(start_state);
			} catch (IllegalArgumentException e) {
				System.out.println("qubits = " + qubits);
				System.out.println("gatesize = " + gatesize);
				System.out.println("ctrl = " + ctrl);
				System.out.println("targ = " + targ);
				System.out.println("padding = " + padding);
				System.out.println("upperPadding = " + upperPadding);
				System.out.println("lowerPadding = " + lowerPadding);
				System.out.println("unitary.getRowDimension() = "
						+ unitary.getRowDimension());
				System.out.println("unitary.getColumnDimension() = "
						+ unitary.getColumnDimension());
				System.out.println("start_state.getRowDimension() = "
						+ start_state.getRowDimension());
				System.out.println("start_state.getColumnDimension() = "
						+ start_state.getColumnDimension());
				unitary.printState();
				e.printStackTrace();
				return operation.times(start_state);
			}
		} else {
			Matrix gateU = innergate.getUnitary_operation(tc);
			double innergatesize = Math.log(gateU.getRowDimension())
					/ Math.log(2);
			if ((ctrl > targ) && (ctrl <= (targ + innergatesize))) {
				return innergate.apply(start_state, tc);
			} else {
				setUnitary(gateU);
				gatesize = Math.log(unitary.getRowDimension()) / Math.log(2);
				if (gatesize + (targ < ctrl ? targ : ctrl) - 1 > qubits) {
					return start_state;
				}

				padding = qubits - gatesize;
				upperPadding = ctrl < targ ? qubits
						- (targ + innergatesize - 1) : qubits - ctrl;
				lowerPadding = padding - upperPadding;

				Matrix operation = unitary;
				operation = MatrixUtils.tensor_prod(operation, Matrix.identity(
						(int) Math.pow(2, lowerPadding),
						(int) Math.pow(2, lowerPadding)));
				operation = MatrixUtils.tensor_prod(Matrix.identity(
						(int) (Math.pow(2, upperPadding)),
						(int) (Math.pow(2, upperPadding))), operation);

				try {
					return operation.times(start_state);
				} catch (IllegalArgumentException e) {
					System.out.println("qubits = " + qubits);
					System.out.println("gatesize = " + gatesize);
					System.out.println("innergatesize = " + innergatesize);
					System.out.println("ctrl = " + ctrl);
					System.out.println("targ = " + targ);
					System.out.println("padding = " + padding);
					System.out.println("upperPadding = " + upperPadding);
					System.out.println("lowerPadding = " + lowerPadding);
					System.out.println("unitary.getRowDimension() = "
							+ unitary.getRowDimension());
					System.out.println("unitary.getColumnDimension() = "
							+ unitary.getColumnDimension());
					System.out.println("start_state.getRowDimension() = "
							+ start_state.getRowDimension());
					System.out.println("start_state.getColumnDimension() = "
							+ start_state.getColumnDimension());
					unitary.printState();
					e.printStackTrace();
					return operation.times(start_state);
				}
			}
		}
	}

	@Override
	public String getlabel() {
		return labelStr;
	}

	@Override
	public int getSecondQubit() {
		return ctrl;
	}

	@Override
	public int getTarget() {
		return targ;
	}

	@Override
	public Matrix getUnitary_operation(testcase tc) {
		return unitary;
	}

	private void setUnitary(Matrix gateU) {
		Matrix spacing = Matrix.identity(1, 1);
		double gateSize = Math.log(gateU.getRowDimension()) / Math.log(2);

		Matrix nonControlUnitary;
		Matrix controlUnitary;
		if (ctrl > targ) {

			for (int i = 0; i < ctrl - (targ + gateSize); i++) {
				spacing = MatrixUtils.tensor_prod(spacing,
						Matrix.identity(2, 2));
			}
			nonControlUnitary = MatrixUtils.tensor_prod(
					nonControlMat,
					MatrixUtils.tensor_prod(
							spacing,
							Matrix.identity(gateU.getRowDimension(),
									gateU.getRowDimension())));
			controlUnitary = MatrixUtils.tensor_prod(controlMat,
					MatrixUtils.tensor_prod(spacing, gateU));
		} else {
			for (int i = 1; i < targ - ctrl; i++) {
				spacing = MatrixUtils.tensor_prod(spacing,
						Matrix.identity(2, 2));
			}
			nonControlUnitary = MatrixUtils.tensor_prod(
					Matrix.identity(gateU.getRowDimension(),
							gateU.getRowDimension()),
					MatrixUtils.tensor_prod(spacing, nonControlMat));
			controlUnitary = MatrixUtils.tensor_prod(gateU,
					MatrixUtils.tensor_prod(spacing, controlMat));
		}
		if ((nonControlUnitary.getRowDimension() != controlUnitary
				.getRowDimension())
				|| (nonControlUnitary.getColumnDimension() != controlUnitary
						.getColumnDimension())) {
			System.out.println("targ = " + targ);
			System.out.println("ctrl = " + ctrl);
			System.out.println("gateSize = " + gateSize);
			System.out.println("nonControlUnitary.getRowDimension() = "
					+ nonControlUnitary.getRowDimension());
			System.out.println("controlUnitary.getRowDimension() = "
					+ controlUnitary.getRowDimension());
			System.out.println("nonControlUnitary.getColumnDimension() = "
					+ nonControlUnitary.getColumnDimension());
			System.out.println("controlUnitary.getColumnDimension() = "
					+ controlUnitary.getColumnDimension());
			nonControlUnitary.printState();
			controlUnitary.printState();
		}

		unitary = nonControlUnitary.plus(controlUnitary);

	}

	@Override
	public String toLatex() {
		if (innergate != null) {
			return latex;
		} else {
			return toLatex(false);
		}
	}

	public String toLatex(boolean upward) {
		if (upward) {
			return latex.substring(0, latex.indexOf('&')).concat(" \\qwx &");
		} else {
			return latex;
		}

	}
}
