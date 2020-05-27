package com.cvicse.stock.portfolio.data;

/** 新闻、公告、研报实体类
 * Created by tang_xqing on 2017/11/28.
 */

public class StockNewsBean {
    private String date;  // 日期
    private String id;     // 序号
    private String content; // 内容
    private String contentFormat;  // 格式
    private String mediaName;   // 来源(新闻、研报)
    private String purl;  // pdf链接（只有财会数据有值）
    private boolean isPDF;  // 是否有PDF链接
    private String reportTitle;  // 标题（新闻、研报）
    private String title;  // 标题（公告）
    private String datasouce;  // 来源（公告、研报）

    public StockNewsBean() {
    }

    public boolean isPDF() {
        return isPDF;
    }

    public void setPDF(boolean PDF) {
        isPDF = PDF;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentFormat() {
        return contentFormat;
    }

    public void setContentFormat(String contentFormat) {
        this.contentFormat = contentFormat;
    }

    public String getMediaName() {
        return mediaName;
    }

    public void setMediaName(String mediaName) {
        this.mediaName = mediaName;
    }

    public String getPurl() {
        return purl;
    }

    public void setPurl(String purl) {
        this.purl = purl;
    }

    public String getReportTitle() {
        return reportTitle;
    }

    public void setReportTitle(String reportTitle) {
        this.reportTitle = reportTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDatasouce() {
        return datasouce;
    }

    public void setDatasouce(String datasouce) {
        this.datasouce = datasouce;
    }

    @Override
    public String toString() {
        return "StockNewsBean{" +
                "datetime='" + date + '\'' +
                ", id='" + id + '\'' +
                ", content='" + content + '\'' +
                ", contentFormat='" + contentFormat + '\'' +
                ", mediaName='" + mediaName + '\'' +
                ", purl='" + purl + '\'' +
                '}';
    }
}
