package com.xhwl.xiyi;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity implements NumberPicker.Formatter{

    private TextView numLimitation;
    private NumberPicker np1,np2;
    //定义上下限具体值
    private int min,max;
    String[] aar={"","0","30","60"};

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        numLimitation = findViewById(R.id.num_limitation);
        numLimitation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.num_limitation:
                        //人数限制
                        showDialog(numLimitation, numberlist, 1);
                        break;
                }
            }
        });
        initData();
        List< Integer > arr_l = new ArrayList< Integer >();
        arr_l.add(10);
        arr_l.add(20);
        arr_l.add(30);
        arr_l.add(40);
        arr_l.add(50);
        //Collections.swap(arr_l, 0, 0);
        arr_l.set(2,100);
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

     /*   WindowManager.LayoutParams lp =getWindow()
                .getAttributes();
        lp.alpha = 0.4f;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(lp);
        PopupWindow popupWindow = new PopupWindow();*/
        np1 = (NumberPicker) findViewById(R.id.np1);
        //设置np1的最大值只和最小值
        np1.setDisplayedValues(aar);
        np1.setMinValue(0);
        np1.setMaxValue(aar.length-1);
        //设置哪怕的当前值
        np1.setValue(2);
        np1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                min = newVal;
                showSelectedPrice();
            }
        });
        np2 = (NumberPicker) findViewById(R.id.np2);
        //设置np1的最大值只和最小值
        np2.setMinValue(0);
        np2.setMaxValue(23);
        //设置哪怕的当前值
        np2.setValue(min);
        np2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                max = newVal;
                showSelectedPrice();
            }
        });

        //设置文字，分割线颜色
        setPickerDividerColor();
        setNumberPickerTextColor(np1,Color.BLUE);
    }
    private void showSelectedPrice(){
        Toast.makeText(MainActivity.this,format(min)+"设定闹钟时间为：" + min + " : " + max,Toast.LENGTH_SHORT).show();
    }
    //这个方法是根据index 格式化先生的文字,需要先 implements NumberPicker.Formatter
    @Override
    public String format(int value) {
        return aar[value];
    }

    /**
     * 通过反射改变分割线颜色,
     */
    private void setPickerDividerColor() {
        Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (Field pf : pickerFields) {
            if (pf.getName().equals("mSelectionDivider")) {
                pf.setAccessible(true);
                try{
                    pf.set(np1,new ColorDrawable(Color.RED));
                }catch (IllegalAccessException e) {
                    e.printStackTrace();
                }catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 过反射改变文字的颜色
     * @param numberPicker
     * @param color
     * @return
     */
    public static boolean setNumberPickerTextColor(NumberPicker numberPicker, int color)
    {
        final int count = numberPicker.getChildCount();
        for(int i = 0; i < count; i++){
            View child = numberPicker.getChildAt(i);
            if(child instanceof EditText){
                try{
                    Field selectorWheelPaintField = numberPicker.getClass()
                            .getDeclaredField("mSelectorWheelPaint");
                    selectorWheelPaintField.setAccessible(true);
                    ((Paint)selectorWheelPaintField.get(numberPicker)).setColor(color);
                    ((EditText)child).setTextColor(color);
                    numberPicker.invalidate();
                    return true;
                }
                catch(NoSuchFieldException e){
                    Log.w("setTextColor", e);
                }
                catch(IllegalAccessException e){
                    Log.w("setTextColor", e);
                }
                catch(IllegalArgumentException e){
                    Log.w("setTextColor", e);
                }
            }
        }
        return false;
    }




    private String selectText = "";
    private String selectText1 = "";
    private ArrayList<String> numberlist = new ArrayList<>();


    //数字选择器
    private void initData() {
        numberlist.clear();
        for (int i = 0; i <=2; i++) {
            numberlist.add(String.format("%d人", i));
        }
    }

    private void showDialog(TextView textView, ArrayList<String> list, int selected){
        showChoiceDialog(list, textView, selected);
    }

    private void showChoiceDialog(ArrayList<String> dataList, final TextView textView, int selected){
        selectText = "";
        selectText1 = "";
        View outerView = LayoutInflater.from(this).inflate(R.layout.dialog_wheelview,null);
        final WheelView wheelView = outerView.findViewById(R.id.wheel_view);
        final WheelView wheelView1 = outerView.findViewById(R.id.wheel_view1);
        wheelView.setOffset(2);// 对话框中当前项上面和下面的项数
        wheelView.setItems(dataList);// 设置数据源
        wheelView.setSeletion(selected);// 默认选中第三项
        wheelView.setOnWheelViewListener(  new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                selectText = item;
            }
        } );

        wheelView1.setOffset(2);// 对话框中当前项上面和下面的项数
        wheelView1.setItems(dataList);// 设置数据源
        wheelView1.setSeletion(selected);// 默认选中第三项
        wheelView1.setOnWheelViewListener(  new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                selectText = item;
            }
        });

        // 显示对话框，点击确认后将所选项的值显示到Button上
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setView(outerView)
                .setPositiveButton("确认",
                        (dialogInterface, i) -> {
                            numLimitation.setText(selectText1+"--"+selectText);
                            numLimitation.setTextColor(this.getResources().getColor(R.color.txtnumber));
                        })
                .setNegativeButton("取消",null).create();
        alertDialog.show();
        int green = this.getResources().getColor(R.color.buyvip);
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(green);
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(green);
    }
}