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
            url: '/api/users',         //请求后台的URL（*）
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
                         title: '用户名',
                         field: 'name',
                         align: 'center',
                         valign: 'middle',
                         sortable: false,
                     },{
                         title: '机构',
                         field: 'organization.name',
                         align: 'center',
                         valign: 'middle',
                         sortable: false,
                     },{
                         title: '用户代码',
                         field: 'code',
                         align: 'center',
                         valign: 'middle',
                         sortable: false,
                     },{
                         title: '昵称',
                         field: 'nickName',
                         align: 'center',
                         valign: 'middle',
                         sortable: false,
                     },{
                         title: '邮箱',
                         field: 'email',
                         align: 'center',
                         valign: 'middle',
                         sortable: false,
                     },{
                         title: '角色',
                         field: 'roles',
                         align: 'center',
                         valign: 'middle',
                         formatter: function (value, row, index) { 
                        	var l=value.length;
                    		var s="";
                    		for(var i=0;i<l;i++){
                    			s+=value[i].name+" ，";
                    		}
                    		s=s.substring(0,s.length-2);
                    		return s;
                         },
                         sortable: false,
                     },{
                         title: '账号状态',
                         field: 'status',
                         align: 'center',
                         valign: 'middle',
                         sortable: false,
                     },{
                         title: '创建时间',
                         field: 'createAt',
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
                roleId: $("#level_box").val(),
                nickName:$(".nc").val(),
                email:$(".yx").val()
        };
        return {"name":temp.name,"size":temp.size,"page":temp.page,"status":temp.status,"roleId":temp.roleId,"status":temp.status,"nickName":temp.nickName,"email":temp.email};
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
	    var data="";
	    if(row.status=='正常'){
	    	data="停用";
	    }else{
	    	data="开启";
	    }
		return [
            '<button type="button" class="RoleOfA btn btn-default  btn-link" style="margin-right:15px;">'+data+'</button>',
            '<button type="button" class="RoleOfB btn btn-default  btn-link" style="margin-right:15px;">编辑用户</button>',
            '<button type="button" class="RoleOfC btn btn-default  btn-link" style="margin-right:15px;">删除</button>'
        ].join('');
            
     }
    
    window.operateEvents = {
            'click .RoleOfA': function (e, value, row, index) {
                if(window.confirm("是否继续？")){
                	var id=row.id;
                	$.ajax({
                		url:"/api/users/"+id+"/upStatus",
            			data:{},
            			type:"put",
            			dataType:"json",
            			success:function(result){
            				$("#tab-list").bootstrapTable('updateRow',{index:index,row:{status:result.status}});
            			},
            			error(){
            				alert("操作失败");
            			}
                	});
                }            
         },
            'click .RoleOfB': function (e, value, row, index) {
                alert("B");            
         },
          'click .RoleOfC': function (e, value, row, index) {
        	  if(window.confirm("是否删除？")){
        		  var id=row.id;
        		  $.ajax({
              		url:"/api/users/"+id,
          			data:{},
          			type:"delete",
          			dataType:"json",
          			success:function(result){
          				$(":checkbox:not(#allCheckbox):checked").parent().parent().remove();
          			},
          			error(){
          				alert("操作失败");
          			}
              	});
        	  }
         }
      }
        