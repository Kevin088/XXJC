package com.baidu.ocr.demo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.ocr.demo.FileUtil;
import com.baidu.ocr.demo.MainActivity;
import com.baidu.ocr.demo.R;
import com.baidu.ocr.demo.RecognizeService;
import com.baidu.ocr.demo.adapter.CollectDataAdapter;
import com.baidu.ocr.demo.bean.CollectDataBean;
import com.baidu.ocr.demo.bean.TableCols;
import com.baidu.ocr.demo.bean.TableColsValue;
import com.baidu.ocr.demo.bean.Tables;
import com.baidu.ocr.demo.utils.GsonUtils;
import com.baidu.ocr.demo.utils.Utils;
import com.baidu.ocr.demo.utils.XmlParseUtil;
import com.baidu.ocr.demo.view.TitleBarView;
import com.baidu.ocr.demo.view.ToastManager;
import com.baidu.ocr.ui.camera.CameraActivity;

import java.util.ArrayList;
import java.util.List;

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
     int index;
    private static final int REQUEST_CODE_GENERAL_WEBIMAGE = 108;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect_datactivity);
        ButterKnife.bind(this);
        tableId = getIntent().getIntExtra("tableId", 0);
        tableName=getIntent().getStringExtra("tableName");

        title.withTitle("待采数据", 0).withLeftImage(R.mipmap.ic_back).setOnTitleBarClickListener(this);

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
//                totalData.get(position).value++;
//                adapter.notifyDataSetChanged();
                index=position;
                Intent intent = new Intent(CollectDatactivity.this, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                        CameraActivity.CONTENT_TYPE_GENERAL);
                startActivityForResult(intent, REQUEST_CODE_GENERAL_WEBIMAGE);


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
                //判断是否所有采集项 都采集了
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        List<TableCols> list= Utils.getTableColsInTable(tableId);
                        int isfullCaiji=1;
                        for(TableCols tableCol:list){
                            if(tableCol.value==0){
                                isfullCaiji=0;
                                break;
                            }
                        }
                        Tables table=XmlParseUtil.tables.get(XmlParseUtil.tables.indexOf(new Tables(tableId)));
                        table.state=isfullCaiji;
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
                            XmlParseUtil.saveDataSheetXml();
                            XmlParseUtil.saveDefinitionXml();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_GENERAL_WEBIMAGE && resultCode == Activity.RESULT_OK) {
            RecognizeService.recWebimage(FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath(),
                    new RecognizeService.ServiceListener() {
                        @Override
                        public void onResult(String result) {
                            CollectDataBean bean= GsonUtils.fromJson(result,CollectDataBean.class);
                            boolean isNotAsys=true;
                            if(bean!=null&&bean.getWords_result()!=null&&bean.getWords_result().size()>0){
                                for(CollectDataBean.WordsResultBean bean1:bean.getWords_result()){
                                    if(Utils.getValueFromStr(bean1.getWords())!=0){
                                        totalData.get(index).value=Utils.getValueFromStr(bean1.getWords());
                                        adapter.notifyDataSetChanged();
                                        isNotAsys=false;
                                    }
                                }
                            }
                            if(isNotAsys)
                                ToastManager.showShortToast("未识别出数字");
                        }
                    });
        }
    }
}
