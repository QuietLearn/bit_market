/**
 * 单个symbol批量成交记录管理初始化
 */
var Tickers = {
    id: "TickersTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Tickers.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '日K线 收盘价', field: 'close', visible: true, align: 'center', valign: 'middle'},
            {title: '日K线 开盘价', field: 'open', visible: true, align: 'center', valign: 'middle'},
            {title: '日K线 最高价', field: 'high', visible: true, align: 'center', valign: 'middle'},
            {title: '日K线 最低价', field: 'low', visible: true, align: 'center', valign: 'middle'},
            {title: '24小时成交量', field: 'amount', visible: true, align: 'center', valign: 'middle'},
            {title: '24小时成交笔数', field: 'count', visible: true, align: 'center', valign: 'middle'},
            {title: '24小时成交额', field: 'vol', visible: true, align: 'center', valign: 'middle'},
            {title: '交易对', field: 'symbol', visible: true, align: 'center', valign: 'middle'},
            {title: '响应生成时间点，单位：毫秒', field: 'ts', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Tickers.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Tickers.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加单个symbol批量成交记录
 */
Tickers.openAddTickers = function () {
    var index = layer.open({
        type: 2,
        title: '添加单个symbol批量成交记录',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/tickers/tickers_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看单个symbol批量成交记录详情
 */
Tickers.openTickersDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '单个symbol批量成交记录详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/tickers/tickers_update/' + Tickers.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除单个symbol批量成交记录
 */
Tickers.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/tickers/delete", function (data) {
            Feng.success("删除成功!");
            Tickers.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("tickersId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询单个symbol批量成交记录列表
 */
Tickers.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Tickers.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Tickers.initColumn();
    var table = new BSTable(Tickers.id, "/tickers/list", defaultColunms);
    table.setPaginationType("client");
    Tickers.table = table.init();
});
