/**
 * 初始化单个symbol批量成交记录详情对话框
 */
var TradeDetailInfoDlg = {
    tradeDetailInfoData : {}
};

/**
 * 清除数据
 */
TradeDetailInfoDlg.clearData = function() {
    this.tradeDetailInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TradeDetailInfoDlg.set = function(key, val) {
    this.tradeDetailInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TradeDetailInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
TradeDetailInfoDlg.close = function() {
    parent.layer.close(window.parent.TradeDetail.layerIndex);
}

/**
 * 收集数据
 */
TradeDetailInfoDlg.collectData = function() {
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
TradeDetailInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/tradeDetail/add", function(data){
        Feng.success("添加成功!");
        window.parent.TradeDetail.table.refresh();
        TradeDetailInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.tradeDetailInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
TradeDetailInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/tradeDetail/update", function(data){
        Feng.success("修改成功!");
        window.parent.TradeDetail.table.refresh();
        TradeDetailInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.tradeDetailInfoData);
    ajax.start();
}

$(function() {

});
