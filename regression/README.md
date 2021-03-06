# Performance Regression

The same performance measurements for the interesting mathematical functions
in `BigDecimalMath` can be executed with different releases of `big-math`.  

This allows to compare the performance regression over all releases.

## Run with gradle

To run the performance measurements execute the following command (change version number as desired):
```console
./gradlew :regression.v1_0_0:run
```

The special version string `v_current` may be used to measure the performance of the current checkout.
```console
./gradlew :regression.v_current:run
```

The `*.csv` files in the folder `analysis` can be created by running the following gradle task afterwards.
```console
./gradlew :regression.analysis:run
```

To convert the `*.csv` files into chart images run the following
(after installing the https://github.com/eobermuhlner/csv2chart application):
```console
cd regression/analysis
csv2chart --property chart=line --format png *.csv
```

As a convenience you can also run the following shell script from the root of the project.
It does all the calls described above for all releases of `big-math`.
```console
run_regression_analysis.sh
```


## Measured performance

The committed `performance.csv` files in each regression project where created
by running on a PC with the following specs: 

```
Caption                                 DeviceID  MaxClockSpeed  Name                                      NumberOfCores  Status
Intel64 Family 6 Model 142 Stepping 11  CPU0      2001           Intel(R) Core(TM) i7-8565U CPU @ 1.80GHz  4              OK
```

Note: the CPU specs where printed with the following command: 

```console
wmic cpu get caption, deviceid, name, numberofcores, maxclockspeed, status
```

![selected functions performance over releases](https://github.com/eobermuhlner/big-math/blob/master/regression/analysis/selected%20functions%20performance%20over%20releases.png)

![fast functions performance over releases](https://github.com/eobermuhlner/big-math/blob/master/regression/analysis/fast%20functions%20performance%20over%20releases.png)

![medium functions performance over releases](https://github.com/eobermuhlner/big-math/blob/master/regression/analysis/medium%20functions%20performance%20over%20releases.png)

![very slow functions performance over releases](https://github.com/eobermuhlner/big-math/blob/master/regression/analysis/very%20slow%20functions%20performance%20over%20releases.png)
