# wheelview_lib
## 结构  ##
|----weathorapp
|
|----wheelview_lib
##效果图##
![](http://i.imgur.com/rdNYOEA.png)
##使用##
1.设置
	 	loopview_province.setNotLoop();//不能循环
        loopview_province.setListener(this);//设置OnItemSelectedListener监听
        loopview_province.setInitPosition(0);//设置初始位置
2.布局	
	 <com.imgpng.wheelview.LoopView
                android:id="@+id/loopview_county"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                 />
## 其他说明 ##
基于第三方WheelView改进