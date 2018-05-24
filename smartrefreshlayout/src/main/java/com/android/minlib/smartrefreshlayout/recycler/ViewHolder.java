package com.android.minlib.smartrefreshlayout.recycler;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Spanned;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ViewHolder extends RecyclerView.ViewHolder {

    private final SparseArray<View> mViews;
    private View mConvertView;
    private View mItemView;

    public ViewHolder(View parent) {
        super(parent);
        this.mConvertView = parent;
        this.mViews = new SparseArray();
    }

    public static ViewHolder get(View convertView, ViewGroup parent) {
        return convertView == null ? new ViewHolder(parent) : (ViewHolder)convertView.getTag();
    }

    public View getConvertView() {
        return this.mConvertView;
    }

    public <T extends View> T getView(int viewId) {
        View view = (View)this.mViews.get(viewId);
        if (view == null) {
            view = this.mConvertView.findViewById(viewId);
            this.mViews.put(viewId, view);
        }
        return (T) view;
    }

    public <T extends View> T getViewAndOnClick(int viewId, View.OnClickListener onClickListener) {
        View view = this.getView(viewId);
        view.setOnClickListener(onClickListener);
        return (T) view;
    }

    public View setOnClickListener(int viewId, View.OnClickListener onClickListener) {
        View view = this.getView(viewId);
        view.setOnClickListener(onClickListener);
        return view;
    }

    public TextView setText(int viewId, String text) {
        TextView view = (TextView)this.getView(viewId);
        view.setText(text);
        return view;
    }

    public TextView setText(int viewId, CharSequence text) {
        TextView view = (TextView)this.getView(viewId);
        if (view != null) {
            view.setText(text);
        }
        return view;
    }

    public TextView setText(int viewId, Spanned text) {
        TextView view = (TextView)this.getView(viewId);
        if (view != null) {
            view.setText(text);
        }
        return view;
    }

    public TextView setTextColor(int viewId, int Color) {
        TextView view = (TextView)this.getView(viewId);
        if (view != null) {
            view.setTextColor(Color);
        }
        return view;
    }

    public TextView setTextAndOnClick(int viewId, String text, View.OnClickListener onClickListener) {
        TextView view = this.setText(viewId, text);
        view.setOnClickListener(onClickListener);
        return view;
    }

    public Button setButtonText(int viewId, String text) {
        Button view = (Button)this.getView(viewId);
        if (view != null) {
            view.setText(text);
        }

        return view;
    }

    public Button setButtonAndOnClick(int viewId, String text, View.OnClickListener onClickListener) {
        Button view = this.setButtonText(viewId, text);
        view.setOnClickListener(onClickListener);
        return view;
    }

    public ImageView setImageResource(int viewId, int drawableId) {
        ImageView view = (ImageView)this.getView(viewId);
        if (view != null) {
            view.setImageResource(drawableId);
        }
        return view;
    }

    public View setBackgroundResource(int viewId, int drawableId) {
        View view = this.getView(viewId);
        if (view != null) {
            view.setBackgroundResource(drawableId);
        }
        return view;
    }

    public ImageView setImageResourceAndOnClick(int viewId, int drawableId, View.OnClickListener onClickListener) {
        ImageView view = this.setImageResource(viewId, drawableId);
        view.setOnClickListener(onClickListener);
        return view;
    }

    public ImageView setImageBitmap(int viewId, Bitmap bm) {
        ImageView view = (ImageView)this.getView(viewId);
        if (view != null) {
            view.setImageBitmap(bm);
        }
        return view;
    }

    public ImageView setImageBitmapAndOnClick(int viewId, Bitmap bm, View.OnClickListener onClickListener) {
        ImageView view = this.setImageBitmap(viewId, bm);
        view.setOnClickListener(onClickListener);
        return view;
    }

    public ImageView setImageByUrl(int defaultImg, int viewId, String url) {
        ImageView view = (ImageView)this.getView(viewId);
        if (this.mConvertView != null) {
            Glide.with(this.mConvertView).load(url).into(view);
        }
        return view;
    }

    public ImageView setImageByUrlAndOnClick(int defaultImg, int viewId, String url, View.OnClickListener onClickListener) {
        ImageView view = this.setImageByUrl(defaultImg, viewId, url);
        view.setOnClickListener(onClickListener);
        return view;
    }

    public RadioButton setRadioChecked(int viewId) {
        RadioButton view = (RadioButton)this.getView(viewId);
        if (view != null) {
            view.setChecked(true);
        }
        return view;
    }

    public TextView setCheckBoxChecked(int viewId, boolean isCheck) {
        CheckBox view = (CheckBox)this.getView(viewId);
        if (view != null) {
            view.setChecked(isCheck);
        }
        return view;
    }

    public View setVisibility(int viewId, int visibility) {
        View view = this.getView(viewId);
        view.setVisibility(visibility);
        return view;
    }

    public int getVisibility(int viewId) {
        View view = this.getView(viewId);
        return view.getVisibility();
    }

    public void setCompoundDrawables(Context context, int viewId, int drawb_left, int drawb_top, int drawb_right, int drawb_bottom) {
        View view = this.getView(viewId);
        if (view != null && view instanceof TextView) {
            ((TextView)view).setCompoundDrawables(this.getTestDrawable(context, drawb_left), this.getTestDrawable(context, drawb_top), this.getTestDrawable(context, drawb_right), this.getTestDrawable(context, drawb_bottom));
        }
    }

    public void setCompoundDrawables(TextView textView, Drawable left, Drawable top, Drawable right, Drawable bottom) {
        if (textView != null) {
            textView.setCompoundDrawables(left, top, right, bottom);
        }
    }

    private Drawable getTestDrawable(Context context, int drawbId) {
        Drawable drawable = null;
        if (drawbId != -1) {
            drawable = ContextCompat.getDrawable(context, drawbId);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        }
        return drawable;
    }

    public Drawable getDrawable(Context context, int drawbId) {
        return ContextCompat.getDrawable(context, drawbId);
    }

    public View getmItemView() {
        return this.mItemView;
    }

    public void setmItemView(View mItemView) {
        this.mItemView = mItemView;
    }
}
