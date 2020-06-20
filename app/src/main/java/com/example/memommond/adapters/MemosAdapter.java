package com.example.memommond.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.memommond.R;
import com.example.memommond.callback.MemoEventListener;
import com.example.memommond.model.Memo;
import com.example.memommond.utils.MemoUtils;

import java.util.ArrayList;

public class MemosAdapter extends RecyclerView.Adapter<MemosAdapter.MemoHolder> {
    private Context context;
    private ArrayList<Memo> memos;
    private MemoEventListener listener;
    private boolean multiCheckMode = false;

    public MemosAdapter(Context context, ArrayList<Memo> memos) {
        this.context = context;
        this.memos = memos;
    }

    @Override
    public MemoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.memo_layout, parent, false);
        return new MemoHolder(v);
    }

    @Override
    public void onBindViewHolder(MemoHolder holder, int position) {
        final Memo memo = getMemo(position);
        if (memo != null){
            holder.memoText.setText(memo.getMemoText());
            holder.memoDate.setText(MemoUtils.dateFromLong(memo.getMemoDate()));
            // init memo click event
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onMemoClick(memo);
                }
            });

            // init memo long click
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onMemoLongClick(memo);
                    return false;
                }
            });

            // check checkBox if memo selected
            if (multiCheckMode){
                holder.checkBox.setVisibility(View.VISIBLE);
                holder.checkBox.setChecked(memo.isChecked());
            } else holder.checkBox.setVisibility(View.GONE);

        }
    }

    @Override
    public int getItemCount() {
        return memos.size();
    }

    private Memo getMemo(int position){
        return memos.get(position);
    }

    class MemoHolder extends RecyclerView.ViewHolder{
        TextView memoText, memoDate;
        CheckBox checkBox;

        public MemoHolder(View itemView){
            super(itemView);
            memoDate = itemView.findViewById(R.id.memo_date);
            memoText = itemView.findViewById(R.id.memo_text);
            checkBox = itemView.findViewById(R.id.checkBox);
        }
    }

    public void setListener(MemoEventListener listener){
        this.listener = listener;
    }

    public void setMultiCheckMode(boolean multiCheckMode) {
        this.multiCheckMode = multiCheckMode;
        notifyDataSetChanged();
    }
}
