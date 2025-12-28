package com.atguigu.lease.web.admin.custom.config;

import com.atguigu.lease.web.admin.custom.convert.StringToBaseEnumConvertFactory;
import com.atguigu.lease.web.admin.custom.interceptor.AuthenticationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {


    @Autowired
    private StringToBaseEnumConvertFactory stringToBaseEnumConvertFactory;
    @Autowired
    private AuthenticationInterceptor authenticationInterceptor;

    @Override
    public void addFormatters(FormatterRegistry registry) {
     //转换器
        registry.addConverterFactory(this.stringToBaseEnumConvertFactory);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.authenticationInterceptor).addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/login/**");
    }
}