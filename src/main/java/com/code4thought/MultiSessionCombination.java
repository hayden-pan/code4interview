package com.code4thought;

import java.util.HashMap;
import java.util.Map;

public class MultiSessionCombination {

    private final Map<SessionInfo, SingleSessionCombination> result;

    public MultiSessionCombination(Map<SessionInfo, SingleSessionCombination> result) {
        this.result = result;
    }

    public Map<SessionInfo, SingleSessionCombination> getResult() {
        return result;
    }
}
