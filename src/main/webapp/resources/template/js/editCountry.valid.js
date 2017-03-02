/*is called by $doument.ready() from count*/
/*functions for validation country check*/
function fLocaliz(arr){ 

	$.validator.addMethod("rolecheckinputformat", function(value, element) {
		return this.optional(element) || /^[a-zA-Z ]{3,}$/i.test(value);
	}, arr[0]); 
	
	$.validator.addMethod("rolecheckinputcase", function(value, element) {
		var caseTest = $('#textbox_id').val()[0] != $('#textbox_id').val()[0].toLowerCase();
		return this.optional(element) || caseTest;
	}, arr[1]);
	
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
				required:  arr[3],
				minlength: arr[2],
			    maxlength: arr[2],
			}
		}	
	});

}
