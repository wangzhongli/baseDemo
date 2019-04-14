package com.momo.basedemo.util;

import java.util.ArrayList;
import java.util.List;

public class DataServer {

    public static List<String> getSampleData(int lenth) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < lenth; i++) {
//            Status status = new Status();
//            status.setUserName("Chad" + i);
//            status.setCreatedAt("04/05/" + i);
//            status.setRetweet(i % 2 == 0);
//            status.setUserAvatar("https://avatars1.githubusercontent.com/u/7698209?v=3&s=460");
//            status.setText("BaseRecyclerViewAdpaterHelper https://www.recyclerview.org");
            list.add("BaseRecyclerViewAdpaterHelper"+i);
        }
        return list;
    }

}
