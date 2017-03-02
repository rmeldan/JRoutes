$(document).ready($(function() {
	//footer
		$(function() {
			itemsPerPage(9);					
		});
		function itemsPerPage(perPage) {
			var items = $("#contentRoute tbody tr");
			items.slice(perPage).hide();
			items.hide().slice(0, perPage).show();
			$("#paginationRoute").pagination({
				items : items.length,
				itemsOnPage : perPage,
				prevText: '<< Попередня',
				nextText: 'Наступна >>',
				cssStyle : "light-theme",
				onPageClick : function(pageNumber) {
					var showFrom = perPage * (pageNumber-1);
					var showTo = perPage + showFrom;
					items.hide().slice(showFrom, showTo).show();
				}
			});			
		}
		
	//Country
		
	}));