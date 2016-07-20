package com.douwifi.yiks.tz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.douwifi.yiks.tz.model.History;

import java.io.Serializable;
import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    private HistoryAdapter mAdatper;
    private GridView mGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        HistoryDatabaseHelper dbHelper = new HistoryDatabaseHelper(this);
        ArrayList<History> histories = dbHelper.getAll();


        mGridView = (GridView)findViewById(R.id.gvFileChooser);
        mGridView.setEmptyView(findViewById(R.id.tvEmptyHint));
        mAdatper = new HistoryAdapter(this, histories);
        mGridView.setAdapter(mAdatper);
        mGridView.setOnItemClickListener(mItemClickListener);
    }

    private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            view.setSelected(true);
            History history = ((HistoryAdapter)parent.getAdapter()).getItem(position);

            //打开DisplayResultActivity
            Intent intent = new Intent(HistoryActivity.this, DisplayResultActivity.class);
            intent.putExtra(DisplayResultActivity.EXTRA_TYPE, 1);
            intent.putExtra(DisplayResultActivity.EXTRA_LIST_DATA, (Serializable) history.getResult());
            startActivity(intent);
        }
    };
}
