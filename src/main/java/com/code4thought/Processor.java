package com.code4thought;

import com.code4thought.combination.CombinationFactory;
import com.code4thought.combination.CombinationItem;

import java.util.*;

public class Processor {

    public static TalkCombination[] genTalkCombinationForSession(TalkInfo[] talks, SessionInfo session,
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
                    continue;
                }
                if (session.getMaxMinute() != null
                        && (totalMinute > session.getMaxMinute()
                        || session.getMaxMinute() - totalMinute > maxRemainMinute)) {
                    continue;
                }
                result.add(new TalkCombination(combinationItem.getBinaryOfM(),
                        session.getMaxMinute() - totalMinute));
            }
        }
        return result.toArray(new TalkCombination[0]);
    }

    public static SingleSessionCombination[] genSingleSessionCombination(TalkCombination[] talkCombinations,
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
        return result.toArray(new SingleSessionCombination[0]);
    }

    public static List<MultiSessionCombination> genMultiSessionCombination(
            Map<SessionInfo, SingleSessionCombination[]> singleCombinations, int maxRemainMinute, long allTalksBinary) {

        List<MultiSessionCombination> result = new ArrayList<>();

        long allCombinationNum = 1;
        int[] singleSingleSessionMaxIndex = new int[singleCombinations.size()];
        int[] currentSingleSessionIndex = new int[singleCombinations.size()];
        SingleSessionCombination[][] singleSessionValue = new SingleSessionCombination[singleCombinations.size()][];
        int index = -1;
        for (Map.Entry<SessionInfo, SingleSessionCombination[]> entry : singleCombinations.entrySet()) {
            index++;

            singleSessionValue[index] = entry.getValue();
            singleSingleSessionMaxIndex[index] = entry.getValue().length - 1;
            allCombinationNum *= entry.getValue().length;
        }

        for (long i = 0; i < allCombinationNum; i++) {
            MultiSessionCombination multiSessionCombination = genMultiSessionCombination(currentSingleSessionIndex,
                    singleSessionValue, maxRemainMinute, allTalksBinary);
            if (multiSessionCombination != null) {
                result.add(multiSessionCombination);
            }
            for (int j = 0; j < currentSingleSessionIndex.length; j++) {
                if (currentSingleSessionIndex[j] < singleSingleSessionMaxIndex[j]) {
                    currentSingleSessionIndex[j]++;
                    break;
                } else {
                    currentSingleSessionIndex[j] = 0;
                }
            }
        }

        return result;
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

    private static MultiSessionCombination genMultiSessionCombination(int[] currentSingleSessionIndex,
                                                                      SingleSessionCombination[][] singleSessionValue,
                                                                      int maxRemainMinute, long allTalksBinary) {

        Map<SessionInfo, SingleSessionCombination> result = new HashMap<>();

        long currentBinary = 0;
        int totalRemainMinute = 0;

        for (int i = 0; i < currentSingleSessionIndex.length; i++) {
            SingleSessionCombination s = singleSessionValue[i][currentSingleSessionIndex[i]];
            totalRemainMinute += s.getTotalRemainMinute();
            if ((currentBinary & s.getBinary()) == 0 && totalRemainMinute <= maxRemainMinute) {
                currentBinary = currentBinary | s.getBinary();
                result.put(s.getSession(), s);
            } else {
                return null;
            }
        }

        if (currentBinary != allTalksBinary) {
            return null;
        }
        return new MultiSessionCombination(result);
    }

}
