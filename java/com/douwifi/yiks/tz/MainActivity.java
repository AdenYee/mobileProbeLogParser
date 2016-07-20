package com.douwifi.yiks.tz;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.douwifi.yiks.tz.common.Config;
import com.douwifi.yiks.tz.model.DetaTime;
import com.douwifi.yiks.tz.model.Result;

import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Button btnStart = null;
    private Button btnEnd = null;
    private TextView tvTime = null;
    private ScrollView svTimeList;
    private LinearLayout llTimeList = null;
    private Button btnClear = null;
    private Button btnParse = null;

    private SQLiteDatabase db;

    private DetaTime curDetaTime;

    private ArrayList<DetaTime> detaTimes;

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        detaTimes = new ArrayList<DetaTime>();
        //initView
        tvTime = (TextView) findViewById(R.id.tvTime);
        btnStart = (Button) findViewById(R.id.btnStart);
        btnEnd = (Button) findViewById(R.id.btnEnd);
        svTimeList = (ScrollView) findViewById(R.id.svTimeList);
        llTimeList = (LinearLayout) findViewById(R.id.llTimeList);
        llTimeList.removeAllViews();
        btnParse = (Button) findViewById(R.id.btnParse);
        btnClear = (Button) findViewById(R.id.btnClear);
        btnStart.setOnClickListener(new BtnClickListener());
        btnEnd.setOnClickListener(new BtnClickListener());
        btnParse.setOnClickListener(new BtnClickListener());
        btnClear.setOnClickListener(new BtnClickListener());

        //showViews
        tvTime.setText("单击开始按钮开始计时");
        openDatabase();
        Cursor cur = db.rawQuery("select * from " + Config.TableName_Timer, null);
        cur.moveToFirst();
        if (cur.moveToFirst() != false){
            do{
                int id = cur.getInt(0);
                long startTime = cur.getLong(1);
                long endTime = cur.getLong(2);

                int selected = 1;
                final DetaTime dt = new DetaTime(id, startTime, endTime, selected);
                this.curDetaTime = dt;
                this.detaTimes.add(dt);
                addTimeItem(dt, false);
            } while (cur.moveToNext());
            cur.moveToLast();
        }
        cur.close();
        closeDatabase();

        updateBtnEnabled();
    }

    class BtnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v){
            switch (v.getId()) {
                case R.id.btnStart:
                    btnStart.setEnabled(false);
                    btnEnd.setEnabled(true);
                    long startTime = System.currentTimeMillis();
                    int id = 1;
                    if(curDetaTime!=null)
                        id = curDetaTime.getId()+1;
                    curDetaTime = new DetaTime();
                    curDetaTime.setId(id);
                    curDetaTime.setStartTime(startTime);
                    handler.post(myRunnable);
                    btnClear.setEnabled(false);
                    btnParse.setEnabled(false);
                    break;
                case R.id.btnEnd:
                    btnStart.setEnabled(true);
                    btnEnd.setEnabled(false);
                    handler.removeCallbacks(myRunnable);
                    long endTime = System.currentTimeMillis();
                    curDetaTime.setEndTime(endTime);
                    curDetaTime.setSelected(1);
                    tvTime.setText(getTimeStr(curDetaTime.getStartTime(), endTime));
                    addTimeItem(curDetaTime, true);
                    btnClear.setEnabled(true);
                    btnParse.setEnabled(true);
                    break;
                case R.id.btnClear:     //全部清除
                    doClear();
                    break;
                case R.id.btnParse:     //开始分析
                    parseFile();
                    break;
            }
        }
    }

    private void doClear(){
        new AlertDialog.Builder(this)
            .setTitle("清除提示")
            .setMessage("确定要清除选中的时间段吗?")
            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    ArrayList<Integer> ids = new ArrayList<Integer>();
                    for(int i=detaTimes.size()-1; i>=0; i--){
                        if(detaTimes.get(i).getSelected()==0)
                            continue;
                        ids.add(detaTimes.get(i).getId());
                        detaTimes.remove(i);
                        llTimeList.removeViewAt(i);
                    }
                    deleteData(ids);
                    updateBtnEnabled();
                }
            })
            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // do nothing
                }
            })
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show();
    }

    private void parseFile(){
        Intent intent = new Intent(MainActivity.this, DisplayResultActivity.class);
        //只分析选中的时间段
        ArrayList<DetaTime> detaTimes = new ArrayList<DetaTime>();
        for(DetaTime dt:this.detaTimes){
            if(dt.getSelected()==1){
                detaTimes.add(dt);
            }
        }
        /////读文件
        Result result = new Result();
        result.setDetaTimes(detaTimes);
        result.setStamacs(new ArrayList<String>());
        Map<String, ArrayList<DetaTime>> resutlMap = new HashMap<String, ArrayList<DetaTime>>();
        //扫描文件位置
        File file = null;
        for(String logPath: Config.logPaths){
            File tempFile = new File(logPath);
            if(tempFile.exists()){
                file = tempFile;
                break;
            }
        }
        if(file!=null){
            try {
                FileInputStream inputStream = new FileInputStream(file);
                byte[] b = new byte[inputStream.available()];
                inputStream.read(b);
                String content = new String(b);
                String[] lines = content.split("\\r\\n");
                for (int j = 0; j < lines.length; j++) {
                    String[] items = lines[j].split("\\t");
                    if (items.length >= 6) {
                        String stamac = items[1];
                        long time = Long.parseLong(items[5]) * 1000;  //日志文件以秒为单位，detatime以毫秒为单位
                        for (DetaTime dt : detaTimes) {
                            if (dt.getStartTime() <= time && dt.getEndTime() >= time) {   //设备出现在该时间段内
                                if (resutlMap.containsKey(stamac)) {
                                    ArrayList<DetaTime> dtAl = resutlMap.get(stamac);
                                    if(!dtAl.contains(dt)){
                                        dtAl.add(dt);
                                    }
                                } else {
                                    ArrayList<DetaTime> dtAl = new ArrayList<DetaTime>();
                                    dtAl.add(dt);
                                    resutlMap.put(stamac, dtAl);
                                }
                            }
                        }
                    }
                }
                //必须满足所有条件
                int dtSize = detaTimes.size();
                ArrayList<String> stamacs = result.getStamacs();
                for (Map.Entry<String, ArrayList<DetaTime>> entry : resutlMap.entrySet()) {
                    String stamac = entry.getKey();
                    ArrayList<DetaTime> dtAl = entry.getValue();
                    if(dtAl.size()==dtSize){
                        stamacs.add(stamac);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            intent.putExtra(DisplayResultActivity.EXTRA_FILE_NO_EXISTS, 1);
        }
        intent.putExtra(DisplayResultActivity.EXTRA_TYPE, 0);
        intent.putExtra(DisplayResultActivity.EXTRA_LIST_DATA, (Serializable) result);
        startActivity(intent);
    }

    private Runnable myRunnable = new Runnable(){
        public void run(){
            long endTime = System.currentTimeMillis();
            long interval = endTime - curDetaTime.getStartTime();
            long mill = interval % 1000 / 100;
            tvTime.setText(getTimeStr(curDetaTime.getStartTime(), endTime)+"."+mill);
            handler.postDelayed(this, 100);
        }
    };

    private String getTimeStr(long startTime, long endTime){
        String startTimeStr = TimeStamp2Date(startTime, "HH:mm:ss");
        String endTimeStr = TimeStamp2Date(endTime, "HH:mm:ss");
        return startTimeStr+"—"+endTimeStr;
    }

    private String TimeStamp2Date(Long timestamp, String formats){
        String date = new java.text.SimpleDateFormat(formats).format(new java.util.Date(timestamp));
        return date;
    }

    private void addTimeItem(final DetaTime dt, boolean addToDB){
        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
        View view = inflater.inflate(R.layout.time_list_item, null);
        final CheckBox cb = (CheckBox) view.findViewById(R.id.selectCheckbox);
        cb.setChecked(dt.getSelected()==1);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dt.setSelected(dt.getSelected()==1?0:1);
                cb.setChecked(dt.getSelected()==1);
                updateBtnEnabled();
            }
        });
        TextView tv = (TextView) view.findViewById(R.id.time);
        tv.setText(getTimeStr(dt.getStartTime(), dt.getEndTime()));
        llTimeList.addView(view);
        //滚动到底部
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                svTimeList.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
        //添加到数据库
        if(addToDB)
            addData(dt);
    }

    private void updateBtnEnabled(){
        boolean flag = false;
        for(DetaTime dt:this.detaTimes){
            if(dt.getSelected()==1){
                flag = true;
                break;
            }
        }
        this.btnClear.setEnabled(flag);
        this.btnParse.setEnabled(flag);
    }

    private void openDatabase(){
        TimerDatabaseHelper dbHelper = new TimerDatabaseHelper(MainActivity.this, Config.DatabaseName, null, 1);
        db = dbHelper.getWritableDatabase();
    }
    private void addData(DetaTime dt){
        this.detaTimes.add(dt);
        openDatabase();
        db.execSQL("insert into " + Config.TableName_Timer + " values(" + dt.getId() + "," + dt.getStartTime() + "," + dt.getEndTime() + ")");
        closeDatabase();
    }
    private void deleteData(ArrayList<Integer> ids){
        if(ids==null || ids.size()==0){
            return;
        }
        openDatabase();
        String idsStr = ids.toString();
        idsStr = idsStr.substring(1, idsStr.length()-1);
        String selection = "_id in ("+ idsStr + ")";
        db.delete(Config.TableName_Timer, selection, null);
        closeDatabase();
    }
    private void closeDatabase() {
        db.close();
    }
}
