package com.redisson.score;

public class ScoreDto {
    private String homeTeam;
    private String awayTeam;
    private String result;
    private String group;

    public ScoreDto(String homeTeam, String awayTeam, String result, String group) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.result = result;
        this.group = group;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
