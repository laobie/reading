package com.jaeger.reading;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Jaeger on 1/23/023.
 */
public class DrawerItemAdapter extends ArrayAdapter {
    private int resourceId;
    public DrawerItemAdapter(Context context, int resourceId, ArrayList<DrawerItem> objects) {
        super(context, resourceId, objects);
        this.resourceId = resourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DrawerItem drawerItem = (DrawerItem) getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.itemIconView = (ImageView) view.findViewById(R.id.drawerItem_icon_view);
            viewHolder.itemNameView = (TextView) view.findViewById(R.id.drawerItem_name_view);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.itemIconView.setImageResource(drawerItem.getItemIconId());
        viewHolder.itemNameView.setText(drawerItem.getItemName());
        return view;
    }

    class ViewHolder{
        private ImageView itemIconView;
        private TextView itemNameView;
    }
}
