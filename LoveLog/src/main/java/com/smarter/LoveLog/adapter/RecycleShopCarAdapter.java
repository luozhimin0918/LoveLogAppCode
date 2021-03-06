package com.smarter.LoveLog.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.smarter.LoveLog.R;
import com.smarter.LoveLog.db.AppContextApplication;
import com.smarter.LoveLog.db.SharedPreUtil;
import com.smarter.LoveLog.db.SharedPreferences;
import com.smarter.LoveLog.http.FastJsonRequest;
import com.smarter.LoveLog.model.loginData.SessionData;
import com.smarter.LoveLog.model.orderMy.ShopCarOrderInfo;
import com.smarter.LoveLog.ui.SlidingButtonView;
import com.smarter.LoveLog.utills.DeviceUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/12/22.
 */
public class RecycleShopCarAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  implements SlidingButtonView.IonSlidingButtonListener{

    RequestQueue mQueue;
    // 数据集
    List<ShopCarOrderInfo.DataEntity.GoodsListEntity> orderLists;

    //编辑完成的数据
    List<ShopCarOrderInfo.DataEntity.GoodsListEntity> tempGoodsLists;

    SessionData sessionData;

    Context mContxt;
    private SlidingButtonView mMenu = null;


    public List<ShopCarOrderInfo.DataEntity.GoodsListEntity> getOrderLists() {
        return orderLists;
    }

    public void setOrderLists(List<ShopCarOrderInfo.DataEntity.GoodsListEntity> orderLists) {
        this.orderLists = orderLists;
    }

    public RecycleShopCarAdapter(List<ShopCarOrderInfo.DataEntity.GoodsListEntity> orderLists,Context mContxt) {
        super();
        this.orderLists = orderLists;
        mQueue = AppContextApplication.getInstance().getmRequestQueue();
        this.tempGoodsLists=new ArrayList<ShopCarOrderInfo.DataEntity.GoodsListEntity>();
        this.mContxt=mContxt;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // 创建一个View，简单起见直接使用系统提供的布局，就是一个TextView
        View view = View.inflate(viewGroup.getContext(), R.layout.adapter_activity_shop_car_item, null);
        // 创建一个ViewHolder
        ViewHolder holder = new ViewHolder(view);
        if(SharedPreUtil.isLogin()){
            String sessionString = SharedPreferences.getInstance().getString("session", "");
            sessionData = JSON.parseObject(sessionString, SessionData.class);

        }

        return holder;
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holedr, final int i) {
        ViewHolder viewHolder = (ViewHolder) holedr;
        // 绑定数据到ViewHolder上
       final ShopCarOrderInfo.DataEntity.GoodsListEntity goodsListOne = orderLists.get(i);

        viewHolder.desInfo.setText("");
        viewHolder.onlay.getLayoutParams().width = DeviceUtil.getWidth((Activity) mContxt);


        if(goodsListOne.getIs_shipping().equals("1")){
        Bitmap b2 = BitmapFactory.decodeResource(mContxt.getResources(), R.mipmap.free);
        ImageSpan imgSpan2 = new ImageSpan(mContxt, b2);
        SpannableString spanString2 = new SpannableString("免费");
        spanString2.setSpan(imgSpan2, 0, spanString2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        viewHolder.desInfo.append(spanString2);
        viewHolder.desInfo.append("  ");
        }

        if(!goodsListOne.getIs_gift().equals("0")){
            Bitmap b = BitmapFactory.decodeResource(mContxt.getResources(), R.mipmap.zengpin);
            ImageSpan imgSpan = new ImageSpan(mContxt, b);
            SpannableString spanString = new SpannableString("赠品");
            spanString.setSpan(imgSpan, 0, spanString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            viewHolder.desInfo.append(spanString);
            viewHolder.desInfo.append("  ");
        }

        viewHolder.desInfo.append(goodsListOne.getGoods_name());
        viewHolder.desInfoTwo.setText(goodsListOne.getGoods_name());
        viewHolder.shopPrice.setText(goodsListOne.getGoods_price().replace("¥",""));
        viewHolder.shopCarNum.setText(goodsListOne.getGoods_number());
        viewHolder.ShopCarNumZhi.setText(goodsListOne.getGoods_number());

        viewHolder.ivAdapterGridPic.setDefaultImageResId(R.drawable.loading_small);
        viewHolder.ivAdapterGridPic.setErrorImageResId(R.drawable.loading_small);
        String UserimageUrl = "";
        if (goodsListOne.getImg_thumb() != null) {
            UserimageUrl = goodsListOne.getImg_thumb();
        }

        if (mQueue.getCache().get(UserimageUrl) == null) {
            viewHolder.ivAdapterGridPic.startAnimation(ImagePagerAdapter.getInAlphaAnimation(2000));
        }
        viewHolder.ivAdapterGridPic.setImageUrl(UserimageUrl, AppContextApplication.getInstance().getmImageLoader());




        if(goodsListOne.is_all_edit()){
            viewHolder.wanchenProgress.setVisibility(View.GONE);
            viewHolder.bianjiProgress.setVisibility(View.VISIBLE);
        }else{
            viewHolder.wanchenProgress.setVisibility(View.VISIBLE);
            viewHolder.bianjiProgress.setVisibility(View.GONE);
        }




        if(goodsListOne.is_all_select()){
            viewHolder.isImage.setBackgroundResource(R.mipmap.choiceon);
        }else{
            viewHolder.isImage.setBackgroundResource(R.mipmap.choice);
        }


        viewHolder.onlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断是否有删除菜单打开
                if (menuIsOpen()) {
                    closeMenu();//关闭菜单
                }
            }
        });


        viewHolder.popAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Integer.parseInt(goodsListOne.getGoods_number())+5;
                goodsListOne.setGoods_number(num + "");

                thisNotifyDataSetChanged(goodsListOne, false);//保存本地数据


            }
        });
        viewHolder.popReduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Integer.parseInt(goodsListOne.getGoods_number())-5;
                if(num>=5){
                    goodsListOne.setGoods_number(num + "");

                    thisNotifyDataSetChanged(goodsListOne, false);//保存本地数据

                }


            }
        });

        viewHolder.isImageLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(goodsListOne.is_all_select()){
                    goodsListOne.setIs_all_select(false);
                    if(isQuxuan){//当全选时，又取消了一个选项。回调

                        OnCheckDefaultListener.onAllselectToCanter(true);
                    }

                }else{
                    goodsListOne.setIs_all_select(true);


                    isQuanHuidiao();//判断全选回调，改变购物车全选图标
                }


                OnCheckDefaultListener.onJisuanZongPrice();//计算总价格


                notifyDataSetChanged();
            }
        });


        viewHolder.deleteBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SharedPreUtil.isLogin()) {
                    if (sessionData != null) {
                            initData(sessionData,orderLists.get(i),"delete");
                    }

                }else{
                    ShopCarOrderInfo.DataEntity.GoodsListEntity tempGods =orderLists.get(i);
                    orderLists.remove(i);
                    thisNotifyDataSetChanged(tempGods,true);//保存本地数据

                    //广播通知刷新购物车数量
                    Intent intent = new Intent();
                    intent.setAction("UpShopCarNum");
                    intent.putExtra("update", "ok");
                    mContxt.sendBroadcast(intent);


                    if(orderLists.size()<=0){

                        OnCheckDefaultListener.onDeleteAll();
                    }


                }




            }
        });


    }

    private void isQuanHuidiao() {
        /**
         * list一个个点击全选之后的回调
         */
        int numOrdeSele=0;
        for(int o=0;o<orderLists.size();o++){
            if(orderLists.get(o).is_all_select()){
                ++numOrdeSele;
            }
        }
        if(numOrdeSele==orderLists.size()){
            OnCheckDefaultListener.onAllselectToCanter(false);
            isQuxuan=true;//表示全选了
        }

    }

    private void thisNotifyDataSetChanged(ShopCarOrderInfo.DataEntity.GoodsListEntity localData,boolean isDelete) {

               if(!SharedPreUtil.isLogin()){
                   if(isDelete){
                       SharedPreUtil.deleteLocalShopCarData(localData);
                       isQuanHuidiao();//判断全选回调，改变购物车全选图标
                   }else{
                   SharedPreUtil.updateLocalShopCarData(localData);



                  }
               }
        notifyDataSetChanged();

        OnCheckDefaultListener.onJisuanZongPrice();//计算总价格


    }








    /**
     * onMenuIsOpen  onDownOrMove   closeMenu  menuIsOpen
     */
    @Override
    public void onMenuIsOpen(View view) {
        mMenu = (SlidingButtonView) view;
    }

    @Override
    public void onDownOrMove(SlidingButtonView slidingButtonView) {
        if(menuIsOpen()){
            if(mMenu != slidingButtonView){
                closeMenu();
            }
        }
    }

    /**
     * 关闭菜单
     */
    public void closeMenu() {
        mMenu.closeMenu();
        mMenu = null;

    }
    /**
     * 判断是否有菜单打开
     */
    public Boolean menuIsOpen() {
        if(mMenu != null){
            return true;
        }
        Log.i("asd","mMenu为null");
        return false;
    }


    //回调开始
    public interface OnCheckDefaultListener {
        void oncheckOK(Boolean[] ischeckArray);
        void onAllselectToCanter(boolean isquxiaoQuxian);
        void onJisuanZongPrice();
        void onDeleteAll();
    }

    private OnCheckDefaultListener OnCheckDefaultListener;

    public void setOnCheckDefaultListener(OnCheckDefaultListener onCheckDefault) {
        this.OnCheckDefaultListener = onCheckDefault;
    }

    //结束
    @Override
    public int getItemCount() {
        return orderLists.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.isImageLinear)
        LinearLayout isImageLinear;
        @Bind(R.id.onlay)
        RelativeLayout onlay;
        @Bind(R.id.isImage)
        ImageView isImage;
        @Bind(R.id.iv_adapter_grid_pic)
        NetworkImageView ivAdapterGridPic;

        @Bind(R.id.desInfo)
        TextView desInfo;
        @Bind(R.id.desInfoTwo)
        TextView desInfoTwo;
        @Bind(R.id.shopPrice)
        TextView shopPrice;
        @Bind(R.id.shopCarNum)
        TextView shopCarNum;
        @Bind(R.id.ShopCarNumZhi)
        TextView ShopCarNumZhi;


        @Bind(R.id.wanchenProgress)
        LinearLayout wanchenProgress;
        @Bind(R.id.pop_reduce)
        ImageView popReduce;
        @Bind(R.id.pop_add)
        ImageView popAdd;
        @Bind(R.id.bianjiProgress)
        LinearLayout bianjiProgress;
        @Bind(R.id.deleteBut)
        TextView deleteBut;



        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            ((SlidingButtonView) view).setSlidingButtonListener(RecycleShopCarAdapter.this);
        }
    }




    /**
     * 更新购物车数据
     * @param sessionDataOne
     */
    private void initData(SessionData sessionDataOne,final ShopCarOrderInfo.DataEntity.GoodsListEntity goodsListEntityOne, final String action) {


        String url = "http://mapp.aiderizhi.com/?url=/cart/"+action;//

        Map<String, String> map = new HashMap<String, String>();



            map = new HashMap<String, String>();
        String oneString="";
         if(action.equals("delete")){
             oneString = "{\"rec_id\":\""+goodsListEntityOne.getRec_id()+"\",\"session\":{\"uid\":\"" + sessionDataOne.getUid() + "\",\"sid\":\"" + sessionDataOne.getSid() + "\"}}";

         }else{
             oneString = "{\"rec_id\":\""+goodsListEntityOne.getRec_id()+"\",\"number\":\""+Integer.parseInt(goodsListEntityOne.getGoods_number())+"\",\"session\":{\"uid\":\"" + sessionDataOne.getUid() + "\",\"sid\":\"" + sessionDataOne.getSid() + "\"}}";

         }
        map.put("json", oneString);
            Log.d("recyShopCarAdapter", oneString + "》》》》");



        RequestQueue mQueue = AppContextApplication.getInstance().getmRequestQueue();
        FastJsonRequest<ShopCarOrderInfo> fastJsonCommunity = new FastJsonRequest<ShopCarOrderInfo>(Request.Method.POST, url, ShopCarOrderInfo.class, null, new Response.Listener<ShopCarOrderInfo>() {
            @Override
            public void onResponse(ShopCarOrderInfo myOrderInfo) {

                ShopCarOrderInfo.StatusEntity status = myOrderInfo.getStatus();
                if (status.getSucceed() == 1) {


                    if(action.equals("delete")){
                        orderLists.remove(goodsListEntityOne);


                        if(orderLists.size()<=0){

                            OnCheckDefaultListener.onDeleteAll();
                        }


                        //广播通知刷新购物车数量
                        Intent intent = new Intent();
                        intent.setAction("UpShopCarNum");
                        intent.putExtra("update", "ok");
                        mContxt.sendBroadcast(intent);


                            notifyDataSetChanged();


                        OnCheckDefaultListener.onJisuanZongPrice();//计算总价格
                        isQuanHuidiao();//判断全选回调，改变购物车全选图标

                    }



                    Log.d("recyShopCarAdapter", "" + status.getSucceed() + "++++succeed》》》》" );
                } else {

//                      Toast.makeText(mContxt,""+status.getError_desc(),Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                //未知错误

                Log.d("recyShopCarAdapter", "errror" + volleyError.toString() + "++++》》》》");
            }
        });

        fastJsonCommunity.setParams(map);

        mQueue.add(fastJsonCommunity);


    }


    Boolean  isQuxuan=false;//是否全选list
    public void myNotifiAdapter(boolean isAllSelect,boolean isAllEdit,boolean isSelectOrEdit){
                   for(int i=0;i<orderLists.size();i++){
                       if(isSelectOrEdit){
                           this.orderLists.get(i).setIs_all_select(isAllSelect);
                           isQuxuan=isAllSelect;



                           OnCheckDefaultListener.onJisuanZongPrice();//计算总价格

                       }else {
                           this.orderLists.get(i).setIs_all_edit(isAllEdit);
                       }



                   }



                    if(!isAllEdit){//完成编辑


                        if (SharedPreUtil.isLogin()) {
                            if (sessionData != null) {

                                        for(int t=0;t<orderLists.size();t++){

                                                initData(sessionData,orderLists.get(t),"update");

                                            //广播通知刷新购物车数量
                                            Intent intent = new Intent();
                                            intent.setAction("UpShopCarNum");
                                            intent.putExtra("update", "ok");
                                            mContxt.sendBroadcast(intent);
                                        }




                            }

                        }else{

                            //广播通知刷新购物车数量
                            Intent intent = new Intent();
                            intent.setAction("UpShopCarNum");
                            intent.putExtra("update", "ok");
                            mContxt.sendBroadcast(intent);

                        }

                    }
                        notifyDataSetChanged();









    }



    public void myNotifiAdapterDelete(){
        if (SharedPreUtil.isLogin()) {
            if (sessionData != null) {
                for(int d=0;d<orderLists.size();d++){
                    if(orderLists.get(d).is_all_select()){
                        initData(sessionData,orderLists.get(d),"delete");
                    }

                }

            }

        }else{
           List<ShopCarOrderInfo.DataEntity.GoodsListEntity> tempDeleteList=new ArrayList<ShopCarOrderInfo.DataEntity.GoodsListEntity>();
            tempDeleteList.clear();
            for(int d=0;d<orderLists.size();d++){
                if(orderLists.get(d).is_all_select()){
                    tempDeleteList.add(orderLists.get(d));
                }

            }
            if(tempDeleteList.size()>0){
                for(int t=0;t<tempDeleteList.size();t++){
                    orderLists.remove(tempDeleteList.get(t));
                    thisNotifyDataSetChanged(tempDeleteList.get(t), true);//保存本地数据
                }

            }


            //广播通知刷新购物车数量
            Intent intent = new Intent();
            intent.setAction("UpShopCarNum");
            intent.putExtra("update", "ok");
            mContxt.sendBroadcast(intent);


            if(orderLists.size()<=0){

                OnCheckDefaultListener.onDeleteAll();
            }


        }


    }
}