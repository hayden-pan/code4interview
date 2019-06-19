package com.code4thought;

import com.code4thought.combination.CombinationFactory;
import com.code4thought.combination.CombinationItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Processor {

    public static List<TalkCombination> genTalkCombinationForSession(TalkInfo[] talks, SessionInfo session,
                                                                     int maxRemainMinute) {
        List<TalkCombination> result = new ArrayList<>();
        int maxTalkNum = genMaxTalkNumForSession(talks, session);
        int minTalkNum = genMinTalkNumForSession(talks, session, maxRemainMinute);

        for (int i = minTalkNum; i < maxTalkNum + 1; i++) {
            for (CombinationItem combinationItem : CombinationFactory.getCombination(talks.length, i)) {
                int totalMinute = 0;
                for (int index : combinationItem.getIndexOfM()) {
                    totalMinute += talks[index].getMinute();
                }
                if (session.getMinMinute() != null && totalMinute < session.getMinMinute()) {
                    break;
                }
                if (session.getMaxMinute() != null && totalMinute > session.getMaxMinute()) {
                    break;
                }
                result.add(new TalkCombination(combinationItem.getBinaryOfM(),
                        session.getMaxMinute() - totalMinute));
            }
        }
        return result;
    }

    public static List<SingleSessionCombination> genSingleSessionCombination(TalkCombination[] talkCombinations,
                                                                             SessionInfo session,
                                                                             int sessionNum,
                                                                             int maxRemainMinute) {
        List<SingleSessionCombination> result = new ArrayList<>();

        for (CombinationItem combinationItem : CombinationFactory.getCombination(talkCombinations.length, sessionNum)) {
            long resultBinary = 0;
            int totalRemainMinute = 0;
            TalkCombination[] innerTalkCombinations = new TalkCombination[sessionNum];
            int index = 0;
            for (int i : combinationItem.getIndexOfM()) {
                long tmpBinary = resultBinary & talkCombinations[i].getBinary(); //组合冲突计算
                int tmpTotalRemainMinute = totalRemainMinute + talkCombinations[i].getRemainMinute();//组合剩余时间计算
                if (tmpBinary == 0 && tmpTotalRemainMinute <= maxRemainMinute) {
                    resultBinary = resultBinary | talkCombinations[i].getBinary();
                    totalRemainMinute = tmpTotalRemainMinute;
                    innerTalkCombinations[index] = talkCombinations[i];
                    index++;
                } else {
                    break;
                }
            }
            if (index == sessionNum) {
                result.add(new SingleSessionCombination(session, innerTalkCombinations, resultBinary,
                        totalRemainMinute));
            }
        }
        return result;
    }

    public static List<MultiSessionCombination> genMultiSessionCombination() {
        return null;
    }

    private static int genMaxTalkNumForSession(TalkInfo[] talks, SessionInfo sessionInfo) {
        //All Talks Asc
        List<TalkInfo> talkList = Arrays.asList(talks);
        talkList.sort((o1, o2) -> {
            if (o1.getMinute() < o2.getMinute()) {
                return -1;
            } else if (o1.getMinute() > o2.getMinute()) {
                return 1;
            } else {
                return 0;
            }
        });

        int totalMinute = 0;
        for (int i = 0; i < talkList.size(); i++) {
            totalMinute += talkList.get(i).getMinute();
            if (totalMinute >= sessionInfo.getMaxMinute()) {
                return i + 1;
            }
        }
        return talks.length;
    }

    private static int genMinTalkNumForSession(TalkInfo[] talks, SessionInfo sessionInfo, int maxRemainMinute) {
        //All Talks Desc
        List<TalkInfo> talkList = Arrays.asList(talks);
        talkList.sort((o1, o2) -> {
            if (o1.getMinute() < o2.getMinute()) {
                return 1;
            } else if (o1.getMinute() > o2.getMinute()) {
                return -1;
            } else {
                return 0;
            }
        });

        int totalMinute = 0;
        for (int i = 0; i < talkList.size(); i++) {
            totalMinute += talkList.get(i).getMinute();
            if (sessionInfo.getMaxMinute() - totalMinute <= maxRemainMinute) {
                return i + 1;
            }
        }
        return talks.length;
    }

}
