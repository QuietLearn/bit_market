/**
 * 单个symbol批量成交记录管理初始化
 */
var MarketDetail = {
    id: "MarketDetailTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
MarketDetail.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '消息id', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '24小时成交量', field: 'amount', visible: true, align: 'center', valign: 'middle'},
            {title: '近24小时累积成交数', field: 'count', visible: true, align: 'center', valign: 'middle'},
            {title: '前推24小时成交价', field: 'open', visible: true, align: 'center', valign: 'middle'},
            {title: '当前成交价', field: 'close', visible: true, align: 'center', valign: 'middle'},
            {title: '近24小时最低价', field: 'low', visible: true, align: 'center', valign: 'middle'},
            {title: '近24小时最高价', field: 'high', visible: true, align: 'center', valign: 'middle'},
            {title: '近24小时累积成交额, 即 sum(每一笔成交价 * 该笔的成交量)', field: 'vol', visible: true, align: 'center', valign: 'middle'},
            {title: '24小时统计时间,', field: 'ts', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
MarketDetail.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        MarketDetail.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加单个symbol批量成交记录
 */
MarketDetail.openAddMarketDetail = function () {
    var index = layer.open({
        type: 2,
        title: '添加单个symbol批量成交记录',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/marketDetail/marketDetail_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看单个symbol批量成交记录详情
 */
MarketDetail.openMarketDetailDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '单个symbol批量成交记录详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/marketDetail/marketDetail_update/' + MarketDetail.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除单个symbol批量成交记录
 */
MarketDetail.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/marketDetail/delete", function (data) {
            Feng.success("删除成功!");
            MarketDetail.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("marketDetailId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询单个symbol批量成交记录列表
 */
MarketDetail.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    MarketDetail.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = MarketDetail.initColumn();
    var table = new BSTable(MarketDetail.id, "/marketDetail/list", defaultColunms);
    table.setPaginationType("client");
    MarketDetail.table = table.init();
});
