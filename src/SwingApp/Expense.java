package SwingApp;

public class Expense {
    private double amount;
    private String date;
    private String category;
    private String note;

    public Expense(double amount, String date, String category, String note) {
        this.amount = amount;
        this.date = date;
        this.category = category;
        this.note = note;
    }

    public double getAmount() { return amount; }
    public String getDate() { return date; }
    public String getCategory() { return category; }
    public String getNote() { return note; }

    @Override
    public String toString() {
        return date + " - ₹" + amount + " [" + category + "] : " + note;
    }
}
