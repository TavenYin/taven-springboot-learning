# docker 安装 postgres
```
docker pull postgres
docker run --name pg -e POSTGRES_PASSWORD=pgpwd -p 5432:5432 -d postgres
```

mdb set connection

show variables like "%conn%";

set GLOBAL max_connections=1020;

set GLOBAL max_user_connections = 1000; 