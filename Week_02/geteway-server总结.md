前提：环境mac os 10.14 16G jdk 1.8
一、wrk -t8 -c40 -d60s http://localhost:8088/api/hello
1.java -jar -Xms2g -Xmx2g -XX:+UseSerialGC -XX:+PrintGCDetails gateway-server-0.0.1-SNAPSHOT.jar        --串行GC，2g堆内存
结果：Requests/sec:  46669.58(平均结果，3次压测)
2.java -jar -Xms2g -Xmx2g -XX:+UseParallelGC -XX:+PrintGCDetails gateway-server-0.0.1-SNAPSHOT.jar      --并行GC，2g堆内存
结果：Requests/sec:  46871.35(平均结果，3次压测)
3.java -jar -Xms2g -Xmx2g -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails gateway-server-0.0.1-SNAPSHOT.jar      --CMSGC，2g堆内存
结果：Requests/sec:  45586.09(平均结果，3次压测)
4.java -jar -Xms2g -Xmx2g -XX:+UseG1GC -XX:+PrintGC gateway-server-0.0.1-SNAPSHOT.jar       -- G1GC，2g堆内存
结果：Requests/sec:  46767.61(平均结果，3次压测)

二、wrk -t8 -c40 -d60s http://localhost:8088/api/hello
1.java -jar -Xms4g -Xmx4g -XX:+UseSerialGC -XX:+PrintGCDetails gateway-server-0.0.1-SNAPSHOT.jar        --串行GC，4g堆内存
结果：Requests/sec:  47208.84(平均结果，3次压测)
2.java -jar -Xms4g -Xmx4g -XX:+UseParallelGC -XX:+PrintGCDetails gateway-server-0.0.1-SNAPSHOT.jar      --并行GC，4g堆内存
结果：Requests/sec:  50549.92(平均结果，3次压测)
3.java -jar -Xms4g -Xmx4g -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails gateway-server-0.0.1-SNAPSHOT.jar      --CMSGC，4g堆内存
结果：Requests/sec:  46954.64(平均结果，3次压测)
4.java -jar -Xms4g -Xmx4g -XX:+UseG1GC -XX:+PrintGC gateway-server-0.0.1-SNAPSHOT.jar       --G1GC，4g堆内存
结果：Requests/sec:  50719.99(平均结果，3次压测)
根据观察，2G内存情况下区别不是很大，4G内存情况下有两个GC表现良好。内存越大的情况下G1和并行GC表现良好，内存越大，推荐用G1。

