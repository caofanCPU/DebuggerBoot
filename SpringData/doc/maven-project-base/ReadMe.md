### **gradle**(或**gradlew**)命令
```
复制代码
#查看所有可用的task
gradlew task

#编译（编译过程中会进行单元测试）
gradlew build

#单元测试
gradlew test

#编译时跳过单元测试
gradlew build -x test

#直接运行项目 
gradlew run

#清空所有编译、打包生成的文件(即：清空build目录)
gradlew clean

#生成mybatis的model、mapper、xml映射文件，注： 生成前，先修改src/main/resources/generatorConfig.xml 文件中的相关参数 ， 比如：mysql连接串，目标文件的生成路径等等
gradle mybatisGenerate

#生成可运行的jar包，生成的文件在build/install/hello-gradle下，其中子目录bin下为启动脚本， 子目录lib为生成的jar包
gradlew installApp

#打包源代码，打包后的源代码，在build/libs目录下
gradlew sourcesJar

#安装到本机maven仓库，此命令跟maven install的效果一样
gradlew install

#生成pom.xml文件，将会在build根目录下生成pom.xml文件，把它复制项目根目录下，即可将gradle方便转成maven项目
gradlew createPom
```

### [**maven**命令](https://www.cnblogs.com/wkrbky/p/6352188.html)

### gradle与maven项目互转
#### **gradle转maven**
* 添加maven插件依赖
    ```
    apply plugin: 'maven'
    ```
    
* 在build.gradle中接近 tasks 的前面定义生成pom文件的任务
    ```
    task writeNewPom << {
        pom {
            project {
                inceptionYear '2018'
                licenses {
                    license {
                        name 'The Apache Software License, Version 2.0'
                        url 'http://www.apache.org/licenses/LICENSE-2.0.txt'
                        distribution 'repo'
                    }
                }
            }
        }.writeTo("$buildDir/pom.xml")
    }
    ```
    
* 执行命令构建任务
    ```
    # windows系统(在项目根目录)
    # 1.IDEA的Terminal窗口执行
    > gradlew writeNewPom
    # 2.CMD窗口执行
    > gradle writeNewPom
    # linux系统(在项目根目录)
    $ ./gradle writeNewPom
    ```
* 在项目根目录/build/下, 可查看pom.xml文件, 然后进行一点修缮即可

#### **maven转gradle**
* 执行命令转换
    ```
    # windows系统(在项目根目录), CMD窗口执行
    > gradle init --type pom
    # linux系统(在项目根目录)
    $ ./gradle init --type pom
    ```
