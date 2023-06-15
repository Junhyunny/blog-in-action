package action.in.blog.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.XsdSchemaCollection;
import org.springframework.xml.xsd.commons.CommonsXsdSchemaCollection;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {

    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/ws/*");
    }

    @Bean
    public XsdSchemaCollection wsdlSchemas() {
        CommonsXsdSchemaCollection schemaCollection = new CommonsXsdSchemaCollection(
                new ClassPathResource("xsd/author.xsd"),
                new ClassPathResource("xsd/book.xsd")
        );
        schemaCollection.setInline(true);
        return schemaCollection;
    }

    @Bean(name = "schemas") // http://localhost:8080/ws/schemas.wsdl
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchemaCollection wsdlSchemas) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("BookStorePort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace("http://blog.in.action");
        wsdl11Definition.setSchemaCollection(wsdlSchemas);
        return wsdl11Definition;
    }
}
