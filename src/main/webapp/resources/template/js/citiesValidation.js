
$(document).ready(function(){

	 $('a.del-a').click(function(){ 
		   confirmation(this.id,this.name);     
		 });
	 
	$('#submit_id').click(function(){ 
		
		   if($('#textbox_id').val()==""){
			   alert("City name can`t be empty.");
			   return false;		
			   
		   } else {   
						   		   
			   if( ($('#textbox_id').val().length)>60 ){
			  		 alert("To long city name.");   
			 	 	 return false;
			   
			   } else{
					   
				  	   if(  $('#textbox_id').val()[0] == $('#textbox_id').val()[0].toLowerCase()  ){
					  		 alert("City name must begin from big character.");
					  		 return false;   
					   
				  	   }else{
				  		   
				  			 var c = $('#textbox_id').val();
				  			 var countryValid =/^[a-zA-Z ']{3,}$/;
				  			
				  		  		 if( countryValid.test(c)==false ){
				  			   		 alert("Invalid input.");
				  			 	     return false;
				  			   
				  		   }else {
				  			   
				  			   var temp = 0;
				  			   
				  				   
				  			  	 	var list = $('.list-group li.list-group-item a.del-a');
							  		list.each(function(i,value){
								
								   		if( value.name == ($('#textbox_id').val())  ){
									 	 temp++;
									 	 alert("This city already exist.");
								   		} 
								   
							   		});
							  	 if(temp>0) return false;
				  		   
				  		  }
				  	  
				  	}
				   
			   }
		   }
		   
	 });

function confirmation(id,realname){ 
	
	var isAdmin = confirm("Do you realy want delete "+realname+"?");
	  if(isAdmin){
		  window.location = "deletingCities/"+id; 
		}
	} 

});
