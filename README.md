# springcloud-parent

一个spring cloud的demo

## 启动顺序(同级不分先后)

* rabbitmq 全局搜ip修改 192.168.1.10
    * 配置中心
        * 注册中心
            * app-*
            * 网关

## 模块进度

* 配置中心(为其他所有服务提供动态配置,跨环境)
    1. 两个环境地址
        1. https://gitee.com/ve0/springcloud-config-repo-dev
        2. https://gitee.com/ve0/springcloud-config-repo-prod**可通过部署密钥下载查看*
    2. 关于config中有两个配置文件bootstrap.yml跟application.yml
    3. 思考:如何屏蔽prod环境的配置跟私钥,对团队中部分开发者透明
        1. 不开放config权限?
        2.config似乎有一种加密配置,可以将配置文件转换成密文?
        
* 注册中心

* 网关(已配置转发,待实现高级功能,如限流,权限等)

* 业务模块(已整合openfeign,没写业务)

* 公共模组(完全没写)

bus刷新配置地址 POST localhost:13000/actuator/bus-refresh
如果使用自定义参数,需要在相应类上加@RefreshScope

## 问题汇总
1. config-server无法正常请求配置文件(服务端请求出错,不是客户端)
    1. 异常表现
        `http://127.0.0.1:13000/springcloud-app-web/dev`请求返回400 Cannot load environment
    2. 故障排查
        ```
        config-server调用了本地git客户端拉取配置仓库
        读取其参数http.postBuffer 5120000000
        config框架中对上述值进行限制,条件是小于int型最大值
        故抛出异常,被springmvc拦截并处理成没有任何日志的异常
        异常位置org.eclipse.jgit.lib.DefaultTypedConfigGetter.getInt
        ```
    3. 解决方法: 命令行设置git参数`git config --global http.postBuffer 524288000`
    4. 相关链接`https://stackoverflow.com/questions/51526733/spring-cloud-config-clone-on-start-http-postbuffer-issue`
2. app-web服务先启动dev环境后启动prod会抛出一个HostKey有关的异常
    1. 异常表现: app-web无法启动
    2. 故障排查
        ```
        config-server服务多仓库配置中,dev缺少ignore-local-ssh-settings与strict-host-key-checking两项配置
        尽管dev环境没有使用私钥,但初始化的时候也会加载上述两项配置并设置为默认值
        后面启动prod环境不再加载这两项,故使用的是默认值,故此报错
        ```
    3. 解决方法: dev仓库配置添加ignore-local-ssh-settings与strict-host-key-checking两项配置
3. 业务服务(app)显示端口占用
    1. 异常表现: app-web无法启动,或启动成功后注册中心显示的服务名称不是app-web,而是config-server
    2. 故障排查
        ```
        bootstrap.yml加载时间早于application.yml
        app-web加载完bootstrap.yml的配置后,会继续加载config-server的application.yml,故使用了config-server的端口或者服务名
        ```
    3. 解决方法: 
        ```
        config-server配置文件拆分成两个
        application.yml放入关于spring-cloud-config的相关配置
        bootstrap.yml放入config服务自身的配置,如应用名,保证不会传递给其他服务
        ```