$.ajaxSetup({
	beforeSend: function(jqXHR, settings){
		if (settings.type === 'POST')
		{
			$.ajax({
				async:false,
				beforeSend: function(){
					
				},
				url:'/getCSRFToken.do',
				success:function(d){
					if (settings.url.indexOf('?') === -1)
					{
						settings.url += '?struts.token.name=token&token=' + d.token;
					}
					else
					{
						settings.url += '&struts.token.name=token&token=' + d.token;
					}
				}
			});
		}
	}
})