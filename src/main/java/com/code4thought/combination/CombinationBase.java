package com.code4thought.combination;

public class CombinationBase {

    private int count;

    private int choice;

    private volatile int hashCode;

    @Override
    public int hashCode() {
        int result = hashCode;

        if (result == 0) {
            result = 17;
            result = 31 * result + count;
            result = 31 * result + choice;
            hashCode = result;
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {

        return super.equals(o);
    }

}
