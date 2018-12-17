/**
 * 初始化单个symbol批量成交记录详情对话框
 */
var MergedInfoDlg = {
    mergedInfoData : {}
};

/**
 * 清除数据
 */
MergedInfoDlg.clearData = function() {
    this.mergedInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MergedInfoDlg.set = function(key, val) {
    this.mergedInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MergedInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
MergedInfoDlg.close = function() {
    parent.layer.close(window.parent.Merged.layerIndex);
}

/**
 * 收集数据
 */
MergedInfoDlg.collectData = function() {
    this
    .set('id')
    .set('close')
    .set('open')
    .set('high')
    .set('low')
    .set('amount')
    .set('count')
    .set('vol')
    .set('bid')
    .set('ask')
    .set('ts');
}

/**
 * 提交添加
 */
MergedInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/merged/add", function(data){
        Feng.success("添加成功!");
        window.parent.Merged.table.refresh();
        MergedInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.mergedInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
MergedInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/merged/update", function(data){
        Feng.success("修改成功!");
        window.parent.Merged.table.refresh();
        MergedInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.mergedInfoData);
    ajax.start();
}

$(function() {

});
