package top.sharehome.springbootinittemplate.starter;

import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

@Component
@Order
@Slf4j
public class ApplicationUriPrinter implements CommandLineRunner {

    @Resource
    private Environment env;

    @Override
    public void run(String... args) throws Exception {
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        String path = env.getProperty("server.servlet.context-path");
        String name = env.getProperty("spring.application.name");
        if (StrUtil.isBlank(path) || "/".equals(path)) {
            path = "";
        }
        log.info("-------------------------------------------------------------------------------------------------\t");
        log.info("{} is running! Access URLs:\t", name);
        log.info("Local Knife4j访问地址:\thttp://localhost:{}{}/doc.html\t", port, path);
        log.info("External访问地址:\t\thttp://{}:{}{}\t", ip, port, path);
        log.info("-------------------------------------------------------------------------------------------------\t");
    }

}
