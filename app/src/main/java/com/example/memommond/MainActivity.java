package com.example.memommond;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ActionMode;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.memommond.adapters.MemosAdapter;
import com.example.memommond.callback.MainActionModeCallback;
import com.example.memommond.callback.MemoEventListener;
import com.example.memommond.db.MemosDB;
import com.example.memommond.db.MemosDao;
import com.example.memommond.model.Memo;
import com.example.memommond.utils.MemoUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.memommond.EditMemoActivity.MEMO_EXTRA_Key;

public class MainActivity extends AppCompatActivity implements MemoEventListener {
    private static final String TAG = "MainActivity";
    private RecyclerView recyclerView;
    private ArrayList<Memo> memos;
    private MemosAdapter adapter;
    private MemosDao dao;
    private MainActionModeCallback actionModeCallback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // init recyclerView
        recyclerView = findViewById(R.id.memos_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // init fab Button
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                // TODO: 06/05/2018 add new memo
                onAddNewMemo();
            }
        });

        dao = MemosDB.getInstance(this).memosDao();
    }

    private void loadMemos() {
//        for (int i = 0; i < 12; i++){
//            memos.add(new Memo("This is a demo mommon. This is a demo mommon. This is a demo mommon...",
//            new Date().getTime()));
//        }
        this.memos = new ArrayList<>();
        List<Memo> list = dao.getMemos(); // get all memo from database
        this.memos.addAll(list);
        this.adapter = new MemosAdapter(this, this.memos);
        this.adapter.setListener(this);
        this.recyclerView.setAdapter(adapter);
        //adapter.notifyDataSetChanged();
    }

    private void onAddNewMemo(){
        // TODO: 06/05/2019 start EditMemoActivity
        startActivity(new Intent(this, EditMemoActivity.class));
//        if (memos !=null)
//            memos.add(new Memo("This is new memo ", new Date().getTime()));
//        if (adapter !=null)
//            adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadMemos();
    }

    @Override
    public void onMemoClick(Memo memo) {
        // TODO: 07/05/2019 memo clicked : edit memo
        //Toast.makeText(this, memo.getId(), Toast.LENGTH_SHORT).show();
        //Log.d(TAG, "onMemoClick: " + memo.toString());
        Intent edit = new Intent(this, EditMemoActivity.class);
        edit.putExtra(MEMO_EXTRA_Key, memo.getId());
        startActivity(edit);
    }

    @Override
    public void onMemoLongClick(final Memo memo) {
        // TODO: 07/05/2019 memo long clicked : delete, share..
        //Log.d(TAG, "onMemoLongClick" + memo.getId());

//        memo.setChecked(true);
//        adapter.setMultiCheckMode(true);
//        actionModeCallback = new MainActionModeCallback() {
//            @Override
//            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
//                // set new listener
//                adapter.setListener(new MemoEventListener() {
//                    @Override
//                    public void onMemoClick(Memo memo) {
//                        memo.setChecked(!memo.isChecked()); // inverse selected
//                        adapter.notifyDataSetChanged();
//                    }
//
//                    @Override
//                    public void onMemoLongClick(Memo memo) {
//
//                    }
//                });
//                return false;
//            }
//        };
//
//        // start action mode
//
//        startActionMode(actionModeCallback);

        //////////////////////

        new AlertDialog.Builder(this)
                .setTitle(R.string.app_name)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO: 09/05/2019 delete memo from database and refresh
                        dao.deleteMemo(memo); //delete
                        loadMemos(); // refresh memos
                    }
                }).setNegativeButton("Share", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO: 09/05/2019 share memo text
                Intent share = new Intent(Intent.ACTION_SEND);
                // logic to share memo
                String text = memo.getMemoText()+"\n Create on :"+
                        MemoUtils.dateFromLong(memo.getMemoDate());
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, text);
                startActivity(share);
            }
        })
                .create()
                .show();
    }

    @Override
    public void onActionModeFinished(ActionMode mode) {
        super.onActionModeFinished(mode);

        adapter.setMultiCheckMode(false);
        adapter.setListener(this); // set back the old listener
    }
}
