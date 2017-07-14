package com.baidu.ocr.demo.utils;

import com.baidu.ocr.demo.bean.TableCols;
import com.baidu.ocr.demo.bean.TableColsValue;

import java.util.ArrayList;

/**
 * 类描述:
 *
 * @author xull
 * @date 2017/7/13.
 */
public class Utils {
    public static ArrayList<TableColsValue>getTableColsValueInTable(int tableId){
        ArrayList<TableColsValue> values=new ArrayList<TableColsValue>();
        for(TableColsValue value:XmlParseUtil.tableColsValues){
            if(value.tableId==tableId){
                values.add(value);
            }
        }
        return values;
    }
    public static ArrayList<TableCols>getTableColsInTable(int tableId){
        ArrayList<TableCols> values=new ArrayList<TableCols>();
        for(TableCols value:XmlParseUtil.tableCols){
            if(value.tableId==tableId){
                values.add(value);
            }
        }
        return values;
    }

    public static int getValueByTableColsId(int id){
        for(TableColsValue tableColsValue:XmlParseUtil.tableColsValues){
            if(tableColsValue.colId==id){
                return tableColsValue.colValue;
            }
        }
        return 0;
    }

}
