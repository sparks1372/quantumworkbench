/**
 * @Author = Sam Ratcliff
 */
package info.samratcliff.core.CircuitEvolution.QPace4.EvolutionOperators.Mutation;

import ec.EvolutionState;
import ec.Individual;
import ec.gp.GPBreedingPipeline;
import ec.gp.GPIndividual;
import ec.gp.GPNode;
import ec.gp.GPNodeSelector;
import ec.gp.breed.GPBreedDefaults;
import ec.util.Parameter;
import info.samratcliff.core.CircuitEvolution.QPace4.nonterminal.Gate;

/**
 * @author Sam Ratcliff
 */
public class InstructionDuplication extends GPBreedingPipeline {

    /**
     *
     */
    private static final long serialVersionUID = -4191874562165428780L;
    public static final String P_DUPLICATEONENODE = "instruction-duplication";
    public static final int NUM_SOURCES = 1;

    /**
     * How the pipeline chooses a subtree to mutate
     */
    public GPNodeSelector nodeselect;

    /**
     * Is our tree fixed? If not, this is -1
     */
    private int tree;

    @Override
    public Object clone() {
        InstructionDuplication c = (InstructionDuplication) (super.clone());

        // deep-cloned stuff
        c.nodeselect = (GPNodeSelector) (nodeselect.clone());
        return c;
    }

    @Override
    public Parameter defaultBase() {
        return GPBreedDefaults.base().push(P_DUPLICATEONENODE);
    }

    @Override
    public int numSources() {
        return NUM_SOURCES;
    }

    @Override
    public int produce(final int min, final int max, final int start,
                       final int subpopulation, final Individual[] inds,
                       final EvolutionState state, final int thread) {
        // grab n individuals from our source and stick 'em right into inds.
        // we'll modify them from there
        int n = sources[0].produce(min, max, start, subpopulation, inds, state,
                thread);

        // should we bother?
        if (!state.random[thread].nextBoolean(likelihood)) {
            return reproduce(n, start, subpopulation, inds, state, thread,
                    false); // DON'T produce children from source -- we already
            // did
        }

        // now let's mutate 'em
        for (int q = start; q < n + start; q++) {
            GPIndividual i = (GPIndividual) inds[q];

            if ((tree != TREE_UNFIXED)
                    && ((tree < 0) || (tree >= i.trees.length))) {
                // uh oh
                state.output
                        .fatal("InstructionMutation attempted to fix tree.0 to a value which was out of bounds of the array of the individual's trees.  Check the pipeline's fixed tree values -- they may be negative or greater than the number of trees in an individual");
            }

            int t;
            // pick random tree
            if (tree == TREE_UNFIXED) {
                if (i.trees.length > 1) {
                    t = state.random[thread].nextInt(i.trees.length);
                } else {
                    t = 0;
                }
            } else {
                t = tree;
            }

            // prepare the nodeselector
            nodeselect.reset();

            // pick a node

            GPNode p1 = null; // the node we pick

            // pick a node in individual 1
            p1 = nodeselect.pickNode(state, subpopulation, thread, i,
                    i.trees[t]);

            if (p1 instanceof Gate) {
                GPNode temp = (GPNode) p1.clone();
                p1.children[1] = temp;
            }
        }
        return n;
    }

    @Override
    public void setup(final EvolutionState state, final Parameter base) {
        super.setup(state, base);

        Parameter p = base.push(P_NODESELECTOR).push("" + 0);
        Parameter def = defaultBase();

        nodeselect = (GPNodeSelector) (state.parameters
                .getInstanceForParameter(p,
                        def.push(P_NODESELECTOR).push("" + 0),
                        GPNodeSelector.class));
        nodeselect.setup(state, p);

        tree = TREE_UNFIXED;
        if (state.parameters.exists(base.push(P_TREE).push("" + 0),
                def.push(P_TREE).push("" + 0))) {
            tree = state.parameters.getInt(base.push(P_TREE).push("" + 0), def
                    .push(P_TREE).push("" + 0), 0);
            if (tree == -1) {
                state.output
                        .fatal("Tree fixed value, if defined, must be >= 0");
            }
        }
    }
}
