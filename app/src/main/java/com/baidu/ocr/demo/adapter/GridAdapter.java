package com.baidu.ocr.demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.ocr.demo.R;
import com.baidu.ocr.demo.bean.TableCols;

import java.util.List;

public class GridAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private List<TableCols> list;


        public GridAdapter(Context context, List<TableCols>list) {
            inflater = LayoutInflater.from(context);
            this.list=list;
        }



        public int getCount() {
            return list.size();
        }

        public Object getItem(int arg0) {
            return null;
        }

        public long getItemId(int arg0) {
            return 0;
        }



        public View getView(int position, View convertView, ViewGroup parent) {

             TextView name;
             EditText value;
             ImageView imageView;
            View view = inflater.inflate(R.layout.item_collect_data,
                    parent, false);
            //name=




            return convertView;
        }



    }