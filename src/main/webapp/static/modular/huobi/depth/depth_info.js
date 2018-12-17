/**
 * 初始化市场深度行情& #40;单symbol& #41;详情对话框
 */
var DepthInfoDlg = {
    depthInfoData : {}
};

/**
 * 清除数据
 */
DepthInfoDlg.clearData = function() {
    this.depthInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
DepthInfoDlg.set = function(key, val) {
    this.depthInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
DepthInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
DepthInfoDlg.close = function() {
    parent.layer.close(window.parent.Depth.layerIndex);
}

/**
 * 收集数据
 */
DepthInfoDlg.collectData = function() {
    this
    .set('id')
    .set('bids')
    .set('asks')
    .set('ts');
}

/**
 * 提交添加
 */
DepthInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/depth/add", function(data){
        Feng.success("添加成功!");
        window.parent.Depth.table.refresh();
        DepthInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.depthInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
DepthInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/depth/update", function(data){
        Feng.success("修改成功!");
        window.parent.Depth.table.refresh();
        DepthInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.depthInfoData);
    ajax.start();
}

$(function() {

});
