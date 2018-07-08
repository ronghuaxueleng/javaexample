# springboot-elasticsearch

> 关于 ES 的更多内容，可以参考[官方文档](https://www.elastic.co/guide/en/elasticsearch/reference/current/getting-started.html)
>
> 本例对应博客文章地址，http://xkcoding.com/2018/01/12/elasticsearch_note.html

ElasticSearch 的 demo 我这里没有使用 spring-data-elasticsearch，我使用的是原生的方式

操作 ElasticSearch 由很多种方式：

1. ES 官方提供的原生方式，**本例子使用这种方式**，这种方式的好处是高度自定义，并且可以使用最新的 ES 版本，缺点就是所有操作都得自己写。
2. 使用 Spring 官方提供的 spring-data-elasticsearch，这里给出地址 https://projects.spring.io/spring-data-elasticsearch/ ，采用的方式类似 JPA，并且为 SpringBoot 提供了一个 `spring-boot-starter-data-elasticsearch`，优点是操作 ES 的方式采用了 JPA 的方式，都已经封装好了，缺点是版本得随着官方慢慢迭代，不能使用 ES 的最新特性。