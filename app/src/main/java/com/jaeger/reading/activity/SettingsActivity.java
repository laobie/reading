package com.jaeger.reading.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.jaeger.reading.R;

public class SettingsActivity extends BaseActivity implements View.OnClickListener {
    private TextView tvLoadCoverMode;
    private Switch swLoadCoverMode;
    private TextView tvLoadCoverQuality;
    private TextView tvrLoadCoverQuality;
    private TextView tvLoadNum;
    private TextView tvrLoadNum;
    private TextView tvReceiveNoticeMode;
    private Switch swReceiveNoticeMode;
    private TextView tvReceiveNoticeTime;
    private TextView tvrReceiveNoticeTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_layout);
        initView();
        GetSettingsConfig();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SaveSettingsConfig();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_load_cover_mode:
                swLoadCoverMode.setChecked(!swLoadCoverMode.isChecked());
                setLoadCover(swLoadCoverMode.isChecked());
                break;
            case R.id.tv_load_cover_quality:
                String[] coverQualityItems =
                        getResources().getStringArray(R.array.array_load_cover_quality);
                ShowItemsDialog(coverQualityItems, tvrLoadCoverQuality);
                break;
            case R.id.tv_load_num:
                ShowItemsDialog(new String[]{"3", "5", "10"}, tvrLoadNum);
                break;
            case R.id.tv_receive_notice_mode:
                swReceiveNoticeMode.setChecked(!swReceiveNoticeMode.isChecked());
                setReceiveNoticeMode(swReceiveNoticeMode.isChecked());
                break;
            case R.id.tv_receive_notice_time:
                ShowTimePickerDialog(tvrReceiveNoticeTime);
                break;
            default:
                break;
        }

    }

    private void initView() {
        // load cover mode
        tvLoadCoverMode = (TextView) findViewById(R.id.tv_load_cover_mode);
        swLoadCoverMode = (Switch) findViewById(R.id.sw_load_cover_mode);
        // load cover quality
        tvLoadCoverQuality = (TextView) findViewById(R.id.tv_load_cover_quality);
        tvrLoadCoverQuality = (TextView) findViewById(R.id.tvr_load_cover_quality);
        // per load items number
        tvLoadNum = (TextView) findViewById(R.id.tv_load_num);
        tvrLoadNum = (TextView) findViewById(R.id.tvr_load_num);
        //receive notice mode
        tvReceiveNoticeMode = (TextView) findViewById(R.id.tv_receive_notice_mode);
        swReceiveNoticeMode = (Switch) findViewById(R.id.sw_receive_notice_mode);
        //receive notice time
        tvReceiveNoticeTime = (TextView) findViewById(R.id.tv_receive_notice_time);
        tvrReceiveNoticeTime = (TextView) findViewById(R.id.tvr_receive_notice_time);

        tvLoadCoverQuality.setOnClickListener(this);
        tvLoadCoverMode.setOnClickListener(this);
        tvLoadNum.setOnClickListener(this);
        tvReceiveNoticeMode.setOnClickListener(this);
        tvReceiveNoticeTime.setOnClickListener(this);
    }


    private void ShowTimePickerDialog(final TextView changeTextView) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.dialogTheme);
        View view = getLayoutInflater().inflate(R.layout.dialog_timepicker, null);
        final TimePicker timePicker = (TimePicker) view.findViewById(R.id.tp_time);
        builder.setView(view)
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Time time = new Time();
                        time.hour = timePicker.getCurrentHour();
                        time.minute = timePicker.getCurrentMinute();
                        String timeStr = (time.hour < 10 ? "0" + time.hour : time.hour) + ":"
                                + (time.minute < 10 ? "0" + time.minute : time.minute);
                        changeTextView.setText(timeStr);
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void ShowItemsDialog(final String[] items, final TextView changeTextView) {
        final AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        View view = getLayoutInflater().inflate(R.layout.listview_of_dialog_items, null);
        ListView listView = (ListView) view.findViewById(R.id.list_items_dialog);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.textview_of_dialog_item, items);
        listView.setAdapter(adapter);
        builder.setView(view);

//        //默认对话框样式，问题是items无法居中
//        builder.setItems(items, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                textView.setText(items[which]);
//            }
//        });

        dialog = builder.create();
        dialog.show();
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        dialog.getWindow().setLayout(360, params.height);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                changeTextView.setText(items[position]);
                dialog.dismiss();
            }
        });
    }

    private void GetSettingsConfig() {
        SharedPreferences config = getSharedPreferences("settings", MODE_PRIVATE);
        swLoadCoverMode.setChecked(config.getBoolean("loadCoverMode", false));
        tvrLoadCoverQuality.setText(config.getString("loadCoverQuality", "一般"));
        tvrLoadNum.setText(String.valueOf(config.getInt("loadNum", 3)));
        swReceiveNoticeMode.setChecked(config.getBoolean("receiveNoticeMode", false));
        tvrReceiveNoticeTime.setText(config.getString("receiveNoticeTime", "20:00"));

        setLoadCover(swLoadCoverMode.isChecked());
        setReceiveNoticeMode(swReceiveNoticeMode.isChecked());

    }


    private void SaveSettingsConfig() {
        SharedPreferences.Editor configEditor = getSharedPreferences("settings", MODE_PRIVATE).edit();
        configEditor.putBoolean("loadCoverMode", swLoadCoverMode.isChecked());
        configEditor.putString("loadCoverQuality", tvrLoadCoverQuality.getText().toString());
        configEditor.putInt("loadNum", Integer.parseInt(tvrLoadNum.getText().toString()));
        configEditor.putBoolean("receiveNoticeMode", swReceiveNoticeMode.isChecked());
        configEditor.putString("receiveNoticeTime", tvrReceiveNoticeTime.getText().toString());
        configEditor.apply();
    }

    private void setLoadCover(boolean flag) {
        SetViewAble(tvLoadCoverQuality, flag, true);
        SetViewAble(tvrLoadCoverQuality, flag, false);
        SetViewAble(tvLoadNum, flag, true);
        SetViewAble(tvrLoadNum, flag, false);
    }

    private void setReceiveNoticeMode(boolean flag){
        SetViewAble(tvReceiveNoticeTime, flag, true);
        SetViewAble(tvrReceiveNoticeTime, flag, false);

    }


    private void SetViewAble(View view, boolean able, boolean leftTextView) {
        if (able) {
            view.setClickable(true);
            if (view.getClass() == TextView.class) {
                TextView textView = (TextView) view;
                if(leftTextView)
                    textView.setTextColor(this.getResources().getColor(R.color.black));
                else
                    textView.setTextColor(this.getResources().getColor(R.color.deep_green));
            }


        } else {
            view.setClickable(false);
            if (view.getClass() == TextView.class) {
                TextView textView = (TextView) view;
                textView.setTextColor(this.getResources().getColor(R.color.grey_title));
            }
        }


    }
}
