/**
 * 初始化单个symbol批量成交记录详情对话框
 */
var KlineInfoDlg = {
    klineInfoData : {}
};

/**
 * 清除数据
 */
KlineInfoDlg.clearData = function() {
    this.klineInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
KlineInfoDlg.set = function(key, val) {
    this.klineInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
KlineInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
KlineInfoDlg.close = function() {
    parent.layer.close(window.parent.Kline.layerIndex);
}

/**
 * 收集数据
 */
KlineInfoDlg.collectData = function() {
    this
    .set('id')
    .set('amount')
    .set('count')
    .set('open')
    .set('close')
    .set('low')
    .set('high')
    .set('vol');
}

/**
 * 提交添加
 */
KlineInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/kline/add", function(data){
        Feng.success("添加成功!");
        window.parent.Kline.table.refresh();
        KlineInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.klineInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
KlineInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/kline/update", function(data){
        Feng.success("修改成功!");
        window.parent.Kline.table.refresh();
        KlineInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.klineInfoData);
    ajax.start();
}

$(function() {

});
