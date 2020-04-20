package game;

public class ScoreEntry {

    private String userName;
    private int score;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public ScoreEntry(String userName, int score) {
        this.userName = userName;
        this.score = score;
    }
}
