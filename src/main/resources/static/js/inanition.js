$(function () {

    //1.初始化Table
    var oTable = new TableInit();
    oTable.Init();

    //2.初始化Button的点击事件
    var oButtonInit = new ButtonInit();
    oButtonInit.Init();

});

var TableInit = function () {
    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
		
       $('#tab-list').bootstrapTable({
            url: '/api/tasks',         //请求后台的URL（*）
            method: 'get',                      //请求方式（*）
            toolbar: '#toolbar',                //工具按钮用哪个容器
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            queryParams: oTableInit.queryParams,//传递参数（*）
            silent: true,
            dataType:'json',
            minimumCountColumns: 0,
            pagination: true,
            maintainSelected:true,
//          showRefresh: true, // 显示刷新按钮  
//          showColumns: true,
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            clickToSelect: true,                //是否启用点击选中行
//            height: 563,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId:"id",                     //每一行的唯一标识，一般为主键列
			responseHandler: function(res) {
						                return {
						                    "total":res.totalElements,
						                	"rows": res.content   //数据
						                 };
						            },
            columns: [
                     {
                         field: '',
                         checkbox: true,
                         align: 'center',
                         valign: 'middle',
                     }, {
                         title: '序号',
                         field: 'number',
                         align: 'center',
                         valign: 'middle',
                         formatter: function (value, row, index) {  
                        	    return index+1;  
                         },
                         sortable: false,
                     }, {
                         title: '采集任务名称',
                         field: 'name',
                         align: 'center',
                         valign: 'middle',
                         sortable: false,
                     },{
                         title: '状态',
                         field: 'status',
                         align: 'center',
                         valign: 'middle',
                         sortable: false,
                     },{
                         title: '已采集时间',
                         field: 'crawlTime',
                         align: 'center',
                         valign: 'middle',
                         sortable: false,
                     },{
                         title: '服务器',
                         field: 'server.ip',
                         align: 'center',
                         valign: 'middle',
                         sortable: false,
                     },{
                         title: '当前下载速度',
                         field: 'currentKbRate',
                         align: 'center',
                         valign: 'middle',
                         formatter: function (value) {  
                       	    return value+" mb/s";  
                         },
                         sortable: false,
                     },{
                         title: '平均采集速度',
                         field: 'avgKbRate',
                         align: 'center',
                         valign: 'middle',
                         formatter: function (value) {  
                       	    return value+" mb/s";  
                         },
                         sortable: false,
                     },{
                         title: '已抓取文档数量',
                         field: 'downloadedCount',
                         align: 'center',
                         valign: 'middle',
                         sortable: false,
                     },{
                         title: '剩余抓取文档数量',
                         field: 'count',
                         align: 'center',
                         valign: 'middle',
                         formatter: function (value,row) {  
                        	return row.discoveredCount-row.downloadedCount;  
                         },
                         sortable: false,
                     },{
                         title: '已抓取文档容量',
                         field: 'totalData',
                         align: 'center',
                         valign: 'middle',
                         formatter: function (value) {  
                       	    return (value/(1024*1024)).toFixed(2)+" G";  
                         },
                         sortable: false,
                     },{
                         title: '采集进度',
                         field: 'nickName',
                         align: 'center',
                         valign: 'middle',
                         formatter: function (value, row, index) {  
                     	    return (row.downloadedCount/row.discoveredCount*100).toFixed(2)+"%";  
                         },
                         sortable: false,
                     },{
                         title: '任务创建时间',
                         field: 'createAt',
                         align: 'center',
                         valign: 'middle',
                         sortable: false,
                     },{
                         title: '调整任务采集顺序（优先级）',
                         field: 'complete',
                         align: 'center',
                         valign: 'middle',
                         sortable: false,
                     }, {
                    	 title: '操作',
                    	 field: 'Desc',
                         align: 'center',
                         events: 'operateEvents',
                         formatter: 'operateFormatter'
                     }
                 ]
        });
    };
    $('#search').click(function(){
    	$('#tab-list').bootstrapTable('refresh');
    });

    //得到查询的参数
    oTableInit.queryParams = function (params) {
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            size: params.limit,   //页面大小
            page: params.offset/params.limit,  //页码
            name: $(".code").val(),
            status: $("#state_box").val(),
            server: $("#level_box").val()
        };
        return {"name":temp.name,"size":temp.size,"page":temp.page,"server":temp.server,"status":temp.status};
    };
    return oTableInit;
};


var ButtonInit = function () {
  var oInit = new Object();
  var postdata = {};

  oInit.Init = function () {
      //初始化页面上面的按钮事件
  };

  return oInit;
};
    function operateFormatter(value, row, index) {
    		var status=row.status;
    		var data="";
    		if(status=='未开始'){
    			data="开始";
    		}else if(status=='采集中'){
    			data="暂停";
    		}else if(status=='暂停'){
    			data="继续";
    		}
            return [
                '<button type="button" class="RoleOfA btn btn-default  btn btn-link" >编辑</button>',
                '<button type="button" class="RoleOfB btn btn-default  btn btn-link" >'+data+'</button>',
                '<button type="button" class="RoleOfC btn btn-default  btn btn-link" >终止</button>',
                '<button type="button" class="RoleOfEdit btn btn-default  btn btn-link" >删除</button>',
                '<button type="button" class="RoleOfCheck btn btn-default  btn btn-link" >检查点</button>',
                '<button type="button" class="RoleOfSee btn btn-default  btn btn-link" >查看详情</button>'
            ].join('');
        }
    
    window.operateEvents = {
            'click .RoleOfA': function (e, value, row, index) {
                $(".edit_box").show();
                var id=row.id;
                if(row.status=="暂停"){
                	alert("修改参数！");
                }else{
                	alert(row.name+":状态-"+row.status+"，不可编辑！");
                }
         },
            'click .RoleOfB': function (e, value, row, index) {
                $(".stop_list").show();
                var id=row.id;
                var status=row.status;
                if(status=='采集中'){
                	$.ajax({
    	      			url:"/api/tasks/"+id+"/pause",
    	      			data:"",
    	      			type:"get",
    	      			dataType:"json",
    	      			success:function(result){
    	      				alert("暂停成功");
    	      			},
    	      			error(){
    	      				alert("暂停失败！");
    	      			}
    	      		});
                }else if(status=='暂停'){
                	$.ajax({
    	      			url:"/api/tasks/"+id+"/resume",
    	      			data:"",
    	      			type:"get",
    	      			dataType:"json",
    	      			success:function(result){
    	      				alert("继续成功");
    	      			},
    	      			error(){
    	      				alert("继续失败！");
    	      			}
    	      		});
                }
         },
          'click .RoleOfC': function (e, value, row, index) {
	        	var id=row.id;
	      		$.ajax({
	      			url:"/api/tasks/"+id+"/teminate",
	      			data:"",
	      			type:"get",
	      			dataType:"text",
	      			success:function(result){
	      				alert("终止失败");
	      			},
	      			error(){
	      				alert("终止失败");
	      			}
	      		});
         },
            'click .RoleOfEdit': function (e, value, row, index) {
            	if(window.confirm("是否删除"+row.name+"?")){
            		var id=row.id;
            		$.ajax({
            			url:"/api/tasks/"+id,
            			data:"",
            			type:"delete",
            			dataType:"text",
            			success:function(result){
            				$(":checkbox:not(#allCheckbox):checked").parent().parent().remove();  
            			},
            			error(){
            				alert("删除失败");
            			}
            		});
            	}
            },
            'click .RoleOfCheck': function (e, value, row, index) {
            	if(window.confirm("是否确定删除报警信息?")){
			   	$(":checkbox:not(#allCheckbox):checked").parent().parent().remove();  
			  }
            }
        }

	$(function(){

		$("#off2").click(function(){
			$(".edit_box").hide();
		});
		//点击取消
		$(".cancel_btn").click(function(){
			$(".edit_box").hide();
		});
		
		//点击暂停
		$("#off3").click(function(){
			$(".stop_list").hide();
		});
		//点击暂停(取消)
		$(".edit_btn").click(function(){
			$(".stop_list").hide();
		});
		
		//list_box点击取消
		$(".cancel").click(function(){
			$(".list_box").hide();
		});
		
	})
	
	/////////////////任务名称不为空////////
	$(function(){
		var name=$.trim($("#rw_name").val());
		$("#check_id").click(function(){
			if(name==""){
				$(".fill").show();
				return;
			}
			
			
		})
	})
	
	var dateRange = new pickerDateRange('date_demo3', {
				aRecent7Days : 'aRecent7DaysDemo3', //最近7天
				isTodayValid : false,
				//startDate : '2013-04-14',
				//endDate : '2013-04-21',
				//needCompare : true,
				//isSingleDay : true,
				//shortOpr : true,
				defaultText : ' 至 ',
				inputTrigger : 'input_trigger_demo3',
				theme : 'ta',
				success : function(obj) {
					$("#dCon_demo3").html('开始时间 : ' + obj.startDate + '<br/>结束时间 : ' + obj.endDate);
				}
			});
	
	var servers;
    var profiles;
	$.get("/api/servers",{"size":1000},function(data){
		servers=data.content;
		$("#level_box").empty();
		$("#level_box").append('<option value="0" style="padding-left: 10px;margin:0px;">全部</option>');
		for(var i=0;i<servers.length;i++){
			$("#level_box").append('<option value="'+servers[i].id+'" style="padding-left: 10px;margin:0px;">'+servers[i].name+'</option>');
		}
		
	});
	$('#myModal').on('show.bs.modal', function () {
		for(var i=0;i<servers.length;i++){
			$("#server_list").append('<option value="'+servers[i].id+'" style="padding-left: 10px;margin:0px;">'+servers[i].name+'</option>');
		}
		for(var j=0;j<servers[0].serverThreads.length;j++){
			$("#serverThread_list").append('<option value="'+servers[0].serverThreads[j].id+'" style="padding-left: 10px;margin:0px;">'+servers[0].serverThreads[j].name+'</option>');
		}
		$.get("/api/profiles",{"size":1000},function(data){
			profiles = data.content;
			$("#profile_list").empty();
			for(var i=0;i<data.content.length;i++){
				$("#profile_list").append('<option value="'+data.content[i].id+'" style="padding-left: 10px;margin:0px;">'+data.content[i].name+'</option>');
			}
		});
	});
	$('#server_list').change(function () {
		var p1=$(this).children('option:selected').val();//这就是selected的值
		for(var i=0;i<servers.length;i++){
			if(servers[i].id == p1){
				$("#serverThread_list").empty();
				for(var j=0;j<servers[i].serverThreads.length;j++){
					$("#serverThread_list").append('<option value="'+servers[i].serverThreads[j].id+'" style="padding-left: 10px;margin:0px;">'+servers[i].serverThreads[j].name+'</option>');
				}
				break;
			}
		}
	});
	$('#set_btn').click(function(){
		
		var selected_server = null;
		var selected_serverThread = null;
		var selected_profile = null;
		
		for(var i=0;i<servers.length;i++){
			if(servers[i].id == $("#server_list").val()){
				selected_server = servers[i];
				break;
			}
		}
		for(var i=0;i<selected_server.serverThreads.length;i++){
			if(selected_server.serverThreads[i].id == $("#serverThread_list").val()){
				selected_serverThread = selected_server.serverThreads[i];
				break;
			}
		}
		for(var i=0;i<profiles.length;i++){
			if(profiles[i].id == $("#profile_list").val()){
				selected_profile = profiles[i];
				break;
			}
		}
		
		var data = {
				name: $("#name_in").val(),
				tid: $("#tid_in").val(),
				url: $("#url_in").val(),
				description: $("#desc_in").val(),
				remarks: $("#remark_in").val(),
				server: selected_server,
				serverThread: selected_serverThread,
				profile: selected_profile
		}
		
		console.log(data);
		
		$.ajax({
	           type: "POST",
	           cache : false,
	           url: '/api/tasks',
	           dataType : "json",
	           data:data,
	           success: function(result){
	        	   alert(b);
	           },
	           error:function(){
	        	   
	           }
	    });
	});

