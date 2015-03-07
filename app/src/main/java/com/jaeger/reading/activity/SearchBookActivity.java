package com.jaeger.reading.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.ListView;

import com.jaeger.reading.R;
import com.jaeger.reading.adapter.BookInfoAdapter;
import com.jaeger.reading.model.BookInfo;
import com.jaeger.reading.model.SettingsConfig;
import com.jaeger.reading.tools.JsonParse;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;


public class SearchBookActivity extends BaseActivity {
    private MaterialEditText keyWordEdtTxt;
    private ListView searchResultLv;
    private static Handler handler = new Handler();
    private BookInfoAdapter adapter;

    private ArrayList<BookInfo> bookInfoList = null;

    private int searchStart = 0;
    private int searchCount = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_book_layout);

        SettingsConfig sc = new SettingsConfig(this);
        searchCount = sc.getLoadNum();
        android.support.v7.app.ActionBar bar = getSupportActionBar();
        bar.setCustomView(R.layout.search_edit_actionbar_layout);
        bar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_CUSTOM);
        keyWordEdtTxt = (MaterialEditText) bar.getCustomView()
                .findViewById(R.id.edtTxt_search_keyword);
        searchResultLv = (ListView) findViewById(R.id.lv_search_result);


        searchResultLv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    if (view.getLastVisiblePosition() == view.getCount() - 1) {
                        searchStart += searchCount;
                        new Thread(runnable).start();
                    }
                }

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                int lastItem = firstVisibleItem + visibleItemCount - 1;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_book, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_serach:
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm.isActive() && getCurrentFocus() != null) {
                    if (getCurrentFocus().getWindowToken() != null) {
                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                }
                if (!keyWordEdtTxt.getText().toString().trim().equals("")) {
                    searchStart = 0;
                    if (bookInfoList != null) bookInfoList.clear();
                    new Thread(runnable).start();
                } else {
                    AlertDialog dialog = new AlertDialog.Builder(this)
                            .setTitle("提示")
                            .setMessage("您的输入有误，请检查。")
                            .setPositiveButton("确认", null)
                            .create();
                    dialog.show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            String keyword = keyWordEdtTxt.getText().toString();
            try {
                SettingsConfig sc = new SettingsConfig(SearchBookActivity.this);
                bookInfoList = JsonParse.GetBookListByKeyword(sc, keyword, searchStart, searchCount);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (searchStart == 0) {
                        adapter = new BookInfoAdapter(
                                SearchBookActivity.this,
                                R.layout.book_info_item,
                                bookInfoList
                        );
                        searchResultLv.setAdapter(adapter);
                    } else {
                        adapter.addItems(bookInfoList);
                        adapter.notifyDataSetChanged();
                    }
                }
            });
        }
    };

}
