# Restful Web for Tencent_url_Security


## 部署 
1. 在这[两行代码][1]里填写你的 appid 及 key.
2. 安装好maven
3. 在 当前目录下 运行 `mvn test`
如果提示成功
4. `mvn clean jetty:run` 运行 




##测试 

web地址为: localhost:8060 

api 说明及事例：



返回参数说明：

|参数         |参数说明   |
|----------|-------------|
|-url        |   the url entered to query. 请求查询的网址  | 
|-status |   status status of query.  请求状态 |
|-urltype    |   Type of risk.  风险类型状态码|
|-urltype_ch |   风险类型.  风险类型|
|-eviltype   |   Malicious classification.  恶意类型分类码|
|-eviltype_ch|   恶意分类 |


如果要查询 `www.gayhub.com` 这个网址的安全性

只需浏览器输入 http://localhost:8060/query?url=www.gayhub.com

结果：
```
{
  "url": "www.gayhub.com",
  "status": 0,
  "urltype": 2,
  "urltype_ch": "风险",
  "eviltype": [
    2,
    5,
    8
  ],
  "eviltype_ch": [
    "信息诈骗",
    "博彩网站"
  ]
}
```


[1]:https://github.com/woailuoli993/tcUrlSec/blob/master/urlsec/src/main/java/udp_model/UdpUrlSec.java#L19-L20