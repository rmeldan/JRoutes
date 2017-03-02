function removeUserRole() {
	var id = $("#haveRoles :selected").val();
	var name = $("#haveRoles :selected").text();
	if (name != "") {
		$("#notHaveRoles").append(
				$('<option value="' + id + '">' + name + '</option>'));
		$("#haveRoles :selected").remove();
		// $("#userRolesManagmentResult").text("");
		$("#userRolesManagmentResult").hide();
	} else {
		// alert("Select role from left list");
		$("#userRolesManagmentResult").show();
		$("#userRolesManagmentResult").text("Select role from left list");
	}
};

function addUserRole() {
	var id = $("#notHaveRoles :selected").val();
	var name = $("#notHaveRoles :selected").text();
	if (name != "") {
		$("#haveRoles").append(
				$('<option value="' + id + '">' + name + '</option>'));
		$("#notHaveRoles :selected").remove();
		// $("#userRolesManagmentResult").text("");
		$("#userRolesManagmentResult").hide();
	} else {
		// alert("Select role from right list");
		$("#userRolesManagmentResult").show();
		$("#userRolesManagmentResult").text("Select role from right list");
	}
};

