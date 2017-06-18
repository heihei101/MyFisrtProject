package com.lt.googlemarket.Module;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lt.googlemarket.JavaBeen.AppInfo;
import com.lt.googlemarket.R;
import com.lt.googlemarket.utils.ConstantUtil;
import com.lt.googlemarket.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/18.
 */

public class SafeModule extends BaseModule <AppInfo>{
    @BindView(R.id.iv_image1)
    ImageView ivImage1;
    @BindView(R.id.iv_image2)
    ImageView ivImage2;
    @BindView(R.id.iv_image3)
    ImageView ivImage3;
    @BindView(R.id.iv_arrow)
    ImageView ivArrow;
    @BindView(R.id.iv_des1)
    ImageView ivDes1;
    @BindView(R.id.tv_des1)
    TextView tvDes1;
    @BindView(R.id.ll1)
    LinearLayout ll1;
    @BindView(R.id.iv_des2)
    ImageView ivDes2;
    @BindView(R.id.tv_des2)
    TextView tvDes2;
    @BindView(R.id.ll2)
    LinearLayout ll2;
    @BindView(R.id.iv_des3)
    ImageView ivDes3;
    @BindView(R.id.tv_des3)
    TextView tvDes3;
    @BindView(R.id.ll3)
    LinearLayout ll3;
    @BindView(R.id.ll_safe)
    LinearLayout llSafe;
    private ImageView[] safeImage;
    private LinearLayout[] linearLayouts;
    private ImageView[] imageViewDes;
    private TextView[] textViews;
    //记录扩展或者收缩状态  false 代表收缩  true 代表扩展
    private boolean isExpend = false;
    private LinearLayout.LayoutParams layoutParams;
    public SafeModule(Context ctx) {
        super(ctx);
        //放置安全,官方,无广告的imageView数组集合
        safeImage = new ImageView[]{ivImage1,ivImage2,ivImage3};
        //放置线性布局数组的集合
        linearLayouts = new LinearLayout[]{ll1, ll2, ll3};
        //维护正确还是错误图片的数组
        imageViewDes = new ImageView[]{ivDes1, ivDes2, ivDes3};
        //维护描述信息的数组
        textViews = new TextView[]{tvDes1, tvDes2, tvDes3};
    }

    @Override
    public View initView() {
        View view = Utils.inflact(R.layout.layout_app_safe);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void bindData(AppInfo appInfo) {
        for (int i = 0; i <3; i++) {
            if(i<appInfo.safe.size()){
                //  i为0 size为1  则有如下情况 0<1
                //  i为0 为1  appInfo.safe.size()为2    0<2  1<2
                //  i为0 为1  为2  appInfo.safe.size()为3    0<3  1<3  2<3
                //确认第一个顶部的ImageView可以展示
                safeImage[i].setVisibility(View.VISIBLE);
                linearLayouts[i].setVisibility(View.VISIBLE);
                textViews[i].setText(appInfo.safe.get(i).safeDes);
                //三级加载图片,显示的是绿色小图标
                Glide.with(Utils.getContext()).
                        load(ConstantUtil.IMGURL+appInfo.safe.get(i).safeUrl).into(safeImage[i]);
                Glide.with(Utils.getContext())
                        .load(ConstantUtil.IMGURL+appInfo.safe.get(i).safeDesUrl)
                        .into(imageViewDes[i]);
            }else{
                safeImage[i].setVisibility(View.GONE);
                linearLayouts[i].setVisibility(View.GONE);
            }
        }
        getShortHeight();
        ivArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //提供方法,扩展或者收缩
                expendOrBack();
            }
        });
    }
    /*写两个方法,一个是回收时的高度,一个是展开的高度*/
    public int getLongHeight(){
        //系统测量
        llSafe.measure(0,0);
        //测量出来的高度获取出来
        return llSafe.getMeasuredHeight();
    }
    public void getShortHeight(){
        layoutParams = (LinearLayout.LayoutParams) llSafe.getLayoutParams();
        layoutParams.height=0;//对高度数据进行设置
        //将修改后的属性值,作用在线性布局的控件llSafe上
        llSafe.setLayoutParams(layoutParams);
    }
    private void expendOrBack() {
        int height = getLongHeight();
        ValueAnimator valueAnimator = null;
        if(isExpend){//判断当前是展开还是收缩状态
            isExpend = false;
            valueAnimator = ValueAnimator.ofInt(height,0);
        }else{
            isExpend = true;
            //点击前是收缩的,点击后就需要扩展
            valueAnimator = ValueAnimator.ofInt(0, height);
        }
        //对动画的更新(控件高度的变化),进行一个监听
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
               int temp = (int) animation.getAnimatedValue();
                layoutParams.height = temp;
                System.out.println(temp);
                llSafe.setLayoutParams(layoutParams);
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (isExpend){
                    //isExpend 扩展状态,箭头朝上
                    ivArrow.setImageResource(R.mipmap.arrow_up);
                }else{
                    //isExpend 扩展状态,箭头朝下
                    ivArrow.setImageResource(R.mipmap.arrow_down);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        valueAnimator.setDuration(200);
        valueAnimator.start();
    }
}
