package com.baidu.ocr.demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.baidu.ocr.demo.FileUtil;
import com.baidu.ocr.demo.R;
import com.baidu.ocr.demo.adapter.BaobiaoAdapter;
import com.baidu.ocr.demo.app.App;
import com.baidu.ocr.demo.bean.TableCols;
import com.baidu.ocr.demo.bean.Tables;
import com.baidu.ocr.demo.bean.User2Tables;
import com.baidu.ocr.demo.config.DfhePreference;
import com.baidu.ocr.demo.utils.Utils;
import com.baidu.ocr.demo.utils.WriteToSD;
import com.baidu.ocr.demo.utils.XmlParseUtil;
import com.baidu.ocr.demo.view.TitleBarView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BaoBiaoActivity extends FragmentActivity implements TitleBarView.OnTitleBarClickListener, AdapterView.OnItemClickListener {

    @Bind(R.id.title)
    TitleBarView title;
    @Bind(R.id.listivew)
    ListView listivew;
    @Bind(R.id.activity_bao_biao)
    LinearLayout activityBaoBiao;
    BaobiaoAdapter adapter;
    ArrayList<Tables>totalData=new ArrayList<>();
    private Tables table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bao_biao);
        ButterKnife.bind(this);
        title.withTitle("采集报表",0).withLeftImage(R.mipmap.ic_back).setOnTitleBarClickListener(this);
        totalData.addAll(User2Tables.getUserTables(DfhePreference.getUserId()));
        adapter=new BaobiaoAdapter(this,totalData,R.layout.item_baobiao);
        listivew.setAdapter(adapter);
        listivew.setOnItemClickListener(this);
    }
    @Override
    public void onTitleBarClick(int titleId) {
        btnBackClick();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        table = totalData.get(i);
        Intent intent = new Intent(this, CollectDatactivity.class);
        intent.putExtra("tableId", table.id);
        intent.putExtra("tableName",totalData.get(i).tableName);
        startActivityForResult(intent,200);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            btnBackClick();
        }
        return false;
    }
    public void  btnBackClick(){
        finish();
        if(App.mainActivity!=null)
            App.mainActivity.finish();
    }
}
