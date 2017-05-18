/**
 * 文件名： AppConfig.java
 * 项目名： report
 * 
 */
package com.winbons.tech.controller.config;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @Configuration classes =~ <beans/> xml documents
 * @author sfang
 * @version 
 * @created $DateTime: Apr 4, 2016 10:20:37 PM
 * @see [相关类/方法]
 */
@Configuration
@ComponentScan({"com.winbons.tech.service", "com.winbons.tech.dao"})
public class AppConfig {
    /**
     * Global JsonFactory
     */
    public static JsonFactory jsonFactory = new JsonFactory();
    
    /**
     * Global ObjectMapper
     */
    public static ObjectMapper objectMapper = new ObjectMapper();
}
