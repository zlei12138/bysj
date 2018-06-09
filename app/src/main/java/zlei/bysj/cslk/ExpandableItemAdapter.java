package zlei.bysj.cslk;

import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * Created by luoxw on 2016/8/9.
 */
public class ExpandableItemAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    private static final String TAG = ExpandableItemAdapter.class.getSimpleName();

    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_ROADDATA = 2;
    String roadNames[] = new String[] {
            "崇文路路段：","东水门大桥路段：","南滨路路段：","重庆长江大桥路段：","菜园坝大桥路段：",
            "真武山隧道路段：","腾龙大道路段：","通江大道路段：","丁香路路段：","北滨二路路段：",
            "北滨一路路段：","渝鲁大道路段：","海尔路路段：","沙滨路路段：","经纬大道路段：",
            "紫荆路路段：","金龙路路段：","巴县大道路段：","大江东路路段：","朝天门大桥路段："
    };

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public ExpandableItemAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_LEVEL_0, R.layout.item_expandable_lv0);
        addItemType(TYPE_ROADDATA, R.layout.item_expandable_lv2);
    }



    @Override
    protected void convert(final BaseViewHolder holder, final MultiItemEntity item) {
        switch (holder.getItemViewType()) {
            case TYPE_LEVEL_0:
                switch (holder.getLayoutPosition() %
                        3) {
                    case 0:
                        holder.setImageResource(R.id.iv_head, R.mipmap.head_img0);
                        break;
                    case 1:
                        holder.setImageResource(R.id.iv_head, R.mipmap.head_img1);
                        break;
                    case 2:
                        holder.setImageResource(R.id.iv_head, R.mipmap.head_img2);
                        break;
                }
                final Level0Item lv0 = (Level0Item)item;
                holder.setText(R.id.title, lv0.title)
                        .setText(R.id.sub_title, lv0.subTitle)
                        .setImageResource(R.id.iv, lv0.isExpanded() ? R.mipmap.arrow_b : R.mipmap.arrow_r);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getAdapterPosition();
                        if (lv0.isExpanded()) {
                            collapse(pos);
                        } else {
//                            if (pos % 3 == 0) {
//                                expandAll(pos, false);
//                            } else {
                                expand(pos);
//                            }
                        }
                    }
                });
                break;

            case TYPE_ROADDATA:
                final RoadData person = (RoadData)item;
                holder.setText(R.id.road_RoadData_1, roadNames[Integer.valueOf(person.roadName1)]);
                holder.setText(R.id.carNumber_RoadData_1, "车辆数："+person.carNumber1);

                holder.setText(R.id.road_RoadData_2, roadNames[Integer.valueOf(person.roadName2)]);
                holder.setText(R.id.carNumber_RoadData_2, "车辆数："+person.carNumber2);

                holder.setText(R.id.road_RoadData_3, roadNames[Integer.valueOf(person.roadName3)]);
                holder.setText(R.id.carNumber_RoadData_3, "车辆数："+person.carNumber3);

                holder.setText(R.id.road_RoadData_4, roadNames[Integer.valueOf(person.roadName4)]);
                holder.setText(R.id.carNumber_RoadData_4, "车辆数："+person.carNumber4);

                holder.setText(R.id.road_RoadData_5, roadNames[Integer.valueOf(person.roadName5)]);
                holder.setText(R.id.carNumber_RoadData_5, "车辆数："+person.carNumber5);

                holder.setText(R.id.road_RoadData_6, roadNames[Integer.valueOf(person.roadName6)]);
                holder.setText(R.id.carNumber_RoadData_6, "车辆数："+person.carNumber6);

                holder.setText(R.id.road_RoadData_7, roadNames[Integer.valueOf(person.roadName7)]);
                holder.setText(R.id.carNumber_RoadData_7, "车辆数："+person.carNumber7);

                holder.setText(R.id.road_RoadData_8, roadNames[Integer.valueOf(person.roadName8)]);
                holder.setText(R.id.carNumber_RoadData_8, "车辆数："+person.carNumber8);

                holder.setText(R.id.road_RoadData_9, roadNames[Integer.valueOf(person.roadName9)]);
                holder.setText(R.id.carNumber_RoadData_9, "车辆数："+person.carNumber9);

                holder.setText(R.id.road_RoadData_10, roadNames[Integer.valueOf(person.roadName10)]);
                holder.setText(R.id.carNumber_RoadData_10, "车辆数："+person.carNumber10);

                holder.setText(R.id.road_RoadData_11, roadNames[Integer.valueOf(person.roadName11)]);
                holder.setText(R.id.carNumber_RoadData_11, "车辆数："+person.carNumber11);

                holder.setText(R.id.road_RoadData_12, roadNames[Integer.valueOf(person.roadName12)]);
                holder.setText(R.id.carNumber_RoadData_12, "车辆数："+person.carNumber12);

                holder.setText(R.id.road_RoadData_13, roadNames[Integer.valueOf(person.roadName13)]);
                holder.setText(R.id.carNumber_RoadData_13, "车辆数："+person.carNumber13);

                holder.setText(R.id.road_RoadData_14, roadNames[Integer.valueOf(person.roadName14)]);
                holder.setText(R.id.carNumber_RoadData_14, "车辆数："+person.carNumber14);

                holder.setText(R.id.road_RoadData_15, roadNames[Integer.valueOf(person.roadName15)]);
                holder.setText(R.id.carNumber_RoadData_15, "车辆数："+person.carNumber15);

                holder.setText(R.id.road_RoadData_16, roadNames[Integer.valueOf(person.roadName16)]);
                holder.setText(R.id.carNumber_RoadData_16, "车辆数："+person.carNumber16);

                holder.setText(R.id.road_RoadData_17, roadNames[Integer.valueOf(person.roadName17)]);
                holder.setText(R.id.carNumber_RoadData_17, "车辆数："+person.carNumber17);

                holder.setText(R.id.road_RoadData_18, roadNames[Integer.valueOf(person.roadName18)]);
                holder.setText(R.id.carNumber_RoadData_18, "车辆数："+person.carNumber18);

                holder.setText(R.id.road_RoadData_19, roadNames[Integer.valueOf(person.roadName19)]);
                holder.setText(R.id.carNumber_RoadData_19, "车辆数："+person.carNumber19);

                holder.setText(R.id.road_RoadData_20, roadNames[Integer.valueOf(person.roadName20)]);
                holder.setText(R.id.carNumber_RoadData_20, "车辆数："+person.carNumber20);




//                holder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        int cp = getParentPosition(person);
//                        ((Level0Item)getData().get(cp)).removeSubItem(person);
//                        getData().remove(holder.getLayoutPosition());
//                        notifyItemRemoved(holder.getLayoutPosition());
//                    }
//                });
                break;
        }
    }
}
