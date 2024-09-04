package dk.tec.catchingtodo.models;

import android.location.Location;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


//enum TypeTask {CLEANING, COOKING, YARDWORK, TELEVISION, PLANNING, COALMINING}


enum Priority {HIGH, NORMAL, LOW}

public class TodoItem {
    public static String[] tasktypes = {"CLEANING", "COOKING", "YARDWORK", "TELEVISION", "PLANNING", "COALMINING"};

    UUID id = java.util.UUID.randomUUID();
    String name;
    String imageBeforeFile;
    String imageAfterFile;
    String description;
    LocalDateTime plannedStartTime;
    LocalDateTime plannedEndTime;
    LocalDateTime startTime;
    LocalDateTime endTime;
    int ticksSpend;
    int tasktype;
    Location location;
    List<TodoItem> todoItemsBlockersList;
    Boolean isCompleted = false;
    Boolean isRepeatable;
    int xp;
}
