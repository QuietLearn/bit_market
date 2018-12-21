/**
 * k线细化划分管理初始化
 */
var KlineDivide = {
    id: "KlineDivideTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
KlineDivide.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '', field: 'kdOpen', visible: true, align: 'center', valign: 'middle'},
            {title: '', field: 'kdClose', visible: true, align: 'center', valign: 'middle'},
            {title: '', field: 'kdLow', visible: true, align: 'center', valign: 'middle'},
            {title: '', field: 'kdHigh', visible: true, align: 'center', valign: 'middle'},
            {title: '', field: 'kdVol', visible: true, align: 'center', valign: 'middle'},
            {title: '', field: 'kdAmount', visible: true, align: 'center', valign: 'middle'},
            {title: '', field: 'kdCount', visible: true, align: 'center', valign: 'middle'},
            {title: '', field: 'kdSymbol', visible: true, align: 'center', valign: 'middle'},
            {title: '', field: 'kdTs', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
KlineDivide.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        KlineDivide.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加k线细化划分
 */
KlineDivide.openAddKlineDivide = function () {
    var index = layer.open({
        type: 2,
        title: '添加k线细化划分',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/klineDivide/klineDivide_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看k线细化划分详情
 */
KlineDivide.openKlineDivideDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: 'k线细化划分详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/klineDivide/klineDivide_update/' + KlineDivide.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除k线细化划分
 */
KlineDivide.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/klineDivide/delete", function (data) {
            Feng.success("删除成功!");
            KlineDivide.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("klineDivideId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询k线细化划分列表
 */
KlineDivide.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    KlineDivide.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = KlineDivide.initColumn();
    var table = new BSTable(KlineDivide.id, "/klineDivide/list", defaultColunms);
    table.setPaginationType("client");
    KlineDivide.table = table.init();
});
