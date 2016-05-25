package com.smarter.LoveLog.activity;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.smarter.LoveLog.R;
import com.smarter.LoveLog.ui.McoySnapPageLayout.McoyScrollView;
import com.smarter.LoveLog.ui.SildingFinishLayout;
import com.smarter.LoveLog.utills.DeviceUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/11/30.
 */
public abstract class BaseBestActivity extends BaseFragmentActivity  {
    String Tag = "BaseBestActivity";
    Context mContext;


    /**
     * RecyclerView
     */
    @Bind(R.id.recyclerview)
    XRecyclerView mRecyclerView;
    @Bind(R.id.centerTitle)
    TextView centerTitle;


    @Bind(R.id.networkInfo)
    LinearLayout networkInfo;
    @Bind(R.id.errorInfo)
    ImageView errorInfo;
    @Bind(R.id.newLoading)
    LinearLayout newLoading;
    @Bind(R.id.loadingTextLinear)
    LinearLayout loadingTextLinear;
    @Bind(R.id.loadingText)
    TextView loadingText;


    @Bind(R.id.progressLinear)
    LinearLayout progressLinear;

    @Bind(R.id.progreView)
    ImageView progreView;

    @Bind(R.id.backBUt)
    ImageView backBUt;

    @Bind(R.id.sildingFinishLayout)
    SildingFinishLayout sildingFinishLayout;
    LinearLayout allLinear;
    LinearLayout xuanfuBar;
    @Bind(R.id.rightTitle)
    TextView rightTitle;
    @Bind(R.id.productDetail_scrollview)
    McoyScrollView productDetailScrollview;


    public abstract void dosetPromo();

    public abstract void initDataAbs();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        setContentView(R.layout.base_best_activity);
        mContext = this;
        allLinear = (LinearLayout) findViewById(R.id.allLinear);
        xuanfuBar=(LinearLayout) findViewById(R.id.xuanfuBar);
        dosetPromo();
        ButterKnife.bind(this);


        newWait();
        setListen();

    }

    private void setListen() {
        backBUt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //右滑finish
        sildingFinishLayout.setOnSildingFinishListener(new SildingFinishLayout.OnSildingFinishListener() {

            @Override
            public void onSildingFinish() {
                BaseBestActivity.this.finish();
            }
        });

        sildingFinishLayout.setTouchView(mRecyclerView);
    }


    /**
     * 暂无评论
     *
     * @param textStr
     */
    public void Ba________nullNetworkData(String textStr) {
        progressLinear.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.GONE);
        errorInfo.setImageDrawable(getResources().getDrawable(R.mipmap.error_nodata));
        networkInfo.setVisibility(View.VISIBLE);
        newLoading.setVisibility(View.GONE);
        loadingTextLinear.setVisibility(View.VISIBLE);
        loadingText.setText(textStr);
    }

    /**
     * 失败请求
     */
    public void Ba________shibaiNetworkData() {
        // 请求失败
        progressLinear.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.GONE);
        errorInfo.setImageDrawable(getResources().getDrawable(R.mipmap.error_nodata));
        networkInfo.setVisibility(View.VISIBLE);
    }

    /**
     * 未知错误
     */
    public void Ba________weizhiErrorNetworkData() {
        //未知错误
        progressLinear.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.GONE);
        errorInfo.setImageDrawable(getResources().getDrawable(R.mipmap.error_default));
        networkInfo.setVisibility(View.VISIBLE);
    }


    /**
     * 进度条消失
     */
    public void Ba________progresNetworkGone() {
        progressLinear.setVisibility(View.GONE);
    }

    /**
     * 完成刷新更多
     */
    public void Ba________recycyeNetworkCompetleMore() {
        mRecyclerView.loadMoreComplete();
    }


    /**
     * 完成刷新
     */
    public void Ba________recycyeNetworkCompetle() {
        mRecyclerView.refreshComplete();
    }

    /**
     * 在中间布局后面添加布局
     */
    public void Ba________addViewToallLinear(View view) {
        allLinear.addView(view);
    }

    /**
     * 在悬浮栏添加布局
     */
    public void Ba________addViewXuanFuLinear(View view) {
        xuanfuBar.addView(view);
    }


    private void newWait() {
        if (DeviceUtil.checkConnection(mContext)) {
            //加载动画
            progressLinear.setVisibility(View.VISIBLE);
            AnimationDrawable animationDrawable = (AnimationDrawable) progreView.getDrawable();
            animationDrawable.start();

            mRecyclerView.setVisibility(View.VISIBLE);
            networkInfo.setVisibility(View.GONE);

            initDataAbs();


        } else {
            errorInfo.setImageDrawable(getResources().getDrawable(R.mipmap.error_nowifi));
            mRecyclerView.setVisibility(View.GONE);
            networkInfo.setVisibility(View.VISIBLE);
            newLoading.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    newWait();
                }
            });
        }
    }


//客户端以json串的post请求方式进行提交,服务端返回json串
       /* CategoryJson categoryJson=new CategoryJson();
        PaginationJson paginationJson2=new PaginationJson();
        paginationJson2.setCount("5");
        paginationJson2.setPage("2");
        categoryJson.setId("4");
        categoryJson.setPagination(paginationJson2);


//        String string2 = JSON.toJSONString(paginationJson2);



        Map<String, String> mapTT = new HashMap<String, String>();
        mapTT.put("id", id);
        mapTT.put("pagination","{\"page\":\"3\",\"count\":\"3\"} ");
        JSONObject jsonObject = new JSONObject(mapTT);
//        Log.d("pingluActivity", "->>>>> " + string2);


        JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(Request.Method.POST,url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("pingluActivity", "response -> " + response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("pingluActivity", error.getMessage(), error);
            }
        })
        {
            //注意此处override的getParams()方法,在此处设置post需要提交的参数根本不起作用
            //必须象上面那样,构成JSONObject当做实参传入JsonObjectRequest对象里
            //所以这个方法在此处是不需要的
//    @Override
//    protected Map<String, String> getParams() {
//          Map<String, String> map = new HashMap<String, String>();
//            map.put("name1", "value1");
//            map.put("name2", "value2");

//        return params;
//    }

            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json; charset=UTF-8");

                return headers;
            }
        };
        mQueue.add(jsonRequest);


*/





    /*   StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://mapp.aiderizhi.com/?url=/post/category",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("onResponse", "response -> " + response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("onResponse", error.getMessage(), error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // 在这里设置需要post的参数
                Map<String, String> map = new HashMap<String, String>();

                PaginationJson paginationJson=new PaginationJson();
                paginationJson.setCount("2");
                paginationJson.setPage("2");


                String string = JSON.toJSONString(paginationJson);
                String  d="{"+"\"pagination\":"+string+"}";
                Log.d("onResponse", "response >>>>>>>>>>>>>-> " + d);
                map.put("json", d);
                map.put("id", id);
//                map.put("id","22758");
//                map.put("type","2");
                return map;
            }
        };
        mQueue.add(stringRequest);*/





    // 手指上下滑动时的最小速度
    private static final int YSPEED_MIN = 1000;

    // 手指向右滑动时的最小距离
    private static final int XDISTANCE_MIN = 150;

    // 手指向上滑或下滑时的最小距离
    private static final int YDISTANCE_MIN = 100;

    // 记录手指按下时的横坐标。
    private float xDown;

    // 记录手指按下时的纵坐标。
    private float yDown;

    // 记录手指移动时的横坐标。
    private float xMove;

    // 记录手指移动时的纵坐标。
    private float yMove;

    // 用于计算手指滑动的速度。
    private VelocityTracker mVelocityTracker;


    /**
     * 创建VelocityTracker对象，并将触摸界面的滑动事件加入到VelocityTracker当中。
     *
     * @param event
     */
    private void createVelocityTracker(MotionEvent event) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
    }

    /**
     * 回收VelocityTracker对象。
     */
    private void recycleVelocityTracker() {
        mVelocityTracker.recycle();
        mVelocityTracker = null;
    }

    /**
     * @return 滑动速度，以每秒钟移动了多少像素值为单位。
     */
    private int getScrollVelocity() {
        mVelocityTracker.computeCurrentVelocity(1000);
        int velocity = (int) mVelocityTracker.getYVelocity();
        return Math.abs(velocity);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        createVelocityTracker(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDown = event.getRawX();
                yDown = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                xMove = event.getRawX();
                yMove = event.getRawY();
                // 滑动的距离
                int distanceX = (int) (xMove - xDown);
                int distanceY = (int) (yMove - yDown);
                // 获取顺时速度
                int ySpeed = getScrollVelocity();
                // 关闭Activity需满足以下条件：
                // 1.x轴滑动的距离>XDISTANCE_MIN
                // 2.y轴滑动的距离在YDISTANCE_MIN范围内
                // 3.y轴上（即上下滑动的速度）<XSPEED_MIN，如果大于，则认为用户意图是在上下滑动而非左滑结束Activity
                if (ySpeed > 100) {
                } else if (ySpeed < 100) {
                    if (distanceX > XDISTANCE_MIN
                            && (distanceY < YDISTANCE_MIN && distanceY > -YDISTANCE_MIN)
                            && ySpeed < YSPEED_MIN) {


                     /*   finish();
                        overridePendingTransition(R.anim.in_from_left,
                                R.anim.out_to_right);*/


                    } else if (distanceX < -XDISTANCE_MIN
                            && (distanceY < YDISTANCE_MIN && distanceY > -YDISTANCE_MIN)
                            && ySpeed < YSPEED_MIN) {


//                        overridePendingTransition(R.anim.in_from_right,
//                                R.anim.out_to_left);
                    }
                }

                break;
            case MotionEvent.ACTION_UP:
                recycleVelocityTracker();
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(event);
    }


}
