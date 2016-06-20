Ext.define('AM.view.testuser.Edit', {
    extend: 'Ext.window.Window',
    alias: 'widget.testuseredit',

    title: '用户编辑',
    layout: 'fit',
    autoShow: true,

    initComponent: function() {
        this.items = [
            {
                xtype: 'form',
                items: [
                    {
                        xtype: 'textfield',
                        name : 'name',
                        fieldLabel: '名称'
                    },
                    {
                        xtype: 'textfield',
                        name : 'email',
                        fieldLabel: '电邮'
                    }
                ]
            }
        ];

        this.buttons = [
            {
                text: '保存',
                action: 'save'
            },
            {
                text: '取消',
                scope: this,
                handler: this.close
            }
        ];

        this.callParent(arguments);
    }
});