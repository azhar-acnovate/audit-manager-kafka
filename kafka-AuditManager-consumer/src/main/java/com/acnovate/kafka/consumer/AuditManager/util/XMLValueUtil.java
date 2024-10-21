package com.acnovate.kafka.consumer.AuditManager.util;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class XMLValueUtil {
    public static String getTagValue(String sTag, Element eElement) {
        String nodeValue = null;
        NodeList nodelist = eElement.getElementsByTagName(sTag);
        Element element1 = (Element) nodelist.item(0);
        if (element1.hasChildNodes()) {
            NodeList nlList = element1.getChildNodes();
            nodeValue = nlList.item(0).getNodeValue();
            return nodeValue;
        }
        return nodeValue;
    }

    public static double getNUMTagValue(String value, String eElement) {
        double dd = 0.0D;
        if (value != null && !"null".equalsIgnoreCase(value.trim()) && !"".equals(value.trim()))
            dd = Double.valueOf(value).doubleValue();
        return dd;
    }

    public static long getLongTagValue(String value, String eElement) {
        long ll = 0L;
        if (value != null && !"null".equalsIgnoreCase(value.trim()) && !"".equals(value.trim()))
            ll = Long.valueOf(value).longValue();
        return ll;
    }

    public static Date getDateTagValue(String sTag, Element eElement) throws Exception {
        Date dt = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String TimeStr = getTagValue(sTag, eElement);
        if (TimeStr != null && !"null".equalsIgnoreCase(TimeStr) && !"".equals(TimeStr.trim()))
            dt = dateFormat.parse(TimeStr);
        return dt;
    }

    public static Date getDateTagValue(String sTag, String eElement) throws Exception {
        Date dt = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String TimeStr = sTag;
        if (TimeStr != null && !"null".equalsIgnoreCase(TimeStr.trim()) && !"".equals(TimeStr.trim()))
            dt = dateFormat.parse(TimeStr);
        return dt;
    }

    public static int getIntTagValue(String sTag, Element eElement) {
        int a = 0;
        String s = getTagValue(sTag, eElement);
        if (s != null && !s.isEmpty())
            //changed from integer.parseInt to Float.parse
            a = (int) Float.parseFloat(s);
        return a;
    }

    public static int getIntTagValue(String sTag, String eElement) {
        int a = 0;
        String s = sTag;
        if (s != null && !s.isEmpty())
            //changed from integer.parseInt to Float.parse
            a = (int) Float.parseFloat(s);
        return a;
    }

    public static Timestamp getTimestampValue() throws Exception {
        SimpleDateFormat gmtDateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss z");
        gmtDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        Timestamp gmtStamp = new Timestamp(gmtDateFormat.parse(gmtDateFormat.format(new Date())).getTime());
        return gmtStamp;
    }
}
