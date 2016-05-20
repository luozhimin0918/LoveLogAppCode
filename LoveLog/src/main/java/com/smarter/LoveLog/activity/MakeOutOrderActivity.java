package com.smarter.LoveLog.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.smarter.LoveLog.R;
import com.smarter.LoveLog.adapter.RecycleMakeoutOrderAdapter;
import com.smarter.LoveLog.db.SharedPreUtil;
import com.smarter.LoveLog.http.FastJsonRequest;
import com.smarter.LoveLog.model.loginData.SessionData;
import com.smarter.LoveLog.model.orderMy.OrderFlowCheckOut;
import com.smarter.LoveLog.model.orderMy.ShopCarOrderInfo;
import com.smarter.LoveLog.ui.SyLinearLayoutManager;
import com.smarter.LoveLog.utills.ViewUtill;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2015/11/30.
 */
public class MakeOutOrderActivity extends BaseFragmentActivity implements View.OnClickListener, RecycleMakeoutOrderAdapter.OnItemClickListener {
    String Tag = "MakeOutOrderActivity";

    @Bind(R.id.backBUt)
    ImageView backBUt;
    @Bind(R.id.tv_top_title)
    TextView tvTopTitle;
    @Bind(R.id.tv_right_title)
    TextView tvRightTitle;
    @Bind(R.id.addressStr)
    TextView addressStr;
    @Bind(R.id.addressRelatiBut)
    LinearLayout addressRelatiBut;

    @Bind(R.id.buy_now)
    TextView buyNow;
    @Bind(R.id.xuanfuBar)
    LinearLayout xuanfuBar;
    @Bind(R.id.consignee)
    TextView consignee;
    @Bind(R.id.mobile)
    TextView mobile;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.addressReceiptLiner)
    LinearLayout addressReceiptLiner;
    @Bind(R.id.addressCreatAddLine)
    LinearLayout addressCreatAddLine;
    @Bind(R.id.isDefault)
    TextView isDefault;
    @Bind(R.id.dingdanConnect)
    LinearLayout dingdanConnect;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_out_order_data_view);
        ButterKnife.bind(this);

        dingdanConnect.setVisibility(View.GONE);
        xuanfuBar.setVisibility(View.GONE);

        getDataIntent();
    }

    @Override
    protected void onResume() {
        super.onResume();



    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void intData() {

        if (SharedPreUtil.isLogin()) {

            if (sessionData != null) {

                networkFlowCheckout(sessionData.getUid(), sessionData.getSid());

                Log.d("MakeOutOrderActivity", "  Session  " + sessionData.getUid() + "      " + sessionData.getSid());
            }

        } else {
            ViewUtill.ShowAlertDialog(this);
//            Toast.makeText(getApplicationContext(), "未登录，请先登录", Toast.LENGTH_SHORT).show();
        }


    }


    SessionData sessionData;

    private void getDataIntent() {
        Intent intent = getIntent();
        if (intent != null) {



            if(SharedPreUtil.isLogin()){
                sessionData=SharedPreUtil.LoginSessionData();
            }
            // Toast.makeText(this,str+"",Toast.LENGTH_LONG).show();
            if (sessionData != null) {
                intData();

            }
        }


    }

    OrderFlowCheckOut.DataEntity.ConsigneeEntity consigneeEntity = new OrderFlowCheckOut.DataEntity.ConsigneeEntity();
    List<OrderFlowCheckOut.DataEntity.GoodsListEntity> goodsListEntityList;

    public void initRecycleViewVertical() {


        initAddressInfo(dataEntity);//初始化收货地址信息


        goodsListEntityList = dataEntity.getGoods_list();
        // 创建一个线性布局管理器
        SyLinearLayoutManager layoutManager = new SyLinearLayoutManager(this);
        layoutManager.setOrientation(SyLinearLayoutManager.VERTICAL);


        // 设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
        // 创建Adapter，并指定数据集
        RecycleMakeoutOrderAdapter adapter = new RecycleMakeoutOrderAdapter(goodsListEntityList, mQueue);
        // 设置Adapter
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);


    }

    private void initAddressInfo(OrderFlowCheckOut.DataEntity data) {

        consigneeEntity = data.getConsignee();


        if (consigneeEntity == null) {
            addressCreatAddLine.setVisibility(View.VISIBLE);
            addressReceiptLiner.setVisibility(View.GONE);
        } else {
            addressCreatAddLine.setVisibility(View.GONE);
            addressReceiptLiner.setVisibility(View.VISIBLE);
            consignee.setText(consigneeEntity.getConsignee());//收货人
            mobile.setText(consigneeEntity.getMobile());//手机号
            if (consigneeEntity.getIs_default() == 1) {
                isDefault.setVisibility(View.VISIBLE);
            } else {
                isDefault.setVisibility(View.GONE);
            }

            addressStr.setText(consigneeEntity.getAddress());//收货地址
        }

    }


    /**
     * 获取订单数据
     */
    OrderFlowCheckOut.DataEntity dataEntity;

    private void networkFlowCheckout(String uid, String sid) {
        String url = "http://mapp.aiderizhi.com/?url=/flow/checkout";//
        Map<String, String> mapTou = new HashMap<String, String>();
        String sessinStr = "{\"session\":{\"uid\":\"" + uid + "\",\"sid\":\"" + sid + "\"}}";
        mapTou.put("json", sessinStr);


        Log.d("MakeOutOrderActivity", sessinStr + "      ");


        FastJsonRequest<OrderFlowCheckOut> fastJsonCommunity = new FastJsonRequest<OrderFlowCheckOut>(Request.Method.POST, url, OrderFlowCheckOut.class, null, new Response.Listener<OrderFlowCheckOut>() {
            @Override
            public void onResponse(OrderFlowCheckOut orderFlowCheckOut) {

                OrderFlowCheckOut.StatusEntity status = orderFlowCheckOut.getStatus();
                if (status.getSucceed() == 1) {
                    dataEntity = orderFlowCheckOut.getData();
                    if (dataEntity != null) {
                        dingdanConnect.setVisibility(View.VISIBLE);
                        xuanfuBar.setVisibility(View.VISIBLE);
                        initRecycleViewVertical();
                        Log.d("MakeOutOrderActivity", "flow/checkout：   " + "++++succeed");
                    }


                } else {

                    // 请求失败
                    Log.d("MakeOutOrderActivity", "succeded=00000  " + JSON.toJSONString(status) + "");
                    Toast.makeText(getApplicationContext(), "" + status.getError_desc(), Toast.LENGTH_SHORT).show();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("MakeOutOrderActivity", "errror" + volleyError.toString() + "");
            }
        });

        fastJsonCommunity.setParams(mapTou);
        mQueue.add(fastJsonCommunity);
    }

    private static final int AddManage = 0;
    private static final int CreatAdd = 1;

    @OnClick({R.id.backBUt, R.id.buy_now, R.id.addressRelatiBut})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backBUt:
                finish();
                break;
            case R.id.buy_now:
                Intent intent = new Intent(this, PayMoneyActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("goods",goodsData);
//                intent.putExtras(bundle);
                this.startActivity(intent);
                break;
            case R.id.addressRelatiBut:

                if (consigneeEntity == null) {
                    Intent intent2 = new Intent(this, CreateAddressActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("isCreateAddress", true);
                    bundle.putBoolean("isOnreateAddress", true);
                    intent2.putExtras(bundle);
                    startActivityForResult(intent2, CreatAdd);
//                    this.startActivity(intent2);
                } else {
                    //挑战到地址管理界面
                    Intent intent2 = new Intent(this, AddressManageActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("isSelectAddress", true);
                    bundle.putString("makeout_addrId",consigneeEntity.getAddress_id());
                    intent2.putExtras(bundle);

                    startActivityForResult(intent2, AddManage);
//                    this.startActivity(intent2);
                }
                break;
        }
    }


    @Override
    public void onItemClickAdapter(String ischeckArray) {
        Intent intent = new Intent(this, ProductDeatilActivity.class);
           /* PromotePostsData postsData=new PromotePostsData();
            postsData.setId(param);
            intent.putExtra("PromotePostsData",postsData);*/
        intent.putExtra("param", ischeckArray);
        this.startActivity(intent);

    }


    boolean  isDefaultOrSelect;//在地址管理有没有选择默认地址
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case AddManage:
                if (resultCode == RESULT_OK) {

                    String addrId = data.getStringExtra("consignee");
                    isDefaultOrSelect=data.getBooleanExtra("isDefaultOrSelect",false);



                    if(isDefaultOrSelect){

                        if(!addrId.equals(consigneeEntity.getAddress_id())){
                            consigneeEntity.setIs_default(0);
                            dataEntity.setConsignee(consigneeEntity);
                            initAddressInfo(dataEntity);
                        }else{
                            consigneeEntity.setIs_default(1);
                            dataEntity.setConsignee(consigneeEntity);
                            initAddressInfo(dataEntity);
                        }



                    }else{



                        if (sessionData != null && addrId != null && !addrId.equals("")) {
                            networkFlowChangeConsignee(sessionData.getUid(), sessionData.getSid(), addrId);
                        }
                    }

                }

                break;
            case CreatAdd:
                if (resultCode == RESULT_OK) {

                    String addrId = data.getStringExtra("consignee");
                    if (sessionData != null && addrId != null && !addrId.equals("")) {
                        networkFlowChangeConsignee(sessionData.getUid(), sessionData.getSid(), addrId);
                    }

                }
                break;

        }
    }


    /**
     * 切换订单配送地址接口
     */
    OrderFlowCheckOut.DataEntity dataEntityNew;

    private void networkFlowChangeConsignee(String uid, String sid, String addrId) {
        String url = "http://mapp.aiderizhi.com/?url=/flow/change_consignee";//
        Map<String, String> mapTou = new HashMap<String, String>();
        String sessinStr = "{\"session\":{\"uid\":\"" + uid + "\",\"sid\":\"" + sid + "\"},\"addr_id\":\"" + addrId + "\"}";
        mapTou.put("json", sessinStr);


        Log.d("MakeOutOrderActivity", sessinStr + "      ");


        FastJsonRequest<OrderFlowCheckOut> fastJsonCommunity = new FastJsonRequest<OrderFlowCheckOut>(Request.Method.POST, url, OrderFlowCheckOut.class, null, new Response.Listener<OrderFlowCheckOut>() {
            @Override
            public void onResponse(OrderFlowCheckOut orderFlowCheckOut) {

                OrderFlowCheckOut.StatusEntity status = orderFlowCheckOut.getStatus();
                if (status.getSucceed() == 1) {
                    dataEntityNew = orderFlowCheckOut.getData();
                    if (dataEntityNew != null) {

                        dataEntity.setConsignee(dataEntityNew.getConsignee());
                        dataEntity.setShipping_list(dataEntityNew.getShipping_list());
                        initAddressInfo(dataEntity);//初始化收货地址信息

                        Log.d("MakeOutOrderActivity", "/flow/change_consignee：   " + "++++succeed");
                    }


                } else {

                    // 请求失败
                    Log.d("MakeOutOrderActivity", "succeded=00000  " + JSON.toJSONString(status) + "");
                    Toast.makeText(getApplicationContext(), "" + status.getError_desc(), Toast.LENGTH_SHORT).show();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("MakeOutOrderActivity", "errror" + volleyError.toString() + "");
            }
        });

        fastJsonCommunity.setParams(mapTou);
        mQueue.add(fastJsonCommunity);
    }


}
