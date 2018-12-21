/**
 * 初始化k线细化划分详情对话框
 */
var KlineDivideInfoDlg = {
    klineDivideInfoData : {}
};

/**
 * 清除数据
 */
KlineDivideInfoDlg.clearData = function() {
    this.klineDivideInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
KlineDivideInfoDlg.set = function(key, val) {
    this.klineDivideInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
KlineDivideInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
KlineDivideInfoDlg.close = function() {
    parent.layer.close(window.parent.KlineDivide.layerIndex);
}

/**
 * 收集数据
 */
KlineDivideInfoDlg.collectData = function() {
    this
    .set('id')
    .set('kdOpen')
    .set('kdClose')
    .set('kdLow')
    .set('kdHigh')
    .set('kdVol')
    .set('kdAmount')
    .set('kdCount')
    .set('kdSymbol')
    .set('kdTs');
}

/**
 * 提交添加
 */
KlineDivideInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/klineDivide/add", function(data){
        Feng.success("添加成功!");
        window.parent.KlineDivide.table.refresh();
        KlineDivideInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.klineDivideInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
KlineDivideInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/klineDivide/update", function(data){
        Feng.success("修改成功!");
        window.parent.KlineDivide.table.refresh();
        KlineDivideInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.klineDivideInfoData);
    ajax.start();
}

$(function() {

});
