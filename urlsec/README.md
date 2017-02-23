# Restful Web for Tencent_url_Security





##测试 

web地址为: http://192.168.223.63/

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

只需浏览器输入 http://192.168.223.63/query?url=www.gayhub.com

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