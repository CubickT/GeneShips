package io.github.GeneShips;

public class MathUtils {

    public static float map(float value, float rangeS, float rangeE, float enRangeS, float enRangeE) {
        return enRangeS + (enRangeE - enRangeS) * ((value - rangeS) / (rangeE - rangeS));
    }

}
