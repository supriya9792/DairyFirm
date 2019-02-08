package com.example.admin.dairyfirm.ModelAndAdapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.admin.dairyfirm.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2/1/2018.
 */

public class Spinner_cust_adapter extends BaseAdapter {
    private LayoutInflater inflater = null;
     List<Spinner_cust_list> itemList;
     Context context;
     ArrayList<Spinner_cust_list> arraylist;
    public Spinner_cust_adapter(Context context, ArrayList<Spinner_cust_list> itemList) {
        this.itemList = itemList;
        this.context = context;
        this.arraylist = new ArrayList<Spinner_cust_list>();
        this.arraylist.addAll(itemList);
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Spinner_cust_list getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(inflater==null)
            inflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        if(convertView==null)
     convertView=inflater.inflate(R.layout.spinner_cust_list,null);
        final Spinner_cust_list model = itemList.get(position);
        TextView custname = (TextView) convertView.findViewById(R.id.custname);


        Typeface font = Typeface.createFromAsset(context.getAssets(), "akshar.ttf");
        custname.setTypeface(font);
        custname.setText(model.getCustname());


        return convertView;
    }
}
