[TOC]  

## 说明    
提供Docker Registry的源代码走读说明    
## Git Repo   
[https://github.com/docker/distribution](https://github.com/docker/distribution)  

## 重要概念(接口定义)   
在distribution的package下定义了Docker Registry的一些Concepts
### Manifests    
[https://github.com/docker/distribution/blob/master/manifests.go](https://github.com/docker/distribution/blob/master/manifests.go)  
### Blobs    
[https://github.com/docker/distribution/blob/master/blobs.go](https://github.com/docker/distribution/blob/master/blobs.go)  
```
// Descriptor describes targeted content. Used in conjunction with a blob
// store, a descriptor can be used to fetch, store and target any kind of
// blob. The struct also describes the wire protocol format. Fields should
// only be added but never changed.
type Descriptor struct {
	// MediaType describe the type of the content. All text based formats are
	// encoded as utf-8.
	MediaType string `json:"mediaType,omitempty"`

	// Size in bytes of content.
	Size int64 `json:"size,omitempty"`

	// Digest uniquely identifies the content. A byte stream can be verified
	// against against this digest.
	Digest digest.Digest `json:"digest,omitempty"`

	// URLs contains the source URLs of this content.
	URLs []string `json:"urls,omitempty"`

	// NOTE: Before adding a field here, please ensure that all
	// other options have been exhausted. Much of the type relationships
	// depend on the simplicity of this type.
}
```
[what is digest?](https://github.com/opencontainers/go-digest)  
### Registry   
[https://github.com/docker/distribution/blob/master/registry.go](https://github.com/docker/distribution/blob/master/registry.go)  
```
// Repository is a named collection of manifests and layers.
type Repository interface {
	// Named returns the name of the repository.
	Named() reference.Named

	// Manifests returns a reference to this repository's manifest service.
	// with the supplied options applied.
	Manifests(ctx context.Context, options ...ManifestServiceOption) (ManifestService, error)

	// Blobs returns a reference to this repository's blob service.
	Blobs(ctx context.Context) BlobStore

	// TODO(stevvooe): The above BlobStore return can probably be relaxed to
	// be a BlobService for use with clients. This will allow such
	// implementations to avoid implementing ServeBlob.

	// Tags returns a reference to this repositories tag service
	Tags(ctx context.Context) TagService
}
```
Registry 用来聚合Manifests,Blobs, tags对象，是一个最高级的对象

## 实现细节    
具体的实现代码都放在distribution\registry目录下 
### Auth 授权
支持3种
- Apache htpassword
- silly
- JSON Web Token 
``` rawToken即为 user+password方式的Basic authentication
func NewToken(rawToken string) (*Token, error) {
 ... 
}
```
### 配置  
[代码](https://github.com/docker/distribution/blob/master/configuration/configuration.go)    
[配置文档](https://github.com/docker/distribution/blob/master/docs/configuration.md)
### CLI  
使用cobra定义cli的接口
[https://github.com/docker/distribution/blob/master/registry/root.go](https://github.com/docker/distribution/blob/master/registry/root.go)
```
func init() {
	RootCmd.AddCommand(ServeCmd)
	RootCmd.AddCommand(GCCmd)
	GCCmd.Flags().BoolVarP(&dryRun, "dry-run", "d", false, "do everything except remove the blobs")
	RootCmd.Flags().BoolVarP(&showVersion, "version", "v", false, "show the version and exit")
}
```
从代码上看，RootCmd（registry）下定义了2个sub Command
- ServerCmd - `registry serve <config>`
- GCCmd - `registry garbage-collect`

### REST API   
v2 API route都定义在[routes.go](https://github.com/docker/distribution/blob/master/registry/api/v2/routes.go)  
HTTP 的handlers定义在[distribution/registry/handlers](https://github.com/docker/distribution/blob/master/registry/handlers)目录下

### Storage Backends     
可以支持多种存储介质[(filesystem, oss, swift,s3,azure...)](https://github.com/docker/distribution/blob/master/registry/storage/driver/)      
StorageDriver的定义在[https://github.com/docker/distribution/blob/master/registry/storage/driver/storagedriver.go](https://github.com/docker/distribution/blob/master/registry/storage/driver/storagedriver.go)  
各种driver 是在[https://github.com/docker/distribution/blob/master/cmd/registry/main.go](https://github.com/docker/distribution/blob/master/cmd/registry/main.go)里init()加载进来的

#### Util method : pathFor  
获取各种路径: [https://github.com/docker/distribution/blob/master/registry/storage/paths.go](https://github.com/docker/distribution/blob/master/registry/storage/paths.go)

## 其他使用的代码库
### Cli
使用了[corbra](https://github.com/spf13/cobra)  
建议系统学习一下，以后CLI程序都可以使用它来。

### ServMux  
[github.com/gorilla/mux](github.com/gorilla/mux) Http Route 
是对标准库 http.DefaultServeMux 的封装

###  Digest  
[github.com/opencontainers/go-digest](github.com/opencontainers/go-digest)


### Aliyun SDK  
[github.com/denverdino/aliyungo](github.com/denverdino/aliyungo)  
[go get github.com/aliyun/aliyun-oss-go-sdk/oss](go get github.com/aliyun/aliyun-oss-go-sdk/oss)   
[官方维护地址](https://github.com/aliyun/alibaba-cloud-sdk-go)    