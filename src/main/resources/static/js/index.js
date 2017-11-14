var is_open = false;
var user_is_open = false;
var opinion_is_open = false;
var share_is_open = false;
var is_create = false;
var is_focus = false;
var share_note_id = "";
var old_content;
var status;
//个人信息下拉菜单
function down_pull(e) {
	e = e || event;
	e.stopPropagation ? e.stopPropagation() : e.cancelBubble = true;
	if (is_open) {
		$("#sess_list").hide();
		$("#down_pull_cli").removeClass("trans_rota_0");
		is_open = false;
	}
	else {
		$("#sess_list").show();
		$("#down_pull_cli").addClass("trans_rota_0");
		is_open = true;
	}
};
//初始化
function list_init() {
	is_open = false;
	$("#sess_list").hide();
	$("#down_pull_cli").removeClass("trans_rota_0");
}
//退出登录
function outLogin() {
	if (confirm("你确定要退出吗?")) {
		$.post("/outLogin", function (e) {
			if (e) {
				var url = window.location.host.split(":")[0];
				// location.href = "http://127.0.0.1:8084/login";
				location.href = "http://"+url+":8084/login";
			}
		});
	}
}
//空白区域点击
$(document).ready(function () {
	create_new_notes();
	Ctrl_s_save();
	img_big();
	content_main_h();
	window.onresize = function(){
		content_main_h();
	}
	$("#share_username").on('keydown',function(){
		share_js.keydown_a(this);
	})
	$("#mui-overlay").on("click",function(){
		e = e || event;
        e.stopPropagation ? e.stopPropagation() : e.cancelBubble = true;
		alert("das");
	})
	$("body").click(function () {
		(is_open) ? list_init() : is_open = false;
	});
});
//手机端回收 左notelist
function hiden_side(z){
	  $("#sidedrawer")[0].style.transform="translate(0px)";
	  $(z).remove();
}
//设置note id  获取此活动分享给的人
function set_share_note_id(z){
	share_note_id = $(z).attr("data-join");
	$("#is_in_share").html("");
	$.ajax({
		url:"/share/"+share_note_id+"/find",
		type:"post",
		data:{},
		success:function(e){
			if(e!=null&&e!=""){
				for(var i = 0; i < e.length; i++){
					var lock;
					if(e[i].qx == "true"){
						lock = "lock_open";
					}
					else{
						lock = "lock_outline";
					}
					var big_div = $("<div class='share_in_user_s' name="+e[i].id+"></div>").appendTo($("#is_in_share"));
					$("<div><img style='width:30px;height:30px' src="+e[i].photo+"><span style='padding-left:5px;font-size:15px;'>"+e[i].account+"</span></div>").appendTo(big_div);
					$("<div style='display: flex;align-items: center;'><i class='material-icons share_lock' onclick='share_js.share_editable(this)'>"+lock+"</i><div onclick='share_js.delete_share_user(this)' class='share_close_x'>+</div></div>").appendTo(big_div);
				}
			}else{
				$("<div class='no_share_people'>暂无分享的人</div>").appendTo($("#is_in_share"));
			}
		}
	})
}
//Ctrl+s 保存
function Ctrl_s_save(){
	$("body").on("keydown",function(e) {
        var keyCode = e.keyCode || e.which || e.charCode;
        var ctrlKey = e.ctrlKey || e.metaKey;
        if(ctrlKey && keyCode == 83) {      
            $(".btn.save_button")[0].click();
            $(".btn.save_button")[0].focus();
            e.preventDefault();
        }
    });
}
//插入图片
function insertphoto(z){
	var file = $(z).parent().find("input")[0];
	file.click();
}
//根据返回数据的类型 给予提醒
function return_is_object(a, b, c) {
	if (typeof (a) === "object") {
		alert(a.message);
		return;
	}
	if (typeof (a) === "string") {
		if (a === "success") {
			if (c != null && c != "") {
				$(".user_from_photo")[0].src = c;
				$(".user_photo_div img")[0].src = c;
				return;
			}
			alert(b + "成功");
			return "success";
		}
	}
}
//content_main 自适应高度
function content_main_h(){
	var zw = $(".mui--appbar-height")[0].offsetHeight;
	var tw = $(".content_title")[0].offsetHeight;
	var qw = $(".btn-toolbar")[0].offsetHeight;
	var bh = document.body.clientHeight;
	$(".content_main")[0].style.height=bh - zw - tw - qw + "px";
}
//删除笔记
function delete_notes(a) {
	if(confirm("你确定要删除此笔记吗？")){
		var data_join = $(a).attr("data-join");
		var this_ = a
		$.ajax({
			url: data_join,
			type:"post",
			data:{"_method":"delete"},
			success:function(e){
				if(e === "success"){
					$(this_).parents(".notes_li").remove();
					$(".notes_ul .notes_li")[0].click();
				}
			}
		})
	}
}
//创建笔记 ajax
function create_notes_ajax(content) {
	$.ajax({
		url: "/notes",
		type: "post",
		async:false,
		data: { "title": "", "content": content },
		success: function (note_id) {
			//前端创建
			var new_li = $("#for_myself").first().clone();
			var date = new ed_change.get_date();
			$(new_li).each(function (x, e) {
				$(e).find(".notes_title .notes_li_title").text("无标题文档");
				$(e).find(".notes_main span").text("");
				$(e).find(".notes_footer_right").find("i").first().attr("data-join","/note/" + note_id);
				$(e).find(".notes_footer_right").find("i").last().attr("data-join",note_id);
				$(e).find(".notes_footer_right").find("div").first().html("");
				$(e).find(".notes_footer_left span").text(date.year + "-" + date.month + "-" + date.day);
			});
			ed_change.change_liClass(new_li);
			$(".notes_ul").append(new_li);
			ed_change.li_onclick_event();
			
		}
	})
}
//双击图片放大
function img_big(){
		var viewer = new Viewer(document.getElementById("editor"), {
			url: 'data-original',
			show: function (){  
		         viewer.update();  
			}
		});
}

//更换头像 ajax
function onchan(aa) {
	var file = $(aa)[0].files[0];
	//判断是否是图片类型
	if (!/image\/\w+/.test(file.type)) {
		alert("只能选择图片");
		return false;
	}
	else {
		var reader = new FileReader();
		reader.readAsDataURL(file);
		reader.onload = function (e) {
			var res = this.result;
			var rel_res = res.split(";base64,")[1];
			$.ajax({
				url: "/user",
				data: { "_method": "PUT", "base_url": rel_res, "change": "photo" },
				type: 'post',
				success: function (e) {
					return_is_object(e, "个人头像修改", res);
				}
			})
		}
	}
}
//更换头像  file显示
function change_photo() {
	if ($("#input_form")[0]) {
		document.getElementById("file_photo").click();
	}
	else {
		var photo_input = $("<input type='file' name='file' onchange='onchan(this)' id='file_photo' style='display:none'/>").appendTo(document.body);
		$(photo_input).click();
	}
}
//修改资料
function change_message(a) {
	var user_nickname = $(a).parent().parent().find("#user_nickname")[0].value;
	var user_province = $(a).parent().parent().find("#user_province")[0].value;
	var user_city = $(a).parent().parent().find("#user_city")[0].value;
	var user_mood = $(a).parent().parent().find("#user_mood")[0].value;

	$.ajax({
		url: "/user",
		data: { "_method": "PUT", "change": "message", "user_nickname": user_nickname, "user_province": user_province, "user_city": user_city, "user_mood": user_mood },
		type: "post",
		success: function (e) {
			return_is_object(e, "个人信息修改", "");
		}
	})
}
//反馈ajax
function sent_opinion(z) {
	var opinion = $(z).parent().parent().find("#user_opinion")[0].value;
	$.ajax({
		url: "/opinion",
		data: { "opinions": opinion },
		type: "post",
		success: function (e) {
			return_is_object(e, "提交反馈", "");
		}
	})
}
//新建笔记
function create_new_notes() {
	$(".add_table").on("click", function () {
		//数据库创建
			create_notes_ajax("");
	});
}
//编辑区赋给li
var ed_change = {
	//编辑区内容赋给li
	editor_change_span: function (e) {
		 $(".notes_li.active").find(".notes_footer_right div")[0].innerHTML = e.innerHTML;
		 $(".notes_li.active").find(".notes_main span").text($(".notes_li.active").find(".notes_footer_right div")[0].innerText);
	},
	editor_change_span2:function(){
		 var inner = $("#editor")[0].innerHTML;
		 $(".notes_li.active").find(".notes_footer_right div")[0].innerHTML = inner;
		 $(".notes_li.active").find(".notes_main span").text($(".notes_li.active").find(".notes_footer_right div")[0].innerText);
	},
	//标题的内容赋给li
	input_change_span: function (e) {
		if (e) {
			($(e).val() === "") ? $(e).val($(".notes_li.active").find(".notes_li_title").text()) :
				$(".notes_li.active").find(".notes_li_title").text(e.value);
		} else {
			var note_title = $(".notes_li").first().find(".notes_li_title");
			$(".content_title").val($(note_title).text());
			var note_cont = $(".notes_li").first().find(".notes_main span");
			ed_change.to_html($(".notes_footer_right div")[0].innerHTML);
			
			var notes_footer_right = $(".notes_footer_right");
			[].forEach.call(notes_footer_right,function(e){
				var get_text = $(e).find("div").first()[0].innerText;
				$(e).parent().parent().find(".notes_main span")[0].innerText = get_text;
			});

		}
	},
	//li的onclick
	li_onclick_event: function () {
		$(".notes_ul .notes_li").each(function (x, e) {
			e.addEventListener("click", function () {
				ed_change.change_liClass($(this));
			});
		});
	},
	//li的onclick执行的函数
	change_liClass: function (that) {
		$(".notes_ul .notes_li").each(function (x, e) {
			//首先删除每个li的class
			$(e).removeClass("active");
		});
		//给选中的li添加class
		$(that).addClass("active");
		var old_title = $(that).find(".notes_li_title");
		var old_cont = $(that).find(".notes_main span");
		$(".content_title").val($(old_title).text());
		ed_change.to_html($(that).find(".notes_footer_right div")[0].innerHTML)
		if($(that).find("#can_change").attr("name") == "false"){
			$("#editor").attr("contenteditable","false");
			$(".content_title").attr("disabled", "disabled");
		}
		else{
			$("#editor").attr("contenteditable","true");
			$(".content_title").removeAttr("disabled")
		}
	},
	//日期不足10补全0
	add_zero: function (x) {
		return (parseInt(x) < 10) ? parseInt(x) + 1 : x;
	},
	//转html
	to_html:function(x){
		$("#editor").html(x);
	},
	//获取当前年月日
	get_date: function () {
		var o = {};
		var dateObj = new Date();
		o.year = dateObj.getFullYear();
		o.month = ed_change.add_zero(dateObj.getMonth());
		o.day = ed_change.add_zero(dateObj.getDate());
		return o;
	},
	findimg:function(rel_res){
		var a;
		$.ajax({
			url:"/notephoto/",
			type:"post",
			data:{"base_url":rel_res},
			async:false,
			success:function(c){
				a = c;
			}
		})
		return a;
	},
	//修改note
	update: function(){
		if($(".notes_li.active").find("#can_change").attr("name") == "false"){
			return;
		}
		var url = $(".notes_li.active").find(".notes_footer_right").find("i.fa").first().attr("data-join");
		var title = $(".content_title").val();
		var content = $(".editor_input")[0].innerHTML;
		var status;
		$.ajax({
			url: url,
			data: {"_method": "put", "title": title, "content": content},
			type: "post",
			success: function(e){
				if(e === "success"){
					old_content = $(".editor_input").text();
					$(".save_button").text("已保存");
					setTimeout(function(){
					$(".save_button").text("保存");
					},1000);
				}
			}
		});
	}
}