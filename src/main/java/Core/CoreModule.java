package Core;

import Core.CircuitBuilder.CircuitBuilder;
import Core.CircuitBuilder.Implementation.SimpleQuantumCircuitBuilder;
import Core.CircuitEvaluator.CircuitEvaluator;
import Core.CircuitEvaluator.SuitabilityMeasureManager;
import Core.CircuitEvaluator.Implementation.basiccircuitevaluator;
import Core.Problem.ProblemManager;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class CoreModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(CircuitBuilder.class).to(SimpleQuantumCircuitBuilder.class).in(
				Scopes.SINGLETON);
		bind(CircuitEvaluator.class).to(basiccircuitevaluator.class).in(
				Scopes.SINGLETON);
		bind(ProblemManager.class).asEagerSingleton();
		bind(SuitabilityMeasureManager.class).in(Scopes.SINGLETON);
	}

}
