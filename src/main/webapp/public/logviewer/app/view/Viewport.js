Ext.define('LogViewer.view.Viewport', {
    extend: 'Ext.container.Viewport',

    requires : [
        'Ext.tree.Panel',
        'Ext.grid.column.Date',
        'Ext.ux.DismissableAlert'
    ],

    layout: {
        type: 'vbox',
        align : 'stretch',
        pack  : 'start'
    },

    initComponent : function () {
        this.items = this.buildItems();
        this.callParent(arguments);
    },

    buildItems : function () {
        return [
            {
                itemId: 'myalert',
                xtype: 'dismissalert',
                hidden: true,
                padding: '5 5 0 5'
            },
            {
                flex: 1,
                itemId: 'fileTree',
                title: 'Logs',
                xtype: 'treepanel',
                store: 'Files',
                autoScroll: true,
                rootVisible: false,
                userArrows: true,
                columns: [
                    {xtype: 'treecolumn', text: 'Name', dataIndex: 'fileName', flex:2},
                    {text: 'Size', dataIndex: 'fileSize', flex:1},
                    {text: 'Last Modified', dataIndex: 'fileDate',  xtype: 'datecolumn', format:'Y-m-d\\TH:i:s', flex: 1},
                    {xtype: 'actioncolumn', icon: '../resources/images/icons/filebrowser.png', tooltip: 'Download', getClass: this.actionItemRenderer}
                ],
                tools:[{
                    itemId: 'refreshTool',
                    type:'refresh',
                    tooltip: 'Reload from disk'
                }]
            }
        ];
    },

    actionItemRenderer: function(value,meta,record,rowIx,ColIx, store) {
        return record.isLeaf() ? 'x-grid-center-icon': 'x-hide-display';
    }
});