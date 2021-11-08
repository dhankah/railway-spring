package com.mospan.railwayspring.util.trips_creator;

import java.util.Timer;

/**
 * Schedules a task for creating trips automatically
 */
public class TaskExecutor {
    public static void main (String[] args) {
        Timer timer = new Timer();
        Task task = new Task();
        timer.schedule(task, 1000);
    }
}
