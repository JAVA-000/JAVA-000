前提：环境mac os 10.14 16G jdk 1.8
一、默认配置
1.java -XX:+PrintGCDetails GCLogAnalysis
结果：共生成对象次数:9593，9次YoungGC, 3次FullGC
2.java -XX:+UseSerialGC -XX:+PrintGCDetails GCLogAnalysis     --串行GC
结果：共生成对象次数:8447，19次左右YoungGC(平均，执行了3次左右)，没有FullGC
3.java -XX:+UseParallelGC -XX:+PrintGCDetails GCLogAnalysis   --并行GC
结果：共生成对象次数:9647，9次YoungGC，3次FullGC    -- 跟没有指定GC执行时结果一样，说明1.8默认的GC是并行GC
4.java -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails GCLogAnalysis    --CMSGC
结果：共生成对象次数:8950，34次左右YoungGC，没有FullGC，中间有一段日志
[GC (CMS Initial Mark) [1 CMS-initial-mark: 90516K(174784K)] 99603K(253440K), 0.0003599 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]       --初始标记
[CMS-concurrent-mark-start]         --并发标记开始
[CMS-concurrent-mark: 0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]     --并发标记
[CMS-concurrent-preclean-start]     --并发预清理开始
[CMS-concurrent-preclean: 0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] --并发预清理
[CMS-concurrent-abortable-preclean-start]   --可中止的并发预清理
后面还有一段日志没有打出来，猜测可能是因为被中断了，或者是因为BUG的原因
5.java -XX:+UseG1GC -XX:+PrintGCDetails GCLogAnalysis       --G1GC
结果：共生成对象次数:5805，G1没有严格区分Young和Old区，分成了一个小块的区域，所以执行GC时间可控，也是用的并行GC线程执行垃圾回收
二、由于默认堆大小在上面已经执行了，为了对比较大差异的堆大小，用-Xms -Xmx4g 来测试
1.java -Xms4g -Xmx4g -XX:+PrintGCDetails GCLogAnalysis    -- -Xms4g -Xmx4g  
结果：共生成对象次数:11799，由于堆内存比较大，只执行了3次左右的YoungGC，没有FullGC
2.java -Xms4g -Xmx4g -XX:+UseSerialGC -XX:+PrintGCDetails GCLogAnalysis   -- 串行GC
结果：共生成对象次数:8509，由于堆内存比较大，只执行了2次左右的YoungGC，没有FullGC，但生成的数量比较小，同样时间内，GC线程运行时会导致业务线程STW
3.java -Xms4g -Xmx4g -XX:+UseParallelGC -XX:+PrintGCDetails GCLogAnalysis   --并行GC
结果：共生成对象次数:11835，由于堆内存比较大，只执行了3次左右的YoungGC，没有FullGC   -- 跟没有指定GC执行时结果一样，说明1.8默认的GC是并行GC
4.java -Xms4g -Xmx4g -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails GCLogAnalysis      --CMSGC
结果：共生成对象次数:10339，5次左右YoungGC，没有FullGC，中间日志也没有了，不知道什么原因没有打还是什么情况？
5.java -Xms4g -Xmx4g -XX:+UseG1GC -XX:+PrintGCDetails GCLogAnalysis         --G1GC
结果：共生成对象次数:10791，G1GC分配的堆内存越大，创建的对象也多一倍，如果堆内存比较大，GC时间又想可控，建议用G1GC
另：可能因为电脑配置原因，设置为2g时，和4g的差异不是很明显
