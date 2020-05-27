package cn.aigestudio.datepicker.bizs.themes;

import android.graphics.Color;

/**
 * 主题的默认实现类
 * 
 * The default implement of theme
 *
 * @author AigeStudio 2015-06-17
 */
public class DPBaseTheme extends DPTheme {
    @Override
    public int colorBG() {
        //return 0xFFFFFFFF;
        return 0xff1d2228;
    }

    @Override
    public int colorBGCircle() {
        return 0x44000000;
    }

    @Override
    public int colorTitleBG() {
        //return 0xFFF37B7A;
        return 0xff1d2228;
    }

    @Override
    public int colorTitle() {
        //return 0xEEFFFFFF;
        return 0xff3897db;
    }

    @Override
    public int colorToday() {
        return 0x88F37B7A;
    }

    @Override
    public int colorG() {
        //return 0xEE333333;
        return 0xFFFFFFFF;
    }

    @Override
    public int colorF() {
        //return 0xEEC08AA4;
        //return 0xFFFFFFFF;
        return 0xff999999;
    }

    @Override
    public int colorWeekend() {
        //return 0xEEF78082;
        return 0xff999999;
    }

    @Override
    public int colorHoliday() {
        return 0x80FED6D6;
    }

    /**
     * 假期文本颜色
     * <p>
     * ColorUtils of Holiday text
     *
     * @return 16进制颜色值 hex color
     */
    @Override
    public int colorFB() {
        return Color.GRAY;
    }
}
