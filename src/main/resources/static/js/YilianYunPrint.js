function dyrecipel(visitNo) {
    //请求病例数据url
    var url = "emr/dyCf";
    //打印处方url
    // var printUrl = "http://chandler2sell.natapp1.cc/dy/doPrint";
    // var printUrl = "http://print.czyfwpla.cn/dy/doPrint";
    var printUrl = "http://print.open108.com/dy/doPrint";
    // console.log(url)
    //请求数据
    var data = new Object({
        "visitNo": visitNo
    });
    // console.log($("#clinicId").val())
    $.ajax({
        type: "POST",
        url: url,
        data: data,
        cache: false,
        async: false,
        success: function (e) {
           // console.log(e)
            //获取二维码
            var data = new Object({
                "disease": e.disease,
                "doctorName": e.doctorName,//医师
                "doctorId":e.doctorId,//医生id
                "visitNo": e.visitNo,
                "patientName": e.patientName,
                "age": e.age,
                "sex": e.sex,
                "createTime": e.createTime,
                "jff": e.recipels[0].jff,//煎服法
                "js": e.recipels[0].js,//剂数
                "je": e.recipels[0].je,//金额
                "recipeItems": JSON.stringify(e.recipels[0].recipelItems),//药品
                "notice": e.recipels[0].notice,
                "fhdoctor_name": e.recipels[0].fhdoctor_name,//药师
                "clinicId": e.clinicId+","+$("#clinicId").val() //医生所在诊所id  处方诊所id
            });
            // console.log(data)
            $.ajax({
                type: "POST",
                url: printUrl,
                data: data,
                cache: false,
                async: false,
                success: function (e) {
                    if (e.indexOf('成功') >= 0) {
                        layer.msg(e);
                    }else {
                        layer.alert(e);
                    }
                },
                error: function () {
                    layer.alert("提交失败！")
                }
            })

        },
        error: function () {
            alert("打印失败")
        }
    })
}