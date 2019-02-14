### Spring AOP 分析

- AspectJ, 借助aspectjweaver.jar及动态字节码生成技术

- 动态代理:

                JDK动态代理: 需要委托类(被代理类)实现一个接口, 使用动态字节码生成技术
      
                CGLIB动态代理: 代理类需要继承目标类, 不适用于目标类为final的情况, 使用动态字节码生成技术

  Spring采用动态代理增强类

  代码层面上

    默认采用JDK动态代理, 如果目标类至少实现了接口, 使用JDK动态代理,  s生成的代理对所有接口的方法都代理(即可以增强所有实现接口的方法)

    如果目标类未实现接口,则强制采用CGLIB动态代理

   性能使用上

    JDK动态代理生成代理类速度快, 但运行性能偏低, 适用于需要频繁创建的委托对象

   CGLIB动态代理生成代理类速度偏慢, 但运行性能高, 适用于单例委托对象或具有池概念的委托对象

在编写了一个简单的Spring AOP应用之后，现在开始来分析Spring AOP的源码实现，本系列文章不会涉及太过细节的内容，只会将主干内容分析总结出来，细节内容将在未来逐步补充学习。另外一点需要说明的是，**细节是很重要的，但是又是很花费时间的，在没有理清整体框架思路之前分析细节是很危险的，因为很容易掉进去出不来**，特别是对于复杂的框架，如果太过注意细节的话，*首先是需要花费大量的时间再抠细节，其次会因为有些细节没有及时搞懂而耽误进度*。一个***合理的做法是首先将主干打通，然后再根据实际中碰到的问题进行细节分析整体，这样的学习效果才是最好的，效率也是最高的***。

---

### JDK代理

- 静态代理

  缺点: 委托类(需要被代理的类)与代理类都需要实现相同的类, 多个委托对象需要构建多个代理类

            代码侵入性强

- 动态代理

  JDK动态代理: 需要委托类(被代理类)实现一个接口, 使用动态字节码生成技术

  使用步骤:

  构造函数传入被代理的对象; invoke方法编写代理增强的所有逻辑; getProxy方法获取代理

  CGLIB动态代理: 代理类需要继承目标类, 不适用于目标类为final的情况, 使用动态字节码生成技术

---

replacing [Root bean: class [null]; scope=; abstract=false; lazyInit=false; autowireMode=3; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=restTemplateConfig; factoryMethodName=restTemplate; initMethodName=null; destroyMethodName=(inferred); defined in class path resource [com/xyz/caofancpu/config/RestTemplateConfig.class]] with [Root bean: class [null]; scope=; abstract=false; lazyInit=false; autowireMode=3; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=debuggerKingApplication; factoryMethodName=restTemplate; initMethodName=null; destroyMethodName=(inferred); defined in com.xyz.caofancpu.DebuggerKingApplication]

---

优秀代码:

```java
private final Set<String> targetSourcedBeans =
            Collections.newSetFromMap(new ConcurrentHashMap<String, Boolean>(16));
```
