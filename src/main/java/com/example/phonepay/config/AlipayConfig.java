package com.example.phonepay.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @ Author     ：Zgq
 * @ Date       ：Created in 12:30 2019/5/27
 * @ Description：支付在配置文件
 * @ Modified By：
 * @Version: $
 */

@Data
@Slf4j
@ConfigurationProperties(prefix = "pay.alipay")
public class AlipayConfig {

    //↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
   /* // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "2016091500517039";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCveherLJtuz6kaIpjqK5UoIjCru4jLeCx9rCYDFGN8FIQEh0iHEHbEvZ15zb50wkuhBEFEoRkPLrsg/CdaYl2qQly+OoPqNqFgQhcRu1qYAjO8aJAlajOYaJkcrC67DUlAIsU/yl8j5tc8Ik5RYzN9BjT2zNIXRoxhwytCX5STJT2ZEvQfkFdG0Hduud5eFLGFuC/mhJU+rdP2i6KZsDluf73VYmqC0JUs3pX+bVS1IV85cWpS/7F7mNR7t9hYCMZ9UJa7NLxJhh5JElwgZRozF0rLIkSN4K04VL9IHxwBQU6mFvnyxLzpF9P4HdPbq3w1AVEGUWcF3OGnOS7qCNBTAgMBAAECggEAMmNp1oJ5o6lH0rRqmpunOh4PQdEZJ+w7Ql8jjzDUnHW7rx01y2sD39zfEnHtJdQBKZxyLYXhD0YJACDIICEs6k4iGZHt/VxwnJZ9z16wVgQT3R9dfRDw/evTQ7OBu77F8P/LClVPWpzX43GqcZ5904HrxY7tKTfd6djEXEvb+yB00ErHisCLvaC2QNDq5kXBqNc9qNdULfWEvHOnALdgw39Qk5cIaGLSs/y9L67RtqN0SwdxZDViJkKsf9uhgHvjPfeGJvmT8nsOZpwT/9Woh0BdHPkbpiyocBUf/XVUSjqWzG8PS0Hj/FGac3M4y2yYN68a8LziapT7wUkoty3RsQKBgQDwP3YhPeWgMnXaa9vySUXVdT8O7b7Vv8XGrnfV6P4nlUmgLQaej2l8ju2YqnfbDXOMpWN8r6TSMhaUlwXFFhEL5yllcSFenk42O3VPYqOhkJK2YhIEkFC2+nH8DpXl7m1Tp8U0D/wxMddFccW+oiUHwGL72T0GZZiAvttBD5bGaQKBgQC6+3TOIHHcQUCQjpjuJXoLTOZ3ENY4p/qVbGuFWYHMfLHIPnZgpHkv8vum9H205Bo6XV9ybKVKdWU1uXZu/yEnkHhZGW8bgG+giB432efT2JQqA75IIo8frkuriJSL0ixfx59iJHGhks0N8ieLCKuqK8u35a2rrrxM6D7i7N7hWwKBgGq954BxfdqlUNOQ5JJLl+l1RGUfoJBbukXCMqAp+vIfVC0ElFJSqa2b21jfNI1w9ovg18kISRTTCFFQoFGmskA1nqDhmM/vShSyajaxPr6D+4tpG1RHwnTp5Ub2PAeC3bQzcFznVUeOO3UxTYibhhRhmBkJtiFndlCNe+D2SikxAoGAQFKahwJQSPXFkTUM1PfdeR+2NIV9PDZFFLwcbN4ysba/reQW8v4BloYfHfsDd4P0pdlTKVjIr8mczJOFc3+e1y5yyCvgtfRD8lSBGLOEdbMOnTl2bvg8hih+bX7powPlMfnJEYj58hw/ICoKgmC0NTElvphrWyEcM9S9NXFI5vsCgYEAxnYChqJ+4VXuNBF9wc6oebRYgGYWMEv0uhqa/7A0o6vsvXrd7QtQTdyeYxpBQLcsFJkKc1n5MFsrSY6FAFTVmBPMnYfQwH0OAYxKfytles3wMVxWbAwJwtxhZ1n3nGFHCES8rwPSa+5xZe6vzpYVUTgnjBfr4QL3dlQJKj51a+0=";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtHjQ2gD1juu9F5Hxr1oy5c8So9EC7G6OlK4rJf98kvFj1GaDxUp9/WY7YMThC1VqTZn+T9FiEO7CI8/EXjiF/4n64NYtJFOtT7dplmQwoVHE5dOvprfR1ugUbeHLcQlUvbctKELCDrUHTe3/p2rCIH7TwKQoUOpP6yunyHardPOddUJEC4C7wZ11KwzTDaa1pU3kLb0Z/0DEte63aEq3fyt1M5UFD5sQgN7FZuW3s7sy5jaIJgkZbNTIl/lpcrNjPGXxZmK6qfiiA8LSFgQj2A0UPSNtDLdUnO0I211tWRfUVVDzviIdV1T9k+JhlPhLsYGRFTvsa9cRgwWL/lt7BQIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://zhouguoqing.natapp1.cc/callback/notify";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://zhouguoqing.natapp1.cc/callback/return";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";*/


    /** 支付宝gatewayUrl */
    private String gatewayUrl;
    /** 商户应用id */
    private String app_id;
    /** RSA私钥，用于对商户请求报文加签 */
    private String merchant_private_key;
    /** 支付宝RSA公钥，用于验签支付宝应答 */
    private String alipay_public_key;
    /** 签名类型 */
    private String sign_type = "RSA2";

    /** 格式 */
    private String formate = "json";
    /** 编码 */
    private String charset = "UTF-8";

    /** 同步地址 */
    private String return_url;

    /** 异步地址 */
    private String notify_url;

    /** 最大查询次数 */
    private static int maxQueryRetry = 5;
    /** 查询间隔（毫秒） */
    private static long queryDuration = 5000;
    /** 最大撤销次数 */
    private static int maxCancelRetry = 3;
    /** 撤销间隔（毫秒） */
    private static long cancelDuration = 3000;

    // 支付宝网关
    public static String log_path = "C:\\";

//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private AlipayConfig() {}

    /**
     * PostContruct是spring框架的注解，在方法上加该注解会在项目启动的时候执行该方法，也可以理解为在spring容器初始化的时候执行该方法。
     */
    @PostConstruct
    public void init() {
        log.info(description());
    }

    public String description() {
        StringBuilder sb = new StringBuilder("\nConfigs{");
        sb.append("支付宝网关: ").append(gatewayUrl).append("\n");
        sb.append(", appid: ").append(app_id).append("\n");
        sb.append(", 商户RSA私钥: ").append(getKeyDescription(merchant_private_key)).append("\n");
        sb.append(", 支付宝RSA公钥: ").append(getKeyDescription(alipay_public_key)).append("\n");
        sb.append(", 签名类型: ").append(sign_type).append("\n");

        sb.append(", 查询重试次数: ").append(maxQueryRetry).append("\n");
        sb.append(", 查询间隔(毫秒): ").append(queryDuration).append("\n");
        sb.append(", 撤销尝试次数: ").append(maxCancelRetry).append("\n");
        sb.append(", 撤销重试间隔(毫秒): ").append(cancelDuration).append("\n");
        sb.append("}");
        return sb.toString();
    }

    private String getKeyDescription(String key) {
        int showLength = 6;
        if (StringUtils.isNotEmpty(key) && key.length() > showLength) {
            return new StringBuilder(key.substring(0, showLength)).append("******")
                    .append(key.substring(key.length() - showLength)).toString();
        }
        return null;
    }


}
