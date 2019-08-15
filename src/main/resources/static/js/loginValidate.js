$(function(){
	$("#mail,#pwd,#my_button,#code_input").hover(function(){
		$("#mail,#pwd,#my_button,#code_input").css({cursor:'pointer'});
	},function(){});
	var regname=/^\w{4,10}$/;
	var regpwd=/^\w{6,10}$/;
	$("#mail").blur(function(){
		var name=$("#mail").val();
		name=$.trim(name);
		if(name==""||name.length==0){
			$("#mail").next().text("请输入用户名！").css({color:'#f00','font-size':'18px'});
		}else if(!regname.test(name)){
			$("#mail").next().text("请输入4到10位用户名！").css({color:'#f00','font-size':'18px'});
		}else{
			$("#mail").next().text("");
		}
	});
	
	$("#pwd").blur(function(){
		var pwd=$("#pwd").val();
		if(pwd==""||pwd.length==0){
			$("#pwd").next().text("请输入密码！").css({color:'#f00','font-size':'18px'});
		}else if(!regpwd.test(pwd)){
			$("#pwd").next().text("请输入6到10位密码！").css({color:'#f00','font-size':'18px'});
		}else{
			$("#pwd").next().text("");
		}
	});
	
	var verifyCode = new GVerify("v_container");
	var falg=false;
	
	$("#my_button").click(function(){
		verifyCode.refresh();
	});
	
	$("#code_input").blur(function(){
		falg=verifyCode.validate($("#code_input").val());
		if(!falg){
			$("#validate").text("验证码输入错误！").css({color:'#f00','font-size':'18px'});
		}else{
			$("#validate").text("");
		}
	});
	//提交验证
	$(".submit").click(function(){
		var f=true;
		var name=$("#mail").val();
		var pwd=$("#pwd").val();
		name=$.trim(name);
		pwd=$.trim(pwd);
		
		if(name==""||name.length==0){
			$("#mail").next().text("请输入用户名！").css({color:'#f00','font-size':'18px'});
			f=false;
		}else if(!regname.test(name)){
			$("#mail").next().text("请输入4到10位用户名！").css({color:'#f00','font-size':'18px'});
			f=false;
		}else{
			$("#mail").next().text("");
		}
		
		if(!falg){
			f=false;
		}
		
		if(pwd==""||pwd.length==0){
			$("#pwd").next().text("请输入密码！").css({color:'#f00','font-size':'18px'});
				f=false;
		}
		if(f){
			$.ajax({
				url:"/login",
				type:"post",
				data:"username="+name+"&password="+pwd,
				dataType:"text",
				success:function(result){
					location="/";
				},
				error:function(result){
					alert("用户名或密码错误！");
				}
			});
		}
	});
});

