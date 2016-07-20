package com.douwifi.yiks.tz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class IndexActivity extends AppCompatActivity {
    private Button btnSet = null;
    private Button btnHistory = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        btnSet = (Button) findViewById(R.id.btnSet);
        btnHistory = (Button) findViewById(R.id.btnHistory);
        btnSet.setOnClickListener(new BtnClickListener());
        btnHistory.setOnClickListener(new BtnClickListener());
    }

    class BtnClickListener implements View.OnClickListener{
        public void onClick(View v){
            switch (v.getId()){
                case R.id.btnSet: {
                    Intent intent = new Intent(IndexActivity.this, MainActivity.class);
                    startActivity(intent);
                    break;
                }
                case R.id.btnHistory: {
                    Intent intent = new Intent(IndexActivity.this, HistoryActivity.class);
                    startActivity(intent);
                    break;
                }
            }
        }
    }
}
