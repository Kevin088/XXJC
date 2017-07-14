package com.baidu.ocr.demo.adapter;

import android.content.Context;

import com.baidu.ocr.demo.R;
import com.baidu.ocr.demo.bean.Tables;

import java.util.ArrayList;

/**
 * 类描述:
 *
 * @author xull
 * @date 2017/7/13.
 */
public class BaobiaoAdapter extends CommonAdapter<Tables> {
    public BaobiaoAdapter(Context context, ArrayList<Tables> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, Tables item) {
        holder.setTextViewText(R.id.tv_name,item.tableName);
        if(item.state==0){
            holder.setTextViewText(R.id.tv_statu,"未采集");
        }else{
            holder.setTextViewText(R.id.tv_statu,"已采集");
        }
    }
}
