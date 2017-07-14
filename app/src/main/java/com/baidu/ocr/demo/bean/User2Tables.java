package com.baidu.ocr.demo.bean;

import com.baidu.ocr.demo.utils.XmlParseUtil;

import java.util.ArrayList;

/**
 * 类描述:
 *
 * @author xull
 * @date 2017/7/13.
 */
public class User2Tables implements Comparable{
    public int id;
    public int uId;
    public int tableId;


    @Override
    public int compareTo(Object o) {
        if(o!=null&&o instanceof User2Tables){
            return this.id-((User2Tables) o).id;
        }
        return 0;
    }
    public static ArrayList<Tables> getUserTables(int userId){
        ArrayList<Tables>tables=new ArrayList<>();
        ArrayList<Integer>tableIDs=new ArrayList<Integer>();
        for(User2Tables user2Table: XmlParseUtil.user2Tables){
            if(user2Table.uId==userId)
                tableIDs.add(user2Table.tableId);
        }
        for(Integer id:tableIDs){
            tables.add(XmlParseUtil.tables.get(XmlParseUtil.tables.indexOf(new Tables(id))!=0?XmlParseUtil.tables.indexOf(new Tables(id)):0));
        }
        return tables;
    }
}
