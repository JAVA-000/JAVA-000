
## mysql 同步
https://www.cnblogs.com/f-ck-need-u/p/9155003.html#%E5%A4%8D%E5%88%B6%E7%9A%84%E5%9F%BA%E6%9C%AC%E6%A6%82%E5%BF%B5%E5%92%8C%E5%8E%9F%E7%90%86


主从读写分:问题
 当一个事务中 读写一起的  会出现 写后读, 有延迟 
 解决方案 : 写后的读 用主库



## 作业
https://gitee.com/ywj1352/jike-ch01/blob/master/sharing-db/src/main/java/com/gitee/ywj1352/sharing/SharingApp.java

1. 手写 实现 spring 动态数据源
   https://gitee.com/ywj1352/jike-ch01/blob/master/sharing-db/src/main/java/com/gitee/ywj1352/sharing/db/DataSourceConfig.java
   https://gitee.com/ywj1352/jike-ch01/blob/master/sharing-db/src/main/java/com/gitee/ywj1352/sharing/db/DynamicDataSource.java
   
2. 使用 ShardingJdbc 实现
   https://gitee.com/ywj1352/jike-ch01/blob/master/sharing-db/src/main/java/com/gitee/ywj1352/sharing/db/ShardingSphereDataSourceConfig.java
   
3. 插入数据100w条数据
 存储过程 > jdbc批量 > jdbc 一条条插入
   
```
CREATE DEFINER=`root`@`localhost` PROCEDURE `insertUser`()
BEGIN
	 #Routine body goes here...
	 declare t int DEFAULT 0;
		#Routine body goes here...
	 WHILE t  <= 1000000 DO
	 INSERT INTO `db`.`t_user`VALUES (null,  CONCAT('ywj',t), 1, 27, now());
	 SET  t = t + 1; 
	 END WHILE;
END
```
