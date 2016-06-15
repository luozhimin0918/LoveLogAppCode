package com.smarter.LoveLog.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.smarter.LoveLog.R;
import com.smarter.LoveLog.adapter.RecycleAddressAdapter;
import com.smarter.LoveLog.db.SharedPreUtil;
import com.smarter.LoveLog.db.SharedPreferences;
import com.smarter.LoveLog.http.FastJsonRequest;
import com.smarter.LoveLog.model.address.AddressData;
import com.smarter.LoveLog.model.address.AddressDataInfo;
import com.smarter.LoveLog.model.home.DataStatus;
import com.smarter.LoveLog.model.loginData.SessionData;
import com.smarter.LoveLog.utills.ViewUtill;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/11/30.
 */
public class AddressManageActivity extends BaseFragmentActivity implements View.OnClickListener, RecycleAddressAdapter.OnCheckDefaultListener {
    String Tag = "AddressManageActivity";
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.addAddressTop)
    TextView addAddressTop;
    @Bind(R.id.backButon)
    ImageView backButon;

    Activity mActivity;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_manage_view);
        ButterKnife.bind(this);
        mActivity = this;


        getDataIntent();
        setListen();

    }

    @Override
    protected void onResume() {
        intData();

        super.onResume();
    }

    private void setListen() {
        addAddressTop.setOnClickListener(this);
        backButon.setOnClickListener(this);
    }

    SessionData sessionData;

    private void intData() {


        if (SharedPreUtil.isLogin()) {
            sessionData = SharedPreUtil.LoginSessionData();

            if (sessionData != null) {

                networkAddreessInfo(sessionData.getUid(), sessionData.getSid());
                Log.d("AddressManageActivity", "  Session  " + sessionData.getUid() + "      " + sessionData.getSid());
            }

        }

    }


    RecycleAddressAdapter adapter;

    public void initRecycleViewVertical() {

        // 创建一个线性布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        // 默认是Vertical，可以不写
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        // 设置布局管理器
        recyclerView.setLayoutManager(layoutManager);


        // 创建Adapter，并指定数据集
        adapter = new RecycleAddressAdapter(addressDataList, this,isSelectAddress,makeout_addrId);
        adapter.setOnCheckDefaultListener(this);
        // 设置Adapter
        recyclerView.setAdapter(adapter);





    }

    boolean isSelectAddress;
    String makeout_addrId;

    private void getDataIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            String str = intent.getStringExtra("ObjectData");
            isSelectAddress = intent.getBooleanExtra("isSelectAddress", false);
            makeout_addrId=intent.getStringExtra("makeout_addrId");
            // Toast.makeText(this,str+"",Toast.LENGTH_LONG).show();
        }




    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addAddressTop:
                //挑战到创建收货地址
                // 界面
                Intent intent2 = new Intent(this, CreateAddressActivity.class);
                Bundle bundle = new Bundle();
                bundle.putBoolean("isCreateAddress", true);
                intent2.putExtras(bundle);
                this.startActivity(intent2);
                break;
            case R.id.backButon:
                setResultTo();
//                finish();
                break;

        }
    }


    @Override
    public void oncheckOK(List<AddressData> addressDataLis) {
        intData();
    }

    String addrIdActivity="null";
    boolean  isDefaultOrSelect;
    @Override
    public void onClickAddressItem(String addrId, Boolean isDefaultOrSelect) {
        if (isSelectAddress) {
           this.addrIdActivity=addrId;
            this.isDefaultOrSelect=isDefaultOrSelect;

            if(!addrIdActivity.equals("null")){

                if(!isDefaultOrSelect){
                    //回传给MakeOutOrderActivity
                    Intent aintent = new Intent(AddressManageActivity.this, MakeOutOrderActivity.class);
                    aintent.putExtra("consignee", addrIdActivity);
                    mActivity.setResult(RESULT_OK, aintent);


                    finish();
                }

            }

        }
    }


    @Override
    public void onBackPressed() {
//        super.onBackPressed();
       setResultTo();
    }


    /**
     * 设置默认而改变默认标记
     */
    private void setResultTo() {
        if(isDefaultOrSelect&&isSelectAddress){
            if(!addrIdActivity.equals("null")){
                //回传给MakeOutOrderActivity
                Intent aintent = new Intent(AddressManageActivity.this, MakeOutOrderActivity.class);
                aintent.putExtra("consignee", addrIdActivity);
                aintent.putExtra("isDefaultOrSelect",isDefaultOrSelect);
                mActivity.setResult(RESULT_OK, aintent);

                finish();
            }
        }else{
            finish();
        }
    }


    List<AddressData> addressDataList;

    /**
     * 获取所有地址数据，其实只有最多五个
     *
     * @param uid
     * @param sid
     */
    private void networkAddreessInfo(String uid, String sid) {
        String url = "http://mapp.aiderizhi.com/?url=/address/list";//
        Map<String, String> mapTou = new HashMap<String, String>();
        String sessinStr = "{\"session\":{\"uid\":\"" + uid + "\",\"sid\":\"" + sid + "\"}}";
        mapTou.put("json", sessinStr);


        Log.d("AddressManageActivity", sessinStr + "      ");


        FastJsonRequest<AddressDataInfo> fastJsonCommunity = new FastJsonRequest<AddressDataInfo>(Request.Method.POST, url, AddressDataInfo.class, null, new Response.Listener<AddressDataInfo>() {
            @Override
            public void onResponse(AddressDataInfo addressDataInfo) {

                DataStatus status = addressDataInfo.getStatus();
                if (status.getSucceed() == 1) {
                    addressDataList = addressDataInfo.getData();
                    if (addressDataList != null && addressDataList.size() > 0) {
                        SharedPreferences.getInstance().putString("address-list", JSON.toJSONString(addressDataList));
                        initRecycleViewVertical();//ok
                        Log.d("AddressManageActivity", "地址list信息：   " + JSON.toJSONString(addressDataList) + "++++succeed");
                    }


                } else {

                    // 请求失败
                    Log.d("AddressManageActivity", "succeded=00000  " + JSON.toJSONString(status) + "");
                    Toast.makeText(getApplicationContext(), "" + status.getError_desc(), Toast.LENGTH_SHORT).show();


                    if (status.getError_code() == 1000) {
                        SharedPreferences.getInstance().putBoolean("islogin", false);
                        ViewUtill.ShowAlertDialog(mActivity);
                    }

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("AddressManageActivity", "errror" + volleyError.toString() + "");
            }
        });
        fastJsonCommunity.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //fastJsonCommunity.setTag(TAG);
        fastJsonCommunity.setParams(mapTou);
        fastJsonCommunity.setShouldCache(true);
        mQueue.add(fastJsonCommunity);
    }


}
