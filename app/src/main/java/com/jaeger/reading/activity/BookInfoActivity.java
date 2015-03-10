package com.jaeger.reading.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.jaeger.reading.R;
import com.jaeger.reading.model.BookInfo;

public class BookInfoActivity extends BaseActivity {
    private TextView titleView, authorView, pagesView, summaryView;
    private BookInfo book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookinfo_item);
        getSupportActionBar().hide();

        Intent intent = getIntent();
        book = (BookInfo) intent.getSerializableExtra("book");

        titleView = (TextView) findViewById(R.id.tv_bookInfo_title);
        authorView = (TextView) findViewById(R.id.tv_bookInfo_author);
        pagesView = (TextView) findViewById(R.id.tv_bookInfo_pages);
        summaryView = (TextView) findViewById(R.id.tv_bookInfo_summary);

        titleView.setText(book.getTitle());
        authorView.setText(book.getAuthor());
        pagesView.setText(String.valueOf(book.getPages()));
        summaryView.setText(book.getSummary());

    }
}
