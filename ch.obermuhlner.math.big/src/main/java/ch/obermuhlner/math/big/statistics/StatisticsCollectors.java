package ch.obermuhlner.math.big.statistics;

import ch.obermuhlner.math.big.statistics.multivariate.stream.CorrelationCalculator;
import ch.obermuhlner.math.big.statistics.univariate.Histogram;
import ch.obermuhlner.math.big.statistics.univariate.list.MedianCalculator;
import ch.obermuhlner.math.big.statistics.univariate.stream.*;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.stream.Collector;

public class StatisticsCollectors {
    public static Collector<BigDecimal, MinCalculator, BigDecimal> min() {
        return Collector.of(
                () -> new MinCalculator(),
                (calc, value) -> calc.add(value),
                (left, right) -> { left.combine(right); return left; },
                (calc) -> calc.getResult());
    }

    public static Collector<BigDecimal, MaxCalculator, BigDecimal> max() {
        return Collector.of(
                () -> new MaxCalculator(),
                (calc, value) -> calc.add(value),
                (left, right) -> { left.combine(right); return left; },
                (calc) -> calc.getResult());
    }

    public static Collector<BigDecimal, SumCalculator, BigDecimal> sum(MathContext mathContext) {
        return Collector.of(
                () -> new SumCalculator(mathContext),
                (calc, value) -> calc.add(value),
                (left, right) -> { left.combine(right); return left; },
                (calc) -> calc.getResult());
    }

    public static Collector<BigDecimal, ProductCalculator, BigDecimal> product(MathContext mathContext) {
        return Collector.of(
                () -> new ProductCalculator(mathContext),
                (calc, value) -> calc.add(value),
                (left, right) -> { left.combine(right); return left; },
                (calc) -> calc.getResult());
    }

    public static Collector<BigDecimal, ArithmeticMeanCalculator, BigDecimal> arithmeticMean(MathContext mathContext) {
        return Collector.of(
                () -> new ArithmeticMeanCalculator(mathContext),
                (calc, value) -> calc.add(value),
                (left, right) -> { left.combine(right); return left; },
                (calc) -> calc.getResult());
    }

    public static Collector<BigDecimal, GeometricMeanCalculator, BigDecimal> geometricMean(MathContext mathContext) {
        return Collector.of(
                () -> new GeometricMeanCalculator(mathContext),
                (calc, value) -> calc.add(value),
                (left, right) -> { left.combine(right); return left; },
                (calc) -> calc.getResult());
    }

    public static Collector<BigDecimal, HarmonicMeanCalculator, BigDecimal> harmonicMean(MathContext mathContext) {
        return Collector.of(
                () -> new HarmonicMeanCalculator(mathContext),
                (calc, value) -> calc.add(value),
                (left, right) -> { left.combine(right); return left; },
                (calc) -> calc.getResult());
    }

    public static Collector<BigDecimal, UnivariateStreamCalculator<BigDecimal>, BigDecimal> median(MathContext mathContext) {
        return Collector.of(
                () -> new UnivariateListAsStreamCalculator<BigDecimal>(new MedianCalculator(mathContext)),
                (calc, value) -> calc.add(value),
                (left, right) -> { throw new UnsupportedOperationException("parallel computation not supported for median"); },
                (calc) -> calc.getResult());
    }

    public static Collector<BigDecimal, PopulationSkewnessKurtosisCalculator, BigDecimal> populationSkewness(MathContext mathContext) {
        return Collector.of(
                () -> new PopulationSkewnessKurtosisCalculator(mathContext),
                (calc, value) -> calc.add(value),
                (left, right) -> { throw new UnsupportedOperationException("parallel computation not supported for skewness"); },
                (calc) -> calc.getSkewness());
    }

    public static Collector<BigDecimal, PopulationSkewnessKurtosisCalculator, BigDecimal> populationKurtosis(MathContext mathContext) {
        return Collector.of(
                () -> new PopulationSkewnessKurtosisCalculator(mathContext),
                (calc, value) -> calc.add(value),
                (left, right) -> { throw new UnsupportedOperationException("parallel computation not supported for kurtosis"); },
                (calc) -> calc.getResult().kurtosis);
    }

    public static Collector<BigDecimal, PopulationSkewnessKurtosisCalculator, SkewnessKurtosis> populationSkewnessKurtosis(MathContext mathContext) {
        return Collector.of(
                () -> new PopulationSkewnessKurtosisCalculator(mathContext),
                (calc, value) -> calc.add(value),
                (left, right) -> { throw new UnsupportedOperationException("parallel computation not supported for skewness and kurtosis"); },
                (calc) -> calc.getResult());
    }

    public static Collector<BigDecimal, SampleSkewnessKurtosisCalculator, BigDecimal> sampleSkewness(MathContext mathContext) {
        return Collector.of(
                () -> new SampleSkewnessKurtosisCalculator(mathContext),
                (calc, value) -> calc.add(value),
                (left, right) -> { throw new UnsupportedOperationException("parallel computation not supported for skewness"); },
                (calc) -> calc.getSkewness());
    }

    public static Collector<BigDecimal, SampleSkewnessKurtosisCalculator, SkewnessKurtosis> sampleSkewnessKurtosis(MathContext mathContext) {
        return Collector.of(
                () -> new SampleSkewnessKurtosisCalculator(mathContext),
                (calc, value) -> calc.add(value),
                (left, right) -> { throw new UnsupportedOperationException("parallel computation not supported for skewness and kurtosis"); },
                (calc) -> calc.getResult());
    }

    public static Collector<BigDecimal, HistogramCalculator, Histogram> histogram(BigDecimal start, BigDecimal end, BigDecimal step) {
        return Collector.of(
                () -> new HistogramCalculator(start, end, step),
                (calc, value) -> calc.add(value),
                (left, right) -> { left.combine(right); return left; },
                (calc) -> calc.getResult());
    }

    public static Collector<BigDecimal[], CorrelationCalculator, BigDecimal> correlation(MathContext mathContext) {
        return Collector.of(
                () -> new CorrelationCalculator(mathContext),
                (calc, tuple) -> calc.add(tuple),
                (left, right) -> { left.combine(right); return left; },
                (calc) -> calc.getResult());
    }

}