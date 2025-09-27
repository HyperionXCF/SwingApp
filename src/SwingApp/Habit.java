package SwingApp;

public class Habit {
    private String name;
    private String startDate;
    private int streak;

    public Habit(String name, String startDate) {
        this.name = name;
        this.startDate = startDate;
        this.streak = 0;
    }

    public String getName() { return name; }
    public void setName(String n) { this.name = n; }
    public String getStartDate() { return startDate; }
    public int getStreak() { return streak; }

    public void incrementStreak() { streak++; }
    public void resetStreak() { streak = 0; }
}
