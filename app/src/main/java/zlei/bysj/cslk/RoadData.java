package zlei.bysj.cslk;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by luoxw on 2016/8/10.
 */

public class RoadData implements MultiItemEntity {
    public RoadData(String roadName1, String carNumber1,String roadName2, String carNumber2,String roadName3, String carNumber3,String roadName4, String carNumber4,String roadName5, String carNumber5
    ,String roadName6, String carNumber6,String roadName7, String carNumber7,String roadName8, String carNumber8,String roadName9, String carNumber9,String roadName10, String carNumber10,
                    String roadName11, String carNumber11,String roadName12, String carNumber12,String roadName13, String carNumber13,String roadName14, String carNumber14,String roadName15, String carNumber15,
    String roadName16, String carNumber16,String roadName17, String carNumber17,String roadName18, String carNumber18,String roadName19, String carNumber19,String roadName20, String carNumber20) {
        this.roadName1 = roadName1;this.roadName6 = roadName6;        this.roadName11 = roadName11;this.roadName16 = roadName16;
        this.roadName2 = roadName2;this.roadName7 = roadName7;        this.roadName12 = roadName12;this.roadName17 = roadName17;
        this.roadName3 = roadName3;this.roadName8 = roadName8;        this.roadName13 = roadName13;this.roadName18 = roadName18;
        this.roadName4 = roadName4;this.roadName9 = roadName9;        this.roadName14 = roadName14;this.roadName19 = roadName19;
        this.roadName5 = roadName5;this.roadName10 = roadName10;        this.roadName15 = roadName15;this.roadName20 = roadName20;

        this.carNumber1 = carNumber1;this.carNumber6 = carNumber6;       this.carNumber11 = carNumber11;this.carNumber16 = carNumber16;
        this.carNumber2 = carNumber2;this.carNumber7 = carNumber7;       this.carNumber12 = carNumber12;this.carNumber17 = carNumber17;
        this.carNumber3 = carNumber3;this.carNumber8 = carNumber8;       this.carNumber13 = carNumber13;this.carNumber18 = carNumber18;
        this.carNumber4 = carNumber4;this.carNumber9 = carNumber9;       this.carNumber14 = carNumber14;this.carNumber19 = carNumber19;
        this.carNumber5 = carNumber5;this.carNumber10 = carNumber10;       this.carNumber15 = carNumber15;this.carNumber20 = carNumber20;
    }

    public String roadName1,roadName2,roadName3,roadName4,roadName5,roadName6,roadName7,roadName8,roadName9,roadName10,roadName11,roadName12,roadName13,roadName14,roadName15,roadName16,roadName17,roadName18,roadName19,roadName20;
    public String carNumber1,carNumber2,carNumber3,carNumber4,carNumber5,carNumber6,carNumber7,carNumber8,carNumber9,carNumber10,carNumber11,carNumber12,carNumber13,carNumber14,carNumber15,carNumber16,carNumber17,carNumber18,carNumber19,carNumber20;

    @Override
    public int getItemType() {
        return ExpandableItemAdapter.TYPE_ROADDATA;
    }
}