# GC

### 作业
使用 GCLogAnalysis.java 自己演练一遍串行 / 并行 /CMS/G1 的案例


### 执行脚本
```bash
java -XX:+UseSerialGC -Xmx128m -Xms128 -XX:+PrintGCDetails -Xloggc:SerialGC-128m.log GCLogAnalysis
java -XX:+UseSerialGC -Xmx512m -Xms512 -XX:+PrintGCDetails -Xloggc:SerialGC-512m.log GCLogAnalysis
java -XX:+UseSerialGC -Xmx1G -Xms1G -XX:+PrintGCDetails -Xloggc:SerialGC-1G.log GCLogAnalysis
java -XX:+UseSerialGC -Xmx2G -Xms2G -XX:+PrintGCDetails -Xloggc:SerialGC-2G.log GCLogAnalysis
java -XX:+UseSerialGC -Xmx4G -Xms4G -XX:+PrintGCDetails -Xloggc:SerialGC-4G.log GCLogAnalysis
java -XX:+UseParallelGC -Xmx128m -Xms128 -XX:+PrintGCDetails -Xloggc:UseParallelGC-128m.log GCLogAnalysis
java -XX:+UseParallelGC -Xmx512m -Xms512 -XX:+PrintGCDetails -Xloggc:UseParallelGC-512m.log GCLogAnalysis
java -XX:+UseParallelGC -Xmx1G -Xms1G -XX:+PrintGCDetails -Xloggc:UseParallelGC-1G.log GCLogAnalysis
java -XX:+UseParallelGC -Xmx2G -Xms2G -XX:+PrintGCDetails -Xloggc:UseParallelGC-2G.log GCLogAnalysis
java -XX:+UseParallelGC -Xmx4G -Xms4G -XX:+PrintGCDetails -Xloggc:UseParallelGC-4G.log GCLogAnalysis
java -XX:+UseConcMarkSweepGC -Xmx128m -Xms128 -XX:+PrintGCDetails -Xloggc:UseConcMarkSweepGC-128m.log GCLogAnalysis
java -XX:+UseConcMarkSweepGC -Xmx512m -Xms512 -XX:+PrintGCDetails -Xloggc:UseConcMarkSweepGC-512m.log GCLogAnalysis
java -XX:+UseConcMarkSweepGC -Xmx1G -Xms1G -XX:+PrintGCDetails -Xloggc:UseConcMarkSweepGC-1G.log GCLogAnalysis
java -XX:+UseConcMarkSweepGC -Xmx2G -Xms2G -XX:+PrintGCDetails -Xloggc:UseConcMarkSweepGC-2G.log GCLogAnalysis
java -XX:+UseConcMarkSweepGC -Xmx4G -Xms4G -XX:+PrintGCDetails -Xloggc:UseConcMarkSweepGC-4G.log GCLogAnalysis
java -XX:+UseG1GC -Xmx128m -Xms128 -XX:+PrintGCDetails -Xloggc:UseG1GC-128m.log GCLogAnalysis
java -XX:+UseG1GC -Xmx512m -Xms512 -XX:+PrintGCDetails -Xloggc:UseG1GC-512m.log GCLogAnalysis
java -XX:+UseG1GC -Xmx1G -Xms1G -XX:+PrintGCDetails -Xloggc:UseG1GC-1G.log GCLogAnalysis
java -XX:+UseG1GC -Xmx2G -Xms2G -XX:+PrintGCDetails -Xloggc:UseG1GC-2G.log GCLogAnalysis
java -XX:+UseG1GC -Xmx4G -Xms4G -XX:+PrintGCDetails -Xloggc:UseG1GC-4G.log GCLogAnalysis
```


### 本机配置

- OS 名称:Microsoft Windows 10 专业版
- 系统类型: x64-based PC
- 处理器: 安装了 1 个处理器。
- [01]: Intel64 Family 6 Model 142 Stepping 9 GenuineIntel ~2803 Mhz
- 物理内存总量: 16,243 MB
- 可用的物理内存: 8,155 MB
- 虚拟内存: 最大值: 26,483 MB
- 虚拟内存: 可用: 15,073 MB
- 虚拟内存: 使用中: 11,410 MB



### 测试报告
| **GC/MEM** | **128M** | **512M** | **1G** | **2G** | **4G** |
| :--- | :---: | :---: | :---: | :---: | :---: |
| **SerialGC** | OOM | OOM | 11265 | 10832 | 8317 |
| **ParallelGC** | OOM | OOM | 11747 | 13750 | 11779 |
| **ConcMarkSweepGC** | OOM | OOM | 12037 | 11213 | 11259 |
| **UseG1GC** | OOM | OOM | 11350 | 11603 | 10122 |



