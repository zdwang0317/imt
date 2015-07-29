<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(function() {
		$.messager.progress();
		$.ajax({
			type : 'post',
			dataType : 'json',
			url : "wipAction!showHighChart.action",
			success : function(r) {
				console.info(r.msg);
				generateChart('container',r.dataList,r.msg);
				generateSpecialChart(r.dataList,r.msg);
			}
		});
		$.ajax({
			type : 'post',
			dataType : 'json',
			url : "wipAction!showLogicHighChart.action",
			success : function(r) {
				generateLogicChart(r.dataList,r.msg);
			}
		});
		$.messager.progress('close');
	});
	
	function generateSpecialChart(list,msg){
		var container2 = new Array();
		var container21 = new Array();
		var container3 = new Array();
		var container4 = new Array();
		var container5 = new Array();
		var container6 = new Array();
		var container7 = new Array();
		var container8 = new Array();
		var container9 = new Array();
		var container10 = new Array();
		for(var i=0;i<list.length;i++){
			var name = list[i].name;
			if(name.indexOf('5129')>=0||name.indexOf('5096')>=0||name.indexOf('4661')>=0){
				container2.push(list[i]);
			}
			if(name.indexOf('W064')>=0||name.indexOf('W085')>=0){
				container21.push(list[i]);
			}
			if(name.indexOf('4624')>=0||name.indexOf('4849')>=0||name.indexOf('4220')>=0||name.indexOf('4421')>=0||name.indexOf('W041')>=0){
				container3.push(list[i]);
			}
			if(name.indexOf('W081')>=0||name.indexOf('3919')>=0||name.indexOf('4083')>=0||name.indexOf('3919')>=0||name.indexOf('4902')>=0||name.indexOf('4997')>=0||name.indexOf('5393')>=0){
				container4.push(list[i]);
			}
			if(name.indexOf('5081')>=0||name.indexOf('5560')>=0||name.indexOf('5684')>=0){
				container5.push(list[i]);
			}
			if(name.indexOf('4395')>=0){
				container6.push(list[i]);
			}
			if(name.indexOf('5193')>=0||name.indexOf('5829')>=0||name.indexOf('6261')>=0||name.indexOf('6131')>=0){
				container7.push(list[i]);
			}
			if(name.indexOf('4363')>=0||name.indexOf('4464')>=0||name.indexOf('4470')>=0||name.indexOf('3801')>=0||name.indexOf('4418')>=0||name.indexOf('5659')>=0||name.indexOf('5498')>=0||name.indexOf('5886')>=0||name.indexOf('5589')>=0){
				container8.push(list[i]);
			}
			if(name.indexOf('0695')>=0||name.indexOf('1075')>=0){
				container9.push(list[i]);
			}
			if(name.indexOf('0017')>=0||name.indexOf('M022')>=0){
				container10.push(list[i]);
			}
		}
		generateChart('container2',container2,msg);
		generateChart('container21',container21,msg);
		generateChart('container3',container3,msg);
		generateChart('container4',container4,msg);
		generateChart('container5',container5,msg);
		generateChart('container6',container6,msg);
		generateChart('container7',container7,msg);
		generateChart('container8',container8,msg);
		generateChart('container9',container9,msg);
		generateChart('container10',container10,msg);
	}
	
	function generateChart(id,list,msg){
		$('#'+id+'').highcharts({
			credits : {enabled:false},
            chart: {
                /* type: 'bar', */
                type: 'column',
            },
            title: {
                text: 'Wip Analysis Daily'
            },
            /* subtitle: {
                text: 'Source: WorldClimate.com'
            }, */
            xAxis: {
                categories: msg.split(','),
                title :{
                	text : 'WIP Flow'
                },
                labels: {
                    rotation: -90,
                    align: 'right',
                    style: {font: 'bold 8px 微软雅黑'}
                }
            },
            yAxis: {
                min: 0,
                title: {
                    text: 'Wip Quantity'
                },
                labels: {
                    overflow: 'justify'
                }
                /* opposite:true */
            },
            legend: {
            	   labelFormatter: function() {
            	      var total = 0;
            	      for(var i=this.yData.length; i--;) { total += this.yData[i]; };
            	      return this.name + '——'+total;
            	   },
            	   itemWidth : 150
            },
            tooltip: {
                headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                    '<td style="padding:0"><b>{point.y:.1f} </b></td></tr>',
                footerFormat: '</table>',
                shared: true,
                useHTML: true
            },
            plotOptions: {
                column: {
                    pointPadding: 0.2,
                    borderWidth: 0/* ,
                     dataLabels: {
                        enabled: true,
                        formatter: function() {  
                            if (this.y > 0)  
                                return this.y;
                        },
                        rotation: -90,
                    } */
                }/* ,
                series: {
                    events: {
                        show: redrawXaxis,
                        hide: redrawXaxis
                    }
                } */
            },
            series: formatDataList(list)
        });
	}
	
	function generateLogicChart(list,msg){
		$('#logic_container').highcharts({
			credits : {enabled:false},
            chart: {
                /* type: 'bar', */
                type: 'column',
            },
            title: {
                text: 'Wip Analysis Daily'
            },
            /* subtitle: {
                text: 'Source: WorldClimate.com'
            }, */
            xAxis: {
                categories: msg.split(','),
                title :{
                	text : 'WIP Flow'
                },
                labels: {
                    rotation: -90,
                    align: 'right',
                    style: {font: 'bold 8px 微软雅黑'}
                }
            },
            yAxis: {
                min: 0,
                title: {
                    text: 'Wip Quantity'
                },
                labels: {
                    overflow: 'justify'
                }
                /* opposite:true */
            },
            legend: {
            	   labelFormatter: function() {
            	      var total = 0;
            	      for(var i=this.yData.length; i--;) { total += this.yData[i]; };
            	      return this.name + '——'+total;
            	   },
            	   itemWidth : 150
            },
            tooltip: {
                headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                    '<td style="padding:0"><b>{point.y:.1f} </b></td></tr>',
                footerFormat: '</table>',
                shared: true,
                useHTML: true
            },
            plotOptions: {
                column: {
                    pointPadding: 0.2,
                    borderWidth: 0/* ,
                     dataLabels: {
                        enabled: true,
                        formatter: function() {  
                            if (this.y > 0)  
                                return this.y;
                        },
                        rotation: -90,
                    } */
                }/* ,
                series: {
                    events: {
                        show: redrawXaxis,
                        hide: redrawXaxis
                    }
                } */
            },
            series: formatDataList(list)
        });
	}
	
	function redrawXaxis() {

        if (typeof this.chart !== "undefined") {
            var visibleSeries = $.grep(this.chart.series, function (e) {
                return e.visible == true
            });

            if (visibleSeries.length < this.chart.series.length && visibleSeries.length !== 0) {
                var arrOfIndexes = new Array();
                var mins = new Array();
                var maxs = new Array();
                for (var i = 0; i < this.chart.xAxis[0].series.length; i++) {
                    if (this.chart.xAxis[0].series[i].visible == true) {
                        for (var j = 0; j < this.chart.xAxis[0].series[i].yData.length; j++) {
                            if (this.chart.xAxis[0].series[i].yData[j] != null&&this.chart.xAxis[0].series[i].yData[j]>0) {
                                arrOfIndexes.push(j);
                            }
                        }
                        if (arrOfIndexes.length != 0) {
                            mins.push(Math.min.apply(Math, arrOfIndexes));
                            maxs.push(Math.max.apply(Math, arrOfIndexes));
                        }
                    }
                }
                if (mins.length > 0) {
                    var minOfMin = Math.min.apply(Math, mins);
                    var maxOfMax = Math.max.apply(Math, maxs);
                }
                this.chart.xAxis[0].setExtremes(minOfMin, maxOfMax);
            } else {
                //reset extremes
                extremes = this.chart.xAxis[0].getExtremes();
                this.chart.xAxis[0].setExtremes(extremes.dataMin, extremes.dataMax);
            }
        }

    };
	function formatDataList(list){
		var returnData =[];
		for(var i=0;i<list.length;i++){
			var obj = list[i];
			returnData[i] = {name:obj.name,data:obj.data,visible:obj.visible};
		}
		return returnData;
	}
	
</script>
<div title="Sub Tabs" style="height:750px;width:100%">
	<div class="easyui-tabs" data-options="border: false,fit:true,plain:true">
		<div title="NOR Flash" style="padding:5px;">
			<div id ="maincontainer" style="height: 700px;width:1100">
				<div class="easyui-tabs" data-options="border: false,fit:true,plain:true">
					<div title="All Groups" style="padding:5px;"><div id ="container" style="height: 650px;width:1100px"></div></div>
					<div title="XMC-65-1.8V" style="padding:5px;"><div id ="container2" style="height: 650px;width:1100px"></div></div>
					<div title="XMC-65-3.3V" style="padding:5px;"><div id ="container21" style="height: 650px;width:1100px"></div></div>
					<div title="XMC-90-1.8V" style="padding:10px;"><div id ="container3" style="height: 650px;width:1100px"></div></div>
					<div title="XMC-90-3.3V" style="padding:10px;"><div id ="container4" style="height: 650px;width:1100px"></div></div>
					<div title="SMIC-65-1.8V" style="padding:10px;"><div id ="container5" style="height: 650px;width:1100px"></div></div>
					<div title="SMIC-90-1.8V" style="padding:10px;"><div id ="container6" style="height: 650px;width:1100px"></div></div>
					<div title="SMIC-65-3.3V" style="padding:10px;"><div id ="container7" style="height: 650px;width:1100px"></div></div>
					<div title="SMIC-90-3.3V" style="padding:10px;"><div id ="container8" style="height: 650px;width:1100px"></div></div>
					<div title="CSMC-130-3.3V" style="padding:10px;"><div id ="container9" style="height: 650px;width:1100px"></div></div>
					<div title="HLMC-65-1.8V" style="padding:10px;"><div id ="container10" style="height: 650px;width:1100px"></div></div>
				</div>
			</div>
		</div>
		<div title="LOGIC" style="padding:5px;">
			<div id ="maincontainer2" style="height: 700px;width:1100">
				<div title="All Groups" style="padding:5px;"><div id ="logic_container" style="height: 650px;width:1100px"></div></div>
			</div>
		</div>
	</div>
	
</div>

