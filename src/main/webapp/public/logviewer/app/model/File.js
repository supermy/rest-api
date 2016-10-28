Ext.define('LogViewer.model.File', {
    extend: 'Ext.data.Model',
    fields: [
        'fileName','fileSize',
        {name:'fileDate',type: 'date', dateFormat: 'c'},
        'filePath'
    ]
});