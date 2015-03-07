package com.jaeger.reading.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jaeger.reading.R;
import com.jaeger.reading.model.BookInfo;

import java.util.ArrayList;

public class BookInfoAdapter extends ArrayAdapter {
    private int resource;
    private ArrayList<BookInfo> bookList;

    public BookInfoAdapter(Context context, int resource, ArrayList<BookInfo> objects) {
        super(context, resource, objects);
        this.resource = resource;
        this.bookList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
        BookInfo bookInfo = (BookInfo) getItem(position);
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resource, null);
            viewHolder = new ViewHolder();
            viewHolder.bookTitleView = (TextView) view.findViewById(R.id.tv_bookInfo_title);
            viewHolder.bookAuthorView = (TextView) view.findViewById(R.id.tv_bookInfo_author);
            viewHolder.bookPagesView = (TextView) view.findViewById(R.id.tv_bookInfo_pages);
            viewHolder.bookCoverView = (ImageView) view.findViewById(R.id.iv_bookInfo_cover);

            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.bookTitleView.setText(bookInfo.getTitle());
        viewHolder.bookAuthorView.setText(bookInfo.getAuthor());
        viewHolder.bookPagesView.setText(bookInfo.getPages() + "页");
        if (bookInfo.getCover() != null)
            viewHolder.bookCoverView.setImageBitmap(bookInfo.getCover());
        return view;
    }

    private static class ViewHolder {
        private TextView bookTitleView;
        private TextView bookAuthorView;
        private TextView bookPagesView;
        private ImageView bookCoverView;
    }

    public void addItems(ArrayList<BookInfo> newBookList) {
        for (int i = 0; i < newBookList.size(); i++)
            this.bookList.add(newBookList.get(i));
    }
}
