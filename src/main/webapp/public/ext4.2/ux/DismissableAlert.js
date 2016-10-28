Ext.define('Ext.ux.DismissableAlert', {
    extend: 'Ext.Component',
    alias: 'widget.dismissalert',

    childEls: [
        'btnEl', 'alertTextEl', 'alertEl'
    ],

    renderTpl: ''.concat(
        '<div id="{id}-alertEl" class="alert {alertCls}">' +
        '<button id="{id}-btnEl" class="close">&times;</button>' +
        '<span id="{id}-alertTextEl">{text}</span>' +
        '</div>'),

    initComponent: function (){
        var me = this;
        me.callParent(arguments);
    },

    beforeRender: function () {
        var me = this;
        Ext.applyIf(me.renderData, {
            text: me.text || '&#160;',
            alertCls: me.alertCls || ''
        });
    },

    onRender: function(ct, position) {
        var me = this,
            btn;
        me.callParent(arguments);

        btn = me.btnEl;
        me.mon(btn, 'click', me.onClick, me);
    },

    onClick: function(e) {
        var me = this;
        if (me.preventDefault || (me.disabled && me.getHref()) && e) {
            e.preventDefault();
        }
        if (e.button !== 0) {
            return;
        }
        if (!me.disabled) {
            me.hide();
        }
    },

    setText: function (text){
        var me = this;
        me.text = text;
        if (me.rendered) {
            me.alertTextEl.update(text || '');
            me.updateLayout();
        }
    },

    showAlertBox:function (text, cls) {
        var me = this,
            alertEl = me.alertEl,
            oldCls = me.alertCls;
        me.text = text;
        me.alertCls = cls;

        if (me.rendered) {
            Ext.suspendLayouts();
            alertEl.removeCls(oldCls);
            alertEl.addCls(cls);
            me.alertTextEl.update(text || '');
            if (me.hidden) {
                me.show();
            }
            Ext.resumeLayouts(true);
        }
    },

    showError: function (text) {
        this.showAlertBox(text, 'alert-error');
    },

    showSuccess: function (text) {
        this.showAlertBox(text, 'alert-success');
    },

    showInfo: function (text) {
        this.showAlertBox(text, 'alert-info');
    },

    showStandard: function (text) {
        this.showAlertBox(text, '');
    }
});