package top.uaian.api.conf;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class ApolloConfig{

    private static Logger logger = LoggerFactory.getLogger(ApolloConfig.class);

    public static void initialize(String[] args) {
        logger.info("-----ApolloConfig initialize-----");
        if(args != null && args.length > 0) {
            Arrays.stream(args).forEach(arg -> {
                if (arg.toLowerCase().contains("spring.profiles.active")) {
                    String[] split = arg.split("=");
                    if(split.length == 2) {
                        String curEnv = split[1];
                        switch (curEnv) {
                            case "dev":
                                System.setProperty("env", "dev");
                                //apollo.meta的地址
                                System.setProperty("apollo.configService", "http://192.168.47.131:8080");
                                //在应用启动阶段，向Spring容器注入被托管的application.properties文件的配置信息。
                                System.setProperty("apollo.bootstrap.enabled", "true");
                                //apollo的命名空间
                                System.setProperty("apollo.bootstrap.namespaces", "application");
                                //将Apollo配置加载提到初始化日志系统之前。
                                System.setProperty("apollo.bootstrap.eagerLoad.enabled", "true");
                                break;
                        }
                    }
                }
            });
        }
    }
}
