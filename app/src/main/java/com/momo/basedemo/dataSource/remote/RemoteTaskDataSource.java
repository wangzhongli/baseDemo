package com.momo.basedemo.dataSource.remote;

import android.os.Handler;
import android.support.annotation.NonNull;

import com.google.common.collect.Lists;
import com.momo.basedemo.bean.Task;
import com.momo.basedemo.dataSource.TaskDataSource;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class RemoteTaskDataSource implements TaskDataSource {

    private static RemoteTaskDataSource INSTANCE;

    private static final int SERVICE_LATENCY_IN_MILLIS = 5000; //模拟网络延迟

    private static final Map<String, Task> TASKS_SERVICE_DATA;

    static {
        TASKS_SERVICE_DATA = new LinkedHashMap<>(2);
        addTask("111", "aaa");
        addTask("222", "bbb");
        addTask("333", "ccc");
    }

    private RemoteTaskDataSource() {
    }

    public static RemoteTaskDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RemoteTaskDataSource();
        }
        return INSTANCE;
    }


    private static void addTask(String title, String description) {
        Task newTask = new Task(title, description);
        TASKS_SERVICE_DATA.put(newTask.getId(), newTask);
    }

    @Override
    public void getTasks(@NonNull LoadTasksCallback callback) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                callback.onTasksLoaded(Lists.newArrayList(TASKS_SERVICE_DATA.values()));
            }
        }, SERVICE_LATENCY_IN_MILLIS);
    }

    @Override
    public void getTask(@NonNull String taskId, @NonNull GetTaskCallback callback) {
        final Task task = TASKS_SERVICE_DATA.get(taskId);

        // Simulate network by delaying the execution.
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                callback.onTaskLoaded(task);
            }
        }, SERVICE_LATENCY_IN_MILLIS);
    }

    @Override
    public void saveTask(@NonNull Task task) {
        TASKS_SERVICE_DATA.put(task.getId(), task);
    }


    @Override
    public void completeTask(@NonNull Task task) {
        Task completedTask = new Task(task.getId(), task.getDescription(), task.getTitle(), true);
        TASKS_SERVICE_DATA.put(task.getId(), completedTask);
    }

    @Override
    public void completeTask(@NonNull String taskId) {

    }

    @Override
    public void activateTask(@NonNull Task task) {
        Task completedTask = new Task(task.getId(), task.getDescription(), task.getTitle(), false);
        TASKS_SERVICE_DATA.put(task.getId(), completedTask);

    }

    @Override
    public void activateTask(@NonNull String taskId) {

    }

    @Override
    public void clearCompletedTasks() {
        Set<Map.Entry<String, Task>> entries = TASKS_SERVICE_DATA.entrySet();
        Iterator<Map.Entry<String, Task>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Task> entry = iterator.next();
            if (entry.getValue().isCompleted()) {
                iterator.remove();
            }

        }
    }

    @Override
    public void refreshTasks() {

    }

    @Override
    public void deleteAllTasks() {
        TASKS_SERVICE_DATA.clear();
    }

    @Override
    public void deleteTask(@NonNull String taskId) {
        TASKS_SERVICE_DATA.remove(taskId);
    }
}
