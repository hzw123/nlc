function ff(time){
	if(10>parseInt(time)){
		return "0"+time;
	}else{
		return time;
	}
}
function dateFmt(creatTime){ 
	var time = new Date(creatTime); 
	return time.getFullYear() + "-"+ff(time.getMonth()+1)+
			"-"+ff(time.getDate())+" "+ff(time.getHours())+
			":"+ff(time.getMinutes())+":"+ff(time.getSeconds());
}
$(function(){
	$.post("/users/user",function(data){
		$(".name").text(data.name);
	});
	$.get("/api/servers",function(data){
		var json=data._embedded.servers;
		var $b=$("#level_box");
		for(var i=0;json.length>i;i++){
			$b.append('<option value='+json[i].ip+'>'+
					json[i].name+'&nbsp;&nbsp;'+json[i].ip+'</option>');
		}
	});
	var $c=$("#creat");
	$c.hover(function(){
		$c.css({cursor:'pointer'});
	},function(){});
	$.get("/api/tasks",{"page":0,"size":4,"sort":"createAt"},function(data){
	    var json=data.content;
		for(var i=0;json.length>i;i++){
			$(".table").append("<tr>"+
					"<td>"+json[i].id+"</td>"+
					"<td>"+json[i].name+"</td>"+
					"<td>"+json[i].status+"</td>"+
					"<td>"+json[i].crawlTime+"</td>"+
					"<td>"+json[i].server.ip+"</td>"+
					"<td>"+json[i].serverThread.name+"</td>"+
					"<td>"+json[i].currentKbRate+"mb/s</td>"+
					"<td>"+json[i].avgKbRate+"mb/s</td>"+
					"<td>"+json[i].downloadedCount+"</td>"+
					"<td>"+(json[i].discoveredCount-json[i].downloadedCount)+"</td>"+
					"<td>"+dateFmt(json[i].createAt)+"</td>"+
					"</tr>");
		}
	});
	$(".quit").click(function(){
		$.get("/logout",function(){
			location="login.html";
		});
	});
});