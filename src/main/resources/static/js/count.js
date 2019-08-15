
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('count'));    
		var option = {
		    tooltip : {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c} ({d}%)"
		    },
		    
		    legend: {
		        orient : 'vertical',
		        x : 'left',
		        data:['科学技术','民俗文化','环境保护','治国理政','名俗文化','治国理政','环境保护','其他']
		    },
		    toolbox: {
		        show : true,
		        feature : {
		            mark : {show: true},
		            dataView : {show: true, readOnly: false},
		            magicType : {
		                show: true, 
		                type: ['pie', 'funnel']
		            },
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    calculable : false,
		    series : [
		
		        {
		            name:'访问来源',
		            type:'pie',
		            radius : [100, 140],
		            
		            // for funnel
		            x: '60%',
		            width: '35%',
		            funnelAlign: 'left',
		            max: 1048,
		            
		            data:[
		                {value:335, name:'环境保护'},
		                {value:310, name:'治国理政'},
		                {value:234, name:'名俗文化'},
		                {value:135, name:'视频广告'},
		                {value:1048, name:'民俗文化'},
		                {value:251, name:'治国理政'},
		                {value:147, name:'环境保护'},
		                {value:102, name:'其他'}
		            ]
		        }
		    ],
		    color:['#e5cf0d', '#97b552','#95706d','#dc69aa','#2ec7c9','#ffb980','#d87a80','#8d98b3']
		};
		
//var ecConfig = require('echarts/config');
//myChart.on(ecConfig.EVENT.PIE_SELECTED, function (param){
//  var selected = param.selected;
//  var serie;
//  var str = '当前选择： ';
//  for (var idx in selected) {
//      serie = option.series[idx];
//      for (var i = 0, l = serie.data.length; i < l; i++) {
//          if (selected[idx][i]) {
//              str += '【系列' + idx + '】' + serie.name + ' : ' + 
//                     '【数据' + i + '】' + serie.data[i].name + ' ';
//          }
//      }
//  }
//  document.getElementById('wrong-message').innerHTML = str;
//})
//                  

        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
  
  
  
  
  
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('count_r'));    
		var option = {
		    tooltip : {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c} ({d}%)"
		    },
		    legend: {
		        orient : 'vertical',
		        x : 'left',
		        data:['科学技术','民俗文化','环境保护','治国理政','名俗文化','治国理政','环境保护','其他']
		    },
		    toolbox: {
		        show : true,
		        feature : {
		            mark : {show: true},
		            dataView : {show: true, readOnly: false},
		            magicType : {
		                show: true, 
		                type: ['pie', 'funnel']
		            },
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    calculable : false,
		    series : [
		
		        {
		            name:'访问来源',
		            type:'pie',
		            radius : [100, 140],
		            
		            // for funnel
		            x: '60%',
		            width: '35%',
		            funnelAlign: 'left',
		            max: 1048,
		            
		            data:[
		                {value:335, name:'环境保护'},
		                {value:310, name:'治国理政'},
		                {value:234, name:'名俗文化'},
		                {value:135, name:'视频广告'},
		                {value:1048, name:'民俗文化'},
		                {value:251, name:'治国理政'},
		                {value:147, name:'环境保护'},
		                {value:102, name:'其他'}
		            ]
		        }
		    ],
		    color:['#e5cf0d', '#97b552','#95706d','#dc69aa','#2ec7c9','#ffb980','#d87a80','#8d98b3']
		};
		
//var ecConfig = require('echarts/config');
//myChart.on(ecConfig.EVENT.PIE_SELECTED, function (param){
//  var selected = param.selected;
//  var serie;
//  var str = '当前选择： ';
//  for (var idx in selected) {
//      serie = option.series[idx];
//      for (var i = 0, l = serie.data.length; i < l; i++) {
//          if (selected[idx][i]) {
//              str += '【系列' + idx + '】' + serie.name + ' : ' + 
//                     '【数据' + i + '】' + serie.data[i].name + ' ';
//          }
//      }
//  }
//  document.getElementById('wrong-message').innerHTML = str;
//})
//                  

        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
 
	
	
	

// 基于准备好的dom，初始化echarts实例
var myChart = echarts.init(document.getElementById('count_t_l'));  
var 	option = {
//  title : {
//      text: '浏览器占比变化',
//      subtext: '纯属虚构',
//      x:'right',
//      y:'bottom'
//  },
    tooltip : {
        trigger: 'item',
        formatter: "{a} <br/>{b} : {c} ({d}%)"
    },
    legend: {
        orient : 'vertical',
        x : 'left',
        data:['民俗文化','环境保护','科学技术','治国理政','等等','其他']
    },
    toolbox: {
        show : true,
        feature : {
            mark : {show: true},
            dataView : {show: true, readOnly: false},
            restore : {show: true},
            saveAsImage : {show: true}
        }
    },
    calculable : false,
    series : (function (){
        var series = [];
        for (var i = 0; i < 30; i++) {
            series.push({
                name:'用户偏爱',
                type:'pie',
                itemStyle : {normal : {
                    label : {show : i > 28},
                    labelLine : {show : i > 28, length:20}
                }},
                radius : [i * 4 + 40, i * 4 + 43],
                data:[
                    {value: i * 128 + 80,  name:'民俗文化'},
                    {value: i * 64  + 160,  name:'环境保护'},
                    {value: i * 32  + 320,  name:'科学技术'},
                    {value: i * 16  + 640,  name:'治国理政'},
                    {value: i * 8  + 1280, name:'等等'},
                     {value: i * 8  + 1280, name:'其他'}
                ],
                 color:['#4c8be3', '#5798f3','#038cc4','#1c7099','#88b0bb','#99d2dd']
            })
        }
        series[0].markPoint = {
            symbol:'emptyCircle',
            symbolSize:series[0].radius[0],
            effect:{show:true,scaleSize:12,color:'rgba(250,225,50,0.8)',shadowBlur:10,period:30},
            data:[{x:'50%',y:'50%'}]
        };
        return series;
    })()
};
          // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
 
	
	
	
	
	
	
	
	


// 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('count_t_r'));  
		var option = {
		    title : {
//		        text: '南丁格尔玫瑰图',
//		        subtext: '纯属虚构',
//		        x:'center'
		    },
		    tooltip : {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c} ({d}%)"
		    },
//		    legend: {
//		        x : 'center',
//		        y : 'bottom',
//		        data:['类别名称','类别名称','类别名称','类别名称','类别名称','类别名称','类别名称','类别名称']
//		    },
		    toolbox: {
		        show : true,
		        feature : {
		            mark : {show: true},
		            dataView : {show: true, readOnly: false},
		            magicType : {
		                show: true, 
		                type: ['pie', 'funnel']
		            },
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    calculable : true,
		    series : [
		        {
		            name:'半径模式',
		            type:'pie',
		            radius : [20, 110],
		            center : ['25%', 200],
		            roseType : 'radius',
		            width: '40%',       // for funnel
		            max: 40,            // for funnel
		            itemStyle : {
		                normal : {
		                    label : {
		                        show : false
		                    },
		                    labelLine : {
		                        show : false
		                    }
		                },
		                emphasis : {
		                    label : {
		                        show : true
		                    },
		                    labelLine : {
		                        show : true
		                    }
		                }
		            },
		            data:[
		                {value:10, name:'1111'},
		                {value:5, name:'22222'},
		                {value:15, name:'33333'},
		                {value:25, name:'4444'},
		                {value:20, name:'55555'},
		                {value:35, name:'66666'},
		                {value:30, name:'77777'},
		                {value:40, name:'88888'}
		            ]
		        },
		        {
		            name:'面积模式',
		            type:'pie',
		            radius : [30, 110],
		            center : ['75%', 200],
		            roseType : 'area',
		            x: '50%',               // for funnel
		            max: 40,                // for funnel
		            sort : 'ascending',     // for funnel
		            data:[
		                {value:10, name:'类别名称'},
		                {value:5, name:'类别名称2'},
		                {value:15, name:'类别名称3'},
		                {value:25, name:'类别名称4'},
		                {value:20, name:'类别名称5'},
		                {value:35, name:'类别名称6'},
		                {value:30, name:'类别名称8'},
		                {value:40, name:'类别名称7'}
		            ]
		        }
		    ],
		    color:['#afd6dd', '#75abd0','#038cc4','#1c7099','#88b0bb','#99d2dd','#1bb2d8','#1790cf']
		};
          // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
 









// 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('count_s'));  

var labelTop = {
    normal : {
        label : {
            show : true,
            position : 'center',
            formatter : '{b}',
            textStyle: {
                baseline : 'bottom'
            }
        },
        labelLine : {
            show : false
        }
    }
};
var labelFromatter = {
    normal : {
        label : {
            formatter : function (params){
                return 100 - params.value + '%'
            },
            textStyle: {
                baseline : 'top'
            }
        }
    },
}
var labelBottom = {
    normal : {
        color: '#ccc',
        label : {
            show : true,
            position : 'center'
        },
        labelLine : {
            show : false
        }
    },
    emphasis: {
        color: 'rgba(0,0,0,0)'
    }
};
var radius = [40, 55];
option = {
    legend: {
        x : 'center',
        y : 'center',
        data:[
            '合作项目','海陆统筹','科技理念','区域合作','框架思路'
        ]
    },
//  title : {
//      text: 'The App World',
//      subtext: 'from global web index',
//      x: 'center'
//  },
    toolbox: {
        show : true,
        feature : {
            dataView : {show: true, readOnly: false},
            magicType : {
                show: true, 
                type: ['pie', 'funnel'],
                option: {
                    funnel: {
                        width: '20%',
                        height: '30%',
                        itemStyle : {
                            normal : {
                                label : {
                                    formatter : function (params){
                                        return 'other\n' + params.value + '%\n'
                                    },
                                    textStyle: {
                                        baseline : 'middle'
                                    }
                                }
                            },
                        } 
                    }
                }
            },
            restore : {show: true},
            saveAsImage : {show: true}
        }
    },
    series : [
        {
            type : 'pie',
            center : ['10%', '30%'],
            radius : radius,
            x: '0%', // for funnel
            itemStyle : labelFromatter,
            data : [
                {name:'other', value:46, itemStyle : labelBottom},
                {name:'合作项目', value:54,itemStyle : labelTop}
            ]
        },
        {
            type : 'pie',
            center : ['30%', '30%'],
            radius : radius,
            x:'20%', // for funnel
            itemStyle : labelFromatter,
            data : [
                {name:'other', value:56, itemStyle : labelBottom},
                {name:'海陆统筹', value:44,itemStyle : labelTop}
            ]
        },
        {
            type : 'pie',
            center : ['50%', '30%'],
            radius : radius,
            x:'40%', // for funnel
            itemStyle : labelFromatter,
            data : [
                {name:'other', value:65, itemStyle : labelBottom},
                {name:'科技理念', value:35,itemStyle : labelTop}
            ]
        },
        {
            type : 'pie',
            center : ['70%', '30%'],
            radius : radius,
            x:'60%', // for funnel
            itemStyle : labelFromatter,
            data : [
                {name:'other', value:70, itemStyle : labelBottom},
                {name:'区域合作', value:30,itemStyle : labelTop}
            ]
        },
        {
            type : 'pie',
            center : ['90%', '30%'],
            radius : radius,
            x:'80%', // for funnel
            itemStyle : labelFromatter,
            data : [
                {name:'other', value:73, itemStyle : labelBottom},
                {name:'框架思路', value:27,itemStyle : labelTop}
            ]
        },
       
    ],
    color:['#ff7f50', '#87cefa','#da70d6','#32cd32','#6495ed']
};
                       // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
 







