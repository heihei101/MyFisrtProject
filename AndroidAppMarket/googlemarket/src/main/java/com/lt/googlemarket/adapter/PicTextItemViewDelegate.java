package com.lt.googlemarket.adapter;

import android.graphics.Color;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lt.googlemarket.JavaBeen.CategoryInfoItem;
import com.lt.googlemarket.R;
import com.lt.googlemarket.utils.ConstantUtil;
import com.lt.googlemarket.utils.Utils;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by Administrator on 2017/6/16.
 */
public class PicTextItemViewDelegate implements ItemViewDelegate {

    @Override
    public int getItemViewLayoutId() {
        return R.layout.category_pic_item;
    }

    @Override
    public boolean isForViewType(Object item, int position) {
        //判断item类型方法
        return item instanceof CategoryInfoItem;
    }

    @Override
    public void convert(ViewHolder holder, Object o, int position) {
        CategoryInfoItem categoryInfoItem = (CategoryInfoItem) o;
        if(!TextUtils.isEmpty(categoryInfoItem.name1) &&
                !TextUtils.isEmpty(categoryInfoItem.url1)){
            //给一个条目中的控件填充数据方法
            holder.setText(R.id.tv_pic_text_1,categoryInfoItem.name1);
            //给每一个分类条目添加图片
            Glide.with(Utils.getContext()).
                    load(ConstantUtil.IMGURL+categoryInfoItem.url1).
                    bitmapTransform(new CropCircleTransformation(Utils.getContext())).
                    into((ImageView) holder.getView(R.id.iv_image_1));
        }else{
            holder.setVisible(R.id.tv_pic_text_1, false);
            holder.setVisible(R.id.iv_image_1, false);
            holder.setBackgroundColor(R.id.ll_category_1, Color.TRANSPARENT);
        }
        if(!TextUtils.isEmpty(categoryInfoItem.name2) &&
                !TextUtils.isEmpty(categoryInfoItem.url2)) {
            holder.setText(R.id.tv_pic_text_2, categoryInfoItem.name2);
            Glide.with(Utils.getContext()).
                    load(ConstantUtil.IMGURL + categoryInfoItem.url2).
                    bitmapTransform(new CropCircleTransformation(Utils.getContext())).
                    into((ImageView) holder.getView(R.id.iv_image_2));
        }else{
            holder.setVisible(R.id.tv_pic_text_2, false);
            holder.setVisible(R.id.iv_image_2, false);
            holder.setBackgroundColor(R.id.ll_category_2, Color.TRANSPARENT);
        }
        if(!TextUtils.isEmpty(categoryInfoItem.name3) &&
                !TextUtils.isEmpty(categoryInfoItem.url3)) {
            holder.setText(R.id.tv_pic_text_3, categoryInfoItem.name3);
            Glide.with(Utils.getContext()).
                    load(ConstantUtil.IMGURL + categoryInfoItem.url3).
                    bitmapTransform(new CropCircleTransformation(Utils.getContext())).
                    into((ImageView) holder.getView(R.id.iv_image_3));
        }else{
           // holder.setText(R.id.tv_pic_text_3,"更多");
            //holder.setImageResource(R.id.iv_image_3,R.mipmap.ic_cancel);
            holder.setVisible(R.id.tv_pic_text_3, false);
            holder.setVisible(R.id.iv_image_3, false);
            holder.setBackgroundColor(R.id.ll_category_3, Color.TRANSPARENT);
        }
    }
}
