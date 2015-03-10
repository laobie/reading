package com.jaeger.reading.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gc.materialdesign.views.ProgressBarDeterminate;
import com.jaeger.reading.R;
import com.jaeger.reading.model.Book;

import java.io.File;
import java.util.ArrayList;

public class ReadingBookAdapter extends ArrayAdapter<Book> {
    private int resourceId;
    public ReadingBookAdapter(Context context, int resourceId, ArrayList<Book> objects) {
        super(context, resourceId, objects);
        this.resourceId = resourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Book readingBook = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.bookCoverView = (ImageView) view.findViewById(R.id.bookCoverView);
            viewHolder.bookNameView = (TextView) view.findViewById(R.id.bookNameView);
            viewHolder.bookAuthorView = (TextView) view.findViewById(R.id.bookAuthorView);
            viewHolder.readProgress = (TextView) view.findViewById(R.id.bookReadProgressView);
            viewHolder.readProgressBar = (ProgressBarDeterminate) view.findViewById(R.id.readProgressBar);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        File bookCoverFile = new File(readingBook.getCoverFile());
        if (bookCoverFile.exists()) {
            Bitmap bookCover = BitmapFactory.decodeFile(readingBook.getCoverFile());
            viewHolder.bookCoverView.setImageBitmap(bookCover);
        }
        viewHolder.bookNameView.setText(readingBook.getName());
        viewHolder.bookAuthorView.setText(readingBook.getAuthor());
        viewHolder.readProgress.setText(readingBook.getReadPages() + "/" + readingBook.getPages());
        viewHolder.readProgressBar.setMax(readingBook.getPages());
        viewHolder.readProgressBar.setProgress(readingBook.getReadPages());
        return view;
    }

    static class ViewHolder{
        ImageView bookCoverView;
        TextView bookNameView;
        TextView bookAuthorView;
        TextView readProgress;
        ProgressBarDeterminate readProgressBar;
    }
}
