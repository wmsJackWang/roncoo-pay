--获取key 以及一些参数值
--获取红包 key
local key = KEYS[1]
--获取剩余金额的key
local remainderkey = KEYS[2]

--获取账户hash 后的队列key
--local accListKey = KEYS[3]

--抢红包的金额 
local amt = tonumber(ARGV[1])

--账户merchantid:username
--local accInfo = ARGV[2] 


--获取redis中hash结构内的 红包剩余金额 

local remainder = redis.call('HGET',key,remainderkey)

--if amt > remainder then
--	return amt
--	end
local remainderamt = tonumber(remainder)


--如果红包剩余金额不足，则直接失败返回-1
if remainderamt < amt
then
	return -2
else
	--成功则将剩余金额减去
	local resultamt  = 0 - amt
	redis.call('Hincrbyfloat',key,remainderkey,resultamt)
	
	
	--将账户信息放入到队列中去
	--local result = accInfo..":"amt
	--redis.call('LPUSH',accListKey , result)
	
	return -1
end


--使用redis的hash
