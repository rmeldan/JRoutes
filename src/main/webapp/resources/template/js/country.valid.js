/*functions for uniq  country check*/
function isDuplicate(){ 

	var list = $('.list-group li.list-group-item a.del-a');
		var temp=0;		
		list.each(function(i,value){
					 	if( value.name == ($('#textbox_id').val())  ){
					    	  temp++;
					 	}	
			});		
		return temp;
	};
	
/*is called by $doument.ready() from count*/
	function fLocaliz(arr){ 
		
	/*functions for deleting country check*/	
	/*confirmation for deleting countries*/
		function confirmation(id,realname){ 
			
			var ask = arr[0]; 
	
			var isAdmin = confirm(ask+realname+"?");
			  if(isAdmin){
				  window.location = "deletingCountry/"+id; 
				}
			} 

		/*input delete country onClick() attribute*/
		 $('.list-group li.list-group-item a.del-a').click(function(){ 
			   confirmation(this.id,this.name);     
			   return false;
			 });
		 
	
	/*functions for validation country check*/
			$.validator.addMethod("rolecheckinputduplicate", function(value, element) {
				var isUniq = (isDuplicate()==0);
				return (isUniq);
			}, arr[3]); 
			
			
			$.validator.addMethod("rolecheckinputformat", function(value, element) {
				
				return this.optional(element) || /^[a-zA-Z ]{3,}$/i.test(value);
			}, arr[1]); 
			
			
			$.validator.addMethod("rolecheckinputcase", function(value, element) {
				var caseTest = $('#textbox_id').val()[0] != $('#textbox_id').val()[0].toLowerCase();
				return this.optional(element) || caseTest;
			}, arr[2]);
			
			
			$("#main_form").validate({
				
				rules:{
					name:{
						required:true,
						minlength:3,
						maxlength: 25,

						rolecheckinputformat:true,
						rolecheckinputcase:true,
						rolecheckinputduplicate:true,
					}
					
				},
				
				messages:{
					name:{
						required:  arr[5],
						minlength: arr[4],
					    maxlength: arr[4],
					}
				}	
			});
		 
	}



	

