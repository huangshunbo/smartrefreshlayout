#### Summary
---
使用 [SmartRefreshLayout](https://github.com/scwang90/SmartRefreshLayout) 封装的一个下拉刷新库，加入了 [情感图](https://github.com/huangshunbo/reloadtipview),
SmartRecyclerView使用SmartRefreshLayout + RecyclerView + ReloadTipView 封装成一个包含情感图的常用上下拉列表


#### Getting Started
---
加入依赖
```Java
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
  
dependencies {
  implementation 'com.github.huangshunbo:smartrefreshlayout:lastest.release'
}
```
设置全局的情感图style，这部分ReloadTipView那边有详细介绍
```Java
<style name="reloadtip_default_attr" parent="Theme.AppCompat.Light.NoActionBar">
    <item name="tip_txt_size">18dp</item>
    <item name="action_txt_size">15dp</item>
    <item name="tip_txt_color">#333333</item>
    <item name="action_txt_color">#999999</item>
    <item name="tip_txt_topmargin">20dp</item>
    <item name="action_txt_topmargin">15dp</item>
    <item name="content_minheigh">300dp</item>
    <item name="content_topmargin">-30dp</item>

    <item name="notdata_icon">@mipmap/default_no_data</item>
    <item name="notdata_tip_txt">真的暂无数据</item>
    <item name="notdata_actoin_txt">点击刷新</item>
    <item name="notnet_icon">@mipmap/default_no_network</item>
    <item name="notnet_tip_txt">网络不给力，请稍候重试</item>
    <item name="notnet_actoin_txt">点击刷新</item>
    <item name="error_icon">@mipmap/default_no_network</item>
    <item name="error_tip_txt">服务器异常</item>
    <item name="error_actoin_txt">点击刷新</item>
</style>
```

简单的使用
```Java
SmartRecyclerView smartRecyclerView;

smartRecyclerView = findViewById(R.id.smart_recyclerview);
smartRecyclerView.setDiver(5,R.drawable.line_left_margin);
smartRecyclerView.setOnSmartFillListener(new MySmartFillListener());
smartRecyclerView.setMode(SmartRecyclerView.STATE_MODE.BOTH);
smartRecyclerView.loadData();
smartRecyclerView.setRefreshHeader(new MyHeaderView(this));

```
OnSmartFillListener的简单示例
```Java
boolean flag = false;
class MySmartFillListener implements OnSmartFillListener<TestBean> {
    @Override
    public void onLoadData(final int taskId, int pageIndex) {
        smartRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                List<TestBean> list = new ArrayList<>();
                if(flag){
                    list.add(new TestBean("HUANGSHUNBO",27,true));
                    list.add(new TestBean("HUANGSHUNBO",27,true));
                    list.add(new TestBean("HUANGSHUNBO",27,true));
                }
                flag = true;
                smartRecyclerView.showData(taskId,list,20);
            }
        },2000);
    }
    @Override
    public void clickItem(int viewId, TestBean item, int position) {
        Toast.makeText(MainActivity.this, "item click : " + position, Toast.LENGTH_SHORT).show();
    }
    @Override
    public int createLayout() {
        return R.layout.item_recycler;
    }
    @Override
    public void createListItem(int viewId, ViewHolder holder, TestBean currentItem, List<TestBean> list, int position) {
        holder.setText(R.id.item_recycler_title,currentItem.getName());
        holder.setText(R.id.item_recycler_subtitle,""+currentItem.getAge());
    }
    @Override
    public void onLastPageHint() {
    }
}
```
PS:loadData会调用onLoadData，建议在onCreate后使用loadData来触发onLoadData来获取第一批数据。SmartRecyclerView用showData来提供需要显示的数据，最后一个参数需要传入整体的数据量条数。


一个丑不拉吉的自定义Header示例，Footer也类似(extends InternalAbstract implements RefreshFooter)
```Java
public class MyHeaderView extends InternalAbstract implements RefreshHeader {
    private static final String TAG = "MyHeaderView";
    TextView textView;
    ObjectAnimator animator;
    protected MyHeaderView(Context context) {
        super(context,null,0);
        textView = new TextView(context);
        textView.setTextColor(Color.RED);
        textView.setTextSize(DensityUtil.dp2px(18));
        textView.setText("Let's begin");
        textView.setRotation(0);
        addView(textView);
    }
    /**
     *动画绘制，需要手动绘制的话就是在这里了
     * */
    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
    }
    /**
     * 测量完成后回调
     */
    @Override
    public void onInitialized(@NonNull RefreshKernel kernel, int height, int maxDragHeight) {
        super.onInitialized(kernel, height, maxDragHeight);
    }
    /**
     * 移动回调，下拉或回弹均会回调
     * */
    @Override
    public void onMoving(boolean isDragging, float percent, int offset, int height, int maxDragHeight) {
        super.onMoving(isDragging, percent, offset, height, maxDragHeight);
        Log.d(TAG,"onMoving  " + height);
    }
    /**
     * 拖到最大距离后释放
     * */
    @Override
    public void onReleased(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {
        super.onReleased(refreshLayout, height, maxDragHeight);
        Log.d(TAG,"onReleased");
        textView.setText("onRelease");
        textView.setPadding(0,0,0,0);
    }
    /**
     * 拖到最大高度，请开始你的动画表演
     * */
    @Override
    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {
        super.onStartAnimator(refreshLayout, height, maxDragHeight);
        Log.d(TAG,"onStartAnimator");
        textView.setText("~~~Les's danceing~~~");
        textView.setPadding(0,0,0,0);
        animator = ObjectAnimator.ofFloat(textView, "rotation", 0f, 360f);
        animator.setDuration(5000);
        animator.setRepeatCount(10);
        animator.start();
    }
    /**
     * 结束，复原
     * */
    @Override
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean success) {
        Log.d(TAG,"onFinish");
        textView.setTextColor(Color.RED);
        textView.setTextSize(DensityUtil.dp2px(18));
        textView.setText("Let's begin");
        textView.setRotation(0);
        if(animator != null){
            animator.cancel();
        }
        return super.onFinish(refreshLayout, success);
    }
    /**
     * 对象销毁处
     * */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        animator = null;
    }
    /**
     * 动画是否支持横向滑动
     * */
    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }
}
```
#### Known Issues
---
暂时没有收到任何反馈，有任何疑问或需求，可提issue。
#### Support
---
黄顺波