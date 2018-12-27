/**
 * Copyright 2018-2020 stylefeng & fengshuonan (https://gitee.com/stylefeng)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.stylefeng.guns;

import cn.stylefeng.guns.huobi.util.KLineCombineChart;
import cn.stylefeng.roses.core.config.WebAutoConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;

/**
 * SpringBoot方式启动类
 *
 * @author stylefeng
 * @Date 2017/5/21 12:06
 */
@SpringBootApplication(exclude = WebAutoConfiguration.class)
@EnableScheduling
public class GunsApplication{
    @Autowired
    private KLineCombineChart kLineCombineChart;
    private final static Logger logger = LoggerFactory.getLogger(GunsApplication.class);

    private Jedis jedis;

    private JedisPoolConfig config;

    private JedisShardInfo sharInfo;
    @Bean
    public Jedis jedis() {
//连接redis服务器，192.168.0.100:6379
// jedis = new Jedis("192.168.0.100", 6379);
// //权限认证
// jedis.auth("123456");
// 操作单独的文本串
        config = new JedisPoolConfig();
        config.setMaxIdle(1000);//最大空闲时间
        config.setMaxWaitMillis(1000); //最大等待时间
        config.setMaxTotal(500); //redis池中最大对象个数
        sharInfo = new JedisShardInfo("127.0.0.1", 6379);
        sharInfo.setPassword("123456");
        sharInfo.setConnectionTimeout(5000);//链接超时时间
        jedis = new Jedis(sharInfo);
        return jedis;
    }

    public static void main(String[] args) {
        SpringApplication.run(GunsApplication.class, args);
        /*SpringApplicationBuilder builder = new SpringApplicationBuilder(GunsApplication.class);
        builder.headless(false).web(false).run(args);*/
        logger.info("GunsApplication is success!");
    }


}
