/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.momo.basedemo.dataSource;

import android.content.Context;
import android.support.annotation.NonNull;


import com.momo.basedemo.dataSource.local.LocalTaskDataSource;
import com.momo.basedemo.dataSource.local.MyDatabase;
import com.momo.basedemo.dataSource.remote.RemoteTaskDataSource;
import com.momo.basedemo.thread.AppExecutors;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Enables injection of mock implementations for
 * {@link TaskDataSource} at compile time. This is useful for testing, since it allows us to use
 * a fake instance of the class to isolate the dependencies and run a test hermetically.
 */
public class Injection {

    public static TasksRepository provideTasksRepository(@NonNull Context context) {
        checkNotNull(context);
        MyDatabase database = MyDatabase.getInstance(context);
        return TasksRepository.getInstance(RemoteTaskDataSource.getInstance(),
                LocalTaskDataSource.getInstance(
                        database.taskDao(), new AppExecutors()));
    }
}
