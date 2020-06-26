package com.example.memommond.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

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
