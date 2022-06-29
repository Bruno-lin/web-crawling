package com.teamscorpio.webcrawling;

import java.util.List;

public interface callWeb {

    void callDigiKey(String itemCode, String itemId);

    void callArrow();

    void callMouser();

    void callAvent();

    void getRealItemList(List<String> itemList);
}
