--2、判断用户参与一次抽奖之后不允许再次抽奖。  抽奖次数=>1,增加用户访问次数，返回奖品为空。
--3、将用户加入以参与抽奖队列。
--4、用户中奖一次的不允许抽奖。 中奖次数.>1，返回奖品为空。
--5、用随机数去队列里匹配对应的奖品；
--5.1 对应奖品减一，返回奖品数大于等于1，则可中奖  ；将用户加入中奖队列。 ；返回用户所抽中奖品。
--5.2 奖品数<=0，用户没有中奖；返回奖品为空。

-- 参数：1.用户手机号，2.奖品表队列名，3.抽奖队列名,4.已参与用户，5.中奖用户
-- telNumber--KEYS[1],
-- LOTTERY_LIST+period---KEYS[2],
-- AWARD_LIST+period---KEYS[3] ,
-- JOIN_USER_LIST+period---KEYS[4],
-- AWARD_USER_LIST+period---KEYS[5]


--判断用户参与一次抽奖之后不允许再次抽奖。  抽奖次数=>1,增加用户访问次数，返回奖品为空。将用户加入以参与抽奖队列。
if redis.call('hincrby', KEYS[4], KEYS[1], 1) > 1 then
    return nil
end
--用户中奖一次的不允许抽奖。 中奖次数.>1，返回奖品为空。
if redis.call('hincrby', KEYS[5], KEYS[1], 1) > 1 then
    return nil
end

--用随机数去队列里匹配对应的奖品；奖品数<=0，用户没有中奖；返回奖品为空。
local ll = redis.call('lindex', KEYS[3], KEYS[6])
if redis.call('hincrby', KEYS[2], ll, -1) < 0 then
    return nil
end

--对应奖品减一，返回奖品数大于等于1，则可中奖  ；将用户加入中奖队列。
redis.call('hincrby', KEYS[5], KEYS[1], 1)

--返回用户所抽中奖品。
if ll then
    local x = cjson.decode('{\"lottery\":' .. ll .. '}')

    x['telNumber'] = KEYS[1]
    local re = cjson.encode(x)
    return re
end

return nil;
