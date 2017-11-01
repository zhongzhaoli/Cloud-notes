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
					var new_li = $(z).clone();
					$(new_li).each(function(x,e){
						$("<div style='color:white;transform: rotate(45deg);font-size: 30px;width: 20px;height: 20px;line-height: 20px;text-align: center;'>+</div>").appendTo($(e));
						$(e)[0].className = "share_in_user_s";
					})
					$("#is_in_share").append(new_li);
				}
			}
		})
	},
	keydown_a:function(){
		setTimeout(function(){
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
	}
}
