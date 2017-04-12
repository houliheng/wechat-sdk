package com.riversoft.weixin.mp.event.ticket;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.riversoft.weixin.common.event.EventRequest;

/**
 * 用户扫描带场景值二维码时，可能推送以下两种事件
 * 1. 如果用户还未关注公众号，则用户可以关注公众号，关注后微信会将带场景值关注事件推送给开发者。
 * 2. 如果用户已经关注公众号，则微信会将带场景值扫描事件推送给开发者。
 * <p/>
 * 本事件属于第2种
 * <p/>
 * Created by exizhai on 11/14/2015.
 */
public class SceneScanEvent extends EventRequest {

    /**
     * 事件KEY值，是一个32位无符号整数，即创建二维码时的二维码scene_id
     */
    @JsonProperty("EventKey")
    @JacksonXmlCData
    private String eventKey;

    /**
     * 二维码的ticket，可用来换取二维码图片
     */
    @JsonProperty("Ticket")
    @JacksonXmlCData
    private String ticket;

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

}
