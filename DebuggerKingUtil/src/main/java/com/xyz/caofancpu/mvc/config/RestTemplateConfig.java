package com.xyz.caofancpu.mvc.config;

import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

/**
 * FileName: RestTemplateConfig
 * Author:   caofanCPU
 * Date:     2018/11/17 16:54
 */
@Configuration
public class RestTemplateConfig {

    @Resource
    RestTemplateBuilder restTemplateBuilder;

    /**
     * 注意：@LoadBalanced注解，使用该注解，则调用其他服务时，必须使用服务名称[http://SERVICE-XXX]，而非IP:port
     *
     * @return
     */
    @Bean(name = "restTemplate")
    @LoadBalanced
    RestTemplate restTemplate() {
        return restTemplateBuilder.build();
    }

    /**
     * 通过IP:port访问服务
     *
     * @return
     */
    @Bean(name = "zuulRestTemplate")
    RestTemplate zuulRestTemplate() {
        return restTemplateBuilder.build();
    }

    /**
     * 测试环境忽略https请求证书的问题
     *
     * @return
     */
    @Bean(name = "ignoreHttpsRestTemplate")
    RestTemplate ignoreHttpsRestTemplate()
            throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        // https
        SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(
                new SSLContextBuilder()
                        .loadTrustMaterial(null, (X509Certificate[] x509Certificates, String s) -> true)
                        .build(),
                new String[]{"SSLv2Hello", "SSLv3", "TLSv1", "TLSv1.2"},
                null,
                NoopHostnameVerifier.INSTANCE
        );
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", new PlainConnectionSocketFactory())
                .register("https", socketFactory).build();
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
        connectionManager.setMaxTotal(200);
        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(socketFactory)
                .setConnectionManager(connectionManager)
                .setConnectionManagerShared(true)
                .build();
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectionRequestTimeout(30000);
        factory.setConnectTimeout(30000);
        factory.setReadTimeout(30000);
        factory.setHttpClient(httpClient);
        return new RestTemplate(factory);
    }
}
