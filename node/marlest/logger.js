var log4js = require('log4js');

log4js.configure({
    "appenders": [{
            "category": "access",
            "type": "dateFile",
            "filename": __dirname+"/logs/access.log",
            "pattern": "-yyyy-MM-dd.log",
            "absolute": false,                   // filename是否绝对路径
            "alwaysIncludePattern": false,       // 文件名是否始终包含占位符
            "backups": 10
    },
    {
        "category": "system",
        "type": "dateFile",
        "filename": __dirname+"/logs/system.log",
        "pattern": "-yyyy-MM-dd.log",
        "absolute": false,                   // filename是否绝对路径
        "alwaysIncludePattern": false,       // 文件名是否始终包含占位符
        "backups": 10
    },
    {
        "category": "error",
        "type": "dateFile",
        "filename": __dirname+"/logs/error.log",
        "pattern": "-yyyy-MM-dd.log",
        "absolute": false,                   // filename是否绝对路径
        "alwaysIncludePattern": false,       // 文件名是否始终包含占位符
        "backups": 10
    },
    {
        "type": "console"
    }],
    "levels": {
        "access": log4js.levels.ALL,
        "system": log4js.levels.INFO,
        "error": "ERROR"
    }
});

module.exports = {
    access: log4js.getLogger('access'),
    system: log4js.getLogger('system'),
    error: log4js.getLogger('error'),
    express: log4js.connectLogger(log4js.getLogger('access'), {level: log4js.levels.INFO}),

    isDebug: function(category) {
        return (log4js.levels.DEBUG.level >= category.level.level);
    }
};
