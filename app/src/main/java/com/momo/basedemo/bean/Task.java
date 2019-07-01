package com.momo.basedemo.bean;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.common.base.Objects;
import com.google.common.base.Strings;

import java.util.UUID;


@Entity(tableName = "tasks")
public final class Task {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "entryid")
    private final String mId;

    @Nullable
    @ColumnInfo(name = "title")
    private final String mTitle;

    @Nullable
    @ColumnInfo(name = "description")
    private final String mDescription;

    @ColumnInfo(name = "completed")
    private final boolean mCompleted;

    @Ignore
    public Task(@Nullable String mTitle, @Nullable String mDescription) {
        this(UUID.randomUUID().toString(), mTitle, mDescription, false);
    }

    @Ignore
    public Task(@Nullable String mId, @Nullable String mTitle, @Nullable String mDescription) {
        this(mId, mTitle, mDescription, false);
    }

    @Ignore
    public Task(@Nullable String mTitle, @Nullable String mDescription, boolean mCompleted) {
        this(UUID.randomUUID().toString(), mTitle, mDescription, mCompleted);
    }

    public Task(String mId, @Nullable String mTitle, @Nullable String mDescription, boolean mCompleted) {
        this.mId = mId;
        this.mTitle = mTitle;
        this.mDescription = mDescription;
        this.mCompleted = mCompleted;
    }

    @NonNull
    public String getId() {
        return mId;
    }

    @Nullable
    public String getTitle() {
        return mTitle;
    }

    @Nullable
    public String getDescription() {
        return mDescription;
    }

    @Nullable
    public String getTitleForList() {
        if (!Strings.isNullOrEmpty(mTitle)) {
            return mTitle;
        } else {
            return mDescription;
        }
    }

    public boolean isCompleted() {
        return mCompleted;
    }


    public boolean isEmpty() {
        return Strings.isNullOrEmpty(mTitle) &&
                Strings.isNullOrEmpty(mDescription);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Task task = (Task) obj;
        return Objects.equal(mId, task.mId) &&
                Objects.equal(mTitle, task.mTitle) &&
                Objects.equal(mDescription, task.mDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mId, mTitle, mDescription);
    }

    @NonNull
    @Override
    public String toString() {
        return "Task with title " + mTitle;
    }
}
