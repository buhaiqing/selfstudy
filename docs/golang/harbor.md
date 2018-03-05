[TOC]  

## 说明    
提供VMWare Harbor的源代码走读说明    
## Git Repo   
[https://github.com/vmware/harbor](https://github.com/vmware/harbor)

## 架构      
![](https://camo.githubusercontent.com/e0de62fb4f08efedd2c5abd44786410d3af06c7b/687474703a2f2f7777772e7468696e6b2d666f756e6472792e636f6d2f77702d636f6e74656e742f75706c6f6164732f323031362f30392f61727469636c65315f696d616765322e706e67)    

[Architecture Doc](https://github.com/vmware/harbor/wiki/Architecture-Overview-of-Harbor)

![](https://camo.githubusercontent.com/ed9cbaeba04a4084f126829a4928105ebcd54453/687474703a2f2f7777772e7468696e6b2d666f756e6472792e636f6d2f77702d636f6e74656e742f75706c6f6164732f323031362f30392f61727469636c65315f696d616765332e706e67)    
Authentication过程

## 实现细节    
### Common  
定义了可以共享给UI和Jobservice，AdminServer的公共部分  
#### modles     
定了db数据模型  
#### dao   
Dao Repository 
#### config
定义了Manager对象。这里的配置只是负责管理System config。Harbor整体的配置的数据结构参见ui/config (mg * comffg.Manager)
```
var (
    // SecretStore manages secrets
    SecretStore *secret.Store
    // AdminserverClient is a client for adminserver
    AdminserverClient client.Client
    // GlobalProjectMgr is initialized based on the deploy mode
    GlobalProjectMgr promgr.ProjectManager
    mg               *comcfg.Manager
    keyProvider      comcfg.KeyProvider
    // AdmiralClient is initialized only under integration deploy mode
    // and can be passed to project manager as a parameter
    AdmiralClient *http.Client
    // TokenReader is used in integration mode to read token
    TokenReader admiral.TokenReader
)
```

### Admin Server  
#### 配置   
系统基本的配置统一放置在config.json中
[接口定义](https://github.com/vmware/harbor/blob/master/src/adminserver/client/client.go)  
```
// Client defines methods that an Adminserver client should implement
type Client interface {
    // Ping tests the connection with server
    Ping() error
    // GetCfgs returns system configurations
    GetCfgs() (map[string]interface{}, error)
    // UpdateCfgs updates system configurations
    UpdateCfgs(map[string]interface{}) error
    // ResetCfgs resets system configuratoins form environment variables
    ResetCfgs() error
    // Capacity returns the capacity of image storage
    Capacity() (*imagestorage.Capacity, error)
}
```
#### Http Route  
[newRouter](https://github.com/vmware/harbor/blob/master/src/adminserver/handlers/router.go)   

### UI  -为前端界面展示提供的API服务      
多处使用[Beego](https://beego.me/)：  
- Controller : HTTP Route  
- Model: ORM  
- Dao：Resitory  

如果对Beego不熟悉，建议先学习它  
#### 代码入口  
[https://github.com/vmware/harbor/blob/master/src/ui/main.go](https://github.com/vmware/harbor/blob/master/src/ui/main.go)
 
#### Models  
- [https://github.com/vmware/harbor/blob/master/src/common/models](https://github.com/vmware/harbor/blob/master/src/common/models)   
- [https://github.com/vmware/harbor/blob/master/src/ui/api/models](https://github.com/vmware/harbor/blob/master/src/ui/api/models)   
#### HTTP Route  
[initRouters](https://github.com/vmware/harbor/blob/master/src/ui/router.go)  


### ui_ng 前端界面
使用Angular + Typescript

### Job Service    
因为现在我们没有使用，暂未走读。  



## 其他使用的代码库

### Beego   
[github.com/astaxie/beego](github.com/astaxie/beego)  

### logrus  
[https://github.com/sirupsen/logrus](https://github.com/sirupsen/logrus)  

### sling - HTTP client库  
[https://github.com/dghubble/sling](https://github.com/dghubble/sling)  
