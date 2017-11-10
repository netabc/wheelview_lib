package com.imgpng.wheelview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by admin on 2017/11/9.
 */

public class DatePicker  extends LinearLayout{
    private static final SimpleDateFormat format = new SimpleDateFormat();
    private String startTime;
    private String endTime;
    private String formatTime;
    private String formatSplit;
    private OnDatePickerChangeListener onDatePickerChangeListener;

    public void setOnDatePickerChangeListener(OnDatePickerChangeListener onDatePickerChangeListener) {
        this.onDatePickerChangeListener = onDatePickerChangeListener;
    }

    public DatePicker(Context context) {
        this(context,null);
    }
    public DatePicker(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public DatePicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        this.setGravity(Gravity.CENTER_VERTICAL);
    }

    private void init() {
        startTime = getContext().getResources().getString(R.string.start_time);
        endTime = getContext().getResources().getString(R.string.end_time);
        formatTime = getContext().getResources().getString(R.string.format_time);
        formatSplit = getContext().getResources().getString(R.string.format_split);
        if(TextUtils.isEmpty(startTime)||TextUtils.isEmpty(endTime)){
            throw new RuntimeException("请先配置 如: <string name=\"start_time\">1993年03月12日 00时00分00秒</string>\n" +
                    "    <string name=\"end_time\">2100年03月12日 00时00分00秒</string>\n" +
                    "    <string name=\"format_time\">yyyy年MM月dd日 HH时mm分ss秒</string>");
        }
        format.applyPattern(formatTime);
        try {
            Date startDate = format.parse(startTime);
            selectCalendar.setTime(startDate);
            Date endDate = format.parse(endTime);
            String format = getFormat();
            createView(format,startDate,endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void createView(String format, Date startDate, Date endDate) {
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        start.setTime(startDate);
        end.setTime(endDate);

        if("YYYYMMDD".equals(format)){//只取年月日
            addView(start.get(Calendar.YEAR),end.get(Calendar.YEAR),formatSplit,Calendar.YEAR);
            addView("年",10,0,0,0);
            addView(start.get(Calendar.MONTH)+1,end.get(Calendar.MONTH)+1,formatSplit, Calendar.MONTH);
            addView("月",10,0,0,0);
            addView(start.get(Calendar.DAY_OF_MONTH),end.get(Calendar.DAY_OF_MONTH),formatSplit, Calendar.DAY_OF_MONTH);
            addView("日",10,0,0,0);
        }else if("HHMMSS".equals(format)){//时分秒
            addView(start.get(Calendar.HOUR_OF_DAY),end.get(Calendar.HOUR_OF_DAY),formatSplit, Calendar.HOUR_OF_DAY);
            addView("时",10,0,0,0);
            addView(start.get(Calendar.MINUTE),end.get(Calendar.MINUTE),formatSplit, Calendar.MINUTE);
            addView("分",10,0,0,0);
            addView(start.get(Calendar.SECOND),end.get(Calendar.SECOND),formatSplit, Calendar.SECOND);
            addView("秒",10,0,0,0);
        }else if("MMDD".equals(format)){//月日
            addView(start.get(Calendar.MONTH)+1,end.get(Calendar.MONTH)+1,formatSplit, Calendar.MONTH);
            addView("月",10,0,0,0);
            addView(start.get(Calendar.DAY_OF_MONTH),end.get(Calendar.DAY_OF_MONTH),formatSplit, Calendar.DAY_OF_MONTH);
            addView("日",10,0,0,0);
        }else if("YYYYMMDDHHMM".equals(format)){//年月日时分
            addView(start.get(Calendar.YEAR),end.get(Calendar.YEAR),formatSplit, Calendar.YEAR);
            addView("年",10,0,0,0);
            addView(start.get(Calendar.MONTH)+1,end.get(Calendar.MONTH)+1,formatSplit, Calendar.MONTH);
            addView("月",10,0,0,0);
            addView(start.get(Calendar.DAY_OF_MONTH),end.get(Calendar.DAY_OF_MONTH),formatSplit, Calendar.DAY_OF_MONTH);
            addView("日",10,0,0,0);
            addView(start.get(Calendar.HOUR_OF_DAY),end.get(Calendar.HOUR_OF_DAY),formatSplit, Calendar.HOUR_OF_DAY);
            addView("时",10,0,0,0);
            addView(start.get(Calendar.MINUTE),end.get(Calendar.MINUTE),formatSplit, Calendar.MINUTE);
            addView("分",10,0,0,0);
        }else if("YYYYMMDDHHMMSS".equals(format)){//年月日时分秒
            addView(start.get(Calendar.YEAR),end.get(Calendar.YEAR),formatSplit, Calendar.YEAR);
            addView("年",10,0,0,0);
            addView(start.get(Calendar.MONTH)+1,end.get(Calendar.MONTH)+1,formatSplit, Calendar.MONTH);
            addView("月",10,0,0,0);
            addView(start.get(Calendar.DAY_OF_MONTH),end.get(Calendar.DAY_OF_MONTH),formatSplit, Calendar.DATE);
            addView("日",10,0,45,0);
            addView(start.get(Calendar.HOUR_OF_DAY),end.get(Calendar.HOUR_OF_DAY)," ", Calendar.HOUR_OF_DAY);
            addView("时",10,0,0,0);
            addView(start.get(Calendar.MINUTE),end.get(Calendar.MINUTE),formatSplit, Calendar.MINUTE);
            addView("分",10,0,0,0);
            addView(start.get(Calendar.SECOND),end.get(Calendar.SECOND),formatSplit, Calendar.SECOND);
            addView("秒",10,0,0,0);
        }else if("HHMM".equals(format)){//时分
            addView(start.get(Calendar.HOUR_OF_DAY),end.get(Calendar.HOUR_OF_DAY),formatSplit, Calendar.HOUR_OF_DAY);
            addView("时",10,0,0,0);
            addView(start.get(Calendar.MINUTE),end.get(Calendar.MINUTE),formatSplit, Calendar.MINUTE);
            addView("分",10,0,0,0);
        }else{
            Log.e("group","错误"+1);
        }
    }

    private void addView(String split, int left,int top,int right,int bottom) {
        TextView textView = new TextView(getContext());
        textView.setText(split);
        textView.setTextSize(16);
        textView.setTextColor(0xff313131);
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        params.setMargins(left,top,right,bottom);
        params.gravity = Gravity.CENTER_VERTICAL;
        addView(textView,params);
    }

    private void addView(int start, int end, String spit, int tag) {
        List<String> items = new ArrayList<>();
        for (int i=start;i<=end;i++){
            items.add(""+i+spit);
        }
        if(items.size()>0){
            LoopView loop = new LoopView(getContext());
            loop.setTag(tag);
            LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
            params.weight=1;
            loop.setLayoutParams(params);
            this.addView(loop);
            loop.setItems(items);
            loop.setInitPosition(0);
            loop.setListener(onItemSelectedListane);
        }

    }

    private String getFormat() {
        String dateFormatL ="[yMdHms]+";
        Matcher matcher = Pattern.compile(dateFormatL).matcher(formatTime);
        StringBuilder ormat = new StringBuilder();
        while(matcher.find()){
            String group = matcher.group();
            ormat.append(group);
            Log.e("group",""+group);
        }
        return ormat.toString().toUpperCase();
    }
    Calendar selectCalendar = Calendar.getInstance();
    OnItemSelectedListener onItemSelectedListane = new OnItemSelectedListener() {
        @Override
        public void onItemSelected(LoopView view, int index) {
            List<String> viewItems = view.getItems();
            int tag = (Integer) view.getTag();
            if(tag == Calendar.YEAR){
                selectCalendar.set(Calendar.YEAR,Integer.parseInt(viewItems.get(index).trim()));
            }else if(tag == Calendar.MONTH){
                selectCalendar.set(Calendar.MONTH,Integer.parseInt(viewItems.get(index).trim())-1);
            }else if(tag == Calendar.DAY_OF_MONTH){
                selectCalendar.set(Calendar.DAY_OF_MONTH,Integer.parseInt(viewItems.get(index).trim()));
            }else if(tag == Calendar.HOUR_OF_DAY){
                selectCalendar.set(Calendar.HOUR_OF_DAY,Integer.parseInt(viewItems.get(index).trim()));
            }else if(tag == Calendar.MINUTE){
                selectCalendar.set(Calendar.MINUTE,Integer.parseInt(viewItems.get(index).trim()));
            }else if(tag == Calendar.SECOND){
                selectCalendar.set(Calendar.SECOND,Integer.parseInt(viewItems.get(index).trim()));
            }
            Toast.makeText(getContext(),""+format.format(selectCalendar.getTime()),Toast.LENGTH_SHORT).show();
            if(onDatePickerChangeListener !=null){
                onDatePickerChangeListener.onChange(selectCalendar.getTime());
            }
        }
    };
   public interface OnDatePickerChangeListener{
       public void onChange(Date date);
   }

   public void selectDate(Date date){

   }
}
