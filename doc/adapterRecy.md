# 加头加尾RecyclerView 适配器

#### 使用



| name        | type           | direction  |
| ------------- |:-------------:| :-----:|
| circle_Color      | color|reference | 圆颜色 |
| wave_Color      | color|reference      |  水波纹颜色 |
| text_Color | color|reference      |    文字颜色 |
| text_Size | dimension      |    文字大小 |
| unitText | dimension      |    单位（默认 “%”） |
| unitText_Color | color|reference      |    单位文字颜色 |
| unitText_Size | dimension      |     单位文字大小 |


## void
| name        | void name           | direction  |
| ------------- | ------------- | :-----:|
| 设置百分比      | setPercent(int percent) | |
| 设置文字颜色      | setTextColor(int textColor)      |  |
| text size | setTextSize(float textSize)      |    换算成 px |
| 设置符号颜色 | setUnitTextColor(int unitTextColor)      |     |
| 设置符号大小 | setUnitTextSize(float unitTextSize)      |   换算成 px  |
| 设置符号文字 | setUnitText(String unitText)      |   默认“%” |
| 设置圆圈颜色 | setCircleColor(int circleColor)     |      |
| 设置水波颜色 | setWaveColor(int waveColor)     |      |

## 引用
```java
// 项目引用
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

#Contact me

E-mail:wenld2014@163.com

blog: [wenld's blog](http://blog.csdn.net/sinat_15877283)
