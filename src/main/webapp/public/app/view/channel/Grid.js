Ext.define('AM.view.channel.Grid', {
    extend: 'Ext.grid.Panel',
    requires: [
        'Ext.ux.grid.FiltersFeature'
    ],
    features: [
        {
            //id: 'filter_feature',
            //ftype: 'filters',
            //autoReload: false, //don't reload automatically
            ////local: true,     //only filter locally
            //filters: []
            ftype: 'filters',
            encode: true
        }
    ],

    alias: 'widget.channelgrid',
    store: 'Channels',
    layout: {
        type: 'border'
    },
    frame: true,
    columnLines: true,
    closable: true,
    loadMask: true,

    title: '渠道秘钥管理',
    initComponent: function () {
        var me = this;
        Ext.applyIf(me, {

            dockedItems: [{
                xtype: 'toolbar',
                items: [{
                    text: '添加',
                    action: 'add',
                    iconCls: 'add_btn'
                }, {
                    text: '添加(form)',
                    action: 'addform',
                    iconCls: 'addform_btn'
                }, {
                    text: '删除',
                    itemId: 'deletechannel',
                    action: 'del',
                    disabled: true,
                    iconCls: 'del_btn'
                }]
            },
                {
                    weight: 2,
                    xtype: 'toolbar',
                    dock: 'bottom',
                    items: [
                        //    {
                        //    xtype: 'tbtext',
                        //    text: '<b>@cfg</b>'
                        //}, '|', {
                        //    text: 'autoSync',
                        //    enableToggle: true,
                        //    pressed: true,
                        //    tooltip: 'When enabled, Store will execute Ajax requests as soon as a Record becomes dirty.',
                        //    scope: this,
                        //    toggleHandler: function(btn, pressed){
                        //        this.store.autoSync = pressed;
                        //    }
                        //}, {
                        //    text: 'batch',
                        //    enableToggle: true,
                        //    pressed: true,
                        //    tooltip: 'When enabled, Store will batch all records for each type of CRUD verb into a single Ajax request.',
                        //    scope: this,
                        //    toggleHandler: function(btn, pressed){
                        //        this.store.getProxy().batchActions = pressed;
                        //    }
                        //}, {
                        //    text: 'writeAllFields',
                        //    enableToggle: true,
                        //    pressed: false,
                        //    tooltip: 'When enabled, Writer will write *all* fields to the server -- not just those that changed.',
                        //    scope: this,
                        //    toggleHandler: function(btn, pressed){
                        //        this.store.getProxy().getWriter().writeAllFields = pressed;
                        //    }
                        //},
                        {
                            xtype: 'pagingtoolbar',
                            id: 'id-pagingtoobar',
                            store: 'Channels',
                            dock: 'bottom',
                            displayInfo: true
                        }
                    ]
                }
                //    , {
                //    weight: 1,
                //    xtype: 'toolbar',
                //    dock: 'bottom',
                //    ui: 'footer',
                //    items: ['->', {
                //        iconCls: 'icon-save',
                //        text: 'Sync',
                //        scope: this,
                //        handler: this.onSync
                //    }]
                //}
            ],

            xtype: 'gridpanel',
            store: 'Channels',
            columns: [
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'pkId',
                    text: '序号',
                    flex: 1,
                    sortable: false
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'name',
                    text: '用户名',
                    flex: 2,
                    filterable: true,
                    filter: {type: 'string'},
                    editor: {
                        xtype: 'textfield',
                        allowBlank: false
                    }
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'code',
                    text: '密码',
                    flex: 2,
                    filter: {type: 'string'},
                    editor: {
                        xtype: 'textfield',
                        allowBlank: false
                    }
                }, {
                    text: ' 秘钥',
                    flex: 5,
                    width: 80,
                    sortable: true,
                    dataIndex: 'pwd',
                    editor: {
                        xtype: 'textfield',
                        allowBlank: false

                    }
                }, {
                    text: '令牌有<br>效期(天)',
                    flex: 1,
                    sortable: true,
                    dataIndex: 'tokenExpire',
                    editor: {
                        xtype: 'numberfield',
                        allowBlank: false,
                        minValue: 0,
                        maxValue: 10000
                    }
                }, {
                    header: ' 服务器IP 地址',
                    flex: 2,
                    width: 80,
                    sortable: true,
                    dataIndex: 'iplist',
                    editor: {
                        xtype: 'textfield'
                        ,
                        allowBlank: false
                    }
                }, {
                    text: ' 禁止时<br>长(秒)',
                    flex: 1,
                    width: 80,
                    sortable: true,
                    dataIndex: 'ipBindtime',
                    editor: {
                        xtype: 'numberfield',
                        allowBlank: false,
                        minValue: 0,
                        maxValue: 10000
                    }
                }, {
                    text: '访问间<br>隔(秒)',
                    flex: 1,
                    sortable: true,
                    dataIndex: 'ipTimeout',
                    //filter: {//todo
                    //    type: 'list',
                    //    store: 'MyOption'
                    //    //,phpMode: true
                    //},
                    editor: {
                        xtype: 'numberfield',
                        allowBlank: false,
                        minValue: 0,
                        maxValue: 10000
                    }
                }, {
                    header: '访问<br>(次)',
                    flex: 1,
                    width: 80,
                    sortable: true,
                    dataIndex: 'connectCount',
                    editor: {
                        xtype: 'numberfield',
                        allowBlank: false,
                        minValue: 0,
                        maxValue: 10000
                    }
                }
                ,
                {
                    text: '带宽',
                    sortable: true,
                    dataIndex: 'limitBandwidth',
                    flex: 2,
                    editor: {//下拉选框进行处理
                        xtype: 'combobox',
                        store: 'Bandwidth',
                        displayField: 'name',
                        valueField: 'code',
                        allowBlank: false,
                        editable: false,
                        queryMode: 'local',
                        forceSelection: true,
                        triggerAction: 'all'
                    }
                    ,
                    //renderer: function(value, metaData, record, row, col, store, gridView){
                    renderer: function (value) {//显示的时候，进行预处理
                        //return value;
                        var store11 = Ext.getStore('Bandwidth');
                        console.log("grid column renderer:" + store11);
                        var rec = store11.findRecord('code', value);
                        console.log("grid column renderer:" + rec);
                        return rec !== null ? rec.get("name") : '';
                    }
                },

                {
                    text: '状态',
                    xtype: 'checkcolumn',
                    flex: 1,
                    sortable: true,
                    dataIndex: 'status',
                    filter: {type: 'boolean'},
                    editor: {
                        xtype: 'checkbox',
                        cls: 'x-grid-checkheader-editor'
                    }
                }, {
                    text: '创建时间',
                    flex: 2,
                    sortable: true,
                    dataIndex: 'createDate',
                    xtype: 'datecolumn',
                    format: 'Y-m-d',
                    filter: {
                        format: 'Y-m-d',
                        type: 'date'
                    },
                    editor: {
                        xtype: 'datefield',
                        allowBlank: false,
                        format: 'Y-m-d',
                        minValue: '2015-01-01',
                        minText: 'Cannot have a start date before the company existed!',
                        //maxValue: Ext.Date.format(new Date(), 'Y-m-d'),
                        disabledDays: [0, 6],
                        disabledDaysText: '周末不允许安排工作计划'
                    }
                }, {
                    text: '更新时间',
                    flex: 3,
                    sortable: true,
                    dataIndex: 'updateDate',
                    xtype: 'datecolumn',
                    format: 'Y-m-d H:i:s',
                    filter: {type: 'date'}
                    //,
                    //editor: {
                    //
                    //}
                }, {
                    xtype: 'actioncolumn',
                    flex: 1,
                    width: 30,
                    sortable: false,
                    menuDisabled: true,
                    items: [{
                        icon: formserver+'/resources/images/icons/fam/delete.gif',
                        tooltip: '删除',
                        scope: this,
                        handler: this.onRemoveClick
                    }]
                }, {
                    xtype: 'actioncolumn',
                    flex: 1,
                    width: 30,
                    sortable: false,
                    menuDisabled: true,
                    items: [{
                        icon: formserver+'/resources/images/icons/fam/cog.gif',
                        tooltip: '编辑',
                        scope: this,
                        handler: this.onEditClick
                    }]
                }
            ],
            selType: 'rowmodel',
            plugins: [
                Ext.create('Ext.grid.plugin.RowEditing', {
                    id: 'rowEditing',
                    saveBtnText: '保存',
                    cancelBtnText: "取消",
                    errorSummary: false,
                    autoCancel: false,
                    clicksToEdit: 2   //双击进行修改  1-单击   2-双击    0-可取消双击/单击事件
                    ,
                    listeners: {
                        'edit': function (editor, e) {
                            console.log("edit......");
                            ////新增
                            //if(typeof (e.record.data.Id) == 'undefined'){
                            //    Ext.popup.msg(e.record.data.RoleName);
                            //    Ext.popup.msg('新增成功');
                            //}//修改
                            //else{
                            //    Ext.popup.msg(e.record.data.RoleName);
                            //    Ext.popup.msg('修改成功');
                            //    Ext.getCmp('pagingtoolbar').doRefresh();
                            //}
                        },
                        //edit: 'onRowEditorEdit',
                        'canceledit': function (rowEditing, context) {
                            // Canceling editing of a locally added, unsaved record: remove it
                            if (context.record.phantom) {
                                context.store.remove(context.record);
                            }
                        }
                    }
                })

            ]

        });
        me.callParent(arguments);
    }
    , onRemoveClick: function (grid, rowIndex) {
        var rec = grid.getStore().getAt(rowIndex);
        Ext.MessageBox.confirm('提示', '确认(' + rec.get('pkId') + ")[" + rec.get('name') + ']要删除这条记录',
            callBack);
        function callBack(id) {
            if (id == 'yes') {
                grid.getStore().removeAt(rowIndex);
                Ext.getCmp('id-pagingtoobar').doRefresh();
                if (grid.store.getCount() > 0) {
                    grid.getSelectionModel().select(0);
                }
                ;
            }
        }
    }, onEditClick: function (grid, rowIndex) {
        var rec = grid.getStore().getAt(rowIndex);
        console.log(rec);
        var view = Ext.widget('channeledit');
        view.down('form').loadRecord(rec);
    }


});