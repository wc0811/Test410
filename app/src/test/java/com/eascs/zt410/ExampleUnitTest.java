package com.eascs.zt410;

import org.junit.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        List<ZT410PrinterBean> listTemp = new ArrayList<>();
        listTemp.add(getListPrintItem());
        setData(listTemp);
    }


    private ZT410PrinterBean getListPrintItem() {
        ZT410PrinterBean tItem = new ZT410PrinterBean();
        tItem.setStrQRPosX("5");
        tItem.setStrQRPosY("60");
        tItem.setStrQRMsg("180101");
        tItem.setListStr(getListStrMsg());
        return tItem;
    }

    private List<ZT410PrinterStrListBean> getListStrMsg() {
        List<ZT410PrinterStrListBean> listTemp = new ArrayList<>();
        ZT410PrinterStrListBean tItem = null;
        tItem = new ZT410PrinterStrListBean();
        tItem.setStrMsg("打印测试");
        tItem.setStrMsgPosX("5");
        tItem.setStrMsgPosY("140");
        tItem.setStrMsgX("30");
        tItem.setStrMsgY("30");
        return listTemp;
    }

    public void setData(List<ZT410PrinterBean> pList) {

        String content = "";
        for (ZT410PrinterBean tItem : pList) {
//            二维码
            content += String.format("^XA^CI28^A@N,30,30,E:stxinwei.TTF^FT%s,%s^BQN,2,6^FDMA,%s",
                    null2Zero(tItem.getStrQRPosX()), null2Zero(tItem.getStrQRPosY()),
                    null2Str(tItem.getStrQRMsg()));
//           其他数据
            for (ZT410PrinterStrListBean tItemStrList : tItem.getListStr()) {
                content += String.format("^FS^FT %s,%s^A@N,%s,%s^FD%s",
                        null2Zero(tItemStrList.getStrMsgPosX()), null2Zero(tItemStrList.getStrMsgPosY()),
                        null2Zero(tItemStrList.getStrMsgX()), null2Zero(tItemStrList.getStrMsgY()),
                        null2Str(tItemStrList.getStrMsg()));
            }

//        打印结束标识
            content += "^FS^PQ1,0,0,N^XZ";
            sendDataToZT410(content);
        }
    }


    public String null2Str(String pStr) {
        if (null == pStr) {
            return "";
        }
        if ("null".equals(pStr)) {
            return "";
        }
        return pStr;
    }

    public String null2Zero(String pStr) {
        if (null == pStr) {
            return "0";
        }
        if (!isInteger(pStr)) {
            return "0";
        }
        return pStr;
    }


    public boolean isInteger(String value) {

        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }

    }

    public void sendDataToZT410(final String pStrPrintMsg) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                Socket socket = null;
                OutputStream os = null;//字节输出流
                PrintWriter pw = null;//字节输出流
                try {
                    socket = new Socket("172.28.5.39", 6101);
                    os = socket.getOutputStream();
                    pw = new PrintWriter(os);
                    pw.write(pStrPrintMsg);
                    pw.flush();
                    socket.shutdownOutput();
                    pw.close();
                    os.close();
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}