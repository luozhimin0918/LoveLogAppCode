package com.smarter.LoveLog.adapter;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.hhl.library.FlowTagLayout;
import com.hhl.library.OnTagSelectListener;
import com.smarter.LoveLog.R;
import com.smarter.LoveLog.db.AppContextApplication;
import com.smarter.LoveLog.model.orderMy.OrderFlowCheckOut;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/12/22.
 */
public class RecyclePayDistModeAdapter extends RecyclerView.Adapter<RecyclePayDistModeAdapter.ViewHolder> {


    // 数据集

    List<OrderFlowCheckOut.DataEntity.PaymentTypeEntity> paymentTypeEntityList = new ArrayList<OrderFlowCheckOut.DataEntity.PaymentTypeEntity>();
    List<OrderFlowCheckOut.DataEntity.ShippingListEntity> shippingListEntityList = new ArrayList<OrderFlowCheckOut.DataEntity.ShippingListEntity>();
    List<Integer> resoId=new ArrayList<Integer>();
    List<String> methodList=new ArrayList<String>();
    RequestQueue mQueue;
    Context mContext;

    OrderFlowCheckOut.DataEntity.PaymentTypeEntity paymentTypeEntity;
    OrderFlowCheckOut.DataEntity.ShippingListEntity shippingListEntity;
    OrderFlowCheckOut.DataEntity  dataEntityAdpter;


    public RecyclePayDistModeAdapter(OrderFlowCheckOut.DataEntity dataEntity, Context mContext) {
        super();
        mQueue = AppContextApplication.getInstance().getmRequestQueue();

        resoId.add(R.mipmap.wait_money_icon_paymothod);
        resoId.add(R.mipmap.wait_money_icon_kuaidi);

        methodList.add("支付方式");
        methodList.add("配送方式");

        dataEntityAdpter=dataEntity;
        paymentTypeEntityList=dataEntityAdpter.getPayment_type();
        shippingListEntityList=dataEntityAdpter.getShipping_list();
        this.mContext = mContext;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // 创建一个View，简单起见直接使用系统提供的布局，就是一个TextView
        View view = View.inflate(viewGroup.getContext(), R.layout.adapter_activity_pay_dist_item, null);
        // 创建一个ViewHolder
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        // 绑定数据到ViewHolder上

        viewHolder.imageTitle.setBackgroundResource(resoId.get(i));
        viewHolder.payMethodText.setText(methodList.get(i));

        TagAdapter<String> mSizeTagAdapter = new TagAdapter<String>(mContext);

        List<String> dataSource = new ArrayList<String>();





        viewHolder.sizeFlowLayout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
        viewHolder.sizeFlowLayout.setAdapter(mSizeTagAdapter);
        viewHolder.sizeFlowLayout.setOnTagSelectListener(new OnTagSelectListener() {
            @Override
            public void onItemSelect(FlowTagLayout parent, List<Integer> selectedList) {
                if (selectedList != null && selectedList.size() > 0) {
                    StringBuilder sb = new StringBuilder();
                    for (int s : selectedList) {
                        sb.append(parent.getAdapter().getItem(s));
                        sb.append(":");
                        if (i == 0) {

                            for (OrderFlowCheckOut.DataEntity.PaymentTypeEntity p : paymentTypeEntityList) {
                                p.setIsSelceteItem(false);
                            }
                            paymentTypeEntityList.get(s).setIsSelceteItem(true);
                            paymentTypeEntity=paymentTypeEntityList.get(s);//保存选择值
                            viewHolder.PaydesInfo.setText(paymentTypeEntityList.get(s).getDesc());
//                            Log.d("nnnnn", sb.toString() + "      " + paymentTypeEntityList.get(s).isSelceteItem() + "  " + paymentTypeEntityList.get(s).getName());

                        }


                        if (i == 1) {
                            for (OrderFlowCheckOut.DataEntity.ShippingListEntity sh : shippingListEntityList) {
                                sh.setIsSelceteItem(false);
                            }
                            shippingListEntityList.get(s).setIsSelceteItem(true);
                            shippingListEntity=shippingListEntityList.get(s);//保存选择值
                            viewHolder.PaydesInfo.setText(shippingListEntityList.get(s).getShipping_desc());

//                            Log.d("nnnnn", sb.toString() + "      " + shippingListEntityList.get(s).isSelceteItem() + "  " + shippingListEntityList.get(s).getShipping_name());

                        }

                    }


//                    Toast.makeText(mContext, "已选:" + sb.toString(),Toast.LENGTH_SHORT).show();
//                    Snackbar.make(parent, "已选:" + sb.toString(), Snackbar.LENGTH_LONG).setAction("Action", null).show();
                } else {
//                    Snackbar.make(parent, "没有选择", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    Toast.makeText(mContext, "没有选择", Toast.LENGTH_SHORT).show();
                }
            }
        });


        int  xiabao=-1;
        if(i==0){
            if(paymentTypeEntityList!=null&&paymentTypeEntityList.size()>0){
                for(int p=0;p<paymentTypeEntityList.size();p++){
                    dataSource.add(paymentTypeEntityList.get(p).getName());
                    if(paymentTypeEntityList.get(p).isSelceteItem()){
                      paymentTypeEntity=paymentTypeEntityList.get(p);//保存默认值
                        xiabao=p;
                    }
                }
                if(xiabao!=-1){
                    viewHolder.PaydesInfo.setText(paymentTypeEntityList.get(xiabao).getDesc());
                }


            }
        }

        if(i==1){
            if(shippingListEntityList!=null&&shippingListEntityList.size()>0){
                for(int s=0;s<shippingListEntityList.size();s++){
                    dataSource.add(shippingListEntityList.get(s).getShipping_name());
                    if(shippingListEntityList.get(s).isSelceteItem()){
                        shippingListEntity=shippingListEntityList.get(s);//保存默认值
                        xiabao=s;
                    }
                }
                if(xiabao!=-1) {
                    viewHolder.PaydesInfo.setText(shippingListEntityList.get(xiabao).getShipping_desc());
                }

            }
        }

        mSizeTagAdapter.onlyAddAll(dataSource);



            viewHolder.sizeFlowLayout.reloadData(xiabao);//0表示第几个默选








    }


    @Override
    public int getItemCount() {
        return resoId.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.imageTitle)
        ImageView imageTitle;
        @Bind(R.id.payMethodText)
        TextView payMethodText;
        @Bind(R.id.size_flow_layout)
        FlowTagLayout sizeFlowLayout;
        @Bind(R.id.PaydesInfo)
        TextView PaydesInfo;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public OrderFlowCheckOut.DataEntity.PaymentTypeEntity getPaymentTypeEntity() {
        return paymentTypeEntity;
    }

    public void setPaymentTypeEntity(OrderFlowCheckOut.DataEntity.PaymentTypeEntity paymentTypeEntity) {
        this.paymentTypeEntity = paymentTypeEntity;
    }

    public OrderFlowCheckOut.DataEntity.ShippingListEntity getShippingListEntity() {
        return shippingListEntity;
    }

    public void setShippingListEntity(OrderFlowCheckOut.DataEntity.ShippingListEntity shippingListEntity) {
        this.shippingListEntity = shippingListEntity;
    }

    public OrderFlowCheckOut.DataEntity getDataEntityAdpter() {
        return dataEntityAdpter;
    }

    public void setDataEntityAdpter(OrderFlowCheckOut.DataEntity dataEntityAdpter) {
        this.dataEntityAdpter = dataEntityAdpter;
    }
}