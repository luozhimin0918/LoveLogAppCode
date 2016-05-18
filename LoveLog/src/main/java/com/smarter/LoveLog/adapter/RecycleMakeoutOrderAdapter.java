package com.smarter.LoveLog.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.NetworkImageView;
import com.smarter.LoveLog.R;
import com.smarter.LoveLog.db.AppContextApplication;
import com.smarter.LoveLog.model.orderMy.OrderFlowCheckOut;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/12/22.
 */
public class RecycleMakeoutOrderAdapter extends RecyclerView.Adapter<RecycleMakeoutOrderAdapter.ViewHolder> {


    // 数据集
    List<OrderFlowCheckOut.DataEntity.GoodsListEntity> goodsListEntityList;
    RequestQueue mQueue;

    public RecycleMakeoutOrderAdapter(List<OrderFlowCheckOut.DataEntity.GoodsListEntity> goodsListEntityList,RequestQueue mQueue) {
        super();
        this.goodsListEntityList = goodsListEntityList;
        this.mQueue=mQueue;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // 创建一个View，简单起见直接使用系统提供的布局，就是一个TextView
        View view = View.inflate(viewGroup.getContext(), R.layout.adapter_activity_make_order_item, null);
        // 创建一个ViewHolder
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        // 绑定数据到ViewHolder上
        //产品图片
        viewHolder.ivAdapterGridPic.setDefaultImageResId(R.drawable.loading_small);
        viewHolder.ivAdapterGridPic.setErrorImageResId(R.drawable.loading_small);
        String UserimageUrl = "";
        if (goodsListEntityList.get(i).getImg_thumb() != null) {
            UserimageUrl = goodsListEntityList.get(i).getImg_thumb();
        }

        if (mQueue.getCache().get(UserimageUrl) == null) {
            viewHolder.ivAdapterGridPic.startAnimation(ImagePagerAdapter.getInAlphaAnimation(2000));
        }
        viewHolder.ivAdapterGridPic.setImageUrl(UserimageUrl, AppContextApplication.getInstance().getmImageLoader());


        viewHolder.goodDes.setText(goodsListEntityList.get(i).getGoods_name());
    }

    @Override
    public int getItemCount() {
        return goodsListEntityList.size();
    }




    //回调开始
    public interface OnItemClickListener {
        void onItemClickAdapter(int ischeckArray);
    }

    public static OnItemClickListener setOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onCheckDefault) {
        this.setOnItemClickListener = onCheckDefault;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.iv_adapter_grid_pic)
        NetworkImageView ivAdapterGridPic;
        @Bind(R.id.goodDes)
        TextView goodDes;
        @Bind(R.id.numBer)
        TextView numBer;
        @Bind(R.id.goodsType)
        TextView goodsType;
        @Bind(R.id.shopPrice)
        TextView shopPrice;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setOnItemClickListener.onItemClickAdapter(getPosition());
                    Log.d("RecycleMakeoutAdapter", "当前点击的位置：" + getPosition());
                }
            });
        }
    }


    //结束
}