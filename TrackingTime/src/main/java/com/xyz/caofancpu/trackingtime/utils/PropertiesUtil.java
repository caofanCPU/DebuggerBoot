package com.xyz.caofancpu.trackingtime.utils;

import com.xyz.caofancpu.util.commonoperateutils.SymbolConstantUtil;
import com.xyz.caofancpu.util.studywaitingutils.VerbalExpressionUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Properties file tool
 *
 * @author caofanCPU
 */
public class PropertiesUtil {

    /**
     * Load configuration file
     *
     * @param configFileAbsolutePath
     * @return
     */
    public static Properties loadPropertiesFromRootResource(String configFileAbsolutePath) {
        Properties customerProperties = new Properties();
        try {
            // Try to read configuration items
            customerProperties.load(new FileInputStream(configFileAbsolutePath));
        } catch (IOException e) {
            // do nothing
        }
        return customerProperties;
    }

    /**
     * Check if the configuration item is configured as 'true' or 'TRUE'
     *
     * @param properties
     * @param propertyKey
     * @return
     */
    public static boolean checkConfigTakEffect(Properties properties, String propertyKey) {
        String property = properties.getProperty(propertyKey);
        if (StringUtils.isBlank(property)) {
            // if no config, just ignore
            return false;
        }
        String noWhiteCharProperty = VerbalExpressionUtil.cleanWhiteChar(property);
        String[] itemConfigs = noWhiteCharProperty.split(SymbolConstantUtil.ENGLISH_COMMA);
        if (itemConfigs.length == 1) {
            // if there is just one item config, ignore too
            return false;
        }
        return Boolean.parseBoolean(itemConfigs[0]);
    }

    /**
     * Detect if a configuration item contains a directory relative path,
     * if true then return it, otherwise return null.
     *
     * @param properties
     * @param propertyKey
     * @return
     */
    public static String detectConfigDirectoryPath(Properties properties, String propertyKey) {
        if (!checkConfigTakEffect(properties, propertyKey)) {
            return null;
        }
        String property = properties.getProperty(propertyKey);
        String noWhiteCharProperty = VerbalExpressionUtil.cleanWhiteChar(property);
        String[] itemConfigs = noWhiteCharProperty.split(SymbolConstantUtil.ENGLISH_COMMA);
        // here, need to create file, so we should check need put it into custom directory or not
        return StringUtils.isBlank(itemConfigs[1]) ? null : itemConfigs[1];
    }

    public static void main(String[] args) {
        String projectRootPath = System.getProperty("user.dir");
        String moduleBasicPath = "/TrackingTime/src/main";
        String resourcePath = "/resources";
        String srcJavaPath = "/java";
        Properties properties = loadPropertiesFromRootResource(projectRootPath + moduleBasicPath + resourcePath + "/d8ger.properties");
        String autoCreateMo = detectConfigDirectoryPath(properties, "autoCreateMo");
        int a = 1;
    }

}
