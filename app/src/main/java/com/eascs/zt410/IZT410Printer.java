package com.eascs.zt410;

import java.util.List;

public interface IZT410Printer {
    void setData(List<ZT410PrinterBean> pList);

    void sendDataToZT410(String pStrPrintMsg);
}
