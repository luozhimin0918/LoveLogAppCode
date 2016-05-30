package com.smarter.LoveLog.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smarter.LoveLog.R;
import com.smarter.LoveLog.activity.MakeOutOrderActivity;
import com.smarter.LoveLog.model.orderMy.OrderFlowCheckOut;
import com.smarter.LoveLog.model.redpacket.RedList;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/12/22.
 */
public class RecycleRedpacketUnusedAdapter extends RecyclerView.Adapter<RecycleRedpacketUnusedAdapter.ViewHolder> {


    // 数据集
    List<RedList> redListList;
    Activity mActivity;
    boolean isOrdeSelectRed;
    String  UseRedId;//已使用红包ID
    List<OrderFlowCheckOut.DataEntity.BonusEntity> BonusEntityRedList=new ArrayList<OrderFlowCheckOut.DataEntity.BonusEntity>();//包含可用红包list

    public RecycleRedpacketUnusedAdapter(List<RedList> redListList, Activity mActivity,boolean isOrdeSelectRed,String UseRedId,OrderFlowCheckOut.DataEntity dataEntityRed) {
        super();
        this.redListList = redListList;
        this.mActivity = mActivity;
        this.isOrdeSelectRed=isOrdeSelectRed;
        this.UseRedId=UseRedId;
        if(dataEntityRed!=null){
            this.BonusEntityRedList=dataEntityRed.getBonus();
        }



    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // 创建一个View，简单起见直接使用系统提供的布局，就是一个TextView
        View view = View.inflate(viewGroup.getContext(), R.layout.adapter_activity_red_packet_unused_item, null);
        // 创建一个ViewHolder
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {

        if(UseRedId!=null&&!UseRedId.equals("")){
            for(int r=0;r<redListList.size();r++){
                if(redListList.get(r).getId().equals(UseRedId)){
                    redListList.get(r).setIsSelect(true);
                }else{
                    redListList.get(r).setIsSelect(false);
                }
            }
            onSelectRedPacketListener.returnRedId(UseRedId, "");

        }




        // 绑定数据到ViewHolder上
        viewHolder.redMoney.setText(redListList.get(i).getAmount().replace(".00", "").replace("¥", ""));
        viewHolder.manMoney.setText("满" + redListList.get(i).getMin_order_amount().replace(".00", "").replace("¥", "") + "可用");
        viewHolder.nameRed.setText(redListList.get(i).getName());
        viewHolder.endTime.setText("有效期至：" + redListList.get(i).getEnd_time());

boolean  kk =false;
        if(isOrdeSelectRed) {
            for (OrderFlowCheckOut.DataEntity.BonusEntity bb : BonusEntityRedList) {
                if (bb.getId().equals(redListList.get(i).getId())) {
                           kk=true;
                    redListList.get(i).setIsCanselect(true);


                }
            }
            if(kk==false){
                redListList.get(i).setIsCanselect(false);
            }



            if(!redListList.get(i).isCanselect()){
                viewHolder.redImage.setImageResource(R.mipmap.red_packet_gray);
                viewHolder.moneyTag.setTextColor(Color.parseColor("#8B8B8B"));
                viewHolder.redMoney.setTextColor(Color.parseColor("#8B8B8B"));
            }else{
                viewHolder.redImage.setImageResource(R.mipmap.red_packet_red);
                viewHolder.moneyTag.setTextColor(Color.parseColor("#fc1359"));
                viewHolder.redMoney.setTextColor(Color.parseColor("#fc1359"));
            }
        }








        if(redListList.get(i).isSelect()){
            viewHolder.isImageTwo.setVisibility(View.VISIBLE);
        }else{
            viewHolder.isImageTwo.setVisibility(View.GONE);
        }








        viewHolder.RedPacketLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(isOrdeSelectRed){




                    if(redListList.get(i).isCanselect()){



                            if(redListList.get(i).isSelect()){
                                redListList.get(i).setIsSelect(false);
                                onSelectRedPacketListener.returnRedId("", "");
                                UseRedId="";
                            }else{
                                for(int r=0;r<redListList.size();r++){
                                    redListList.get(r).setIsSelect(false);
                                }
                                redListList.get(i).setIsSelect(true);
                                UseRedId=redListList.get(i).getId();
                                onSelectRedPacketListener.returnRedId(redListList.get(i).getId(),redListList.get(i).getAmount());
                            }

                            notifyDataSetChanged();

                    }


                }

            }
        });


    }


    //回调开始
    public interface onSelectRedPacketListener {
        void returnRedId(String redId,String redMoney);
    }

    private onSelectRedPacketListener onSelectRedPacketListener;

    public void setOnCheckDefaultListener(onSelectRedPacketListener onSelectRedPacketListener1) {
        this.onSelectRedPacketListener = onSelectRedPacketListener1;
    }

    //结束
    @Override
    public int getItemCount() {
        return redListList.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.redImage)
        ImageView redImage;
        @Bind(R.id.moneyTag)
        TextView moneyTag;
        @Bind(R.id.redMoney)
        TextView redMoney;
        @Bind(R.id.manMoney)
        TextView manMoney;
        @Bind(R.id.nameRed)
        TextView nameRed;
        @Bind(R.id.isImageTwo)
        ImageView isImageTwo;
        @Bind(R.id.isDefaultLinarTow)
        LinearLayout isDefaultLinarTow;
        @Bind(R.id.endTime)
        TextView endTime;
        @Bind(R.id.RedPacketLinear)
        LinearLayout RedPacketLinear;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }




}