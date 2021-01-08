package com.xhwl.xiyi;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.PopupWindow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List< Integer > arr_l = new ArrayList< Integer >();
        arr_l.add(10);
        arr_l.add(20);
        arr_l.add(30);
        arr_l.add(40);
        arr_l.add(50);
        Collections.swap(arr_l, 0, 0);
        List< Integer > arr_2 = new ArrayList< Integer >();
        arr_2.add(10);
        arr_2.add(20);
        arr_2.add(30);
        arr_2.add(40);
        arr_2.add(50);
        Collections.shuffle(arr_2);

        ArrayList<Integer> intList = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        Collections.rotate(intList, -3);

        //Collections.reverse(intList);
        Log.e("TAG", "onCreate: "+arr_l +"---"+arr_2+"---"+intList);

        WindowManager.LayoutParams lp =getWindow()
                .getAttributes();
        lp.alpha = 0.4f;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(lp);
        PopupWindow popupWindow = new PopupWindow();

    }
}