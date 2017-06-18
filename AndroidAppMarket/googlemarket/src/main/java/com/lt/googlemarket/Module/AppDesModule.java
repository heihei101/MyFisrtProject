package com.lt.googlemarket.Module;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lt.googlemarket.JavaBeen.AppInfo;
import com.lt.googlemarket.R;
import com.lt.googlemarket.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/18.
 */

public class AppDesModule extends BaseModule<AppInfo>{
    private ScrollView scrollView;

    @BindView(R.id.tv_des)
    TextView tvDes;
    @BindView(R.id.tv_author)
    TextView tvAuthor;
    @BindView(R.id.iv_arrow)
    ImageView ivArrow;
    private AppInfo appInfo;
    private boolean isExpend = false;
    private LinearLayout.LayoutParams layoutParams;

    public AppDesModule(Context ctx, ScrollView scrollView) {
        super(ctx);
        this.scrollView = scrollView;
    }

    @Override
    public View initView() {
        View view = Utils.inflact(R.layout.layout_app_des);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void bindData(AppInfo appInfo) {
        this.appInfo = appInfo;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvDes.setText(Html.fromHtml(appInfo.des,0));
        }else{
            tvDes.setText(Html.fromHtml(appInfo.des));
        }
        tvAuthor.setText(appInfo.author);

        //让TextView只显示7行,收缩时候的高度
        int shortHeight = getShortHeight();
        layoutParams = (LinearLayout.LayoutParams) tvDes.getLayoutParams();
        layoutParams.height = shortHeight;
        tvDes.setLayoutParams(layoutParams);
        //扩展和收缩
        ivArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expendsOrBack();
            }
        });
    }

    private void expendsOrBack() {
        int shortHeight = getShortHeight();
        int longHeight = getLongHeight();

        ValueAnimator valueAnimator = null;
        if (isExpend){
            //点击前扩展,点击后收缩
            if (longHeight>shortHeight){
                valueAnimator = ValueAnimator.ofInt(longHeight, shortHeight);
                isExpend = false;
            }
        }else{
            //点击前收缩,点击后扩展
            if (shortHeight<longHeight){
                valueAnimator = ValueAnimator.ofInt(shortHeight,longHeight);
                isExpend = true;
            }
        }
        if (valueAnimator!=null){
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    layoutParams.height = (int) animation.getAnimatedValue();
                    //将变化后的属性,作用在控件上
                    tvDes.setLayoutParams(layoutParams);
                }
            });
            valueAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    if (isExpend){
                        //扩展,箭头朝上
                        ivArrow.setImageResource(R.mipmap.arrow_up);
                    }else{
                        //收缩,箭头朝下
                        ivArrow.setImageResource(R.mipmap.arrow_down);
                    }
                    //如果文本显示不全,让TextView滚动起来,屏幕触底的时候滚动起来
                    scrollView.fullScroll(ScrollView.FOCUS_DOWN);
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

    /**
     * 模拟一个TextView
     *      让TextView的宽度和tvDes一样宽
     *      让TextView的高度要不7行的高度,要不显示500个像素作为高度
     *      显然 7行的高度<500个像素,则取7行作为控件高度
     *      将7行的高度,测量出来,将其作为tvDes的高度大小
     * @return
     */
    private int getShortHeight() {
        //1.创建一个TextView让其填充tvDes中的文本内容,但是只能填充7行
        TextView textView = new TextView(Utils.getContext());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            textView.setText(Html.fromHtml(appInfo.des,0));
        }else{
            textView.setText(Html.fromHtml(appInfo.des));
        }
        //限制只显示7行
        textView.setLines(7);
        //让textView最大的行数也是7行
        textView.setMaxLines(7);
        //让tvDes的宽高和textView的宽高保持一致,tvDes也就显示7行

        //获取textView测量后得到的宽度,宽度等同于填充满手机的横屏
        int width = tvDes.getMeasuredWidth();
        //创建指定一个控件宽度和高度的32位数
        int width32 = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        //500是高度的限制条件
        int height32 = View.MeasureSpec.makeMeasureSpec(500,View.MeasureSpec.AT_MOST);

        //让宽高的32位数,都作用在textView上
        textView.measure(width32,height32);

        //再一次测量tvDes在自己定义后的高度大小
        int shortHeight = textView.getMeasuredHeight();
        return shortHeight;
    }

    private int getLongHeight() {
        //1.创建一个TextView让其填充tvDes中的文本内容,但是只能填充7行
        TextView textView = new TextView(Utils.getContext());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            textView.setText(Html.fromHtml(appInfo.des,0));
        }else{
            textView.setText(Html.fromHtml(appInfo.des));
        }

        //获取textView测量后得到的宽度,宽度等同于填充满手机的横屏
        int width = tvDes.getMeasuredWidth();
        //创建指定一个控件宽度和高度的32位数
        int width32 = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        //500是高度的限制条件
        int height32 = View.MeasureSpec.makeMeasureSpec(500,View.MeasureSpec.AT_MOST);

        //让宽高的32位数,都作用在textView上
        textView.measure(width32,height32);

        //再一次测量tvDes在自己定义后的高度大小
        int shortHeight = textView.getMeasuredHeight();
        return shortHeight;
    }
}

