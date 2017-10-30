package com.heking.qsy.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;
import android.util.Xml;


public class getXml {
    private static String[] mess = {
            "result",
            "message",
            "code",
            "prodExpired",
            "queryCount",
            "firstQuery",
            "qualityReport",
            "name",
            "value",

    };

    public static ArrayList<MessageSearch> parseExternXML(String xml) {
        ArrayList<MessageSearch> list = new ArrayList<MessageSearch>();
        String message = "";
        ByteArrayInputStream tInputStringStream = null;
        try {
            if (xml != null && !xml.trim().equals("")) {
                tInputStringStream = new ByteArrayInputStream(xml.getBytes());
            }
        } catch (Exception e) {
        }
        XmlPullParser parser = Xml.newPullParser();
        try {
            parser.setInput(tInputStringStream, "UTF-8");
            int eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:// 文档开始事件,可以进行数据初始化处理

                        break;
                    case XmlPullParser.START_TAG:// 开始元素事件
                        String name = parser.getName();
                        for (String ioNaem : mess) {
                            boolean is = true;
                            String str = "";
                            if (name.equalsIgnoreCase(ioNaem)) {
                                if (name.equals("name")) {
                                    is = false;
                                    str = name + "=" + parser.nextText();
                                } else if (name.equals("value")) {
                                    str = name + "=" + parser.nextText();
                                    is = true;
                                } else {
                                    is = true;
                                    str = name + "=" + parser.nextText();
                                }
                                if (is) {
                                    message += str + ",";
                                } else {
                                    message += str + "_";
                                }
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:// 结束元素事件

                        break;
                }
                eventType = parser.next();
            }
            tInputStringStream.close();
            // return persons;
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String[] strss = message.split(",");
        if (strss[1].equals("message=监管码查询失败")) {
            MessageSearch MessageSearch = new MessageSearch();
            MessageSearch.setKey("message");
            MessageSearch.setValue("监管码查询失败");
            list.add(MessageSearch);
        } else {
            for (String mes : strss) {
                if (mes.indexOf("name=") == -1) {
                    MessageSearch MessageSearch = new MessageSearch();
                    String[] str_1 = mes.split("=");
                    MessageSearch.setKey(str_1[0]);
                    MessageSearch.setValue(str_1[1]);
                    list.add(MessageSearch);
                } else {
                    String messa = mes.replace("name=", "");
                    String messa1 = messa.replace("_value=", ",　");
                    String[] str_2 = messa1.split(",");
                    MessageSearch MessageSearch1 = new MessageSearch();
                    MessageSearch1.setKey(str_2[0]);
                    MessageSearch1.setValue(str_2[1]);
                    list.add(MessageSearch1);
                }
            }
        }

        Log.d("测试数据", message);
        return list;
    }


}
