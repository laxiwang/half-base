package com.halfroom.distribution.config.web;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.ResourceServlet;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.support.spring.stat.BeanTypeAutoProxyCreator;
import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import com.halfroom.distribution.core.listener.ConfigListener;
import com.halfroom.distribution.core.util.xss.XssFilter;
import org.springframework.aop.Advisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.JdkRegexpMethodPointcut;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextListener;

import java.util.Properties;

/**
 * web 配置类
 */
@Configuration
public class WebConfig {


    /**
     * druidServlet注册
     */
    @Bean
    public ServletRegistrationBean statViewServle(){
        ServletRegistrationBean servletRegistrationBean=new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
        //IP白名单
        //servletRegistrationBean.addInitParameter("allow","192.168.1.12,127.0.0.1");
        //IP黑名单
        //servletRegistrationBean.addInitParameter("deny","192.168.4.23");
        //控制台用户
        servletRegistrationBean.addInitParameter(ResourceServlet.PARAM_NAME_USERNAME,"banjianjiaoshitingyun");
        servletRegistrationBean.addInitParameter(ResourceServlet.PARAM_NAME_PASSWORD,"20180529");

        //是否能够重置数据
        servletRegistrationBean.addInitParameter(StatViewServlet.PARAM_NAME_RESET_ENABLE,"true");

        return servletRegistrationBean;
    }
    /**
     * druid监控 配置URI拦截策略
     * @return
     */
    @Bean
    public FilterRegistrationBean druidStatFilter(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        //添加过滤规则.
        filterRegistrationBean.addUrlPatterns("/*");
        //添加不需要忽略的格式信息.
        filterRegistrationBean.addInitParameter(
                "exclusions","/static/*,*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid,/druid/*");

         //监控单个url调用的sql列表
        filterRegistrationBean.addInitParameter(WebStatFilter.PARAM_NAME_PROFILE_ENABLE,"true");

        filterRegistrationBean.addInitParameter(WebStatFilter.PARAM_NAME_SESSION_STAT_ENABLE,"true");
        filterRegistrationBean.addInitParameter(WebStatFilter.PARAM_NAME_PRINCIPAL_SESSION_NAME,"username");
        filterRegistrationBean.addInitParameter(WebStatFilter.PARAM_NAME_SESSION_STAT_MAX_COUNT,"1000");
        filterRegistrationBean.addInitParameter(WebStatFilter.PARAM_NAME_PRINCIPAL_COOKIE_NAME,"shiroCookie");

        return filterRegistrationBean;
    }

    /**
     * druid数据库连接池监控
     */
    @Bean
    public DruidStatInterceptor druidStatInterceptor() {
        return new DruidStatInterceptor();
    }


    @Bean
    public JdkRegexpMethodPointcut druidStatPointcut(){
        JdkRegexpMethodPointcut druidStatPointcut = new JdkRegexpMethodPointcut();
        String patterns = "com.halfroom.distribution.modular.*.service.*";
        String p2="com.halfroom.distribution.modular.dist.amountsign.impl.*";
        String dao="com.halfroom.distribution.dao.*";
        //可以set多个
        druidStatPointcut.setPatterns(dao);
        return druidStatPointcut;
    }

    /**
     * druid数据库连接池监控
     */
    @Bean
    public BeanTypeAutoProxyCreator beanTypeAutoProxyCreator() {
        BeanTypeAutoProxyCreator beanTypeAutoProxyCreator = new BeanTypeAutoProxyCreator();
        beanTypeAutoProxyCreator.setTargetBeanType(DruidDataSource.class);
        beanTypeAutoProxyCreator.setInterceptorNames("druidStatInterceptor");
        return beanTypeAutoProxyCreator;
    }

    /**
     * druid 为druidStatPointcut添加拦截
     * @return
     */
    @Bean
    public Advisor druidStatAdvisor() {
        return new DefaultPointcutAdvisor(druidStatPointcut(), druidStatInterceptor());
    }

    /**
     * xssFilter注册
     */
    @Bean
    public FilterRegistrationBean xssFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean(new XssFilter());
        registration.addUrlPatterns("/*");
        return registration;
    }

    /**
     * RequestContextListener注册
     */
    @Bean
    public ServletListenerRegistrationBean<RequestContextListener> requestContextListenerRegistration() {
        return new ServletListenerRegistrationBean<>(new RequestContextListener());
    }

    /**
     * ConfigListener注册
     */
    @Bean
    public ServletListenerRegistrationBean<ConfigListener> configListenerRegistration() {
        return new ServletListenerRegistrationBean<>(new ConfigListener());
    }

    /**
     * 验证码生成相关
     */
    @Bean
    public DefaultKaptcha kaptcha() {
        Properties properties = new Properties();
        properties.put("kaptcha.border", "no");
        properties.put("kaptcha.border.color", "105,179,90");
        properties.put("kaptcha.textproducer.font.color", "blue");
        properties.put("kaptcha.image.width", "125");
        properties.put("kaptcha.image.height", "45");
        properties.put("kaptcha.textproducer.font.size", "45");
        properties.put("kaptcha.session.key", "code");
        properties.put("kaptcha.textproducer.char.length", "4");
        properties.put("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑");
        Config config = new Config(properties);
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
