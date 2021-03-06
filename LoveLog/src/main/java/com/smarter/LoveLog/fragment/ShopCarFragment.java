package com.smarter.LoveLog.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jcodecraeer.xrecyclerview.LinearItemDecoration;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.smarter.LoveLog.R;
import com.smarter.LoveLog.activity.LoginActivity;
import com.smarter.LoveLog.activity.MakeOutOrderActivity;
import com.smarter.LoveLog.adapter.RecycleShopCarAdapter;
import com.smarter.LoveLog.db.AppContextApplication;
import com.smarter.LoveLog.db.SharedPreUtil;
import com.smarter.LoveLog.db.SharedPreferences;
import com.smarter.LoveLog.http.FastJsonRequest;
import com.smarter.LoveLog.model.PaginationJson;
import com.smarter.LoveLog.model.loginData.SessionData;
import com.smarter.LoveLog.model.orderMy.ShopCarCreate;
import com.smarter.LoveLog.model.orderMy.ShopCarOrderInfo;
import com.smarter.LoveLog.ui.popwindow.AlertDialog;
import com.smarter.LoveLog.utills.DeviceUtil;
import com.smarter.LoveLog.utills.ViewUtill;

import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2015/11/30.
 */
public class ShopCarFragment extends Fragment implements RecycleShopCarAdapter.OnCheckDefaultListener {
    protected WeakReference<View> mRootView;
    @Bind(R.id.tv_right_title)
    TextView tvRightTitle;
    @Bind(R.id.isImage)
    ImageView isImage;
    @Bind(R.id.buy_now)
    TextView buyNow;
    private View view;


    @Bind(R.id.recyclerview)
    XRecyclerView mRecyclerView;

    @Bind(R.id.networkInfo)
    LinearLayout networkInfo;
    @Bind(R.id.errorInfo)
    ImageView errorInfo;
    @Bind(R.id.newLoading)
    LinearLayout newLoading;

   /* @Bind(R.id.loadingTextLinear)
    LinearLayout loadingTextLinear;
    @Bind(R.id.loadingText)
    TextView loadingText;*/


    @Bind(R.id.progressLinear)
    LinearLayout progressLinear;

    @Bind(R.id.progreView)
    ImageView progreView;

    @Bind(R.id.carLinear)
    LinearLayout carLinear;

    @Bind(R.id.xuanfuBar)
    LinearLayout xuanfuBar;

    @Bind(R.id.hejiText)
    TextView hejiText;
    @Bind(R.id.hejiTop)
    TextView hejiTop;




    Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null || mRootView.get() == null) {
            view = inflater.inflate(R.layout.shop_car_fragment, null);
            mRootView = new WeakReference<View>(view);
            mContext = getContext();
            ButterKnife.bind(this, view);



        } else {
            ViewGroup parent = (ViewGroup) mRootView.get().getParent();
            if (parent != null) {
                parent.removeView(mRootView.get());
            }
        }
//        ButterKnife.bind(this, mRootView.get());
        return mRootView.get();

    }


    @Override
    public void onResume() {
        super.onResume();
        initRecycleViewVertical();
        isLogiin(false);
    }

    SessionData sessionData;
    Boolean isLoginTag = false;

    public void isLogiin(Boolean isFistOnTab) {

        Boolean isLogin = SharedPreferences.getInstance().getBoolean("islogin", false);
        if (isLogin) {
            String sessionString = SharedPreferences.getInstance().getString("session", "");
            sessionData = JSON.parseObject(sessionString, SessionData.class);
            if (sessionData != null && isFistOnTab == false) {
                newWait();


            }

        } else {


            newWait();
            if (isFistOnTab) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {

//                        isLoginTag=true;
                        //登录
                        Intent intent = new Intent(mContext, LoginActivity.class);
                                  /*  Bundle bundle = new Bundle();
                                    bundle.putSerializable("PromotePostsData", (Serializable) pp);
                                    intent.putExtras(bundle);*/
                        mContext.startActivity(intent);


                    }

                }, 100);            //refresh data here
               /* new AlertDialog(mContext).builder().setTitle("提示")
                        .setMsg("您未登录，请登录")
                        .setPositiveButton("确认", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                isLoginTag=true;
                                //登录
                                Intent intent = new Intent(mContext, LoginActivity.class);
                                  *//*  Bundle bundle = new Bundle();
                                    bundle.putSerializable("PromotePostsData", (Serializable) pp);
                                    intent.putExtras(bundle);*//*
                                mContext.startActivity(intent);

                            }
                        }).setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onShopCarLonginListener.onBackShopCarOK(false);
                    }
                }).show();


                mRecyclerView.setVisibility(View.GONE);
                networkInfo.setVisibility(View.VISIBLE);
                carLinear.setVisibility(View.GONE);
                xuanfuBar.setVisibility(View.GONE);
                */


//            Toast.makeText(mContext, "未登录，请先登录", Toast.LENGTH_SHORT).show();
            } else {

                if (isLoginTag) {//isLoginTag为true时返回点购物车之前的页面
                    onShopCarLonginListener.onBackShopCarOK(false);
                    isLoginTag = false;
                }

             /*   mRecyclerView.setVisibility(View.GONE);
                networkInfo.setVisibility(View.VISIBLE);
                carLinear.setVisibility(View.GONE);
                xuanfuBar.setVisibility(View.GONE);*/

            }


        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        ButterKnife.unbind(this);
    }

    boolean   isdeleteOrJiSuan=false;
    @OnClick({R.id.tv_right_title, R.id.isImage, R.id.buy_now})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_right_title:
                 if(idBianji){
                     idBianji=false;
                     tvRightTitle.setText("完成");
                     adapter.myNotifiAdapter(false, true, false);



                     //编辑状态下，编辑栏。。。
                     hejiText.setVisibility(View.GONE);
                     hejiTop.setVisibility(View.GONE);
                     isdeleteOrJiSuan=true;
                     buyNow.setText("删除");

                 }else{
                     idBianji=true;
                     tvRightTitle.setText("编辑");
                     adapter.myNotifiAdapter(false, false,false);

                     //未编辑状态下，编辑栏。。。
                     hejiText.setVisibility(View.VISIBLE);
                     hejiTop.setVisibility(View.VISIBLE);
                     isdeleteOrJiSuan=false;
                     buyNow.setText("去结算");

                 }


                break;
            case R.id.isImage:
                if(isQuanxuna){
                    adapter.myNotifiAdapter(true,false,true);
                    isQuanxuna=false;
                    isImage.setBackgroundResource(R.mipmap.choiceon);



                }else{
                    adapter.myNotifiAdapter(false,false,true);
                    isQuanxuna=true;
                    isImage.setBackgroundResource(R.mipmap.choice);

                }


                break;
            case R.id.buy_now:
                      if(isdeleteOrJiSuan){
                          adapter.myNotifiAdapterDelete();
                      }else{

                          if(SharedPreUtil.isLogin()){
                              Intent intent = new Intent(mContext, MakeOutOrderActivity.class);
                              Bundle bundle = new Bundle();
//                              ShopCarOrderInfo.DataEntity.GoodsListEntity  oneGoodsEntity =adapter.getOrderLists().get(0);
//                              bundle.putSerializable("session", sessionData);
//                              bundle.putSerializable("goods",oneGoodsEntity);
                              bundle.putString("moneyZong",hejinMoney);
                              intent.putExtras(bundle);
                              mContext.startActivity(intent);
                          }else{
                              ViewUtill.ShowAlertDialog(mContext);
                          }

                      }
                break;
        }
    }


    //回调开始
    public interface OnShopCarLonginListener {
        void onBackShopCarOK(Boolean isBack);
    }

    private OnShopCarLonginListener onShopCarLonginListener;

    public void setOnShopCarListener(OnShopCarLonginListener onShopCarLongin) {
        this.onShopCarLonginListener = onShopCarLongin;
    }


    List<ShopCarOrderInfo.DataEntity.GoodsListEntity> loadGoods=new ArrayList<ShopCarOrderInfo.DataEntity.GoodsListEntity>();//本地购物车数据

    int numToshop=0;//本地购物车成功提交登录购物车数量
    private void newWait() {
        if (DeviceUtil.checkConnection(mContext)) {
            //加载动画
            progressLinear.setVisibility(View.VISIBLE);
            AnimationDrawable animationDrawable = (AnimationDrawable) progreView.getDrawable();
            animationDrawable.start();

         /*   tvRightTitle.setText("编辑");
            idBianji=true;
//            isQuanxuna=true;
//            isImage.setBackgroundResource(R.mipmap.choice);
            isdeleteOrJiSuan=false;
            buyNow.setText("去结算");
            hejiText.setVisibility(View.VISIBLE);
            hejiText.setText("");
*/

            if(SharedPreUtil.isLogin()){
                mRecyclerView.setVisibility(View.VISIBLE);
                networkInfo.setVisibility(View.GONE);
                carLinear.setVisibility(View.GONE);


                if(SharedPreUtil.getLocalShopCarData().size()>0){
                    loadGoods.clear();
                    loadGoods=SharedPreUtil.getLocalShopCarData();
                    for(int l=0;l<loadGoods.size();l++){
                        initNewnetData(sessionData, loadGoods.get(l));
                    }

                }else{
                    initData(sessionData);
                }
            }else{

                        initRecycleViewVertical();
                        if(SharedPreUtil.getLocalShopCarData().size()>0){
                            progressLinear.setVisibility(View.GONE);

                            mRecyclerView.setVisibility(View.VISIBLE);
                            networkInfo.setVisibility(View.GONE);
                            carLinear.setVisibility(View.GONE);

                            orderListList.clear();
                            orderListList.addAll(SharedPreUtil.getLocalShopCarData());




                            adapter.notifyDataSetChanged();
                            xuanfuBar.setVisibility(View.VISIBLE);
                            tvRightTitle.setVisibility(View.VISIBLE);


                            setAllSelect();//设置全选




                        }else{
                            progressLinear.setVisibility(View.GONE);
                            mRecyclerView.setVisibility(View.GONE);
                            networkInfo.setVisibility(View.GONE);
                            carLinear.setVisibility(View.VISIBLE);

                            xuanfuBar.setVisibility(View.GONE);
                            tvRightTitle.setVisibility(View.INVISIBLE);

                        }


               /**/
            }


        } else {
            errorInfo.setImageDrawable(getResources().getDrawable(R.mipmap.error_nowifi));
            mRecyclerView.setVisibility(View.GONE);
            networkInfo.setVisibility(View.VISIBLE);

        }
    }


    List<ShopCarOrderInfo.DataEntity.GoodsListEntity> orderListList = new ArrayList<ShopCarOrderInfo.DataEntity.GoodsListEntity>();//
    public int page = 1;
    int loadingTag = 2;//刷新flag   2 默认   1 下拉刷新  -1是上拉更多

    private void initData(SessionData sessionDataOne) {

        String url = "http://mapp.aiderizhi.com/?url=/cart/list";//

        Map<String, String> map = new HashMap<String, String>();


        if (loadingTag == -1) {
            map = new HashMap<String, String>();
            PaginationJson paginationJson = new PaginationJson();
            paginationJson.setCount("10");
            paginationJson.setPage((++page) + "");
            String string = JSON.toJSONString(paginationJson);
            String d = "{\"pagination\":" + string + " ,\"type\":\"shipped\",\"session\":{\"uid\":\"" + sessionDataOne.getUid() + "\",\"sid\":\"" + sessionDataOne.getSid() + "\"}}";//type all为所有的订单
            map.put("json", d);
            Log.d("ShopCarFragment", d + "》》》》");
        }
        if (loadingTag == 2) {//第一次加载数据
            map = new HashMap<String, String>();
            String oneString = "{\"session\":{\"uid\":\"" + sessionDataOne.getUid() + "\",\"sid\":\"" + sessionDataOne.getSid() + "\"}}";
            map.put("json", oneString);
            Log.d("ShopCarFragment", oneString + "》》》》");
        }


        RequestQueue mQueue = AppContextApplication.getInstance().getmRequestQueue();
        FastJsonRequest<ShopCarOrderInfo> fastJsonCommunity = new FastJsonRequest<ShopCarOrderInfo>(Request.Method.POST, url, ShopCarOrderInfo.class, null, new Response.Listener<ShopCarOrderInfo>() {
            @Override
            public void onResponse(ShopCarOrderInfo myOrderInfo) {

                ShopCarOrderInfo.StatusEntity status = myOrderInfo.getStatus();
                if (status.getSucceed() == 1) {

                    progressLinear.setVisibility(View.GONE);


                    if (loadingTag == -1) {


                        List<ShopCarOrderInfo.DataEntity.GoodsListEntity> p = myOrderInfo.getData().getGoods_list();
                        Log.d("ShopCarFragment", "" + orderListList.size() + "1111++++orderListList");
                        for (int i = 0; i < p.size(); i++) {
                            orderListList.add(p.get(i));
                        }
                        Log.d("ShopCarFragment", "" + orderListList.size() + "2222++++orderListList");


                        loadingTag = 2;

                        mRecyclerView.loadMoreComplete();
                    }
                    if (loadingTag == 2) {



                        orderListList.clear();
                        orderListList.addAll(myOrderInfo.getData().getGoods_list());

                        if (orderListList != null && orderListList.size() > 0) {






                            adapter.notifyDataSetChanged();
                            xuanfuBar.setVisibility(View.VISIBLE);
                            tvRightTitle.setVisibility(View.VISIBLE);


                            setAllSelect();//设置全选



                        } else {
                            progressLinear.setVisibility(View.GONE);
                            mRecyclerView.setVisibility(View.GONE);
                            networkInfo.setVisibility(View.GONE);
                            carLinear.setVisibility(View.VISIBLE);

                            xuanfuBar.setVisibility(View.GONE);
                            tvRightTitle.setVisibility(View.INVISIBLE);

                        }



                        mRecyclerView.refreshComplete();
                    }


//                    Log.d("ShopCarFragment", "" + status.getSucceed() + "++++succeed》》》》" + promotePostDateList.get(0).getCat_name());
                } else {
                    // 请求失败
                    progressLinear.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.GONE);
                    errorInfo.setImageDrawable(getResources().getDrawable(R.mipmap.error_nodata));
                    networkInfo.setVisibility(View.VISIBLE);
                    xuanfuBar.setVisibility(View.GONE);
                    tvRightTitle.setVisibility(View.INVISIBLE);
                    // 请求失败

                    Toast.makeText(mContext, status.getError_desc(), Toast.LENGTH_SHORT).show();
                    Log.d("ShopCarFragment", "" + status.getSucceed() + "++++success=0》》》》");


                    if (status.getError_code() == 1000) {
                        SharedPreferences.getInstance().putBoolean("islogin", false);
                        ViewUtill.ShowAlertDialog(mContext);
                    }

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                //未知错误
                progressLinear.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.GONE);
                errorInfo.setImageDrawable(getResources().getDrawable(R.mipmap.error_default));
                networkInfo.setVisibility(View.VISIBLE);
                xuanfuBar.setVisibility(View.GONE);
                tvRightTitle.setVisibility(View.INVISIBLE);

                Log.d("ShopCarFragment", "errror" + volleyError.toString() + "++++》》》》");
            }
        });

        fastJsonCommunity.setParams(map);

        mQueue.add(fastJsonCommunity);


    }

    private void setAllSelect() {


        adapter.myNotifiAdapter(true, false, true);
        isQuanxuna=false;
        isImage.setBackgroundResource(R.mipmap.choiceon);



        tvRightTitle.setText("编辑");
        idBianji=true;
//            isQuanxuna=true;
//            isImage.setBackgroundResource(R.mipmap.choice);
        isdeleteOrJiSuan=false;
        buyNow.setText("去结算");
        hejiTop.setVisibility(View.VISIBLE);
        hejiText.setVisibility(View.VISIBLE);



        jishuZongPrice();







    }

    String hejinMoney;
    private void jishuZongPrice() {

        double jinge=0.00d;
        List<ShopCarOrderInfo.DataEntity.GoodsListEntity> temGoodsList = adapter.getOrderLists();

        if(temGoodsList!=null&&temGoodsList.size()>0){
            for(int j=0;j<temGoodsList.size();j++){

                if(temGoodsList.get(j).is_all_select()){
                    int num = Integer.parseInt(temGoodsList.get(j).getGoods_number());
                    double price =Double.parseDouble(temGoodsList.get(j).getGoods_price().replace("¥", ""));
                    jinge+=price*num;
                }

            }
            BigDecimal   bd   =   new   BigDecimal(jinge);
            bd   =   bd.setScale(2, BigDecimal.ROUND_HALF_UP);
            hejinMoney="¥" + bd;
            hejiText.setText("¥" + bd + "元");
        }else{
            hejiText.setText("¥0.00元");
        }

    }


    /**
     * 加入购物车
     * @param sessionDataOne
     */
    private void initNewnetData(SessionData sessionDataOne,ShopCarOrderInfo.DataEntity.GoodsListEntity netGoodsList) {

        String url = "http://mapp.aiderizhi.com/?url=/cart/create";//

        Map<String, String> map = new HashMap<String, String>();



        map = new HashMap<String, String>();
        String oneString = "{\"id\":\""+netGoodsList.getGoods_id()+"\",\"number\":\""+netGoodsList.getGoods_number()+"\",\"spec\":\"\",\"session\":{\"uid\":\"" + sessionDataOne.getUid() + "\",\"sid\":\"" + sessionDataOne.getSid() + "\"}}";
        map.put("json", oneString);
        Log.d("ShopCarFragment", oneString + "》》》》");



        RequestQueue mQueue = AppContextApplication.getInstance().getmRequestQueue();
        FastJsonRequest<ShopCarCreate> fastJsonCommunity = new FastJsonRequest<ShopCarCreate>(Request.Method.POST, url, ShopCarCreate.class, null, new Response.Listener<ShopCarCreate>() {
            @Override
            public void onResponse(ShopCarCreate shopCarCreate) {

                ShopCarCreate.StatusEntity status = shopCarCreate.getStatus();
                if (status.getSucceed() == 1) {

                    ++numToshop;

                    if(numToshop==loadGoods.size()){
                        initData(sessionData);
                        SharedPreferences.getInstance().putString("local_shop_car", "");
                        numToshop=0;
                        //广播通知刷新购物车数量
                        Intent intent = new Intent();
                        intent.setAction("UpShopCarNum");
                        intent.putExtra("update", "ok");
                        mContext.sendBroadcast(intent);

                    }

                    Log.d("ShopCarFragment", "" + status.getSucceed() + "++++succeed》》》》" );
                } else {

                    Log.d("ShopCarFragment", "" + status.getSucceed() + "++++shibai》》》》" );
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                //未知错误

                Log.d("ShopCarFragment", "errror" + volleyError.toString() + "++++》》》》");
            }
        });

        fastJsonCommunity.setParams(map);

        mQueue.add(fastJsonCommunity);


    }





    // 创建Adapter，并指定数据集
    RecycleShopCarAdapter adapter;
    boolean  idBianji=true;//是否编辑
    boolean  isQuanxuna=true;//是否全选

    LinearItemDecoration  linearItemDecoration=null;

    public void initRecycleViewVertical() {


        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setArrowImageView(R.mipmap.iconfont_downgrey);


        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {


                    if(isQuanxuna==false||idBianji==false){
                        Toast.makeText(mContext,"编辑中不可刷新",Toast.LENGTH_SHORT).show();
                        mRecyclerView.refreshComplete();
                    }else{




                        if(SharedPreUtil.isLogin()){
                            loadingTag = 2;//重新加载
                            page = 1;
                             initData(sessionData);
                        }else{
                            newWait();
                           //为登录下刷新
                            new Handler().postDelayed(new Runnable() {
                                public void run() {

                                    mRecyclerView.refreshComplete();


                                }

                            }, 500);            //refresh data here
                        }




                   }



            }

            @Override
            public void onLoadMore() {

//                new Handler().postDelayed(new Runnable() {
//                    public void run() {

//                        mRecyclerView.loadMoreComplete();
//                    }
//                }, 2000);

                if(SharedPreUtil.isLogin()){
                    loadingTag = -1;
                    Log.d("ShopCarFragment", "initial    more");
                    initData(sessionData);
                }else{
                    //为登录下刷新
                    new Handler().postDelayed(new Runnable() {
                        public void run() {

                            mRecyclerView.loadMoreComplete();


                        }

                    }, 1000);            //refresh data here
                }

            }
        });


        if (orderListList != null) {
            adapter = new RecycleShopCarAdapter(orderListList,mContext);
            adapter.setOnCheckDefaultListener(this);

            mRecyclerView.setAdapter(adapter);

            if(linearItemDecoration==null){
                linearItemDecoration=new LinearItemDecoration(Color.parseColor("#f2f2f2"));
                mRecyclerView.addItemDecoration(linearItemDecoration);
            }



        }else{
            xuanfuBar.setVisibility(View.GONE);
            tvRightTitle.setVisibility(View.INVISIBLE);
        }

        newLoading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean isLogin = SharedPreferences.getInstance().getBoolean("islogin", false);
                if (!isLogin) {
                    new AlertDialog(mContext).builder().setTitle("提示")
                            .setMsg("您未登录，请登录")
                            .setPositiveButton("确认", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

//                                    isLoginTag=true;
                                    //登录
                                    Intent intent = new Intent(mContext, LoginActivity.class);
//                                      Bundle bundle = new Bundle();
//                                    bundle.putSerializable("PromotePostsData", (Serializable) pp);
//                                    intent.putExtras(bundle);
                                    mContext.startActivity(intent);

                                }
                            }).setNegativeButton("取消", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onShopCarLonginListener.onBackShopCarOK(false);
                        }
                    }).show();
                } else {

                    isLogiin(false);

                }


            }
        });


    }

    @Override
    public void oncheckOK(Boolean[] ischeckArray) {

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onAllselectToCanter(boolean isquxiaoQuxian) {
          if(isquxiaoQuxian) {



              isImage.setBackgroundResource(R.mipmap.choice);
              isQuanxuna = true;
          }else{
              isImage.setBackgroundResource(R.mipmap.choiceon);
              isQuanxuna = false;
          }




     }

    @Override
    public void onJisuanZongPrice() {
        jishuZongPrice();

    }

    @Override
    public void onDeleteAll() {
        newWait();
    }


}
