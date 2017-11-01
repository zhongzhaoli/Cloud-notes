var share_js = {
	choose_share_user:function(z){
		$("#share_user_list").html("");
		$("#share_user_list").hide();
		$("#share_username")[0].value = $(z).find("span")[0].innerHTML;
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
							$("<div class='share_in_user' onclick='share_js.choose_share_user(this)'><img style='width:30px;height:30px' src="+list.photo+"><span style='padding-left:5px;font-size:15px;'>"+list.account+"</span></div>").appendTo($("#share_user_list"));
						}
					}
				}
			})
		},0)
	}
}
