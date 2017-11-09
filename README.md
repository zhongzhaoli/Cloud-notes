# 前言
这篇文章将简单地描述一下随记的搭建过程，以及安装说明、使用手册、运行环境。
<br>
整个项目分为两部分，一部分是登录注册，一部分是笔记。
<br>
随记源代码(笔记)：https://github.com/zhongzhaoli/Cloud-notes
<br>
随记源代码(登录)：https://github.com/zhongzhaoli/Cloud-notes-user

随记从2017年10月初开始搭建，10月中旬完成所有前端页面，2017年11月完成基本功能，一直处于边学习边开发的状态。

# 成员
- 黄晓纯(产品经理) 
- 黄彩霞(美工设计)
- 金奕和(程序开发)
- 钟兆立(程序开发)

# 文件路径结构

```
Cloud-note (user)
  │
  ├─src/main
  │   │
  │   ├─java 存放java文件的地方
  │   │
  │   └─resources 存放静态文件的地方
  │
  │
  ├─upload 存放用户上传图片的地方
  │
  │
  ├─.gitignore 版本控制
  │
  │
  └─pom.xml 配置文件
```

# 安装说明
此项目可在windown 7版本及以上 安装
<br>
如需部署在docker看：http://blog.csdn.net/evankaka/article/details/50722788
<br>
此项目的一些依赖以及需要的环境都在**环境配置**中说明了。

# 运行环境

##### 第一步：导入项目
导入为maven项目，然后点Finish，项目就导入了。<br>
![image](http://oxzvqoqjq.bkt.clouddn.com/dr1)
![image](http://oxzvqoqjq.bkt.clouddn.com/dr2)
<br>

##### 第二步：下载编辑器
下载++Eclipse++编辑器
<br>
Eclipse 下载地址：https://www.eclipse.org/downloads/

##### 第三步：设置你的maven仓库
依次在任务栏点击Window - Preferences，输入框输入Maven，然后在左边的列表选择User Setting，设置你的maven仓库
![image](http://oxzvqoqjq.bkt.clouddn.com/dr3)

##### 第四步：下载consul.exe
consul是一个服务中心，这个项目也是依赖于服务中心，所以需要下载并在运行项目前运行服务它
<br>
consul.exe 下载地址：http://oxzvqoqjq.bkt.clouddn.com/consul.exe
<br>
操作流程：打开命令行，进入指定目录
![image](http://oxzvqoqjq.bkt.clouddn.com/dr4)
输入命令 consul agent -dev 回车
![image](http://oxzvqoqjq.bkt.clouddn.com/dr5)

##### 第五步：安装redis集群
因为是两个项目，所以要进行项目间的 通信(Spring session)依赖于redis集群


安装redis:
https://github.com/MSOpenTech/redis/releases/download/win-3.0.501/Redis-x64-3.0.501.msi
<br>
安装Ruby：http://dl.bintray.com/oneclick/rubyinstaller/rubyinstaller-2.2.4-x64.exe
<br>
##### 详细的配置过程看 https://github.com/ameizi/DevArticles/issues/129

##### 第六步：配置文件
数据库配置

```
server.port=8085 //端口可自定义
spring.application.name = Cloud-notes //项目名字必填
spring.datasource.url = jdbc:mysql://localhost:3306/Cloud-notes-user?useUnicode=true&characterEncoding=utf8 //数据库地址
spring.datasource.username = root //用户名
spring.datasource.password = 123456 //密码
```
服务注册与发现

```
management.context-path=/management //地址不用变
spring.cloud.consul.host=consul //服务发现
spring.cloud.consul.port=8500 //服务发现端口一般都是8500
```
redis集群配置

```
spring.redis.cluster.nodes=redis-master:6379 //集群端口 可选择其中一个
spring.redis.database=0
spring.redis.host=redis-master
spring.redis.password=123456 //密码
spring.redis.pool.max-active=8
spring.redis.pool.max-idle=8
spring.redis.pool.max-wait=-1
spring.redis.pool.min-idle=0
spring.redis.port=6379 //端口

spring.session.store-type=redis
spring.session.cookie.maxAge=360
spring.session.timeout=360 //session延迟

```

##### 第七步：运行项目
当项目没有报错，以及redis，consul安装完成后即可运行项目
![image](http://oxzvqoqjq.bkt.clouddn.com/dr6)

# 使用手册

##### 1. 访问网站
看自己的配置文件中的 server.port=xxxx 是多少端口
<br>
然后在网址栏输入 http://localhost: + 端口即可
##### 2. 注册登录
这个就不用我教了吧，直接输入用户名密码即可
###### 第三方登录
输入第三方登录的账号密码直接登录即可
##### 3 .更改个人信息 
![image](http://oxzvqoqjq.bkt.clouddn.com/dr7)点击头像或是个人信息都可以进入个人信息修改页面
![image](http://oxzvqoqjq.bkt.clouddn.com/dr8)
<br>
接下来输入个人信息即可修改，点击头像可修改头像
##### 4.创建笔记
![image](http://oxzvqoqjq.bkt.clouddn.com/dr9)
点击++新文档++即可添加笔记
##### 5.修改笔记
![image](http://oxzvqoqjq.bkt.clouddn.com/dr10)
点击左边笔记列表中的你想修改的笔记，可以修改它的标题以及内容，最后点击保存 或者Ctrl+S即可保存修改。
##### 6.删除笔记
![image](http://oxzvqoqjq.bkt.clouddn.com/dr11)
点击左边笔记列表中的你想删除的笔记，点击删除图标即可删除。
##### 7.分享笔记
![image](http://oxzvqoqjq.bkt.clouddn.com/dr12)点击左边笔记列表中你想要分享的笔记，![image](http://oxzvqoqjq.bkt.clouddn.com/dr13)输入你想分享的人的用户名，下面就会显示出对应的用户。点击即分享成功。
##### 8.分享笔记权限
![image](http://oxzvqoqjq.bkt.clouddn.com/dr14)这里的锁头表示，分享的用户可否修改此笔记。锁住代表无法修改，开锁代表可修改。
##### 9.删除分享
![image](http://oxzvqoqjq.bkt.clouddn.com/dr14)点击叉叉即可删除分享的用户。
##### 10.查看分享的笔记
![image](http://oxzvqoqjq.bkt.clouddn.com/dr15)右上角 两个小人的符号表示这是别人分享给你的笔记，下面可看出是谁分享给你的笔记。
##### 11.反馈
![image](http://oxzvqoqjq.bkt.clouddn.com/dr16)点击用户旁边的小箭头，下拉点击意见反馈，输入你对随记的意见与建议即可。
##### 12.退出登录
![image](http://oxzvqoqjq.bkt.clouddn.com/dr16)点击用户旁边的小箭头，下拉点击退出登录，即可退出当前账号。

# 致谢
再次感谢铱王星科技有限公司和深圳技师学院举办的此次比赛，让我们从中得到锻炼。<br>
感谢深圳技师学院电子信息系的老师们给予的建议与指导。
<br>
感谢极客工作室同学们的支持与帮助。
