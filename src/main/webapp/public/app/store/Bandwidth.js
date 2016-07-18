Ext.define('AM.store.Bandwidth', {
    extend: 'Ext.data.Store',

    fields: ['name', 'code'],
    data: [
        {name: '100M',code: '100'},
        {name: '1G',code: '1000'},
        {name: '2G',code: '2000'},
        {name: '3G',code: '3000'},
        {name: '5G',code: '5000'},
        {name: '10G',code: '10000'}
    ]

});




