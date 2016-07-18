#!/usr/bin/env bash
#mysql 的数据同步到redis;同步时间间隔由crontab 控制
#–raw: 使mysql不转换字段值中的换行符。
#–skip-column-names: 使mysql输出的每行中不包含列名。
#单独执行 echo -en '*3\r\n$3\r\nSET\r\n$3\r\nkey\r\n$5\r\nvalue\r\n' | redis-cli --pipe
#mysql -h 127.0.0.1 -uroot -pxxxxxx -Dtest --skip-column-names --raw <mysql2redis.sql |redis-cli --pipe
mysql -h 192.168.59.103 -ujava -pjava -Djavatest --skip-column-names --raw < mysql2redis.sql |redis-cli -h 192.168.59.103 --pipe



echo -en '*10\r\n$5\r\nHMSET\r\n$9\r\nhash_test\r\n$12\r\nip_bind_time\r\n$3\r\n300\r\n$11\r\nip_time_out\r\n$2\r\n60\r\n$13\r\nconnect_count\r\n$3\r\n100\r\n$3\r\npwd\r\n$4\r\ntest\r\n' | redis-cli --pipe

echo -en '*14\r\n$5\r\nHMSET\r\n$9\r\nhash_test\r\n$12\r\nip_bind_time\r\n$3\r\n300\r\n$11\r\nip_time_out\r\n$2\r\n60\r\n$13\r\nconnect_count\r\n$3\r\n100\r\n$3\r\npwd\r\n$4\r\ntest\r\n$6\r\niplist\r\n$14\r\n192.168.59.103\r\n$5\r\ntoken\r\n$2\r\n22\r\n' | redis-cli --pipe