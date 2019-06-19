package com.felipecsl.asymmetricgridview.app;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.felipecsl.asymmetricgridview.AsymmetricGridView;
import com.felipecsl.asymmetricgridview.AsymmetricGridViewAdapter;
import com.felipecsl.asymmetricgridview.Utils;
import com.felipecsl.asymmetricgridview.app.model.DemoItem;
import com.felipecsl.asymmetricgridview.app.model.PostListResponse;
import com.felipecsl.asymmetricgridview.app.volley.NetWorkResultListener;
import com.felipecsl.asymmetricgridview.app.volley.VolleyRequestResponse;
import com.felipecsl.asymmetricgridview.app.widget.DefaultListAdapter;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private static final boolean USE_CURSOR_ADAPTER = false;
    private AsymmetricGridView listView;
    private DefaultListAdapter adapter;
    private DrawerLayout drawerLayout;
    private int mPage = 1;
    private boolean hasLoadedAllItems = false;
    private boolean isLoading = false;
    private int totalCount = 0;
    private int lastVisiblePosition = 0;
    Dialog pDialog;
    List<DemoItem> items = new ArrayList<>();
    AsymmetricGridViewAdapter asymmetricGridViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (AsymmetricGridView) findViewById(R.id.listView);

        //items= moreItems(18);
        adapter = new DefaultListAdapter(this, items);
        callApi();


        listView.setRequestedColumnCount(3);
        listView.setRequestedHorizontalSpacing(Utils.dpToPx(this, 3));
        listView.setAdapter(getNewAdapter());
        listView.setDebugging(true);
        listView.setOnItemClickListener(this);

        listView.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                Log.e("totalCount", "totalCount" + totalCount);
                Log.e("totalItemsCount", "totalItemsCount" + adapter.getCount());

                if(totalCount>adapter.getCount())
                {
                    mPage = mPage + 1;

                    callApi();

                }

               /* //items.addAll(moreItems(18));
                Handler handler= new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.appendItems(moreItems(18));

                    }
                },1000);
*/

                // or loadNextDataFromApi(totalItemsCount);
                return true; // ONLY if more data is actually being loaded; false otherwise.
            }
        });



      /*  if (!isLoading) {
            adapter.clearList();
            items=moreItems(50);
        }

        hasLoadedAllItems = false;*/


    }



    private AsymmetricGridViewAdapter getNewAdapter() {
        asymmetricGridViewAdapter = new AsymmetricGridViewAdapter(this, listView, adapter);
        // return new AsymmetricGridViewAdapter(this, listView, adapter);

        return asymmetricGridViewAdapter;
    }


    @Override
    public void onItemClick(@NonNull AdapterView<?> parent, @NonNull View view,
                            int position, long id) {
        Toast.makeText(this, "Item " + position + " clicked", Toast.LENGTH_SHORT).show();
    }






    /*public List<DemoItem> moreItems(int qty) {
        List<DemoItem> items = new ArrayList<>();
        int left = 0;
        int right = 10;
        for (int i = 0; i < qty; i++) {

            if(i==left)
            {
                // Swap the next 2 lines to have items with variable
                // column/row span.
                // int rowSpan = Math.random() < 0.2f ? 2 : 1;
                DemoItem item = new DemoItem(2, 2);
                items.add(item);

                left += 18;

            }else if(i==right)
            {

                //int colSpan = Math.random() < 0.2f ? 2 : 1;
                // Swap the next 2 lines to have items with variable
                // column/row span.
                // int rowSpan = Math.random() < 0.2f ? 2 : 1;
                // int rowSpan = colSpan;
                DemoItem item = new DemoItem(2, 2);
                items.add(item);
                right += 18;

            }else
            {

                // int colSpan = Math.random() < 0.2f ? 2 : 1;
                // Swap the next 2 lines to have items with variable
                // column/row span.
                // int rowSpan = Math.random() < 0.2f ? 2 : 1;
                // int rowSpan = colSpan;
                DemoItem item = new DemoItem(1, 1);
                items.add(item);
            }

        }



        return items;
    }*/


    public void callApi() {
        Log.e("Times","Times"+mPage);
        showProgressDialog();
        final ArrayList<DemoItem> sampleData=new ArrayList<>();
        String url = "https://starstalk-dev.zencode.guru/posts/?get_all_posts&page=" + mPage;
        VolleyRequestResponse.getInstance().apiCall(this, url, new JSONObject(), "GET", "Token 2f404fac5414115fb7ee0908740b2f342b947ba2", new NetWorkResultListener() {
            @Override
            public void onSuccess(String response, String requestType) {

                hideProgressDialog();
                PostListResponse postListResponse = new Gson().fromJson(response, PostListResponse.class);
                totalCount=postListResponse.getData().getCount();
                Log.e("Response", "Response" + postListResponse.getData().getList().toString());
                int left = 0;
                int right = 10;
                for (int i = 0; i < postListResponse.getData().getList().size(); i++) {


                    if (i == left) {
                        // Swap the next 2 lines to have items with variable
                        // column/row span.
                        // int rowSpan = Math.random() < 0.2f ? 2 : 1;
                        if(postListResponse.getData().getList().get(i).getPost_attachments().size()>0)
                        {
                            DemoItem item = new DemoItem(2, 2, postListResponse.getData().getList().get(i).getPost_attachments().get(0).getUrl());
                            sampleData.add(item);

                        }else
                        {
                            DemoItem item = new DemoItem(1, 1, "");
                            sampleData.add(item);
                        }

                        left += 18;

                    } else if (i == right) {

                        //int colSpan = Math.random() < 0.2f ? 2 : 1;
                        // Swap the next 2 lines to have items with variable
                        // column/row span.
                        // int rowSpan = Math.random() < 0.2f ? 2 : 1;
                        // int rowSpan = colSpan;
                        if(postListResponse.getData().getList().get(i).getPost_attachments().size()>0)
                        {
                            DemoItem item = new DemoItem(2, 2, postListResponse.getData().getList().get(i).getPost_attachments().get(0).getUrl());
                            sampleData.add(item);

                        }else {
                            DemoItem item = new DemoItem(1, 1, "");
                            sampleData.add(item);
                        }
                        right += 18;

                    } else {

                        // int colSpan = Math.random() < 0.2f ? 2 : 1;
                        // Swap the next 2 lines to have items with variable
                        // column/row span.
                        // int rowSpan = Math.random() < 0.2f ? 2 : 1;
                        // int rowSpan = colSpan;
                        if(postListResponse.getData().getList().get(i).getPost_attachments().size()>0)
                        {
                            DemoItem item = new DemoItem(1, 1, postListResponse.getData().getList().get(i).getPost_attachments().get(0).getUrl());
                            sampleData.add(item);
                        }else
                        {
                            DemoItem item = new DemoItem(1, 1, "");
                            sampleData.add(item);
                        }

                    }

                }


                adapter.appendItems(sampleData);

                //adapter.notifyDataSetChanged();


            }

            @Override
            public void onFailure(String requestType, String error) {
                hideProgressDialog();

            }
        });
    }


    // Progress Dialog
    private void showProgressDialog() {

        pDialog =new Dialog(MainActivity.this);
        pDialog.setContentView(R.layout.dialog);
        pDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pDialog.setCancelable(false);

        Window window = pDialog.getWindow();
        window.setLayout(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        pDialog.show();


    }

    void hideProgressDialog() {

        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
        }
    }
}
