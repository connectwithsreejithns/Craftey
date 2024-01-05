// Function to check Whether both passwords 
// is same or not. 
function checkPassword(form) {
	password1 = document.forms["signup-form"]["password"].value;

	password2 = document.forms["signup-form"]["confirmPassword"].value;


	if (password1 != password2) {
		alert("\nEntered Password did not match: Please try again...")
		return false;
	}


} 