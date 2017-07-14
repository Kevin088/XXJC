/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.ocr.demo;

import java.io.File;

import android.content.Context;

import javax.xml.parsers.SAXParserFactory;

public class FileUtil {
    public static File getSaveFile(Context context) {
        File file = new File(context.getFilesDir(), "pic.jpg");
        return file;
    }

    public static  String xmlPath="/storage/emulated/0/XXCJ/fileCache/";
    public static String xmlName1="Definition.xml";
    public static String xmlName2="datasheet.xml";

}
