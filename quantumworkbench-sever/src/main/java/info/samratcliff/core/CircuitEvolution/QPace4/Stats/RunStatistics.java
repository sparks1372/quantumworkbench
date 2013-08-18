package info.samratcliff.core.CircuitEvolution.QPace4.Stats;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: Sam Ratcliff
 * Date: 18/08/13
 * Time: 19:06
 */
public class RunStatistics {
    private final BigDecimal fitness;
    private final long generationCount;

    public RunStatistics(BigDecimal fitness, long generationCount) {
        this.fitness = fitness;
        this.generationCount = generationCount;
    }

    public BigDecimal getFitness() {
        return fitness;
    }

    public long getGenerationCount() {
        return generationCount;
    }
}
