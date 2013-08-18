package info.samratcliff.core.Circuit.Implementation;

import com.google.common.collect.ImmutableList;
import info.samratcliff.core.Circuit.Circuit;
import info.samratcliff.core.Circuit.GateImplementations.ControlledU_Gate;
import info.samratcliff.core.Circuit.GateImplementations.Swap_Gate;
import info.samratcliff.core.Circuit.IQuantumGate;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class SimpleCircuit implements Circuit {
    private final ImmutableList<IQuantumGate> circuit;

    public SimpleCircuit() {
        circuit = ImmutableList.of();
    }

    private SimpleCircuit(ImmutableList<IQuantumGate> circuit) {
        this.circuit = circuit;
    }

    @Override
    public SimpleCircuit addCircuit(int b_id, Circuit circuit)
            throws IllegalArgumentException {
        return new SimpleCircuit(ImmutableList.<IQuantumGate>builder()
                .addAll(this.circuit).addAll(circuit.getCircuitlayout())
                .build());
    }

    @Override
    public SimpleCircuit addGate(int b_id, IQuantumGate quantumgate)
            throws IllegalArgumentException {
        return new SimpleCircuit(ImmutableList.<IQuantumGate>builder()
                .addAll(this.circuit).add(quantumgate).build());
    }

    protected List<IQuantumGate> getCircuit() {
        return circuit;
    }

    @Override
    public ListIterator<IQuantumGate> getCircuitlayout() {
        return circuit.listIterator();
    }

    @Override
    public int getSize() {
        return circuit.size();
    }

    @Override
    public SimpleCircuit removeLastGate() throws NoSuchElementException {
        return new SimpleCircuit(ImmutableList.<IQuantumGate>builder()
                .addAll(circuit.subList(0, getSize() - 1)).build());
    }

    @Override
    public String toLatex(int qubits) {
        String[] cir_diag = new String[qubits];

        for (int i = 0; i < cir_diag.length; i++) {
            cir_diag[i] = "&";
        }

        Iterator<IQuantumGate> cir_iter = getCircuitlayout();
        IQuantumGate next_gate;

        while (cir_iter.hasNext()) {
            next_gate = cir_iter.next();

            if (next_gate instanceof Swap_Gate) {

                for (int i = 0; i < qubits; i++) {

                    if (i == (((Swap_Gate) next_gate).getTarget() - 1)) {

                        cir_diag[i] = cir_diag[i]
                                .concat(((Swap_Gate) next_gate).toLatex(true));

                    } else if (i == (((Swap_Gate) next_gate).getTarget2() - 1)) {

                        cir_diag[i] = cir_diag[i]
                                .concat(((Swap_Gate) next_gate).toLatex(false));

                    } else if ((i >= ((Swap_Gate) next_gate).getTarget())
                            && (i <= ((Swap_Gate) next_gate).getTarget2())) {

                        cir_diag[i] = cir_diag[i].concat("\\qw \\qwx&");

                    } else {
                        cir_diag[i] = cir_diag[i].concat("\\qw&");
                    }

                }
            } else if (next_gate instanceof ControlledU_Gate) {

                for (int i = 0; i < qubits; i++) {

                    if (i == (((ControlledU_Gate) next_gate).getTarget() - 1)) {
                        if ((((ControlledU_Gate) next_gate).getTarget() > ((ControlledU_Gate) next_gate)
                                .getSecondQubit())) {
                            cir_diag[i] = cir_diag[i]
                                    .concat(((ControlledU_Gate) next_gate)
                                            .toLatex(false));
                        } else {
                            cir_diag[i] = cir_diag[i]
                                    .concat(((ControlledU_Gate) next_gate)
                                            .toLatex(true));
                        }

                    } else if ((i == (((ControlledU_Gate) next_gate)
                            .getSecondQubit() - 1))
                            && (((ControlledU_Gate) next_gate).getTarget() > ((ControlledU_Gate) next_gate)
                            .getSecondQubit())) {
                        int dif = ((ControlledU_Gate) next_gate).getTarget()
                                - ((ControlledU_Gate) next_gate)
                                .getSecondQubit();

                        cir_diag[i] = cir_diag[i].concat("\\ctrl{-" + dif
                                + "} \\qwx&");

                    } else if (i == (((ControlledU_Gate) next_gate)
                            .getSecondQubit() - 1)) {
                        int dif = ((ControlledU_Gate) next_gate)
                                .getSecondQubit()
                                - ((ControlledU_Gate) next_gate).getTarget();

                        cir_diag[i] = cir_diag[i].concat("\\ctrl{" + dif
                                + "} &");

                    } else if ((i > (((ControlledU_Gate) next_gate).getTarget() - 1))
                            && (i < (((ControlledU_Gate) next_gate)
                            .getSecondQubit() - 1))) {

                        cir_diag[i] = cir_diag[i].concat("\\qw \\qwx&");

                    } else if ((i < (((ControlledU_Gate) next_gate).getTarget() - 1))
                            && (i > (((ControlledU_Gate) next_gate)
                            .getSecondQubit() - 1))) {

                        cir_diag[i] = cir_diag[i].concat("\\qw \\qwx&");

                    } else {

                        cir_diag[i] = cir_diag[i].concat("\\qw&");
                    }
                }
            } else {
                for (int i = 0; i < qubits; i++) {
                    if (i == (next_gate.getTarget() - 1)) {
                        cir_diag[i] = cir_diag[i].concat(next_gate.toLatex());
                    } else {
                        cir_diag[i] = cir_diag[i].concat("\\qw&");
                    }
                }
            }
        }

        for (int i = 0; i < qubits; i++) {
            cir_diag[i] = cir_diag[i].concat("\\qw&");
        }

        String to_return = new String();
        int qubit = cir_diag.length;
        String new_line = "\\\\%" + qubit + "\n";
        to_return = to_return
                .concat("\\begin{figure}\n\\[\n\\Qcircuit @C=1.0em @R=.7em {\n");
        for (int index = cir_diag.length; index > 0; ) {
            to_return = to_return.concat(cir_diag[--index]);
            to_return = to_return.concat(new_line);
            new_line = "\\\\%" + --qubit + "\n";
        }

        to_return = to_return.concat("}\n\\]\n\\caption{Circuit for " + qubits
                + " qubits}\n\\end{figure}\n");

        return to_return;

    }
}
