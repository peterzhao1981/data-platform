function orderCountCtrl($scope, $http) {

	//google.charts.load("visualization", "1", {packages:["corechart","table"]});
	$scope.type = 1;
	$scope.datepicker = 0;
	 //var start = $scope.start;
	 $scope.search = function() {
	 	//
	 	$scope.start = $scope.start.replace(/\s+/g,"");
	 	var textStartDate = $scope.start.substring(0,4) + "/" + $scope.start.substring(4,6) + "/" +$scope.start.substring(6,8);
	 	startIntDate = parseInt($scope.start);

	 	$scope.end = $scope.end.replace(/\s+/g,"");
	 	var textEndDate = $scope.end.substring(0,4) + "/" + $scope.end.substring(4,6) + "/" +$scope.end.substring(6,8);
	 	endDate = parseInt($scope.end);

	 	if (startIntDate > endDate) {
	 		alert("star date must smaller than end ndate")
	 		return;
	 	};
	 	var type = parseInt($scope.type);
	 	if (type == 3) {
	 		startIntDate = Math.floor(startIntDate/100);
	 		endDate = Math.floor(endDate/100);
	 	};
	 	$scope.getOrderCount(startIntDate, endDate, type);
	 	document.getElementById("strSearchDate").innerHTML= textStartDate + "-" + textEndDate;
	 }

	 $scope.initSearch = function () {
	 	$scope.searchLastDay(14);
	 }

	 $scope.getRadioValue = function() {
      	if ($scope.datepicker == 5) {
        	$scope.search();
      	};
  	}

	 $scope.searchLastDay = function( count ) {
	 	// init search date the last day to last 15 day
	 	var myDate = new Date();

	 	var startDate = new Date(myDate.getTime() - count * 24 * 3600 * 1000);
	 	
	 	var startMonth = startDate.getMonth() + 1;

	 	if (startMonth < 10) {
	 		var startMonthStr = "0" + startMonth.toString();
	 	} else {
	 		startMonthStr = startMonth.toString();
	 	}
	 	var startDay = startDate.getDate();
	 	if (startDay < 10) {
	 		var startDayStr = "0" + startDay.toString();
	 	} else {
	 		startDayStr = startDay.toString();
	 	};

	 	startDateStr = startDate.getFullYear().toString() + startMonthStr + startDayStr;
	 	var textStartDate = startDate.getFullYear().toString() + "/" + startMonthStr + "/" +startDayStr;

	 	startIntDate = parseInt(startDateStr);


	 	var month = myDate.getMonth() + 1;
	 	if (month < 10) {
	 		var monthStr = "0" + month.toString();
	 	} else {
	 		monthStr = month.toString();
	 	}
	 	var day = myDate.getDate();
	 	if (day < 10) {
	 		var dayStr = "0" + day.toString();
	 	} else {
	 		dayStr = day.toString();
	 	}
	 	dateStr = myDate.getFullYear().toString()+monthStr+dayStr;
	 	var textEndDate = myDate.getFullYear().toString() + "/" + monthStr + "/" +dayStr;

	 	endDate = parseInt(dateStr);
	 	document.getElementById("strSearchDate").innerHTML= textStartDate + "-" + textEndDate;

	 	$scope.getOrderCount(startIntDate, endDate, $scope.type);
	}

	$scope.setType = function(type) {
		$scope.type = type;
		
		if ($scope.datepicker == 1) {
			$scope.searchLastDay(7);
		} else if ($scope.datepicker == 2) {
			$scope.searchLastDay(30);
		} else if ($scope.datepicker == 3) {
			$scope.searchLastDay(90);
		} else if ($scope.datepicker == 4) {
			$scope.searchLastDay(365);
		} else if ($scope.datepicker == 5) {
			$scope.search();
		} else if ($scope.datepicker == 0) {
			$scope.searchLastDay(14);
		}
	}
	 

	$scope.getOrderCount = function(startDate, endDate, type) {
		var req = {
			method:"GET",
			url : API_URI_ENDPOINT + "/stats?startDate=" + startDate + "&endDate=" + endDate + "&type=" + type + "&query=user",
		}
		var space = type;
		$http(req)
		.success(function(response){
			//$scope.realTimedata = response;
			if (response.code == undefined) {
				$scope.data = response;
				var length = $scope.data.length;
				var total = 0; 
				for (var i = length - 1; i >= 0; i--) {
					total += $scope.data[i].order;
				};
				var avg = (total/length).toFixed(2);
				document.getElementById("order").innerHTML = total;
				document.getElementById("avg").innerHTML = avg;
					
				google.charts.setOnLoadCallback(drawLineChart(space));
				
				google.charts.setOnLoadCallback(drawTable);
			} else {
				document.getElementById("chart_div1").innerHTML = "get data error";
				document.getElementById("table_div").innerHTML = "get data error";
			}
			
			
		})
	}
	function drawLineChart(space) {
	 	var length = $scope.data.length;
	 
	 // 	var data = google.visualization.arrayToDataTable([
		//  ['Month', 'Order'],
		//  [intDateToString($scope.data[0].date),$scope.data[0].order]
		// ]);
		var data = new google.visualization.DataTable();
		data.addColumn('string', 'Month');
		data.addColumn('number', 'orderCount');
	    if (space == 1) {
	    	for(var i = 0;i < length; i++) {
	      		if (i%2 == 0) {
	      			data.addRows([[intDateToString($scope.data[i].date),$scope.data[i].order]]);
	      		} else {
	      			data.addRows([["",$scope.data[i].order]]);
	      		}
		    }
	    } else {
	    	for(var i = o;i < length; i++) {
	      		data.addRows([[intDateToString($scope.data[i].date),$scope.data[i].order]]);
		    }
	    }
	     var options = {
			height: 300,
			//title : '                 User Active                        ',
			vAxis: {title: 'Number of Active Users'},
			hAxis: {title: 'Date'},
			legend : 'none',
			interpolateNulls : 'true',
			};
        var chart = new google.visualization.LineChart(document.getElementById('chart_div1'));
        chart.draw(data, options);
      }
      $scope.initSearch();
      
      //google.charts.load('current', {'packages':['table']});
      function drawTable() {
      	var cssClassNames = {
	      'tableCell': 'center-text',
	      'headerCell': 'center-text'
	    };
        var data = new google.visualization.DataTable();
        data.addColumn('string', 'Date');
        data.addColumn('number', 'Order Count');
        
        data.addRows([
          [intDateToString($scope.data[0].date),  $scope.data[0].order],
        ]);
        var length = $scope.data.length;
        for(var i = 1;i < length; i++) {
      		data.addRows([[intDateToString($scope.data[i].date),$scope.data[i].order]]);
	    }

        var table = new google.visualization.Table(document.getElementById('table_div'));

        table.draw(data, {showRowNumber: false, width: '100%', height: '100%','cssClassNames': cssClassNames,interpolateNulls : 'true',});
      }
          

      $scope.jsonToCSV = function() {
  		var length = $scope.data.length;
  		var data = [];
  		for (var i = 0; i < length; i++) {
  			var json = {};
  			json.date = $scope.data[i].date;
  			json.order = $scope.data[i].order;
  			data.push(json);
  		};
		JSONToCSVConvertor(data, "OrderCount", true);
  		//JSONToExcelConvertor(date, "Report", $scope.title);
      }

	  var watch = $scope.$watch('datepicker',function(newValue,oldValue, scope){
	        if (newValue == 5) {
	        	document.getElementById("datepicker").style.display = 'block';
	        } else if (newValue == 1) {
	        	$scope.searchLastDay(7);
	        	document.getElementById("datepicker").style.display = 'none';
	        } else if (newValue == 2) {
	        	$scope.searchLastDay(30);
	        	document.getElementById("datepicker").style.display = 'none';
	        } else if (newValue == 3) {
	        	$scope.searchLastDay(90);
	        	document.getElementById("datepicker").style.display = 'none';
	        } else if (newValue == 4) {
	        	$scope.searchLastDay(365);
	        	document.getElementById("datepicker").style.display = 'none';
	        } 
		});
}


angular
    .module('modeApp')
    .controller('orderCountCtrl', orderCountCtrl);