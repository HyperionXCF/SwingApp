// File: Todo.java
package SwingApp;
public class Todo {
    private String task;
    private boolean done;

    public Todo(String task) {
        this.task = task;
        this.done = false;
    }

    public String getTask() { return task; }
    public boolean isDone() { return done; }
    public void toggleDone() { done = !done; }
}
