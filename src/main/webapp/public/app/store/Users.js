Ext.define('AM.store.Users', {
    extend: 'Ext.data.Store',
    storeId: 'usersid',

    total: 1,//数据增减的时候，记录动态记录总数

    autoLoad: true,
    autoSync: true,

    remoteFilter: true,
    remoteSort: true,

    pageSize: pageSize,
    currentPage: 1,

    sorters: [{
        property: 'username',
        direction: 'ASC'
    }],


    model: 'AM.model.User',

    proxy: {
        type: 'rest',
        url: webserver+'/user/filter', //带filter 查询的支撑后台
        limitParam: 'size',
        reader: {
            type: 'json',
            totalProperty: 'PAGE.totalElements',
            root: 'DATA'
        },
        writer: {
            type: 'json'
        },


        processResponse: processResponse,//针对spring-data-rest 的返回值统一预处理为DATA
        doRequest: doRequest,//
        afterRequest: afterRequest  //操作提示统一处理
    },

    listeners: {

        metachange: metachange,
        beforeload: beforeload  //提交参数预处理
    }

});




