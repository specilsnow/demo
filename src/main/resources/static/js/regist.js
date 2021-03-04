var contextPath = '';

//返回登陆	
function skipToLogin() {
  $("#inputContent").load(contextPath + "/login");
}

var ar = false;
//登录名是否重复
function check_account_repetition(){
  var data = $("#account").val();
  $.ajax({
    url : "/validation",
    data : {
      account : data
    },
    success : function(msg) {
      if (msg.status == "F") {
        alert(msg.content);
        $("#account").css("border", "1px solid red");
        ar =  false;
      } else {
        $("#account").css("border", "1px solid #CCCCCC");
        ar =  true;
      }
    }
  })
}

//验证用户名
function check_account() {
	var userName = $("#account").val();
	var regName = /^\w{3,11}/;
	if (userName == "" || userName.trim() == "") {
		$("#account").css("border", "1px solid red");
		return false;
	} else if (!regName.test(userName)) {
		alert("由英文字母和数字组成的3-11位字符(建议使用手机号码)")
		$("#account").css("border", "1px solid red");
		return false;
	} else {
		$("#account").css("border", "1px solid #CCCCCC");
		return true;
	}
}
//验证姓名
function check_username() {
	var nickName = document.getElementById("username").value;
	if (nickName == "" || nickName.trim() == "") {
		//		layer.msg("请输入姓名");
		$("#username").css("border", "1px solid red");
		return false;
	} else {
		$("#username").css("border", "1px solid #CCCCCC");
		return true;
	}
}

//验证密码
function check_pwd() {
	var pwd = document.getElementById("pwd").value;
	var regPwd = /^\w{3,20}$/;
	if (pwd == "" || pwd.trim() == "") {
				//layer.msg("请输入密码");
		$("#pwd").css("border", "1px solid red");
		return false;
	} else if (!regPwd.test(pwd)) {
				alert("密码由3-20位数字和英文字母组成")
		$("#pwd").css("border", "1px solid red");
		return false;
	} else {
		$("#pwd").css("border", "1px solid #CCCCCC");
		return true;
	}
}
//确认密码
function check_pwd2() {
	var pwd = document.getElementById("pwd").value;
	var pwd2 = document.getElementById("pwd2").value;
	if (pwd2 == "" || pwd2.trim() == "") {
		//		layer.msg("请输入密码");
		$("#pwd2").css("border", "1px solid red");
		return false;
	} else if (pwd2 != pwd) {
				alert("两次输入密码不一致")
		$("#pwd2").css("border", "1px solid red");
		return false;
	} else {
		$("#pwd2").css("border", "1px solid #CCCCCC");
		return true;
	}
}
function userregist() {
	check_account_repetition();
	var ac = check_account();
	var un = check_username();
	var pwd = check_pwd();
	var pwd2 = check_pwd2();
// alert(ar+"="+ac+"="+un+"="+pwd+"="+"="+pwd2);
	if (ac && un && pwd && pwd2 ) {
		$("#registForm").ajaxSubmit(function(response) {
			if (response.status == "T") {
				top.location.href = contextPath+"/login";
				parent.layer.closeAll();
			} else {
				alert(response.content)
			}
		});
	}
}




$("#select").change(function(){
	var c = $('#select option:selected').text();
	if('药房'==c){
		$("#clinicDiv").show()
	}else{
		$("#clinicDiv").hide()
	}
})

$('#registForm').bind('keyup', function(event) {
        if (event.keyCode == "13") {
            //回车执行查询
        	userregist()
        }
    });
