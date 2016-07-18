//设置插件路径
//var webserver="http://127.0.0.1:9006/form/rest";
//nginx+lua
var webserver="http://192.168.99.101/extjsform/proxy";
var formserver='http://192.168.99.101/extjsform';

var abc=tttt;


var pageSize=23;

Ext.Loader.setConfig({enabled: true});
Ext.Loader.setPath('Ext.ux', '../ux');

Ext.Loader.setPath('Ext.ux', 'ext4.2/ux');

Ext.require([
    'Ext.grid.*',
    'Ext.data.*',
    'Ext.ux.grid.FiltersFeature',
    'Ext.toolbar.Paging',
    'Ext.ux.ajax.JsonSimlet',
    'Ext.ux.ajax.SimManager'
]);

//Ext.require([
//    '*',
//    'Ext.toolbar.Paging',
//    'Ext.ux.grid.FiltersFeature',//必不可少的
//    'Scripts.*'
//]);
//
//var filters = {
//    alias: 'widget.filters',
//    ftype: 'filters',
//
//    encode: false
//    //, // json encode the filter query
//
//    //指定要对哪些列进行过滤
//    //filters: [{
//    //    type: 'boolean',
//    //    dataIndex: 'IsSuccessed'
//    //}]
//};


//修正chrome 浏览器下extjs grid filter 的过滤的编辑面板不好用的问题
// fix hide submenu (in chrome 43)
Ext.override(Ext.menu.Menu, {
    onMouseLeave: function (e) {
        var me = this;


        // BEGIN FIX
        var visibleSubmenu = false;
        me.items.each(function (item) {
            if (item.menu && item.menu.isVisible()) {
                visibleSubmenu = true;
            }
        })
        if (visibleSubmenu) {
            //console.log('apply fix hide submenu');
            return;
        }
        // END FIX


        me.deactivateActiveItem();


        if (me.disabled) {
            return;
        }


        me.fireEvent('mouseleave', me, e);
    }
});

//验证错误不能变回问题，重写RowEditor解决问题
Ext.override(Ext.grid.RowEditor,
    {
        addFieldsForColumn: function (column, initial) {
            var me = this, i, length, field;
            if (Ext.isArray(column)) {
                for (i = 0, length = column.length; i < length; i++) {
                    me.addFieldsForColumn(column[i], initial);
                }
                return;
            }
            if (column.getEditor) {
                field = column.getEditor(null, {
                    xtype: 'displayfield',
                    getModelData: function () {
                        return null;
                    }
                });
                if (column.align === 'right') {
                    field.fieldStyle = 'text-align:right';
                }
                if (column.xtype === 'actioncolumn') {
                    field.fieldCls += ' ' + Ext.baseCSSPrefix + 'form-action-col-field';
                }
                if (me.isVisible() && me.context) {
                    if (field.is('displayfield')) {
                        me.renderColumnData(field, me.context.record, column);
                    } else {
                        field.suspendEvents();
                        field.setValue(me.context.record.get(column.dataIndex));
                        field.resumeEvents();
                    }
                }
                if (column.hidden) {
                    me.onColumnHide(column);
                } else if (column.rendered && !initial) {
                    me.onColumnShow(column);
                }

                // -- start edit
                me.mon(field, 'change', me.onFieldChange, me);
                // -- end edit
            }
        }
    });



Date.createFromMysql = function(mysql_string)
{
    var t, result = null;

    if( typeof mysql_string === 'string' )
    {
        t = mysql_string.split(/[- :]/);

        //when t[3], t[4] and t[5] are missing they defaults to zero
        result = new Date(t[0], t[1] - 1, t[2], t[3] || 0, t[4] || 0, t[5] || 0);
    }

    return result;
}

//对后端返回的json 进行标准化处理和封装；
function processResponse(success, operation, request, response, callback, scope) {

    //自定义--begin ,列表的时候初始化，新增和删除的时候读取total 数据
    console.log("process response:......" ,this.model.total);

    console.log("process responseText:......" ,response.responseText);
    console.log("process request:......" ,request.url);
    console.log("process operation:......" ,operation);
    console.log("process success:......" , success);

    //var mystrjson = response.responseText;
    var myobjson = Ext.decode("{}");
    if (!Ext.isEmpty(response.responseText)){
        var myobjson = Ext.decode(response.responseText);//将json字符串转换为对象
    }

    if (Ext.isEmpty(myobjson["DATA"])) {
        console.log("lua 没有进行filter 处理 ...........");

        //后台请求成功,处理返回数据
        if (success) {

            //var mystrjson2;
            var myobjson2 = Ext.decode("{}");
            myobjson2["DATA"] = myobjson;

            var page;
            //对后端返回的json 进行标准化处理和封装；
            if (request.action == 'read') {

                myobjson2["DATA"] = {};
                if (!Ext.isEmpty(myobjson._embedded)) {
                    //方法1:使用正则根据filter 特征获取数据的标签;
                    //var s = request.url;
                    //var dl = new RegExp("[a-zA-z0-9]+/(filter)", "i").exec(s)[0];
                    //var datalabel = /[a-zA-z0-9]+/.exec(dl)[0];
                    //
                    //console.log("datalabel:......", datalabel);
                    //console.log(myobjson._embedded[datalabel]);
                    //myobjson2["DATA"] = myobjson._embedded[datalabel];

                    //方法2:获取json key;利用json的数据层次获取数据
                    for (var key in myobjson._embedded) {
                        console.log(key);
                        console.log(myobjson._embedded[key]);
                        myobjson2["DATA"] = myobjson._embedded[key];
                    }
                }

                page = myobjson.page;
            }
            else if (request.action == 'create') {
                this.model.total = this.model.total + 1;

                page = {
                    "page": {
                        "totalElements": this.model.total
                    }
                };

            }

            else if (request.action == 'update') {
                page = {
                    "page": {
                        "totalElements": this.model.total
                    }
                };
            }

            else if (request.action == 'destroy') {
                this.model.total = this.model.total - 1;
                page = {
                    "page": {
                        "totalElements": this.model.total
                    }
                };
            }

            myobjson2["PAGE"] = page;

            //mystrjson2 = Ext.encode(myobjson2);//将json对象转换为json字符串 其中Mystrjson=Mystrjson2
            console.info("result response:......" + Ext.encode(myobjson2));//使用json对象属性

            response.responseText = Ext.encode(myobjson2);//将json对象转换为json字符串 其中Mystrjson=Mystrjson2

        } else {//后台处理失败,提示错误信息;debug = true 显示详细错误信息,显示概要描述信息.

            Ext.MessageBox.alert("操作失败", myobjson.message, function (id) {
                //var userStore = operation.getStore();
                var userStore = Ext.getStore('Users');
                userStore.reload();
            });

        }
    }

    //自定义--end

    var me = this,
        reader,
        result;

    if (success === true) {
        reader = me.getReader();

        // Apply defaults to incoming data only for read operations.
        // For create and update, there will already be a client-side record
        // to match with which will contain any defaulted in values.
        reader.applyDefaults = operation.action === 'read';

        result = reader.read(me.extractResponseData(response));


        if (result.success !== false) {
            //see comment in buildRequest for why we include the response object here
            Ext.apply(operation, {
                response: response,
                resultSet: result
            });

            operation.commitRecords(result.records);
            operation.setCompleted();
            operation.setSuccessful();
        } else {
            operation.setException(result.message);
            me.fireEvent('exception', this, response, operation);
        }
    } else {
        me.setException(operation, response);
        me.fireEvent('exception', this, response, operation);
    }

    //this callback is the one that was passed to the 'read' or 'write' function above
    if (typeof callback == 'function') {
        callback.call(scope || me, operation);
    }

    me.afterRequest(request, success);
}




function doRequest (operation, callback, scope) {
    console.log("do request:", operation);

    var writer = this.getWriter(),
        request = this.buildRequest(operation);
    if (operation.allowWrite()) {
        request = writer.write(request);
    }


    Ext.apply(request, {
        async: this.async,
        binary: this.binary,
        headers: this.headers,
        timeout: this.timeout,
        scope: this,
        callback: this.createRequestCallback(request, operation, callback, scope),
        method: this.getMethod(request),
        disableCaching: false
    });


    /*
     * do anything needed with the request object
     */
    //console.log('request', request);
    console.log('request.params', request.params);
    //对filter 参数进行加工处理  fixme 或者后台处理
    //var filterstr = new Array();
    //function filterArrayElements(element, index, array) {
    //    console.log("filter element:"+element.field);
    //    filterstr[index] = element.field + "=" + element.value;
    //}
    //request.params.filter.forEach(filterArrayElements);
    //
    //var params = {
    //    filter: filterstr
    //};
    //Ext.apply(operation.getstore().proxy.extraParams, params);


    Ext.Ajax.request(request);
    return request;
}

//4store 提交后处理
function afterRequest (request, success) {
    //var store=Ext.getStore("channelsid");
    //统一给用户修改提示信息
    console.log("after request:", request);

    //var mystrjson = response.responseText;
    //var mystrjson2;
    //var myobjson = Ext.decode(mystrjson);//将json字符串转换为对象


    var result = request.operation.success;

    if (request.action == 'read') {

        //记录记录总数，标准化返回参数。
        if (!Ext.isEmpty(request.operation.resultSet)){
            this.model.total = request.operation.resultSet.total;
            console.log("记录总数:",  this.model.total);
        }



        Ext.MessageBox.show({
            msg: '正在查询数据,请稍后...',
            progressText: 'Saving...',
            width: 300,
            wait: true,
            waitConfig: {interval: 200},
            icon: 'ext-mb-download', //custom class in msg-box.html
            iconHeight: 50
        });
        setTimeout(function () {
            //This simulates a long-running operation like a database save or XHR call.
            //In real code, this would be in a callback function.
            Ext.MessageBox.hide();
        }, 500);
    }

    else if (request.action == 'create') {
        if (result) {
            Ext.MessageBox.show({
                msg: '正在保存数据,请稍后...',
                progressText: 'Saving...',
                width: 300,
                wait: true,
                waitConfig: {interval: 200},
                icon: 'ext-mb-download', //custom class in msg-box.html
                iconHeight: 50
            });
            setTimeout(function () {
                //This simulates a long-running operation like a database save or XHR call.
                //In real code, this would be in a callback function.
                Ext.MessageBox.hide();
            }, 500);

            //Ext.Msg.alert('添加提示', '添加成功！');
            //store.reload();
        }
        //else {
        //    Ext.Msg.alert('添加提示', '添加失败！');
        //}
    }

    else if (request.action == 'update') {
        if (result) {
            Ext.MessageBox.show({
                msg: '正在保存数据,请稍后...',
                progressText: 'Saving...',
                width: 300,
                wait: true,
                waitConfig: {interval: 200},
                icon: 'ext-mb-download', //custom class in msg-box.html
                iconHeight: 50
            });
            setTimeout(function () {
                //This simulates a long-running operation like a database save or XHR call.
                //In real code, this would be in a callback function.
                Ext.MessageBox.hide();
            }, 500);
            //Ext.Msg.alert('提示', '更新成功！');
            //store.reload();
        }
        //else {
        //    Ext.Msg.alert('提示', '更新失败！');
        //}
    }

    else if (request.action == 'destroy') {
        if (result) {
            Ext.MessageBox.show({
                msg: '正在删除数据,请稍后...',
                progressText: 'Delete...',
                width: 300,
                wait: true,
                waitConfig: {interval: 200},
                icon: 'ext-mb-download', //custom class in msg-box.html
                iconHeight: 50
            });
            setTimeout(function () {
                //This simulates a long-running operation like a database save or XHR call.
                //In real code, this would be in a callback function.
                Ext.MessageBox.hide();
            }, 500);
            //Ext.Msg.alert('提示', '数据删除成功');
            //store.reload();
        }
        //else {
        //    //Ext.Msg.alert('提示', '数据删除失败');
        //}
    }
}



function metachange  (store, meta) {
    console.log("Version " + meta.version + "Search query " + meta.searchQuery);
}

//查询参数预处理
function beforeload (store, operation, eOpts) {
    console.log(store);
    console.log(operation);

    //排序参数预处理
    var sortstr = new Array();
    function sortArrayElements(element, index, array) {
        sortstr[index] = element.property + "," + element.direction;
    }
    operation.sorters.forEach(sortArrayElements);


    //将搜索条件放在store的baseParams中，每次加载都赋值
    //var new_params = { name: Ext.getCmp('search').getValue() };
    //Ext.apply(store.proxy.extraParams, "searchkeyword");
    //var params = {sthecstr:conditionstr};
    //Ext.apply(options.params, params);
    //store.baseParams = {limit: gridPageSize, filter: $('#txtFilter').val()};


    //翻页参数预处理，以及排序参数更新
    var params = {
        sort: sortstr,
        page: store.currentPage - 1
    };
    Ext.apply(store.proxy.extraParams, params);



}


//JS时区转载函数
//function formatTime(date){
//    //CST时间和UTC时差转换
//    var formatPattern = 'Y-m-d H:i:s';
//
//    var dateStr=date.toString();
//    var position=dateStr.indexOf("UTC");
//    if(position!=-1){
//        return Ext.util.Format.date(date, formatPattern);
//    }else{
//        console.log('here......');
//        var vdate=new Date(date); //后台默认为格林尼治时间，该方法会默认把后台时间认为是北京时间，北京时间比格林尼治时间多14小时。
//        //找出当地时间偏移值的毫秒数
//        var localOffset=vdate.getTimezoneOffset()*60000;
//        var utcOffset= vdate.getTime() + localOffset;
//        var timezone=utcOffset+8*3600000;
//        //var timezone=utcOffset;
//
//        var lastDate=new Date(timezone);
//        return Ext.util.Format.date(lastDate, formatPattern);
//    }
//}

//JS时区转载函数
//function formatDate(date){
//    //CST时间和UTC时差转换
//    var formatPattern = 'Y-m-d';
//
//    var dateStr=date.toString();
//    var position=dateStr.indexOf("UTC");
//    if(position!=-1){
//        return Ext.util.Format.date(date, formatPattern);
//    }else{
//        console.log('here......');
//        var vdate=new Date(date); //后台默认为格林尼治时间，该方法会默认把后台时间认为是北京时间，北京时间比格林尼治时间多14小时。
//        //找出当地时间偏移值的毫秒数
//        var localOffset=vdate.getTimezoneOffset()*60000;
//        var utcOffset= vdate.getTime() + localOffset;
//        var timezone=utcOffset+8*3600000;
//        //var timezone=utcOffset;
//
//        var lastDate=new Date(timezone);
//        return Ext.util.Format.date(lastDate, formatPattern);
//    }
//}

//function convertDiffTime(date){
//        console.log('here......'+date);
//        var formatPattern = 'Y-m-d H:i:s';
//
//        var vdate=new Date(date); //后台默认为格林尼治时间，该方法会默认把后台时间认为是北京时间，北京时间比格林尼治时间多14小时。
//        //找出当地时间偏移值的毫秒数
//        var localOffset=vdate.getTimezoneOffset()*60000;
//        var utcOffset= vdate.getTime() + localOffset;
//        var timezone=utcOffset+8*3600000;
//        //var timezone=utcOffset;
//
//        var lastDate=new Date(timezone);
//    console.log(lastDate);
//
//    console.log(Ext.util.Format.date(lastDate, formatPattern));
//
//        return timezone;
//
//}
//
//function convertDiffDate(date){
//    console.log('here......');
//    var formatPattern = 'Y-m-d H:i:s';
//
//    var vdate=new Date(date); //后台默认为格林尼治时间，该方法会默认把后台时间认为是北京时间，北京时间比格林尼治时间多14小时。
//    //找出当地时间偏移值的毫秒数
//    var localOffset=vdate.getTimezoneOffset()*60000;
//    var utcOffset= vdate.getTime() + localOffset;
//    var timezone=utcOffset+8*3600000;
//    //var timezone=utcOffset;
//
//    var lastDate=new Date(timezone);
//    console.log(Ext.util.Format.date(lastDate, formatPattern));
//
//    return lastDate;
//
//}
