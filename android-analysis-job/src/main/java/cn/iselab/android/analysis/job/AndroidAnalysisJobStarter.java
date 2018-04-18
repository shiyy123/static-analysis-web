package cn.iselab.android.analysis.job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.MessageSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration;
import org.springframework.boot.autoconfigure.cloud.CloudAutoConfiguration;
import org.springframework.boot.autoconfigure.dao.PersistenceExceptionTranslationAutoConfiguration;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.data.solr.SolrRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.boot.autoconfigure.groovy.template.GroovyTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.boot.autoconfigure.hateoas.HypermediaAutoConfiguration;
import org.springframework.boot.autoconfigure.integration.IntegrationAutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JndiDataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.XADataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jersey.JerseyAutoConfiguration;
import org.springframework.boot.autoconfigure.jms.JmsAutoConfiguration;
import org.springframework.boot.autoconfigure.jms.JndiConnectionFactoryAutoConfiguration;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQAutoConfiguration;
import org.springframework.boot.autoconfigure.jms.hornetq.HornetQAutoConfiguration;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration;
import org.springframework.boot.autoconfigure.mobile.DeviceDelegatingViewResolverAutoConfiguration;
import org.springframework.boot.autoconfigure.mobile.DeviceResolverAutoConfiguration;
import org.springframework.boot.autoconfigure.mobile.SitePreferenceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mustache.MustacheAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.reactor.ReactorAutoConfiguration;
import org.springframework.boot.autoconfigure.security.FallbackWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.social.FacebookAutoConfiguration;
import org.springframework.boot.autoconfigure.social.LinkedInAutoConfiguration;
import org.springframework.boot.autoconfigure.social.SocialWebAutoConfiguration;
import org.springframework.boot.autoconfigure.social.TwitterAutoConfiguration;
import org.springframework.boot.autoconfigure.solr.SolrAutoConfiguration;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.boot.autoconfigure.velocity.VelocityAutoConfiguration;
import org.springframework.boot.autoconfigure.web.*;
import org.springframework.boot.autoconfigure.websocket.WebSocketAutoConfiguration;

/**
 * @author henrylee
 */
@SpringBootApplication(exclude = {
        ActiveMQAutoConfiguration.class,
        AopAutoConfiguration.class,
        BatchAutoConfiguration.class,
        CloudAutoConfiguration.class,
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        DeviceDelegatingViewResolverAutoConfiguration.class,
        DeviceResolverAutoConfiguration.class,
        DispatcherServletAutoConfiguration.class,
        ElasticsearchRepositoriesAutoConfiguration.class,
        EmbeddedServletContainerAutoConfiguration.class,
        ErrorMvcAutoConfiguration.class,
        FacebookAutoConfiguration.class,
        FallbackWebSecurityAutoConfiguration.class,
        FlywayAutoConfiguration.class,
        FreeMarkerAutoConfiguration.class,
        GroovyTemplateAutoConfiguration.class,
        GsonAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class,
        HornetQAutoConfiguration.class,
        HttpEncodingAutoConfiguration.class,
        HttpMessageConvertersAutoConfiguration.class,
        HypermediaAutoConfiguration.class,
        IntegrationAutoConfiguration.class,
        JacksonAutoConfiguration.class,
        JerseyAutoConfiguration.class,
        JmsAutoConfiguration.class,
        JmxAutoConfiguration.class,
        JndiConnectionFactoryAutoConfiguration.class,
        JndiDataSourceAutoConfiguration.class,
        JpaRepositoriesAutoConfiguration.class,
        LinkedInAutoConfiguration.class,
        LiquibaseAutoConfiguration.class,
        MailSenderAutoConfiguration.class,
        MessageSourceAutoConfiguration.class,
        MongoAutoConfiguration.class,
        MongoRepositoriesAutoConfiguration.class,
        MultipartAutoConfiguration.class,
        MustacheAutoConfiguration.class,
        PersistenceExceptionTranslationAutoConfiguration.class,
        RabbitAutoConfiguration.class,
        ReactorAutoConfiguration.class,
        RepositoryRestMvcAutoConfiguration.class,
        SecurityAutoConfiguration.class,
        ServerPropertiesAutoConfiguration.class,
        SitePreferenceAutoConfiguration.class,
        SocialWebAutoConfiguration.class,
        SolrAutoConfiguration.class,
        SolrRepositoriesAutoConfiguration.class,
        SpringDataWebAutoConfiguration.class,
        ThymeleafAutoConfiguration.class,
        TwitterAutoConfiguration.class,
        VelocityAutoConfiguration.class,
        WebMvcAutoConfiguration.class,
        WebSocketAutoConfiguration.class,
        XADataSourceAutoConfiguration.class
})
public class AndroidAnalysisJobStarter {

    public static void main(String[] args) {
        System.setProperty("org.jboss.logging.provider", "slf4j");

        SpringApplication application = new SpringApplication(AndroidAnalysisJobStarter.class);
        application.run(args);
    }
}
