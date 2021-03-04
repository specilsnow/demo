/*煎服法回显*/
function jffhuixian() {
    var take=$("textarea.ff").val();
    $("input.yf").val("");
    $("input.zf").val("");
    $("input.sj").val("");
    $("input.ai").val("");
    if(take.indexOf("用")!=-1&&take.indexOf("制")!=-1&&take.indexOf("时")!=-1&&take.indexOf("智")!=-1){
        $("input.yf").val((take.substring(0,take.indexOf("制"))).substring(3,(take.substring(0,take.indexOf("制")+2)).length-1));
        $("input.zf").val((take.substring(take.indexOf("制"),take.indexOf("时"))).substring(3,(take.substring(take.indexOf("制"),take.indexOf("时")).length)-1));
        $("input.sj").val((take.substring(take.indexOf("时"),take.indexOf("智"))).substring(3,(take.substring(take.indexOf("时"),take.indexOf("智")).length)));
        $("input.ai").val((take.substring(take.indexOf("智"),take.length)).substring(5,(take.substring(take.indexOf("时"),take.length).length)));
    }else if(take.indexOf("用")==-1&&take.indexOf("制")!=-1&&take.indexOf("时")!=-1&&take.indexOf("智")!=-1){
        $("input.zf").val((take.substring(0,take.indexOf("时"))).substring(3,(take.substring(0,take.indexOf("时")).length)-1));
        $("input.sj").val((take.substring(take.indexOf("时"),take.indexOf("智"))).substring(3,(take.substring(take.indexOf("时"),take.indexOf("智")).length)));
        $("input.ai").val((take.substring(take.indexOf("智"),take.length)).substring(5,(take.substring(take.indexOf("时"),take.length).length)));
    }else if(take.indexOf("用")!=-1&&take.indexOf("制")==-1&&take.indexOf("时")!=-1&&take.indexOf("智")!=-1){
        $("input.yf").val((take.substring(0,take.indexOf("时"))).substring(3,(take.substring(0,take.indexOf("时")+2)).length-1));
        $("input.sj").val((take.substring(take.indexOf("时"),take.indexOf("智"))).substring(3,(take.substring(take.indexOf("时"),take.indexOf("智")).length)));
        $("input.ai").val((take.substring(take.indexOf("智"),take.length)).substring(3,(take.substring(take.indexOf("时"),take.length).length)));
    }else if(take.indexOf("用")!=-1&&take.indexOf("制")!=-1&&take.indexOf("时")==-1&&take.indexOf("智")!=-1){
        $("input.yf").val((take.substring(0,take.indexOf("制"))).substring(3,(take.substring(0,take.indexOf("制")+2)).length-1));
        $("input.zf").val((take.substring(take.indexOf("制"),take.indexOf("智"))).substring(3,(take.substring(take.indexOf("制"),take.indexOf("智")).length)-1));
        $("input.ai").val((take.substring(take.indexOf("智"),take.length)).substring(5,(take.substring(take.indexOf("制"),take.length).length)));
    } else if (take.indexOf("用") != -1 && take.indexOf("制") != -1 && take.indexOf("时") != -1 && take.indexOf("智") == -1) {
        $("input.yf").val((take.substring(0, take.indexOf("制"))).substring(3, (take.substring(0, take.indexOf("制") + 2)).length - 1));
        $("input.zf").val((take.substring(take.indexOf("制"), take.indexOf("时"))).substring(3, (take.substring(take.indexOf("制"), take.indexOf("时")).length) ));
        $("input.sj").val((take.substring(take.indexOf("时"), take.length)).substring(3, (take.substring(take.indexOf("制"), take.length).length)));
    }else if (take.indexOf("用") == -1 && take.indexOf("制") == -1 && take.indexOf("时") != -1 && take.indexOf("智") != -1) {
        $("input.sj").val((take.substring(0, take.indexOf("智"))).substring(3, (take.substring(0, take.indexOf("智") + 2)).length - 1));
        $("input.ai").val((take.substring(take.indexOf("智"),take.length)).substring(5,(take.substring(take.indexOf("智"),take.length).length)));
    }else if (take.indexOf("用") == -1 && take.indexOf("制") != -1 && take.indexOf("时") == -1 && take.indexOf("智") != -1) {
        $("input.zf").val((take.substring(0, take.indexOf("智"))).substring(3, (take.substring(0, take.indexOf("智"))).length-1));
        $("input.ai").val((take.substring(take.indexOf("智"),take.length)).substring(5,(take.substring(take.indexOf("智"),take.length).length)));
    }else if (take.indexOf("用") == -1 && take.indexOf("制") != -1 && take.indexOf("时") != -1 && take.indexOf("智") == -1) {
        $("input.zf").val((take.substring(0, take.indexOf("时"))).substring(3, (take.substring(0, take.indexOf("时"))).length-1));
        $("input.sj").val((take.substring(take.indexOf("时"),take.length)).substring(3,(take.substring(take.indexOf("时"),take.length).length)));
    }else if (take.indexOf("用") != -1 && take.indexOf("制") == -1 && take.indexOf("时") == -1 && take.indexOf("智") != -1) {
        $("input.yf").val((take.substring(0, take.indexOf("智"))).substring(3, (take.substring(0, take.indexOf("智"))).length));
        $("input.ai").val((take.substring(take.indexOf("智"),take.length)).substring(5,(take.substring(take.indexOf("智"),take.length).length)));
    }else if (take.indexOf("用") != -1 && take.indexOf("制") == -1 && take.indexOf("时") != -1 && take.indexOf("智") == -1) {
        $("input.yf").val((take.substring(0, take.indexOf("时"))).substring(3, (take.substring(0, take.indexOf("时"))).length));
        $("input.sj").val((take.substring(take.indexOf("时"),take.length)).substring(3,(take.substring(take.indexOf("时"),take.length).length)));
    }else if (take.indexOf("用") != -1 && take.indexOf("制") != -1 && take.indexOf("时") == -1 && take.indexOf("智") == -1) {
        $("input.yf").val((take.substring(0, take.indexOf("制"))).substring(3, (take.substring(0, take.indexOf("制"))).length));
        $("input.zf").val((take.substring(take.indexOf("制"),take.length)).substring(3,(take.substring(take.indexOf("制"),take.length).length-1)));
    }else if (take.indexOf("用") != -1 && take.indexOf("制") == -1 && take.indexOf("时") == -1 && take.indexOf("智") == -1) {
        $("input.yf").val(take.substring(3,take.length));
    }else if (take.indexOf("用") == -1 && take.indexOf("制") != -1 && take.indexOf("时") == -1 && take.indexOf("智") == -1) {
        $("input.zf").val(take.substring(3,take.length-1));
    }else if (take.indexOf("用") == -1 && take.indexOf("制") == -1 && take.indexOf("时") != -1 && take.indexOf("智") == -1) {
        $("input.sj").val(take.substring(3,take.length));
    }else if (take.indexOf("用") == -1 && take.indexOf("制") == -1 && take.indexOf("时") == -1 && take.indexOf("智") != -1) {
        $("input.ai").val(take.substring(5,take.length));
    }
    MaxMe(document.getElementById("shijian"));
}
/*症状回显*/
function huixian() {
    var str=$("textarea[id='disease']").text();
    if(str.indexOf("舌")!=-1&&str.indexOf("症")!=-1&&str.indexOf("脉")!=-1){
        $("#vein").val((str.substring(0,str.indexOf("舌"))).substring(3,(str.substring(0,str.indexOf("舌")+2)).length-1));
        $("#shezhi").val((str.substring(str.indexOf("舌"),str.indexOf("症"))).substring(3,(str.substring(str.indexOf("舌"),str.indexOf("症")+2)).length-1));
        $("#symptom").val((str.substring(str.indexOf("症"),str.length)).substring(3,(str.substring(str.indexOf("症"),str.length)).length));
    }else if(str.indexOf("脉")!=-1&&str.indexOf("舌")!=-1&&str.indexOf("症")==-1){
        $("#vein").val((str.substring(0,str.indexOf("舌"))).substring(3,(str.substring(0,str.indexOf("舌")+2)).length-1));
        $("#shezhi").val((str.substring(str.indexOf("舌"),str.length)).substring(3,(str.substring(str.indexOf("舌"),str.lenght)).length-1));
        $("#symptom").val("");
    }else if(str.indexOf("脉")!=-1&&str.indexOf("舌")==-1&&str.indexOf("症")!=-1){
        $("#vein").val((str.substring(0,str.indexOf("症"))).substring(3,(str.substring(0,str.indexOf("症")+2)).length-1));
        $("#shezhi").val("");
        $("#symptom").val((str.substring(str.indexOf("症"),str.length)).substring(3,(str.substring(str.indexOf("症"),str.length)).length));
    }else if(str.indexOf("脉")==-1&&str.indexOf("舌")!=-1&&str.indexOf("症")!=-1){
        $("#vein").val("");
        $("#shezhi").val((str.substring(str.indexOf("舌"),str.indexOf("症"))).substring(3,(str.substring(str.indexOf("舌"),str.indexOf("症")+2)).length-1));
        $("#symptom").val((str.substring(str.indexOf("症"),str.length)).substring(3,(str.substring(str.indexOf("症"),str.length)).length));
    }else if(str.indexOf("舌")!=-1&&str.indexOf("症")==-1&&str.indexOf("脉")==-1){
        $("#vein").val("");
        $("#shezhi").val(str.substring(3,str.length));
        $("#symptom").val("");
    }else if(str.indexOf("舌")==-1&&str.indexOf("症")==-1&&str.indexOf("脉")!=-1){
        $("#vein").val(str.substring(3,str.length));
        $("#shezhi").val("");
        $("#symptom").val("");
    }else if(str.indexOf("舌")==-1&&str.indexOf("症")!=-1&&str.indexOf("脉")==-1){
        $("#vein").val("");
        $("#shezhi").val("");
        $("#symptom").val(str.substring(3,str.length));
    }else{
        $("#vein").val("");
        $("#shezhi").val("");
        $("#symptom").val("");
    }
}