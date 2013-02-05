package Core.Algorithms.Implementation;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

import Core.Algorithms.QuantumAlgorithm;
import Core.Algorithms.QuantumInstruction;
import Core.Algorithms.QuantumInstructionEnum;
import Core.Algorithms.exp_node;

public class basicquantumalgorithm implements QuantumAlgorithm {
	private static final long serialVersionUID = -3625148453956686365L;
	private final LinkedList<QuantumInstruction> inst_list;
	private int size = 0;
	private int val_sum = 0;

	public basicquantumalgorithm() {
		inst_list = new LinkedList<QuantumInstruction>();
	}

	@Override
	public void addInstruction(QuantumInstructionEnum inst, exp_node gate1,
			exp_node gate2, exp_node phase, QuantumAlgorithm[] subalg) {

		QuantumInstruction to_add = new basicquantuminstruction();
		switch (inst) {
		case Body:
		case Iterate:
			// case Root:
			size = size + 1;
			break;
		default:
			size = size + 1;
			if (gate1 != null) {
				val_sum += gate1.val_sum();
			}
			if (gate2 != null) {
				val_sum += gate2.val_sum();
			}
			break;
		}
		to_add.setInstruction(inst);
		to_add.setInteger1(gate1);
		to_add.setInteger2(gate2);
		to_add.setDouble1(phase);
		to_add.setSubalg(subalg);
		if (to_add.getSubalg() == null) {
			basicquantumalgorithm[] temp = new basicquantumalgorithm[0];
			to_add.setSubalg(temp);
		}
		for (int i = 0; i < to_add.getSubalg().length; i++) {
			size = size + to_add.getSubalg()[i].getSize();

			val_sum += to_add.getSubalg()[i].getValSum();
		}

		inst_list.add(to_add);
	}

	@Override
	public ListIterator<QuantumInstruction> getInstructions() {
		return inst_list.listIterator();
	}

	@Override
	public int getSize() {
		return size;
	}

	@Override
	public int getValSum() {
		return val_sum;
	}

	@Override
	public String print() {
		String to_return = "";
		String ins;
		QuantumInstruction qi;
		Iterator<QuantumInstruction> iter = getInstructions();
		while (iter.hasNext()) {
			qi = iter.next();
			ins = "";
			switch (qi.getInstruction()) {
			case Body:
				ins = "{\n";
				for (int i = 0; i < qi.getSubalg().length; i++) {
					ins = ins.concat(qi.getSubalg()[i].print());
				}
				ins = ins.concat("}\n");
				break;
			case Iterate:
			case RevIterate:
				if ((qi.getSubalg().length != 0)) {
					ins = qi.getInstruction().name() + " for "
							+ qi.getInteger1().toPrint() + " iterations {\n";
					for (int i = 0; i < qi.getSubalg().length; i++) {
						ins = ins.concat(qi.getSubalg()[i].print());
					}
					ins = ins.concat("}\n");
				}
				break;
			default:
				if (QuantumInstructionEnum.hasSecondQubit(qi.getInstruction())) {
					if (QuantumInstructionEnum.hasPhase(qi.getInstruction())) {
						ins = qi.getInstruction().name() + " on gate "
								+ qi.getInteger1().toPrint() + " and gate "
								+ qi.getInteger2().toPrint() + " with phase "
								+ qi.getDouble1().toPrint() + "\n";
					} else {
						ins = qi.getInstruction().name() + " on gate "
								+ qi.getInteger1().toPrint() + " and gate "
								+ qi.getInteger2().toPrint() + "\n";
					}
				} else {
					if (QuantumInstructionEnum.hasPhase(qi.getInstruction())) {
						ins = qi.getInstruction().name() + " on gate "
								+ qi.getInteger1().toPrint() + " with phase "
								+ qi.getDouble1().toPrint() + "\n";
					} else {
						ins = qi.getInstruction().name() + " on gate "
								+ qi.getInteger1().toPrint() + "\n";
					}
				}

			}

			to_return = to_return.concat(ins);
		}

		return to_return;
	}
}
