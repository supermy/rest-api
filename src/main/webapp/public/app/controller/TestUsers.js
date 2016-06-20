Ext.define('AM.controller.TestUsers', {
    extend: 'Ext.app.Controller',

    views: [
        'testuser.List',
        'testuser.Edit'

    ],
    stores: [
        'TestUsers'
    ],
    models: [
        'TestUser'
    ],

    init: function() {
        this.control({
            'viewport > testuserlist': {
                itemdblclick: this.editUser
            },
            'testuseredit button[action=save]': {
                click: this.updateUser
            }
        });
    },

    editUser: function(grid, record) {
        console.log('Double clicked on ' + record.get('name'));
        var view = Ext.widget('testuseredit');

        view.down('form').loadRecord(record);
    },

    //updateUser: function(button) {
    //    console.log('clicked the Save button');
    //}

    updateUser: function(button) {
        var win    = button.up('window'),
            form   = win.down('form'),
            record = form.getRecord(),
            values = form.getValues();

        record.set(values);
        win.close();
        // synchronize the store after editing the record
        this.getUsersStore().sync();
    }

});