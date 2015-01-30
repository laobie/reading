package com.jaeger.reading.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.dexafree.materialList.view.MaterialListView;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.jaeger.reading.model.Book;
import com.jaeger.reading.model.DrawerItem;
import com.jaeger.reading.DrawerItemAdapter;
import com.jaeger.reading.R;
import com.jaeger.reading.ReadingBookAdapter;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    private MaterialListView materialListView;
    private MaterialMenuDrawable materialMenu;
    private DrawerLayout mDrawerLayout;
    private ListView mListViewDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar toolbar;
    private FloatingActionButton fabAdd;

    private RelativeLayout mainBookListLayout;
    private WebView browseBooksView;

    private String titleStr;
    private ArrayList<Book> BookList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        titleStr = "在读书单";
        initView();
        BookList = (ArrayList<Book>) DataSupport.where("isFinish = ?", "0").find(Book.class);
        UpadateBook();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            default:
                return false;
        }
    }

    private void UpadateBook() {
        ReadingBookAdapter adapter = new ReadingBookAdapter(MainActivity.this,
                R.layout.reading_book_item, BookList);
        materialListView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
        case 1:
            if (resultCode == RESULT_OK){
               UpadateBook();
            }
            break;
        case 2:
            if (resultCode == RESULT_OK){
                UpadateBook();
            }
        default:
            break;
        }
    }

    private void initDrawerItem(){
        ArrayList<DrawerItem> drawerItemArrayList = new ArrayList<>();
        int[] drawerItemIconIds = new int[]{
                R.drawable.ic_wantbook_icon,
                R.drawable.ic_readbook_icon,
                R.drawable.ic_brower_icon
        };
        String[] drawerItemNames = new String[]{
                "在读书单",
                "已读书单",
                "浏览推荐"
        };
        for (int i = 0; i < 3; i++){
            DrawerItem drawerItem = new DrawerItem(drawerItemIconIds[i], drawerItemNames[i]);
            drawerItemArrayList.add(drawerItem);
        }
        DrawerItemAdapter adapter = new DrawerItemAdapter(this, R.layout.drawer_item, drawerItemArrayList);
        mListViewDrawer.setAdapter(adapter);
        mListViewDrawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        mainBookListLayout.setVisibility(View.VISIBLE);
                        browseBooksView.setVisibility(View.GONE);
                        mDrawerLayout.closeDrawer(mListViewDrawer);
                        titleStr = "在读书单";
                        BookList = (ArrayList<Book>) DataSupport.where("isFinish = ?", "0").find(Book.class);
                        UpadateBook();
                        break;
                    case 1:
                        mainBookListLayout.setVisibility(View.VISIBLE);
                        browseBooksView.setVisibility(View.GONE);
                        mDrawerLayout.closeDrawer(mListViewDrawer);
                        titleStr = "已完成书单";
                        BookList = (ArrayList<Book>) DataSupport.where("isFinish = ?", "1").find(Book.class);
                        UpadateBook();
                        break;
                    case 2:
                        mDrawerLayout.closeDrawer(mListViewDrawer);
                        titleStr = "浏览推荐";
                        mainBookListLayout.setVisibility(View.GONE);
                        browseBooksView.setVisibility(View.VISIBLE);
                        browseBooksView.getSettings().setJavaScriptEnabled(true);
//                        browseBooksView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
                        browseBooksView.loadUrl("http://book.douban.com/tag/%E9%A6%99%E6%B8%AF%E4%B8" +
                                "%AD%E6%96%87%E5%A4%A7%E5%AD%A6%E6%8E%A8%E8%8D%90%E4%B9%A6%E5%8D%95");
                        browseBooksView.setWebViewClient(new initWebView());
                        break;
                    default:
                        break;
                }
            }
        });

    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
////        if (keyCode == KeyEvent.KEYCODE_BACK && browseBooksView.canGoBack()){
////            //browseBooksView.goBack();
////            return true;
////        }
//        return false;
//    }

    private void initView(){
        materialListView = (MaterialListView) findViewById(R.id.material_listView);
        fabAdd = (FloatingActionButton) findViewById(R.id.fab_button);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mListViewDrawer = (ListView) findViewById(R.id.left_drawer);

        mainBookListLayout = (RelativeLayout) findViewById(R.id.mainBookListLayout);
        browseBooksView = (WebView) findViewById(R.id.browseBooksView);

        initDrawerItem();

        toolbar.setTitle(titleStr);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT){
            toolbar.setElevation(5);
        }

        materialMenu = new MaterialMenuDrawable(this, Color.WHITE, MaterialMenuDrawable.Stroke.THIN);
        toolbar.setNavigationIcon(materialMenu);
        materialMenu.setNeverDrawTouch(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open,R.string.drawer_close ){
            public void onDrawerClosed(View view) {
                materialMenu.animateIconState(MaterialMenuDrawable.IconState.BURGER, true);
                super.onDrawerClosed(view);
                toolbar.setTitle(titleStr);
                //invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                materialMenu.animateIconState(MaterialMenuDrawable.IconState.ARROW, true);
                super.onDrawerOpened(drawerView);
                toolbar.setTitle("在读");
                // invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddNewBookActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        materialListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

        materialListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Book readingBook =  BookList.get(position);
                Intent intent = new Intent(MainActivity.this, BookDetailActivity.class);
                intent.putExtra("bookId", readingBook.getId());
                startActivityForResult(intent, 2);
            }
        });
    }

    @Override
    protected void onRestart() {
        switch (titleStr){
            case "在读书单":
                BookList = (ArrayList<Book>) DataSupport.where("isFinish = ?", "0").find(Book.class);
                break;
            case "已完成书单":
                BookList = (ArrayList<Book>) DataSupport.where("isFinish = ?", "1").find(Book.class);
                break;
            default:
                BookList = (ArrayList<Book>) DataSupport.where("isFinish = ?", "0").find(Book.class);
                break;
        }
        UpadateBook();
        super.onRestart();
    }

    private class initWebView extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            view.loadUrl(url);
            return true;
        }
    }
}
