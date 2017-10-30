package com.heking.snoa;

/**
 * Created by ljj on 2017/8/14.
 */

public class WPConfig {
    public final static String SAO_YI_SAO_YAO_PING = "http://ypzs.kydev.net/?action=drug&code=";
    /**
     * 个人中心地址
     */
    public final static String PERSONAL_CENTERS = "http://123.207.227.252/UserPortal/";
    // public final static String
    // PERSONAL_CENTERS="http://192.168.0.207/UserPortal/";
    /**
     * 图片文件地址
     */
    public final static String IMAGE_VIEW = "http://123.207.227.252/FILE/Home/ViewFile/";
    public final static String IMAGE_VIEW_01 = "http://192.168.0.224/File/Home/DownFile/";//http://192.168.0.224/File/Home/DownFile/555d8886-41d4-4bcf-952d-937459cc3436
//		 public final static String
//		 IMAGE_VIEW_01="http://192.168.0.224/File/Home/DownFile/";
    /**
     * 投诉举报提交资料
     */
    public final static String URL_POST_CESHI = "http://192.168.0.224:89/YZTQW/SJSPZHAPI/api/ComplaintSuggestion/SaveComplaintSuggestionInfo";
    // public final static String
    // URL_POST_CESHI=Url.URL_API_INTRANET+"/ComplaintSuggestion/SaveComplaintSuggestionInfo";
    /**
     * 内网访问
     */
    public final static String URL_API_INTRANET = "http://192.168.0.224:89/YZTQW/SJSPZHAPI/api/";//测试
    public final static String URL_API_INTRANET_ZS_SP = "http://117.173.38.55:84/YZTQW/SJSPZHAPI/api/";//正式的
    public final static String URL_API_HOST = "http://117.173.38.55:84/";//正式
    //public final static String URL_API_HOST_Debug = "http://192.168.0.224:89/";//
    /**
     * 外网访问
     */
    public final static String URL_API_THE_NETWORK = "";

}