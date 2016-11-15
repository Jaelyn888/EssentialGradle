package com.yishanxiu.yishang.getdata;

import java.util.Map;

/**
 * Created by Jaelyn on 2015/12/3 0003.
 * <p/>
 * 网络数据请求对象
 */
public class GetDataQueue {

    private Map<String,Object> fileMap;

    /**
     * 服务器地址
     */
    private String ip;
    /**
     * 方法名字
     */
    private String actionName;
    /**
     * 请求参数
     */
    private Object params;

    /**
     * 网络返回数据
     */
    private String responseData = "";

    /**
     * 标记网络错误
     */
    private boolean isNetError = false;
    /**
     * 请求的标记
     */
    public int what;

    private Object tag;

    public enum MediaType{
        URLENCODEFORM,JSON, MUTILPART
    }

    public Map<String, Object> getFileMap() {
        return fileMap;
    }

    public void setFileMap(Map<String, Object> fileMap) {
        this.fileMap = fileMap;
    }

    /**
	 * 请求数据格式
     */
    private MediaType mediaType=MediaType.URLENCODEFORM;

    public MediaType getMediaType() {
        return mediaType;
    }

    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }


    public GetDataQueue() {
        ip = GetDataConfing.ip;
    }

    /**
     * 是否是网络异常
     *
     * @return
     */
    public boolean isOk() {
        return !isNetError;
    }

    public void setNetError(boolean isNetError) {
        this.isNetError = isNetError;
    }

    /**
     * 网络数据回调监听
     */
    private IGetServicesDataCallBack getServicesDataCallBack;

    public IGetServicesDataCallBack getCallBack() {
        return getServicesDataCallBack;
    }

    public void setCallBack(
            IGetServicesDataCallBack getServicesDataCallBack) {
        this.getServicesDataCallBack = getServicesDataCallBack;
    }


    public int getWhat() {
        return what;
    }

    public void setWhat(int what) {
        this.what = what;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getActionName() {
        return actionName;
    }


    /**
     * 由于次项目传入参数时 使用setParamsNoJson<br/>
     * 如果没有传入Params_key信息 这将所有的参数调用toString使用setParams进行调用
     *
     * @param params 例如本来是userInfo={UserAccount:"",Password:""}<br/>
     *               现在只需传入 Params_key=userInfo, UserAccount="", Password=""
     *               参数可传入Map<K, V> List<? extends Map<K, V>>
     */
    public void setParamsNoJson(Object params) {
      //  String s = GetDataConfing.Key_Str;//(String) params.get(Params_key);
        //Map<String, String> params2 = new HashMap<String, String>();
//
//        Map<String, Object> params3 = new HashMap<String, Object>();
//        params3.putAll(params);
//        params3.remove(Params_key);
//        params2.put(s, new JsonMapOrListJsonMap2JsonUtil<String, Object>()
 //               .map2Json(params));
        this.params = params;
    }

    public Object getParams() {
        return params;
    }

    public void setParams(Object params) {
        this.params = params;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getResponseData() {
        return responseData;
    }

    public void setResponseData(String responseData) {
        this.responseData = responseData;
    }

    /**
     * 重写父类的获取信息的方法 当含有&lt;string> &lt;/string>时去掉前后的&lt;string> &lt;/string>
     */
    public String getInfo() {
//        if (!TextUtils.isEmpty(responseData)) {
//            int startIndex = responseData.indexOf("{");
//            int endIndex = responseData.lastIndexOf("}");
//            responseData = responseData.substring(startIndex, endIndex + 1);
//        }
        return responseData;
    }

}
