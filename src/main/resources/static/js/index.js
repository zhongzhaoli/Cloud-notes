var is_open=false;
var user_is_open=false;
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
//关闭个人信息
function close_user_div(){
	if(user_is_open){
		$(".user_mb").hide();
		user_is_open = false;
	}
	else{
		$(".user_mb").show();
		user_is_open = true;
	}
}
