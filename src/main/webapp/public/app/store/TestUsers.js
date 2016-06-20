Ext.define('AM.store.TestUsers', {
    extend: 'Ext.data.Store',
    model: 'AM.model.TestUser',
    autoLoad: true,

    proxy: {
        type: 'ajax',
        api: {
            read: 'data/users.json',
            update: 'data/updateUsers.json'
        },
        reader: {
            type: 'json',
            root: 'users',
            successProperty: 'success'
        }
    }
});