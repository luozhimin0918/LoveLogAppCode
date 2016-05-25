package com.smarter.LoveLog.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.smarter.LoveLog.R;
import com.smarter.LoveLog.adapter.RecyclePinglunAdapter;
import com.smarter.LoveLog.db.SharedPreUtil;
import com.smarter.LoveLog.db.SharedPreferences;
import com.smarter.LoveLog.http.FastJsonRequest;
import com.smarter.LoveLog.model.PaginationJson;
import com.smarter.LoveLog.model.community.InvitationDataPinglunActi;
import com.smarter.LoveLog.model.goods.CmtGoods;
import com.smarter.LoveLog.model.home.DataStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/5/20.
 */
public class ModeBaseTestActivity extends BaseBestActivity {

    String proId;
    RecyclePinglunAdapter mAdapter;
    @Bind(R.id.btn_biaoqing_linear)
    LinearLayout btnBiaoqingLinear;
    @Bind(R.id.et_sendmessage)
    EditText etSendmessage;


    @Override
    public void dosetPromo() {

        addViewLay();//添加布局

        Intent intent = getIntent();
        if (intent != null) {

            proId = "22765";


        }



    }

    View view;
    private void addViewLay() {
        view = LayoutInflater.from(mContext).inflate(R.layout.custom_facerelativelayout, null);

        Ba________addViewToallLinear(view);
    }

    @Override
    public void initDataAbs() {
        etSendmessage.setText("我成功了，哈哈哈");
        centerTitle.setText("选择支付配送方式");
        initData(proId);
    }


    List<CmtGoods> promotePostDateList = new ArrayList<CmtGoods>();//本类帖子 分类里所有数据
    public int page = 1;
    int loadingTag = 2;//刷新flag   2 默认   1 下拉刷新  -1是上拉更多

    private void initData(final String id) {
        String url = "http://mapp.aiderizhi.com/?url=/comment/list";//

        Map<String, String> map = new HashMap<String, String>();


        String sessStr = "";
        if (SharedPreUtil.isLogin()) {
            String sessionString = SharedPreferences.getInstance().getString("session", "");
            if (sessionString != null && !sessionString.equals("")) {
                sessStr = ",\"session\":" + sessionString;
            }
        }

        if (loadingTag == -1) {
            map = new HashMap<String, String>();
            PaginationJson paginationJson = new PaginationJson();
            paginationJson.setCount("10");
            paginationJson.setPage((++page) + "");
            String string = JSON.toJSONString(paginationJson);
            String d = "{\"id\":\"" + id + "\",\"pagination\":" + string + " ,\"type\":\"2\"" + "}";
            map.put("json", d);
            Log.d("payDistMode", d + "》》》》");
        }
        if (loadingTag == 2 || loadingTag == 1) {//第一次加载数据
            map = new HashMap<String, String>();
            String oneString = "{\"type\":\"2\",\"id\":\"" + id + "\"" + "}";
            map.put("json", oneString);
            Log.d("payDistMode", oneString + "》》》》");
        }


        FastJsonRequest<InvitationDataPinglunActi> fastJsonCommunity = new FastJsonRequest<InvitationDataPinglunActi>(Request.Method.POST, url, InvitationDataPinglunActi.class, null, new Response.Listener<InvitationDataPinglunActi>() {
            @Override
            public void onResponse(InvitationDataPinglunActi pinglunActi) {

                DataStatus status = pinglunActi.getStatus();
                if (status.getSucceed() == 1) {


                    Ba________progresNetworkGone();


                    if (loadingTag == -1) {


                        List<CmtGoods> p = pinglunActi.getData();
                        Log.d("payDistMode", "" + promotePostDateList.size() + "1111++++promotePostDateList");
                        for (int i = 0; i < p.size(); i++) {
                            promotePostDateList.add(p.get(i));
                        }
                        Log.d("payDistMode", "" + promotePostDateList.size() + "2222++++promotePostDateList");


                        Ba________recycyeNetworkCompetleMore();
                    }


                    if (loadingTag == 1 || loadingTag == 2) {
                        promotePostDateList.clear();
                        promotePostDateList.addAll(pinglunActi.getData());

                        if (promotePostDateList != null && promotePostDateList.size() > 0) {

                            if (loadingTag == 2) {
                                intData();//初始界面
                            }
                            if (loadingTag == 1) {
                                MyRecycleNotifi();
                            }


                            Log.d("payDistMode", "2lslsls");
                        } else {
                            Ba________nullNetworkData("暂无评论");
                        }

                    }


                } else {


                    Ba________shibaiNetworkData();
                    // 请求失败
                    Log.d("payDistMode", "" + status.getSucceed() + "++++success=0》》》》");

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {


                Ba________weizhiErrorNetworkData();
                Log.d("payDistMode", "errror" + volleyError.toString() + "++++》》》》");
            }
        });

        fastJsonCommunity.setParams(map);

        mQueue.add(fastJsonCommunity);

    }

    private void intData() {


        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setArrowImageView(R.mipmap.iconfont_downgrey);


        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                loadingTag = 1;//重新加载
                page = 1;
                initData(proId);
//                new Handler().postDelayed(new Runnable() {
//                    public void run() {
//
//                        mRecyclerView.refreshComplete();
//
//
//                    }
//
//                }, 1000);            //refresh data here
            }

            @Override
            public void onLoadMore() {

//                new Handler().postDelayed(new Runnable() {
//                    public void run() {
                loadingTag = -1;
                Log.d("BaseBestActivityURL", "initial    more");
                initData(proId);
//                        mRecyclerView.loadMoreComplete();
//                    }
//                }, 2000);

            }
        });


        if (promotePostDateList != null && promotePostDateList.size() > 0) {
            mAdapter = new RecyclePinglunAdapter(promotePostDateList, mContext);

            mRecyclerView.setAdapter(mAdapter);
        }

    }


    public void MyRecycleNotifi() {
        mAdapter.notifyDataSetChanged();
        Ba________recycyeNetworkCompetle();
    }



}
