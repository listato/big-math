package ch.obermuhlner.math.big.statistics.univariate.stream;

import ch.obermuhlner.math.big.BigDecimalMath;

import java.math.BigDecimal;
import java.math.MathContext;

public class SampleSkewnessKurtosisCalculator implements UnivariateStreamCalculator<SkewnessKurtosis> {

    private final MathContext mathContext;

    private final PopulationSkewnessKurtosisCalculator populationSkewnessKurtosisCalculator;

    public SampleSkewnessKurtosisCalculator(MathContext mathContext) {
        this.mathContext = mathContext;
        this.populationSkewnessKurtosisCalculator = new PopulationSkewnessKurtosisCalculator(mathContext);
    }

    @Override
    public void add(BigDecimal value) {
        populationSkewnessKurtosisCalculator.add(value);
    }

    public BigDecimal getSkewness() {
        BigDecimal n = BigDecimal.valueOf(populationSkewnessKurtosisCalculator.getCount());
        BigDecimal nMinus1 = BigDecimal.valueOf(populationSkewnessKurtosisCalculator.getCount() - 1);
        BigDecimal nMinus2 = BigDecimal.valueOf(populationSkewnessKurtosisCalculator.getCount() - 2);
        BigDecimal correction = BigDecimalMath.sqrt(n.multiply(nMinus1, mathContext), mathContext).divide(nMinus2, mathContext);

        return correction.multiply(populationSkewnessKurtosisCalculator.getSkewness(), mathContext);
    }

    public BigDecimal getKurtosis() {
        // TODO correct kurtosis to sample?
        return populationSkewnessKurtosisCalculator.getKurtosis();
    }

    @Override
    public SkewnessKurtosis getResult() {
        return new SkewnessKurtosis(getSkewness(), getKurtosis());
    }
}