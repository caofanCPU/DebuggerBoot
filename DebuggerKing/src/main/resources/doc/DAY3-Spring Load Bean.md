### Spring Load Bean

**框架源码准则: 一个*真正干活*的函数总是*以do开头*, 给予干活错觉的函数喜欢以get/create/load开头**

加载过程图



![](https://img-blog.csdn.net/20171212142946140?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvamFja193YW5nMDAx/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center)



详细流程可参考[SpringBoot启动流程图](https://www.processon.com/view/link/59812124e4b0de2518b32b6e)





![](https://github.com/caofanCPU/PassedCode/blob/master/MATLAB/Chromosome/ResultPicture/chromosome%20(1).jpg)











---

### Spring中组件Bean为啥默认(推荐)是单例

Spring中各个MVC组件, 例如: 申明为@Controller/@Service/@Component/@Mapper等, 默认都是单例, 单例对象本身是无状态的,可以保证线程安全.

如此设计原因是因为, SpringMVC将每个Http请求作用域设置为 request, 其会话的作用域设置为session, 并且一个resuqest对应一个线程, 并且每个线程都将变量对象Controller->Service->mapper->对象保存在ThreadLocalMap中, 通过线程变量副本"以空间换时间"的方式解决并发访问的问题, Spring推荐这种方式解决MVC的并发问题, 原因在于, 性能高, 若用线程同步是用"时间换空间", 要么是单例对象用锁, 要么是多例对象则存在频繁创建和销毁对象,及其影响性能.

最佳实践: 

- Controller/Service/Mapper对象中不要存在公用的全局成员变量或是静态成员变量

- 如果非要存在, 那么该公用成员变量最好是固定值,只有读操作

- 如果存在写操作, 则满足以下三个条件

  1.Controller/Service对象使用多例

  2.公用成员变量必须使用线程安全的数据结构, 例如ConcurrentMap

  3.公用成员函数需要考虑锁机制, 防重处理

- 最佳实现, 组件不要使用存在写操作的公有变量, 考虑其他方法予以解决

---

### ThreadLocal及ThreadLocalMap

- ThreadLocalMap是ThreadLocal的静态内部类, 设计为内部类的原因如下

  1.ThreadLocalMap不依赖外部类ThreadLocal,能够单独创建实例

  2.ThreadLocal依赖ThreadLocalMap, 保持嵌套可读性的关系

  总结: 外部类单恋内部类时, 静态内部类

- Thread中存在两个ThreadLocal.ThreadLocalMap变量

  threadLocals用于存储当前线程的变量副本

  inheritableThreadLocals用于需要传给当前线程的子线程的变量副本

- 常见Http处理中,每个请求到响应都在一个线程Thread上, 该线程中的变量都是一个一个set到ThreadLocalMap中的

  set/get方法核心, 通过线程对象Thread获取ThreadLocalMap, 判断ThreadLocalMap是否为空, 为空则初始化创建一个ThreadLocalMap, 非空则到ThreadLocalMap的Entry中查找, 查找的key为ThreadLocal对象

- 另外,Spring为保证一个事物的所有方法拿到的是同一个数据库连接,将首次从数据库连接池获得的连接封装到当前线程的ThreadLocalMap中,结构: `ThreadLocalMap<Map<Object, Object>>`, **该Map中key是根据数据源DataSource生成, 而calue则是DataSource获取到的连接封装的ConnectionHolrder**

  最佳实践:

  尽可能保证一个事物只在一个线程中; 

  或者主线程开启的子线程不涉及事务处理;

  如果子线程非要涉及事务, 则子线程调用的方法配置事务不能与主线程的调用方法在同一个类里面.原因就是Spring的事务是基于AOP的, 两个方法在同一个类中,父方法走的是代理增强类->调用父方法原始类->子方法,子方法根本未进行事务增强

- ThreadLocalMap内存泄露原因

  在一个线程中, 当线程还未结束(Y一直运, ThreadRef未出栈) 部分ThreadLocal对象在Thread的运行栈中出栈了, 因为已经执行过了,而

  不巧的是,线程阻塞了, 等到JVM进行GC时, 发现这些ThreadLocal没有强引用, 只有ThreadLocalMap的Entry中的弱引用, 对于只剩下弱引用标志关联的对象, JVM会在下一次GC时回收该ThreadLocal对象

  那么问题来了, ThreadLocalMap的Entry的键对象不存在,为null了

  但是ThreadRef强引用Thread,强引用ThreadLocalMap,强引用Entry, 这样导致该Entry存在但是无法访问

  所以出现了内存泄漏, 如下图所示

  ![](https://images2015.cnblogs.com/blog/1156565/201707/1156565-20170724121152430-1111069410.png)

- 避免内存泄漏的方法
















