# docker 安装 postgres
```
docker pull postgres
docker run --name pg -e POSTGRES_PASSWORD=pgpwd -p 5432:5432 -d postgres
```