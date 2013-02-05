package Core.Algorithms.Implementation;

import Core.Algorithms.QuantumAlgorithm;
import Core.Algorithms.QuantumInstruction;
import Core.Algorithms.QuantumInstructionEnum;
import Core.Algorithms.exp_node;

public class basicquantuminstruction implements QuantumInstruction {

	private static final long serialVersionUID = -7387521810547397545L;
	private QuantumInstructionEnum inst;
	private exp_node gate1;
	private exp_node gate2;
	private exp_node phase;
	private QuantumAlgorithm[] subalg;

	@Override
	public exp_node getDouble1() {
		return phase;
	}

	@Override
	public QuantumInstructionEnum getInstruction() {
		return inst;
	}

	@Override
	public exp_node getInteger1() {
		return gate1;
	}

	@Override
	public exp_node getInteger2() {
		return gate2;
	}

	@Override
	public QuantumAlgorithm[] getSubalg() {
		return subalg;
	}

	@Override
	public void setDouble1(exp_node phase) {
		this.phase = phase;
	}

	@Override
	public void setInstruction(QuantumInstructionEnum instruction) {
		inst = instruction;
	}

	@Override
	public void setInteger1(exp_node gate1) {
		this.gate1 = gate1;
	}

	@Override
	public void setInteger2(exp_node gate2) {
		this.gate2 = gate2;
	}

	@Override
	public void setSubalg(QuantumAlgorithm[] subalg) {
		this.subalg = subalg;
	}

}
