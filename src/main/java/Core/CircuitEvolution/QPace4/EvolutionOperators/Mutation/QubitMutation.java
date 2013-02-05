/**
 * @Author = Sam Ratcliff
 */
package Core.CircuitEvolution.QPace4.EvolutionOperators.Mutation;

import Core.CircuitEvolution.QPace4.terminal.Qubit;
import ec.BreedingPipeline;
import ec.EvolutionState;
import ec.Individual;
import ec.gp.GPBreedingPipeline;
import ec.gp.GPFunctionSet;
import ec.gp.GPIndividual;
import ec.gp.GPInitializer;
import ec.gp.GPNode;
import ec.gp.GPNodeSelector;
import ec.gp.GPTree;
import ec.gp.GPType;
import ec.gp.breed.GPBreedDefaults;
import ec.util.Parameter;

/**
 * @author Sam Ratcliff
 * 
 */
public class QubitMutation extends GPBreedingPipeline {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -3979838960870572846L;
	public static final String	P_MUTATEONENODE	= "qubit-mutation";
	public static final int		NUM_SOURCES		= 1;

	/** How the pipeline chooses a subtree to mutate */
	public GPNodeSelector		nodeselect;

	/** Is our tree fixed? If not, this is -1 */
	private int							tree;

	@Override
	public Object clone() {
		QubitMutation c = (QubitMutation) (super.clone());

		// deep-cloned stuff
		c.nodeselect = (GPNodeSelector) (nodeselect.clone());
		return c;
	}

	@Override
	public Parameter defaultBase() {
		return GPBreedDefaults.base().push(P_MUTATEONENODE);
	}

	@Override
	public int numSources() {
		return NUM_SOURCES;
	}

	/**
	 * Returns a node which is swap-compatible with returntype, and whose
	 * arguments are swap-compatible with the current children of original. You
	 * need to clone this node.
	 */

	private GPNode pickCompatibleNode(final GPNode original,
			final GPFunctionSet set, final EvolutionState state,
			final GPType returntype, final int thread) {
		// an expensive procedure: we will linearly search for a valid node
		int numValidNodes = 0;

		int type = returntype.type;
		GPInitializer initializer = ((GPInitializer) state.initializer);
		int len = original.constraints(initializer).childtypes.length;
		boolean failed;

		if (initializer.numAtomicTypes + initializer.numSetTypes == 1) {
			numValidNodes = set.nodesByArity[type][len].length;
		} else {
			for (int x = 0; x < set.nodesByArity[type][len].length; x++) // ugh,
																			// the
																			// hard
																			// way
																			// --
																			// nodes
																			// swap-compatible
																			// with
																			// type,
																			// and
																			// of
																			// arity
																			// len
			{
				failed = false;
				for (int y = 0; y < set.nodesByArity[type][len][x]
						.constraints(initializer).childtypes.length; y++) {
					if (!set.nodesByArity[type][len][x]
							.constraints(initializer).childtypes[y]
							.compatibleWith(initializer, original.children[y]
									.constraints(initializer).returntype)) {
						failed = true;
						break;
					}
				}
				if (!failed) {
					numValidNodes++;
				}
			}
		}

		// we must have at least success -- the node itself. Otherwise we're
		// in deep doo-doo.

		// now pick a random node number
		int nodenum = state.random[thread].nextInt(numValidNodes);

		// find and return that node
		int prosnode = 0;

		if (numValidNodes == set.nodesByArity[type][len].length) {
			return set.nodesByArity[type][len][nodenum];
		} else {
			for (int x = 0; x < set.nodesByArity[type][len].length; x++) // ugh,
																			// the
																			// hard
																			// way
																			// --
																			// nodes
																			// swap-compatible
																			// with
																			// type,
																			// and
																			// of
																			// arity
																			// len
			{
				failed = false;
				for (int y = 0; y < set.nodesByArity[type][len][x]
						.constraints(initializer).childtypes.length; y++) {
					if (!set.nodesByArity[type][len][x]
							.constraints(initializer).childtypes[y]
							.compatibleWith(initializer, original.children[y]
									.constraints(initializer).returntype)) {
						failed = true;
						break;
					}
				}
				if (!failed) {
					if (prosnode == nodenum) {
						return set.nodesByArity[type][len][x];
					}
					prosnode++;
				}
			}
		}

		// should never be able to get here
		throw new InternalError(); // whoops!
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

		GPInitializer initializer = ((GPInitializer) state.initializer);

		// now let's mutate 'em
		for (int q = start; q < n + start; q++) {
			GPIndividual i = (GPIndividual) inds[q];

			if ((tree != TREE_UNFIXED)
					&& ((tree < 0) || (tree >= i.trees.length))) {
				// uh oh
				state.output
						.fatal("QubitMutation attempted to fix tree.0 to a value which was out of bounds of the array of the individual's trees.  Check the pipeline's fixed tree values -- they may be negative or greater than the number of trees in an individual");
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
			GPNode p2 = null;

			// pick a node in individual 1
			p1 = nodeselect.pickNode(state, subpopulation, thread, i,
					i.trees[t]);

			if (p1 instanceof Qubit) {
				// generate a tree with a new root but the same children,
				// which we will replace p1 with

				GPType type;
				type = p1.parentType(initializer);

				p2 = (pickCompatibleNode(p1,
						i.trees[t].constraints(initializer).functionset, state,
						type, thread)).lightClone();

				// if it's an ERC, let it set itself up
				p2.resetNode(state, thread);

				// p2's parent and argposition will be set automatically below

				GPIndividual j;

				if (sources[0] instanceof BreedingPipeline)
				// it's already a copy, so just smash the tree in
				{
					j = i;
					p1.replaceWith(p2);
					j.evaluated = false;
				} else {
					j = (i.lightClone());

					// Fill in various tree information that didn't get filled
					// in
					// there
					j.trees = new GPTree[i.trees.length];

					for (int x = 0; x < j.trees.length; x++) {
						if (x == t) // we've got a tree with a kicking cross
									// position!
						{
							j.trees[x] = (i.trees[x].lightClone());
							j.trees[x].owner = j;
							j.trees[x].child = i.trees[x].child
									.cloneReplacingAtomic(p2, p1);
							j.trees[x].child.parent = j.trees[x];
							j.trees[x].child.argposition = 0;
							j.evaluated = false;
						} // it's changed
						else {
							j.trees[x] = (i.trees[x].lightClone());
							j.trees[x].owner = j;
							j.trees[x].child = (GPNode) (i.trees[x].child
									.clone());
							j.trees[x].child.parent = j.trees[x];
							j.trees[x].child.argposition = 0;
						}
					}
				}

				// add the new individual, replacing its previous source
				inds[q] = j;
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
