/**
 * 初始化单个symbol批量成交记录详情对话框
 */
var HistoryTradeDetailInfoDlg = {
    historyTradeDetailInfoData : {}
};

/**
 * 清除数据
 */
HistoryTradeDetailInfoDlg.clearData = function() {
    this.historyTradeDetailInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
HistoryTradeDetailInfoDlg.set = function(key, val) {
    this.historyTradeDetailInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
HistoryTradeDetailInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
HistoryTradeDetailInfoDlg.close = function() {
    parent.layer.close(window.parent.HistoryTradeDetail.layerIndex);
}

/**
 * 收集数据
 */
HistoryTradeDetailInfoDlg.collectData = function() {
    this
    .set('id')
    .set('bargainId')
    .set('price')
    .set('amount')
    .set('direction')
    .set('ts');
}

/**
 * 提交添加
 */
HistoryTradeDetailInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/historyTradeDetail/add", function(data){
        Feng.success("添加成功!");
        window.parent.HistoryTradeDetail.table.refresh();
        HistoryTradeDetailInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.historyTradeDetailInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
HistoryTradeDetailInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/historyTradeDetail/update", function(data){
        Feng.success("修改成功!");
        window.parent.HistoryTradeDetail.table.refresh();
        HistoryTradeDetailInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.historyTradeDetailInfoData);
    ajax.start();
}

$(function() {

});
