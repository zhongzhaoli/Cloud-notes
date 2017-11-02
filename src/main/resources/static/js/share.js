var share_js = {
	choose_share_user:function(z){
		var userid = $(z).attr("name");
		$("#share_user_list").html("");
		$("#share_user_list").hide();
		$("#share_username")[0].value = $(z).find("span")[0].innerHTML;
		$.ajax({
			url:"/share/"+share_note_id+"/user/"+userid,
			type:"post",
			data:{},
			success:function(e){
				return_is_object(e,"分享","");
				if(e === "success"){
					if($("#is_in_share").find("div.no_share_people").length > 0){
						$("#is_in_share").html("");
					}
					var new_li = $(z).clone();
					$(new_li).each(function(x,e){
						$("<div style='display: flex;align-items: center;'><i class='material-icons share_lock' onclick='share_js.share_editable(this)'>lock_outline</i><div onclick='share_js.delete_share_user(this)' class='share_close_x'>+</div></div>").appendTo($(e));
						$(e)[0].className = "share_in_user_s";
						$(e).attr("onclick","");
					})
					$("#is_in_share").append(new_li);
				}
			}
		})
	},
	keydown_a:function(z){
		var re=/^[a-zA-Z0-9]*$/;//只允许输入数字和大小写字母
		setTimeout(function(){
			if(!re.test(z.value)){
				var str = z.value;
				var newstr=str.substring(0,str.length-1);
				$(z)[0].value=newstr;
				return;
			}
			   var input_username = $("#share_username")[0].value;
			if(input_username == ""||input_username == null){
			   var input_username = "null";
			}
			$.ajax({
				url:"/share/"+input_username,
				type:"post",
				data:{},
				success:function(e){
					$("#share_user_list").html("");
					$("#share_user_list").hide();
					if(e != null && e != ""){
						$("#share_user_list").show();
						for(var i = 0; i < e.length; i++){
							var list = e[i];
							var big_div = $("<div class='share_in_user' name="+list.id+" onclick='share_js.choose_share_user(this)'></div>").appendTo($("#share_user_list"));
							$("<div><img style='width:30px;height:30px' src="+list.photo+"><span style='padding-left:5px;font-size:15px;'>"+list.account+"</span></div>").appendTo(big_div);
						}
					}
				}
			})
		},0)
	},
	delete_share_user:function(z){
		if(confirm("你确定取消分享给此用户吗?")){
			var user_id = $(z).parent().parent().attr("name");
			var note_id = share_note_id;
			$.ajax({
				url:"/share/"+note_id+"/user/"+user_id,
				type:"post",
				data:{"_method":"delete"},
				success:function(e){
					if(e === "success"){
						$(z).parent().parent().remove();
					}
				}
			})
		}
	},
	share_editable:function(z){
		var mess;
		($(z)[0].innerHTML=="lock_open") ? mess="拒绝" : mess="允许"; 
		if(confirm("你确定"+mess+"此人更改笔记吗？")){
			var user_id = $(z).parent().parent().attr("name");
			var note_id = share_note_id;
			$.ajax({
				url:"/share/"+note_id+"/editable/"+user_id,
				type:"post",
				data:{"_method":"put"},
				success:function(e){
					if(typeof(e) == "object"){
						(e.editable=="true") ? $(z)[0].innerHTML = "lock_open" : $(z)[0].innerHTML = "lock_outline";
					}
				}
			})
		}
	}
}
