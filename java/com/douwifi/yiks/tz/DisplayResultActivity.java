package com.douwifi.yiks.tz;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.douwifi.yiks.tz.model.DetaTime;
import com.douwifi.yiks.tz.model.Result;
import com.douwifi.yiks.tz.util.SerializerClass;
import com.douwifi.yiks.tz.util.TimeUtil;

public class DisplayResultActivity extends AppCompatActivity {
    public static final String EXTRA_LIST_DATA = "DisplayResultActivity_list_data";
    public static final String EXTRA_TYPE = "DisplayResultActivity_type";
    public static final String EXTRA_FILE_NO_EXISTS = "DisplayResultActivity_FILE_NO_EXISTS";

    private DisplayResultAdapter mAdatper ;
    private GridView mGridView;

    private int type;//从哪个Acitvity进入：0=MainActivity，1=HistoryActivity
    private Result result;

    private Button btnBack;
    private Button btnClear;
    private Button btnSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_result);

        btnBack = (Button) findViewById(R.id.btnBack);
        btnClear = (Button) findViewById(R.id.btnClear);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnBack.setOnClickListener(new BtnClickListener());
        btnClear.setOnClickListener(new BtnClickListener());
        btnSave.setOnClickListener(new BtnClickListener());


        Intent intent = getIntent();

        result = (Result)intent.getSerializableExtra(DisplayResultActivity.EXTRA_LIST_DATA);

        TextView tvDetaTimes = (TextView)findViewById(R.id.tvDetaTimes);
        StringBuffer sb = new StringBuffer();
        for(DetaTime dt:result.getDetaTimes()){
            sb.append(TimeUtil.TimeStamp2Date(dt.getStartTime(), "HH:mm:ss"));
            sb.append("—");
            sb.append(TimeUtil.TimeStamp2Date(dt.getEndTime(), "HH:mm:ss"));
            sb.append("\n");
        }
        String stamas = "";
        if(sb.length()>0){
            stamas = sb.substring(0, sb.length()-1);
        }
        tvDetaTimes.setText(stamas);

        TextView tvEmptyHint = (TextView)findViewById(R.id.tvEmptyHint);
        int flag = intent.getIntExtra(DisplayResultActivity.EXTRA_FILE_NO_EXISTS, 0);
        if(flag==1)
            tvEmptyHint.setText(getText(R.string.file_no_exists));
        mGridView = (GridView)findViewById(R.id.gvFileChooser);
        mGridView.setEmptyView(tvEmptyHint);

        mAdatper = new DisplayResultAdapter(this, result.getStamacs());
        mGridView.setAdapter(mAdatper);

        type = intent.getIntExtra(DisplayResultActivity.EXTRA_TYPE, 0);
        btnSave.setEnabled(type==0 && result.getStamacs().size()>0);
    }

    class BtnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnBack:
                    finish();
                    break;
                case R.id.btnClear:

                    break;
                case R.id.btnSave:
                    save2History();
                    break;
            }
        }
    }
    //保存到历史记录
    private void save2History(){
        inputTitleDialog();
//        HistoryDatabaseHelper dbHelper = new HistoryDatabaseHelper(DisplayResultActivity.this);
//        byte[] bytes = SerializerClass.serializeObject(this.resultList);
//        Long time = System.currentTimeMillis();
//        dbHelper.insert(bytes, time);
//        toast(getText(R.string.save_history_success));
//        btnSave.setEnabled(false);
    }


    private void inputTitleDialog() {

        final EditText inputServer = new EditText(this);
        inputServer.setText(TimeUtil.TimeStamp2Date(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss"));
        inputServer.setFocusable(true);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.save_history_title)).setView(inputServer);
        builder.setPositiveButton(getString(R.string.save_history_save),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String inputName = inputServer.getText().toString();

                        HistoryDatabaseHelper dbHelper = new HistoryDatabaseHelper(DisplayResultActivity.this);
                        byte[] bytes = SerializerClass.serializeObject(result);
                        Long time = System.currentTimeMillis();
                        dbHelper.insert(bytes, inputName);

                        toast(getText(R.string.save_history_success));
                        btnSave.setEnabled(false);
                    }
                });
        builder.show();
    }

    private void toast(CharSequence hint){
        Toast.makeText(this, hint , Toast.LENGTH_SHORT).show();
    }
}
