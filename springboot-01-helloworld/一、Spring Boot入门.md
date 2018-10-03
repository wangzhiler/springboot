# 一、Spring Boot入门

## 1. Spring Boot简介

> 简化Spring应用开发的一个框架
>
> 整个Spring技术栈的整合
>
> j2ee开发的一站式解决方案

## 2. 微服务

2014，martin fowler

微服务：架构风格

一个应用应该是一组小型服务；可以通过http方式进行互通

单体应用：ALL IN ONE

每一个功能元素最终都是一个可独立替换和独立升级的软件单元



环境：

jdk1.8 

maven3.x 

springboot1.5.9



## 3. Spring Boot HelloWorld

功能：浏览器发送hello请求，服务器接受请求并处理，响应hello world字符串

```java
@SpringBootApplication
public class x{
    public static void main(String[] args){
        //Spring应用启动起来
        SpringApplication.run(x.class, args);
    }
}
```

@SpringBootApplication 标注在某各类上说明这个类是SpringBoot的主配置类

@SpringBootConfiguration: SpringBoot的配置类

​	标注在某各类上，表示这是一个SpringBoot的配置类

​	@Configuration: 配置类上来标注这个注解

​	配置类---配置文件; 配置类也是容器中的一个组件; @Component

@EnableAutoConfiguration: 开启自动配置功能

```java
@AutoConfigurationPackage
@Import(EnableAutoConfigurationImportSelector.class)
public @interface EnableAutoConfiguration{}
```

​	@AutoConfigurationPackage: 自动配置包

​		@Import(AutoConfigurationPackages.Register.class)

​		Spring底层注解@Import 给容器中导入一个组件; 导入的组件由

AutoConfigurationPackages.Register.class;

将主配置类 （@SpringBootApplication标注的类）的所在包及下面所有子包里面的所有组件扫描到Spring容器

​	@Import(AutoConfigurationPackages.Register.class);

​		给容器中导入组件?

​		EnableAutoConfigurationImportSelector; 导入哪些组件的选择器

​		将所有需要导入的组件以全类名的方式返回； 这些组件就会被添加到容器中

​		会给容器中导入非常多的自动配置类(xxxAutoConfiguration); 就是给容器中导入这个场景需要的所有组件，并配置号这些组件

![](C:\Users\thinkpad\Documents\springboot\images\屏幕快照 2018-09-17 下午4.33.57.png)

有了自动配置类，免去了我们手动编写配置注入功能组件等的工作

SpringFactoriesLoader.loadFactoryNames(EnableAutoConfiguration.class, classLoader);

**SpringBoot在启动的时候从类路径下的META-INF/spring.factories中获取EnableAutoConfiguration指定的值，将这些值作为自动配置类导入到容器中，自动配置类就生效，帮我们进行自动配置工作**

J2ee的整体整合解决方案和自动配置都在spring-boot-autoconfigure-1.5.9RELEASE.jar中



## 4. Spring Initializer

Resources 目录结构

* static：保存所有静态资源
* template: 保存所有的模板页面；（SpringBoot默认jar包使用嵌入式的Tomcat，默认不支持JSP页面）可以使用模板引擎 freemarker rhymeleaf
* application.properties: SpringBoot应用的配置文件；可以修改一些默认设置



# 二、配置文件

## 1. 配置文件

SpringBoot使用一个全局的配置文件，配置文件名是固定的

* application.properties
* application.yml

配置文件的作用：修改SpringBoot自动配置的默认值，SpringBoot在底层都给我们自动配置好

>  YAML(YAML Ain't Markup Language)

标记语言：YAML以数据为中心，比json xml更适合做配置文件

```
server:
  port: 8080
```

## 2. YAML语法

### 1. 基本语法

K: V  表示一堆键值对 空格必须有

以空格的缩进来控制层级关系；只要是左对齐的一列数据，都是同一层级的，属性和值也是大小写敏感



### 2. 值的写法

**字面量： 普通的值（数字，字符串，布尔）**

​	k: v  字面直接来写

​	字符串默认不用加上单引号或者双引号；

​	"": 双引号，不会转义字符串里面的特殊字符，特殊字符会作为本身想表示的意思

​		name: "zhangsan \n lisi" 

​		output:  zhangsan

​				lisi

​	'': 单引号， 会转义特殊字符，特殊字符最终只是一个普通的字符串数据

​		output: zhangsan \n lisi

**对象、Map（属性和值）（键值对）**

​	k: v 在下一行来写对象的属性和值的关系； 注意缩进

​		对象还是k: v的方式

```yaml
friends:
	lastName: zhangsan
	age: 20
```

行内写法

```yaml
friends: {lastname: zhangsan, age: 18}
```



**数组（List、Set）**

用-值表示数组中的一个元素

```yaml
pets:
	- cat
	- dog
	- pig
```

行内写法

```yaml
pets: [cat, dog, pig]
```

### 3. 配置文件注入 

```yaml
person:
  lastName: zhangsan
  age: 18
  boss: false
  birth: 2018/01/01
  maps: {k1: v1, k2: v2}
  lists:
    - listi
    - zhangsan
  dogs:
    name: 小狗
    age: 2
```

javaBean:

```java
/**
 *  将配置文件中配置的每一个属性的置，映射到这个组件中
 *  @ConfigurationProperties: 告诉SpringBoot本类中的所有属性和配置文件中相关的配置进行绑定
 *      prefix="person": 配置文件中哪个下面的所有属性进行一一映射
 *  只有这个组件时容器中的组件，才能容器提供的@ConfigurationProperties功能
 */

@Component
@ConfigurationProperties(prefix = "person")
public class Person {
    private String lastName;
    private Integer age;
    private Boolean boss;
    private Date birth;

    private Map<String, Object> maps;
    private List<Object> lists;
    private Dog dog;
```

#### 1) @Value @ConfigurationProperties

|                      | @ConfigurationProperties   | @Value     |
| -------------------- | -------------------------- | ---------- |
| 功能                 | 批量注入配置为文件中的属性 | 一个个指定 |
| 松散绑定（松散语法） | 支持                       | 不支持     |
| SpEL eg. #{11*2}     | 不支持                     | 支持       |
| JSR303数据校验       | 支持                       | 不支持     |
| 复杂类型封装         | 支持                       | 不支持     |

配置文件yml还是properties他们都能获取到值

#### 2) @PropertySource和@ImportResource 

@PropertySource: 加载指定的配置文件

```java
@PropertySource(value = {"classpath:person.properties"})
@Component
@ConfigurationProperties(prefix = "person")
@Validated
public class Person {
```

@ImportResource: 导入Spring配置文件，让配置文件里面的内容生效

SpringBoot里面没有Spring的配置文件，我们自己编写的配置文件，也不能自动识别

想让Spring的配置文件生效，加载进来；@ImportResource标注在配置类上

```java
@ImportResource(locations={"classpath:beans.xml"})
导入Spring的配置文件让其生效
```



Springboot推荐给容器中添加组件的方式；推荐使用全注解的方式

1. 配置类====Spring配置文件
2. 使用@Bean给容器中添加组件

```java
/**
 * @Configuration: 指明当前类是一个配置类; 就是来替代之前的Spring配置文件
 */
@Configuration
public class MyAppConfig {
    //将方法的返回值添加到容器中; 容器中这个组件的默认id是方法名
    @Bean
    public HelloService helloService() {
        System.out.println("配置类@Bean给容器中添加组件。。。");
        return new HelloService();
    }
}
```



### 4. 配置文件占位符

#### 1）随机数

```java
${random.value} ${random.int} ${random.long}
${random.int(10)} ${random.int[1024,65536]}
```

#### 2) 占位符获取之前配置的值，如果没有可以是用:指定默认值

```properties
person.last-name=张三${random.uuid}
person.age=${random.int}
person.boss=false
person.birth=2018/01/01
person.maps.k1=v1
person.maps.k2=v4
person.lists=a,b,c
person.dog.name=${person.last-name}_小狗
person.dog.age=${person.hello:hello}2
```

### 5. Profile

#### 1) 多Profile文件

我们在主配置文件编写的时候，文件名可以是application-{profile}.properties/yml

默认使用application.properties配置

#### 2) yml支持多文档块方式

```yaml
server:
  port: 8083
spring:
  profiles:
    active: dev
---
server:
  port: 8084
spring:
  profiles: dev
---
server:
  port: 8085
spring:
  profiles: prod
```

#### 3) 激活指定profile

1. 在配置文件中指定spring.profiles.active=dev

2. 命令行

   java -jar xxx.jar --spring.profiles.active=dev

   可以直接在测试的时候，配置传入命令行参数

3. 虚拟机参数

   VM options: -Dspring.profiles.active=dev

### 6. 配置文件加载位置

springboot启动会扫描以下位置的application.properties或者application.yml文件作为Spring boot的默认设置文件。优先级从上往下，从高到低，高的覆盖低的

-file:./config/

-file:./

-classpath:/config/

-classpath:/

SpringBoot会从这四个位置全部加载主配置文件；互补配置

### 7. 外部配置加载顺序

SpringBoot也可以从以下位置加载配置； 优先级从高到低；高优先级的配置覆盖低优先级的配置，所有的配置会形成互补配置

1. **命令行参数**

java -jar xxx.jar --server.port=8087

2. 来自java:comp/env的NDI属性

3. java系统属性（System.getProperties()）

4. 操作系统环境变量

5. RandomValuePropertySource配置的random.*属性值

   

   **优先加载带profile，再来加载不带profile**.都由jar外向jar内

6. jar包外application-{profile}.properties或yml配置文件（带spring.profile）

7. jar包内application-{profile}.properties或yml配置文件（带spring.profile）

8. jar包外application.properties或yml配置文件（不带spring.profile）

9. jar包内application.properties或yml配置文件（不带spring.profile）



10. @Configuration注解类上的@PropertySource
11. 通过SpringApplication.setDefaultProperties指定的默认属性

### 8. 自动配置原理

#### 1. 自动配置原理

1） SpringBoot启动的时候在加载了主配置类，开启了自动配置功能@EnableAutoConfiguration

2）@EnableAutoConfiguration作用：

* @Import({AutoConfigurationImportSelector.class}) 利用该选择器给容器导入了一些组件
* 可查看selectImports()的内容

```java
public String[] selectImports(AnnotationMetadata annotationMetadata) {
    ...
        //获取候选池的的配置
        List<String> configurations = this.getCandidateConfigurations(annotationMetadata, attributes);
    ...
        return StringUtils.toStringArray(configurations);
}
```

```java
protected List<String> getCandidateConfigurations(AnnotationMetadata metadata, AnnotationAttributes attributes) {
    //
        List<String> configurations = SpringFactoriesLoader.loadFactoryNames(this.getSpringFactoriesLoaderFactoryClass(), this.getBeanClassLoader());
        return configurations;
    }
```

```java
classLoader != null?classLoader.getResources("META-INF/spring.factories"):ClassLoader.getSystemResources("META-INF/spring.factories");
```

**loadFactoryNames扫描所有jar包类路径下 meta-inf/spring.factories**

**把扫描到的文件内容包装成properties**

**从properties中获取到的EnableAutoConfiguration.class类对应的值，然后把他们添加在容器中**

![](C:\Users\thinkpad\Documents\springboot\images\新建位图图像.bmp)

每一个这样的xxxAutoConfiguration类都是容器中的一个组件，都加入到容器中

3） 每一个自动配置类，进行自动配置功能

4）以**HttpEncodingAutoConfiguration**为例解释自动配置原理

```java
//表示这是一个配置类，以前编写的配置文件一样，也可以给容器中添加组件
@Configuration 

//启动指定类ConfigurationProperties功能；将配置文件中对应的值和HttpEncodingProperties绑定起来
//并把HttpEncodingProperties加入到ioc容器中
@EnableConfigurationProperties({HttpEncodingProperties.class}) 

//Spring底层@Conditional注解，根据不同的条件，如果满足指定条件，整个配置类里面的配置就会生效；
//此处在判断当前应用是否是Web应用，是则当前配置类生效
@ConditionalOnWebApplication(type = Type.SERVLET)

//判断当前项目有没有这个类  CharacterEncodingFilter; SpringMVC中进行乱码处理的过滤器
@ConditionalOnClass({CharacterEncodingFilter.class})

//判断配置文件中是否存在某个配置 spring.http.encoding.enabled; matchMissing=true表示如果不存在，也是成立的
//即使配置文件中不配置，也是默认生效的
@ConditionalOnProperty(
    prefix = "spring.http.encoding",
    value = {"enabled"},
    matchIfMissing = true
)
public class HttpEncodingAutoConfiguration {
    //已经和SpringBoot的配置文件映射了
    private final HttpEncodingProperties properties;
    
    //只有一个有参构造器的情况下，参数的值就会从容器中拿
    public HttpEncodingAutoConfiguration(HttpEncodingProperties properties) {
        this.properties = properties;
    }

    @Bean //给容器中添加一个组件，这个组件的某些之需要从properties中获取
    @ConditionalOnMissingBean
    public CharacterEncodingFilter characterEncodingFilter() {
        CharacterEncodingFilter filter = new OrderedCharacterEncodingFilter();
        filter.setEncoding(this.properties.getCharset().name());
        filter.setForceRequestEncoding(this.properties.shouldForce(org.springframework.boot.autoconfigure.http.HttpEncodingProperties.Type.REQUEST));
        filter.setForceResponseEncoding(this.properties.shouldForce(org.springframework.boot.autoconfigure.http.HttpEncodingProperties.Type.RESPONSE));
        return filter;
    }
```

根据当前不同的条件判断，决定这个配置类是否生效？

一旦这个配置类生效；这个配置类就会给容器中添加各种组件，这些组件的属性是从对应的Properties类中获取的，这些类里面的每一个属性又是和配置文件绑定的

5） 所有配置文件中能配置的属性都是在xxxxProperties类中封装着，配置文件能配置什么就可以参照某个功能对应的这个属性类

```java
@ConfigurationProperties(prefix = "spring.http.encoding")
//从配置文件中获取指定的值和bean的属性进行绑定
public class HttpEncodingProperties {
```

```properties
#我们能配置的属性都是来源于这个功能的properties类
spring.http.encoding.enabled=true
spring.http.encoding.charset=utf-8
```

精髓：

**1）SpringBoot启动会加载大量的自动配置类**

**2）我们看我们需要的功能有没有SpringBoot默认写好的自动配置类**

**3）我们再来看这个自动配置类中到底配置了哪些组件（只要我们要用的组件有，我们就不需要再来配置了）**

**4）给容器中自动配置类添加组件的时候，会从Properties类中获取某些属性。我们就可以在配置文件中指定这些属性的值**

xxxAutoConfiguration: 自动配置类

给容器中添加组件

xxxProperties:封装配置文件中相关属性

#### 2. 细节

1）@Conditional派生注解（Spring原生的@Conditional作用）

作用：必须是@Conditional指定的条件成立，才给容器中添加组件，配置类里面的所有内容才生效

| @Conditional扩展注解            | 作用（判断是否满足当前指定条件）                   |
| ------------------------------- | -------------------------------------------------- |
| @ConditionalOnJava              | 系统的java是否符合要求                             |
| @ConditionalOnBean              | 容器中是否存在Bean                                 |
| @ConditionalOnMissingBean       | 容器不存在Bean                                     |
| @ConditionalOnExpression        | 满足SpEL表达式指定                                 |
| @ConditionalOnClass             | 系统中有指定的类                                   |
| @ConditionalOnMissingClass      | 没有指定类                                         |
| @ConditionalOnSingleCandidate   | 容器中只有一个指定的Bean，或者这个Bean是首选的Bean |
| @ConditionalOnProperty          | 系统中指定的属性是否由指定的值                     |
| @ConditionalOnResource          | 类路径下是否存在指定资源文件                       |
| @ConditionalOnWebApplication    | 当前是web环境                                      |
| @ConditionalOnNotWebApplication | 不是web                                            |
| @ConditionalOnJndi              | JNDI存在指定项                                     |

**自动配置类必须在一定的条件下生效**

我们如何知道哪些生效了？

```properties
#开启Springboot的debug
debug=true
```

```

============================
CONDITIONS EVALUATION REPORT
============================
Positive matches: 自动配置类启动的
-----------------
   CodecsAutoConfiguration matched:
   ……
Negative matches: 没有匹配启动成功的自动配置类
-----------------
   ActiveMQAutoConfiguration:
   ……
```





# 三、日志

## 1、日志框架

小张；开发一个大型系统；

 1、System.out.println(“”)；将关键数据打印在控制台；去掉？写在一个文件？

 2、框架来记录系统的一些运行时信息；日志框架 ； zhanglogging.jar；

 3、高大上的几个功能？异步模式？自动归档？xxxx？ zhanglogging-good.jar？

 4、将以前框架卸下来？换上新的框架，重新修改之前相关的API；zhanglogging-prefect.jar；

 5、JDBC—数据库驱动；

 写了一个统一的接口层；日志门面（日志的一个抽象层）；logging-abstract.jar；

 给项目中导入具体的日志实现就行了；我们之前的日志框架都是实现的抽象层；

**市面上的日志框架；**

JUL、JCL、Jboss-logging、logback、log4j、log4j2、slf4j….

| 日志门面 （日志的抽象层）                                    | 日志实现                                         |
| ------------------------------------------------------------ | ------------------------------------------------ |
| ~~JCL（Jakarta Commons Logging）~~SLF4j（Simple Logging Facade for Java） **~~jboss-logging~~** | Log4j JUL（java.util.logging） Log4j2**Logback** |

左边选一个门面（抽象层）、右边来选一个实现；

日志门面： SLF4J；

日志实现：Logback；

SpringBoot：底层是Spring框架，Spring框架默认是用JCL；‘

 **==SpringBoot选用 SLF4j和logback；==**



## 2、SLF4j使用

### 1、如何在系统中使用SLF4j [https://www.slf4j.org](https://www.slf4j.org/)

以后开发的时候，日志记录方法的调用，不应该来直接调用日志的实现类，而是调用日志抽象层里面的方法；

给系统里面导入slf4j的jar和 logback的实现jar

```
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloWorld {
  public static void main(String[] args) {
    Logger logger = LoggerFactory.getLogger(HelloWorld.class);
    logger.info("Hello World");
  }
}
```

图示；

![](C:\Users\thinkpad\Documents\springboot\images\001.png)

每一个日志的实现框架都有自己的配置文件。使用slf4j以后，**配置文件还是做成日志实现框架自己本身的配置文件；**

### 2、遗留问题

a（slf4j+logback）: Spring（commons-logging）、Hibernate（jboss-logging）、MyBatis、xxxx

统一日志记录，即使是别的框架和我一起统一使用slf4j进行输出？

![](C:\Users\thinkpad\Documents\springboot\images\002.png)

**如何让系统中所有的日志都统一到slf4j；**

==1、将系统中其他日志框架先排除出去；==

==2、用中间包来替换原有的日志框架；==

==3、我们导入slf4j其他的实现==

## 3、SpringBoot日志关系

```
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter</artifactId>
</dependency>
```

SpringBoot使用它来做日志功能；

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-logging</artifactId>
  </dependency>
```

底层依赖关系

[
![](C:\Users\thinkpad\Documents\springboot\images\003.png)]

总结：

 1）、SpringBoot底层也是使用slf4j+logback的方式进行日志记录

 2）、SpringBoot也把其他的日志都替换成了slf4j；

 3）、中间替换包？

```
@SuppressWarnings("rawtypes")
public abstract class LogFactory {

    static String UNSUPPORTED_OPERATION_IN_JCL_OVER_SLF4J = "http://www.slf4j.org/codes.html#unsupported_operation_in_jcl_over_slf4j";

    static LogFactory logFactory = new SLF4JLogFactory();
```

![](C:\Users\thinkpad\Documents\springboot\images\004.png)

 4）、如果我们要引入其他框架？一定要把这个框架的默认日志依赖移除掉？

 Spring框架用的是commons-logging；

```
<dependency>
  <groupId>org.springframework</groupId>
  <artifactId>spring-core</artifactId>
  <exclusions>
    <exclusion>
      <groupId>commons-logging</groupId>
      <artifactId>commons-logging</artifactId>
    </exclusion>
  </exclusions>
</dependency>
```

**==SpringBoot能自动适配所有的日志，而且底层使用slf4j+logback的方式记录日志，引入其他框架的时候，只需要把这个框架依赖的日志框架排除掉即可；==**

## 4、日志使用

### 1、默认配置

SpringBoot默认帮我们配置好了日志；

```
//记录器
Logger logger = LoggerFactory.getLogger(getClass());
@Test
public void contextLoads() {
  //System.out.println();

  //日志的级别；
  //由低到高   trace<debug<info<warn<error
  //可以调整输出的日志级别；日志就只会在这个级别以以后的高级别生效
  logger.trace("这是trace日志...");
  logger.debug("这是debug日志...");
  //SpringBoot默认给我们使用的是info级别的，没有指定级别的就用SpringBoot默认规定的级别；root级别
  logger.info("这是info日志...");
  logger.warn("这是warn日志...");
  logger.error("这是error日志...");


}
```

```
日志输出格式：
%d表示日期时间，
%thread表示线程名，
%-5level：级别从左显示5个字符宽度
%logger{50} 表示logger名字最长50个字符，否则按照句点分割。 
%msg：日志消息，
%n是换行符
-->
%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n
```

SpringBoot修改日志的默认配置

```
logging.level.com.atguigu=trace


#logging.path=
# 不指定路径在当前项目下生成springboot.log日志
# 可以指定完整的路径；
#logging.file=G:/springboot.log

# 在当前磁盘的根路径下创建spring文件夹和里面的log文件夹；使用 spring.log 作为默认文件
logging.path=/spring/log

#  在控制台输出的日志的格式
logging.pattern.console=%d{yyyy-MM-dd} [%thread] %-5level %logger{50} - %msg%n
# 指定文件中日志输出的格式
logging.pattern.file=%d{yyyy-MM-dd} === [%thread] === %-5level === %logger{50} ==== %msg%n
```

| logging.file | logging.path | Example  | Description                        |
| ------------ | ------------ | -------- | ---------------------------------- |
| (none)       | (none)       |          | 只在控制台输出                     |
| 指定文件名   | (none)       | my.log   | 输出日志到my.log文件               |
| (none)       | 指定目录     | /var/log | 输出到指定目录的 spring.log 文件中 |

### 2、指定配置

给类路径下放上每个日志框架自己的配置文件即可；SpringBoot就不使用他默认配置的了

| Logging System          | Customization                                                |
| ----------------------- | ------------------------------------------------------------ |
| Logback                 | `logback-spring.xml`, `logback-spring.groovy`, `logback.xml` or `logback.groovy` |
| Log4j2                  | `log4j2-spring.xml` or `log4j2.xml`                          |
| JDK (Java Util Logging) | `logging.properties`                                         |

logback.xml：直接就被日志框架识别了；

**logback-spring.xml**：日志框架就不直接加载日志的配置项，由SpringBoot解析日志配置，可以使用SpringBoot的高级Profile功能

```
<springProfile name="staging">
    <!-- configuration to be enabled when the "staging" profile is active -->
    可以指定某段配置只在某个环境下生效
</springProfile>
```

如：

```
<appender name="stdout" class="ch.qos.logback.core.ConsoleAppender">
        <!--
        日志输出格式：
      %d表示日期时间，
      %thread表示线程名，
      %-5level：级别从左显示5个字符宽度
      %logger{50} 表示logger名字最长50个字符，否则按照句点分割。 
      %msg：日志消息，
      %n是换行符
        -->
        <layout class="ch.qos.logback.classic.PatternLayout">
            <springProfile name="dev">
                <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} ----> [%thread] ---> %-5level %logger{50} - %msg%n</pattern>
            </springProfile>
            <springProfile name="!dev">
                <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} ==== [%thread] ==== %-5level %logger{50} - %msg%n</pattern>
            </springProfile>
        </layout>
    </appender>
```

如果使用logback.xml作为日志配置文件，还要使用profile功能，会有以下错误

`no applicable action for [springProfile]`

## 5、切换日志框架

可以按照slf4j的日志适配图，进行相关的切换；

slf4j+log4j的方式；

```
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-web</artifactId>
  <exclusions>
    <exclusion>
      <artifactId>logback-classic</artifactId>
      <groupId>ch.qos.logback</groupId>
    </exclusion>
    <exclusion>
      <artifactId>log4j-over-slf4j</artifactId>
      <groupId>org.slf4j</groupId>
    </exclusion>
  </exclusions>
</dependency>

<dependency>
  <groupId>org.slf4j</groupId>
  <artifactId>slf4j-log4j12</artifactId>
</dependency>
```

切换为log4j2

```
   <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <exclusions>
                <exclusion>
                    <artifactId>spring-boot-starter-logging</artifactId>
                    <groupId>org.springframework.boot</groupId>
                </exclusion>
            </exclusions>
        </dependency>

<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-log4j2</artifactId>
</dependency>
```

