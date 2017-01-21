# 加头加尾RecyclerView 适配器

## 使用
```java

public class MyAdapter extends AdvancedAdapter<MyAdapter.Holder, String> {

        @Override
        public void onBindAdvanceViewHolder(Holder holder, int i) {
            holder.tv.setText(mData.get(i));
        }

        @Override
        public Holder onCreateAdvanceViewHolder(ViewGroup parent, int viewType) {
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

            @Override
            public int getAdpPosition() {
                return getAdapterPosition()-getmHeaderViews();
            }
        }
    }
```

| name        | type           | direction  |
| ------------- |:-------------:| :-----:|
| addHeaderView(View headerView)      |  |  |
|  addFooterView(View footerView)      |  |  |
|void setListener(ItemClickInterface<M> listener)      |  |  |
#Contact me

E-mail:wenld2014@163.com

blog: [wenld's blog](http://blog.csdn.net/sinat_15877283)
