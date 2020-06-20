package com.example.memommond.callback;

import com.example.memommond.model.Memo;

public interface MemoEventListener {

    /**
     * call when memo clicked
     * @param memo : memo item
     */
    void onMemoClick(Memo memo);

    /**
     * call when long click to memo
     * @param memo : item
     */
    void onMemoLongClick(Memo memo);

}
