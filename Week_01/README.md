学习笔记

# 查看内存参数

`jps`

`jmap -heap pid`

> using thread-local object allocation.  
> Parallel GC with 8 thread(s) # jdk 8 默认 垃圾回收器
> 
> Heap Configuration:  
>     MinHeapFreeRatio = 0  
>     MaxHeapFreeRatio = 100  
>     MaxHeapSize = 734003200 (700.0MB)  
>     NewSize = 89128960 (85.0MB)  
>     MaxNewSize = 244318208 (233.0MB)  
>     OldSize = 179306496 (171.0MB) # 老年代大小
>     NewRatio = 2 #年轻代比例
>     SurvivorRatio = 8 # 年轻代S区比例 8:1:1
>     MetaspaceSize = 21807104 (20.796875MB)  
>     CompressedClassSpaceSize = 1073741824 (1024.0MB)  
>     MaxMetaspaceSize = 17592186044415 MB  
>     G1HeapRegionSize = 0 (0.0MB)
> 
> Heap Usage:  
> PS Young Generation  
> Eden Space:  
>     capacity = 67108864 (64.0MB)  
>     used = 10230800 (9.756851196289062MB)  
>     free = 56878064 (54.24314880371094MB)  
>     15.24507999420166% used  
> From Space:  
>     capacity = 11010048 (10.5MB)  
>     used = 4483304 (4.275611877441406MB)  
>     free = 6526744 (6.224388122558594MB)  
>     40.720113118489586% used  
> To Space:  
>     capacity = 11010048 (10.5MB)  
>     used = 0 (0.0MB)  
>     free = 11010048 (10.5MB)  
> 0.0% used  
> PS Old Generation  
> capacity = 179306496 (171.0MB)  
> used = 147488 (0.140655517578125MB)  
>     free = 179159008 (170.85934448242188MB)  
>     0.08225468864217836% used
> 
> 5225 interned Strings occupying 445536 bytes.
>


```$xslt
Compiled from "CodeDemo01.java"
public class com.gitee.ywj1352.CodeDemo01 {
  public com.gitee.ywj1352.CodeDemo01();
    Code:
       0: aload_0
       1: invokespecial #1                  // Method java/lang/Object."<init>":()V
       4: return

  public static void main(java.lang.String[]);
    Code:
       0: iconst_2
       1: istore_1
       2: iconst_1
       3: istore_2
       4: iconst_0
       5: istore_3
       6: iload_3
       7: bipush        10
       9: if_icmpge     22
      12: iload_1
      13: iload_2
      14: iadd
      15: istore_1
      16: iinc          3, 1
      19: goto          6
      22: iload_1
      23: bipush        10
      25: if_icmple     32
      28: iload_1
      29: iload_1
      30: imul
      31: istore_1
      32: new           #2                  // class com/gitee/ywj1352/CodeDemo01
      35: dup
      36: invokespecial #3                  // Method "<init>":()V
      39: astore_3
      40: aload_3
      41: iload_1
      42: iload_2
      43: invokevirtual #4                  // Method add:(II)I
      46: istore        4
      48: return

  public int add(int, int);
    Code:
       0: iload_1
       1: iload_2
       2: iadd
       3: ireturn
}

```
