# GC总结

## 一、Serial GC(串行GC)
串行GC把堆划分成两个部分，一部分为年轻代，另一部分为老年代。串行GC对年轻代使用 **mark-copy(标记-复制)** 算法, 对老年代使用 **mark-sweep-compact(标记-清除-整理)算法。**无论是年轻代，还是老年代都属于单线程的垃圾收集器，从而不能进行并行处理。并且两者在GC时，都会触发全线暂停(STW)，停止所有的应用线程。因此这种GC算法不能充分利用多核CPU的优势。不管有多少CPU内核，JVM 在垃圾收集时都只能使用单个核心，执行效率非常低，一般不建议使用。<br />

### 1、适用场景
适合堆内存只有几百MB、且CPU核心数为单核的JVM，一般多个核心的服务器，并不推荐使用。
### 2、串行GC开启方法
开启串行GC，只需要在启动程序时加上-XX:+UseSerialGC参数即可，无需要单独指定年轻代和老年代的GC算法。
```
java -XX:+UseSerialGC GCLogAnalysis //测试程序
```
### 3、串行GC日志分析
开启GC日志记录
```
-XX:+PrintGCDetails
-XX:+PrintGCDateStamps 
-XX:+PrintGCTimeStamps
```
串行GC 执行日志（太长已经简化）
```
[root@VM-0-3-centos java]# java -XX:+UseSerialGC -Xms512m -Xmx512m -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis
正在执行...
2020-10-27T11:20:45.446+0800: [GC (Allocation Failure) 2020-10-27T11:20:45.446+0800: [DefNew: 139776K->17472K(157248K), 0.0072377 secs] 139776K->47942K(506816K), 0.0072848 secs] [Times: user=0.01 sys=0.00, real=0.00 secs] 
（忽略。。。。。）
2020-10-27T11:20:45.653+0800: [GC (Allocation Failure) 2020-10-27T11:20:45.653+0800: [DefNew: 156918K->17470K(157248K), 0.0065314 secs] 451279K->350826K(506816K), 0.0065732 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
2020-10-27T11:20:45.677+0800: [GC (Allocation Failure) 2020-10-27T11:20:45.677+0800: [DefNew: 157246K->157246K(157248K), 0.0000134 secs]2020-10-27T11:20:45.677+0800: [Tenured: 333356K->281782K(349568K), 0.0228899 secs] 490602K->281782K(506816K), [Metaspace: 2531K->2531K(1056768K)], 0.0229552 secs] [Times: user=0.02 sys=0.00, real=0.02 secs] 
（忽略。。。。。）
2020-10-27T11:20:45.957+0800: [Full GC (Allocation Failure) 2020-10-27T11:20:45.957+0800: [Tenured: 349317K->349499K(349568K), 0.0295393 secs] 506130K->356551K(506816K), [Metaspace: 2531K->2531K(1056768K)], 0.0295949 secs] [Times: user=0.03 sys=0.00, real=0.03 secs] 
（忽略。。。。。）
```
从日志中可以看出，在这段日志中产生了两个GC事件，其中一次清理的是年轻代，另一次清理的是整个堆内存。
#### Minor GC(小型GC)
```
2020-10-27T11:20:45.446+0800: [GC (Allocation Failure) 2020-10-27T11:20:45.446+0800: [DefNew: 139776K->17472K(157248K), 0.0072377 secs] 139776K->47942K(506816K), 0.0072848 secs] [Times: user=0.01 sys=0.00, real=0.00 secs] 
```
从日志中可以看出，GC执行前，年轻代堆内存使用为139776K，堆总内存已使用139776K， 垃圾收集之后， 年轻代的使用量减少了 122304K，但堆内存的总体使用量只下降了 91834K。 从中可以算出，有 30470K 的对象从年轻代提升到了老年代。应用暂停时间为：0s，也印证了下图：<br />![image.png](https://cdn.nlark.com/yuque/0/2020/png/2699025/1603872752291-2111e72a-0536-450a-91c2-e30613a2a0fe.png#align=left&display=inline&height=156&margin=%5Bobject%20Object%5D&name=image.png&originHeight=156&originWidth=490&size=14068&status=done&style=stroke&width=490)
#### Full GC(完全GC)
以下日志为Full GC日志
```
2020-10-27T11:20:45.957+0800: [Full GC (Allocation Failure) 2020-10-27T11:20:45.957+0800: [Tenured: 349317K->349499K(349568K), 0.0295393 secs] 506130K->356551K(506816K), [Metaspace: 2531K->2531K(1056768K)], 0.0295949 secs] [Times: user=0.03 sys=0.00, real=0.03 secs] 
```
从日志中可以看出，与Minor GC相比较，最明显的区别是除了年轻代, 还清理了老年代和Metaspace。应用暂停时间为0.03s
## 二、ParallelGC(并行GC)
同样的，并行GC在年轻代使用 **标记-复制(mark-copy)算法**, 在老年代使用 **标记-清除-整理(mark-sweep-compact)算法**。年轻代和老年代的垃圾回收都会触发STW事件，暂停所有的应用线程来执行垃圾收集。在进行年轻代和老年代垃圾回收时，都采用多线程并行执行，通过并行执行, 使得GC时间大幅减少。
### 1、适用场景
并行GC适用于多核服务器，主要目标是增加吞吐量，所以比较适合后台运算的任务，而不是太多交互的场景
### 2、开启并行GC的方法
```
java -XX:+UseParallelGC GCLogAnalysis //测试程序
java -XX:+UseParallelOldGC GCLogAnalysis //测试程序
java -XX:+UseParallelGC -XX:+UseParallelOldGC GCLogAnalysis //测试程序
```
并行GC默认线程数为CPU内核，可以通过参数指定
```
-XX:ParallelGCThreads=NNN
```
### 3、并行GC日志分析
开启GC日志，同样是
```
-XX:+PrintGCDetails
-XX:+PrintGCDateStamps 
-XX:+PrintGCTimeStamps
```
并行GC执行日志（太长已简化）
```
java -XX:+UseParallelGC -Xms512m -Xmx512m -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis
正在执行...
2020-10-28T15:50:08.648+0800: [GC (Allocation Failure) [PSYoungGen: 131072K->21757K(152832K)] 131072K->41355K(502528K), 0.0114937 secs] [Times: user=0.05 sys=0.02, real=0.01 secs]
（省略。。。。）
2020-10-28T15:50:09.185+0800: [Full GC (Ergonomics) [PSYoungGen: 21366K->0K(116480K)] [ParOldGen: 317707K->241903K(349696K)] 339073K->241903K(466176K), [Metaspace: 1680K->1680K(4480K)], 0.1600748 secs]
[Times: user=0.08 sys=0.49, real=0.16 secs]
（省略。。。。。）
```
同样，并行GC也存在两种GC事件， 一个是发生在年轻代GC（Minor GC）,  另一个是发生在老年代GC(minor GC)
#### Minor GC(小型GC)
```
2020-10-28T15:50:08.648+0800: [GC (Allocation Failure) [PSYoungGen: 131072K->21757K(152832K)] 131072K->41355K(502528K), 0.0114937 secs] [Times: user=0.05 sys=0.02, real=0.01 secs]
```
从日志中可以看出，GC执行前，年轻代堆内存使用为131072K，堆总内存已使用131072K， 垃圾收集之后， 年轻代的使用量减少了 109315K，但堆内存的总体使用量只下降了 89717K。 从中可以算出，有 19598K 的对象从年轻代提升到了老年代。应用暂停时间为0.01s<br />示意图：<br />![image.png](https://cdn.nlark.com/yuque/0/2020/png/2699025/1603872680167-e25fbd69-1354-46fc-8579-894c9c333130.png#align=left&display=inline&height=176&margin=%5Bobject%20Object%5D&name=image.png&originHeight=176&originWidth=557&size=24485&status=done&style=stroke&width=557)<br />

#### Full GC(完全GC)
```
2020-10-28T15:50:09.185+0800: [Full GC (Ergonomics) [PSYoungGen: 21366K->0K(116480K)] [ParOldGen: 317707K->241903K(349696K)] 339073K->241903K(466176K), [Metaspace: 1680K->1680K(4480K)], 0.1600748 secs]
[Times: user=0.08 sys=0.49, real=0.16 secs]
```
从日志中可以看出，发生在老年代的Full GC，年轻代内存全部清空，老年代也进行了清理。应用程序暂停的时间为0.16S。<br />示意图：<br />![image.png](https://cdn.nlark.com/yuque/0/2020/png/2699025/1603873304559-836708b8-a83c-4997-9d85-81a58fd36c69.png#align=left&display=inline&height=177&margin=%5Bobject%20Object%5D&name=image.png&originHeight=177&originWidth=555&size=22266&status=done&style=stroke&width=555)<br />

## 三、CMS GC
CMS GC 对年轻代采用并行**mark-copy (标记-复制)算法**，对老年代主要使用并发 **mark-sweep (标记-清除)算法**。对年轻代进行GC时，会触发STW事件，应用会出现暂停。而老年代进行GC时， 并没有明显暂停应用程序线程（并不是不会暂停），大部分时间都与应用线程一起并发工作。CMS并发使用的线程数，默认为CPU内核的1 / 4。
### 1、适用场景
适用于多核CPU服务器，且希望降低因为GC停顿导致系统延迟的系统。<br />

### 2、CMS GC 老年代处理
CMS为了避免进行老年代GC时，出现长时间的卡顿，从而采用以下两种方式：<br />（1）、不对老年代进行整理，而是使用空闲列表（free-lists）来管理内存空间的回收。<br />（2）、在 mark-and-sweep （标记-清除） 阶段的大部分工作和应用线程一起并发执行。<br />
<br />CMS 对老年代进行GC时，会分为七大阶段（实际GC日志分析出来）：

1. **Initial Mark(初始标记)**

标记老年代中所有存活的对象，包括 GC ROOR 的直接引用，以及由年轻代中存活对象所引用的对象

2. **Concurrent Mark(并发标记)**

CMS GC遍历老年代，标记所有的存活对象，从前一阶段 “Initial Mark” 找到的 root 根开始算起。此阶段与应用线程并发运行，不会暂停应用线程。值得注意的是， 由于该阶段是并发执行的，所以并非所有老年代的存活对象都会被标志到，引用关系时时刻刻在变化。

3. **Concurrent Preclean(并发预清理)**

并发执行，不会暂停应用线程。并发标记阶段是并发执行，到达此阶段时，部分引用可能已经改变。在此阶段，这些脏对象会被统计出来，从他们可达的对象也被标记下来。

4. **Concurrent Abortable Preclean**

未了解，猜测是并发执行为下一个阶段做准备。

5. **Final Remark(最终标记)**

完成老年代中所有存活对象的标记， 会解发STW事件，暂停应用线程。

6. **Concurrent Sweep(并发清除)**

清除经过前5个阶段确定下来的未使用的对象，并回收它们的空间，并发执行，不需要STW停顿。

7. **Concurrent Reset(并发重置)**

重置CMS算法相关的内部数据，为下一次GC循环做准备<br />

### 3、开启CMS GC方法
```
java -XX:+UseConcMarkSweepGC GCLogAnalysis
```
### 4、CMS GC日志分析
开启GC日志，同样是
```
-XX:+PrintGCDetails
-XX:+PrintGCDateStamps 
-XX:+PrintGCTimeStamps
```
CMS GC 执行日志（已作简化）
```
 java -XX:+UseConcMarkSweepGC -Xms512m -Xmx512m -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis
正在执行...
2020-10-28T16:29:07.016+0800: [GC (Allocation Failure) [ParNew: 139776K->17471K(157248K), 0.0132159 secs] 139776K->45582K(506816K), 0.0134637 secs] [Times: user=0.00 sys=0.05, real=0.01 secs]
（省略。。。。。）
2020-10-28T16:29:07.321+0800: [GC (CMS Initial Mark) [1 CMS-initial-mark: 205834K(349568K)] 223973K(506816K), 0.0004064 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
2020-10-28T16:29:07.322+0800: [CMS-concurrent-mark-start]
2020-10-28T16:29:07.343+0800: [CMS-concurrent-mark: 0.021/0.021 secs] [Times: user=0.02 sys=0.02, real=0.02 secs]
2020-10-28T16:29:07.344+0800: [CMS-concurrent-preclean-start]
2020-10-28T16:29:07.345+0800: [CMS-concurrent-preclean: 0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
2020-10-28T16:29:07.345+0800: [CMS-concurrent-abortable-preclean-start]
2020-10-28T16:29:07.366+0800: [GC (Allocation Failure) [ParNew: 157248K->17472K(157248K), 0.0764724 secs] 363082K->267018K(506816K), 0.0766238 secs] [Times: user=0.14 sys=0.06, real=0.08 secs]
2020-10-28T16:29:07.489+0800: [GC (Allocation Failure) [ParNew2020-10-28T16:29:07.547+0800: :[CMS-concurrent-abortable-preclean: 0.003/0.201 secs] 157248K->17472K(157248K) [Times: user=0.36 sys=0.13, re
al=0.22 secs]
, 0.0724072 secs] 406794K->310507K(506816K), 0.0727687 secs] [Times: user=0.16 sys=0.06, real=0.07 secs]
2020-10-28T16:29:07.563+0800: [GC (CMS Final Remark) [YG occupancy: 18234 K (157248 K)][Rescan (parallel) , 0.0009045 secs][weak refs processing, 0.0000433 secs][class unloading, 0.0002199 secs][scrub s
ymbol table, 0.0003415 secs][scrub string table, 0.0000487 secs][1 CMS-remark: 293035K(349568K)] 311270K(506816K), 0.0018870 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
2020-10-28T16:29:07.565+0800: [CMS-concurrent-sweep-start]
2020-10-28T16:29:07.566+0800: [CMS-concurrent-sweep: 0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
2020-10-28T16:29:07.566+0800: [CMS-concurrent-reset-start]
2020-10-28T16:29:07.569+0800: [CMS-concurrent-reset: 0.003/0.003 secs] [Times: user=0.03 sys=0.00, real=0.00 secs]
（省略。。。。。）
```
#### Minor GC(小型GC)
```
2020-10-28T16:29:07.016+0800: [GC (Allocation Failure) [ParNew: 139776K->17471K(157248K), 0.0132159 secs] 139776K->45582K(506816K), 0.0134637 secs] [Times: user=0.00 sys=0.05, real=0.01 secs]
```
以上CMS 日志描述的是清理年轻代的小型GC，从日志可以看出在GC之前总的堆内存使用量为 139776K, 年轻代的使用量为 139776K。这意味着老年代使用量等于 0K。GC之后,年轻代的使用量减少了 122305K，而总的堆内存使用只下降了 94194K。可以算出有 28111K 的对象从年轻代提升到老年代。应用暂时时间为0.01s<br />示意图：<br />![image.png](https://cdn.nlark.com/yuque/0/2020/png/2699025/1603874626316-9614b1e2-3613-43ef-9ed6-8024462b4303.png#align=left&display=inline&height=155&margin=%5Bobject%20Object%5D&name=image.png&originHeight=155&originWidth=477&size=18900&status=done&style=stroke&width=477)
#### Full GC(完全GC)
```
2020-10-28T16:29:07.321+0800: [GC (CMS Initial Mark) [1 CMS-initial-mark: 205834K(349568K)] 223973K(506816K), 0.0004064 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
2020-10-28T16:29:07.322+0800: [CMS-concurrent-mark-start]
2020-10-28T16:29:07.343+0800: [CMS-concurrent-mark: 0.021/0.021 secs] [Times: user=0.02 sys=0.02, real=0.02 secs]
2020-10-28T16:29:07.344+0800: [CMS-concurrent-preclean-start]
2020-10-28T16:29:07.345+0800: [CMS-concurrent-preclean: 0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
2020-10-28T16:29:07.345+0800: [CMS-concurrent-abortable-preclean-start]
2020-10-28T16:29:07.366+0800: [GC (Allocation Failure) [ParNew: 157248K->17472K(157248K), 0.0764724 secs] 363082K->267018K(506816K), 0.0766238 secs] [Times: user=0.14 sys=0.06, real=0.08 secs]
2020-10-28T16:29:07.489+0800: [GC (Allocation Failure) [ParNew2020-10-28T16:29:07.547+0800: :[CMS-concurrent-abortable-preclean: 0.003/0.201 secs] 157248K->17472K(157248K) [Times: user=0.36 sys=0.13, re
al=0.22 secs]
, 0.0724072 secs] 406794K->310507K(506816K), 0.0727687 secs] [Times: user=0.16 sys=0.06, real=0.07 secs]
2020-10-28T16:29:07.563+0800: [GC (CMS Final Remark) [YG occupancy: 18234 K (157248 K)][Rescan (parallel) , 0.0009045 secs][weak refs processing, 0.0000433 secs][class unloading, 0.0002199 secs][scrub s
ymbol table, 0.0003415 secs][scrub string table, 0.0000487 secs][1 CMS-remark: 293035K(349568K)] 311270K(506816K), 0.0018870 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
2020-10-28T16:29:07.565+0800: [CMS-concurrent-sweep-start]
2020-10-28T16:29:07.566+0800: [CMS-concurrent-sweep: 0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
2020-10-28T16:29:07.566+0800: [CMS-concurrent-reset-start]
2020-10-28T16:29:07.569+0800: [CMS-concurrent-reset: 0.003/0.003 secs] [Times: user=0.03 sys=0.00, real=0.00 secs]
```
CMS对老年代进行GC时输出的日志体现出了，CMS Full GC的各个阶段的情况<br />**Initial Mark(初始标记)**
```
2020-10-28T16:29:07.321+0800: [GC (CMS Initial Mark) [1 CMS-initial-mark: 205834K(349568K)] 223973K(506816K), 0.0004064 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
```
**Concurrent Mark(并发标记)**
```
2020-10-28T16:29:07.322+0800: [CMS-concurrent-mark-start]
2020-10-28T16:29:07.343+0800: [CMS-concurrent-mark: 0.021/0.021 secs] [Times: user=0.02 sys=0.02, real=0.02 secs]
```
**Concurrent Preclean(并发预清理)**
```
2020-10-28T16:29:07.344+0800: [CMS-concurrent-preclean-start]
2020-10-28T16:29:07.345+0800: [CMS-concurrent-preclean: 0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
```
**Concurrent Abortable Preclean**
```
2020-10-28T16:29:07.345+0800: [CMS-concurrent-abortable-preclean-start]
```
**Final Remark(最终标记)**
```
2020-10-28T16:29:07.563+0800: [GC (CMS Final Remark) [YG occupancy: 18234 K (157248 K)][Rescan (parallel) , 0.0009045 secs][weak refs processing, 0.0000433 secs][class unloading, 0.0002199 secs][scrub s
```
**Concurrent Sweep(并发清除)**
```
2020-10-28T16:29:07.565+0800: [CMS-concurrent-sweep-start]
2020-10-28T16:29:07.566+0800: [CMS-concurrent-sweep: 0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
```
**Concurrent Reset(并发重置)**
```
2020-10-28T16:29:07.566+0800: [CMS-concurrent-reset-start]
2020-10-28T16:29:07.569+0800: [CMS-concurrent-reset: 0.003/0.003 secs] [Times: user=0.03 sys=0.00, real=0.00 secs]
```


## 四、G1 GC
G1 的全称是 Garbage-First，最主要的设计目标是: 将STW停顿的时间和分布变成可预期以及可配置的。为此，G1 GC堆不再分成连续的年轻代和老年代空间。而是划分为多个(通常是2048个)可以存放对象的小堆区(smaller heap regions)。每个小堆区都可能是 Eden区，Survivor区或者Old区，在逻辑上, 所有的Eden区和Survivor区合起来就是年轻代，所有的Old区拼在一起那就是老年代。<br />特点：<br />（1）、GC不必每次都去收集整个堆空间，而是以增量的方式来处理：每次只处理一部分小堆区，称为此次的回收集(collection set)。每次暂停都会收集所有年轻代的小堆区，但可能只包含一部分老年代小堆区<br />（2）、并发阶段估算每个小堆区存活对象的总数。用来构建回收集(collection set)的原则是：垃圾最多的小堆区会被优先收集<br />

### 1、适用场景
G1 GC适合大堆内存（个人认为4G以上），需要低延迟的场景。<br />

### 2、G1 GC 处理步骤

1. 年轻代模式转移暂停（Evacuation Pause）

在应用程序刚启动时，还没有获得足够的信息，处于fully-young 模式。在年轻代空间用满之后，应用线程被暂停，年轻代堆区中的存活对象被复制到存活区，如果还没有存活区，则选择任意一部分空闲的小堆区用作存活区。<br />复制的过程称为转移(Evacuation)， 这与串行GC、并行GC、CMS GC，年轻代收集器基本上是一样的工作原理。<br />

2. 并发标记（Concurrent Marking）

**（1）、Initial Mark（初始标记）**<br />标记所有从 GC 根对象直接可达的对象<br />**（2）、Root Region Scan（Root区扫描）**<br />标记所有从 "根区域" 可达的存活对象。根区域包括：非空的区域，以及在标记过程中不得不收集的区域。<br />**（3）、Concurrent Mark（并发标记）**<br />此阶段和 CMS 的并发标记阶段非常类似：只遍历对象图，并在一个特殊的位图中标记能访问到的对象。<br />**（4）、Remark（再次标记）**<br />与CMS类似，这是一次STW停顿(因为不是并发的阶段)，以完成标记过程。 G1 收集器会短暂地停止应用线程，<br />停止并发更新信息的写入，处理其中的少量信息，并标记所有在并发标记开始时未被标记的存活对象。<br />**（5）、Cleanup（清理）**<br />最后这个清理阶段为即将到来的转移阶段做准备，统计小堆块中所有存活的对象，并将小堆块进行排序，以提升GC 的效率，维护并发标记的内部状态。 所有不包含存活对象的小堆块在此阶段都被回收了。有一部分任务是并发的：例如空堆区的回收，还有大部分的存活率计算。此阶段也需要一个短暂的 STW 暂停。<br />

3. 转移暂停: 混合模式（Evacuation Pause: Mixed）

并发标记完成之后，G1将执行一次混合收集（mixed collection），就是不只清理年轻代，还将一部分老年代区域也加入到回收集中。混合模式的转移暂停不一定紧跟并发标记阶段。有很多规则和历史数据会影响混合模式的启动时机。比如，假若在老年代中可以并发地腾出很多的小堆块，就没有必要启动混合模式。<br />因此，在并发标记与混合转移暂停之间，很可能会存在多次 young 模式的转移暂停。<br />具体添加到回收集的老年代小堆块的大小及顺序，也是基于许多规则来判定的。其中包括指定的软实时性能指标，存活性，以及在并发标记期间收集的 GC 效率等数据，外加一些可配置的 JVM 选项。混合收集的过程，很大程度上和前面的 fully-young gc 是一样的。<br />

### 3、启用G1 GC的方法
```
java -XX:+UseG1GC GCLogAnalysis
```
4、G1 GC日志分析<br />开启GC日志，同样是
```
-XX:+PrintGCDetails
-XX:+PrintGCDateStamps 
-XX:+PrintGCTimeStamps
```
年轻代模式转移暂停（Evacuation Pause）日志
```
2020-10-28T16:58:28.806+0800: [GC pause (G1 Evacuation Pause) (young), 0.0040517 secs]
   [Parallel Time: 3.3 ms, GC Workers: 4]
      [GC Worker Start (ms): Min: 116.4, Avg: 116.4, Max: 116.5, Diff: 0.1]
      [Ext Root Scanning (ms): Min: 0.1, Avg: 0.2, Max: 0.3, Diff: 0.2, Sum: 0.6]
      [Update RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
         [Processed Buffers: Min: 0, Avg: 0.0, Max: 0, Diff: 0, Sum: 0]
      [Scan RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Code Root Scanning (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Object Copy (ms): Min: 2.6, Avg: 2.8, Max: 2.9, Diff: 0.3, Sum: 11.2]
      [Termination (ms): Min: 0.0, Avg: 0.1, Max: 0.2, Diff: 0.2, Sum: 0.5]
      [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.1, Diff: 0.0, Sum: 0.2]
      [GC Worker Total (ms): Min: 3.1, Avg: 3.1, Max: 3.2, Diff: 0.1, Sum: 12.5]
      [GC Worker End (ms): Min: 119.5, Avg: 119.5, Max: 119.6, Diff: 0.0]
   [Code Root Fixup: 0.0 ms]
   [Code Root Purge: 0.0 ms]
   [Clear CT: 0.1 ms]
   [Other: 0.7 ms]
      [Choose CSet: 0.0 ms]
      [Ref Proc: 0.1 ms]
      [Ref Enq: 0.0 ms]
      [Redirty Cards: 0.2 ms]
      [Humongous Reclaim: 0.0 ms]
      [Free CSet: 0.0 ms]
   [Eden: 25.0M(25.0M)->0.0B(21.0M) Survivors: 0.0B->4096.0K Heap: 30.8M(512.0M)->12.4M(512.0M)]
 [Times: user=0.00 sys=0.00, real=0.01 secs]
```
从日志可以看出，本次GC操作，由4个线程并行执行，消耗了3.3ms。GC前Eden区容量为25M, 存活区 0B， 堆总使用内存30.8M， GC后，Eden区清零，存活区变为4096K，堆使用内存降为12.4M。应用暂停时间为0.01s。<br />
<br />并发标记阶段日志<br />Initial Mark（初始标记）
```
2020-10-28T16:58:29.315+0800: [GC pause (G1 Humongous Allocation) (young) (initial-mark), 0.0025645 secs]
```
Root Region Scan（Root区扫描）
```
2020-10-28T16:58:29.321+0800: [GC concurrent-root-region-scan-start]
2020-10-28T16:58:29.321+0800: [GC concurrent-root-region-scan-end, 0.0002911 secs]
```
Concurrent Mark（并发标记）
```
2020-10-28T16:58:29.321+0800: [GC concurrent-mark-start]
2020-10-28T16:58:29.326+0800: [GC concurrent-mark-end, 0.0043932 secs]
```
Remark（再次标记）
```
2020-10-28T16:58:29.326+0800: [GC remark [Finalize Marking, 0.0008366 secs] [GC ref-proc, 0.0001153 secs] [Unloading, 0.0007604 secs], 0.0038263 secs]
```
Cleanup（清理）
```
2020-10-28T16:58:29.331+0800: [GC cleanup 338M->329M(512M), 0.0004621 secs]
 [Times: user=0.00 sys=0.00, real=0.00 secs]
```
转移暂停: 混合模式（Evacuation Pause: Mixed）日志
```
2020-10-28T16:58:29.364+0800: [GC pause (G1 Evacuation Pause) (mixed), 0.0075986 secs]
   [Parallel Time: 6.3 ms, GC Workers: 4]
      [GC Worker Start (ms): Min: 674.7, Avg: 676.3, Max: 680.9, Diff: 6.2]
      [Ext Root Scanning (ms): Min: 0.0, Avg: 0.0, Max: 0.1, Diff: 0.1, Sum: 0.1]
      [Update RS (ms): Min: 0.0, Avg: 0.1, Max: 0.3, Diff: 0.3, Sum: 0.5]
         [Processed Buffers: Min: 0, Avg: 1.3, Max: 2, Diff: 2, Sum: 5]
      [Scan RS (ms): Min: 0.0, Avg: 0.0, Max: 0.1, Diff: 0.1, Sum: 0.1]
      [Code Root Scanning (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Object Copy (ms): Min: 0.0, Avg: 4.0, Max: 5.4, Diff: 5.4, Sum: 16.1]
      [Termination (ms): Min: 0.0, Avg: 0.5, Max: 0.7, Diff: 0.7, Sum: 1.9]
      [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [GC Worker Total (ms): Min: 0.0, Avg: 4.7, Max: 6.3, Diff: 6.3, Sum: 18.8]
      [GC Worker End (ms): Min: 680.9, Avg: 681.0, Max: 681.0, Diff: 0.0]
   [Code Root Fixup: 0.0 ms]
   [Code Root Purge: 0.0 ms]
   [Clear CT: 0.0 ms]
   [Other: 1.2 ms]
      [Choose CSet: 0.0 ms]
      [Ref Proc: 0.6 ms]
      [Ref Enq: 0.0 ms]
      [Redirty Cards: 0.0 ms]
      [Humongous Reclaim: 0.1 ms]
      [Free CSet: 0.1 ms]
   [Eden: 15.0M(15.0M)->0.0B(21.0M) Survivors: 10.0M->4096.0K Heap: 354.0M(512.0M)->299.3M(512.0M)]
 [Times: user=0.00 sys=0.00, real=0.01 secs]
```
G1执行混合收集(mixed collection), 不只清理年轻代, 还将一部分老年代区域也加入其中。<br />
<br />

