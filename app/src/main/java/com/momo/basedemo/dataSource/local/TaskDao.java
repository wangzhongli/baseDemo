package com.momo.basedemo.dataSource.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.momo.basedemo.bean.Task;

import java.util.List;


@Dao
public interface TaskDao {

    @Query("SELECT * FROM tasks")
    List<Task> getTasks();

    @Query("SELECT * FROM tasks WHERE entryid =:taskId")
    Task getTaskById(String taskId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTask(Task task);

    @Update
    int update(Task task);

    @Query("update tasks set completed = :complated where entryid  = :taskId ")
    void updateComplate(String taskId, boolean complated);

    @Query("delete from tasks where entryid = :taskId")
    int deleteTaskById(String taskId);

    /**
     * Delete all tasks.
     */
    @Query("DELETE FROM Tasks")
    void deleteAllTasks();

    /**
     * Delete all completed tasks from the table.
     *
     * @return the number of tasks deleted.
     */
    @Query("DELETE FROM Tasks WHERE completed = 1")
    int deleteCompletedTasks();
}
