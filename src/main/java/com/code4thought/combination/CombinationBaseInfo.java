package com.code4thought.combination;

public class CombinationBaseInfo {

    private int n;

    private int m;

    public CombinationBaseInfo(int n, int m) {
        this.n = n;
        this.m = m;
    }


    //region

    private volatile int hashCode;

    @Override
    public int hashCode() {
        int result = hashCode;

        if (result == 0) {
            result = 17;
            result = 31 * result + n;
            result = 31 * result + m;
            hashCode = result;
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof CombinationBaseInfo)) {
            return false;
        }
        CombinationBaseInfo other = (CombinationBaseInfo) o;
        return other.n == this.n && other.m == this.m;
    }

    //endregion
}
