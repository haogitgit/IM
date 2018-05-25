package com.sdust.im;

import com.corundumstudio.socketio.AuthorizationListener;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.HandshakeData;
import com.corundumstudio.socketio.SocketConfig;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import javax.servlet.MultipartConfigElement;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableCaching
@EnableAutoConfiguration
@EnableTransactionManagement
@MapperScan("com.sdust.im.mapper")
public class ImApplication {

	@Value("${wss.server.host}")
	private String host;

	@Value("${wss.server.port}")
	private Integer port;

	public static void main(String[] args) {
		SpringApplication.run(ImApplication.class, args);
	}

	/*@Bean
	public FilterRegistrationBean filterRegistrationBean() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		LoginFilter loginFilter = new LoginFilter();
		registrationBean.setFilter(loginFilter);
		List<String> urlPatterns = new ArrayList<String>();
		urlPatterns.add("/*");
		registrationBean.setUrlPatterns(urlPatterns);
		return registrationBean;
	}*/

	@Bean
	public SocketIOServer socketIOServer()
	{

		Configuration config = new Configuration();
		config.setPort(port);
        config.setHostname(host);
		config.setSocketConfig(new SocketConfig());
		config.setWorkerThreads(100);
		config.setAuthorizationListener(new AuthorizationListener() {

			public boolean isAuthorized(HandshakeData data) {
				return true;
			}
		});

		SocketIOServer server  = new SocketIOServer(config);
		return server;
	}

	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setMaxFileSize("50MB"); //KB,MB
		factory.setMaxRequestSize("100MB");
		return factory.createMultipartConfig();
	}

	@Bean
	public SpringAnnotationScanner springAnnotationScanner(SocketIOServer socketServer) {
		return new SpringAnnotationScanner(socketServer);
	}
}
