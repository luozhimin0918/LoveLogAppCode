package com.smarter.LoveLog.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.smarter.LoveLog.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/12/22.
 */
public class RecycleMakeoutMoneyAdapter extends RecyclerView.Adapter<RecycleMakeoutMoneyAdapter.ViewHolder> {


    // 数据集
    List<String> moneyAdapData;
    List<String> moneyAdapDataValue;

    public RecycleMakeoutMoneyAdapter(List<String> moneyAdapData, List<String> moneyAdapDataValue) {
        super();
        this.moneyAdapData = moneyAdapData;
        this.moneyAdapDataValue = moneyAdapDataValue;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // 创建一个View，简单起见直接使用系统提供的布局，就是一个TextView
        View view = View.inflate(viewGroup.getContext(), R.layout.adapter_activity_make_order_money_item, null);
        // 创建一个ViewHolder
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        // 绑定数据到ViewHolder上
        //产品图片
        viewHolder.listItemMoneyName.setText(moneyAdapData.get(i));
        viewHolder.moneyJin.setText(moneyAdapDataValue.get(i));

    }

    @Override
    public int getItemCount() {
        return moneyAdapData.size();
    }


    //回调开始
    public interface OnItemClickListener {
        void onItemClickAdapter(String ischeckArray);
    }

    public static OnItemClickListener setOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onCheckDefault) {
        this.setOnItemClickListener = onCheckDefault;
    }

   public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.list_item_moneyName)
        TextView listItemMoneyName;
        @Bind(R.id.moneyJin)
        TextView moneyJin;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }



    //结束
}