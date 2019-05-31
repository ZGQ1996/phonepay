package com.example.phonepay.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @ Author     ：Zgq
 * @ Date       ：Created in 11:51 2019/5/30
 * @ Description：
 * @ Modified By：
 * @Version: $
 */
@Configuration
public class WebMvcConfiguration extends WebMvcConfigurationSupport {
    @Override
    protected void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/topay").setViewName("pay");
        registry.addViewController("/query").setViewName("query");
        registry.addViewController("/tk").setViewName("refund");
        registry.addViewController("/tkquery").setViewName("refundquery");
        registry.addViewController("/url").setViewName("downloadurl");
        super.addViewControllers(registry);
    }

    /**
     * @Description 静态资源路径
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        super.addResourceHandlers(registry);
    }
}
