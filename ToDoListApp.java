import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.time.LocalDateTime;
import java.time.LocalDate;

public class ToDoListApp extends Application {

    private ObservableList<TaskItem> tasks;
    private int taskCounter = 1;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("TaskFlow - JavaFX To-Do List");

        tasks = FXCollections.observableArrayList();
        ListView<TaskItem> todoListView = new ListView<>(tasks);
        
        // UI Components
        TextField taskInput = new TextField();
        taskInput.setPromptText("Enter task description...");
        DatePicker datePicker = new DatePicker();
        datePicker.setPromptText("Select due date");
        
        Button addButton = new Button("Add Task");
        Button deleteButton = new Button("Delete");
        Button completeButton = new Button("Mark Complete");
        Button editButton = new Button("Update Task");

        // Logic: Add Task
        addButton.setOnAction(e -> {
            String desc = taskInput.getText().trim();
            if (!desc.isEmpty()) {
                tasks.add(new TaskItem(taskCounter++, desc, LocalDateTime.now(), datePicker.getValue()));
                taskInput.clear();
                datePicker.setValue(null);
            }
        });

        // Logic: Delete Task
        deleteButton.setOnAction(e -> {
            TaskItem selected = todoListView.getSelectionModel().getSelectedItem();
            if (selected != null) tasks.remove(selected);
        });

        // Logic: Mark Complete
        completeButton.setOnAction(e -> {
            TaskItem selected = todoListView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                selected.setCompleted(true);
                todoListView.refresh();
            }
        });

        // Logic: Update Task (Includes Date Update)
        editButton.setOnAction(event -> {
            int selectedIndex = todoListView.getSelectionModel().getSelectedIndex();
            if (selectedIndex != -1) {
                String editedDesc = taskInput.getText().trim();
                LocalDate editedDate = datePicker.getValue();
                
                if (!editedDesc.isEmpty()) {
                    TaskItem selectedTask = tasks.get(selectedIndex);
                    selectedTask.setDescription(editedDesc);
                    selectedTask.setDueDate(editedDate);
                    
                    taskInput.clear();
                    datePicker.setValue(null);
                    todoListView.refresh(); 
                }
            }
        });

        // UI Sync: When a task is clicked, fill the fields automatically
        todoListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                taskInput.setText(newVal.getDescription());
                datePicker.setValue(newVal.getDueDate());
            }
        });

        // Layout Organization
        HBox inputBox = new HBox(10, taskInput, datePicker, addButton);
        HBox actionBox = new HBox(10, editButton, completeButton, deleteButton);
        VBox controlsBox = new VBox(15, inputBox, actionBox);
        controlsBox.setPadding(new Insets(20));

        BorderPane root = new BorderPane();
        root.setTop(controlsBox);
        root.setCenter(todoListView);

        // Background Image Scaling (Cover Mode)
        try {
            Image backgroundImage = new Image(getClass().getResourceAsStream("/todolist.jpeg"));
            BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, false, true);
            BackgroundImage background = new BackgroundImage(
                    backgroundImage, 
                    BackgroundRepeat.NO_REPEAT, 
                    BackgroundRepeat.NO_REPEAT, 
                    BackgroundPosition.CENTER, 
                    backgroundSize
            );
            root.setBackground(new Background(background));
        } catch (Exception e) {
            System.out.println("Could not load background image. Ensure 'todolist.jpeg' is in the source folder.");
        }

        // Semi-transparent style for the list to see background
        todoListView.setStyle("-fx-background-color: rgba(255, 255, 255, 0.6); -fx-background-insets: 10;");

        Scene scene = new Scene(root, 900, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}