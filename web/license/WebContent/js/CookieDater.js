function createDate(d) {

	function zeroPrep(value, padding) {
		var zeroes = new Array(padding + 1).join("0");
		return (zeroes + value).slice(-padding);
	}

	year = d.getUTCFullYear();
	month = d.getUTCMonth();
	wDay = d.getUTCDay();
	day = zeroPrep(d.getUTCDate(), 2);
	hour = zeroPrep(d.getUTCHours(), 2);
	minutes = zeroPrep(d.getUTCMinutes(), 2);
	seconds = zeroPrep(d.getUTCSeconds(), 2);

	var date = {
		"year" : year,
		"month" : month,
		"wDay" : wDay,
		"day" : day,
		"hour" : hour,
		"minutes" : minutes,
		"seconds" : seconds
	};

	return date;
};

function createExp(d) {
//	var day31Months = [ 0, 2, 4, 6, 7, 9, 11 ];
//	var day30Months = [ 3, 5, 8, 10 ];
	var weekDay = [ "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" ];
	var months = [ "Jan", "Fb", "Mar", "Apr", "May", "Jun", "Jul", "Aug",
			"Sep", "Oct", "Nov", "Dec" ];

	var month = this.month;
	var day = this.day;
	var year = this.year;
	var wDay = this.wDay;

//	function leapYear(y) {
//		return ((y % 4 == 0) && (y % 100 != 0)) || (y % 400 == 0);
//	}
//
//	// If Saturday, make Sunday
//	if (wDay == 6) {
//		wDay = 0;
//	}
//	// Else, increment
//	else
//		wDay = wDay++;
//
//	// If a 31 day month
//	if (day31Months.indexOf(month) > -1) {
//		// If the 31st
//		if (day == 31) {
//			// If December the 31st, Make Janurary 1st, Increment year
//			if (month == 11) {
//				month = 1;
//				day = 1;
//				year++;
//			}
//			// Else, increment month and set day to 1st
//			else {
//				month++;
//				day = 1;
//			}
//		}
//		// Any other day but 31st
//		else {
//			day++;
//		}
//	}
//	// If a 30 day month
//	else if (day30Months.indexOf(d.month) > -1) {
//		// If the 30th
//		if (day == 30) {
//			month = month++;
//			day = 1;
//		}
//		// Else, not the 31st
//		else {
//			day++;
//		}
//	}
//	// If February
//	else {
//		// if the 28th
//		if (day == 28) {
//			// check for leap year, if true
//			if (leapYear(year)) {
//				day++;
//			}
//			// If not
//			else {
//				day = 1;
//				month++;
//			}
//		}
//		// if not the 28
//		else {
//			day++;
//		}
//	}

	var UTCStr = weekDay[wDay] + ", " + day + " " + months[month] + " " + year
			+ " " + d.hour + ":" + d.minutes + ":" + d.seconds + " UTC";
	console.log(UTCStr);
	return UTCStr;
};