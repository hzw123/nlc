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
            showRefresh: true, // 显示刷新按钮  
            showColumns: true,
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            clickToSelect: true,                //是否启用点击选中行
            height: 563,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
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
                         field: 'surplusCount',
                         align: 'center',
                         valign: 'middle',
                         sortable: false,
                     },{
                         title: '已抓取文档容量',
                         field: 'downCapacity',
                         align: 'center',
                         valign: 'middle',
                         formatter: function (value) {  
                       	    return value+" G";  
                         },
                         sortable: false,
                     },{
                         title: '采集进度',
                         field: 'nickName',
                         align: 'center',
                         valign: 'middle',
                         formatter: function () {  
                     	    return "33%";  
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
                         field: 'createAt',
                         align: 'center',
                         valign: 'middle',
                         sortable: false,
                     }, {
                    	 title: '操作',
                    	 field: 'updateAt',
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
            return [
                '<button type="button" class="RoleOfA btn btn-default  btn btn-link" >编辑</button>',
                '<button type="button" class="RoleOfB btn btn-default  btn btn-link" >暂停</button>',
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
	      		$.ajax({
	      			url:"/api/tasks/"+id+"/pause",
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
          'click .RoleOfC': function (e, value, row, index) {
	        	var id=row.id;
	      		$.ajax({
	      			url:"/api/"+id+"/teminate",
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
            			url:"/api/"+id,
            			data:"",
            			type:"delete",
            			dataType:"text",
            			success:function(result){
            			},
            			error(){
            				alert("删除失败");
            			}
            		});
            		$(":checkbox:not(#allCheckbox):checked").parent().parent().remove();  
            	}
            },
            'click .RoleOfCheck': function (e, value, row, index) {
            }
        }

