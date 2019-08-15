// JavaScript Document
		function checkformname(){
		var regname=/^\w{4,10}$/;
		var fname=document.getElementById("name");
		var ftname=document.getElementById("sname");
		if(fname.value==""|| fname.value.length<4||fname.value.length>10){
		ftname.className="frred";
		ftname.innerHTML="× 请输入邮箱";
		}
		else{
		if(fname.value.match(regname)){
		ftname.className="fgren";
		/*ftname.innerHTML="√用户名可用!"*/
		}
		else{
		ftname.className="frred";
		ftname.innerHTML="× 您输入的格式不正确，请重新输入！";
		}}
		}
		function checkpwd(){
		var fpwd=document.getElementById("pwd");
		var ftpwd=document.getElementById("spwd");
		if(fpwd.value==""||fpwd.value.length<4||fpwd.value.length>20){
		ftpwd.className="frred";
		ftpwd.innerHTML="× 登录密码或帐号输入有误！";
		}
		else{
		ftpwd.className="fgren";
		/*ftpwd.innerHTML="√密码可用！"*/
		}
		}
		function rcheckpwd(){
		var fpwd=document.getElementById("pwd");
		var frpwd=document.getElementById("rpwd");
		var ftrpwd=document.getElementById("srpwd");
		if(frpwd.value=="")
		{
		ftrpwd.className="frred";
		ftrpwd.innerHTML="× 请输入您的重复密码！"
		}
		else{
		if(frpwd.value!=fpwd.value)
		{
		ftrpwd.className="frred";
		ftrpwd.innerHTML="× 俩次密码输入不一致，请重新输入！";
		}
		else
		{
		ftrpwd.className="fgren";
		ftrpwd.innerHTML=" √ 密码输入正确"
		}
		}
		}
		
		function checkemail(){
		var regemail=/^\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*(;\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*)*$/;
		var femail=document.getElementById("mail");
		var ftemail=document.getElementById("semail");
		if(femail.value==""){
		ftemail.className="frred";
		ftemail.innerHTML="× 请输入邮箱！";
		}
		else{
		if(femail.value.match(regemail)){
		ftemail.className="fgren";
		/*ftemail.innerHTML="√ 邮箱地址正确！";*/
		}
		else{
		ftemail.className="frred";
		ftemail.innerHTML="× 您输入的格式不正确，请重新输入！";
		}
		}
		}