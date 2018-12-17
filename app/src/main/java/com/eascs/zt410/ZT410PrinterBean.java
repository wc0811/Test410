package com.eascs.zt410;

import java.io.Serializable;
import java.util.List;

public class ZT410PrinterBean implements Serializable {
    private String strQRMsg;
    private String strQRPosX;
    private String strQRPosY;
    private String strQRZoom1;
    private String strQRZoom2;
    private List<ZT410PrinterStrListBean> listStr;

    public String getStrQRMsg() {
        return strQRMsg;
    }

    public void setStrQRMsg(String strQRMsg) {
        this.strQRMsg = strQRMsg;
    }


    public String getStrQRPosX() {
        return strQRPosX;
    }

    public void setStrQRPosX(String strQRPosX) {
        this.strQRPosX = strQRPosX;
    }

    public String getStrQRPosY() {
        return strQRPosY;
    }

    public void setStrQRPosY(String strQRPosY) {
        this.strQRPosY = strQRPosY;
    }

    public String getStrQRZoom1() {
        return strQRZoom1;
    }

    public void setStrQRZoom1(String strQRZoom1) {
        this.strQRZoom1 = strQRZoom1;
    }

    public String getStrQRZoom2() {
        return strQRZoom2;
    }

    public void setStrQRZoom2(String strQRZoom2) {
        this.strQRZoom2 = strQRZoom2;
    }

    public List<ZT410PrinterStrListBean> getListStr() {
        return listStr;
    }

    public void setListStr(List<ZT410PrinterStrListBean> listStr) {
        this.listStr = listStr;
    }
}
