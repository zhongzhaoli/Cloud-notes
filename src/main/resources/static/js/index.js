var is_open=false;
var user_is_open=false;
var opinion_is_open=false;
//个人信息下拉菜单
function down_pull(e){
	e = e || event;
	e.stopPropagation ? e.stopPropagation() : e.cancelBubble = true;
	if(is_open){
		$("#sess_list").hide();
		$("#down_pull_cli").removeClass("trans_rota_0");
		is_open=false;
	}
	else{
		$("#sess_list").show();
		$("#down_pull_cli").addClass("trans_rota_0");
		is_open=true;
	}
};
//初始化
function list_init(){
	is_open=false;
	$("#sess_list").hide();
	$("#down_pull_cli").removeClass("trans_rota_0");
}
//退出登录
function outLogin(){
	if(confirm("你确定要退出吗?")){
		$.post("/outLogin",function(e){
			if(e){
				location.href="http://127.0.0.1:8084/login";
			}
		});
	}
}
//空白区域点击
$(document).ready(function () {
    $("body").click(function () {
    	(is_open) ? list_init() : is_open=false;
    });
});
//个人信息
function for_user_div(){
	if(user_is_open){
		$("#user_mb").hide();
		user_is_open = false;
	}
	else{
		$("#user_mb").show();
		user_is_open = true;
	}
}
//意见反馈
function for_opinion_div(){
	if(opinion_is_open){
		$("#opinion_mb").hide();
		opinion_is_open = false;
	}
	else{
		$("#opinion_mb").show();
		opinion_is_open = true;
	}
}
//根据返回数据的类型 给予提醒
function return_is_object(a,b,c){
	if(typeof(a)==="object"){
		alert(a.message);
		return;
	}
	if(typeof(a)==="string"){
		if(a==="success"){
			if(c!=null&&c!=""){
				$(".user_from_photo")[0].src=c;
				$(".user_photo_div img")[0].src=c;
			}
			alert(b+"成功");
		}
	}
}
//更换头像 ajax
function onchan(aa){
    var file = $(aa)[0].files[0];
    //判断是否是图片类型
    if (!/image\/\w+/.test(file.type)) {
        alert("只能选择图片");
        return false;
    }
    else{
        var reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = function (e) {
        	var res=this.result;
        	var rel_res=res.split(";base64,")[1];
        	$.ajax({
        		url:"/user",
        		data:{"_method":"PUT","base_url":rel_res,"change":"photo"},
        		type:'post',
        		success:function(e){
        			return_is_object(e,"个人头像修改",res);
        		}
        	}) 
        }
    }
}
//更换头像  file显示
function change_photo(){
	if($("#input_form")[0]){
		document.getElementById("file_photo").click();
	}
	else{
		var photo_input = $("<input type='file' name='file' onchange='onchan(this)' id='file_photo' style='display:none'/>").appendTo(document.body);
		$(photo_input).click();
	}
}
//修改资料
function change_message(a){
	var user_nickname = $(a).parent().find("#user_nickname")[0].value;
	var user_province = $(a).parent().find("#user_province")[0].value;
	var user_city = $(a).parent().find("#user_city")[0].value;
	var user_mood = $(a).parent().find("#user_mood")[0].value;
	
	$.ajax({
		url:"/user",
		data:{"_method":"PUT","change":"message","user_nickname":user_nickname,"user_province":user_province,"user_city":user_city,"user_mood":user_mood},
		type:"post",
		success:function(e){
			return_is_object(e,"个人信息修改","");
		}
	})
}
//反馈ajax
function sent_opinion(z){
	var opinion = $(z).parent().find("#user_opinion")[0].value;
	$.ajax({
		url:"/opinion",
		data:{"opinions":opinion},
		type:"post",
		success:function(e){
			return_is_object(e,"提交反馈","");
		}
	})
}