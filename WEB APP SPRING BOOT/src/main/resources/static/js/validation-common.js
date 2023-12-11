 
function reportValidateIncorrectIfNeeded(status, inputName){
  if(!status) {
	  $("#"+inputName+"-invalid").css("display", "block");
  }
  return status;
}

function validateNotEmpty(fieldName, fieldContent){
  if (fieldContent == "" || fieldContent.trim() == "") {
    return false;
  }
  return true;
}

function validateLength(fieldName, fieldContent, minLength) {
  if (fieldContent == "" || fieldContent.trim() == "") {
    return false;
  }
  if(fieldContent.length < minLength) {
	  return false;
  }
  return true;	
}

function validateHasOnlyAcceptableChars(fieldName, fieldContent) {
    const regex = /_/i;
    fieldContent = fieldContent.replace(regex, '').trim();
    
    var acceptableChars = /^[A-Za-z0-9]+$/;
    if(fieldContent.match(acceptableChars)){
          return true;
    }
    return false;
}

function validateIsbnFormat(fieldContent) {	
  if (fieldContent == "" || fieldContent.trim() == "") {
    return false;
  }
  regex = /^\d+-\d+-\d+-\d+-\d+$/;

  return regex.test(fieldContent);
}

function validateISBN(fieldName, formName, desiredDigits){
  let fieldValue = document.forms[formName][fieldName].value;
  validationStatus = validateIsbnFormat(fieldValue);
  return reportValidateIncorrectIfNeeded(validationStatus, fieldName);
}

function validateHasAcceptableCharacters(fieldName, formName){
  let fieldValue = document.forms[formName][fieldName].value;
  validationStatus = validateHasOnlyAcceptableChars(fieldName, fieldValue);
  return reportValidateIncorrectIfNeeded(validationStatus, fieldName);
}

function validateLengthGreaterThan(fieldName, formName, minLength){
  let fieldValue = document.forms[formName][fieldName].value;
  validationStatus = validateLength(fieldName, fieldValue, minLength);
  return reportValidateIncorrectIfNeeded(validationStatus, fieldName);
}

function validateFieldNonEmpty(fieldName, formName){
  let fieldValue = document.forms[formName][fieldName].value;
  validationStatus = validateNotEmpty(fieldName, fieldValue);
  return reportValidateIncorrectIfNeeded(validationStatus, fieldName);
}

function clearPreviousValidationErrors(){
	$(".invalid-value").css("display", "none");
}