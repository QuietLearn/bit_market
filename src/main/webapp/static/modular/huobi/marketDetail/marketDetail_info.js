/**
 * 初始化单个symbol批量成交记录详情对话框
 */
var MarketDetailInfoDlg = {
    marketDetailInfoData : {}
};

/**
 * 清除数据
 */
MarketDetailInfoDlg.clearData = function() {
    this.marketDetailInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MarketDetailInfoDlg.set = function(key, val) {
    this.marketDetailInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MarketDetailInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
MarketDetailInfoDlg.close = function() {
    parent.layer.close(window.parent.MarketDetail.layerIndex);
}

/**
 * 收集数据
 */
MarketDetailInfoDlg.collectData = function() {
    this
    .set('id')
    .set('amount')
    .set('count')
    .set('open')
    .set('close')
    .set('low')
    .set('high')
    .set('vol')
    .set('ts');
}

/**
 * 提交添加
 */
MarketDetailInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/marketDetail/add", function(data){
        Feng.success("添加成功!");
        window.parent.MarketDetail.table.refresh();
        MarketDetailInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.marketDetailInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
MarketDetailInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/marketDetail/update", function(data){
        Feng.success("修改成功!");
        window.parent.MarketDetail.table.refresh();
        MarketDetailInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.marketDetailInfoData);
    ajax.start();
}

$(function() {

});
