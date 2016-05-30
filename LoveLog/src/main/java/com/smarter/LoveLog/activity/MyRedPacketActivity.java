package com.smarter.LoveLog.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.smarter.LoveLog.R;
import com.smarter.LoveLog.fragment.RedpacketHaveExpiredFragment;
import com.smarter.LoveLog.fragment.RedpacketUnusedFragment;
import com.smarter.LoveLog.fragment.RedpacketUsedFragment;
import com.smarter.LoveLog.model.orderMy.OrderFlowCheckOut;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2015/11/30.
 */
public class MyRedPacketActivity extends BaseFragmentActivity implements OnTabSelectListener {
    String Tag = "MyRedPacketActivity";
    @Bind(R.id.tl_2)
    SlidingTabLayout tabLayout_2;
    @Bind(R.id.view_pager)
    ViewPager vp;


    @Bind(R.id.back_but)
    ImageView back_but;
    @Bind(R.id.tv_right_title)
    TextView tvRightTitle;

    private List<Fragment> list_fragment;                                //定义要装fragment的列表
    private List<String> list_title;                                     //tab名称列表

    private RedpacketUnusedFragment redpacketUnusedFragment;                          //未使用fragment
    private RedpacketUsedFragment redpacketUsedFragment;
    private RedpacketHaveExpiredFragment redpacketHaveExpiredFragment;
    Activity mActivity;
    Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_red_packet_view);
        ButterKnife.bind(this);
        mActivity = this;
        mContext = this;

        getDataIntent();
        intData();

    }


    private void intData() {

        if(isOrdeSelectRed){
            tvRightTitle.setVisibility(View.VISIBLE);
        }

                //fragment List
                redpacketUnusedFragment = new RedpacketUnusedFragment(this,isOrdeSelectRed,UseRedId,dataEntityRed);
        redpacketUsedFragment = new RedpacketUsedFragment();
        redpacketHaveExpiredFragment = new RedpacketHaveExpiredFragment();

        list_fragment = new ArrayList<Fragment>();
        list_fragment.add(redpacketUnusedFragment);

        list_fragment.add(redpacketUsedFragment);
        list_fragment.add(redpacketHaveExpiredFragment);

        //tab title List
        list_title = new ArrayList<String>();
        list_title.add("未使用");
        list_title.add("已使用");
        list_title.add("已过期");


        vp.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        tabLayout_2.setViewPager(vp);
        tabLayout_2.setOnTabSelectListener(this);
//        tabLayout_2.showDot(0);
        vp.setCurrentItem(0);
//        tabLayout_2.showMsg(1, 5);
//        tabLayout_2.setMsgMargin(1, 12.0f, 10.0f);


    }

    @Override
    public void onTabSelect(int position) {
//        Toast.makeText(mContext, "onTabSelect&position--->" + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTabReselect(int position) {
//        Toast.makeText(mContext, "onTabReselect&position--->" + position, Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.back_but, R.id.tv_right_title})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_but:
                finish();
                break;
            case R.id.tv_right_title:
                String  redId =redpacketUnusedFragment.getSelectRedId();
                String  redMoney=redpacketUnusedFragment.getSelectRedMoney();
                //回传给MakeOutOrderActivity
                   Intent aintent = new Intent(mActivity, MakeOutOrderActivity.class);
                    aintent.putExtra("red_id", redId);
                    aintent.putExtra("red_money",redMoney);
                    setResult(mActivity.RESULT_OK, aintent);
                   finish();

                break;
        }
    }


    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return list_fragment.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return list_title.get(position);
        }

        @Override
        public Fragment getItem(int position) {
            return list_fragment.get(position);
        }
    }


    boolean isOrdeSelectRed;//是否是红包选择界面
    String UseRedId;//已使用红包id
    OrderFlowCheckOut.DataEntity  dataEntityRed;//包含可用红包list


    private void getDataIntent() {
        Intent intent = getIntent();
        if (intent != null) {
//            String  str = intent.getStringExtra("ObjectData");
            // Toast.makeText(this,str+"",Toast.LENGTH_LONG).show();

            isOrdeSelectRed = intent.getBooleanExtra("isOrdeSelectRed", false);
           UseRedId= intent.getStringExtra("isUseRedPacketValue");
            dataEntityRed= (OrderFlowCheckOut.DataEntity) intent.getSerializableExtra("bonusList");
        }


    }


}
