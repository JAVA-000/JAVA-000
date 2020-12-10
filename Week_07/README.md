学习笔记
mac上mysql主从复制搭建
下载mysql:
官方网站上下载

1. 创建一个帐号
groupadd mysql
useradd -g mysql -d /usr/local/mysql -s /sbin/nologin -M mysql
id mysql

2. 基本软件安装
mkdir /opt/mysql
cd /opt/mysql
tar zxvf /vagrant/MySQL/mysql-5.7.19-linux-glibc2.12-x86_64.tar.gz
cd /usr/local/
ln -s /opt/mysql/mysql-5.7.19-linux-glibc2.12-x86_64 mysql
chown -R mysql:mysql mysql

3. 数据库相关的目录创建出来
配置文件：/etc/my.cnf
/data  是一个单独挂载的一个分区(Mac装在/Users/zenglinhui/下面)
datadir /data/mysql/mysql3306/data
binlog  /data/mysql/mysql3306/logs
        /data/mysql/mysql3306/tmp
mkdir /data/mysql/mysql3306/{data,logs,tmp} -p

4. 初始化
cd /usr/local/mysql
./bin/mysqld --defaults-file=/etc/my.cnf --initialize
如果要初始化两个数据库，--defaults-file=后面的路径就要找到相应目录下的.cnf文件(如mysql3307/my3307.cnf)

5.6,5.5,5.1
./script/mysql_db_install

./bin/mysql_db_install


5. 启动
cp support-files/mysql.server /etc/init.d/mysql(改为mach_init.d,下面跟这个一样)
/etc/init.d/mysql start
以后用这个启动 /usr/local/mysql/bin/mysqld -—defaults-file=/Users/zenglinhui/data/mysql/mysql3306/my3306.cnf &


6.连接
mysql -S /tmp/mysql3306.sock -p(加sudo登录)
>alter user user() identified by 'mysql';

7. 关闭mysql
/etc/init.d/mysql stop(mach_init.d)

启动两台机器，一台3307，一台3306
先用第6步登录到mysql，然后创建复制账号
CREATE USER 'repl'@'%' IDENTIFIED BY '123456';
分配权限
GRANT REPLICATION SLAVE ON *.* TO 'repl'@'%';
刷新
flush privileges;
查看主库binlog，复制binlog文件名和position的值，到从库上需要这两个值
show master status;

登录从库执行
CHANGE MASTER TO
    MASTER_HOST='localhost',  
    MASTER_PORT = 3306,
    MASTER_USER='repl',      
    MASTER_PASSWORD='123456',   
    MASTER_LOG_FILE='mysql-bin.000003',     --主库的binlog文件名
    MASTER_LOG_POS=305;                     --主库的position
注意：还有很重要的一点，执行启动命令
start slave;
然后就可以开始愉快的同步了


demo 配置多个数据源
demo1 动态切换数据源
demo2 数据库框架版本数据源