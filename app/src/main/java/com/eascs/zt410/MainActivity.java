package com.eascs.zt410;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements IZT410Printer {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.tv_PrintTest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<ZT410PrinterBean> listTemp = new ArrayList<>();
                for (int i = 0; i < 40; i++) {
                    listTemp.add(getListPrintItem());
                    listTemp.add(getListPrintItem());
                }
                setData(listTemp);
            }
        });
    }

    private ZT410PrinterBean getListPrintItem() {
        ZT410PrinterBean tItem = new ZT410PrinterBean();
        tItem.setStrQRPosX("10");
        tItem.setStrQRPosY("190");
        tItem.setStrQRMsg("180101");
        tItem.setListStr(getListStrMsg());
        tItem = new ZT410PrinterBean();
        tItem.setStrQRPosX("101");
        tItem.setStrQRPosY("190");
        tItem.setStrQRMsg("180101");
        tItem.setListStr(getListStrMsg());
        tItem = new ZT410PrinterBean();
        tItem.setStrQRPosX("1011");
        tItem.setStrQRPosY("190");
        tItem.setStrQRMsg("180101");
        tItem.setListStr(getListStrMsg());
        return tItem;
    }

    private List<ZT410PrinterStrListBean> getListStrMsg() {
        List<ZT410PrinterStrListBean> listTemp = new ArrayList<>();
        ZT410PrinterStrListBean tItem = null;
        tItem = new ZT410PrinterStrListBean();
        tItem.setStrMsg("打印测试");
        tItem.setStrMsgPosX("220");
        tItem.setStrMsgPosY("135");
        tItem.setStrMsgX("50");
        tItem.setStrMsgY("50");
        listTemp.add(tItem);
        return listTemp;
    }

                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        @Override
    public void setData(List<ZT410PrinterBean> pList) {
        final int PRINT_LABEL_NUM = 20;
        String content = "";
        int i = 0;
        for (ZT410PrinterBean tItem : pList) {
//            二维码
            content += String.format("^XA^CI28^A@N,30,30,E:stxinwei.TTF^FT%s,%s^BQN,3,9^FDMA,%s",
                    null2Zero(tItem.getStrQRPosX()), null2Zero(tItem.getStrQRPosY()),
                    null2Str(tItem.getStrQRMsg()));
//           其他数据
            for (ZT410PrinterStrListBean tItemStrList : tItem.getListStr()) {
                content += String.format("^FS^FT %s,%s^A@N,%s,%s^FD%s",
                        null2Zero(tItemStrList.getStrMsgPosX()), null2Zero(tItemStrList.getStrMsgPosY()),
                        null2Zero(tItemStrList.getStrMsgX()), null2Zero(tItemStrList.getStrMsgY()),
                        null2Str(tItemStrList.getStrMsg() +"     "+ String.valueOf(i++)));
            }

//        打印结束标识
            content += "^FS^PQ1,0,0,N^XZ";
            if (i % PRINT_LABEL_NUM == 0) {
                sendDataToZT410(content);
                content = "";
            }
        }
        sendDataToZT410(content);
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

    @Override
    public void sendDataToZT410(final String pStrPrintMsg) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                Socket socket = null;
                OutputStream os = null;//字节输出流
                PrintWriter pw = null;//字节输出流
                try {
                    socket = new Socket("172.28.4.92", 6101);
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

//                ZebraPrinterConnection connection = null;
//                connection = new TcpPrinterConnection("172.28.5.39", 6101);
//                try {
//                    connection.open();
//                    ZebraPrinter printer = ZebraPrinterFactory.getInstance(connection);
//                    File filepath = getFileStreamPath("TEST.LBL");
//                    OutputStream os = openFileOutput("TEST.LBL", Context.MODE_PRIVATE);
//                    OutputStreamWriter outw = null;
//                    try {
//                        outw = new OutputStreamWriter(os, "UTF-8");
//                    } catch (UnsupportedEncodingException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
//
//                    try {
//                        outw.write(pStrPrintMsg);
//                        outw.close();
//                        os.flush();
//                        os.close();
//                    } catch (IOException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
//
//                    connection.close();
//                } catch (ZebraPrinterConnectionException e) {
//                    String strTemp = e.toString();
//                } catch (ZebraPrinterLanguageUnknownException e) {
//                } catch (FileNotFoundException e) {
//                    // TODO Auto-generated catch block
//                } finally {
//                }
            }
        }.start();
    }
}
