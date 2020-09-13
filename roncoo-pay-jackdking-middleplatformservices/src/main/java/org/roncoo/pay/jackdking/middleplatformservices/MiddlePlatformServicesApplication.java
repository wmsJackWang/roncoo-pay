package org.roncoo.pay.jackdking.middleplatformservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */

@SpringBootApplication
//@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})//这个注解使用场景： 开发者使用自己的数据源配置信息
//@ComponentScan(basePackages = {"org.roncoo.pay.jackdking.middleplatformservices","com.roncoo.pay.trade.service.impl.RpPayChannelWechatServiceImpl","com.roncoo.pay.trade.service.impl.RpPayChannelAlipayServiceImpl"})
public class MiddlePlatformServicesApplication{
	
    public static void main(String[] args) {
        SpringApplication.run(MiddlePlatformServicesApplication.class, args);
    }

}
