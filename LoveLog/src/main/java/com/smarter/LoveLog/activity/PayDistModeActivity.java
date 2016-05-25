package com.smarter.LoveLog.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.smarter.LoveLog.R;
import com.smarter.LoveLog.adapter.RecyclePayDistModeAdapter;
import com.smarter.LoveLog.model.orderMy.OrderFlowCheckOut;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/5/20.
 */
public class PayDistModeActivity extends BaseBestActivity {
    List<OrderFlowCheckOut.DataEntity.ShippingListEntity> shipping_list = new ArrayList<OrderFlowCheckOut.DataEntity.ShippingListEntity>();
    OrderFlowCheckOut.DataEntity dataEntity = new OrderFlowCheckOut.DataEntity();
    RecyclePayDistModeAdapter mAdapter;



    @Override
    public void dosetPromo() {

        addViewLay();//添加布局

        Intent intent = getIntent();
        if (intent != null) {

            dataEntity = (OrderFlowCheckOut.DataEntity) intent.getSerializableExtra("shipping_list");

        }


    }

    View view;

    private void addViewLay() {
        view = LayoutInflater.from(mContext).inflate(R.layout.item_layout_quedin, null);


        Ba________addViewXuanFuLinear(view);
    }

    @Override
    public void initDataAbs() {
        centerTitle.setText("选择支付配送方式");

        shipping_list = dataEntity.getShipping_list();


        new Handler().postDelayed(new Runnable() {
            public void run() {
                Ba________progresNetworkGone();
                intData();//初始界面
            }

        }, 0);            //refresh data here


    }


    public int page = 1;
    int loadingTag = 2;//刷新flag   2 默认   1 下拉刷新  -1是上拉更多


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
//                initData(proId);
                Ba________recycyeNetworkCompetle();
            }

            @Override
            public void onLoadMore() {
                Ba________recycyeNetworkCompetleMore();
//                new Handler().postDelayed(new Runnable() {
//                    public void run() {
                loadingTag = -1;
//                initData(proId);

//                    }
//                }, 2000);

            }
        });


        if (shipping_list != null && shipping_list.size() > 0) {
            mAdapter = new RecyclePayDistModeAdapter(dataEntity, mContext);
            mRecyclerView.setAdapter(mAdapter);
        }

    }


    public void MyRecycleNotifi() {
        mAdapter.notifyDataSetChanged();
        Ba________recycyeNetworkCompetle();
    }




    @OnClick(R.id.quedingBut)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.quedingBut:
                //回传给MakeOutOrderActivity


                Intent aintent = new Intent(this, MakeOutOrderActivity.class);
                aintent.putExtra("pay_type",mAdapter.getPaymentTypeEntity());
                aintent.putExtra("shipping_id",mAdapter.getShippingListEntity());
                aintent.putExtra("ps_data_entity",mAdapter.getDataEntityAdpter());
                this.setResult(RESULT_OK, aintent);


                finish();

                break;

        }
    }
}
