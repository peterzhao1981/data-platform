function splitDate(dateStr) {
	return dateStr.toString().substring(4,10)
}
function intDateToString(date) {
 	var strDate = date.toString();
 	//alert(date.toString());
 	var length = date.toString().length;
 	//alert(length )
 	var month = strDate.substring(4,6);
 	if(length = 8) {
 		var day = strDate.substring(6,8);
 	} else if (length == 6) {
 		var day = "";
 	};
 	if (month == "01") {
 		var result = "Jan" + " " + day;
 	} else if (month == "02") {
 		var result = "Feb" + " " + day;
 	} else if (month == "03") {
 		var result = "Mar" + " " + day;
 	} else if (month == "04") {
 		var result = "Apr" + " " + day;
 	} else if (month == "05") {
 		var result = "May" + " " + day;
 	} else if (month == "06") {
 		var result = "Jun" + " " + day;
 	} else if (month == "07") {
 		var result = "Jul" + " " + day;
 	} else if (month == "08") {
 		var result = "Aug" + " " + day;
 	} else if (month == "09") {
 		var result = "Sep" + " " + day;
 	} else if (month == "10") {
 		var result = "Oct" + " " + day;
 	} else if (month == "11") {
 		var result = "Nov" + " " + day;
 	} else if (month == "12") {
 		var result = "Dec" + " " + day;
 	};
 	return result;
}
google.charts.load("visualization", "1", {
    packages: ["corechart", "table", "geochart"]
  });
