Ext.define('LogViewer.controller.Viewport', {
    extend: 'Ext.app.Controller',

    requires: [
        'Ext.grid.column.Action'
    ],

    stores: [
        'Files'
    ],

    refs: [
        { ref: 'fileTree', selector: '#fileTree' },
        { ref: 'alertBox', selector: '#myalert'}
    ],

    init : function () {
        Ext.log('init viewport controller');
        this.getFilesStore().getProxy().on({
            exception: this.onProxyException,
            scope: this
        });
        this.control({
            'viewport #fileTree' : {
                itemclick : this.fileClick
            },
            'viewport tool[type="refresh"]' : {
                click: this.onRefresh
            },
            'viewport actioncolumn' : {
                click: this.handleActionColumn
            }
        });
    },

    fileClick : function(view, record, item, index, event) {
        Ext.log("in fileClick ");
        var logWin, fileName, cleanName, logUrl;

        if (record.isLeaf()) {
            fileName = record.get('filePath');
            logUrl = Ext.String.urlAppend('/logviewer/open', 'fileName='+fileName);
            cleanName = fileName.replace(/\W/g, '')
            Ext.log('open [' + logUrl + '] name [' + cleanName + ']');
        }
    },

    onRefresh : function() {
        Ext.log('in refresh');
        var fileTree = this.getFileTree();
        this.getAlertBox().hide();
        fileTree.getStore().load();
    },

    handleActionColumn : function(view, row, col, item, e, record) {
        var logWin, fileName, downloadUrl;
        Ext.log("handleActionColumn");
        if (record.isLeaf()) {
            fileName = record.get('filePath');
            this.onDownload(fileName);
        }
        return false;
    },

    onDownload : function(fileName) {
        Ext.log("Exporting to Excel");

        var frame, form, hidden, params, url;

        frame = Ext.fly('exportframe').dom;
        frame.src = Ext.SSL_SECURE_URL;

        form = Ext.fly('exportform').dom;
        url = '../util/htmlresponse.json';
        form.action = url;
        hidden = document.getElementById('excelconfig');
        params = {fileName: fileName};
        hidden.value = Ext.encode(fileName);

        form.submit();
    },

    onProxyException : function(proxy, response, operation) {
        var alertBox = this.getAlertBox();
        alertBox.showError("An unexpected error occurred.");
    }
});