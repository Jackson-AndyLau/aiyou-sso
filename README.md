# aiyou-sso
# 哎哟商城单点登录系统
## 一、项目总体架构
![商城系统总体架构](https://raw.githubusercontent.com/Jackson-AndyLau/pictures-storage/master/002/202011/QQ%E5%9B%BE%E7%89%8720200701113619.png)

**本B2C商城通过三层架构实现，分别为：表现层、服务层、持久层；**

**表现层包括：后台管理系统、B2C商城门户网站系统、搜索系统、商品详情系统、单点登录系统、购物车系统、订单系统；**

**服务层包括：商品服务、内容服务、单点登录服务、购物车服务、订单服务、搜索服务、图片服务；**

**持久层包括：Redis 集群、MySQL 集群、Solr 集群、FastDFS 集群；**

## 二、系统软硬件设施总体规划
### 1、系统服务规划
|类目|名称 |	服务器数量|	虚拟机数量	|IP地址|端口号|
|:---:|:---:|:---:|:---:|:---:|:---:|
|MySQL|	RDBMS，关系型数据库|	2	|1	|192.168.159.128|	3306~3307|
|Redis	|NoSQL，非关系型数据库	|6	|1	|192.168.159.129	|6001~6006|
|FastDFS|	文件服务器|	2|	1	|192.168.159.130	|Tracker 22122-22123 <br>Storage 23000-23001<br>Http_Port 8888-8889|
|Nginx|	WEB服务器	|2	|1|	192.168.159.131|	80|
|ActiveMQ|	消息中间件	|2	|1|	192.168.159.132|	8161~8162|
|Zookeeper|	服务注册中心	|3|	1|	192.168.159.133|	2181~2183|
|Solr|	文档索引库	|6	|1	|192.168.159.134	|2001~2006|

### 2、应用服务规划
|应用名称|	说明|服务器数量	|虚拟数量|	IP地址	|端口号|
|:---:|:---:|:---:|:---:|:---:|:---:|
|aiyou-manager|	商品后台管理服务|	2|	1	|192.168.159.135	|8001~8002|
|aiyou-content	|内容后台管理服务	|2	|1	|192.168.159.136|	8001~8002|
|aiyou-search	|索引库内容搜索服务|	2	|1	|192.168.159.137	|8001~8002|
|aiyou-manager-web	|商城后台管理系统	|2	|1|	192.168.159.138|	8001~8002|
|aiyou-portal-web|	商城门户网站系统|	2|	1|	192.168.159.139	|8001~8002|
|aiyou-search-web|	内容搜索系统	|2	|1	|192.168.159.140	|8001~8002|
|aiyou-item-web|	商品详情系统	|2|	1	|192.168.159.141|	8001~8002|
|aiyou-sso	|单点登录后台服务	|2|	1	|192.168.159.142	|8001~8002|
|aiyou-sso-web	|单点系统	|2	|1	|192.168.159.143	|8001~8002|
|aiyou-cart	|购物车后台服务	|2	|1|	192.168.159.144	|8001~8002|
|aiyou-cart-web	|购物车系统|	2	|1	|192.168.159.145	|8001~8002|
|aiyou-order	|订单后台服务|	2	|1	|192.168.159.146	|8001~8002|
vaiyou-order-web|	订单系统	|2|	1	|192.168.159.147|	8001~8002|

### 3、域名地址规划
|系统名称	|说明	|一级域名|	二级域名|
|:---:|:---:|:---:|:---:|
|aiyou-manager-web	|商城后台管理系统	|aiyou.cn|	manager.aiyou.cn|
|aiyou-portal-web	|商城门户网站系统|	aiyou.cn|	portal.aiyou.cn|
|aiyou-search-web|	内容搜索系统	|aiyou.cn|	search.aiyou.cn|
|aiyou-item-web|	商品详情系统|	aiyou.cn	|item.aiyou.cn|
|aiyou-sso-web|	单点系统	|aiyou.cn|	passport.aiyou.cn|
|aiyou-cart-web|	购物车系统|	aiyou.cn	|cart.aiyou.cn|
|aiyou-order-web|	订单系统	|aiyou.cn	|order.aiyou.cn|

## 三、项目数据库创建
**>从aiyou_common项目中拿sql进行导入到已创建的b2c_aiyou数据库中（如果不存在，则自行创建一个）**

**文件路径：aiyou-common\src\main\resources\sql\b2c_aiyou.sql**

## 四、项目拉取

### 1、aiyou-parent

项目地址：《 GitHub aiyou-parent 项目地址[GitHub aiyou-parent 项目地址](https://github.com/Jackson-AndyLau/aiyou-parent) 》
项目拉取地址：https://github.com/Jackson-AndyLau/aiyou-parent.git

 

2、aiyou-common

项目地址：《 GitHub aiyou-common 项目地址 》
项目拉取地址：https://github.com/Jackson-AndyLau/aiyou-common.git

 

3、aiyou-manager

项目地址：《 GitHub aiyou-manager 项目地址 》
项目拉取地址：https://github.com/Jackson-AndyLau/aiyou-manager.git

 

4、aiyou-content

项目地址：《 GitHub aiyou-content 项目地址 》
项目拉取地址：https://github.com/Jackson-AndyLau/aiyou-content.git

 

5、aiyou-manager-web

项目地址：《 GitHub aiyou-manager-web 项目地址 》
项目拉取地址：https://github.com/Jackson-AndyLau/aiyou-manager-web.git

 

6、aiyou-portal-web

项目地址：《 GitHub aiyou-portal-web 项目地址 》
项目拉取地址：https://github.com/Jackson-AndyLau/aiyou-portal-web.git

 

7、aiyou-search

项目地址：《 GitHub aiyou-search 项目地址 》
项目拉取地址：https://github.com/Jackson-AndyLau/aiyou-search.git

 

8、aiyou-search-web

项目地址：《 GitHub aiyou-search-web 项目地址 》
项目拉取地址：https://github.com/Jackson-AndyLau/aiyou-search-web.git

 

9、aiyou-item-web

项目地址：《 GitHub aiyou-item-web 项目地址 》
项目拉取地址：https://github.com/Jackson-AndyLau/aiyou-item-web.git

 

10、aiyou-sso

项目地址：《 GitHub aiyou-sso 项目地址 》
项目拉取地址：https://github.com/Jackson-AndyLau/aiyou-sso.git

 

11、aiyou-sso-web

项目地址：《 GitHub aiyou-sso-web 项目地址 》
项目拉取地址：https://github.com/Jackson-AndyLau/aiyou-sso-web.git

 

12、aiyou-cart

项目地址：《 GitHub aiyou-cart 项目地址 》
项目拉取地址：https://github.com/Jackson-AndyLau/aiyou-cart.git

 

13、aiyou-cart-web

项目地址：《 GitHub aiyou-cart-web 项目地址 》
项目拉取地址：https://github.com/Jackson-AndyLau/aiyou-cart-web.git

 

14、aiyou-order

项目地址：《 GitHub aiyou-order 项目地址 》
项目拉取地址：https://github.com/Jackson-AndyLau/aiyou-order.git

 

15、aiyou-order-web

项目地址：《 GitHub aiyou-order-web 项目地址 》
项目拉取地址：https://github.com/Jackson-AndyLau/aiyou-order-web.git

