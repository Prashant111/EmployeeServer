package com.airtel.test.util;

import java.util.List;

public class GeneralUtils {
    public static boolean isListBlank(List list) {
        return list == null || list.size() == 0;
    }

    public static boolean isListNotBlank(List list) {
        return !(isListBlank(list));
    }
}
