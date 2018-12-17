/**
 * 市场深度行情& #40;单symbol& #41;管理初始化
 */
var Depth = {
    id: "DepthTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Depth.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '消息id', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '买盘,[price(成交价), amount(成交量)], 按price降序', field: 'bids', visible: true, align: 'center', valign: 'middle'},
            {title: '卖盘,[price(成交价), amount(成交量)], 按price升序', field: 'asks', visible: true, align: 'center', valign: 'middle'},
            {title: '消息生成时间，单位：毫秒', field: 'ts', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Depth.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Depth.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加市场深度行情& #40;单symbol& #41;
 */
Depth.openAddDepth = function () {
    var index = layer.open({
        type: 2,
        title: '添加市场深度行情& #40;单symbol& #41;',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/depth/depth_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看市场深度行情& #40;单symbol& #41;详情
 */
Depth.openDepthDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '市场深度行情& #40;单symbol& #41;详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/depth/depth_update/' + Depth.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除市场深度行情& #40;单symbol& #41;
 */
Depth.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/depth/delete", function (data) {
            Feng.success("删除成功!");
            Depth.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("depthId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询市场深度行情& #40;单symbol& #41;列表
 */
Depth.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Depth.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Depth.initColumn();
    var table = new BSTable(Depth.id, "/depth/list", defaultColunms);
    table.setPaginationType("client");
    Depth.table = table.init();
});
