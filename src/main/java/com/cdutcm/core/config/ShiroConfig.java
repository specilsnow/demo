package com.cdutcm.core.config;

import java.util.*;

import javax.servlet.Filter;

import com.cdutcm.tcms.shiro.*;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.FirstSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.WebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class ShiroConfig {

  @Bean(name = "shiroFilter")
  public ShiroFilterFactoryBean shiroFilterFactoryBean(
      org.apache.shiro.mgt.SecurityManager securityManager) {

    ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
    HashMap<String, Filter> map = new HashMap<String, Filter>();

    shiroFilterFactoryBean.setFilters(map);
    //Shiro的核心安全接口,这个属性是必须的
    shiroFilterFactoryBean.setSecurityManager(securityManager);
    //要求登录时的链接(可根据项目的URL进行替换),非必须的属性,默认会自动寻找Web工程根目录下的"/login.jsp"页面
    shiroFilterFactoryBean.setLoginUrl("/");
    //登录成功后要跳转的连接,逻辑也可以自定义，例如返回上次请求的页面
    //shiroFilterFactoryBean.setSuccessUrl("/index");
    //用户访问未对其授权的资源时,所显示的连接
    shiroFilterFactoryBean.setUnauthorizedUrl("/page_500.html");
    /*定义shiro过滤器,例如实现自定义的FormAuthenticationFilter，需要继承FormAuthenticationFilter
     **本例中暂不自定义实现，在下一节实现验证码的例子中体现
     */

    /*定义shiro过滤链  Map结构
     * Map中key(xml中是指value值)的第一个'/'代表的路径是相对于HttpServletRequest.getContextPath()的值来的
     * anon：它对应的过滤器里面是空的,什么都没做,这里.do和.jsp后面的*表示参数,比方说login.jsp?main这种
     * authc：该过滤器下的页面必须验证后才能访问,它是Shiro内置的一个拦截器org.apache.shiro.web.filter.authc.FormAuthenticationFilter
     */
    Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
    // 配置退出过滤器,其中的具体的退出代码Shiro已经替我们实现了
    filterChainDefinitionMap.put("/logout", "logout");
    // <!-- 过滤链定义，从上向下顺序执行，一般将 /**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
    // <!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
    filterChainDefinitionMap.put("/", "anon");
    filterChainDefinitionMap.put("/login", "anon");
    filterChainDefinitionMap.put("/recommendate/**", "anon");
    filterChainDefinitionMap.put("/statics/**", "anon");
    filterChainDefinitionMap.put("/hulian/yb/**", "anon");
    filterChainDefinitionMap.put(" /upload/file/**", "anon");
    filterChainDefinitionMap.put("/sendSms", "anon");
    filterChainDefinitionMap.put("/loginhtml", "anon");
    filterChainDefinitionMap.put("/websocket/**", "anon");
    filterChainDefinitionMap.put("/emrfile/**", "anon");
//    filterChainDefinitionMap.put("/chatServer/**", "anon");
    filterChainDefinitionMap.put("/forget/**", "anon");
    filterChainDefinitionMap.put("/userregist", "anon");
    filterChainDefinitionMap.put("/userlogin", "anon");
    filterChainDefinitionMap.put("/validation", "anon");
    filterChainDefinitionMap.put("/interface", "anon");
    filterChainDefinitionMap.put("/interface/**", "anon");
	filterChainDefinitionMap.put("/getallbasedata/**", "anon");
	filterChainDefinitionMap.put("/clinics", "anon");
    filterChainDefinitionMap.put("/forgetPassword", "anon");

    filterChainDefinitionMap.put("/static/down/**", "anon");
    filterChainDefinitionMap.put("/excel/**", "anon");
    filterChainDefinitionMap.put("/bootstrap/**", "anon");
    filterChainDefinitionMap.put("/css/**", "anon");
    filterChainDefinitionMap.put("/easyui/**", "anon");
    filterChainDefinitionMap.put("/img/**", "anon");
    filterChainDefinitionMap.put("/jquery/**", "anon");
    filterChainDefinitionMap.put("/js/**", "anon");
    filterChainDefinitionMap.put("/layui/**", "anon");
    filterChainDefinitionMap.put("/metronic/**", "anon");
    filterChainDefinitionMap.put("/QrImg/**", "anon");
    filterChainDefinitionMap.put("**/*.html", "anon");
    filterChainDefinitionMap.put("/**", "user");
    filterChainDefinitionMap.put("/**", "authc");
    shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

    return shiroFilterFactoryBean;
  }

  /**
   * cacheManager 缓存 redis实现
   * 使用的是shiro-redis开源插件
   *
   * @return
   */
  public RedisCacheManager cacheManager() {
    RedisCacheManager redisCacheManager = new RedisCacheManager();
    redisCacheManager.setRedisManager(redisManager());
    return redisCacheManager;
  }

  /**
   * 不指定名字的话，自动创建一个方法名第一个字母小写的bean
   *
   * @Bean(name = "securityManager")
   */
  @Bean
  public DefaultWebSecurityManager securityManager() {

    DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
    ModularRealmAuthenticator modularRealmAuthenticator = new ModularRealmAuthenticator();
    modularRealmAuthenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
    List<Realm> realms = new ArrayList<Realm>();
    realms.add(userRealm());
/*    realms.add(zsjkRealm());
    realms.add(studentRealm());*/
    securityManager.setAuthenticator(modularRealmAuthenticator);
    securityManager.setRememberMeManager(rememberMeManager());
    securityManager.setSessionManager(sessionManager());
    securityManager.setRealms(realms);
    securityManager.setCacheManager(cacheManager());
    return securityManager;
  }

  
  /**
   * Shiro Realm 继承自AuthorizingRealm的自定义Realm,即指定Shiro验证用户登录的类为自定义的
   */
  @Bean
  public UserRealm userRealm() {
    UserRealm userRealm = new UserRealm();
    //告诉realm,使用credentialsMatcher加密算法类来验证密文
    userRealm.setCredentialsMatcher(hashedCredentialsMatcher());
    userRealm.setCachingEnabled(false);
    return userRealm;
  }
  /**
   * 掌上金课Realm
   */
  @Bean
  public ZsjkRealm zsjkRealm(){
    ZsjkRealm zsjkRealm=new ZsjkRealm();
    zsjkRealm.setCredentialsMatcher(new MyRetryLimitCredentialsMatcher());
    zsjkRealm.setCachingEnabled(false);
    return zsjkRealm;
  }
  /**
   *学生认证Realm
   */
  @Bean
  public StudentRealm studentRealm(){
    StudentRealm studentRealm=new StudentRealm();
    studentRealm.setCredentialsMatcher(new NoReatryLimitCredentialsMatcher());
    studentRealm.setCachingEnabled(false);
    return studentRealm;
  }

  /**
   * 凭证匹配器 （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了 所以我们需要修改下doGetAuthenticationInfo中的代码; ）
   * 可以扩展凭证匹配器，实现 输入密码错误次数后锁定等功能，下一次
   */
  @Bean(name = "credentialsMatcher")
  public HashedCredentialsMatcher hashedCredentialsMatcher() {
    HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();

    hashedCredentialsMatcher.setHashAlgorithmName("md5");//散列算法:这里使用MD5算法;
    hashedCredentialsMatcher.setHashIterations(2);//散列的次数，比如散列两次，相当于 md5(md5(""));
    //storedCredentialsHexEncoded默认是true，此时用的是密码加密用的是Hex编码；false时用Base64编码
    hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);

    return hashedCredentialsMatcher;
  }
//
//  /**
//   * 凭证匹配器 （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了 所以我们需要修改下doGetAuthenticationInfo中的代码; ）
//   * 可以扩展凭证匹配器，实现 输入密码错误次数后锁定等功能，下一次
//   */
//  @Bean(name = "credentialsUnPassMatcher")
//  public HashedCredentialsMatcher hashedcredentialsUnPassMatcher() {
//    HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
////
////    hashedCredentialsMatcher.setHashAlgorithmName("md5");//散列算法:这里使用MD5算法;
////    hashedCredentialsMatcher.setHashIterations(2);//散列的次数，比如散列两次，相当于 md5(md5(""));
////    //storedCredentialsHexEncoded默认是true，此时用的是密码加密用的是Hex编码；false时用Base64编码
////    hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
//
//    return hashedCredentialsMatcher;
//  }

  /**
   * Shiro生命周期处理器
   */
  @Bean
  public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
    return new LifecycleBeanPostProcessor();
  }

  /**
   * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
   * 配置以下两个bean(DefaultAdvisorAutoProxyCreator(可选)和AuthorizationAttributeSourceAdvisor)即可实现此功能
   */
  @Bean
  @DependsOn({"lifecycleBeanPostProcessor"})
  public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
    DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
    advisorAutoProxyCreator.setProxyTargetClass(true);
    return advisorAutoProxyCreator;
  }

  @Bean
  public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
    AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
    authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
    return authorizationAttributeSourceAdvisor;
  }

  /**
   * cookie对象; rememberMeCookie()方法是设置Cookie的生成模版，比如cookie的name，cookie的有效时间等等。
   */
  @Bean
  public SimpleCookie rememberMeCookie() {
//    System.out.println("ShiroConfiguration.rememberMeCookie()");
    //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
    SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
    //<!-- 记住我cookie生效时间7天 ,单位秒;-->
    simpleCookie.setMaxAge(86400000);
    return simpleCookie;
  }

  /**
   * cookie管理对象; rememberMeManager()方法是生成rememberMe管理器，而且要将这个rememberMe管理器设置到securityManager中
   */
  @Bean
  public CookieRememberMeManager rememberMeManager() {
//    System.out.println("ShiroConfiguration.rememberMeManager()");
    CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
    cookieRememberMeManager.setCookie(rememberMeCookie());
    //rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
//         cookieRememberMeManager.setCipherKey(Base64.decode("2AvVhdsgUs0FSA3SDFAdag=="));
    return cookieRememberMeManager;
  }

//  @Bean
//  public SessionDAO sessionDAO() {
//    return new EnterpriseCacheSessionDAO();
//  }
  /**
   * RedisSessionDAO shiro sessionDao层的实现 通过redis
   * 使用的是shiro-redis开源插件
   */
  @Bean
  public RedisSessionDAO redisSessionDAO() {
    RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
    redisSessionDAO.setRedisManager(redisManager());
    return redisSessionDAO;
  }
  /**
   * 配置shiro redisManager
   * 使用的是shiro-redis开源插件
   *
   * @return
   */
//  @Value("${spring.redis.host}")
//  private String host;
  @Bean
  public RedisManager redisManager() {
    RedisManager redisManager = new RedisManager();
    redisManager.setHost("127.0.0.1");
    redisManager.setPort(6379);
    redisManager.setExpire(3600);// 配置缓存过期时间
    redisManager.setTimeout(300);
    redisManager.setPassword("cdutcm@123");
    // redisManager.setPassword(password);
    return redisManager;
  }

  //使用Shiro自带的Session管理器
  @Bean
  public WebSessionManager sessionManager() {
    DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
    sessionManager.setSessionDAO(redisSessionDAO());
    //sessionManager.setGlobalSessionTimeout(1000*60*60);
    //设置Cookie中返回的SessionID的名字，默认是JSESSIONID
    sessionManager.getSessionIdCookie().setName("Chd6SESSIONID");
    return sessionManager;
  }





}