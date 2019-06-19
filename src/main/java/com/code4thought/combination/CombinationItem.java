package com.code4thought.combination;

/**
 * N选M组合项
 */
public class CombinationItem {

    private final int[] indexOfM;

    private long binaryOfM;

    public CombinationItem(int[] bitArray, int m) {
        indexOfM = new int[m];
        int currentIndexOfM = 0;

        for (int i = 0; i < bitArray.length; i++) {
            if (bitArray[i] > 0) {

                indexOfM[currentIndexOfM] = i;
                currentIndexOfM++;

                binaryOfM += Math.pow(2, i);
            }
        }
    }

    public int[] getIndexOfM() {
        return indexOfM;
    }

    public long getBinaryOfM() {
        return binaryOfM;
    }
}
