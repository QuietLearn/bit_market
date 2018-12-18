/**
 * 买卖盘管理初始化
 */
var Order = {
    id: "OrderTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Order.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '成交价', field: 'price', visible: true, align: 'center', valign: 'middle'},
            {title: '成交量', field: 'amount', visible: true, align: 'center', valign: 'middle'},
            {title: '0 买盘bids   1 卖盘asks', field: 'type', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Order.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Order.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加买卖盘
 */
Order.openAddOrder = function () {
    var index = layer.open({
        type: 2,
        title: '添加买卖盘',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/order/order_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看买卖盘详情
 */
Order.openOrderDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '买卖盘详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/order/order_update/' + Order.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除买卖盘
 */
Order.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/order/delete", function (data) {
            Feng.success("删除成功!");
            Order.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("orderId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询买卖盘列表
 */
Order.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Order.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Order.initColumn();
    var table = new BSTable(Order.id, "/order/list", defaultColunms);
    table.setPaginationType("client");
    Order.table = table.init();
});
