package info.samratcliff.core.CircuitBuilder.Implementation;

import com.google.inject.Inject;
import info.samratcliff.core.Algorithms.QuantumAlgorithm;
import info.samratcliff.core.Algorithms.QuantumInstruction;
import info.samratcliff.core.Algorithms.QuantumInstructionEnum;
import info.samratcliff.core.Circuit.Circuit;
import info.samratcliff.core.Circuit.GateImplementations.*;
import info.samratcliff.core.Circuit.Implementation.SimpleCircuit;
import info.samratcliff.core.CircuitBuilder.CircuitBuilder;
import info.samratcliff.core.CircuitEvolution.QPace4.terminal.Variables.SystemSize;

import java.security.SecureRandom;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class SimpleQuantumCircuitBuilder implements CircuitBuilder {
    /**
     *
     */
    private static final long serialVersionUID = 5642773511675685318L;
    private final int Builder_ID;

    @Inject
    public SimpleQuantumCircuitBuilder() {
        SecureRandom rand = new SecureRandom();
        Builder_ID = rand.nextInt();
    }

    @Override
    public Circuit Build(QuantumAlgorithm quantumAlgorithm, int num_qubits) {
        return Build(quantumAlgorithm, num_qubits, new int[0]);
    }

    @Override
    public Circuit Build(QuantumAlgorithm quantumAlgorithm, int num_qubits,
                         int[] loopvars) {
        QuantumInstructionEnum lastInstruction;
        int lastInt1;
        int lastInt2;
        double lastDouble;
        lastInstruction = null;
        lastInt1 = 0;
        lastInt2 = 0;
        lastDouble = 0.0;
        QuantumAlgorithm[] qcarray;
        Circuit to_return = new SimpleCircuit();
        if (quantumAlgorithm == null) {
            return to_return;
        }
        Circuit temp = null;
        SimpleQuantumCircuitBuilder internal_builder = new SimpleQuantumCircuitBuilder();
        int Int1 = 0;
        int Int2 = 0;
        double Double1 = 0.0;
        int[] new_itervals;

        ListIterator<QuantumInstruction> iter = quantumAlgorithm
                .getInstructions();
        QuantumInstruction next_instruction;
        while (iter.hasNext()) {
            next_instruction = iter.next();
            if (next_instruction.getInteger1() != null) {
                Int1 = Math.round((float) next_instruction.getInteger1()
                        .evaluate(num_qubits, loopvars));
                // if (loopvars.length != 0) {
                Int1 = (Int1 < 0) ? Math.abs(Int1) : Int1;
                // }
                Int1 = (Int1 == SystemSize.SYSTEM_SIZE_FLAG) ? num_qubits
                        : Int1;
            }
            if (next_instruction.getInteger2() != null) {
                Int2 = Math.round((float) next_instruction.getInteger2()
                        .evaluate(num_qubits, loopvars));
                Int2 = (Int1 < 0) ? Math.abs(Int2) : Int2;
                // if (loopvars.length != 0) {
                // Int2 = (Int2 < 0) ? loopvars[Math.abs(Int2)
                // % loopvars.length] : Int2;
                // }
                Int2 = (Int2 == SystemSize.SYSTEM_SIZE_FLAG) ? num_qubits
                        : Int2;
            }
            if (next_instruction.getDouble1() != null) {
                Double1 = next_instruction.getDouble1().evaluate(num_qubits,
                        loopvars);
                if (next_instruction.getInstruction() != QuantumInstructionEnum.Create_CCX) {
                    Double1 = Double1 % (2 * Math.PI);
                }
            }

            // If the requested Qubit number is greater than the number of
            // Qubits available then set it to the System Size, final Qubit

            // System.out.println("Int1 = " + Int1);
            // System.out.println("Int2 = " + Int2);
            boolean add = true;
            try {
                if (lastInstruction != null) {
                    if (lastInstruction == next_instruction.getInstruction()) {
                        if (QuantumInstructionEnum
                                .isSelfReversing(next_instruction
                                        .getInstruction())) {
                            if (Int1 == lastInt1) {
                                if (!QuantumInstructionEnum
                                        .hasSecondQubit(next_instruction
                                                .getInstruction())
                                        || (Int2 == lastInt2)) {
                                    if (!QuantumInstructionEnum
                                            .hasPhase(next_instruction
                                                    .getInstruction())
                                            || (Math.abs(Double1 - lastDouble) < 0.00001)) {
                                        try {
                                            to_return = to_return
                                                    .removeLastGate();
                                            add = false;
                                            lastInstruction = null;
                                            lastInt1 = 0;
                                            lastInt2 = 0;
                                            lastDouble = 0.0;
                                        } catch (NoSuchElementException e) {
                                            e.printStackTrace();
                                            System.out
                                                    .println("lastInstruction = "
                                                            + lastInstruction);
                                            System.out.println("lastInt1 = "
                                                    + lastInt1);
                                            System.out.println("lastInt2 = "
                                                    + lastInt2);
                                            System.out.println("lastDouble1 = "
                                                    + lastDouble);
                                        }
                                    }

                                }
                            }
                        }
                    }
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
                if (lastInstruction != null) {
                    System.out.println("lastInstruction = " + lastInstruction);
                } else {
                    System.out.println("lastInstruction = null");
                }
                System.out.println("lastInt1 = " + lastInt1);
                System.out.println("lastInt2 = " + lastInt2);
                System.out.println("lastDouble1 = " + lastDouble);
            }
            if (add && !(Int1 > num_qubits) && !(Int1 < 1)) {
                switch (next_instruction.getInstruction()) {
                    case Create_H:
                        to_return = to_return.addGate(Builder_ID,
                                new Hadamard_Gate(Int1));
                        lastInstruction = next_instruction.getInstruction();
                        lastInt1 = Int1;
                        break;
                    case Create_X:
                        to_return = to_return
                                .addGate(Builder_ID, new Pauli_X(Int1));
                        lastInstruction = next_instruction.getInstruction();
                        lastInt1 = Int1;
                        break;
                    case Create_Y:
                        to_return = to_return
                                .addGate(Builder_ID, new Pauli_Y(Int1));
                        lastInstruction = next_instruction.getInstruction();
                        lastInt1 = Int1;
                        break;
                    case Create_Z:
                        to_return = to_return
                                .addGate(Builder_ID, new Pauli_Z(Int1));
                        lastInstruction = next_instruction.getInstruction();
                        lastInt1 = Int1;
                        break;
                    case Create_R:
                        to_return = to_return.addGate(Builder_ID, new Phase_Gate(
                                Int1, Double1));
                        lastInstruction = next_instruction.getInstruction();
                        lastInt1 = Int1;
                        lastDouble = Double1;
                        break;
                    case Create_RX:
                        to_return = to_return.addGate(Builder_ID, new RX_Gate(Int1,
                                Double1));
                        lastInstruction = next_instruction.getInstruction();
                        lastInt1 = Int1;
                        lastDouble = Double1;
                        break;
                    case Create_RY:
                        to_return = to_return.addGate(Builder_ID, new RY_Gate(Int1,
                                Double1));
                        lastInstruction = next_instruction.getInstruction();
                        lastInt1 = Int1;
                        lastDouble = Double1;
                        break;
                    case Create_RZ:
                        to_return = to_return.addGate(Builder_ID, new RZ_Gate(Int1,
                                Double1));
                        lastInstruction = next_instruction.getInstruction();
                        lastInt1 = Int1;
                        lastDouble = Double1;
                        break;
                    case Create_SWAP:
                        if ((Int1 != Int2) && (Int2 <= num_qubits) && (Int2 >= 1)) {
                            to_return = to_return.addGate(Builder_ID,
                                    new Swap_Gate(Int1, Int2));
                            lastInstruction = next_instruction.getInstruction();
                            lastInt1 = Int1;
                            lastInt2 = Int2;
                            lastDouble = Double1;
                        }
                        break;
                    case Create_V:
                        to_return = to_return.addGate(Builder_ID, new V_Gate(Int1));
                        lastInstruction = next_instruction.getInstruction();
                        lastInt1 = Int1;
                        break;
                    case Create_W:
                        to_return = to_return.addGate(Builder_ID, new W_Gate(Int1));
                        lastInstruction = next_instruction.getInstruction();
                        lastInt1 = Int1;
                        break;
                    case Create_Zero:
                        to_return = to_return.addGate(Builder_ID, new Zero_Gate(
                                Int1));
                        lastInstruction = next_instruction.getInstruction();
                        lastInt1 = Int1;
                        break;
                    case Create_Custom1:
                        to_return = to_return.addGate(Builder_ID, new Custom_Gate(
                                Int1, 0));
                        lastInstruction = next_instruction.getInstruction();
                        lastInt1 = Int1;
                        break;
                    case Create_Custom2:
                        to_return = to_return.addGate(Builder_ID, new Custom_Gate(
                                Int1, 1));
                        lastInstruction = next_instruction.getInstruction();
                        lastInt1 = Int1;
                        break;
                    case Create_Custom3:
                        to_return = to_return.addGate(Builder_ID, new Custom_Gate(
                                Int1, 2));
                        lastInstruction = next_instruction.getInstruction();
                        lastInt1 = Int1;
                        break;
                    case Create_CH:
                        if ((Int1 != Int2) && (Int2 <= num_qubits) && (Int2 >= 1)) {
                            to_return = to_return.addGate(Builder_ID,
                                    new ControlledU_Gate((new Hadamard_Gate(1)),
                                            Int1, Int2));
                            lastInstruction = next_instruction.getInstruction();
                            lastInt1 = Int1;
                            lastInt2 = Int2;
                        }
                        break;
                    case Create_CX:
                        if ((Int1 != Int2) && (Int2 <= num_qubits) && (Int2 >= 1)) {
                            to_return = to_return.addGate(Builder_ID,
                                    new ControlledU_Gate((new Pauli_X(1)), Int1,
                                            Int2));
                            lastInstruction = next_instruction.getInstruction();
                            lastInt1 = Int1;
                            lastInt2 = Int2;
                        }
                        break;
                    case Create_CCX:
                        if ((Int1 != Int2) && (Int1 != (int) Math.round(Double1))
                                && (Int2 != (int) Math.round(Double1))
                                && (Int2 <= num_qubits) && (Int2 >= 1)
                                && (Double1 <= num_qubits) && (Double1 >= 1)) {
                            if (Math.abs(Int2 - Int1) < Math.abs((int) Math
                                    .round(Double1) - Int1)) {

                                int secondTarg = Math.abs(Int2
                                        - (int) Math.round(Double1)) < Math
                                        .abs(Int1 - (int) Math.round(Double1)) ? Int2
                                        : Int1;
                                ControlledU_Gate innerGate = new ControlledU_Gate(
                                        (new Pauli_X(1)), Int1, Int2);

                                to_return = to_return.addGate(Builder_ID,
                                        new ControlledU_Gate(innerGate, secondTarg,
                                                (int) Math.round(Double1)));
                                lastInstruction = next_instruction.getInstruction();
                                lastInt1 = Int1;
                                lastInt2 = Int2;
                                lastDouble = Double1;
                            } else {

                                int secondTarg = Math.abs((int) Math.round(Double1)
                                        - Int2) < Math.abs(Int1 - Int2) ? (int) Math
                                        .round(Double1) : Int1;

                                ControlledU_Gate innerGate = new ControlledU_Gate(
                                        (new Pauli_X(1)), Int1,
                                        (int) Math.round(Double1));

                                to_return = to_return.addGate(Builder_ID,
                                        new ControlledU_Gate(innerGate, secondTarg,
                                                Int2));
                                lastInstruction = next_instruction.getInstruction();
                                lastInt1 = Int1;
                                lastInt2 = Int2;
                                lastDouble = Double1;
                            }
                        }
                        break;
                    case Create_CY:
                        if ((Int1 != Int2) && (Int2 <= num_qubits) && (Int2 >= 1)) {
                            to_return = to_return.addGate(Builder_ID,
                                    new ControlledU_Gate((new Pauli_Y(1)), Int1,
                                            Int2));
                            lastInstruction = next_instruction.getInstruction();
                            lastInt1 = Int1;
                            lastInt2 = Int2;
                        }
                        break;
                    case Create_CZ:
                        if ((Int1 != Int2) && (Int2 <= num_qubits) && (Int2 >= 1)) {
                            to_return = to_return.addGate(Builder_ID,
                                    new ControlledU_Gate((new Pauli_Z(1)), Int1,
                                            Int2));
                            lastInstruction = next_instruction.getInstruction();
                            lastInt1 = Int1;
                            lastInt2 = Int2;
                        }
                        break;
                    case Create_CR:
                        if ((Int1 != Int2) && (Int2 <= num_qubits) && (Int2 >= 1)) {
                            to_return = to_return.addGate(Builder_ID,
                                    new ControlledU_Gate(
                                            (new Phase_Gate(1, Double1)), Int1,
                                            Int2));
                            lastInstruction = next_instruction.getInstruction();
                            lastInt1 = Int1;
                            lastInt2 = Int2;
                            lastDouble = Double1;
                        }
                        break;
                    case Create_CRX:
                        if ((Int1 != Int2) && (Int2 <= num_qubits) && (Int2 >= 1)) {
                            to_return = to_return.addGate(Builder_ID,
                                    new ControlledU_Gate((new RX_Gate(1, Double1)),
                                            Int1, Int2));
                            lastInstruction = next_instruction.getInstruction();
                            lastInt1 = Int1;
                            lastInt2 = Int2;
                            lastDouble = Double1;
                        }
                        break;
                    case Create_CRY:
                        if ((Int1 != Int2) && (Int2 <= num_qubits) && (Int2 >= 1)) {
                            to_return = to_return.addGate(Builder_ID,
                                    new ControlledU_Gate((new RY_Gate(1, Double1)),
                                            Int1, Int2));
                            lastInstruction = next_instruction.getInstruction();
                            lastInt1 = Int1;
                            lastInt2 = Int2;
                            lastDouble = Double1;
                        }
                        break;
                    case Create_CRZ:
                        if ((Int1 != Int2) && (Int2 <= num_qubits) && (Int2 >= 1)) {
                            to_return = to_return.addGate(Builder_ID,
                                    new ControlledU_Gate((new RZ_Gate(1, Double1)),
                                            Int1, Int2));
                            lastInstruction = next_instruction.getInstruction();
                            lastInt1 = Int1;
                            lastInt2 = Int2;
                            lastDouble = Double1;
                        }
                        break;
                    case Create_CV:
                        if ((Int1 != Int2) && (Int2 <= num_qubits) && (Int2 >= 1)) {
                            to_return = to_return.addGate(Builder_ID,
                                    new ControlledU_Gate((new V_Gate(1)), Int1,
                                            Int2));
                            lastInstruction = next_instruction.getInstruction();
                            lastInt1 = Int1;
                            lastInt2 = Int2;
                        }
                        break;
                    case Create_CW:
                        if ((Int1 != Int2) && (Int2 <= num_qubits) && (Int2 >= 1)) {
                            to_return = to_return.addGate(Builder_ID,
                                    new ControlledU_Gate((new W_Gate(1)), Int1,
                                            Int2));
                            lastInstruction = next_instruction.getInstruction();
                            lastInt1 = Int1;
                            lastInt2 = Int2;
                        }
                        break;
                    case Create_CCustom1:
                        if ((Int1 != Int2) && (Int2 <= num_qubits) && (Int2 >= 1)) {
                            to_return = to_return.addGate(Builder_ID,
                                    new ControlledU_Gate((new Custom_Gate(1, 0)),
                                            Int1, Int2));
                            lastInstruction = next_instruction.getInstruction();
                            lastInt1 = Int1;
                            lastInt2 = Int2;
                        }
                        break;
                    case Create_CCustom2:
                        if ((Int1 != Int2) && (Int2 <= num_qubits) && (Int2 >= 1)) {
                            to_return = to_return.addGate(Builder_ID,
                                    new ControlledU_Gate((new Custom_Gate(1, 1)),
                                            Int1, Int2));
                            lastInstruction = next_instruction.getInstruction();
                            lastInt1 = Int1;
                            lastInt2 = Int2;
                        }
                        break;
                    case Create_CCustom3:
                        if ((Int1 != Int2) && (Int2 <= num_qubits) && (Int2 >= 1)) {
                            to_return = to_return.addGate(Builder_ID,
                                    new ControlledU_Gate((new Custom_Gate(1, 2)),
                                            Int1, Int2));
                            lastInstruction = next_instruction.getInstruction();
                            lastInt1 = Int1;
                            lastInt2 = Int2;
                        }
                        break;
                    // case Root:
                    case Body:
                        qcarray = next_instruction.getSubalg();

                        for (QuantumAlgorithm element : qcarray) {
                            temp = internal_builder.Build(element, num_qubits);
                            to_return = to_return.addCircuit(Builder_ID, temp);
                        }
                        break;
                    case Iterate:
                        qcarray = next_instruction.getSubalg();
                        new_itervals = new int[loopvars.length + 1];
                        // for (int j = 0; j < loopvars.length; j++) {
                        // new_itervals[j + 1] = loopvars[j];
                        // }
                        System.arraycopy(loopvars, 0, new_itervals, 1,
                                loopvars.length);
                        lastInstruction = null;

                        for (int i = 1; (qcarray.length > 0) && (i <= Int1); i++) {
                            try {
                                new_itervals[0] = i;
                                temp = internal_builder.Build(qcarray[0],
                                        num_qubits, new_itervals);
                            } catch (NullPointerException e) {
                                e.printStackTrace();
                            }
                            to_return = to_return.addCircuit(Builder_ID, temp);
                        }
                        break;
                    case RevIterate:
                        qcarray = next_instruction.getSubalg();
                        new_itervals = new int[loopvars.length + 1];
                        int k;
                        for (k = 0; k < loopvars.length; ) {
                            new_itervals[k + 1] = loopvars[k];
                            k++;
                        }
                        lastInstruction = null;

                        for (int i = Int1; (qcarray.length > 0) && (1 <= i); i--) {
                            try {
                                new_itervals[0] = i;
                                temp = internal_builder.Build(qcarray[0],
                                        num_qubits, new_itervals);
                            } catch (NullPointerException e) {
                                e.printStackTrace();
                            }
                            to_return = to_return.addCircuit(Builder_ID, temp);
                        }
                        break;

                    default:
                        break;
                }
            }
        }

        return to_return;
    }
}
