package com.code4thought;

public class SessionInfo {

    private String title;

    private Integer maxMinute;

    private Integer minMinute;

    private volatile int hashCode;

    public SessionInfo(String title, Integer maxMinute, Integer minMinute) {
        this.title = title;
        this.maxMinute = maxMinute;
        this.minMinute = minMinute;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof SessionInfo)) {
            return false;
        }
        SessionInfo other = (SessionInfo) o;
        return other.maxMinute == this.maxMinute && other.minMinute == this.minMinute;
    }

    @Override
    public int hashCode() {
        int result = hashCode;

        if (result == 0) {
            result = 17;
            result = 31 * result + (maxMinute == null ? 0 : maxMinute);
            result = 31 * result + (minMinute == null ? 0 : minMinute);
            hashCode = result;
        }
        return result;
    }

    public String getTitle() {
        return title;
    }

    public Integer getMaxMinute() {
        return maxMinute;
    }

    public Integer getMinMinute() {
        return minMinute;
    }
}
