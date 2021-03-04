//判断用户加载

function loadBackground() {
    if(localStorage.getItem("theme")=="red"){
        $(".myPage").css("background","#cf3535");
        $(".myPage").css("color","black");
        $(".myPage").css("font-weight","600");
    }else {
        $(".myPage").css("background","#abe7ed");
        $(".myPage").css("color","#27a4b0");
        $(".myPage").css("font-weight","600");
    }
}