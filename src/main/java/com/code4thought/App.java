package com.code4thought;

import com.code4thought.combination.CombinationFactory;
import com.code4thought.combination.CombinationItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello World!");

        //2147483647

        int a = -new Double(Math.pow(2, 5)).intValue();

        System.out.println(a);

        System.out.println(Integer.toBinaryString(a));

        int b = a >>> 1;

        System.out.println(b);

        System.out.println(Integer.toBinaryString(b));

        CombinationItem[] combinationItems = CombinationFactory.getCombination(5, 2);

        combinationItems = CombinationFactory.getCombination(5, 2);


        List<Integer> list = new ArrayList<>();

        list.add(1);
        list.add(3);
        list.add(2);
        list.add(10);
        list.add(5);
        list.add(7);
        list.add(7);
        list.add(7);

        list.sort((o1, o2) -> {
            if (o1 < o2) {
                return 1;
            } else if (o1 > o2) {
                return -1;
            } else {
                return 0;
            }
        });


        return;


    }

    /**
     * 检查
     *
     * @param sessionInfos
     * @param talkInfos
     * @throws Exception 给出信息无法进行处理，错误内容
     */
    private static void checkInfo(List<SessionInfo> sessionInfos, List<TalkInfo> talkInfos) throws Exception {

        //if total minute large than sessions
        int totalTalkMinutes = 0;
        int totalSessionMinutes = 0;
        int maxTalkMinute = 0;
        int maxSessionMinute = 0;
        for (TalkInfo talkInfo : talkInfos) {
            totalTalkMinutes += talkInfo.getMinute();
            if (maxTalkMinute < talkInfo.getMinute()) {
                maxTalkMinute = talkInfo.getMinute();
            }
        }
        for (SessionInfo sessionInfo : sessionInfos) {
            totalSessionMinutes += sessionInfo.getMaxMinute();
            if (maxSessionMinute < sessionInfo.getMaxMinute()) {
                maxSessionMinute = sessionInfo.getMaxMinute();
            }
        }

        if (totalSessionMinutes < totalTalkMinutes) {
            throw new Exception("Session Time Not Enough");
        }

        if (maxSessionMinute < maxTalkMinute) {
            throw new Exception("There Is A Talk Can Not Be Allocated");
        }

    }

    /**
     * 获取会场信息
     *
     * @return 会场信息
     */
    private static Map<SessionInfo, Integer> getSessionInfos() {
        Map<SessionInfo, Integer> sessionInfos = new HashMap<>();
        SessionInfo morningSession = new SessionInfo(180, null);
        sessionInfos.put(morningSession, 2);

        SessionInfo afternoonSession = new SessionInfo(239, 180);
        sessionInfos.put(afternoonSession, 2);

        return sessionInfos;
    }

    /**
     * 获取演讲信息
     *
     * @return 演讲信息
     */
    private static List<TalkInfo> getTalkInfos() {
        List<TalkInfo> talkInfos = new ArrayList<>();

        talkInfos.add(new TalkInfo("Writing Fast Tests Against Enterprise Rails", 60));
        talkInfos.add(new TalkInfo("Overdoing it in Python", 45));
        talkInfos.add(new TalkInfo("Lua for the Masses", 30));
        talkInfos.add(new TalkInfo("Ruby Errors from Mismatched Gem Versions", 45));
        talkInfos.add(new TalkInfo("Common Ruby Errors", 45));
        talkInfos.add(new TalkInfo("Rails for Python Developers", 5));
        talkInfos.add(new TalkInfo("Communicating Over Distance", 60));
        talkInfos.add(new TalkInfo("Accounting-Driven Development", 45));
        talkInfos.add(new TalkInfo("Woah", 30));
        talkInfos.add(new TalkInfo("Sit Down and Write", 30));
        talkInfos.add(new TalkInfo("Pair Programming vs Noise", 45));
        talkInfos.add(new TalkInfo("Rails Magic", 60));
        talkInfos.add(new TalkInfo("Ruby on Rails: Why We Should Move On", 60));
        talkInfos.add(new TalkInfo("Clojure Ate Scala (on my project)", 45));
        talkInfos.add(new TalkInfo("Programming in the Boondocks of Seattle", 30));
        talkInfos.add(new TalkInfo("Ruby vs. Clojure for Back-End Development", 30));
        talkInfos.add(new TalkInfo("Ruby on Rails Legacy App Maintenance", 60));
        talkInfos.add(new TalkInfo("A World Without HackerNews", 30));
        talkInfos.add(new TalkInfo("User Interface CSS in Rails Apps", 30));

        return talkInfos;
    }
}
