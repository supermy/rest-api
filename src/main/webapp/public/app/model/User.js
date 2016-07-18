Ext.define('AM.model.User', {
    extend: 'Ext.data.Model',
    total:2, //与数据库中记录个数有关系
    fields: [
        'pkId','username', 'password','namecn', 'tel', 'email',
        'qq', 'weixin',
        'enabled',
        {name: 'createDate', type: 'datetime'},
        //{name: 'updateDate', type: 'datetime'},
        {name: 'updateDate',type: 'datetime',convert:function(value){
            console.log("读写双向影响数据格式:"+value);
            //console.log("@@@@@@@@@@@@"+formatTime(value));
            return value;
            //return new date();
        }},
            'createBy', 'updateBy'],
    idProperty: 'pkId',
    clientIdProperty: 'pkId'
    //,
    //
    //validations: [{
    //    type: 'length',
    //    field: 'name',
    //    min: 4
    //}, {
    //    type: 'length',
    //    field: 'code',
    //    min: 4
    //}, {
    //    type: 'length',
    //    field: 'pwd',
    //    min: 10
    //}]
});