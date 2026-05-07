import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TaskItem {
    private int number;
    private String description;
    private LocalDateTime timestamp;
    private LocalDate dueDate;
    private boolean completed;

    public TaskItem(int number, String description, LocalDateTime timestamp, LocalDate dueDate) {
        this.number = number;
        this.description = description;
        this.timestamp = timestamp;
        this.dueDate = dueDate;
        this.completed = false;
    }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }
    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTimestamp = timestamp.format(formatter);
        String formattedDueDate = (dueDate != null) ? dueDate.toString() : "No Due Date";
        String status = completed ? " (Completed)" : "";
        return String.format("%d. %s | Created: %s | Due: %s%s", 
                number, description, formattedTimestamp, formattedDueDate, status);
    }
}