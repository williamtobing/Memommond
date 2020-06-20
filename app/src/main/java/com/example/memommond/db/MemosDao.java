package com.example.memommond.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.memommond.model.Memo;
import java.util.List;

@Dao
public interface MemosDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE) // if the Memo exist replace it
    void insertMemo(Memo memo);

    @Delete
    void deleteMemo(Memo memo);

    @Update
    void updateMemo(Memo memo);

    // list all memo from database
    @Query("SELECT * FROM memos")
    List<Memo> getMemos();

    @Query("SELECT * FROM memos WHERE id = :memoId")
        // get memo by Id
    Memo getMemoById(int memoId);

    @Query("DELETE FROM memos WHERE id = :memoId")
    void deleteMemoById(int memoId);
}
