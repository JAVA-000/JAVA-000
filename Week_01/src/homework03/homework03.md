# 画一张图，展示 Xmx、Xms、Xmn、Meta、DirectMemory、Xss 这些内存参数的关系。
链接：https://www.processon.com/view/link/5f3780d85653bb06f2cf1eb5
- Xms:初始堆大小
- Xmx:最大堆大小
- Xmn:年轻代大小
- Xss:JVM启动的每个线程分配的内存大小
- Meta:
- DirectoryMemory:直接内存，不是虚拟机运行时数据区的一部分，也不是Java虚拟机规范中定义的内存区域。通过-XX:MaxDirectMemorySize配置
