var is_open = false;
var user_is_open = false;
var opinion_is_open = false;
var share_is_open = true;
var is_create = false;
var is_focus = false;
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
				location.href = "http://127.0.0.1:8084/login";
			}
		});
	}
}
//空白区域点击
$(document).ready(function () {
	create_new_notes();
	$("body").click(function () {
		(is_open) ? list_init() : is_open = false;
	});
});
//个人信息
function for_user_div() {
	if (user_is_open) {
		$("#user_mb").hide();
		user_is_open = false;
	}
	else {
		$("#user_mb").show();
		user_is_open = true;
	}
}
//意见反馈
function for_opinion_div() {
	if (opinion_is_open) {
		$("#opinion_mb").hide();
		opinion_is_open = false;
	}
	else {
		$("#opinion_mb").show();
		opinion_is_open = true;
	}
}
//分享
function for_share_div() {
	if (share_is_open) {
		$("#share_mb").hide();
		share_is_open = false;
	}
	else {
		$("#share_mb").show();
		share_is_open = true;
	}
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
//删除笔记
function delete_notes(a) {
	if(confirm("你确定要删除此笔记吗？")){
		var notes_id = $(a).parent().find("input")[0].name;
		var this_ = a
		$.ajax({
			url: "/notes/" + notes_id,
			type:"post",
			data:{"_method":"delete"},
			success:function(e){
				var b = return_is_object(e,"删除","");
				if(b === "success"){
					$(this_).parents(".notes_li").remove();
					$(".content_title")[0].value = "";
					$("#editor")[0].innerHTML = "";
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
			var new_li = $(".notes_ul .notes_li").last().clone();
			var date = new ed_change.get_date();
			$(new_li).each(function (x, e) {
				$(e).find(".notes_title .notes_li_title").text("无标题文档");
				$(e).find(".notes_main span").text("");
				$(e).find("#notes_id")[0].name = note_id;
				$(e).find(".notes_footer_left span").text(date.year + "-" + date.month + "-" + date.day);
			});
			ed_change.change_liClass(new_li);
			$(".notes_ul").append(new_li);
			ed_change.li_onclick_event();
		}
	})
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
	var user_nickname = $(a).parent().find("#user_nickname")[0].value;
	var user_province = $(a).parent().find("#user_province")[0].value;
	var user_city = $(a).parent().find("#user_city")[0].value;
	var user_mood = $(a).parent().find("#user_mood")[0].value;

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
	var opinion = $(z).parent().find("#user_opinion")[0].value;
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
		$(".notes_li.active").find(".notes_main span").text(e.innerHTML);
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
			$(".editor_input").text($(note_cont).text());
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
		$(".editor_input").text($(old_cont).text());
	},
	//日期不足10补全0
	add_zero: function (x) {
		return (parseInt(x) < 10) ? parseInt(x) + 1 : x;
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
	update: function(){
		var id = $(".notes_li.active").find(".notes_footer_right input")[0].name;
		var title = $(".content_title").val();
		var content = $(".editor_input").text();
		var status;
		$.ajax({
			url: "/note/"+ id,
			data: {"_method": "put", "id": id, "title": title, "content": content},
			type: "post",
			success: function(e){
				if(e === "success"){
					old_content = $(".editor_input").text();
				}
			}
		});
	}
}