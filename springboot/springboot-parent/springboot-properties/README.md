# spring-boot-demo-properties

### 读取配置文件的两种方式

#### ApplicationConfig.java（第一种方式）

```java
@Component
@Data
public class ApplicationConfig {
	@Value("${application.name}")
	private String name;
	@Value("${application.version}")
	private String version;
}
```

#### AuthorConfig.java（第二种方式）

```java
@Data
@ConfigurationProperties(prefix = "application.author")
@Component
public class AuthorConfig {
	private String name;
	private String website;
	private String qq;
	private String phoneNumber;
}
```