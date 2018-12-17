/**
 * 单个symbol批量成交记录管理初始化
 */
var Symbols = {
    id: "SymbolsTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Symbols.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '基础币种', field: 'base-currency', visible: true, align: 'center', valign: 'middle'},
            {title: '计价币种', field: 'quote-currency', visible: true, align: 'center', valign: 'middle'},
            {title: '价格精度位数（0为个位，ethbtc, etcbtc, bchbtc, ltcbtc 除外，这四个交易对的精度固定为4位 ）', field: 'price-precision', visible: true, align: 'center', valign: 'middle'},
            {title: '数量精度位数（0为个位，指 base-currency 数量）', field: 'amount-precision', visible: true, align: 'center', valign: 'middle'},
            {title: '交易区(main主区，innovation创新区，bifurcation分叉区)', field: 'symbol-partition', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Symbols.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Symbols.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加单个symbol批量成交记录
 */
Symbols.openAddSymbols = function () {
    var index = layer.open({
        type: 2,
        title: '添加单个symbol批量成交记录',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/symbols/symbols_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看单个symbol批量成交记录详情
 */
Symbols.openSymbolsDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '单个symbol批量成交记录详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/symbols/symbols_update/' + Symbols.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除单个symbol批量成交记录
 */
Symbols.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/symbols/delete", function (data) {
            Feng.success("删除成功!");
            Symbols.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("symbolsId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询单个symbol批量成交记录列表
 */
Symbols.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Symbols.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Symbols.initColumn();
    var table = new BSTable(Symbols.id, "/symbols/list", defaultColunms);
    table.setPaginationType("client");
    Symbols.table = table.init();
});
