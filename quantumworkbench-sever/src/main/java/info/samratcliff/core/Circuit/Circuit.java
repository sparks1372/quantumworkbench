package info.samratcliff.core.Circuit;

import info.samratcliff.core.Circuit.Implementation.SimpleCircuit;

import java.util.Iterator;
import java.util.NoSuchElementException;

public interface Circuit {
    public SimpleCircuit addCircuit(int builder_id, Circuit circuit);

    public SimpleCircuit addGate(int builder_id, IQuantumGate quantumgate);

    public Iterator<IQuantumGate> getCircuitlayout();

    public int getSize();

    public SimpleCircuit removeLastGate() throws NoSuchElementException;

    public String toLatex(int qubits);

}
