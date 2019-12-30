//时间戳转具体日期
function timestampToTime(timestamp) {
	var date = new Date(timestamp); //时间戳为10位需*1000，时间戳为13位的话不需乘1000
	Y = date.getFullYear() + '-';
	M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
	D = (date.getDate() < 10 ? '0' + date.getDate() : date.getDate()) + ' ';
	h = (date.getHours() < 10 ? '0' + date.getHours() : date.getHours()) + ':';
	m = (date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes()) + ':';
	s = (date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds());
	return Y + M + D + h + m + s;
};

jQuery(function($) {
	//显示用户名
	$("#us_name").html($.cookie("us_name"));
});
jQuery(function($) {
	//显示用户名
	$("#us_name").html($.cookie("us_name"));
		$("#profile").attr("href", "/view/userInformation/teacherProfile.html?id=" + $.cookie("us_id"));
		$("#setting").attr("href", "/view/userInformation/teacherSetting.html");
});

