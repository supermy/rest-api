Ext.application({
    requires: ['Ext.container.Viewport'],
    name: 'AM',

    controllers: [
        'TestUsers'
    ],

    appFolder: 'app',

    launch: function() {
        Ext.create('Ext.container.Viewport', {
            layout: 'fit',
            items: {
                xtype: 'testuserlist'
            }
        });
    }
});