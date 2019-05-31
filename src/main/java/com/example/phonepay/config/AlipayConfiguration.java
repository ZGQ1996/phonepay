package com.example.phonepay.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.demo.trade.service.AlipayTradeService;
import com.alipay.demo.trade.service.impl.AlipayTradeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ Author     ：Zgq
 * @ Date       ：Created in 11:46 2019/5/30
 * @ Description：
 * @ Modified By：
 * @Version: $
 */
@Configuration
@EnableConfigurationProperties(AlipayConfig.class)
public class AlipayConfiguration {

    @Autowired
    private AlipayConfig properties;

    @Bean
    public AlipayTradeService alipayTradeService() {
        return new AlipayTradeServiceImpl.ClientBuilder()
                .setGatewayUrl(properties.getGatewayUrl())
                .setAppid(properties.getApp_id())
                .setPrivateKey(properties.getMerchant_private_key())
                .setAlipayPublicKey(properties.getAlipay_public_key())
                .setSignType(properties.getSign_type())
                .build();
    }

    @Bean
    public AlipayClient alipayClient(){
        return new DefaultAlipayClient(properties.getGatewayUrl(),
                properties.getApp_id(),
                properties.getMerchant_private_key(),
                properties.getFormate(),
                properties.getCharset(),
                properties.getAlipay_public_key(),
                properties.getSign_type());
    }
}


