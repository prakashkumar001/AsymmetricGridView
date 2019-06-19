package com.felipecsl.asymmetricgridview.app.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.felipecsl.asymmetricgridview.app.R;
import com.felipecsl.asymmetricgridview.app.model.DemoItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Sample adapter implementation extending from AsymmetricGridViewAdapter<DemoItem> This is the
 * easiest way to get started.
 */
public class DefaultListAdapter extends ArrayAdapter<DemoItem>  {

  private final LayoutInflater layoutInflater;
  Context context;
  private int threshold = 0;
  List<DemoItem> items;

  public DefaultListAdapter(Context context, List<DemoItem> items) {
    super(context, 0, items);
    layoutInflater = LayoutInflater.from(context);
    this.context=context;
    this.items=items;

  }

  public DefaultListAdapter(Context context) {
    super(context, 0);
    this.context=context;
    layoutInflater = LayoutInflater.from(context);
  }

  @Override
  public View getView(int position, View convertView, @NonNull ViewGroup parent) {
    ViewHolder holder;


    DemoItem item = getItem(position);

    if (convertView == null) {
      convertView = layoutInflater.inflate(R.layout.adapter_item , parent, false);
      holder = new ViewHolder();
      holder.imageView = (ImageView) convertView.findViewById(R.id.textview);
      convertView.setTag(holder);

    } else {
      holder = (ViewHolder) convertView.getTag();
    }



    Glide.with(context)
            .load(item.getImageUrl())
            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
            .centerCrop()
            .placeholder(R.drawable.ic_launcher)
            .into(holder.imageView);

    //textView.setText(String.valueOf(item.getPosition()));

    return convertView;
  }

  @Override public int getViewTypeCount() {
    return 2;
  }

  @Override public int getItemViewType(int position) {
    return position ;
  }

  public void appendItems(List<DemoItem> newItems) {
    items.addAll(newItems);
    this.notifyDataSetChanged();
  }

  public void setItems(List<DemoItem> moreItems) {
    clear();
    appendItems(moreItems);
  }

  public void clearList() {
    items = new ArrayList<>();
    notifyDataSetChanged();
  }

  static class ViewHolder {
    private ImageView imageView;
  }
}