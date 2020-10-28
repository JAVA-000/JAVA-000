第二周学习笔记与作业
本周两次课，第一次课主要是继续上次课的内容，进一步讲解了JVM相关的内容，内容包括JVM GC相关内容，
老师通过案例的展示，详细展示了JVM里调优的经验，各种GC的特点及其适用场景；
全面介绍内存工具及使用，JVM的线程堆栈模型，数据分析，内存泄漏；最后总结了JVM的高频面试题等等。
第二次课则是NIO的入门，
老师通过样例，展示了NIO的优势，与传统的阻塞型的IO相比，NIO的优势在于

- 1.使用 GCLogAnalysis.java 自己演练一遍串行/并行/CMS/G1 的案例。    
- 2.使用压测工具（wrk 或 sb），演练 gateway-server-0.0.1-SNAPSHOT.jar 示例。

- 3.（选做） 如果自己本地有可以运行的项目，可以按照 2 的方式进行演练。
略过

- 4.（必做） 根据上述自己对于 1 和 2 的演示，写一段对于不同 GC 的总结，提交到 Github。


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
此时RPS较小，因为是单线程工作；

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
```
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