package com.example.memommond.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.memommond.model.Memo;

@Database(entities = Memo.class, version = 1)
public abstract class MemosDB extends RoomDatabase {
    public abstract MemosDao memosDao();

    public static final String DATABASE_NAME = "memosDb";
    private static  MemosDB instance;

    public static MemosDB getInstance(Context context) {
        if (instance == null)
            instance = Room.databaseBuilder(context, MemosDB.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        return instance;
    }
}
