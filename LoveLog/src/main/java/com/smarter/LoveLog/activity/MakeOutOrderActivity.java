package com.smarter.LoveLog.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.leaking.slideswitch.SlideSwitch;
import com.smarter.LoveLog.R;
import com.smarter.LoveLog.adapter.RecycleMakeoutMoneyAdapter;
import com.smarter.LoveLog.adapter.RecycleMakeoutOrderAdapter;
import com.smarter.LoveLog.db.SharedPreUtil;
import com.smarter.LoveLog.http.FastJsonRequest;
import com.smarter.LoveLog.model.loginData.SessionData;
import com.smarter.LoveLog.model.orderMy.OrderFlowCheckOut;
import com.smarter.LoveLog.ui.SyLinearLayoutManager;
import com.smarter.LoveLog.utills.ViewUtill;

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
public class MakeOutOrderActivity extends BaseFragmentActivity implements View.OnClickListener, RecycleMakeoutOrderAdapter.OnItemClickListener {
    String Tag = "MakeOutOrderActivity";
    Context mContext;
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
    @Bind(R.id.distriText)
    TextView distriText;
    @Bind(R.id.paydistLinear)
    LinearLayout paydistLinear;
    @Bind(R.id.paymentTypeText)
    TextView paymentTypeText;
    @Bind(R.id.slideSwitchBut)
    SlideSwitch slideSwitchBut;
    @Bind(R.id.user_integral)
    TextView userIntegral;
    @Bind(R.id.edit_integral_area_text)
    EditText editIntegralAreaText;
    @Bind(R.id.edit_integral_linear)
    LinearLayout editIntegralLinear;
    @Bind(R.id.cancel_integralTools)
    TextView cancelIntegralTools;
    @Bind(R.id.true_integralTools)
    TextView trueIntegralTools;
    @Bind(R.id.orderIntegralToolLinear)
    LinearLayout orderIntegralToolLinear;
    @Bind(R.id.orderXuanfuLinear)
    LinearLayout orderXuanfuLinear;
    @Bind(R.id.redPacketText)
    TextView redPacketText;
    @Bind(R.id.redTishiText)
    TextView redTishiText;
    @Bind(R.id.RedPacketButLinear)
    LinearLayout RedPacketButLinear;
    @Bind(R.id.recyclerViewMoney)
    RecyclerView recyclerViewMoney;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_out_order_data_view);
        ButterKnife.bind(this);
        mContext = this;
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


            if (SharedPreUtil.isLogin()) {
                sessionData = SharedPreUtil.LoginSessionData();
            }
            // Toast.makeText(this,str+"",Toast.LENGTH_LONG).show();
            if (sessionData != null) {
                intData();

            }
        }


    }

    OrderFlowCheckOut.DataEntity.ConsigneeEntity consigneeEntity = new OrderFlowCheckOut.DataEntity.ConsigneeEntity();
    List<OrderFlowCheckOut.DataEntity.GoodsListEntity> goodsListEntityList = new ArrayList<OrderFlowCheckOut.DataEntity.GoodsListEntity>();
    List<OrderFlowCheckOut.DataEntity.ShippingListEntity> shipping_list = new ArrayList<OrderFlowCheckOut.DataEntity.ShippingListEntity>();
    List<OrderFlowCheckOut.DataEntity.PaymentTypeEntity> paymentTypeEntityList = new ArrayList<OrderFlowCheckOut.DataEntity.PaymentTypeEntity>();
    int userIntegralNum;
    int maxIntegral;

    public void initRecycleViewVertical() {

//初始化收货地址信息
        initAddressInfo();

//订单产品list数据
        goodsListEntityList = dataEntity.getGoods_list();
        if (goodsListEntityList != null) {
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


        //初始化支付配送信息
        initPayShipMehth();


        //积分

        initIntegralMeth();

        //红包

        initRedPacket();

        //结算list
        initListMoney();


    }

    List<String> moneyAdapData =new ArrayList<String>();
    List<String> moneyAdapDataValue =new ArrayList<String>();
    RecycleMakeoutMoneyAdapter adapterMoney;
    private void initListMoney() {

        initListDataMoney();

        // 创建一个线性布局管理器
        SyLinearLayoutManager layoutManager = new SyLinearLayoutManager(this);
        layoutManager.setOrientation(SyLinearLayoutManager.VERTICAL);
        // 设置布局管理器
        recyclerViewMoney.setLayoutManager(layoutManager);
        // 创建Adapter，并指定数据集
       adapterMoney = new RecycleMakeoutMoneyAdapter(moneyAdapData,moneyAdapDataValue);
        // 设置Adapter
        recyclerViewMoney.setAdapter(adapterMoney);
    }

   private void refrefMoneyListAdapter(){
       moneyAdapData.clear();
       moneyAdapDataValue.clear();
       initListDataMoney();
       adapterMoney.notifyDataSetChanged();

   }


    private void initListDataMoney(){
        OrderFlowCheckOut.DataEntity.TotalEntity  totalEntity =dataEntity.getTotal();


        if(totalEntity!=null){
            moneyAdapData.add("商品金额");
            moneyAdapDataValue.add(totalEntity.getGoods_price_formated());//"goods_price_formated":"¥0.05",


            if(isSelectDefuPaymentTypt&&isSelectShippingType){
                moneyAdapData.add("运费");
                moneyAdapDataValue.add(selectShippingListEntity.getFormat_shipping_fee());//"shipping_fee_formated":"¥0.00",
            }

            moneyAdapData.add("积分抵扣");
            moneyAdapDataValue.add(totalEntity.getIntegral_formated());//"integral_formated":"¥0.00",


            if(isSelectUseRedPacket){
                moneyAdapData.add("红包抵扣");

                moneyAdapDataValue.add(dataEntity.getTotal().getBonus_formated());//"bonus_formated":"¥0.00",
            }

        }
    }


    private void initRedPacket() {
       /* String bousT="{\n" +
                "\"id\":\"11756\",\n" +
                "\"sn\":\"N/A\",\n" +
                "\"name\":\"好面膜齐分享5元红包\",\n" +
                "\"amount\":\"¥5.00\",\n" +
                "\"min_order_amount\":\"¥50.00\",\n" +
                "\"start_time\":\"2016-05-12\",\n" +
                "\"end_time\":\"2016-06-12\"\n" +
                "}";
        String boudF="{\n" +
                "\"id\":\"11872\",\n" +
                "\"sn\":\"N/A\",\n" +
                "\"name\":\"897电台听众专属红包\",\n" +
                "\"amount\":\"¥5.00\",\n" +
                "\"min_order_amount\":\"¥78.00\",\n" +
                "\"start_time\":\"2016-03-29\",\n" +
                "\"end_time\":\"2016-04-29\"\n" +
                "}";
        OrderFlowCheckOut.DataEntity.BonusEntity  bonusEntity=JSON.parseObject(bousT, OrderFlowCheckOut.DataEntity.BonusEntity.class);
        OrderFlowCheckOut.DataEntity.BonusEntity  bonusEntity2=JSON.parseObject(boudF, OrderFlowCheckOut.DataEntity.BonusEntity.class);
       List<OrderFlowCheckOut.DataEntity.BonusEntity> myBounsList=new ArrayList<OrderFlowCheckOut.DataEntity.BonusEntity>();
        myBounsList.add(bonusEntity2);
         myBounsList.add(bonusEntity);
        dataEntity.setBonus(myBounsList);
*/


        if(dataEntity.getAllow_use_bonus()==1&&dataEntity.getBonus()!=null&&dataEntity.getBonus().size()>0){
            redPacketText.setText("未使用红包");

        }else{
            redPacketText.setText("无可用红包");
        }


    }

    private void initIntegralMeth() {
        slideSwitchBut.setShapeType(SlideSwitch.SHAPE_CIRCLE);
        userIntegralNum = Integer.parseInt(dataEntity.getUser_integral());//可用积分
        maxIntegral = dataEntity.getOrder_max_integral();//订单最大使用积分
        if (userIntegralNum > 0 && maxIntegral > 0) {//
            userIntegral.setText("可用积分" + userIntegralNum);
            slideSwitchBut.setSlideable(true);

        } else {
            slideSwitchBut.setSlideable(false);
            userIntegral.setText("无积分可用");
        }
//        slideSwitchBut.setState(true);
//        slideSwitchBut.setSlideable(false);
//        slideSwitchBut.setSlideable(true);
        slideSwitchBut.setSlideListener(new SlideSwitch.SlideListener() {

            @Override
            public void open() {
                // Do something ,,,
                userIntegral.setVisibility(View.GONE);
                editIntegralLinear.setVisibility(View.VISIBLE);
                editIntegralAreaText.setText(userIntegralNum + "");
                //获得焦点
                editIntegralAreaText.setFocusable(true);
                editIntegralAreaText.setFocusableInTouchMode(true);
                editIntegralAreaText.requestFocus();


            }

            @Override
            public void close() {
                // Do something ,,,
                userIntegral.setVisibility(View.VISIBLE);
                editIntegralLinear.setVisibility(View.GONE);

                //取消焦点

                editIntegralAreaText.clearFocus();
            }
        });
        editIntegralAreaText.addTextChangedListener(new EditChangedListener());
        editIntegralAreaText.setOnFocusChangeListener(new View.
                OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                    orderXuanfuLinear.setVisibility(View.GONE);
                    orderIntegralToolLinear.setVisibility(View.VISIBLE);
                    InputMethodManager imm = (InputMethodManager) editIntegralAreaText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);


                } else {
                    // 此处为失去焦点时的处理内容
                    orderXuanfuLinear.setVisibility(View.VISIBLE);
                    orderIntegralToolLinear.setVisibility(View.GONE);
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (inputMethodManager != null) {
                        // 隐藏键盘
                        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                }
            }
        });


        dingdanConnect.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //获取当前获得当前焦点所在View
                    View view = getCurrentFocus();
                    if (isClickEt(view, event)) {
                        if (orderIntegralToolLinear.getVisibility() == View.VISIBLE) {


                            // 如果不是edittext，则隐藏键盘
                            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            if (inputMethodManager != null) {
                                // 隐藏键盘
                                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                            }

//                            editIntegralAreaText.clearFocus();

                            return true;

                        }
                    }
                }


                return false;
            }
        });


    }


    class EditChangedListener implements TextWatcher {
        private String temp;//监听前的文本
        private int editStart;//光标开始位置
        private int editEnd;//光标结束位置
        private final int charMaxNum = 10;

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            temp = s.toString();

            Log.i(Tag, "输入文本之前的状态" + temp.toString());
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            Log.i(Tag, "输入文字中的状态，count是一次性输入字符数" + s);

        }

        @Override
        public void afterTextChanged(Editable s) {

            /** 得到光标开始和结束位置 ,超过最大数后记录刚超出的数字索引进行控制 */
            Message msg = mHandler.obtainMessage();
            msg.what = 1;
            msg.obj = editIntegralAreaText.getText().toString();
            Log.i(Tag, "输入文字后的状态");

            mHandler.sendMessageDelayed(msg, 800);


        }
    }

    String tempEditText = "";
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    tempEditText = ((String) msg.obj);
                    int integralNum = 1;
                    if (tempEditText.equals("")) {
//                        editIntegralAreaText.setText(1 + "");
                    } else {
                        integralNum = Integer.parseInt(tempEditText);
//
//                        if(tempEditText.startsWith("0")&&tempEditText.length()>=2){
//                            editIntegralAreaText.setText(integralNum + "");
//                        }


                        if (integralNum > maxIntegral) {//integralNum >userIntegralNum||
                            editIntegralAreaText.setText(maxIntegral + "");
                        }
                        if (integralNum <= 0) {
//                            editIntegralAreaText.setText(userIntegralNum + "");
                        }
                    }


                    editIntegralAreaText.setSelection(editIntegralAreaText.getText().length());

                    break;
            }
        }
    };


    /**
     * 點擊EditText以外的區域后鍵盤隱藏
     *
     * @param event
     * @return
     *//*
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            //获取当前获得当前焦点所在View
            View view = getCurrentFocus();
            if (isClickEt(view, event)) {


                if(orderIntegralToolLinear.getVisibility()==View.VISIBLE){


                    // 如果不是edittext，则隐藏键盘
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
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

       *//* 看源码可知superDispatchTouchEvent  是个抽象方法，用于自定义的Window
                 此处目的是为了继续将事件由dispatchTouchEvent ( MotionEvent event ) 传递到onTouchEvent ( MotionEvent event )
                 必不可少，否则所有组件都不能触发 onTouchEvent ( MotionEvent event )
        *//*
        if (getWindow().superDispatchTouchEvent(event)) {
            return true;
        }
        return onTouchEvent(event);
    }*/


    //    获取当前点击位置是否为et
//     @param view 焦点所在View
//     @param event 触摸事件
//    @ return
    public static boolean isClickEt(View view, MotionEvent event) {
        if (view != null && (view instanceof EditText)) {

            int[] leftTop = {0, 0};
            // 获取输入框当前的 location 位置
            view.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            // 此处根据输入框左上位置和宽高获得右下位置
            int bottom = top + view.getHeight();
            int right = left + view.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }


        return false;
    }

boolean  isSelectAddress;//是否添加了收货地址
    private void initAddressInfo() {


        consigneeEntity = dataEntity.getConsignee();


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


            isSelectAddress=true;
        }

    }

    boolean  isSelectDefuPaymentTypt=false;//是否选择支付方式
    boolean  isSelectShippingType=false;//是否选择配送方式
    OrderFlowCheckOut.DataEntity.ShippingListEntity selectShippingListEntity;//选择了的配送方式（运费）
    private void initPayShipMehth() {
        //选择配送方式
        shipping_list = dataEntity.getShipping_list();
        if (shipping_list != null && shipping_list.size() > 0) {
            for(OrderFlowCheckOut.DataEntity.ShippingListEntity ss:shipping_list){
                if((dataEntity.getDefault_shipping_id()+"").equals(ss.getShipping_id())){
                    isSelectShippingType=true;
                    selectShippingListEntity=ss;
                    ss.setIsSelceteItem(true);
                    distriText.setText(ss.getShipping_name());
                }else{
                    ss.setIsSelceteItem(false);
                }
            }

            if(!isSelectShippingType){
                distriText.setText("未选择");
            }




        }else{
            distriText.setText("未选择");
        }


//选择支付方式


        paymentTypeEntityList = dataEntity.getPayment_type();
        if (paymentTypeEntityList != null && paymentTypeEntityList.size() > 0) {



            for(OrderFlowCheckOut.DataEntity.PaymentTypeEntity pp:paymentTypeEntityList){
                if(dataEntity.getDefault_pay_type_id()==Integer.parseInt(pp.getId())){
                    pp.setIsSelceteItem(true);

                    isSelectDefuPaymentTypt=true;
                    paymentTypeText.setText(pp.getName());
                }else{
                    pp.setIsSelceteItem(false);
                }
            }

            if(!isSelectDefuPaymentTypt){
                paymentTypeText.setText("未选择");
            }



        }else{
            paymentTypeText.setText("未选择");
        }




        if(isSelectShippingType&&isSelectDefuPaymentTypt){
            refrefMoneyListAdapter();
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


    /**
     * 获取订单积分，抵扣数据
     */

    OrderFlowCheckOut.DataEntity IntegralDataEntity = new OrderFlowCheckOut.DataEntity();//积分的json数据
    boolean isUsedIntegral;

    private void networkFlowChangeIntegral(String uid, String sid, String integral, String bonus, final String netTag) {
        String url = "http://mapp.aiderizhi.com/?url=/flow/";//
        String sessinStr = "{\"session\":{\"uid\":\"" + uid + "\",\"sid\":\"" + sid + "\"}";
        if (netTag.equals("change_integral")) {
            url += "change_integral";
            sessinStr += ",\"integral\":\"" + integral + "\"}";
        } else {
            url += "change_bonus";
            sessinStr += ",\"bonus_id\":\"" + bonus + "\"}";
        }
        Map<String, String> mapTou = new HashMap<String, String>();


        mapTou.put("json", sessinStr);


        Log.d("MakeOutOrderActivity", sessinStr + "      ");


        FastJsonRequest<OrderFlowCheckOut> fastJsonCommunity = new FastJsonRequest<OrderFlowCheckOut>(Request.Method.POST, url, OrderFlowCheckOut.class, null, new Response.Listener<OrderFlowCheckOut>() {
            @Override
            public void onResponse(OrderFlowCheckOut orderFlowCheckOut) {

                OrderFlowCheckOut.StatusEntity status = orderFlowCheckOut.getStatus();
                if (status.getSucceed() == 1) {
                    IntegralDataEntity = orderFlowCheckOut.getData();
                    if (IntegralDataEntity != null) {

                        if (netTag.equals("change_integral")) {
                            isUsedIntegral = true;
                            userIntegral.setText("使用" + IntegralDataEntity.getUser_integral() + "积分，抵" + IntegralDataEntity.getDeductible_amount());

                        } else {

                            isSelectUseRedPacket = true;
                            redPacketText.setText("已使用");
                            redTishiText.setText("红包减免" + IntegralDataEntity.getDeductible_amount());



                            //使用红包赋值dataEntity
                            dataEntity.setUsed_bouns_id(IntegralDataEntity.getUsed_bouns_id());
                            dataEntity.getTotal().setBonus_formated(IntegralDataEntity.getTotal().getBonus_formated());
                            dataEntity.getTotal().setBonus(IntegralDataEntity.getTotal().getBonus());

//                            Toast.makeText(mContext,redId+" ———————— "+isSelectUseRedPacket,Toast.LENGTH_SHORT).show();

                        }

                    }

                    Log.d("MakeOutOrderActivity", "/flow/change_integral：   " + "++++succeed");


                } else {

                    // 请求失败
                    Log.d("MakeOutOrderActivity", "succeded=00000  " + JSON.toJSONString(status) + "");
                    Toast.makeText(getApplicationContext(), "" + status.getError_desc(), Toast.LENGTH_SHORT).show();

                }


                if (netTag.equals("change_integral")) {
                    userIntegral.setVisibility(View.VISIBLE);
                    editIntegralLinear.setVisibility(View.GONE);

                    //取消焦点

                    editIntegralAreaText.clearFocus();
                    slideSwitchBut.setState(false);
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
    private static final int PayDist = 2;
    private static final int RedPack = 3;


    @OnClick({R.id.backBUt, R.id.buy_now, R.id.addressRelatiBut, R.id.paydistLinear, R.id.cancel_integralTools, R.id.true_integralTools, R.id.RedPacketButLinear})
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
                    bundle.putString("makeout_addrId", consigneeEntity.getAddress_id());
                    intent2.putExtras(bundle);

                    startActivityForResult(intent2, AddManage);
//                    this.startActivity(intent2);
                }
                break;
            case R.id.paydistLinear:


                if (consigneeEntity == null) {
                    Toast.makeText(mContext, "请先完善您的配送地址", Toast.LENGTH_SHORT).show();
                } else {
                    //支付配送界面
                    shipping_list=dataEntity.getShipping_list();
                    if (shipping_list != null && shipping_list.size() > 0) {
                        Intent intent4 = new Intent(this, PayDistModeActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("shipping_list", dataEntity);
                        intent4.putExtras(bundle);
                        startActivityForResult(intent4, PayDist);
                    }

                }
                break;

            case R.id.cancel_integralTools:


                userIntegral.setVisibility(View.VISIBLE);
                editIntegralLinear.setVisibility(View.GONE);

                //取消焦点

                editIntegralAreaText.clearFocus();
                slideSwitchBut.setState(false);

                break;
            case R.id.true_integralTools:
                if (sessionData != null) {
                    String integralNum = editIntegralAreaText.getText().toString();
                    if (integralNum.equals("")) {
                        Toast.makeText(mContext, "积分不能为空", Toast.LENGTH_SHORT).show();
                    } else {
                        networkFlowChangeIntegral(sessionData.getUid(), sessionData.getSid(), integralNum, null, "change_integral");

                    }

                }
                break;
            case R.id.RedPacketButLinear:
                //红包界面
                if (dataEntity.getAllow_use_bonus() ==1&&dataEntity.getBonus()!=null&&dataEntity.getBonus().size()>0) {
                    Intent intent4 = new Intent(this, MyRedPacketActivity.class);
                    Bundle bundle = new Bundle();
                    if (isSelectUseRedPacket) {
                        bundle.putString("isUseRedPacketValue", redId);
                    }

                    bundle.putSerializable("bonusList",dataEntity);

                    bundle.putBoolean("isOrdeSelectRed", true);
                    intent4.putExtras(bundle);
                    startActivityForResult(intent4, RedPack);
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


    boolean isDefaultOrSelect;//在地址管理有没有选择默认地址

    OrderFlowCheckOut.DataEntity.PaymentTypeEntity paymentTypeEntity;//选中的支付方式
    OrderFlowCheckOut.DataEntity.ShippingListEntity shippingListEntity;//选中的配送方式


    String redId;
    String redMoney;
    boolean isSelectUseRedPacket;//是否使用了红包

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case AddManage:
                if (resultCode == RESULT_OK) {

                    String addrId = data.getStringExtra("consignee");
                    isDefaultOrSelect = data.getBooleanExtra("isDefaultOrSelect", false);


                    if (isDefaultOrSelect) {

                        if (!addrId.equals(dataEntity.getConsignee().getAddress_id())) {
                            dataEntity.getConsignee().setIs_default(0);
                            initAddressInfo();
                        } else {
                            dataEntity.getConsignee().setIs_default(1);
                            initAddressInfo();
                        }


                    } else {


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

            case PayDist:
                if (resultCode == RESULT_OK) {

                    paymentTypeEntity = (OrderFlowCheckOut.DataEntity.PaymentTypeEntity) data.getSerializableExtra("pay_type");
                    shippingListEntity = (OrderFlowCheckOut.DataEntity.ShippingListEntity) data.getSerializableExtra("shipping_id");
                    OrderFlowCheckOut.DataEntity ps_data_entity = (OrderFlowCheckOut.DataEntity) data.getSerializableExtra("ps_data_entity");
                    dataEntity.setPayment_type(ps_data_entity.getPayment_type());
                    dataEntity.setShipping_list(ps_data_entity.getShipping_list());
                    dataEntity=ps_data_entity;
                    initPayShipMehth();

                }
                break;
            case RedPack:
                if (resultCode == RESULT_OK) {
                    redId = data.getStringExtra("red_id");
                    redMoney = data.getStringExtra("red_money");
                    if (redId == null || redId.equals("")) {
                        if (isSelectUseRedPacket) {
                            isSelectUseRedPacket = false;
                            redPacketText.setText("未使用红包");
                            redTishiText.setText("");
                        }

                    } else {
                        if (sessionData != null) {

                            networkFlowChangeIntegral(sessionData.getUid(), sessionData.getSid(), null, redId, "change_bonus");


                        }
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
                        initAddressInfo();//初始化收货地址信息

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
