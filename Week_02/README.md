第二周学习笔记与作业
本周两次课，第一次课主要是继续上次课的内容，进一步讲解了JVM相关的内容，内容包括JVM GC相关内容，
老师通过案例的展示，详细展示了JVM里调优的经验，各种GC的特点及其适用场景；
全面介绍内存工具及使用，JVM的线程堆栈模型，数据分析，内存泄漏；最后总结了JVM的高频面试题等等。
第二次课则是NIO的入门，
老师通过样例，展示了NIO的优势，与传统的阻塞型的IO相比，NIO的优势在于

- 1.使用 GCLogAnalysis.java 自己演练一遍串行/并行/CMS/G1 的案例。    
- 2.使用压测工具（wrk 或 sb），演练 gateway-server-0.0.1-SNAPSHOT.jar 示例。
操作总结：
(1) 堆内存设置不好，不够程序运行时跑的时候所消耗的，程序会报OOM；
(2) 通过jmap -heap可以查看当前JVM使用的垃圾收集器类型；
(3) JVM与GC相关的参数设置：
-XX:+Use**GC 启用**GC
-XX:+PrintGC 输出GC日志
-XX:+PrintGCDetails 输出GC的详细日志
-XX:+PrintGCTimeStamps 输出GC的时间戳
-Xloggc:./gc.log 设置GC日志输出路径

- 3.（选做） 如果自己本地有可以运行的项目，可以按照 2 的方式进行演练。
略过

- 4.（必做） 根据上述自己对于 1 和 2 的演示，写一段对于不同 GC 的总结，提交到 Github。
主要的GC算法：
(1) 标记-清除算法：优点是不需要移动对象，简单高效；缺点是算法的执行过程效率低，GC会产生内存碎片；
(2) 复制算法：优点是简单高效，不会产生内存碎片；缺点是内存使用率低；
(3) 标记-整理算法：综合了标记-清除算法与复制算法的有点，缺点是仍然需要进行局部对象的移动；
(4) 分代回收算法：优点是按分区进行回收，缺点是对于长时间存活对象的场景回收效果不明显；

主要的GC类型：
(1)串行GC（SerialGC）- 主要采用复制算法和标记-整理算法。
    在清理使用单线程处理所有垃圾回收工作，没有多线程的交互，简单高效。
    相比其他GC，串行GC的停顿时间会长（单线程），也无法使用多处理器的优势，
    所以此收集器适合单处理器机器，固其适合在系统资源较为紧俏的时候使用；设置参数-XX:+UseSerialGC启用；

(2)并行GC（ParallelGC）- 主要采用复制算法和标记-整理算法。
    开启多线程复制回收垃圾，降低了停顿时间，高效利用多核CPU，
    相比串行GC，其效率略低，因为垃圾收集器需要和应用程序交替运行。
    其吞吐量也可能会偏低（需要时不时进行清理与回收）。
    设置参数-XX:+UseParallelGC启用；

(3)CMS GC - 主要是采用的标记-清除算法。CMS适用于对响应时间有很高要求的应用，高并发、低停顿场景。
主要针对老年代进行回收。其占用CPU资源较多，响应时间快，停顿时间短。通过设置参数-XX:UseConcMarkSweepGC启用
 
(4)G1 GC - 主要采用标记-整理+复制算法。相比其他的GC，G1更能满足高并发、低停顿的需求，G1 GC可以预测停顿时间，
通过设置-XX:+UseG1GC启用，设置-XX:MaxGCPauseMillis=200设置其最大暂停时间

- 5.（选做）运行课上的例子，以及 Netty 的例子，分析相关现象。
本次案例进一步了解了压测工具的使用，由于平常几乎没有做过并发的测试，
故这块还是蛮陌生的，希望后续能在工作里有机会用起来吧。
(1)运行样例HttpServer01（单线程）进行测试，得到结果：
```text
Requests: 1219
RPS: 39.2
90th Percentile: 1231ms
95th Percentile: 1248ms
99th Percentile: 1252ms
Average: 1000.1ms
Min: 98ms
Max: 1256ms
```
此时RPS较小，因为是单线程工作，程序里还有20ms的sleep，固RPS受此影响。

(2)运行样例HttpServer02（每一个请求，new一个线程）进行测试，得到结果：
```text
Requests: 37334
RPS: 1203.2
90th Percentile: 31ms
95th Percentile: 35ms
99th Percentile: 59ms
Average: 27.1ms
Min: 6ms
Max: 113ms
```
此时RPS还可以，不过每来一个请求就创建了一个线程，相当浪费资源。

(3) 运行样例HttpServer03进行测试，得到结果：
```text
Requests: 39037
RPS: 1257.1
90th Percentile: 30ms
95th Percentile: 36ms
99th Percentile: 58ms
Average: 25.7ms
Min: 7ms
Max: 119ms
```
此时开启了一个固定大小（40）的线程池，前后的请求均复用此线程池，避免浪费过多系统资源。

(4) 运行Netty Server，得到结果：
```text
Requests: 121880
RPS: 3915.5
90th Percentile: 0ms
95th Percentile: 4ms
99th Percentile: 13ms
Average: 0.6ms
Min: 0ms
Max: 156ms
```
Netty的数据整体好，据群友反应是sb工具并没有等到Netty返回，所以终端里有很多报错。

- 6.（必做）写一段代码，使用 HttpClient 或 OkHttp 访问 http://localhost:8801 ，代码提交到 Github。
```java
package src.code;

import okhttp3.*;

import java.io.IOException;
import java.util.Objects;

public class RequestApiTest {
    public static void main(String[] args) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
        String url = "http://localhost:8801/test";
        Request request = new Request.Builder().url(url).build();
        Response response = okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            System.out.println(Objects.requireNonNull(response.body()).toString());
        }
    }
}

```