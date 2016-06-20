Ext.define('AM.view.testuser.List' ,{
    extend: 'Ext.grid.Panel',
    alias: 'widget.testuserlist',

    title: '所有用户',

    store: 'TestUsers',

    initComponent: function() {

        this.columns = [
            {header: '名称',  dataIndex: 'name',  flex: 1},
            {header: '电邮', dataIndex: 'email', flex: 1}
        ];

        this.callParent(arguments);
    }
});