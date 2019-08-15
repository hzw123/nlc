 
    //进度条
    
	    function LoadingBar(id)  
           {  
               this.loadbar = $("#" + id);  
               this.percentEle = $(".percent", this.loadbar);  
               this.percentNumEle = $(".percentNum", this.loadbar);  
               this.max = 100;  
               this.currentProgress = 0;  
           }  
           LoadingBar.prototype = {  
               constructor: LoadingBar,  
               setMax: function (maxVal)  
               {  
                   this.max = maxVal;  
               },  
               setProgress: function (val)  
               {  
                   if (val >= this.max)  
                   {  
                       val = this.max;  
                   }  
                   this.currentProgress = parseInt((val / this.max) * 40) + "%";  
                   this.percentEle.width(this.currentProgress);  
                   this.percentNumEle.text(this.currentProgress);  
      
      
               }  
           };  
               $(function ()  
         {  
      
             var loadbar = new LoadingBar("loadBar01");  
             var max = 1000;  
             loadbar.setMax(max);  
             var i = 0;  
             var time = setInterval(function ()  
             {  
                 loadbar.setProgress(i);  
                 if (i == max)  
                 {  
                     clearInterval(time);  
                     return;  
                 }  
                 i += 10;  
             }, 40);  
         });  
         




////////////table表格///////////////
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
		
        $('#tab-details').bootstrapTable({
            url: 'data2.json',         //请求后台的URL（*）
            method: 'get',                      //请求方式（*）
            toolbar: '#toolbar',                //工具按钮用哪个容器
            striped: true,                      //是否显示行间隔色
//          cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
            sortable: true,                     //是否启用排序
            sortOrder: "asc",                   //排序方式
//          queryParams: oTableInit.queryParams,//传递参数（*）
            sidePagination: "client",           //分页方式：client客户端分页，server服务端分页（*）
            pageNumber:1,                       //初始化加载第一页，默认第一页
            pageSize: 10,                       //每页的记录行数（*）
//          pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
            search: true,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            strictSearch: true,
            showColumns: true,                  //是否显示所有的列
//          showRefresh: true,                  //是否显示刷新按钮
            minimumCountColumns: 2,             //最少允许的列数
            clickToSelect: true,                //是否启用点击选中行
            height: 400,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: "ID",                     //每一行的唯一标识，一般为主键列
//          showToggle:true,                    //是否显示详细视图和列表视图的切换按钮
//          cardView: false,                    //是否显示详细视图
//          detailView: false,                   //是否显示父子表
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
                         visible: false
                     }, {
                         title: 'URL',
                         field: 'Name',
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
                         title: '已采集时间',
                         field: 'Time',
                         align: 'center',
                         valign: 'middle',
                         sortable: false,
                     },{
                         title: '当前下载速度',
                         field: 'AverageSpeed',
                         align: 'center',
                         valign: 'middle',
                         sortable: false,
                     },{
                         title: '已抓取文档数量',
                         field: 'AverageSpeed',
                         align: 'center',
                         valign: 'middle',
                         sortable: false,
                     },{
                         title: '剩余抓取文档数量',
                         field: 'Num',
                         align: 'center',
                         valign: 'middle',
                         sortable: false,
                     },{
                         title: '已抓取文档容量',
                         field: 'Schedule',
                         align: 'center',
                         valign: 'middle',
                         sortable: false,
                     },{
                         title: '采集进度',
                         field: 'CreatTime',
                         align: 'center',
                         valign: 'middle',
                         sortable: false,
                     }
                 ]
        });
        
    };
    


    //得到查询的参数
    oTableInit.queryParams = function (params) {
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            limit: params.limit,   //页面大小
            offset: params.offset,  //页码
            departmentname: $("#txt_search_departmentname").val(),
            statu: $(".code").val()
        };
        return temp;
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
                '<button type="button" class="RoleOfA btn btn-default  btn-sm" style="margin-right:15px;">编辑</button>',
                '<button type="button" class="RoleOfB btn btn-default  btn-sm" style="margin-right:15px;">暂停</button>',
                '<button type="button" class="RoleOfC btn btn-default  btn-sm" style="margin-right:15px;">终止</button>',
                '<button type="button" class="RoleOfEdit btn btn-default  btn-sm" style="margin-right:15px;">删除</button>',
                '<button type="button" class="RoleOfCheck btn btn-default  btn-sm" style="margin-right:15px;">检查点</button>',
                '<button type="button" class="RoleOfSee btn btn-default  btn-sm" style="margin-right:15px;">查看详情</button>'
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
//  // 搜索按钮触发事件
//	$(function() {
//		$("#eventquery").click(function() {
//			$('#eventTable').bootstrapTable(('refresh'));	// 很重要的一步，刷新url！
//			// console.log("/program/area/findbyItem?offset="+0+"&"+$("#areaform").serialize())
//			$('#eventqueryform input[name=\'eventName\']').val('')
//			$('#eventqueryform input[name=\'status\']').val('')
//			$('#eventqueryform input[name=\'location\']').val('')
//			$('#eventqueryform input[name=\'startdate\']').val('')
//			$('#eventqueryform input[name=\'enddate\']').val('')
//		});
//	 
//	});



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
        