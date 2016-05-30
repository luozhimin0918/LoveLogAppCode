package com.smarter.LoveLog.ui.myWeigt;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.smarter.LoveLog.R;
import com.smarter.LoveLog.activity.MakeOutOrderActivity;
import com.smarter.LoveLog.utills.FaceConversionUtil;

/**
 * Created by Administrator on 2016/5/25.
 */
public class TouchLinearLayout extends LinearLayout {
    Activity mContext;
    public TouchLinearLayout(Context context) {

        super(context);
        this.mContext= (Activity) context;
        Init_View();
    }

    public TouchLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext= (Activity) context;
        Init_View();
    }

    public TouchLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext= (Activity) context;
        Init_View();
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            //获取当前获得当前焦点所在View
            View view = mContext.getCurrentFocus();
            if (MakeOutOrderActivity.isClickEt(view, event)) {


                if(orderIntegralToolLinear.getVisibility()==View.VISIBLE){


                    // 如果不是edittext，则隐藏键盘
                    InputMethodManager inputMethodManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (inputMethodManager != null) {
                        // 隐藏键盘
                        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }

//                    editIntegralAreaText.clearFocus();


//
//                    orderIntegralToolLinear.setVisibility(View.GONE);
//                    orderXuanfuLinear.setVisibility(View.VISIBLE);
//                    editIntegralLinear.setVisibility(View.GONE);


                    return false;





                }
            }
            return super.dispatchTouchEvent(event);
        }

       /* 看源码可知superDispatchTouchEvent  是个抽象方法，用于自定义的Window
        此处目的是为了继续将事件由dispatchTouchEvent ( MotionEvent event ) 传递到onTouchEvent ( MotionEvent event )
        必不可少，否则所有组件都不能触发 onTouchEvent ( MotionEvent event )
        */
        if (mContext.getWindow().superDispatchTouchEvent(event)) {
            return true;
        }
        return onTouchEvent(event);
    }


    /**
     * 初始化控件
     */
    LinearLayout orderIntegralToolLinear;
    private void Init_View() {
        orderIntegralToolLinear = (LinearLayout) findViewById(R.id.orderIntegralToolLinear);


    }
  /*  @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

    }*/


}
