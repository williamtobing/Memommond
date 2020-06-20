package com.example.memommond.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "memos")
public class Memo {
    @PrimaryKey(autoGenerate = true)
    private int id; // default value
    @ColumnInfo(name = "text")
    private String memoText;
    @ColumnInfo(name = "date")
    private long memoDate;

    @Ignore //
    private boolean checked = false;

    public Memo() {
    }

    public Memo(String memoText, long memoDate) {
        this.memoText = memoText;
        this.memoDate = memoDate;
    }

    public String getMemoText() {
        return memoText;
    }

    public void setMemoText(String memoText) {
        this.memoText = memoText;
    }

    public long getMemoDate() {
        return memoDate;
    }

    public void setMemoDate(long memoDate) {
        this.memoDate = memoDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public String toString() {
        return "Memo{" +
                "id=" + id +
                ", memoDate=" + memoDate +
                '}';
    }
}
