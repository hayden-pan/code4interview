package com.code4thought;

import com.code4thought.combination.CombinationFactory;
import com.code4thought.combination.CombinationItem;

import java.util.*;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello World!");

        run();
    }

    public static void run() throws Exception {
        TalkInfo[] talks = getTalks();
        long allTalksBinary = getAllTalksBinary(talks);
        Map<SessionInfo, Integer> sessions = getSessions();
        int maxRemainMinute = checkInfo(sessions, talks);
        //Generate All Talk Combination For All Session
        Map<SessionInfo, TalkCombination[]> talkCombinations = new HashMap<>();
        for (Map.Entry<SessionInfo, Integer> entry : sessions.entrySet()) {
            TalkCombination[] talkCombination =
                    Processor.genTalkCombinationForSession(talks, entry.getKey(), maxRemainMinute);
            talkCombinations.put(entry.getKey(), talkCombination);
        }

        Map<SessionInfo, SingleSessionCombination[]> singleSessionCombinations = new HashMap<>();
        for (Map.Entry<SessionInfo, TalkCombination[]> entry : talkCombinations.entrySet()) {
            int sessionNum = sessions.get(entry.getKey());
            SingleSessionCombination[] singleSessionCombination =
                    Processor.genSingleSessionCombination(entry.getValue(), entry.getKey(), sessionNum, maxRemainMinute);
            singleSessionCombinations.put(entry.getKey(), singleSessionCombination);
        }

        List<MultiSessionCombination> result =
                Processor.genMultiSessionCombination(singleSessionCombinations, maxRemainMinute, allTalksBinary);

        return;
    }

    /**
     * 检查
     *
     * @param sessions
     * @param talks
     * @return MaxRemainMinute
     * @throws Exception 给出信息无法进行处理，错误内容
     */
    private static int checkInfo(Map<SessionInfo, Integer> sessions, TalkInfo[] talks) throws Exception {

        //if total minute large than sessions
        int totalTalkMinutes = 0;
        int totalSessionMinutes = 0;
        int maxTalkMinute = 0;
        int maxSessionMinute = 0;
        for (TalkInfo talkInfo : talks) {
            totalTalkMinutes += talkInfo.getMinute();
            if (maxTalkMinute < talkInfo.getMinute()) {
                maxTalkMinute = talkInfo.getMinute();
            }
        }

        for (Map.Entry<SessionInfo, Integer> entry : sessions.entrySet()) {
            totalSessionMinutes += entry.getKey().getMaxMinute() * entry.getValue();
            if (maxSessionMinute < entry.getKey().getMaxMinute()) {
                maxSessionMinute = entry.getKey().getMaxMinute();
            }
        }

        if (totalSessionMinutes < totalTalkMinutes) {
            throw new Exception("Session Time Not Enough");
        }

        if (maxSessionMinute < maxTalkMinute) {
            throw new Exception("There Is A Talk Can Not Be Allocated");
        }

        return totalSessionMinutes - totalTalkMinutes;

    }

    /**
     * 获取会场信息
     *
     * @return 会场信息
     */
    private static Map<SessionInfo, Integer> getSessions() {
        Map<SessionInfo, Integer> sessions = new HashMap<>();
        SessionInfo morningSession = new SessionInfo(180, null);
        sessions.put(morningSession, 2);

        SessionInfo afternoonSession = new SessionInfo(239, 180);
        sessions.put(afternoonSession, 2);

        return sessions;
    }

    /**
     * 获取演讲信息
     *
     * @return 演讲信息
     */
    private static TalkInfo[] getTalks() {
        List<TalkInfo> talks = new ArrayList<>();

        talks.add(new TalkInfo("Writing Fast Tests Against Enterprise Rails", 60));
        talks.add(new TalkInfo("Overdoing it in Python", 45));
        talks.add(new TalkInfo("Lua for the Masses", 30));
        talks.add(new TalkInfo("Ruby Errors from Mismatched Gem Versions", 45));
        talks.add(new TalkInfo("Common Ruby Errors", 45));
        talks.add(new TalkInfo("Rails for Python Developers", 5));
        talks.add(new TalkInfo("Communicating Over Distance", 60));
        talks.add(new TalkInfo("Accounting-Driven Development", 45));
        talks.add(new TalkInfo("Woah", 30));
        talks.add(new TalkInfo("Sit Down and Write", 30));
        talks.add(new TalkInfo("Pair Programming vs Noise", 45));
        talks.add(new TalkInfo("Rails Magic", 60));
        talks.add(new TalkInfo("Ruby on Rails: Why We Should Move On", 60));
        talks.add(new TalkInfo("Clojure Ate Scala (on my project)", 45));
        talks.add(new TalkInfo("Programming in the Boondocks of Seattle", 30));
        talks.add(new TalkInfo("Ruby vs. Clojure for Back-End Development", 30));
        talks.add(new TalkInfo("Ruby on Rails Legacy App Maintenance", 60));
        talks.add(new TalkInfo("A World Without HackerNews", 30));
        talks.add(new TalkInfo("User Interface CSS in Rails Apps", 30));

        return talks.toArray(new TalkInfo[talks.size()]);
    }

    /**
     * @param talks
     * @return
     */
    private static long getAllTalksBinary(TalkInfo[] talks) {
        long result = 0;
        for (int i = 0; i < talks.length; i++) {
            result += Math.pow(2, i);
        }
        return result;
    }
}
