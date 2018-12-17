/**
 * 初始化单个symbol批量成交记录详情对话框
 */
var TickersInfoDlg = {
    tickersInfoData : {}
};

/**
 * 清除数据
 */
TickersInfoDlg.clearData = function() {
    this.tickersInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TickersInfoDlg.set = function(key, val) {
    this.tickersInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TickersInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
TickersInfoDlg.close = function() {
    parent.layer.close(window.parent.Tickers.layerIndex);
}

/**
 * 收集数据
 */
TickersInfoDlg.collectData = function() {
    this
    .set('close')
    .set('open')
    .set('high')
    .set('low')
    .set('amount')
    .set('count')
    .set('vol')
    .set('symbol')
    .set('ts');
}

/**
 * 提交添加
 */
TickersInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/tickers/add", function(data){
        Feng.success("添加成功!");
        window.parent.Tickers.table.refresh();
        TickersInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.tickersInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
TickersInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/tickers/update", function(data){
        Feng.success("修改成功!");
        window.parent.Tickers.table.refresh();
        TickersInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.tickersInfoData);
    ajax.start();
}

$(function() {

});
