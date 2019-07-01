package com.momo.basedemo.dataSource.local;

import android.support.annotation.NonNull;

import com.momo.basedemo.bean.Task;
import com.momo.basedemo.dataSource.TaskDataSource;
import com.momo.basedemo.thread.AppExecutors;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class LocalTaskDataSource implements TaskDataSource {

    private static volatile LocalTaskDataSource INSTANCE;

    private TaskDao mTasksDao;

    private AppExecutors mAppExecutors;

    public LocalTaskDataSource(@NonNull TaskDao mTasksDao, @NonNull AppExecutors mAppExecutors) {
        this.mTasksDao = mTasksDao;
        this.mAppExecutors = mAppExecutors;
    }

    public static LocalTaskDataSource getInstance(@NonNull TaskDao mTasksDao, @NonNull AppExecutors mAppExecutors) {
        if (null == INSTANCE) {
            synchronized (LocalTaskDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LocalTaskDataSource(mTasksDao, mAppExecutors);
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void getTasks(@NonNull LoadTasksCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                List<Task> tasks = mTasksDao.getTasks();
                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (tasks.isEmpty()) {
                            callback.onDataNotAvailable();
                        } else {
                            callback.onTasksLoaded(tasks);
                        }
                    }
                });
            }
        };
        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void getTask(@NonNull String taskId, @NonNull GetTaskCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Task task = mTasksDao.getTaskById(taskId);
                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (task == null) {
                            callback.onDataNotAvailable();
                        } else {
                            callback.onTaskLoaded(task);
                        }
                    }
                });
            }
        };
        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void saveTask(@NonNull Task task) {
        checkNotNull(task, "不能为空！");
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mTasksDao.insertTask(task);
            }
        };
        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void completeTask(@NonNull Task task) {
        checkNotNull(task, "不能为空！");
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mTasksDao.updateComplate(task.getId(), true);
            }
        };
        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void completeTask(@NonNull String taskId) {

    }

    @Override
    public void activateTask(@NonNull Task task) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mTasksDao.updateComplate(task.getId(), false);
            }
        };
        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void activateTask(@NonNull String taskId) {

    }

    @Override
    public void clearCompletedTasks() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mTasksDao.deleteCompletedTasks();
            }
        };
        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void refreshTasks() {

    }

    @Override
    public void deleteAllTasks() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mTasksDao.deleteAllTasks();
            }
        };
        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void deleteTask(@NonNull String taskId) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mTasksDao.deleteTaskById(taskId);
            }
        };
        mAppExecutors.diskIO().execute(runnable);
    }
}
