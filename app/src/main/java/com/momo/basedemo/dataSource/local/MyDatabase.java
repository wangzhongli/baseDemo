package com.momo.basedemo.dataSource.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.momo.basedemo.bean.Task;


@Database(entities = {Task.class}, version = 1, exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {

    private static final String DB_NAME = "Tasks.db";

    private static MyDatabase INSTANCE;

    public abstract TaskDao taskDao();

    private static final Object sLock = new Object();

    public static MyDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), MyDatabase.class, DB_NAME)
                        .addMigrations()
//                        .allowMainThreadQueries()//允许在主线程查询数据
                        .build();
            }
        }
        return INSTANCE;
    }
}
