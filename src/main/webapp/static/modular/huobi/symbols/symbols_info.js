/**
 * 初始化单个symbol批量成交记录详情对话框
 */
var SymbolsInfoDlg = {
    symbolsInfoData : {}
};

/**
 * 清除数据
 */
SymbolsInfoDlg.clearData = function() {
    this.symbolsInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
SymbolsInfoDlg.set = function(key, val) {
    this.symbolsInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
SymbolsInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
SymbolsInfoDlg.close = function() {
    parent.layer.close(window.parent.Symbols.layerIndex);
}

/**
 * 收集数据
 */
SymbolsInfoDlg.collectData = function() {
    this
    .set('base-currency')
    .set('quote-currency')
    .set('price-precision')
    .set('amount-precision')
    .set('symbol-partition');
}

/**
 * 提交添加
 */
SymbolsInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/symbols/add", function(data){
        Feng.success("添加成功!");
        window.parent.Symbols.table.refresh();
        SymbolsInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.symbolsInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
SymbolsInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/symbols/update", function(data){
        Feng.success("修改成功!");
        window.parent.Symbols.table.refresh();
        SymbolsInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.symbolsInfoData);
    ajax.start();
}

$(function() {

});
