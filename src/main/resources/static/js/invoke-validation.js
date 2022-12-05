 $(function() {

	 $('#add-user-form').on('submit', function() {
		  clearPreviousValidationErrors();
		  
		  var isValidationOk = true;
		  
		  isValidationOk = validateFieldNonEmpty("userName","add-user-form");
		  isValidationOk = validateLengthGreaterThan("login","add-user-form", 8) && isValidationOk;      
		  isValidationOk = validateHasAcceptableCharacters("login","add-user-form") && isValidationOk;
		  
		  if(!isValidationOk){
		  	$("html, body").animate({ scrollTop: 0 }, "slow");
		  }
		  return isValidationOk;
	});
	     
	 $('#add-book-form').on('submit', function() {
		  clearPreviousValidationErrors();
		  
		  var isValidationOk = true;
		  
		  isValidationOk = validateFieldNonEmpty("title","add-book-form");
		  isValidationOk = validateFieldNonEmpty("author","add-book-form") && isValidationOk;
		  isValidationOk = validateISBN("isbn","add-book-form") && isValidationOk;
		  isValidationOk = validateFieldNonEmpty("yearOfPublication","add-book-form") && isValidationOk;
		  
		  if(!isValidationOk){
		  	$("html, body").animate({ scrollTop: 0 }, "slow");
		  }
		  return isValidationOk;
	 });
	 
	 
	 $('.delete-item-button').click(function() {
          var result = confirm("Are you sure?");
          return result;
     });
});
 