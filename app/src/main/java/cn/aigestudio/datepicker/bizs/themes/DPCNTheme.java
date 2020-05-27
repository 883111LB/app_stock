package cn.aigestudio.datepicker.bizs.themes;

/**
 * 天朝日历主题类
 * 其他国家的主题不需要这样去处理
 * 
 * Theme of china
 * You don't need this class for other countries
 * 黑夜 主题
 * @author AigeStudio 2015-06-17
 */
public class DPCNTheme extends DPBaseTheme {
    /**
     * 农历文本颜色
     * 
     * ColorUtils of Lunar text
     *
     * @return 16进制颜色值 hex color
     */
    public int colorL() {
        return 0xEE888888;
    }

    /**
     * 补休日期背景颜色
     * 
     * ColorUtils of Deferred background
     *
     * @return 16进制颜色值 hex color
     */
    public int colorDeferred() {
        return 0x50B48172;
    }

    @Override
    public int colorF() {
        // return 0xEEC08AA4;
        return 0xFF00acb5;
    }

    @Override
    public int colorHoliday() {
        //return 0x80FED6D6;
        return 0xFF00acb5;
    }
}
