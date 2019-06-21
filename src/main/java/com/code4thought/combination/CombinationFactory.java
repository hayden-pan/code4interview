package com.code4thought.combination;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * N选M组合生成工厂
 */
public class CombinationFactory {


    private final static Map<CombinationBaseInfo, CombinationItem[]> cache = new HashMap<>();

    private final static Map<Integer, long[]> extractCache = new HashMap<>();

    /**
     * 生成N选M组合项
     *
     * @param n
     * @param m
     * @return
     */
    public static synchronized CombinationItem[] getCombination(int n, int m) {

        CombinationBaseInfo baseInfo = new CombinationBaseInfo(n, m);

        CombinationItem[] items = cache.get(baseInfo);

        if (items != null) {
            return items;
        }

        items = genCombination(n, m);
        cache.put(baseInfo, items);

        return items;
    }

    public static synchronized long[] getBinaryExtract(int bitNum) {
        long[] result = extractCache.get(bitNum);
        if (result == null) {
            result = new long[bitNum];
            for (int i = 0; i < result.length; i++) {
                result[i] = Double.valueOf(Math.pow(2, i)).longValue();
            }
            extractCache.put(bitNum, result);
        }
        return result;
    }

    private static CombinationItem[] genCombination(int n, int m) {
        //prepare bitArray
        int[] bitArray = new int[n];
        for (int i = 0; i < m; i++) {
            bitArray[i] = 1;
        }

        //compute result size
        int size = calCombinationSize(n, m);
        CombinationItem[] items = new CombinationItem[size];
        items[0] = new CombinationItem(bitArray, m);
        int currentItemIndex = 1;

        for (int i = 0; i < n - 1; i++) {
            if (bitArray[i] > bitArray[i + 1]) {
                int tmp = bitArray[i];
                bitArray[i] = bitArray[i + 1];
                bitArray[i + 1] = tmp;
                moveAllBeforeBitToLeft(bitArray, i);
                items[currentItemIndex] = new CombinationItem(bitArray, m);
                currentItemIndex++;
                i = -1;
            }
        }

        return items;
    }

    /**
     * Move All 1 To The Left Side
     *
     * @param bitArray
     * @param maxIndex
     */
    private static void moveAllBeforeBitToLeft(int[] bitArray, int maxIndex) {
        for (int i = 0; i < maxIndex - 1; i++) {
            if (bitArray[i] < bitArray[i + 1]) {
                int tmp = bitArray[i];
                bitArray[i] = bitArray[i + 1];
                bitArray[i + 1] = tmp;
                i = -1;
            }
        }
    }

    /**
     * @param n
     * @param m
     * @return
     */
    private static int calCombinationSize(int n, int m) {
        BigInteger nFactorial = BigInteger.valueOf(1);
        for (int i = 2; i < n + 1; i++) {
            nFactorial = nFactorial.multiply(BigInteger.valueOf(i));
        }

        BigInteger mFactorial = BigInteger.valueOf(1);
        for (int i = 2; i < m + 1; i++) {
            mFactorial = mFactorial.multiply(BigInteger.valueOf(i));
        }

        BigInteger nmFactorial = BigInteger.valueOf(1);
        for (int i = 2; i < n - m + 1; i++) {
            nmFactorial = nmFactorial.multiply(BigInteger.valueOf(i));
        }

        return nFactorial.divide(mFactorial).divide(nmFactorial).intValue();
    }


}
