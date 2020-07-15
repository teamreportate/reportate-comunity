//package bo.com.reportate.configuration;
//
//import org.apache.catalina.Context;
//import org.apache.catalina.connector.Connector;
//import org.apache.tomcat.util.descriptor.web.SecurityCollection;
//import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
//import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
//import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * @Created by :Reportate
// * @Autor :Ricardo Laredo
// * @Email :rllayus@gmail.com
// * @Date :31-07-19
// * @Project :bancarizacion
// * @Package :bo.com.embol.bancarizacion.configuration
// * @Copyright :Reportate
// */
//
//@Configuration
//public class ConnectorConfig {
//
//    @Bean
//    public ServletWebServerFactory servletContainer() {
//        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
//            @Override
//            protected void postProcessContext(Context context) {
//                SecurityConstraint securityConstraint = new SecurityConstraint();
//                securityConstraint.setUserConstraint("CONFIDENTIAL");
//                SecurityCollection collection = new SecurityCollection();
//                collection.addPattern("/*");
//                securityConstraint.addCollection(collection);
//                context.addConstraint(securityConstraint);
//            }
//        };
//        tomcat.addAdditionalTomcatConnectors(getHttpConnector());
//        return tomcat;
//    }
//
//    private Connector getHttpConnector() {
//        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
//        connector.setScheme("http");
//        connector.setPort(9090);
//        connector.setSecure(false);
//        connector.setRedirectPort(9443);
//        return connector;
//    }
//}
