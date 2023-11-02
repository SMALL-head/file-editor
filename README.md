# 一、模块划分
## 1.1 基本功能模块
包含输入parsing，各个Command功能实现等，放置在command中

## 1.2 日志模块
放置在.log目录下

## 1.3 统计模块
放置在status目录下

## 1.4 其他目录说明
log目录存放日志模块中生成数据文件  
status目录中存放统计模块中的生成的数据文件

# 二、采用的设计模式说明
1. 工厂模式。由于我们需要通过字符串(例如"load xxx")来生成命令(`LoadCommand`)，因此我们采用工厂模式进行创建。详细可参照src/main/java/command/factory中的实现
2. 命令模式。为了实现指令执行的效果，切指令执行可以满足redo和undo，我们采用命令模式来设计。具体来说就是创建AbstractCommand，这个类中execute方法是关键，所有实现类都在这个方法实现了执行逻辑。
3. 单例模式。我们使用了`FileEditorContext`这个类来存放一些全局属性。为了满足这种全局性，我们采用单例模式——隐藏了构造函数，使用final static public关键字来创建单例对象，并通过一个get方法将单例对象对外暴露
4. 观察者模式。日志的记录采用了观察者模式。即指令（`AbstractCommand`）作为Subject，日志模块（`CommandLogger`）作为Observer，当指令执行的时候去notify日志模块来记录日志。

# 三、项目运行说明及自动化测试说明
项目结构在最终实现自动化测试的过程中，进行了简单的重构：使用Maven作为包管理工具，使用Junit4作为测试工具。  
运行项目之前需要对环境进行配置。jdk版本需要17及以上，maven版本需要3.5及以上。
以下是我通过`mvn -V`的结果。
```text
Apache Maven 3.5.4 (1edded0938998edf8bf061f1ceb3cfdeccf443fe; 2018-06-18T02:33:14+08:00)
Maven home: /opt/homebrew/Cellar/maven@3.5/3.5.4_1/libexec
Java version: 17.0.6, vendor: GraalVM Community, runtime: /Library/Java/JavaVirtualMachines/graalvm-ce-java17-22.3.1/Contents/Home
Default locale: zh_CN_#Hans, platform encoding: UTF-8
OS name: "mac os x", version: "14.0", arch: "aarch64", family: "mac"
```
使用`mvn test` 单独运行测试结果  
使用`mvn install` 不仅会进行单元测试，而且得到打包结果，jar包会存储在target目录下  
有了jar包后可以通过`java -jar target/FileEditor-1.0-jar-with-dependencies.jar`运行

# 四、开发人员必读
1. 任何一个指令都需要实现或者重写三个方法: 
   - `public boolean isRecordable()`
   - `public void execute()`
   - `public Operator reverseOperator()`   
2. 请各位在编写自己的Command时，最后一行一定加上`super.execute()`，这条命令会根据`isRecordable()`来自动判断是否需要放进执行栈中 
3. FileEditorContext.getContext()可以不用作为参数传递，在你需要的时候调用这个方法就好
4. 每个指令创建的时候都把`stringCommand`传入构造函数里，这样方便日志模块记录(参照我的LoadCommand和SaveCommand)
5. 新指令的增加流程
   - 在command.commandImpl中完成实现类，这个过程需要注意构造函数的编写
   - 在command.factory中完成工厂函数
   - 记录测试的输入，之后zyc来汇总
6. FileEditorContext的每个属性我都设置了get和set方法，放心取用！！

关于日志模块，拟采用观察者模式。fu同学和zyc交流一下。  
关于stat命令应该需要修改我的load和save命令。有问题来和zyc交流。我在LoadCommand中的第59行设置了文件打开的时候的日期，因此在save的时候可以用这个来进行计算(gpt一下如何计算两个Date对象的差值)  
关于insert方法: 我在context中的存放了tmp文件的`AccessRandomFile file`这个对象，可以用它来进行操作。append-head\append-tail这些方法有很多地方的代码可以复用，我们可以把这些代码封装一下作为static方法写在util目录下的FileUtils的方法里

