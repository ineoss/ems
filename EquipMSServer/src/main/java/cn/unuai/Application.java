package cn.unuai;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.File;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    //配置跨域请求
    @Configuration
    public class CORSConfiguration extends WebMvcConfigurerAdapter {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")
                    .allowedMethods("GET", "POST")
                    .allowedOrigins("*")
                    .allowedHeaders("*");
        }
    }

    //配置静态文件路径
    @Configuration
    public class WebAppConfig extends WebMvcConfigurerAdapter {
        //获取配置文件中图片的路径
        @Value("${cbs.imagesPath}")
        private String mImagesPath;
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            if (mImagesPath.equals("") || mImagesPath.equals("${cbs.imagesPath}")) {
                String imagesPath = WebAppConfig.class.getClassLoader().getResource("").getPath();
                if (imagesPath.indexOf(".jar") > 0) {
                    imagesPath = imagesPath.substring(0, imagesPath.indexOf(".jar"));
                } else if (imagesPath.indexOf("classes") > 0) {
                    imagesPath = "file:" + imagesPath.substring(0, imagesPath.indexOf("classes"));
                }
                imagesPath = imagesPath.substring(0, imagesPath.lastIndexOf("/")) + "/images/";
                mImagesPath = imagesPath;
            }
            registry.addResourceHandler("/**").addResourceLocations(mImagesPath);
            super.addResourceHandlers(registry);
        }
    }

}

