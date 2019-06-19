package com.code4thought;

import java.util.HashMap;
import java.util.Map;

public class SingleSessionCombination {

    private final SessionInfo session;
    private final TalkCombination[] talkCombinations;

    private final long binary;

    private final int totalRemainMinute;

    public SingleSessionCombination(SessionInfo session, TalkCombination[] talkCombinations, long binary,
                                    int totalRemainMinute) {
        this.session = session;
        this.talkCombinations = talkCombinations;
        this.binary = binary;
        this.totalRemainMinute = totalRemainMinute;
    }

    public SessionInfo getSession() {
        return session;
    }

    public TalkCombination[] getTalkCombinations() {
        return talkCombinations;
    }

    public long getBinary() {
        return binary;
    }

    public int getTotalRemainMinute() {
        return totalRemainMinute;
    }
}
