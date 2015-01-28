package com.jaeger.reading;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gc.materialdesign.views.ButtonFlat;
import com.gc.materialdesign.views.ProgressBarDeterminate;
import com.getbase.floatingactionbutton.FloatingActionButton;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Jaeger on 1/24/024.
 */

public class BookDetailActivity extends ActionBarActivity {
    private int bookId;
    private ImageView bookCoverView;
    private TextView bookNameView, bookAuthorView, bookPagesView,
            addDateView, finDateView, bookReadingPgrView;
    private ProgressBarDeterminate readProgressBar;
    private FloatingActionButton addBookmark;
    private LinearLayout readingInfoView, finishDateView;
    private final static int REQUESTCODE_EDIT_BOOK = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_detial_layout);
        initView();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        bookId = intent.getIntExtra("bookId", 0);
        UpdateBookInfo();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_book_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_delete:
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("提示")
                        .setMessage("确定删除此书吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                File temp = new File(DataSupport.find(Book.class, bookId).getCoverFile());
                                if (temp.exists())
                                    temp.delete();
                                DataSupport.delete(Book.class, bookId);
                                Intent intent = new Intent();
                                setResult(RESULT_OK, intent);
                                finish();
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                return true;
            case R.id.action_edit:
                Intent intent = new Intent(BookDetailActivity.this, AddNewBookActivity.class);
                intent.putExtra("editBookId", bookId);
                startActivityForResult(intent, REQUESTCODE_EDIT_BOOK);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initView() {
        bookCoverView       = (ImageView) findViewById(R.id.bookCoverView);
        bookNameView        = (TextView) findViewById(R.id.bookNameView);
        bookAuthorView      = (TextView) findViewById(R.id.bookAuthorView);
        bookPagesView       = (TextView) findViewById(R.id.bookPagesView);
        addDateView         = (TextView) findViewById(R.id.addDateView);
        finDateView         = (TextView) findViewById(R.id.finDateView);
        bookReadingPgrView  = (TextView) findViewById(R.id.bookReadProgressView);
        readProgressBar     = (ProgressBarDeterminate) findViewById(R.id.readProgressBar);
        addBookmark         = (FloatingActionButton) findViewById(R.id.addBookmark);
        finishDateView      = (LinearLayout) findViewById(R.id.finishDateView);
        readingInfoView     = (LinearLayout) findViewById(R.id.readingInfoView);

        addBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog;
                View view = LayoutInflater.from(BookDetailActivity.this).inflate(R.layout.add_bookmark_dialog, null);
                final EditText et = (EditText) view.findViewById(R.id.bookmarkEditView);
                ButtonFlat sureAddBookmark = (ButtonFlat) view.findViewById(R.id.updateBookmarkButton);

                AlertDialog.Builder builder = new AlertDialog.Builder(BookDetailActivity.this);
                dialog = builder.setView(view).create();
                dialog.show();
                dialog.getWindow().setLayout(500, 360);
                dialog.getWindow().setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                sureAddBookmark.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!et.getText().toString().trim().equals("")) {
                            int readPages = Integer.parseInt(et.getText().toString());
                            Book book = DataSupport.find(Book.class, bookId);
                            if (readPages <= book.getPages()) {
                                Book newBook = new Book();
                                newBook.setReadPages(readPages);
                                if (readPages == book.getPages()) {
                                    newBook.setFinishDate(new Date(System.currentTimeMillis()));
                                    newBook.setFinish(true);
                                }else {
                                    newBook.setToDefault("isFinish");
                                }
                                newBook.update(bookId);
                                UpdateBookInfo();
                                dialog.dismiss();
                            } else {
                                AlertDialog alertDialog = new AlertDialog.Builder(BookDetailActivity.this)
                                        .setTitle("提示")
                                        .setMessage("输入的页数不对")
                                        .setPositiveButton("确定", null)
                                        .show();
                            }
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case REQUESTCODE_EDIT_BOOK:
                UpdateBookInfo();
                break;
            default:
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void UpdateBookInfo() {
        Book book = DataSupport.find(Book.class, bookId);

        File bookCoverFile = new File(book.getCoverFile());
        if (bookCoverFile.exists()) {
            Bitmap bookCover = BitmapFactory.decodeFile(book.getCoverFile());
            bookCoverView.setImageBitmap(bookCover);
        }
        bookNameView.setText(book.getName());
        bookAuthorView.setText(book.getAuthor());
        bookPagesView.setText(book.getPages() + " 页");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        addDateView.setText(dateFormat.format(book.getAddDate()));

        if (book.isFinish()){
            readingInfoView.setVisibility(View.GONE);
            finishDateView.setVisibility(View.VISIBLE);
            finDateView.setText(dateFormat.format(book.getFinishDate()));
            addBookmark.setIcon(R.drawable.ic_action_done);
//            addBookmark.setEnabled(false);
        } else {
            readingInfoView.setVisibility(View.VISIBLE);
            finishDateView.setVisibility(View.GONE);
            addBookmark.setIcon(R.drawable.ic_bookmark);
            readProgressBar.setMax(book.getPages());
            readProgressBar.setProgress(book.getReadPages());
            bookReadingPgrView.setText(book.getReadPages() + "/" + book.getPages());
        }

    }

}
