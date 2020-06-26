package com.example.memommond.db;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
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
