/**
 * 单个symbol批量成交记录管理初始化
 */
var Merged = {
    id: "MergedTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Merged.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: 'K线id（时间戳）', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '收盘价,当K线为最晚的一根时，是最新成交价', field: 'close', visible: true, align: 'center', valign: 'middle'},
            {title: '开盘价', field: 'open', visible: true, align: 'center', valign: 'middle'},
            {title: '最高价', field: 'high', visible: true, align: 'center', valign: 'middle'},
            {title: '最低价', field: 'low', visible: true, align: 'center', valign: 'middle'},
            {title: '成交量', field: 'amount', visible: true, align: 'center', valign: 'middle'},
            {title: '成交笔数', field: 'count', visible: true, align: 'center', valign: 'middle'},
            {title: '成交额, 即 sum(每一笔成交价 * 该笔的成交量)', field: 'vol', visible: true, align: 'center', valign: 'middle'},
            {title: '[买1价,买1量]', field: 'bid', visible: true, align: 'center', valign: 'middle'},
            {title: '[卖1价,卖1量]', field: 'ask', visible: true, align: 'center', valign: 'middle'},
            {title: '响应生成时间点，单位：毫秒', field: 'ts', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Merged.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Merged.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加单个symbol批量成交记录
 */
Merged.openAddMerged = function () {
    var index = layer.open({
        type: 2,
        title: '添加单个symbol批量成交记录',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/merged/merged_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看单个symbol批量成交记录详情
 */
Merged.openMergedDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '单个symbol批量成交记录详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/merged/merged_update/' + Merged.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除单个symbol批量成交记录
 */
Merged.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/merged/delete", function (data) {
            Feng.success("删除成功!");
            Merged.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("mergedId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询单个symbol批量成交记录列表
 */
Merged.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Merged.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Merged.initColumn();
    var table = new BSTable(Merged.id, "/merged/list", defaultColunms);
    table.setPaginationType("client");
    Merged.table = table.init();
});
