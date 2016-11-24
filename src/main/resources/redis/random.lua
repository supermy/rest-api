--根据队列keys[1]产生随机数
local curtime=redis.call('TIME');
math.randomseed(curtime[1]);
local length = redis.call('llen',KEYS[1])
local random = math.random(length)
return random;
