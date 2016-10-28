Ext.define('LogViewer.store.Files', {
    extend: 'Ext.data.TreeStore',
    requires: ['LogViewer.model.File'],
    model: 'LogViewer.model.File',
    autoLoad: true,
    defaultRootId: '',
    proxy : {
        type: 'ajax',
        //url: './logviewer/data/files.json',
        url: '/form/rest/logviewer/files',
        reader: {
            type: 'json',
            readRecordsOnFailure: false,
            successProperty: 'success',
            messageProperty: 'message'
        }
    }
});
