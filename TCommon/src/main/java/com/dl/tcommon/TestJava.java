package com.dl.tcommon;

import android.content.Context;
import android.widget.Toast;

/**
 * @Author: DL
 * @Date: 2025/5/27 17:34
 * @Description:
 */
public class TestJava {
    public static String test(Context context) {
        Toast.makeText(context, "调用Java代码成功", Toast.LENGTH_SHORT).show();
        return "result java string";
    }
}
