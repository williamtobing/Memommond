package com.example.memommond;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.memommond.db.MemosDB;
import com.example.memommond.db.MemosDao;
import com.example.memommond.model.Memo;

import java.util.Date;

public class EditMemoActivity extends AppCompatActivity {
    private EditText inputMemo;
    private MemosDao dao;
    private Memo temp;
    public static final String MEMO_EXTRA_Key = "memo_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_memo);
        inputMemo = findViewById(R.id.input_memo);
        dao = MemosDB.getInstance(this).memosDao();
        if (getIntent().getExtras()!=null){
            int id = getIntent().getExtras().getInt(MEMO_EXTRA_Key,0);
            temp = dao.getMemoById(id);
            inputMemo.setText(temp.getMemoText());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_memo_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.save_memo)
            onSaveMemo();
        return super.onOptionsItemSelected(item);
    }

    private void onSaveMemo() {
        // TODO: 06/05/2019 Save memo
        String text = inputMemo.getText().toString();
        if (!text.isEmpty()) {
            long date = new Date().getTime(); // get current system time

            // if exist update els create new
            if(temp == null){
                temp = new Memo(text, date);
                dao.insertMemo(temp); // create new memo and inserted to database
            }else{
                temp.setMemoText(text);
                temp.setMemoDate(date);
                dao.updateMemo(temp); // change text, date and update memo on database
            }

            finish(); // return to MainActivity
        }
    }
}
