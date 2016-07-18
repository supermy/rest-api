Ext.define('AM.store.MyOption', {
    extend: 'Ext.data.Store',
    fields: ['id', 'text'],
    proxy: {
        type: 'ajax',
        url: 'data/option.json',
        reader: 'array'
    }
});

