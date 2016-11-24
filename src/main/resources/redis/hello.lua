local a=1;
local jsonstring = '{"a":1,"b":2}';
local json=cjson.decode(jsonstring);
return json.a;