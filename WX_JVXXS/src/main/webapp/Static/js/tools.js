function changesDisabled() {
    $(this).attr("disabled", "");
}

function initTableCheckbox() {
    var $thr = $('table thead tr');
    // var $checkAllTh = $('<th><input type="checkbox" id="checkAll" name="checkAll" /></th>');
    var $checkAllTh = $thr.find("#checkAll").parent();
    /*将全选/反选复选框添加到表头最前，即增加一列*/
    // $thr.prepend($checkAllTh);
    /*“全选/反选”复选框*/
    var $checkAll = $thr.find('input');
    $checkAll.click(function (event) {
        /*将所有行的选中状态设成全选框的选中状态*/
        $tbr.find('input').prop('checked', $(this).prop('checked'));
        /*并调整所有选中行的CSS样式*/
        if ($(this).prop('checked')) {
            $tbr.find('input').parent().parent().addClass('warning');
        } else {
            $tbr.find('input').parent().parent().removeClass('warning');
        }
        /*阻止向上冒泡，以防再次触发点击操作*/
        event.stopPropagation();
    });
    /*点击全选框所在单元格时也触发全选框的点击操作*/
    $checkAllTh.click(function () {
        $(this).find('input').click();
    });
    var $tbr = $('table tbody tr');
    var $checkItemTd = $('<td class="checktd"><input  type="checkbox" name="checkProduct" /></td>');
    /*每一行都在最前面插入一个选中复选框的单元格*/
    $tbr.prepend($checkItemTd);
    /*点击每一行的选中复选框时*/
    $tbr.find('input').click(function (event) {
        /*调整选中行的CSS样式*/
        $(this).parent().parent().toggleClass('warning');
        /*如果已经被选中行的行数等于表格的数据行数，将全选框设为选中状态，否则设为未选中状态*/
        $checkAll.prop('checked', $tbr.find('input:checked').length == $tbr.length ? true : false);
        /*阻止向上冒泡，以防再次触发点击操作*/
        event.stopPropagation();
    });
    /*点击每一行时也触发该行的选中操作*/
    $tbr.find(".checktd").click(function () {
        $(this).find('input').click();
    });
}

function fix_amountthis(obj) {
    var fix_amountTest = /^(?:[1-9][0-9]*(?:\.[0-9]{1,2})?|0(?:\.[0-9]{1,2})?)$/;
    if (fix_amountTest.test(obj) == false) {
        return false;
    } else {
        return true;
    }
}

/**
 * 获取指定URL的参数值
 * @param url  指定的URL地址
 * @param name 参数名称
 * @return 参数值
 */
function getUrlParam(url, name) {
    var pattern = new RegExp("[?&]" + name + "\=([^&]+)", "g");
    var matcher = pattern.exec(url);
    var items = "";
    if (null != matcher) {
        try {
            items = decodeURIComponent(decodeURIComponent(matcher[1]));
        } catch (e) {
            try {
                items = decodeURIComponent(matcher[1]);
            } catch (e) {
                items = matcher[1];
            }
        }
    }
    return items;
}