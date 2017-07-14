package com.baidu.ocr.demo.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.ocr.demo.R;
import com.baidu.ocr.demo.adapter.CollectDataAdapter;
import com.baidu.ocr.demo.bean.TableCols;
import com.baidu.ocr.demo.bean.TableColsValue;
import com.baidu.ocr.demo.utils.Utils;
import com.baidu.ocr.demo.utils.XmlParseUtil;
import com.baidu.ocr.demo.view.TitleBarView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CollectDatactivity extends FragmentActivity implements TitleBarView.OnTitleBarClickListener, View.OnClickListener {

    CollectDataAdapter adapter;
    ArrayList<TableCols> totalData = new ArrayList<>();
    int tableId;
    String tableName;
    @Bind(R.id.title)
    TitleBarView title;
    @Bind(R.id.listview)
    ListView listview;
    @Bind(R.id.line1)
    View line1;
    @Bind(R.id.tv_back)
    TextView tvBack;
    @Bind(R.id.tv_save)
    TextView tvSave;
    @Bind(R.id.layout_02)
    RelativeLayout layout02;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect_datactivity);
        ButterKnife.bind(this);
        tableId = getIntent().getIntExtra("tableId", 0);
        tableName=getIntent().getStringExtra("tableName");

        title.withTitle("待采数据", 0).withLeftImage(R.mipmap.ic_back).setOnTitleBarClickListener(this);


        //ArrayList<TableColsValue> tableColsValues = Utils.getTableColsValueInTable(tableId);
        ArrayList<TableCols> tableColses = Utils.getTableColsInTable(tableId);
        for (TableCols tableCol : tableColses) {
            tableCol.value = Utils.getValueByTableColsId(tableCol.id);
        }
        totalData.addAll(tableColses);
        adapter = new CollectDataAdapter(this, totalData, R.layout.item_collect_data);
        listview.setAdapter(adapter);


        adapter.onSeeClickBack=new CollectDataAdapter.OnSeeClickBack() {
            @Override
            public void onClickBack(int position) {
                totalData.get(position).value++;
                adapter.notifyDataSetChanged();
            }
        };

        tvBack.setOnClickListener(this);
        tvSave.setOnClickListener(this);
    }

    @Override
    public void onTitleBarClick(int titleId) {
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_save:
                try {
                    XmlParseUtil.tableColsValues.clear();
                    int index=1;
                    for(TableCols tableCol:XmlParseUtil.tableCols){
                        TableColsValue valueBean=new TableColsValue();
                        valueBean.id=index;
                        valueBean.colId=tableCol.id;
                        valueBean.colValue=tableCol.value;
                        valueBean.tableId=tableId;
                        valueBean.tablename=tableName;
                        XmlParseUtil.tableColsValues.add(valueBean);
                        index++;
                    }
                    XmlParseUtil.save();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
