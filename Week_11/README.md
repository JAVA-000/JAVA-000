学习笔记
redis做分布式锁 参考地址：https://www.cnblogs.com/jojop/p/14008824.html

docker安装运行redis
## 安装、启动

1. 运行 docker pull redis 拉取最新的镜像，如果要指定版本，要在后面加版本号
2. 用 docker images 命令查看拉取的镜像
3. 使用 docker run -itd --name redis-test -p 6379:6379 redis 命令运行redis
4. 用 docker ps 命令查看是否启动
5. 用 docker image inspect redis:latest|grep -i version 查看当前镜像的版本号
6. 如果redis已经启动了，运行 docker exec -it redis-test /bin/bash 命令进入到redis里面

## 操作redis

1. 执行 redis-cli 命令，进入redis命令行程序
2. 执行 info 操作，查看相关信息

## 压测

1. 执行redis自带的工具 redis-benchmark -n 100000 -c 32 -t SET,GET,INCR,HSET,LPUSH,MSET -q 压测(注意要先退出redis命令行)