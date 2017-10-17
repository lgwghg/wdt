//链接redis
var redis = require('redis'),
    RDS_PORT = 6379,                //端口号
    RDS_HOST = "192.168.10.47",       //服务器IP
    RDS_PWD = '123456',     //密码
    RDS_OPTS = {},                  //设置项
    client = redis.createClient(RDS_PORT,RDS_HOST,RDS_OPTS),
    redisClient = redis.createClient(RDS_PORT,RDS_HOST,RDS_OPTS);
	client.auth(RDS_PWD,function(){    
	    console.log('client通过认证');    
	});
    redisClient.auth(RDS_PWD,function(){    
        console.log('redisClient通过认证');    
    });

//socket 定义
var io = require('socket.io').listen(5678);

//log4j 定义
var logger = require('./logger');

//客户端集合
var clients = {};

//客户端个数
var count = 0;

/**
 * 订阅一个频道
 * @param   c 消息频道名称 默认 console
 * @throws Exception
 */
var sub = function(c)
{
    var c = c || "console";
    client.subscribe(c,function(e)
    {
        //console.log("starting subscribe channel:"+c);
        logger.access.info("starting subscribe channel:"+c);
    });
};

/**
 * 订阅一个频道
 * @param o
 * @throws Exception
 */
sub();

/**
 * 获取数组个数
 * @param o
 * @throws Exception
 */
var length = function(o)
{
    var t = typeof o;

    if(t == 'string')
    {
        return o.length;
    }
    else if(t == 'object')
    {
        var n = 0;

        for(var i in o)
        {
            n++;
        }

        return n;
    }

    return 0;
};

/**
 * 处理错误,如果出现错误,或者服务器断开了链接,等待恢复时,继续订阅这个频道
 * @param error
 * @throws Exception
 */
client.on("error", function(error)
{
    logger.error.error(error);
    sub();
});

/**
 * redis客户端关闭连接
 * @param err
 * @throws Exception
 */
redisClient.on('end',function(err)
{
    logger.access.info("redis end......");
});

/**
 * redis 数据库选择
 * @param 1
 * @throws Exception
 */
redisClient.select(1, function()
{
    logger.access.info("redis select 1......");
});

/**
 * redis客户端连接
 * @param connect
 * @throws Exception
 */
redisClient.on('connect',function()
{
    logger.access.info("redis connect......");
});

/**
 * 队列消息监听
 * @param  channel 消息通道名称
 *         data    信息对象数据
 * @throws Exception
 */
client.on('message', function(channel, data)
{
    //判断消息通道名称
    if (channel=="console")
    {
        //打印消息内容
        logger.system.debug(channel + "->" + data);

        //截取消息内容
        var start = data.indexOf("{");
        var args = null;

        if(start > -1)
        {
            args = JSON.parse(data.substr(start));
        }
        else
        {
            args = JSON.parse(data);
        }

        //打印并记录日志
        logger.access.info(args);
        //给所有客户端推送消息
        for(var s in clients)
        {
            var event = args.event || "notice";
            clients[s].emit(event, JSON.stringify(args));
        }
    }
});

/**
 * 客户端建立连接
 * @param  socket
 * @throws Exception
 */
io.on('connection', function(socket)
{
    //保存客户端地址信息
    var address = socket.request.connection.remoteAddress + "->" + socket.request.connection.remotePort;
    //打印并记录日志
    logger.access.info("client connected ["+ socket.id + "] " + address);
    //将客户端对象存入集合
    clients[socket.id] = socket;

    //客户端个数加1
    count++;
    //打印并记录日志
    logger.access.info("client count:[" + count+ "]");

    /**
     * 客户端断开连接
     * @param  socket
     * @throws Exception
     */
    socket.on('disconnect', function()
    {
        //打印并记录日志
        logger.access.info("client discopnnect [" + this.id + "] " + this.request.connection.remoteAddress + "->" + this.request.connection.remotePort);
        //删除集合元素
        delete clients[socket.id];
        //客户端个数减1
        count--;
        //打印并记录日志
        logger.access.info("client count:[" + count+ "]");
    });

    /**
     * 客户端事件监听
     * @param  message
     * @throws Exception
     */
    socket.on('event', function(message)
    {
        if(logger.isDebug(logger.system))
        {
            //打印并记录日志
            logger.system.debug(message);
        }
    });
});

