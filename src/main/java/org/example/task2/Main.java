package org.example.task2;

import org.example.task3.TaskEntity;

import java.util.List;

import static org.example.task3.TaskApp.getOpenTasksForUser;

public class Main {
    public static void main(String[] args) {
        int userId = 1;
        List<TaskEntity> openTasks = getOpenTasksForUser(userId);

        if (openTasks != null) {
            System.out.println("Open tasks for User ID " + userId + ":");
            for (TaskEntity task : openTasks) {
                System.out.println(task.getId() + ": " + task.getTitle());
            }
        } else {
            System.out.println("Failed to retrieve open tasks for User ID " + userId);
        }
    }
}
