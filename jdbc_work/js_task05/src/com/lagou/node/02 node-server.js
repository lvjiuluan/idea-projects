const http = require("http") // node中自带的引入方法，返回一个服务对象
http.createServer(function (request,response) {
    // 响应http请求的头部信息
    // http状态码：200；ok
    // 请求的内容类型：text/plain
    response.writeHead(200,{"Content-Type":"text/plain"});
    // 响应的数据
    response.end("Hello, welcome!")
}).listen(8888);
console.log("服务器已启动，请访问 http://127.0.0.1:8888")