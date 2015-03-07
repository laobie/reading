package com.jaeger.reading.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.balysv.materialmenu.extras.toolbar.MaterialMenuIconCompat;
import com.dexafree.materialList.view.MaterialListView;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.jaeger.reading.R;
import com.jaeger.reading.adapter.DrawerItemAdapter;
import com.jaeger.reading.adapter.ReadingBookAdapter;
import com.jaeger.reading.model.Book;
import com.jaeger.reading.model.DrawerItem;
import com.jaeger.reading.tools.ActivityCollector;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;


public class MainActivity extends BaseActivity {
    private MaterialListView materialListView;
    private DrawerLayout drawerLayout;
    private RelativeLayout drawerView;
    private ListView drawerListView;
    private ActionBarDrawerToggle mDrawerToggle;
    private FloatingActionButton fabAdd;

    private MaterialMenuIconCompat materialMenu;
    private RelativeLayout mainBookListLayout;
    private WebView browseBooksView;
    private ArrayList<Book> BookList;

    private ListView settingAndExitView;
    //MainActivity content model and title string
    private String titleStr;
    private static final String READING = "在读";
    private static final String READING_BOOKS_LIST = "在读书单";
    private static final String READ_BOOKS_LIST = "已读书单";
    private static final String RECOMMEND_BOOKS_LIST = "推荐书单";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        materialMenu = new MaterialMenuIconCompat(this, Color.WHITE, MaterialMenuDrawable.Stroke.THIN);
        setContentView(R.layout.activity_main);
        titleStr = READING_BOOKS_LIST;
        initView();
        BookList = (ArrayList<Book>) DataSupport.where("isFinish = ?", "0").find(Book.class);
        UpdateBook();
    }




    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        materialMenu.onSaveInstanceState(outState);
    }
    @Override
    protected void onRestart() {
        switch (titleStr) {
            case READING_BOOKS_LIST:
                BookList = (ArrayList<Book>) DataSupport.where("isFinish = ?", "0").find(Book.class);
                break;
            case READ_BOOKS_LIST:
                BookList = (ArrayList<Book>) DataSupport.where("isFinish = ?", "1").find(Book.class);
                break;
            case RECOMMEND_BOOKS_LIST:
                break;
            default:
                BookList = (ArrayList<Book>) DataSupport.where("isFinish = ?", "0").find(Book.class);
                break;
        }
        UpdateBook();
        super.onRestart();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        materialMenu.syncState(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.action_serach:
                Intent intent = new Intent(this, SearchBookActivity.class);
                startActivity(intent);
                return true;
            default:
                return false;
        }
    }

    private void UpdateBook() {
        ReadingBookAdapter adapter = new ReadingBookAdapter(MainActivity.this,
                R.layout.book_item, BookList);
        materialListView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    UpdateBook();
                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    UpdateBook();
                }
            default:
                break;
        }
    }

    private void initDrawerItem() {
        ArrayList<DrawerItem> drawerItemArrayList = new ArrayList<>();
        int[] drawerItemIconIds = new int[]{
                R.drawable.ic_drawer_readingbook,
                R.drawable.ic_drawer_readbook,
                R.drawable.ic_drawer_recommend
        };
        String[] drawerItemNames = new String[]{
                READING_BOOKS_LIST,
                READ_BOOKS_LIST,
                RECOMMEND_BOOKS_LIST
        };
        for (int i = 0; i < 3; i++) {
            DrawerItem drawerItem = new DrawerItem(drawerItemIconIds[i], drawerItemNames[i]);
            drawerItemArrayList.add(drawerItem);
        }
        DrawerItemAdapter adapter = new DrawerItemAdapter(this, R.layout.drawer_item, drawerItemArrayList);
        drawerListView.setAdapter(adapter);
        drawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        mainBookListLayout.setVisibility(View.VISIBLE);
                        browseBooksView.setVisibility(View.GONE);
                        drawerLayout.closeDrawer(drawerView);
                        titleStr = READING_BOOKS_LIST;
                        BookList = (ArrayList<Book>)
                                DataSupport.where("isFinish = ?", "0").find(Book.class);
                        UpdateBook();
                        break;
                    case 1:
                        mainBookListLayout.setVisibility(View.VISIBLE);
                        browseBooksView.setVisibility(View.GONE);
                        drawerLayout.closeDrawer(drawerView);
                        titleStr = READ_BOOKS_LIST;
                        BookList = (ArrayList<Book>)
                                DataSupport.where("isFinish = ?", "1").find(Book.class);
                        UpdateBook();
                        break;
                    case 2:
                        drawerLayout.closeDrawer(drawerView);
                        titleStr = RECOMMEND_BOOKS_LIST;
                        mainBookListLayout.setVisibility(View.GONE);
                        browseBooksView.setVisibility(View.VISIBLE);
                        browseBooksView.getSettings().setJavaScriptEnabled(true);
                        browseBooksView.loadUrl("http://book.douban.com/");
                        browseBooksView.setWebViewClient(new initWebView());
                        break;
                    default:
                        break;
                }
            }
        });

        ArrayList<DrawerItem> functionArrayList = new ArrayList<>();
        DrawerItem drawerItem;
        drawerItem = new DrawerItem(R.drawable.ic_drawer_settings, "设置");
        functionArrayList.add(drawerItem);
        drawerItem = new DrawerItem(R.drawable.ic_drawer_exit, "退出");
        functionArrayList.add(drawerItem);
        DrawerItemAdapter adapter1 = new DrawerItemAdapter(this, R.layout.drawer_item, functionArrayList);
        settingAndExitView.setAdapter(adapter1);
        settingAndExitView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        drawerLayout.closeDrawer(drawerView);
                        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        ActivityCollector.finishAll();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void initView() {
        materialListView = (MaterialListView) findViewById(R.id.material_listView);
        fabAdd = (FloatingActionButton) findViewById(R.id.fab_button);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerListView = (ListView) findViewById(R.id.left_drawer);
        drawerView = (RelativeLayout) findViewById(R.id.drawer_view);
        mainBookListLayout = (RelativeLayout) findViewById(R.id.mainBookListLayout);
        browseBooksView = (WebView) findViewById(R.id.browseBooksView);
        settingAndExitView = (ListView) findViewById(R.id.settingAndExitView);
        initDrawerItem();

        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                materialMenu.animatePressedState(MaterialMenuDrawable.IconState.BURGER);
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(titleStr);
                //invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                materialMenu.animatePressedState(MaterialMenuDrawable.IconState.ARROW);
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(READING);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        drawerLayout.setDrawerListener(mDrawerToggle);

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
                Book readingBook = BookList.get(position);
                Intent intent = new Intent(MainActivity.this, BookDetailActivity.class);
                intent.putExtra("bookId", readingBook.getId());
                startActivityForResult(intent, 2);
            }
        });


    }

    private class initWebView extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && browseBooksView.canGoBack()) {
            browseBooksView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
