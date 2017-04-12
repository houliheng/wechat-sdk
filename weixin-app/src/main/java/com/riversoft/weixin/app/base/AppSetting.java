package com.riversoft.weixin.app.base;

import com.riversoft.weixin.common.exception.WxRuntimeException;
import com.riversoft.weixin.common.util.XmlObjectMapper;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * @borball on 11/7/2016.
 */
public class AppSetting {

    private static Logger logger = LoggerFactory.getLogger(AppSetting.class);

    private static AppSetting appSetting = null;

    private String appId;
    private String secret;
    private String token;
    private String aesKey;
    private String tokenHolderClass;

    public AppSetting() {
    }

    public AppSetting(String appId, String secret) {
        this.appId = appId;
        this.secret = secret;
    }

    public static void setDefault(AppSetting appSetting) {
        AppSetting.appSetting = appSetting;
    }

    public static AppSetting defaultSettings() {
        if (appSetting == null) {
            loadFromSystemProperties();
        }

        if (appSetting == null) {
            loadFromClasspath();
        }

        if (appSetting == null) {
            throw new WxRuntimeException(999, "当前系统没有设置缺省的appId和secret,请使用setDefault方法或者在classpath下面创建wx-app-settings.xml文件.");
        }
        return appSetting;
    }

    private static void loadFromSystemProperties() {
        if (System.getProperties().contains("appconfig")) {
            logger.info("loading app configuration from system properties...");
            String xml = System.getProperties().getProperty("appconfig", "");
            logger.info("appconfig: {}", xml);
            if (xml == null || "".equals(xml)) {
                return;
            } else {
                try {
                    AppSetting setting = XmlObjectMapper.defaultMapper().fromXml(xml, AppSetting.class);
                    appSetting = setting;
                } catch (IOException e) {
                }
            }
        }
    }

    private static void loadFromClasspath() {
        try {
            InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("wx-app-settings-test.xml");

            if (inputStream == null) {
                inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("wx-app-settings.xml");
            }

            if (inputStream != null) {
                String xml = IOUtils.toString(inputStream);
                AppSetting setting = XmlObjectMapper.defaultMapper().fromXml(xml, AppSetting.class);
                appSetting = setting;
            }
        } catch (IOException e) {
            logger.error("read settings from wx-app-settings-test.xml or wx-app-settings.xml failed:", e);
        }
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAesKey() {
        return aesKey;
    }

    public void setAesKey(String aesKey) {
        this.aesKey = aesKey;
    }

    public String getTokenHolderClass() {
        return tokenHolderClass;
    }

    public void setTokenHolderClass(String tokenHolderClass) {
        this.tokenHolderClass = tokenHolderClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppSetting that = (AppSetting) o;

        if (!appId.equals(that.appId)) return false;
        return secret.equals(that.secret);

    }

    @Override
    public int hashCode() {
        int result = appId.hashCode();
        result = 31 * result + secret.hashCode();
        return result;
    }
}
