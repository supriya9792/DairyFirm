package com.example.admin.dairyfirm.ModelAndAdapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.dairyfirm.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by admin on 1/9/2018.
 */

public class AreaAdapter  extends BaseAdapter {

    private LayoutInflater inflater = null;
    private Activity activity;
    private List<Area_list> itemList;
    private Context context;
    private ArrayList<Area_list> arraylist;

    public AreaAdapter(Context context, ArrayList<Area_list> itemList) {
        this.itemList = itemList;
        this.context = context;
        this.arraylist = new ArrayList<Area_list>();
        this.arraylist.addAll(itemList);
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Area_list getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.area_list, null);

        final Area_list model = itemList.get(position);
        TextView employeeName = (TextView) convertView.findViewById(R.id.tv_areaName);



        employeeName.setText(model.getAreaname());


        return convertView;


    }
    /*public void updateList (String selectedarea) {

        selectedarea = selectedarea.toLowerCase(Locale.getDefault());
        Toast.makeText(context,"You have selected "+selectedarea, Toast.LENGTH_LONG).show();
        itemList.clear();
        if (selectedarea.equals(" ") ) {
            itemList.addAll(arraylist);
        } else {
            for (Area_list wp : arraylist) {
               *//* if((wp.getTaxtype().equals("ITR"))&&(wp.getLocation().equals("pune"))){
                    itemList.add(wp);
                }*//**//*
               if((wp.contains(selectedtax))&&(wp.contains(selectedlocation))){
                    itemList.add(wp);
                }*//*
                if((wp.getAreaname().toLowerCase(Locale.getDefault())
                        .contains(selectedarea))) {

                    itemList.add(wp);

                } *//*else  if(wp.getLocation().contains(selectedlocation)) {

                        itemList.add(wp);

                    }*//*

                //and call notifyDataSetChanged
                notifyDataSetChanged();
            }
        }
    }
*/

}
