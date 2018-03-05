[TOC]

## Git Repo  
[https://github.com/astaxie/beego](https://github.com/astaxie/beego)  


## 整体架构  
![](https://beego.me/docs/images/architecture.png)
## 执行逻辑  
![](https://beego.me/docs/images/flow.png)



## 主要模块     
各个模块都可以使用  
### context   
上下文模块是用的最多的，需要重点了解其中的数据结构
[https://beego.me/docs/module/context.md](https://beego.me/docs/module/context.md)
### orm  


### httplibs   
可以理解为已http client库， 如[req](https://github.com/imroc/req)  
### config    
配置的模型如果比较简单可以考虑使用，复杂的考虑使用[spf13/viper](https://github.com/spf13/viper)   

## 突出特色
### swagger 
[https://beego.me/docs/advantage/docs.md](https://beego.me/docs/advantage/docs.md)
### 支持脚手架  
参考[bee](https://beego.me/docs/install/bee.md)   
