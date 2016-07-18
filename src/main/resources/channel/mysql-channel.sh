#!/bin/sh
# 同步数据到es，进行查询
# !!! 注意执行目录 elasticsearch-jdbc-2.3.3.0-dist 有缓存错误的配置文件的问题
#curl -XPUT 'http://192.168.99.101:9200/channel/'
#curl -XDELETE 'http://192.168.99.101:9200/channel/'

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
bin=${DIR}/../bin
lib=${DIR}/../lib

echo '
{
    "type" : "jdbc",
    "jdbc" : {
        "url" : "jdbc:mysql://localhost:3306/hibnatedb",
        "statefile" : "statefile.json",
        "schedule" : "0 0-59 0-23 ? * *",
        "user" : "root",
        "password" : "todo",
        "sql" : [{
                "statement": "select id as _id, id, name as name, createBy as auth from channel_auth"
                }
            ],
        "index" : "channel",
        "type" : "channel",
        "metrics": {
            "enabled" : true
        },
        "elasticsearch" : {
             "cluster" : "mycluster",
             "host" : "192.168.99.101",
             "port" : 9300 
        }   
    }
}
' | java \
    -cp "${lib}/*" \
    -Dlog4j.configurationFile=${bin}/log4j2.xml \
    org.xbib.tools.Runner \
    org.xbib.tools.JDBCImporter
