package cn.aigestudio.datepicker.bizs.themes;

/**
 * Created by liu_zlu on 2017/5/2 09:39
 * 白天主题
 */
public class WhiteDPTheme extends DPCNTheme {
    @Override
    public int colorBG() {
        return 0xFFFFFFFF;
    }

    @Override
    public int colorBGCircle() {
        return 0x44000000;
    }

    @Override
    public int colorTitleBG() {
        return 0xFFF37B7A;
    }

    @Override
    public int colorTitle() {
        return 0xEEFFFFFF;
    }

    @Override
    public int colorToday() {
        return 0x88F37B7A;
    }

    @Override
    public int colorG() {
        return 0xEE333333;
    }

    @Override
    public int colorF() {
       // return 0xEEC08AA4;
        return 0xFF00acb5;
    }

    @Override
    public int colorWeekend() {
        return 0xEEF78082;
    }

    @Override
    public int colorHoliday() {
        //return 0x80FED6D6;
        return 0xFF00acb5;
    }
}
