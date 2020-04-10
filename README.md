# springcloud-parent

一个spring cloud的demo

## 备注

* 配置中心(下午思考了一下,感觉架构不太对劲)
    1. 三个仓库对应三个环境,其中prod设置了仅读取权限的密钥
    2. 三个仓库的配置(除开密钥跟Patter跟uri)应尽量相同,否则会出现一些初始化的问题
        ```
        举个例子
        在dev与test环境删掉ignore-local-ssh-settings与strict-host-key-checking两项配置
        先启动config,后启动dev或者test环境的web
        再关掉web,切换到prod环境,会抛出一个HostKey有关的异常
        应该是先启动dev(或test)获取配置文件后,会使用默认值初始化上述两项配置
        初始化完毕后当使用prod配置时,就因为配置缺失,导致报错
        ```
    3. 关于config中有两个配置文件bootstrap.yml跟application.yml
        ```
        如果config的服务名配置到application.yml而不是bootstrap.yml
        app-web启动后会使用config的服务名注册到eureka
        总结
        config的两个文件中
        application.yml放入关于spring-cloud-config的相关配置
        bootstrap.yml放入config服务自身的配置,如应用名,保证不覆盖app-web服务
        ```
    4. 思考:如何屏蔽prod环境的配置跟私钥,对团队中部分开发者透明
        
        个人认为,实际开发中,config应当作为基础设施优先开发完毕,后续不再直接维护
        所以对于权限较少的团队成员,仅开放业务模块代码跟dev环境的仓库即可,不知道这样理解对不对

注册中心(仅注册功能,还没有覆盖所有业务模块)

网关(只配置了映射,暂未配置配置中心)

业务模块(只写了web一点点)

公共模组(完全没写)

## 其他
* 启动顺序(同级不分先后)
    * 注册中心
    * rabbitmq 全局搜ip改mq配置 192.168.1.10
        * 配置中心
            * app-*
            * 网关

bus刷新配置地址 POST localhost:13000/actuator/bus-refresh
如果是自定义参数,需要在使用的地方加上@RefreshScope

### 2020/4/9遇到一个很特别的问题
通过官方文档配置的config-server始终无法正常请求配置文件
`http://127.0.0.1:13000/springcloud-app-web/dev`请求返回400 Cannot load environment

0. 按照惯性思维,一般都是配置问题跟依赖问题,中间有考虑过git配置文件拉不下来,但是没有往git客户端上面去思考
1. 一开始以为自己配置少了什么,那几个配置翻来覆去,然后远程仓库的配置文件也改来改去,始终不通过
2. 然后通过降低版本进行测试(2.2.2左右的版本,已经把启动时映射的日志都去掉了),还是不行
3. 后面又从github上拉取别人的项目启动,居然也出现同样的问题
4. 尝试DEBUG
    1. 原本想着重写400异常,好进入断点
    2. 想到spring.cloud.config.server.git.uri配置错误了能在控制台打印日志,可以快速进入相关代码
    3. 最后通过追踪发现是调用了本地的git客户端
    4. 读取了参数http.postBuffer 5120000000
    5. 上述参数超出了int最大值21亿,故框架中抛出异常
    6. 相关链接`https://stackoverflow.com/questions/51526733/spring-cloud-config-clone-on-start-http-postbuffer-issue`
    7. 异常位置`org.eclipse.jgit.lib.DefaultTypedConfigGetter.getInt`
5. 解决方法: 命令行设置git参数`git config --global http.postBuffer 524288000`