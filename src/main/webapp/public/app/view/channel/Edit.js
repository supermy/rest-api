Ext.define('AM.view.channel.Edit', {
    extend: 'Ext.window.Window',
    alias: 'widget.channeledit',

    title: '编辑渠道信息',
    layout: 'fit',
    autoShow: true,


    initComponent: function () {
        this.items = [
            {
                xtype: 'form',
                items: [
                    {
                        xtype: 'textfield',
                        name: 'name',
                        allowBlank: false,
                        fieldLabel: '名称'
                    },
                    {
                        xtype: 'textfield',
                        name: 'code',
                        allowBlank: false,
                        fieldLabel: '编码'
                    }
                    ,
                    {
                        xtype: 'textfield',
                        name: 'pwd',
                        allowBlank: false,
                        fieldLabel: '秘钥'
                    },
                    {
                        xtype: 'textfield',
                        name: 'tokenExpire',
                        allowBlank: false,
                        xtype: 'numberfield',
                        fieldLabel: '令牌有效期'
                    },
                    {
                        xtype: 'textfield',
                        name: 'iplist',
                        allowBlank: false,
                        fieldLabel: '服务器IP 地址'
                    },
                    {
                        xtype: 'textfield',
                        name: 'ipBindtime',
                        allowBlank: false,
                        xtype: 'numberfield',
                        fieldLabel: '禁止时长'
                    },
                    {
                        xtype: 'textfield',
                        name: 'ipTimeout',
                        allowBlank: false,
                        xtype: 'numberfield',
                        fieldLabel: '访问间隔'
                    },
                    {
                        xtype: 'textfield',
                        name: 'connectCount',
                        allowBlank: false,
                        xtype: 'numberfield',
                        fieldLabel: '访问次数'
                    },
                    {
                        xtype: 'textfield',
                        name: 'limitBandwidth',
                        allowBlank: false,
                        xtype: 'numberfield',
                        fieldLabel: '带宽'
                    },
                    {
                        xtype: 'checkboxfield',
                        checked: false,
                        fieldLabel: '状态',
                        boxLabel: '生效',
                        name: 'status',
                        inputValue: '0'
                    },
                    {
                        xtype: 'datefield',
                        name: 'createDate',
                        format: 'Y-m-d',
                        allowBlank: false,
                        fieldLabel: '创建日期'
                    },{
                        xtype: 'datefield',
                        name: 'updateDate',
                        format: 'Y-m-d H:i:s',

                        allowBlank: false,
                        fieldLabel: '更新时间'
                    }
        ]
            }
        ];

        this.buttons = [
            {
                text: 'Save',
                action: 'save'
            },
            {
                text: 'Cancel',
                scope: this,
                handler: this.close
            }
        ];

        this.callParent(arguments);
    }
});