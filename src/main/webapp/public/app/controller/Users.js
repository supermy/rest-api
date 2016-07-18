Ext.define('AM.controller.Users', {
    extend: 'Ext.app.Controller',
    rectotal: 1,
    views: [
        'user.Grid'
    ],
    stores: [
        'Users'
    ],
    models: [
        'User'
    ]
    ,
    init: function () {
        this.control({
            'usergrid': {
                render: this.loadUserInfo
            },
            'usergrid>toolbar button[action=add]': {
                click: this.addUser
            },
            'usergrid>toolbar button[action=del]': {
                click: this.delUser
            }
            ,
            'usergrid': {
                selectionchange: this.selectionChange
            }
        });
    },


    loadUserInfo: function (obj) {
        var store = obj.getStore();
        store.reload();
    },

    addUser: function (btn) {

        //'pkId','username', 'password','namecn', 'tel', 'email',
        //    'qq', 'weixin',
        //    'enabled',
        //{name: 'createDate', type: 'date'},
        //{name: 'updateDate', type: 'date'},
        //    'createBy', 'updateBy'],

        var r = Ext.create('AM.model.User', {
            username: 'newuser'+Math.round(Math.random()*1000),
            password: '口令'+Math.round(Math.random()*1000),
            namecn: '新用户',
            tel: '1861088198',
            email: 'moyong@bonc.com.cn',
            qq: '252050572',
            weixin: 'supermy6',
            updateBy: 'jamesmo',
            createBy: 'jamesmo',
            enabled: false
            ,
            //createDate: Ext.Date.now(),
            //updateDate: Ext.Date.now()
            createDate: new Date(),
            updateDate: new Date()
        });

        this.getUsersStore().insert(0, r);

        console.log("add user",Ext.getCmp('id-pagingtoobar'));

        Ext.getCmp('id-pagingtoobar').doRefresh();

        var rowEditing = btn.up('gridpanel').editingPlugin;
        rowEditing.startEdit(0, 0);

        //var pagingtoolbar = btn.up('panel').down('pagingtoolbar').getStore();

        //store.reload();//fixme
        //console.log(store);


    },



    delUser: function (btn) {
        var grid = btn.up('usergrid');

        var store = grid.getStore();
        var sm = grid.getSelectionModel();
        var selectedRecord = sm.getSelection();
        console.info(selectedRecord[0]);

        Ext.MessageBox.confirm('提示', '确认(' + selectedRecord[0].get('pkId') + ")[" + selectedRecord[0].get('username') + ']要删除这条记录',
            callBack);

        function callBack(id) {
            if (id == 'yes') {

                store.remove(selectedRecord);
                store.reload();

                Ext.getCmp('id-pagingtoobar').doRefresh();

                if (store.getCount() > 0) {
                    sm.select(0);
                }

            }
        }


    },

    //激活删除按钮
    selectionChange: function (selModel, selections) {
        console.log("selection change......");
        var delBtn = Ext.ComponentQuery.query("usergrid button[iconCls=del_btn]")[0];
        delBtn.setDisabled(selections.length === 0);
    }

});
