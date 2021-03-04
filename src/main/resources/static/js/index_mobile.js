var recipelItemsname = [];
var thisname;
var storage = window.localStorage;
var day = "";
//声明一个全局变量来保存键盘类型
var pckeyboardType = "undefined"
//声明一个来保存位置ID
var lableid = 0;

window.addEventListener('click', function (event) {

    if ($(".zhen").css("display") == "block") {
        $(".symptom").text($(".symptom").text() + day);
    }
    $(".zhen").hide();
    $("#doctorlistPage").animate({width: "0",}, 500);
    var ckuser = getCookie('ybuser');
    if (pdlocal() || ckuser == null || ckuser == '') {
        layer.msg('身份信息过期，请重新登录')
        top.location.href = contextPath + "/logout";
        return;
    }
    var display = $('#ageboard').css('display')
    if (display == "block" || $('#jsboard').css('display') == "block" || $('#yfboard').css('display') == "block" || $('#days').css('display') == "block") {
        $("#ageboard").hide();
        $('#jsboard').hide();
        $('#yfboard').hide();
        $('#days').hide();
    }
    var elm = document.getElementById("pop");
    if (elm) {
        //默认剂量键盘末尾加“g”
        if ($(thiskey).parent().attr("onclick") == "pop(this,event,'jl')") {
            if ($(thiskey).text().length > 0) {
                var s = $(thiskey).text().charAt($(thiskey).text().length - 1);
                if (s != "g") {
                    var materialsjson = JSON.parse(storage.getItem("dosages"));
                    var bool = true
                    for (var i = 0; i < materialsjson.length; i++) {
                        if (materialsjson[i].name == $(thisinput).val()) {
                            materialsjson[i].freq += 1;
                            bool = false;
                            break;
                        }
                    }
                    if (bool) {
                        var basedata = new Object;
                        basedata.id = "";
                        basedata.name = $(thisinput).val();
                        basedata.pinyin = "";
                        basedata.base_id = "";
                        basedata.user_id = getCookie('ybuser')
                        basedata.freq = 0;
                        basedata.ctype = "jl";
                        basedata.gmt_create = "";
                        basedata.max_dosage = "";
                        basedata.min_dosage = "";
                        materialsjson.push(basedata);
                    }
                    storage.setItem("dosages", JSON.stringify(materialsjson));
                    $(thiskey).text($(thisinput).val() + "g");
                    $(thisinput).val($(thisinput).val() + "g");
                }
            }
        }
        document.body.removeChild(elm);
    }
    var evt = event || window.event;
    if (evt.stopPropagation) {
        evt.stopPropagation();
    } else {
        evt.cancelBubble = true;
    }
    // evt.preventDefault();
});

function guanbijp() {
    if ($(".zhen").css("display") == "block") {
        $(".symptom").text($(".symptom").text() + day);
    }
    $(".zhen").hide();
    $("#doctorlistPage").animate({width: "0",}, 500);
    var ckuser = getCookie('ybuser');
    if (pdlocal() || ckuser == null || ckuser == '') {
        layer.msg('身份信息过期，请重新登录')
        top.location.href = contextPath + "/logout";
        return;
    }
    var display = $('#ageboard').css('display')
    if (display == "block" || $('#jsboard').css('display') == "block" || $('#yfboard').css('display') == "block" || $('#days').css('display') == "block") {
        $("#ageboard").hide();
        $('#jsboard').hide();
        $('#yfboard').hide();
        $('#days').hide();
    }
    var elm = document.getElementById("pop");
    if (elm) {
        //默认剂量键盘末尾加“g”
        if ($(thiskey).parent().attr("onclick") == "pop(this,event,'jl')") {
            if ($(thiskey).text().length > 0) {
                var s = $(thiskey).text().charAt($(thiskey).text().length - 1);
                if (s != "g") {
                    var materialsjson = JSON.parse(storage.getItem("dosages"));
                    var bool = true
                    for (var i = 0; i < materialsjson.length; i++) {
                        if (materialsjson[i].name == $(thisinput).val()) {
                            materialsjson[i].freq += 1;
                            bool = false;
                            break;
                        }
                    }
                    if (bool) {
                        var basedata = new Object;
                        basedata.id = "";
                        basedata.name = $(thisinput).val();
                        basedata.pinyin = "";
                        basedata.base_id = "";
                        basedata.user_id = getCookie('ybuser')
                        basedata.freq = 0;
                        basedata.ctype = "jl";
                        basedata.gmt_create = "";
                        basedata.max_dosage = "";
                        basedata.min_dosage = "";
                        materialsjson.push(basedata);
                    }
                    storage.setItem("dosages", JSON.stringify(materialsjson));
                    $(thiskey).text($(thisinput).val() + "g");
                    $(thisinput).val($(thisinput).val() + "g");
                }
            }
        }
        document.body.removeChild(elm);
    }
}

$("#doctorlistPage").on('click', function (event) {
    var evt = event || window.event;
    if (evt.stopPropagation) {
        evt.stopPropagation();
    } else {
        evt.cancelBubble = true;
    }
    evt.preventDefault();
})

/**
 * 获取当前输入框中的所有药品的名称
 */
function getCurrentMaterialList() {
    var currentMaterialList = [];
    // console.log($(thisinput).attr("name"))
    $(".material_hidden").each(function (key, value) {
        if ($(thisinput).attr("name") == $(this).attr("name")) {
            // console.log('本框')
        } else {
            currentMaterialList.push($(this).val());
        }
    });
    return currentMaterialList;
}

/*load加载层*/
function loadmsg() {
    layer.load(2, {shade: [0.1, "#000"], shadeClose: true, scrollbar: false});

}

function closeload() {
    layer.closeAll('loading');
}

/**
 * 判断数组内容是否有重复
 */
function arrRepeat(arr) {
    var repeatStr = null;
    var nary = arr.sort();
    for (var i = 0; i < arr.length; i++) {
        if (arr[i] == "") continue
        if (nary[i] == nary[i + 1]) {
            repeatStr = nary[i];
        }
    }
    return repeatStr;
}

var ifsavebaserecipel = true;

function savebaserecipel() {
    var ybuser = getCookie('ybuser');
    if (ybuser == null || ybuser == "" || ybuser == undefined) {
        /*先判断是不是登录状态如果不是先登录*/
        var evt = event || window.event;
        if (evt.stopPropagation) {
            evt.stopPropagation();
        } else {
            evt.cancelBubble = true;
        }
        evt.preventDefault();
        layer.open({
            type: 2,
            title: false,
            shade: 0.6,
            id: 'loginFormParent',
            shadeClose: false,
            area: ['90%', '90%'],
            content: 'd6/login' //iframe的url
        });
    } else {
        layer.prompt({
            title: '请输入处方名字,不能为空',
            formType: 2
        }, function (pass, index) {
            if (pass.replace(/ /g, '').length == 0 || pass == "") {
                layer.msg('请输入处方名字')
            } else {
                $('.disablerecipelitem').attr("disabled", true);
                pass = pass.replace(/[\r\n]/g, "");
                $("#recipelname").val(pass.replace(/ /g, ''))
                $("#emrform").attr("action", contextPath + "/savebaserecipel");
                $("#emrform").ajaxSubmit(function (e) {
                    if (e == "处方名已有") {
                        layer.msg(pass + "名称已有，请重新命名");
                        $('.disablerecipelitem').attr("disabled", false);
                        var evt = event || window.event;
                        if (evt.stopPropagation) {
                            evt.stopPropagation();
                        } else {
                            evt.cancelBubble = true;
                        }
                        evt.preventDefault();
                        return
                    } else {
                        var strs = e.split(",")
                        // console.log(strs[0] + "," + strs[1] + "," + strs[2] + "," + strs[3]);
                        var materialsjson = JSON.parse(storage.getItem("materials"));
                        var basedata = new Object;
                        basedata.id = "";
                        basedata.name = strs[1];
                        basedata.pinyin = strs[3];
                        basedata.base_id = strs[2];
                        basedata.user_id = getCookie('ybuser');
                        basedata.freq = 0;
                        basedata.ctype = "yp";
                        basedata.gmt_create = "";
                        basedata.max_dosage = "";
                        basedata.min_dosage = "";
                        // console.log(basedata)
                        materialsjson.push(basedata);
                        storage.setItem("materials", JSON.stringify(materialsjson));
                        layer.msg(strs[0]);
                        searchNextPage(1);
                        layer.close(index);
                    }
                });
                $('.disablerecipelitem').attr("disabled", false);

            }

        });
    }

}


function findEmrHistoryByName(date, type) {

    $("#histroy-list").load(contextPath + "/emr/findlistpageemr?datestring=" + date, function (response, status, xhr) {
        if (status == "success") {
            if (response == "") {
            } else {
                $("#histroy-list").html(response)
                if (type == 'index') {
                    $("#rightToolsTab a:last").tab('show');
                }
            }
        }
    });
}

// 预约
function findwaitByName(name, date, type) {

    $("#wait-list").load(contextPath + "/emr/waitlist?patient.name=" + name + "&kssj=" + date, function (response, status, xhr) {
        if (status == "success") {
            if (response == "") {
            } else {
                $("#wait-list").html(response)
                if (type == 'index') {
                    $("#rightToolsTab a:last").tab('show');
                }
            }
        }
    });
}

/**
 * 模糊查询医生
 * @param name
 * @param specialty
 * @param type
 */
function findUserByName(name, specialty, type) {

    $("#doctor-list").load(contextPath + "/user/listPageDoctorModal?name=" + name + "&specialty=" + specialty, function (response, status, xhr) {
        if (status == "success") {
            if (response == "") {
            } else {
                $("#doctor-list").html(response)
                if (type == 'index') {
                    $("#rightToolsTab a:last").tab('show');
                }
            }
        }
    });
}

/**
 * 初始加载处方模板
 */
function searchNextPage(currentPage) {
    var url = contextPath + "/listPageBaseRecipels";
    postdata = {'page.currentPage': currentPage};
    $.post(url, postdata, function (data) {
        $("#baseRecipelHolder").html(data);
    });
}

/**
 * 切换到开处方tab时，要重新加载处方模板，因为两个地方都有分页，他们的URL会冲突
 */
function writeRecipel() {
    $(".col-md-3 .tab-content").hide()
    $("#emrhistoryPage").animate({width: "0"}, 500);
    var url = contextPath + "/listPageBaseRecipels";
    postdata = {'page.currentPage': 1};
    $.post(url, postdata, function (data) {
        $("#baseRecipelHolder").html(data);
    });
}

/**
 * 预约列表回显
 */
function reservationData(visitNo) {
    $(".col-md-3 .tab-content").hide()
    var url = contextPath + "/emr/diagnosis";
    postdata = {'visitNo': visitNo};
    $.post(url, postdata, function (data) {
        console.log(data)
        $("#visitNo").val(data.visitNo);
        $("#ghks").val(data.ghks);
        $("#telephone").val(data.patient.telephone);
        $("#patientName").val(data.patient.name);
        $("#patientAge").val(data.patient.age+"岁");
        if (data.patient.sex == '女') {
            $("#optionsRadios26").attr("checked", "true");
            $(".menstruation").css("display", "inline-block");
            $("#menstruation").val(data.registInfo.menstruationHistory);
        }

        $("#chiefComplaint").val(data.registInfo.chiefComplaint);
        $("#presentillness").val(data.registInfo.presentIllness);
        $("#pastillness").val(data.registInfo.passedIllness);
        $("#allergicHistory").val(data.registInfo.allergicHistory);
        $("#personalIllness").val(data.registInfo.personalIllness);

        $("textarea[id='disease']").text(data.registInfo.symptom);
    });
}


function pop(th, event, keyboardType) {
    pckeyboardType = keyboardType;
    if (pdlocal()) {
        layer.msg('身份信息过期，请重新登录')
        top.location.href = contextPath + "/logout";
        return;
    }
    var display = $('#ageboard').css('display');
    if (display == "block" || $('#jsboard').css('display') == "block") {
        $("#ageboard").hide();
        $("#jsboard").hide();
    }
    var elm = document.getElementById("pop");
    if (elm) {
        //默认剂量键盘末尾加“g”
        if ($(thiskey).parent().attr("onclick") == "pop(this,event,'jl')") {
            if ($(thiskey).text().length > 0) {
                $(thiskey).text();
                var s = $(thiskey).text().charAt($(thiskey).text().length - 1);
                if (s != "g") {
                    var materialsjson = JSON.parse(storage.getItem("dosages"));
                    var bool = true
                    for (var i = 0; i < materialsjson.length; i++) {
                        if (materialsjson[i].name == $(thisinput).val()) {
                            materialsjson[i].freq += 1;
                            bool = false;
                            break;
                        }
                    }
                    if (bool) {
                        var basedata = new Object;
                        basedata.id = "";
                        basedata.name = $(thisinput).val();
                        basedata.pinyin = "";
                        basedata.base_id = "";
                        basedata.user_id = getCookie('ybuser');
                        basedata.freq = 0;
                        basedata.ctype = "jl";
                        basedata.gmt_create = "";
                        basedata.max_dosage = "";
                        basedata.min_dosage = "";
                        materialsjson.push(basedata);
                    }
                    storage.setItem("dosages", JSON.stringify(materialsjson));
                    $(thiskey).text($(thisinput).val() + "g");
                    $(thisinput).val($(thisinput).val() + "g");
                }
            }
        }
        document.body.removeChild(elm);
    }
    thiskey = $(th).find("lable");
    thisinput = $(th).find("input");
    thisname = $(th).find("lable").html().trim();
    var allmaterialdiv = $(document).find(".div-box");
    var divsize = allmaterialdiv.length;
    for (var i = 0; i < divsize; i++) {
        if (allmaterialdiv.eq(i)[0] == th) {
            lableid = i;
        }
    }
    var evt = event || window.event;
    if (evt.stopPropagation) {
        evt.stopPropagation();
    } else {
        evt.cancelBubble = true;
    }
    var rect = th.getBoundingClientRect();
    var basew = rect.right - rect.left;
    var baseh = rect.bottom - rect.top;
    //先得到dom元素的位置。
    //得到浏览器的客户区宽高
    var docw = document.documentElement.clientWidth;
    var doch = document.documentElement.clientHeight;
    //判断dom元素的左边空间大还是右边空间大
    var isleft = docw - rect.left < rect.right ? true : false;
    //判断dom元素的上边空间大还是下面空间大
    var istop = doch - rect.bottom < rect.top ? true : false;
    //得到滚动条的位置。
    var scrollTop = document.documentElement.scrollTop || window.pageYOffset || document.body.scrollTop;
    var scrollLeft = document.documentElement.scrollLeft || window.pageXOffset || document.body.scrollLeft;
    var scrollw = Math.max(document.documentElement.scrollWidth, document.body.scrollWidth);
    //将弹窗显示在左上角位置
    var div = document.createElement("div");
    div.style.cssText = "position:absolute;z-Index:20;background:#fff;border:2px solid #A0C2DF;left:0;top:0;box-sizing:border-box;border-radius:10px";
    div.id = "pop";
    if (keyboardType == "jl") {
        div.innerHTML = document.getElementById("jlTipWin").innerHTML;
    } else if (keyboardType == "yf") {
        div.innerHTML = document.getElementById("yfTipWin").innerHTML;
    } else if (keyboardType == "zz") {
        div.innerHTML = document.getElementById("zzTipWin").innerHTML;
    } else {
        div.innerHTML = document.getElementById("tipwin").innerHTML;
    }
    document.body.appendChild(div);

    div.addEventListener('click', function (event) {
        var evt = event || window.event;
        if (evt.stopPropagation) {
            evt.stopPropagation();
        } else {
            evt.cancelBubble = true;
        }
        evt.preventDefault();
    });
    //得到div的宽高
    var drect = div.getBoundingClientRect();
    var dw = drect.right - drect.left;
    var dh = drect.bottom - drect.top;
    //首先考虑div左边对齐，显示的input下方，
    //先判断div跟input是左对齐还是右对齐？
    //大于右边的宽度，并且左边空间比右边大
    var loc_isright = (dw > (docw - rect.left) && isleft) ? true : false;
    var left = (dw > (docw - rect.left) && isleft) ? rect.right : rect.left;
    //判断div是否最大宽度。
    var ismax = (dw > docw - rect.left) && dw > rect.right ? true : false;
    var max_w;
    //如果需要最大宽度
    // if (ismax) {
    //     max_w = (docw - rect.left) > (rect.right) ? (docw - rect.left) : rect.right;
    // }
    var ismin = dw < basew;
    // if (ismin) {
    //     max_w = basew;
    // }
    //判断上下的位置
    var top = keyboardType == 'jl' ? ((dh > doch - rect.bottom) && istop ? (rect.top > dh ? rect.top - dh - 24 : 0) : rect.bottom) : ((dh > doch - rect.bottom) && istop ? (rect.top > dh ? rect.top - dh : 0) : rect.bottom);

    var ismax_h = dh > doch - rect.bottom && dh > rect.top ? true : false;

    var max_h;
    //最大高度
    if (ismax_h) {
        max_h = doch - rect.bottom > rect.top ? (doch - rect.bottom) : rect.top;
    }
    // if (loc_isright) {
    //     div.style.left = "";
    //     // div.style.right = (scrollw - (left + scrollLeft)) + "px";
    // } else {
    //     div.style.left = (left + scrollLeft) + "px";
    // }
    div.style.top = (top + scrollTop) + "px";
    if (ismax || ismin) {
        div.style.width = max_w + "px";
        if (ismax) {
            div.style.overflowX = "auto";
        }
    }
    if (ismax_h) {
        div.style.height = max_h + "px";
        div.style.overflowY = "auto";
    }
    keypre(null, keyboardType);
}

//监听事件，待重构
$(document).keydown(function (event) {

    var materialdiv = document.getElementById("ypdiv");
    var yfdiv = document.getElementById("yfdiv");
    var jldiv = document.getElementById("jldiv");
    if (materialdiv != null || yfdiv != null || jldiv != null) {
        var keyCode = event.keyCode;
        var key = event.key;
        switch (true) {
            case keyCode == 13:
                var thisdom = $(document).find(".div-box").eq(lableid + 1)[0]
                if (thisdom != null) {
                    thisdom.click();
                } else {
                    // console.log('没有节点了')
                }
                break;
            case keyCode >= 65 && keyCode <= 90 && jldiv == null:
                var letters = $(".letter")
                for (var i = 0; i < letters.length; i++) {
                    if (letters.eq(i).html().toUpperCase() == key.toUpperCase()) {
                        keypre(letters.eq(i), pckeyboardType)
                    }
                }
                break;
            case keyCode == 8:
                keypre($(".delete.lastitem").eq(0), pckeyboardType)
                break;
            case jldiv != null && ((event.keyCode >= 48 && event.keyCode <= 57) || (event.keyCode >= 96 && event.keyCode <= 105)):
                var letters = $(".letter")
                for (var i = 0; i < letters.length; i++) {
                    if (letters.eq(i).html() == key) {
                        keypre(letters.eq(i), pckeyboardType);
                    }
                }
                break;
            case (keyCode == 190 || keyCode == 187) && jldiv == null:
                var next = $(".glyphicon.glyphicon-menu-right").eq(0);
                if (next != null) {
                    next.click()
                }
                break;
            case (keyCode == 188 || keyCode == 189) && jldiv == null:
                var last = $(".glyphicon.glyphicon-menu-left").eq(0);
                if (last != null) {
                    last.click()
                }
                break;
            case    jldiv == null && ((keyCode >= 48 && keyCode <= 57) || (keyCode >= 96 && keyCode <= 105)):
                var displaytds = $("#pop").find("td");
                var tddom = null;
                for (var i = 0; i < displaytds.length; i++) {
                    if (key == displaytds.eq(i).html().charAt(0)) {
                        tddom = displaytds.eq(i)
                    }
                }
                if (materialdiv != null && tddom != null) {
                    var materi = $(tddom).text().substring(2, $(tddom).text().length);
                    // 判断重复的药品
                    var mList = getCurrentMaterialList();
                    for (var i = 0; i < mList.length; i++) {
                        if (mList[i] == materi) {
                            layer.msg(materi + "已有")
                            return
                        }
                    }
                    material(tddom, undefined)
                    var iscf = $(tddom).find("img").html()
                    if (iscf == null) {
                        var thisdom = $(document).find(".div-box").eq(lableid + 1)[0]
                        pop(thisdom, event, 'yf')
                    } else {
                        var civlength = ""
                        setTimeout(function () {
                            alldiv = $(document).find(".div-box");
                            for (var i = 0; i < alldiv.length; i++) {
                                if (i % 3 == 0 && alldiv.eq(i).find("lable").html() == "") {
                                    civlength = i;
                                    break;
                                }
                            }
                            // console.log(civlength)
                            $(document).find(".div-box").eq(civlength)[0].click();
                        }, 5 * 100);

                    }
                } else if (yfdiv != null && tddom != null) {
                    material(tddom, 'yf')
                    var thisdom = $(document).find(".div-box").eq(lableid + 1)[0]
                    pop(thisdom, event, 'jl')
                }
                break;
            case keyCode == 32 && jldiv == null:
                var tddom = $("#pop").find("td").eq(0);
                if (materialdiv != null && tddom != null) {
                    var materi = $(tddom).text().substring(2, $(tddom).text().length);
                    // 判断重复的药品
                    var mList = getCurrentMaterialList();
                    for (var i = 0; i < mList.length; i++) {
                        if (mList[i] == materi) {
                            layer.msg(materi + "已有")
                            var evt = event || window.event;
                            if (evt.stopPropagation) {
                                evt.stopPropagation();
                            } else {
                                evt.cancelBubble = true;
                            }
                            evt.preventDefault();
                            return
                        }
                    }
                    material(tddom, undefined)
                    var iscf = $(tddom).find("img").html()
                    if (iscf == null) {
                        var thisdom = $(document).find(".div-box").eq(lableid + 1)[0]
                        pop(thisdom, event, 'yf')
                    } else {
                        var civlength = ""
                        setTimeout(function () {
                            alldiv = $(document).find(".div-box");
                            for (var i = 0; i < alldiv.length; i++) {
                                if (i % 3 == 0 && alldiv.eq(i).find("lable").html() == "") {
                                    civlength = i;
                                    break;
                                }
                            }
                            // console.log(civlength)
                            $(document).find(".div-box").eq(civlength)[0].click();
                        }, 5 * 100);

                    }
                } else if (yfdiv != null && tddom != null) {
                    material(tddom, 'yf')
                    var thisdom = $(document).find(".div-box").eq(lableid + 1)[0]
                    pop(thisdom, event, 'jl')
                }
                var evt = event || window.event;
                if (evt.stopPropagation) {
                    evt.stopPropagation();
                } else {
                    evt.cancelBubble = true;
                }
                evt.preventDefault();
                break;
            case keyCode == 32 && jldiv != null:
                var evt = event || window.event;
                if (evt.stopPropagation) {
                    evt.stopPropagation();
                } else {
                    evt.cancelBubble = true;
                }
                evt.preventDefault();
                break;

            default:
                break;
        }

    }

});

var thiskey;
var thisinput;

function searchmaterrial(obj) {
    var url = contextPath + "/searchmaterialtable";
    var parms = {
        q: $(obj).find("lable").html()
    };
    $.get(url, parms, function (data) {
        $("#material").html(data)
    });
}

/*历史病人*/
function historypeople() {
    $(".col-md-3 .tab-content").show()
    $("#histroy-list").load(contextPath + "/emr/findlistpageemr?patientName=&datestring=");
}

/*待诊病人*/
function waitlist() {
    $(".col-md-3 .tab-content").show();
    $("#wait-list").load(contextPath + "/emr/waitlist");
}

/*远程协助列表*/
function doctorList() {
    $("#doctor-list").load(contextPath + "/user/doctorList");
    var item = storage.getItem("users");
    if (item != null) {
        var obj2 = eval("(" + item + ")");
        var users = obj2
    }
    $("#appointmentDoc").empty()
    for (i = 0; i < users.length; i++) {
        $("#appointmentDoc").append("<span>" + users[i].name + "</span>")
    }
}


/**
 * 重新加载页面
 */
function reloadPage() {
    layer.confirm('您确定要重开一个新处方笺吗？<br/>点击【确定】它会清除当前页面数据！新开处方', {
        btn: ['确定', '取消'] //按钮
    }, function () {
        layer.closeAll('dialog');
        window.location.href = contextPath + "/recipelIndex";
    });
}

//保存后刷新
$(".share-all .close").click(function () {
    window.location.href = contextPath + "/recipelIndex";
})

//获取药品拼音
function query(str) {
    if (str == "") return;
    var arrRslt = makePy(str);
    return arrRslt;
}

function keypre(obj, keyboardType) { //event
    var $write = $(thiskey);
    var $this = $(obj);
    var character = "";
    if (obj != null) {    // 默认弹出就加载，没有点击
        character = $this.html();
        if ($this.hasClass('delete')) {
            if ($this.siblings("li").attr("onclick") == "keypre(this,'jl')") {
                var html = $write.html();
                $write.html(html.substr(0, html.length - 1));
                //console.log("1："+ $write.html())
                thisname = $write.html();
                $(thisinput).val($write.html());
                var parms = {
                    q: $write.html()
                };

            } else if (keyboardType == "yf") {
                /*删除时用法查询*/
                var html = $write.html();
                $write.html(html.substr(0, html.length - 1));
                thisname = $write.html();
                $(thisinput).val($write.html());
                var q = $write.html();
                var materialsjson = JSON.parse(storage.getItem("usages"));
                if (q != "") {
                    $("#material table tr td").remove();
                    var materiallength = Enumerable.from(materialsjson).where("x=>x.pinyin.toLowerCase().indexOf('" + q + "')!=-1 ||x.name.indexOf('" + q + "')!=-1 ").orderByDescending("x=>x.freq").toArray();
                    var materialname = Enumerable.from(materialsjson).where("x=>x.pinyin.toLowerCase().indexOf('" + q + "')!=-1 ||x.name.indexOf('" + q + "')!=-1 ").orderByDescending("x=>x.freq").take(5 * 1).skip(5 * 0).toArray();
                } else {
                    $("#material table tr td").remove();
                    var materiallength = Enumerable.from(materialsjson).orderByDescending("x=>x.freq").toArray();
                    var materialname = Enumerable.from(materialsjson).orderByDescending("x=>x.freq").take(5 * 1).skip(5 * 0).toArray();
                }

                if (materialname.length == 0) {

                    $("#material table tr ").append("<td>当前字母暂无匹配药品</td>")
                } else {
                    for (var q = 0; q < materialname.length; q++) {
                        $("#material table tr").append("<td onclick='material(this,\"yf\")' style='font-size: 16px;padding:8px 6px;' nowrap id='" + materialname[q].id + "'>" + (q + 1) + "." + materialname[q].name + "</td>");
                    }
                    if ((materiallength.length) / 5 > 1) {
                        $("#matertable").html("<span style='padding:13px 0px;' class='glyphicon glyphicon-menu-right' aria-hidden='true' onclick='searchNextPageusages(1)' ></span>")
                    }
                }
            } else {
                /*删除时药品查询*/
                var html = $write.html();
                $write.html(html.substr(0, html.length - 1));
                thisname = $write.html();
                $(thisinput).val($write.html());
                var q = $write.html();
                var materialsjson = JSON.parse(storage.getItem("materials"));
                if (q != "") {
                    $("#material table tr td").remove();
                    var materialname = Enumerable.from(materialsjson).where("x=>x.pinyin.toLowerCase().indexOf('" + q + "')!=-1 ||x.name.indexOf('" + q + "')!=-1 ").orderByDescending("x=>x.freq").take(5 * 1).skip(5 * 0).toArray();
                    var materiallength = Enumerable.from(materialsjson).where("x=>x.pinyin.toLowerCase().indexOf('" + q + "')!=-1 ||x.name.indexOf('" + q + "')!=-1 ").orderByDescending("x=>x.freq").toArray();
                } else {
                    $("#material table tr td").remove();
                    var materialname = Enumerable.from(materialsjson).orderByDescending("x=>x.freq").take(5 * 1).skip(5 * 0).toArray();
                    var materiallength = Enumerable.from(materialsjson).orderByDescending("x=>x.freq").toArray();
                }

                if (materialname.length == 0) {
                    $("#material table tr ").append("<td>当前字母暂无匹配药品</td>")
                } else {
                    for (var q = 0; q < materialname.length; q++) {
                        //$("#material table tr").append("<td onclick='material(this)' style='font-size: 16px;padding:8px 6px;' nowrap id='" + materialname[q].id + "'>" + (q + 1) + "." + materialname[q].name + "</td>");
                        if (materialname[q].base_id != 0 && materialname[q].base_id != null) {
                            $("#material table tr").append("<td onclick='material(this)' style='font-size: 16px;padding:8px 6px;' nowrap id='" + materialname[q].id + "'>" + (q + 1) + "." + materialname[q].name + "<img class='fang' src='/img/cf.png'></td>");
                        } else {
                            $("#material table tr").append("<td onclick='material(this)' style='font-size: 16px;padding:8px 6px;' nowrap id='" + materialname[q].id + "'>" + (q + 1) + "." + materialname[q].name + "</td>");
                        }
                    }
                    if ((materiallength.length) / 5 > 1) {
                        $("#matertable").html("<span style='padding:13px 0px;' class='glyphicon glyphicon-menu-right' aria-hidden='true' onclick='searchNextPagematerial(1)' ></span>")
                    }
                }
            }
            return false;
        }
    }

    // 如果是剂量键盘，点击后不用查询，直接关闭
    if (keyboardType == "jl") {
        $("#material table tr td").remove();
        var q = $write.html();
        if ($(thisinput).val().substr($(thisinput).val().length - 1) == "g") {
            q = q.substr(0, q.length - 1);
            $(thisinput).val(q + character + "g");
            $write.html(q + character + "g");
            // console.log($(thisinput).val().length);
        } else {
            $(thisinput).val($write.html() + character);
            if ($(thisinput).val().length > 0) {
                $(thisinput).val($write.html() + character + "g");
                $write.html($write.html() + character + "g");
            }
        }

        var materialsjson = JSON.parse(storage.getItem("dosages"));
        var materialname = Enumerable.from(materialsjson).orderByDescending("x=>x.freq").take(5 * 2).toArray();
        for (var q = 0; q < materialname.length; q++) {
            if (q < 5) {
                $("#material table tr").eq(0).append("<td onclick='material(this,\"jl\")' style='font-size: 16px;padding:6px 6px;' nowrap id='" + materialname[q].id + "'>" + materialname[q].name + "</td>");
            } else {
                $("#material table tr").eq(1).append("<td onclick='material(this,\"jl\")' style='font-size: 16px;padding:8px 6px;' nowrap id='" + materialname[q].id + "'>" + materialname[q].name + "</td>");
            }

        }


    } else if (keyboardType == "yf") {
        /*用法查询*/
        $write.html($write.html() + character);
        $(thisinput).val($write.html());
        var q = $write.html();
        var materialsjson = JSON.parse(storage.getItem("usages"));
        if (q != "") {
            $("#material table tr td").remove();
            var materialname = Enumerable.from(materialsjson).where("x=>x.pinyin.toLowerCase().indexOf('" + q + "')!=-1 ||x.name.indexOf('" + q + "')!=-1 ").orderByDescending("x=>x.freq").take(5 * 1).skip(5 * 0).toArray();
            var materiallength = Enumerable.from(materialsjson).where("x=>x.pinyin.toLowerCase().indexOf('" + q + "')!=-1 ||x.name.indexOf('" + q + "')!=-1 ").orderByDescending("x=>x.freq").toArray();
        } else {
            $("#material table tr td").remove();
            var materialname = Enumerable.from(materialsjson).orderByDescending("x=>x.freq").take(5 * 1).skip(5 * 0).toArray();
            var materiallength = Enumerable.from(materialsjson).orderByDescending("x=>x.freq").toArray();
        }

        if (materialname.length == 0) {
            $("#material table tr ").append("<td>当前字母暂无匹配用法</td>")
        } else {
            for (var q = 0; q < materialname.length; q++) {
                $("#material table tr").append("<td onclick='material(this,\"yf\")' style='font-size: 16px;padding:8px 6px;' nowrap id='" + materialname[q].id + "'>" + (q + 1) + "." + materialname[q].name + "</td>");
            }

            if ((materiallength.length) / 5 > 1) {

                $("#matertable").html("<span style='padding:13px 0px;' class='glyphicon glyphicon-menu-right' aria-hidden='true' onclick='searchNextPageusages(1)' ></span>")
            } else {
                $("#matertable").html("")
            }
        }
    } else {
        /*药品查询*/
        $write.html($write.html() + character);
        $(thisinput).val($write.html());
        var q = $write.html();
        var materialsjson = JSON.parse(storage.getItem("materials"));
        if (q != "") {
            $("#material table tr td").remove();
            var materialname = Enumerable.from(materialsjson).where("x=>x.pinyin.toLowerCase().indexOf('" + q + "')!=-1 ||x.name.indexOf('" + q + "')!=-1 ").orderByDescending("x=>x.freq").take(5 * 1).skip(5 * 0).toArray();
            var materiallength = Enumerable.from(materialsjson).where("x=>x.pinyin.toLowerCase().indexOf('" + q + "')!=-1 ||x.name.indexOf('" + q + "')!=-1 ").orderByDescending("x=>x.freq").toArray();
        } else {
            $("#material table tr td").remove();
            var materialname = Enumerable.from(materialsjson).orderByDescending("x=>x.freq").take(5 * 1).skip(5 * 0).toArray();
            var materiallength = Enumerable.from(materialsjson).orderByDescending("x=>x.freq").toArray();
        }
        if (materialname.length == 0) {
            $("#material table tr ").append("<td>当前字母暂无匹配药品</td>")
        } else {
            for (var q = 0; q < materialname.length; q++) {
                //$("#material table tr").append("<td onclick='material(this)' style='font-size: 16px;padding:8px 6px;' nowrap id='" + materialname[q].id + "'>" + (q + 1) + "." + materialname[q].name + "</td>");
                if (materialname[q].base_id != 0 && materialname[q].base_id != null) {
                    $("#material table tr").append("<td onclick='material(this)' style='font-size: 16px;padding:8px 6px;position:relative' nowrap id='" + materialname[q].id + "'>" + (q + 1) + "." + materialname[q].name + "<img class='fang' src='/img/cf.png'></td>");
                } else {
                    $("#material table tr").append("<td onclick='material(this)' style='font-size: 16px;padding:8px 6px;' nowrap id='" + materialname[q].id + "'>" + (q + 1) + "." + materialname[q].name + "</td>");
                }
            }

            if ((materiallength.length) / 5 > 1) {
                $("#matertable").html("<span style='padding:13px 0px;' class='glyphicon glyphicon-menu-right' aria-hidden='true' onclick='searchNextPagematerial(1)' ></span>")
            } else {
                $("#matertable").html("")
            }
        }
    }
}

// 手动输入
function handle(obj, keyboardType) {
    layer.prompt({
        title: '手写输入',
        formType: 2
    }, function (pass, index) {
        pass = pass.replace(/[\r\n]/g, "")
        if (pass.replace(/ /g, '').length == 0) {
            layer.msg('请输入内容');
        } else {
            var $write = $(thiskey);
            if (keyboardType == "jl" || keyboardType == "yf") {
                if (pass.length <= 2) {
                    $(thiskey).css("white-space", "nowrap")
                    $(thiskey).css("font-size", "16px");
                } else if (pass.length == 3) {
                    $(thiskey).css("white-space", "nowrap")
                    $(thiskey).css("font-size", "14px");
                } else if (pass.length == 4) {
                    $(thiskey).css("white-space", "nowrap")
                    $(thiskey).css("font-size", "12px");
                } else {
                    $(thiskey).css("white-space", "nowrap")
                    $(thiskey).css("font-size", "8px");
                }
            } else {
                if (pass.length <= 3) {
                    $(thiskey).css("font-size", "30px");
                } else if (pass.length == 4) {
                    $(thiskey).css("font-size", "24px");
                } else if (pass.length == 5) {
                    $(thiskey).css("font-size", "20px");
                } else {
                    $(thiskey).css("font-size", "16px");
                }
            }
            if (keyboardType == "material") {
                // 判断重复的药品
                var mList = getCurrentMaterialList();
                mList.push(pass);
                var rm = arrRepeat(mList);
                if (rm != null) {
                    layer.msg(pass + "已有")
                    return
                }
                if (parseInt($(thiskey).attr("id")) == countindex - 1) {
                    addmaterial();
                }
                var materialsjson = JSON.parse(storage.getItem("materials"));
                var bool = true
                for (var i = 0; i < materialsjson.length; i++) {
                    if (materialsjson[i].name == pass) {
                        materialsjson[i].freq += 1;
                        bool = false;
                        break;
                    }
                }
                if (bool) {
                    var pinyin = query(pass);
                    var pinyinstr = "";
                    for (var i = 0; i < pinyin.length; i++) {
                        pinyinstr += pinyin[i] + ","
                    }
                    var basedata = new Object;
                    basedata.id = "";
                    basedata.name = pass;
                    basedata.pinyin = pinyinstr;
                    basedata.base_id = "";
                    basedata.user_id = getCookie('ybuser');
                    basedata.freq = 0;
                    basedata.ctype = "yp";
                    basedata.gmt_create = "";
                    basedata.max_dosage = "";
                    basedata.min_dosage = "";
                    // console.log(basedata)
                    materialsjson.push(basedata);
                }
                addmedicine();
                getmedical();
                storage.setItem("materials", JSON.stringify(materialsjson));
            } else if (keyboardType == "yf") {
                var materialsjson = JSON.parse(storage.getItem("usages"));
                var bool = true
                for (var i = 0; i < materialsjson.length; i++) {
                    if (materialsjson[i].name == pass) {
                        materialsjson[i].freq += 1;
                        bool = false;
                        break;
                    }
                }
                if (bool) {
                    var pinyin = query(pass);
                    var pinyinstr = "";
                    for (var i = 0; i < pinyin.length; i++) {
                        pinyinstr += pinyin[i] + ","
                    }
                    var basedata = new Object;
                    basedata.id = "";
                    basedata.name = pass;
                    basedata.pinyin = pinyinstr;
                    basedata.base_id = "";
                    basedata.user_id = getCookie('ybuser');
                    basedata.freq = 0;
                    basedata.ctype = "yf";
                    basedata.gmt_create = "";
                    basedata.max_dosage = "";
                    basedata.min_dosage = "";
                    materialsjson.push(basedata);
                }
                storage.setItem("usages", JSON.stringify(materialsjson));
            }
            addmedicine();
            getmedical();

            if (keyboardType == "age") {
                $("input[name='age']").val(pass);
            } else {
                $(thisinput).val(pass);
                $write.html(pass);
            }
            layer.close(index);
        }
    });
}


function material(obj, keyboardType) { //event
    var materi = $(obj).text().substring(2, $(obj).text().length);
    // 判断重复的药品
    var mList = getCurrentMaterialList();
    // mList.splice($(thiskey).html(),1);
    // console.log(mList)
    /*  var rm = arrRepeat(mList);
      if (rm != null && materi != $(thiskey).html()) {
          layer.msg(materi + "已有")
          return
      }*/
    var unit = "px";

    //判断是不是药品键盘
    if (keyboardType == undefined) {
        for (var i = 0; i < mList.length; i++) {
            if (mList[i] == materi) {
                layer.msg(materi + "已有")
                return
            }
        }
        mList.push(materi);
        if (materi.length <= 3) {
            $(thiskey).css("font-size", "30px");
        } else if (materi.length == 4) {
            $(thiskey).css("font-size", "24" + unit);
        } else if (materi.length == 5) {
            $(thiskey).css("font-size", "20" + unit);
        } else {
            $(thiskey).css("font-size", "16" + unit);
        }
        var materialsjson = JSON.parse(storage.getItem("materials"));
        var base_id = 0;
        for (var i = 0; i < materialsjson.length; i++) {
            if (materialsjson[i].name == $(obj).text().substring(2, $(obj).text().length)) {
                base_id = materialsjson[i].base_id;
                // console.log(base_id);
                materialsjson[i].freq += 1;
                storage.setItem("materials", JSON.stringify(materialsjson));
                break;
            }
        }
        if (base_id != 0) {
            $(thisinput).val("");
            showitem(base_id, $(obj).text().substring(2, $(obj).text().length), obj)
        } else {
            $(thiskey).html($(obj).text().substring(2, $(obj).text().length))
            $(thisinput).val($(obj).text().substring(2, $(obj).text().length))
        }
        addmedicine();
        if (parseInt($(thiskey).attr("id")) == countindex - 1) {
            addmaterial();
        }

    } else if (keyboardType == 'jl') {
        $(thiskey).css("font-size", "16" + unit);
        var materialsjson = JSON.parse(storage.getItem("dosages"));
        for (var i = 0; i < materialsjson.length; i++) {
            if (materialsjson[i].name == $(obj).text()) {
                materialsjson[i].freq += 1;
                storage.setItem("dosages", JSON.stringify(materialsjson));
            }
        }
        $(thiskey).html($(obj).text() + "g")
        $(thisinput).val($(obj).text() + "g")
        getmedical();
        addmedicine();
    } else if (keyboardType == 'yf') {
        var materialsjson = JSON.parse(storage.getItem("usages"));
        for (var i = 0; i < materialsjson.length; i++) {

            if (materialsjson[i].name == $(obj).text().substring(2, $(obj).text().length)) {
                materialsjson[i].freq += 1;
                storage.setItem("usages", JSON.stringify(materialsjson));
            }
        }
        if (materi.length <= 2) {
            $(thiskey).css("white-space", "nowrap")
            $(thiskey).css("font-size", "16px");
        } else if (materi.length == 3) {
            $(thiskey).css("white-space", "nowrap")
            $(thiskey).css("font-size", "14px");
        } else if (materi.length == 4) {
            $(thiskey).css("white-space", "nowrap")
            $(thiskey).css("font-size", "12px");
        } else {
            $(thiskey).css("white-space", "nowrap")
            $(thiskey).css("font-size", "8px");
        }
        $(thiskey).html($(obj).text().substring(2, $(obj).text().length))
        $(thisinput).val($(obj).text().substring(2, $(obj).text().length))
    }

    var elm = document.getElementById("pop");
    if (elm) {
        document.body.removeChild(elm);
    }
}

/**
 * 移除已选择药品
 */
function removematerial(obj) {
    console.log($(obj.parentNode.parentNode).find("lable").length)
    var materialname = $(obj.parentNode.parentNode).find("lable").eq(0).html();
    for (var i = 0; i < $(obj.parentNode.parentNode).find("lable").length; i++) {
        if ($(obj.parentNode.parentNode).find("lable").eq(i).html() != "") {
            $("#materialname").val(materialname);
            $("#emrform").attr("action", contextPath + "/removematerial");
            $("#emrform").ajaxSubmit(function (e) {
                $("#materialdiv").html(e);
                $("#materialname").val("");
                getmedical();
                addmedicine();
            });
        }
    }

}

var selectedRecipel = [];

/**
 * 增加已选择处方
 */
function showitem(id, name, obj) {

    if (selectedRecipel.length >= 10) {
        layer.msg("最多合并十个处方");
        return
    }
    for (var i = 0; i < selectedRecipel.length; i++) {
        if (id == selectedRecipel[i]) {
            layer.msg("该处方已添加!")
            return
        }
    }
    selectedRecipel.push(id);
    $("#emrform").attr("action", contextPath + "/iteminfo");
    $("#recipelbyid").val(id);
    $("#emrform").ajaxSubmit(function (e) {
        $("#materialdiv").html(e);
        $("#selectedrecipel").append("<label class='col-md-12' style='padding:0 5px' onclick='removerecipel(\"" + id + "\",this)'>" + name + "</label>");
        getmedical();
        addmedicine();
    });
}

/**
 * 关闭处方
 */

function closeItem() {
    $("#emrhistoryPage").animate({width: "0",}, 500);
    var evt = event || window.event;
    if (evt.stopPropagation) {
        evt.stopPropagation();
    } else {
        evt.cancelBubble = true;
    }
    evt.preventDefault();
}

/**
 * 移除已选择处方
 */
function removerecipel(id, obj) {
    $("#emrform").attr("action", contextPath + "/removerecipel");
    $("#recipelbyid").val(id);
    $("#emrform").ajaxSubmit(function (e) {
        $("#materialdiv").html(e);
        $(obj).remove();
        for (var i = 0; i < selectedRecipel.length; i++) {
            if (id == selectedRecipel[i]) {
                selectedRecipel.splice(i, 1);
            }
        }
        getmedical();
        addmedicine();
    });
}

/**
 * 添加药品框
 *
 */
function addmaterial() {
    var url = contextPath + "/addmaterial"
    var parms = {
        'index': countindex,
        'recipelindex': recipelindex
    };
    $.get(url, parms, function (data) {
        $("#materialdiv").append(data);
    });

}


/**
 * 排序药品
 */
function sortMaterial() {
    $("#emrform").attr("action", contextPath + "/sortrecipel");
    $("#emrform").ajaxSubmit(function (e) {
        $("#materialdiv").html(e);
    });
}

function searchNextPagematerial(a, event) {

    var last = a - 1;
    var q = $(thiskey).html()
    var materialsjson = JSON.parse(storage.getItem("materials"));
    // console.log(a + ":")
    var materialname = Enumerable.from(materialsjson).where("x=>x.pinyin.toLowerCase().indexOf('" + q + "')!=-1 ||x.name.indexOf('" + q + "')!=-1 ").orderByDescending("x=>x.freq").take(5 * (a + 1)).skip(5 * a).toArray();
    var materiallength = Enumerable.from(materialsjson).where("x=>x.pinyin.toLowerCase().indexOf('" + q + "')!=-1 ||x.name.indexOf('" + q + "')!=-1 ").toArray();
    $("#material table tr td").remove();

    if (materialname.length == 0) {

        $("#material table tr td").text("当前字母暂无匹配药品")
    } else {
        for (var q = 0; q < materialname.length; q++) {
            $("#material table tr").append("<td onclick='material(this)' style='font-size: 16px;padding:8px 6px;' nowrap id='" + materialname[q].id + "'>" + (q + 1) + "." + materialname[q].name + "</td>");
        }

        if ((materiallength.length) / 5 > (a + 1) && a > 0) {
            $("#matertable").html("<span style='padding:13px 0px;' class='glyphicon glyphicon-menu-left'  onclick='searchNextPagematerial(" + last + ")' ></span><span style='padding:13px 0px;' class='glyphicon glyphicon-menu-right'  onclick='searchNextPagematerial(" + (a + 1) + ")'></span>")
        } else if (a == 0 && (materiallength.length) / 5 > (a + 1)) {
            $("#matertable").html("<span style='padding:13px 0px;' class='glyphicon glyphicon-menu-right'  onclick='searchNextPagematerial(" + (a + 1) + ")'></span>")
        } else if ((materiallength.length) / 5 <= (a + 1) && a > 0) {
            $("#matertable").html("<span style='padding:13px 0px;' class='glyphicon glyphicon-menu-left'  onclick='searchNextPagematerial(" + last + ")' ></span>")
        } else {

            $("#matertable").html("")
        }
    }
}

function searchNextPageusages(a) {
    var last = a - 1;
    var q = $(thiskey).html()
    var materialsjson = JSON.parse(storage.getItem("usages"));
    var materialname = Enumerable.from(materialsjson).where("x=>x.pinyin.toLowerCase().indexOf('" + q + "')!=-1 ||x.name.indexOf('" + q + "')!=-1 ").orderByDescending("x=>x.freq").take(5 * (a + 1)).skip(5 * a).toArray();
    var materiallength = Enumerable.from(materialsjson).where("x=>x.pinyin.toLowerCase().indexOf('" + q + "')!=-1 ||x.name.indexOf('" + q + "')!=-1 ").toArray();
    $("#material table tr td").remove();

    if (materialname.length == 0) {

        $("#material table tr td").text("当前字母暂无匹配药品")
    } else {
        for (var q = 0; q < materialname.length; q++) {
            $("#material table tr").append("<td onclick='material(this,\"yf\")' style='font-size: 16px;padding:8px 6px;' nowrap id='" + materialname[q].id + "'>" + (q + 1) + "." + materialname[q].name + "</td>");
        }

        if ((materiallength.length) / 5 > (a + 1) && a > 0) {
            $("#matertable").html("<span style='padding:13px 0px;' class='glyphicon glyphicon-menu-left'  onclick='searchNextPageusages(" + last + ")' ></span><span style='padding:13px 0px;' class='glyphicon glyphicon-menu-right'  onclick='searchNextPagematerial(" + (a + 1) + ")'></span>")
        } else if (a == 0 && (materiallength.length) / 5 > (a + 1)) {
            $("#matertable").html("<span style='padding:13px 0px;' class='glyphicon glyphicon-menu-right'  onclick='searchNextPageusages(" + (a + 1) + ")'></span>")
        } else if ((materiallength.length) / 5 <= (a + 1) && a > 0) {
            $("#matertable").html("<span style='padding:13px 0px;' class='glyphicon glyphicon-menu-left'  onclick='searchNextPageusages(" + last + ")' ></span>")
        } else {

            $("#matertable").html("")
        }
    }

}

//循环执行，每隔1秒钟执行一次 1000，如果返回了微信OPENID，关闭定时器
function refreshCount() {
    var visitNo = $("#visitNo").val()
    var url = contextPath + "/interface/hasWxOpenId?visitNo=" + visitNo;
    // $.get(url, function (data) {
    //     if (data != null && data != "") {
    //         $("#telephone").val(data.telephone);
    //         $("#patientName").val(data.patientName);
    //         window.clearInterval(qrTimmer);
    //         $("#vtd3").css("visibility", "hidden");
    //     }
    // });
}

/**
 * 获取二维码
 */
function getQRCode(type) {
    // var url = contextPath + "/interface/getQRCode?visitNo=" + visitNo + "&userName=" + doctorName+"&timestamp="+timestamp;

    $("#qrCodeHolder").load(contextPath + "/interface/getQRCode?visitNo=" + visitNo + "&userName=" + doctorName + "&timestamp=" + timestamp + "&type=" + type, function (response, status, xhr) {
        if (status == "success") {
            $("#vtd3").css("visibility", "visible");
            qrTimmer = window.setInterval(refreshCount, 1000);
        }
    })
}

function showRecipelPage(page) {
    if (page == 1) {
        $("#recipelPage1").css("display", "block");
        $("#recipelPage2").css("display", "none");
    } else if (page == 2) {
        $("#recipelPage1").css("display", "none");
        $("#recipelPage2").css("display", "block");
    }
}

function Login() {
    layer.open({
        scrollbar: false,
        type: 2,
        title: false,
        shade: 0.6,
        id: 'loginFormParent',
        shadeClose: false,
        area: ['90%', '90%'],
        content: '/login' //iframe的url
    });
}

//share
var iflag = true;

function share() {
    console.log('提交')
    if (iflag) {
        iflag = false;
        var ybuser = getCookie('ybuser');
        if (ybuser == null || ybuser == "" || ybuser == undefined) {
            /*先判断是不是登录状态如f果不是先登录*/
            var evt = event || window.event;
            if (evt.stopPropagation) {
                evt.stopPropagation();
            } else {
                evt.cancelBubble = true;
            }
            evt.preventDefault();
            Login();
        } else {
            $("#emrFormSubmitBtn").submit();
            var formValid = $("#emrform").data('bootstrapValidator').isValid();

            if (!formValid) {
                $("#emrFormSubmitBtn").submit();
                iflag = true;
            } else {
                $('.disablerecipelitem').attr("disabled", true);
                var diseaseSS = $("#diseaseSS").val();
                var disds = $("#disease").val();

                if (diseaseSS == "" && disds == "") {
                } else {
                    $("#diseasesum").val(diseaseSS + "&&&" + disds)
                }
                $("#emrform").attr("action", contextPath + "/sendemrvalid");
                $("#emrform").ajaxSubmit({
                    success: function (e) {
                        if (e == "验证成功") {
                            $('.share').show();
                            layer.open({
                                type: 1,
                                shade: false,
                                title: false, //不显示标题
                                content: $('.layer_notice'), //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
                                cancel: function () {
                                    $('.share').hide();
                                }
                            });
                            $("#emrform").attr("action", contextPath + "/saverecipel");
                            $("#emrform").ajaxSubmit(function (e) {
                                if (e == "发送成功") {
                                    $('.share').hide();
                                    // $(".share-all").css("visibility", "visible");
                                    layer.msg("发送成功")
                                    setTimeout(function () {
                                        layer.closeAll();
                                        window.location.href = contextPath + "/recipelIndex";
                                    }, 1500);
                                } else {
                                    $('.disablerecipelitem').attr("disabled", false);
                                    layer.msg(e);
                                    iflag = true;
                                }
                            }, function () {
                                layer.msg("网络问题")

                                iflag = true;
                            });
                        } else {
                            $('.disablerecipelitem').attr("disabled", false);
                            layer.msg(e);
                            iflag = true;
                        }
                    }, error: function () {
                        layer.msg("网络问题")
                        // console.log($("#emrform").serialize());
                        iflag = true;
                    }
                });

            }
        }
    }
}

$(".close").click(function () {
    $(this).parent().css("visibility", "hidden");
    $(document.body).css({
        "overflow-x": "auto",
        "overflow-y": "auto"
    });
    //脉舌回显
    var str = "";
    if ($("textarea.vein").val() != "") {
        str += "脉象：" + $("textarea.vein").val();
    }
    if ($("textarea.shezhi").val() != "") {
        str += "舌象：" + $("textarea.shezhi").val();
    }
    if ($(".symptom").val() != "") {
        str += "症状：" + $(".symptom").val();
    }
    $("textarea[id='disease']").text(str);
    MaxMe(document.getElementById("disease"));
    var bootstrapValidator = $("#emrform").data('bootstrapValidator');
    bootstrapValidator.updateStatus('disease', 'NOT_VALIDATED').validateField('disease');

})
/*舌切换*/
$(".shexzhi-title span").click(function () {
    $(this).addClass("check").siblings().removeClass("check");
    var sindex = $(this).index();
    $(".shezhi-div").eq(sindex).show().siblings(".shezhi-div").hide();
})
//date

$('.form_date').datetimepicker({
    language: 'zh-CN',
    weekStart: 1,
    todayBtn: 1,
    autoclose: 1,
    todayHighlight: 1,
    startView: 2,
    minView: 2,
    forceParse: 0
});
//VTD
var fs = true;
$(".vtd-title label").click(function () {
    huixian();
    var dd = $(this).parent().parent().index();
    console.log(dd)
    if (dd == 7) {
        /*症状弹框请求*/
        if (fs) {
            fs = false;
            var url = "/symptom/menus";
            $.get(url, function (menus) {
                menus.forEach(function (i, index) {
                    var s = "<span id=" + i.id + ">" + i.name + "</span>";
                    $("#menus").append(s)
                })
            })
        }
        $("#vtd" + $(this).index()).css("visibility", "visible");
        // }
        $(document.body).css({
            "overflow-x": "hidden",
            "overflow-y": "hidden"
        });
    }
    MaxMe(document.getElementById("vein"));
    MaxMe(document.getElementById("shezhi"));
    MaxMe(document.getElementById("symptom"));
})
//tongue
$(".VTD .tongue").on("click", "div ul li", function () {
    var shetai = "";
    var shezhi = "";
    var ss = "", sse = "", st = "", sx = "", sxml = "", cy = "";
    $(this).toggleClass("check");
    var thistext = $(this).text();
    for (var t = 0; t <= $(".shetai ul li").length; t++) {
        if ($(".shetai ul li").eq(t).attr("class") == "check") {
            shetai += $(".shetai ul li").eq(t).text() + "、";
        }
    }
    if ($(this).parent().parent().index() == 1) {
        for (var sxdiv = 1; sxdiv < $(".shezhi-div").length; sxdiv++) {
            for (var sxli = 0; sxli < $(".shezhi-div").eq(sxdiv).find("ul li").length; sxli++) {
                if ($(".shezhi-div").eq(sxdiv).find("ul li").eq(sxli).text() == thistext) {
                    $(".shezhi-div").eq(sxdiv).find("ul li").eq(sxli).toggleClass("check");
                }
                ;
            }
        }
    } else {
        for (var cyli = 0; cyli < $(".shezhi-div").eq(0).find("ul li").length; cyli++) {
            if ($(".shezhi-div").eq(0).find("ul li").eq(cyli).text() == thistext) {
                $(".shezhi-div").eq(0).find("ul li").eq(cyli).toggleClass("check");
            }
        }
    }

    if ($(".shezhi-div").eq(1).find("ul li.check").length != 0) {
        for (var t = 0; t <= $(".shezhi-div").eq(1).find("ul li").length; t++) {
            if ($(".shezhi-div").eq(1).find("ul li").eq(t).attr("class") == "check") {
                ss += $(".shezhi-div").eq(1).find("ul li").eq(t).text() + "、";
            }
        }
        ss = $(".shexzhi-title span").eq(1).text() + "：" + ss.substr(0, ss.length - 1) + "；"
    }
    if ($(".shezhi-div").eq(2).find("ul li.check").length != 0) {
        for (var t = 0; t <= $(".shezhi-div").eq(2).find("ul li").length; t++) {
            if ($(".shezhi-div").eq(2).find("ul li").eq(t).attr("class") == "check") {
                sse += $(".shezhi-div").eq(2).find("ul li").eq(t).text() + "、";
            }
        }
        sse = $(".shexzhi-title span").eq(2).text() + "：" + sse.substr(0, sse.length - 1) + "；"
    }
    if ($(".shezhi-div").eq(3).find("ul li.check").length != 0) {
        for (var t = 0; t <= $(".shezhi-div").eq(3).find("ul li").length; t++) {
            if ($(".shezhi-div").eq(3).find("ul li").eq(t).attr("class") == "check") {
                sx += $(".shezhi-div").eq(3).find("ul li").eq(t).text() + "、";
            }
        }
        sx = $(".shexzhi-title span").eq(3).text() + "：" + sx.substr(0, sx.length - 1) + "；"
    }
    if ($(".shezhi-div").eq(4).find("ul li.check").length != 0) {
        for (var t = 0; t <= $(".shezhi-div").eq(4).find("ul li").length; t++) {
            if ($(".shezhi-div").eq(4).find("ul li").eq(t).attr("class") == "check") {
                st += $(".shezhi-div").eq(4).find("ul li").eq(t).text() + "、";
            }
        }
        st = $(".shexzhi-title span").eq(4).text() + "：" + st.substr(0, st.length - 1) + "；"
    }
    if ($(".shezhi-div").eq(5).find("ul li.check").length != 0) {
        for (var t = 0; t <= $(".shezhi-div").eq(4).find("ul li").length; t++) {
            if ($(".shezhi-div").eq(5).find("ul li").eq(t).attr("class") == "check") {
                sxml += $(".shezhi-div").eq(5).find("ul li").eq(t).text() + "、";
            }
        }
        sxml = $(".shexzhi-title span").eq(5).text() + "：" + sxml.substr(0, sxml.length - 1) + "；"
    }
    shezhi = cy + ss + sse + st + sx + sxml;

    if (shetai != "") {
        shetai = shetai.substr(0, shetai.length - 1);
        shetai = "舌苔：" + shetai + "；";
    }
    $("textarea.shezhi").val(shetai + shezhi);

    MaxMe(document.getElementById("shezhi"));
})
$(".shexzhi-title span").eq(0).trigger("click");
//vein
$(".VTD .vein .nav a").click(function () {
    $(this).addClass("check");
    $(this).siblings().removeClass("check");
    maixiang();
})
$(".VTD .vein .tab-pane table td div a").click(function () {
    var tdindex = $(this).parent().parent().index();
    var trindex = $(this).parent().parent().parent().index();
    $(this).parent().toggleClass("xuan");
    if (trindex % 2 == 0) {
        $(this).parent().parent().parent().next().find("td").eq(tdindex).find("div").removeClass("xuan");
        $(this).parent().parent().parent().next().find("td").eq(tdindex).find("div").find("p span").removeClass("ch");
    } else if (trindex % 2 != 0) {
        $(this).parent().parent().parent().prev().find("td").eq(tdindex).find("div").removeClass("xuan");
        $(this).parent().parent().parent().prev().find("td").eq(tdindex).find("div").find("p span").removeClass("ch");
    }
    if (!$(this).parent().hasClass("xuan")) {
        $(this).parent().find("p span").removeClass("ch");
        $(this).parent().siblings().find("p span").removeClass("ch");
    }
    maixiang();
})
$(".VTD .vein .tab-pane table td div p span").click(function () {
    $(this).toggleClass("ch");
    if ($(this).hasClass("ch")) {
        var tdindex = $(this).parent().parent().parent().index();
        var trindex = $(this).parent().parent().parent().parent().index();
        $(this).parent().parent().addClass("xuan");
        if (trindex % 2 == 0) {
            $(this).parent().parent().parent().parent().next().find("td").eq(tdindex).find("div").removeClass("xuan");
            $(this).parent().parent().parent().parent().next().find("td").eq(tdindex).find("div").find("p span").removeClass("ch");
        } else if (trindex % 2 != 0) {
            $(this).parent().parent().parent().parent().prev().find("td").eq(tdindex).find("div").removeClass("xuan");
            $(this).parent().parent().parent().parent().prev().find("td").eq(tdindex).find("div").find("p span").removeClass("ch");
        }
    }
    var t = $(".VTD .vein .nav a");
    maixiang();
})

/*脉象选择*/
function maixiang() {
    var t = $(".VTD .vein .nav a");
    var maixiang = "";
    for (var i = 0; i < t.length; i++) {
        if ($(".VTD .vein .tab-pane").eq(i).find(".xuan").length != 0) {
            maixiang += t.eq(i).text() + "：";

            var xuanLength = $(".VTD .vein .tab-pane").eq(i).find(".xuan").length;
            for (var j = 0; j < xuanLength; j++) {
                maixiang += $(".VTD .vein .tab-pane").eq(i).find(".xuan").eq(j).find("a").text();
                maixiang += $(".VTD .vein .tab-pane").eq(i).find(".xuan").eq(j).parent().find(".ch").text();
                maixiang += " ";
            }
            maixiang += "；";
        }
    }
    $("textarea.vein").val(maixiang);
    MaxMe(document.getElementById("vein"));
}

//默认当天
if ($(".date input").val() == "") {
    var myDate = new Date();
    var y = myDate.getFullYear();    //获取完整的年份(4位,1970-????)
    var m = myDate.getMonth() + 1;       //获取当前月份(0-11,0代表1月)
    var d = myDate.getDate();  //获取当前日(1-31)
    if (m < 10) {
        m = "0" + m;
    }
    var nowdata = y + "/" + m + "/" + d;
    $(".date input").val(nowdata);
}

//处方复用
function findemrbyvisitNo(visitNo, action) {
    $("#myModal").modal('hide')
    if (action == 'fz') {
        window.location.href = contextPath + "/recipelIndex?visitNo=" + visitNo + "&action=" + action;
        return;
    }
    window.location.href = contextPath + "/recipelIndex?visitNo=" + visitNo;
}

function refuserecipel(visitNo) {

    $("#emrhistoryPage").animate({width: "0",}, 500);
    $("#emrform").attr("action", contextPath + "/findreciplebyvisitNostr");
    $("#visitNostr").val(visitNo);
    $("#emrform").ajaxSubmit(function (e) {
        $("#materialdiv").html(e);
        addmedicine();
        getmedical();
    });

}

//处方编辑
function editrecipel(visitNo) {
    $("#emrhistoryPage").animate({width: "0",}, 500);
    writeRecipel()
    var url = contextPath + "/editrecipel"
    var parms = {
        visitNostr: visitNo
    };
    $.get(url, parms, function (data) {
        $("#materialdiv").html(data);
    });

}

//提示信息滚动播放
function AutoScroll() {
    $(".news div").eq(0).animate({marginTop: "-59px"}, 2000, function () {
        $(".news").find("div:first").css({marginTop: "unset"}).appendTo($(".news"));
        $(".news div").eq(1).css({marginTop: "0px"});
    })
}

setInterval('AutoScroll()', 4000);
//科室选择

$(".keshi-nav li label").click(function () {
    $(".keshi-nav").hide();
    var href = $(this).attr("class");
    $("#" + href).show().siblings().hide();

});
$(".keshi").click(function () {
    $(".keshi-nav").show();
    $(".keshi-content ul").hide();
})

//设置cookie
function setCookie(name, value, day) {
    var date = new Date();
    date.setDate(date.getDate() + day);
    document.cookie = name + '=' + value + ';expires=' + date;
};

//获取cookie
function getCookie(name) {

    var reg = RegExp(name + '=([^;]+)');
    var arr = document.cookie.match(reg);
    if (arr) {
        return arr[1];
    } else {
        return '';
    }
};

//删除cookie
function delCookie(name) {
    setCookie(name, null, -1);
};

//煎服法
function TakeMedicine() {
    $(".Make").show();
    layer.open({
        type: 1,
        shade: 0,
        shadeClose: false,
        closeBtn: 0,
        title: false, //不显示标题
        area: ['540px', 'auto'],
        content: $('.make')
    });
    jffhuixian();
}

$(".h").click(function () {
    $(this).parent().hide();
    var aii = "", yongf = "", zhif = "", shij = "";
    if ($(".ai").val() != "") {
        aii = "智能免煎：" + $(".ai").val()
    }
    if ($(".yf").val() != "") {
        yongf = "用法：" + $(".yf").val()
    }
    if ($(".zf").val() != "") {
        zhif = "制法：" + $(".zf").val() + "；"
    }
    if ($(".sj").val() != "") {
        shij = "时间：" + $(".sj").val()
    }
    $("textarea.ff").val(yongf + zhif + shij + aii);
    MaxMe(document.getElementsByClassName("ff")[0])
})
$(".time span").click(function () {
    $(this).addClass("check");
    $(this).siblings().removeClass("check");
    var thisIndex = $(this).index();
    $("#time" + thisIndex).show();
    $("#time" + thisIndex).siblings("div").hide();
})
var yff = "";
$(".time-content>span").click(function () {
    $(this).addClass("check");
    $(this).siblings().removeClass("check");
    yfhx();
    zfhx();
})

/*
 * 制法剂量选择
 * */
function MaxMe(o) {
    o.style.height = o.scrollTop + o.scrollHeight + "px";
}

/*
 * 年龄输入
 */
$("input[name='age']").click(function () {
    $("#jsboard").hide();
    var elem = document.getElementById("pop");
    if (elem) {
        document.body.removeChild(elem);
    }
    var eletop = $(this).offset().top;
    var eleleft = $(this).offset().left;
    var eleh = $(this).height();
    var agetop = eletop + eleh + 10;
    $("#ageboard").show().css({'top': agetop, 'left': eleleft});
    var evt = event || window.event;
    if (evt.stopPropagation) {
        evt.stopPropagation();
    } else {
        evt.cancelBubble = true;
    }
    evt.preventDefault();
});

function chuan(num) {
    var defaultage = $("input[name='age']").val();
    if (num == "del") {
        if (defaultage == "成人" || defaultage == "儿童") {
            defaultage = "";
        } else if (defaultage.substr(1) == "岁") {
            defaultage = "";
        } else if (defaultage != "" && defaultage.substr(defaultage.length - 1) == "岁") {
            defaultage = defaultage.substring(0, defaultage.length - 2) + "岁";
        } else {
            defaultage = "";
        }
    } else if (num == "成人" || num == "儿童") {
        defaultage = num;
    } else if (defaultage.substr(defaultage.length - 1) == "岁") {
        defaultage = defaultage.substring(0, defaultage.length - 1);
        defaultage += num + "岁";
    } else if (defaultage == "成人" || defaultage == "儿童") {
        defaultage = num + "岁";
    } else {
        defaultage += num + "岁";
    }
    if (defaultage.substring(0, defaultage.length - 1) > 120) {
        layer.msg("年龄超过限度");
    } else {
        $("input[name='age']").val(defaultage);
    }
    var evt = event || window.event;
    if (evt.stopPropagation) {
        evt.stopPropagation();
    } else {
        evt.cancelBubble = true;
    }
    evt.preventDefault();
}

$(function () {
    MaxMe(document.getElementsByClassName('ff')[0]);
    MaxMe(document.getElementById("disease"));
    $('.dropdown-toggle').dropdown()
    findEmrHistoryByName("","","histroy")
})

/*
 * 判断十八反十九畏
 */
function addmedicine() {
    $("div.alert.alert-warning").remove();
    var eighteenNinteensjson = JSON.parse(storage.getItem("eighteenNinteens"));
    materlist = getCurrentMaterialList();
    for (var i = 0; i < eighteenNinteensjson.length; i++) {
        if (materlist.indexOf(eighteenNinteensjson[i].medicine1) != -1 && materlist.indexOf(eighteenNinteensjson[i].medicine2) != -1) {
            var type = ""
            if (eighteenNinteensjson[i].type == "opposite") {
                type = "相反"
            } else if (eighteenNinteensjson[i].type == "fear") {
                type = "相畏"
            } else if (eighteenNinteensjson[i].type == "notsuitable") {
                type = "同用"
            }
            $("#autoscroll").append(" <div class='alert alert-warning' ><strong>警告!</strong>" + eighteenNinteensjson[i].medicine1 + "不宜与" + eighteenNinteensjson[i].medicine2 + type + "</div>")
        }
    }
}

//获取药品名字和对应剂量
function getmedical() {
    $("div.alert.alert-danger").remove();
    var currentmedicalList = [];
    $(".material_hidden").each(function (key, value) {
        var medical = [];
        medical.name = $(this).val();
        if ($(this).val() != "") {
            var medosage = $(value).attr("name").replace("name", "dosage")
            var str11 = "input[name='" + medosage + "']";
            medical.dosage = $(str11).val().replace("g", "");
            currentmedicalList.push(medical);
        }
    });
    var materialsjson = JSON.parse(storage.getItem("materials"));
    for (var j = 0; j < currentmedicalList.length; j++) {
        var materialname = Enumerable.from(materialsjson).where("x=>x.name=='" + currentmedicalList[j].name + "' &&x.min_dosage!='' &&x.max_dosage !=''").toArray();
        if (materialname.length > 0) {
            if (Number(currentmedicalList[j].dosage) > Number(materialname[0].max_dosage)) {
                $("#autoscroll").append(" <div class='alert alert-danger' ><strong>警告!</strong>" + currentmedicalList[j].name + "剂量超标" + "</div>")
            } else if (Number(currentmedicalList[j].dosage) < Number(materialname[0].min_dosage) && currentmedicalList[j].dosage != "") {
                $("#autoscroll").append(" <div class='alert alert-danger' ><strong>警告!</strong>" + currentmedicalList[j].name + "剂量不足" + "</div>")
            } else if (currentmedicalList[j].dosage == "") {
                $("#autoscroll").append(" <div class='alert alert-danger' ><strong>警告!</strong>" + currentmedicalList[j].name + "未填写剂量" + "</div>")
            }
        }
    }
}

/**
 * 剂数输入
 */
$("input[title='jishu']").click(function () {
    $("#ageboard").hide();
    var elem = document.getElementById("pop");
    if (elem) {
        document.body.removeChild(elem);
    }
    var eletop = $(this).offset().top;
    var eleleft = $(this).offset().left;
    var eleh = $(this).height();
    var board = $("#jsboard").height();
    var agetop = eletop - eleh - board;
    $("#jsboard").show().css({'top': agetop, 'left': eleleft});
    var evt = event || window.event;
    if (evt.stopPropagation) {
        evt.stopPropagation();
    } else {
        evt.cancelBubble = true;
    }
    evt.preventDefault();
});

function chuan1(num) {
    var defaultjs = $("input[title='jishu']").val();
    if (num == "del") {
        defaultjs = defaultjs.substring(0, defaultjs.length - 1);
    } else {
        defaultjs += num;
    }
    $("input[title='jishu']").val(defaultjs);

    var evt = event || window.event;
    if (evt.stopPropagation) {
        evt.stopPropagation();
    } else {
        evt.cancelBubble = true;
    }
    evt.preventDefault();
}

//判断缓存中是否还有信息
function pdlocal() {
    if (storage.getItem("materials") == null || storage.getItem("usages") == null || storage.getItem("dosages") == null || storage.getItem("eighteenNinteens") == null) {
        // console.log("没了")
        return true;
    } else {
        return false;
    }
}

/*具体症状加载*/
$("#menus").on('click', 'span', function () {
    $(this).addClass("select");
    $(this).siblings().removeClass("select");
    var url = "/symptom/menu/" + this.id;
    var idi = this.id;
    var div = "<div id='symptom" + this.id + "'></div>";
    $("#symptoms").append(div);
    $("#symptom" + idi).show();
    $("#symptom" + idi).siblings().hide();
    if ($("#symptom" + idi).find("span").length == 0) {
        $.get(url, function (symptoms) {
            symptoms.forEach(function (i, index) {
                var s = "<span>" + i.name + "</span>";
                $("#symptom" + idi).append(s);
            })
            $("#symptom" + idi).append("<span class='icon-plus'></span>");
        });
        $("#specific").css("opacity", "1");
    } else {
        $("#specific").css("opacity", "1");
    }


});

function chuan2(num) {
    var defaultjs = $(".zhen p input").val();
    if (num == "del") {
        defaultjs = defaultjs.substring(0, defaultjs.length - 1);
    } else {
        defaultjs += num;
    }
    $(".zhen p input").val(defaultjs);
    day = "咳嗽：" + $(".zhen span.select").text() + defaultjs + "天；";
    var evt = event || window.event;
    if (evt.stopPropagation) {
        evt.stopPropagation();
    } else {
        evt.cancelBubble = true;
    }
    evt.preventDefault();
}

/*具体症状选择*/
$(".specific").on('click', 'span', function () {
    if ($(this).attr("class") == "icon-plus") {
        layer.prompt({
            title: '添加症状',
            formType: 2
        }, function (pass, index) {
            var pid = $("#menus .select").attr("id");
            $("#symptom" + pid).append("<span>" + pass + "</span>");
            var url = contextPath + "/symptom/add";
            var data = {menuId: pid, symptomName: pass};
            $.get(url, data, function (result) {
                /*暂无*/
            })
            layer.close(index);
        });
    }
    if ($(this).attr("class") != "icon-plus") {
        $(this).toggleClass("select");
    }
    var days = "";
    if ($(this).text() == "咳嗽") {
        var eletop = $(this).offset().top;
        var eleleft = $(this).offset().left;
        var eleh = $(this).height();
        $(".zhen").css({'top': eletop + eleh, 'left': eleleft}).show();
    }
    if ($(".specific").find(".select").length == 0) {
        $(".symptom").val("");
    } else {
        for (var ss = 0; ss < $(".specific span").length; ss++) {
            if ($(".specific span").eq(ss).attr("class") != "select" && $(".specific span").eq(ss).text() == "咳嗽") {
                $(".zhen").hide();
                $(".symptom").val(days);
            } else if ($(".specific span").eq(ss).attr("class") == "select" && $(".specific span").eq(ss).text() != "咳嗽") {
                days += $(".specific span").eq(ss).text() + "；";
                $(".symptom").val(days + day);
            }
        }

    }
    MaxMe(document.getElementById("symptom"));
})


$(".zhen span").on('click', function () {
    $(this).addClass("select").siblings().removeClass("select");
    var evt = event || window.event;
    if (evt.stopPropagation) {
        evt.stopPropagation();
    } else {
        evt.cancelBubble = true;
    }
    evt.preventDefault();
})

/*具体症状具体信息键盘显示*/
$(".zhen p input").on('click', function () {
    $(this).focus();
    var eletop = $(this).offset().top;
    var eleleft = $(this).offset().left;
    var eleh = $("#days").height();
    $("#days").css({'top': eletop - eleh - 30, 'left': eleleft}).show();
    var evt = event || window.event;
    if (evt.stopPropagation) {
        evt.stopPropagation();
    } else {
        evt.cancelBubble = true;
    }
    evt.preventDefault();
})
/*用法输入*/
var objIndex;
var objparentIndex;

function yf(obj) {
    var eletop = $(obj).offset().top;
    var eleleft = $(obj).offset().left - 100;
    var eleh = $(obj).height();
    var board = $("#yfboard").height();
    objIndex = $(obj).parent().index();
    objparentIndex = $(obj).parents(".time-content").index();
    var agetop = eletop - eleh - board - 10;

    $("#yfboard").show().css({'top': agetop, 'left': eleleft});
    if (objIndex > 1 && objIndex <= 4) {
        objIndex -= 1;
    } else if (objIndex > 4) {
        objIndex -= 2;
    }
    var evt = event || window.event;
    if (evt.stopPropagation) {
        evt.stopPropagation();
    } else {
        evt.cancelBubble = true;
    }
    evt.preventDefault();
};

function chuanyf(num) {
    var defaultjs = $(".time-content").eq(objparentIndex - 1).find("p").find("span").eq(objIndex).find("input").val();
    if (num == "del") {
        defaultjs = defaultjs.substring(0, defaultjs.length - 1);
    } else {
        defaultjs += num;
    }
    $(".time-content").eq(objparentIndex - 1).find("p").find("span").eq(objIndex).find("input").val(defaultjs);
    yfhx();
    zfhx();
    sjhx();
    aihx();
    var evt = event || window.event;
    if (evt.stopPropagation) {
        evt.stopPropagation();
    } else {
        evt.cancelBubble = true;
    }
    evt.preventDefault();
}

/*用法回显*/
function yfhx() {
    var yongfa = $("#time0").find(".check").text();
    if ($("#time0").find("p").find("span").eq(0).find("input").val() != "") {
        yongfa += "：一天" + $("#time0").find("p").find("span").eq(0).find("input").val() + "次；"
    }
    if ($("#time0").find("p").find("span").eq(1).find("input").val() != "") {
        yongfa += "一剂" + $("#time0").find("p").find("span").eq(1).find("input").val() + "天；"
    }
    $(".yf").val(yongfa);
}

/*制法回显*/
function zfhx() {
    $(".zf").val($("#time1").find(".check").text());
}

/*时间回显*/
function sjhx() {
    var shijian = "";
    if ($("#time2").find("p").find("span").eq(0).find("input").val() != "") {
        shijian += "泡" + $("#time2").find("p").find("span").eq(0).find("input").val() + "分钟；"
    }
    for (var shij = 1; shij < $("#time2").find("p").find("span").length - 2; shij++) {
        if ($("#time2").find("p").find("span").eq(shij).find("input").val() != "") {
            var china = new Array('零', '一', '二', '三', '四', '五', '六', '七', '八', '九');
            var arr = china[shij]
            if (shij == 1) {
                shijian += arr + "、大火烧开后改小火煎煮" + $("#time2").find("p").find("span").eq(shij).find("input").val() + "分钟，滤出药汁；"
            } else {
                shijian += arr + "、再次加入适量水，大火烧开后改小火煎煮" + $("#time2").find("p").find("span").eq(shij).find("input").val() + "分钟，滤出药汁；"
            }
        }
    }
    if ($("#time2").find("p").find("span").eq($("#time2").find("p").find("span").length - 2).find("input").val() != "") {
        shijian += "先下" + $("#time2").find("p").find("span").eq($("#time2").find("p").find("span").length - 2).find("input").val() + "分钟；"
    }
    if ($("#time2").find("p").find("span").eq($("#time2").find("p").find("span").length - 1).find("input").val() != "") {
        shijian += "后下" + $("#time2").find("p").find("span").eq($("#time2").find("p").find("span").length - 1).find("input").val() + "分钟；"
    }
    $(".sj").val(shijian);
    MaxMe(document.getElementById("shijian"));
}

/*用法回显*/
function aihx() {
    var ai = "";
    if ($("#time3").find("p").find("span").eq(0).find("input").val() != "") {
        ai += $("#time3").find("p").find("span").eq(0).find("input").val() + "剂；"
    }
    if ($("#time3").find("p").find("span").eq(1).find("input").val() != "") {
        ai += $("#time3").find("p").find("span").eq(1).find("input").val() + "袋；"
    }
    $(".ai").val(ai);
}

function fuckcc(obj) {
    obj.focus()
    var evt = event || window.event;
    if (evt.stopPropagation) {
        evt.stopPropagation();
    } else {
        evt.cancelBubble = true;
    }
    evt.preventDefault();
}

function fuckcm(obj) {
    var elm = document.getElementById("pop");
    if (elm) {
        $(thiskey).html($(obj).val())
        $(thisinput).html($(obj).val())
    }
    keypre(obj, "yp")
    var daaaa = $("#material");
    var xian = $("#xian");
    $("#material").remove();
    $("#container").append(xian).append(daaaa);
    var evt = event || window.event;
    if (evt.stopPropagation) {
        evt.stopPropagation();
    } else {
        evt.cancelBubble = true;
    }
    evt.preventDefault();
}

//清空页面所有药品
function clearmaterial() {
    $("#materialdiv").html($("#materialinput").html());
    $("#selectedrecipel").html("")
    getmedical();
    addmedicine();
    selectedRecipel = []
}

$(function () {
    // 设置每次ajax开始都要加载等待图片，每次结束关闭等待图片
    $.ajaxSetup({
        beforeSend: function (xhr) {
            loadmsg();
        },
        complete: function () {
            closeload();
        }
    });
});
$(".vtd-title1 label").click(function () {
    $(".pucker").slideToggle("500");
    if ($(this).find("span").hasClass("fa-chevron-down")) {
        $(this).find("span").removeClass("fa-chevron-down").addClass("fa-chevron-up")
    } else {
        $(this).find("span").addClass("fa-chevron-down").removeClass("fa-chevron-up")
    }
})
/*月经史展示*/
$("input[name=sex]").change(function () {
    var inputId = $(this).attr("id");
    if (inputId == "optionsRadios26") {
        $(".menstruation").css("display", "inline-block");
    } else {
        $(".menstruation").hide();

    }
})
/*选择跟诊医生*/
function doctorchoose(obj) {
    $("#followTeacher").val(obj.innerText)
    $("#doctorchoose a").text($(obj).text());
    $("#doctordropdown").dropdown('toggle')
    var evt = event || window.event;
    if (evt.stopPropagation) {
        evt.stopPropagation();
    } else {
        evt.cancelBubble = true;
    }
    evt.preventDefault();
}
$(".addDoctor").keydown(function (e) {
    var evt = event || window.event;
    if(evt.keyCode==13) {
        $.ajax({
            url : "/follow/add",
            data : {
                account : account,
                teacherName : $(this).val()
            }
        })

        $(this).before("<button class='btn btn-default' onclick='doctorchoose(this)'>" + $(this).val() + "<span class='fa fa-close' onclick='removename(this)'></span></button>");
        $(this).val("").blur();
    }
    if (evt.stopPropagation) {
        evt.stopPropagation();
    } else {
        evt.cancelBubble = true;
    }
    evt.preventDefault();
}).click(function () {
    var evt = event || window.event;
    if (evt.stopPropagation) {
        evt.stopPropagation();
    } else {
        evt.cancelBubble = true;
    }
    evt.preventDefault();
})
function removename(obj,id) {
    $.ajax({
        url : "/follow/del",
        data : {
            id : id
        }
    })

    $(obj).parent().remove()
    var evt = event || window.event;
    if (evt.stopPropagation) {
        evt.stopPropagation();
    } else {
        evt.cancelBubble = true;
    }
    evt.preventDefault();
}
