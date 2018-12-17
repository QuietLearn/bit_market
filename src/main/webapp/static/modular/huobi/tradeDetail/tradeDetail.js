/**
 * 单个symbol批量成交记录管理初始化
 */
var TradeDetail = {
    id: "TradeDetailTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
TradeDetail.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '消息id', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '成交id', field: 'bargainId', visible: true, align: 'center', valign: 'middle'},
            {title: '成交价钱', field: 'price', visible: true, align: 'center', valign: 'middle'},
            {title: '成交量', field: 'amount', visible: true, align: 'center', valign: 'middle'},
            {title: '主动成交方向', field: 'direction', visible: true, align: 'center', valign: 'middle'},
            {title: '最新成交时间', field: 'ts', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
TradeDetail.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        TradeDetail.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加单个symbol批量成交记录
 */
TradeDetail.openAddTradeDetail = function () {
    var index = layer.open({
        type: 2,
        title: '添加单个symbol批量成交记录',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/tradeDetail/tradeDetail_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看单个symbol批量成交记录详情
 */
TradeDetail.openTradeDetailDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '单个symbol批量成交记录详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/tradeDetail/tradeDetail_update/' + TradeDetail.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除单个symbol批量成交记录
 */
TradeDetail.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/tradeDetail/delete", function (data) {
            Feng.success("删除成功!");
            TradeDetail.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("tradeDetailId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询单个symbol批量成交记录列表
 */
TradeDetail.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    TradeDetail.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = TradeDetail.initColumn();
    var table = new BSTable(TradeDetail.id, "/tradeDetail/list", defaultColunms);
    table.setPaginationType("client");
    TradeDetail.table = table.init();
});
