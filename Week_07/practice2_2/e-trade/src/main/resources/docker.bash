# master
docker run --name mysql-master -d -v D:\docker\mysql\master:/var/lib/mysql -p 127.0.0.1:3306:3306 -e MYSQL_ROOT_PASSWORD=123456 mysql

# slave1
docker run --name mysql-slave -d -v D:\docker\mysql\slave1:/var/lib/mysql -p 127.0.0.1:3307:3306 -e MYSQL_ROOT_PASSWORD=123456 mysql

# Container shell access
docker exec -it some-mysql bash
mysql -u root -p