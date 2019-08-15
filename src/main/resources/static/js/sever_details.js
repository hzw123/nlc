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
            url: '/api/serverThreads',         //请求后台的URL（*）
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
                         field: 'Id',
                         align: 'center',
                         valign: 'middle',
                         formatter: function (value, row, index) {  
                     	    return index+1;  
                         },
                         sortable: false,
                     }, {
                         title: '线程',
                         field: 'name',
                         align: 'center',
                         valign: 'middle',
                         sortable: false,
                     },{
                         title: '采集任务名称',
                         field: 'task.name',
                         align: 'center',
                         valign: 'middle',
                         sortable: false,
                     },{
                         title: '任务ID',
                         field: 'Id',
                         align: 'center',
                         valign: 'middle',
                         sortable: false,
                     },{
                         title: '状态',
                         field: 'State',
                         align: 'center',
                         valign: 'middle',
                         sortable: false,
                     },{
                         title: '采集速度',
                         field: 'Time',
                         align: 'center',
                         valign: 'middle',
                         sortable: false,
                     },{
                         title: '采集进度',
                         field: 'Severs',
                         align: 'center',
                         valign: 'middle',
                         sortable: false,
                     },{
                         field: 'Desc',
                         title: '操作',
                         align: 'center',
                         events: 'operateEvents',
                         formatter: 'operateFormatter'
                     }
                 ]
        });
        
    };
    
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
                '<button type="button" class="RoleOfCheck btn btn-default  btn-sm" style="margin-right:15px;">移动任务</button>',
                '<button type="button" class="RoleOfSee btn btn-default  btn-sm" style="margin-right:15px;">删除线程</button>'
            ].join('');
        }
    
    window.operateEvents = {
            'click .RoleOfA': function (e, value, row, index) {
                alert("A");            
         },
            'click .RoleOfB': function (e, value, row, index) {
                alert("B");            
         },
          'click .RoleOfC': function (e, value, row, index) {
                alert("C");            
         },
            'click .RoleOfEdit': function (e, value, row, index) {
                }
            }



$(function(){
	$("#search").keyup(function(){
02
	        //过滤空
03
	        var keyword = $(this).val().toLowerCase().replace(/(^\s*)|(\s*$)/g, "");
04
	        if (keyword) {
05
	            //使用正则
06
	            var reg = new RegExp(keyword,'i');
07
	            //遍历option
08
	            $('option').each(function(key,val){
09
	                //定义要搜索的字符串
10
	                var ref = $(this).attr('ref');
11
	                //如果搜到，设置select值
12
	                if(ref && reg.test( ref ) ){
13
	                    $("select").val($(val).val());
14
	                    return false;
15
	                }
16
	            });//end each
17
	        }//end if keyword
18
	    });
})
        