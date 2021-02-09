- -Xmx(the maximum size of the heap):最大堆内存
- -Xms(the minimum and initial size (in bytes) of the heap):最小和初始堆内存
- -Xmn(the initial and maximum size (in bytes) of the heap of the heap for the young generation (nursery) in the generational collectors):年轻代出世和最大堆内存
- -Xss(the thread stack size):线程栈大小
- -XX:MetaspaceSize(the size of the allocated class metadata space):非堆区大小
- -XX:MaxDirectMemorySize(the maximum total size (in bytes) of the java.nio package, direct-buffer allocations):堆外内存，NIO会用到

参数文档参考：https://docs.oracle.com/en/java/javase/15/docs/specs/man/java.html#standard-options-for-java
关系图参考：https://github.com/coder-robot/JAVA-000/tree/main/Week_01