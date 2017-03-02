/**
 * In this file we can hold functions, which calls, when 
 * confirmation of record deleting is needed 
 */


/**
 * Calling confirm window, when security role is deleting
 * @param id - id of SecurityRole to delete
 * @param realname - name of SecurityRole to display when confirm window appears
 */
function confirmRoleDelete(id, realname){ 
	var result = confirm("Do you want to delete "+realname+"?");
	  if(result){
		  window.location = "deletingSecurityRole/"+id; 
		}
	} 