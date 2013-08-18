package info.samratcliff.core;

import com.google.common.eventbus.EventBus;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import info.samratcliff.core.CircuitBuilder.CircuitBuilder;
import info.samratcliff.core.CircuitBuilder.Implementation.SimpleQuantumCircuitBuilder;
import info.samratcliff.core.CircuitEvaluator.CircuitEvaluator;
import info.samratcliff.core.CircuitEvaluator.Implementation.basiccircuitevaluator;
import info.samratcliff.core.CircuitEvaluator.SuitabilityMeasureManager;
import info.samratcliff.core.Problem.ProblemManager;

public class CoreModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(CircuitBuilder.class).to(SimpleQuantumCircuitBuilder.class).in(
                Scopes.SINGLETON);
        bind(CircuitEvaluator.class).to(basiccircuitevaluator.class).in(
                Scopes.SINGLETON);
        bind(ProblemManager.class).asEagerSingleton();
        bind(SuitabilityMeasureManager.class).in(Scopes.SINGLETON);
        bind(EventBus.class).in(Scopes.SINGLETON);
    }

}
