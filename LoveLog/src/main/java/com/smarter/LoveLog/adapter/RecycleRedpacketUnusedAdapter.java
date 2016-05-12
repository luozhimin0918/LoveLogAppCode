package com.smarter.LoveLog.adapter;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.smarter.LoveLog.R;
import com.smarter.LoveLog.model.redpacket.RedList;

import java.util.List;

/**
 * Created by Administrator on 2015/12/22.
 */
public class RecycleRedpacketUnusedAdapter extends RecyclerView.Adapter<RecycleRedpacketUnusedAdapter.ViewHolder>{





    // 数据集
    List<RedList> redListList;

    public RecycleRedpacketUnusedAdapter(List<RedList> redListList) {
        super();
        this.redListList=redListList;
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
        // 绑定数据到ViewHolder上
       viewHolder.redMoney.setText(redListList.get(i).getAmount().replace(".00","").replace("¥",""));
        viewHolder.manMoney.setText("满"+redListList.get(i).getMin_order_amount().replace(".00","").replace("¥","")+"可用");
        viewHolder.nameRed.setText(redListList.get(i).getName());
        viewHolder.endTime.setText("有效期至："+redListList.get(i).getEnd_time());



    }


    //回调开始
    public interface OnCheckDefaultListener {
        void oncheckOK(Boolean[] ischeckArray);
    }
    private OnCheckDefaultListener OnCheckDefaultListener;

    public void setOnCheckDefaultListener(OnCheckDefaultListener onCheckDefault) {
        this.OnCheckDefaultListener=onCheckDefault;
    }
    //结束
    @Override
    public int getItemCount() {
        return redListList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView redMoney,manMoney,nameRed,endTime;


        public ViewHolder(View itemView) {
            super(itemView);

            redMoney = (TextView) itemView.findViewById(R.id.redMoney);
            manMoney= (TextView) itemView.findViewById(R.id.manMoney);
            nameRed= (TextView) itemView.findViewById(R.id.nameRed);
            endTime= (TextView) itemView.findViewById(R.id.endTime);


        }
    }
}