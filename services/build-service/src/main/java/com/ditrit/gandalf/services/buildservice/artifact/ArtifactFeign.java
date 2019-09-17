package com.ditrit.gandalf.services.buildservice.artifact;

import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartResolver;

import javax.servlet.MultipartConfigElement;


@FeignClient(name = "${service.endPointName}", url = "${service.endPointConnection}", configuration = ArtifactFeign.FeignConfig.class)
public interface ArtifactFeign {

    @RequestMapping(method = RequestMethod.POST, value = "/upload/file")
    String uploadBuildFile(@RequestParam("file") MultipartFile file);

    @RequestMapping(method = RequestMethod.POST, value = "/upload/conf")
    String uploadBuildConf(@RequestParam("conf") MultipartFile conf);

    @Configuration
    class FeignConfig {

        @Autowired
        private ObjectFactory<HttpMessageConverters> messageConverters;

        @Bean
        @Primary
        @Scope("prototype")
        public Encoder feignEncoder() {
            return new SpringFormEncoder(new SpringEncoder(messageConverters));
        }

        @Bean
        public MultipartConfigElement multipartConfigElement() {
            return new MultipartConfigElement("");
        }

        @Bean
        public MultipartResolver multipartResolver() {
            org.springframework.web.multipart.commons.CommonsMultipartResolver multipartResolver = new org.springframework.web.multipart.commons.CommonsMultipartResolver();
            multipartResolver.setMaxUploadSize(1000000);
            return multipartResolver;
        }
    }
}


