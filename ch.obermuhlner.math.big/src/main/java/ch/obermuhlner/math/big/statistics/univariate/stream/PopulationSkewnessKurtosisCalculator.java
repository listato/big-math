package ch.obermuhlner.math.big.statistics.univariate.stream;

import ch.obermuhlner.math.big.BigDecimalMath;

import java.math.BigDecimal;
import java.math.MathContext;

// https://en.wikipedia.org/wiki/Algorithms_for_calculating_variance
public class PopulationSkewnessKurtosisCalculator implements UnivariateStreamCalculator<SkewnessKurtosis> {

    private static final BigDecimal B3 = BigDecimal.valueOf(3);
    private static final BigDecimal B4 = BigDecimal.valueOf(4);
    private static final BigDecimal B6 = BigDecimal.valueOf(6);

    private final MathContext mathContext;
    private PopulationStandardDeviationCalculator standardDeviationCalculator;

    private int count = 0;
    private BigDecimal mean = BigDecimal.ZERO;
    private BigDecimal m2 = BigDecimal.ZERO;
    private BigDecimal m3 = BigDecimal.ZERO;
    private BigDecimal m4 = BigDecimal.ZERO;

    public PopulationSkewnessKurtosisCalculator(MathContext mathContext) {
        this.mathContext = mathContext;
        this.standardDeviationCalculator = new PopulationStandardDeviationCalculator(mathContext);
    }

    @Override
    public void add(BigDecimal value) {
        count++;
        BigDecimal n = BigDecimal.valueOf(count);
        BigDecimal nMinus1 = BigDecimal.valueOf(count - 1);
        BigDecimal nMinus2 = BigDecimal.valueOf(count - 2);
        BigDecimal nPow2 = n.multiply(n, mathContext);
        BigDecimal nMult3 = BigDecimal.valueOf(3L * count);

        BigDecimal delta = value.subtract(mean, mathContext);
        BigDecimal deltaDivN = delta.divide(n, mathContext);
        BigDecimal deltaDivNPow2 = deltaDivN.multiply(deltaDivN, mathContext);

        mean = mean.add(deltaDivN, mathContext);

        BigDecimal term = delta.multiply(deltaDivN, mathContext).multiply(nMinus1, mathContext);

        BigDecimal m4Step = term.multiply(deltaDivNPow2, mathContext).multiply(nPow2.subtract(nMult3, mathContext).add(B3, mathContext), mathContext)
                .add(B6.multiply(deltaDivNPow2, mathContext).multiply(m2, mathContext), mathContext)
                .add(B4.multiply(deltaDivN, mathContext).multiply(m3, mathContext));
        m4 = m4.add(m4Step, mathContext);

        BigDecimal m3Step = term.multiply(deltaDivN, mathContext).multiply(nMinus2, mathContext)
                .subtract(B3.multiply(deltaDivN, mathContext).multiply(m2, mathContext));
        m3 = m3.add(m3Step, mathContext);

        BigDecimal m2Step = term;
        m2 = m2.add(m2Step, mathContext);
    }

    public int getCount() {
        return count;
    }

    public BigDecimal getSkewness() {
        BigDecimal n = BigDecimal.valueOf(count);
        BigDecimal nom = BigDecimalMath.sqrt(n, mathContext).multiply(m3, mathContext);
        BigDecimal denom = BigDecimalMath.pow(m2, BigDecimal.valueOf(1.5), mathContext);
        return nom.divide(denom, mathContext);
    }

    public BigDecimal getKurtosis() {
        BigDecimal n = BigDecimal.valueOf(count);
        return n.multiply(m4, mathContext).divide(m2.multiply(m2, mathContext), mathContext).subtract(B3, mathContext);
    }

    @Override
    public SkewnessKurtosis getResult() {
        return new SkewnessKurtosis(getSkewness(), getKurtosis());
    }

}