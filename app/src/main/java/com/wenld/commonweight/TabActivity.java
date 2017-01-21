package com.wenld.commonweight;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wenld.commonweights.adapterRecy.AdvancedAdapter;
import com.wenld.commonweights.adapterRecy.ItemClickInterface;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2017/1/21.
 */

public class TabActivity extends Activity {
    RecyclerView rlvAty;
    MyAdapter adapter;

    private List<String> mList = Arrays.asList(new String[]{"水波纹百分比加载效果", "录音效果"});

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);


        rlvAty = (RecyclerView) findViewById(R.id.rlv);
        adapter = new MyAdapter();
        adapter.setData(mList);
        rlvAty.setLayoutManager(new LinearLayoutManager(this));
        rlvAty.setAdapter(adapter);
        adapter.setListener(new ItemClickInterface<String>() {
            @Override
            public void onItemClick(String Item, int position) {
                Intent intent = null;
                switch (position) {
                    case 0:
                        intent = new Intent(TabActivity.this, WaveActivity.class);
                        break;
                    case 1:
                        intent = new Intent(TabActivity.this, RecordActivity.class);
                        break;
                }
                startActivity(intent);
            }
        });


        adapter.addHeaderView(LayoutInflater.from(this).inflate(R.layout.layout_header, null));
        adapter.addFooterView(LayoutInflater.from(this).inflate(R.layout.layout_bottom, null));

    }

    public class MyAdapter extends AdvancedAdapter<MyAdapter.Holder, String> {


        @Override
        public void onBindAdvanceViewHolder(Holder holder, int i) {
            holder.tv.setText(mData.get(i));
        }

        @Override
        public RecyclerView.ViewHolder onCreateAdvanceViewHolder(ViewGroup parent, int viewType) {
            return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem, parent, false));
        }

        class Holder extends AdvancedAdapter.ViewHolder {
            public TextView tv;

            public Holder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (listener != null) {
                            listener.onItemClick(mData.get(getAdpPosition()), getAdpPosition());
                        }
                    }
                });
                tv = (TextView) itemView.findViewById(R.id.tv_listitem);
            }
        }
    }
}
