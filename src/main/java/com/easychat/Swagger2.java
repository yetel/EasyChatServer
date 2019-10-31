/*
 * @(#)Swagger2.java 2017年10月25日
 * Copyright (c), 2017 深圳业拓讯通信科技有限公司（Shenzhen Yetelcom Communication Tech. Co.,Ltd.）,  
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.easychat;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * @author Zed
 * date: 2019/08/20.
 * description:
 */
@Configuration
@EnableSwagger2
public class Swagger2 {
	
	@Bean
    public Docket createRestApi() {
		
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("com.easy.controller"))
                .paths(PathSelectors.any())
                .build();
        
    }
}
