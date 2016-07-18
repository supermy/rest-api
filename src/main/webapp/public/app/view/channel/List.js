Ext.define('AM.view.channel.List', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.channellist',

    title: '所有渠道',

    store: 'Channels',

    initComponent: function () {

        this.columns = [
            {header: '主键', dataIndex: 'pkId', flex: 1},
            {header: '名称', dataIndex: 'name', flex: 1},
            {header: '编码', dataIndex: 'code', flex: 1}
            //,
            //{header: '秘钥',  dataIndex: 'pwd',  flex: 1},
            //{header: '令牌有效期', dataIndex: 'tokenExpire', flex: 1},
            //{header: '服务器IP 地址',  dataIndex: 'iplist',  flex: 1},
            //{header: '禁止时长', dataIndex: 'ipBindtime', flex: 1},
            //{header: '访问间隔',  dataIndex: 'ipTimeout',  flex: 1},
            //{header: '访问次数', dataIndex: 'connectCount', flex: 1},
            //{header: '带宽',  dataIndex: 'limitBandwidth',  flex: 1},
            //{header: '状态', dataIndex: 'status', flex: 1}
        ];

        this.callParent(arguments);
    }
});