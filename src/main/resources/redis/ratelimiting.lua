--[[
实现访问频率的脚本.
参数:
    KEY[1] 用来标识同一个用户的id
    ARGV[1] 过期时间
    ARGV[2] 过期时间内可以访问的次数
返回值: 如果没有超过指定的频率, 则返回1; 否则返回0
]]
local times = redis.call('incr', KEYS[1])

if times == 1 then
    -- 说明刚创建, 设置生存时间
    redis.call('expire', KEYS[1], ARGV[1])
end

if times > tonumber(ARGV[2]) then
    return 0
end

return 1
