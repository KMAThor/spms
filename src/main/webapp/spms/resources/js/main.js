$(document).on('click', '[data-toggle=collapse] .btn-group', function(e) {
	e.stopPropagation();
});
$(document).on('click', '[data-toggle=collapse] [type=checkbox]', function(e) {
	e.stopPropagation();
});


function traitCheckboxAction(checkboxElem, traitId, projectId) {
  if (checkboxElem.checked) {
    $('#loadingModal').modal('show');
    $.ajax({
	    url: "/spms/update/project/"+projectId+"/addTrait/"+traitId+"/",
	    type: "GET",
	    dataType : "text",
		timeout: 15000
	})
	.done(function( json ) {
	    $('#loadingModal').modal('hide');
	})
	.fail(function( xhr, status, errorThrown ) {
		$('#loadingModal').modal('hide');
		$('#projectTraitsManagerModal').modal('hide');
		$('#networkErrorModal').modal('show');
	})
	.always(function( xhr, status ) {
	});
  } else {
    $('#loadingModal').modal('show');
    $.ajax({
	    url: "/spms/update/project/"+projectId+"/deleteTrait/"+traitId+"/",
	    type: "GET",
	    dataType : "text",
		timeout: 15000
	})
	.done(function( json ) {
	    $('#loadingModal').modal('hide');
	})
	.fail(function( xhr, status, errorThrown ) {
		$('#loadingModal').modal('hide');
		$('#projectTraitsManagerModal').modal('hide');
		$('#networkErrorModal').modal('show');
	})
	.always(function( xhr, status ) {
	});
  }
}

function selectAllTraitAction(traitCategoryId, projectId) {
	$('#loadingModal').modal('show');
    $.ajax({
	    url: "/spms/update/project/"+projectId+"/addTraitCategory/"+traitCategoryId+"/",
	    type: "GET",
	    dataType : "text",
		timeout: 15000
	})
	.done(function( json ) {
		$('#category-'+traitCategoryId+'-traits [type=checkbox]').prop('checked', true);
	    $('#loadingModal').modal('hide');
	})
	.fail(function( xhr, status, errorThrown ) {
		$('#loadingModal').modal('hide');
		$('#projectTraitsManagerModal').modal('hide');
		$('#networkErrorModal').modal('show');
	})
	.always(function( xhr, status ) {
	});
}

function deselectAllTraitAction(traitCategoryId, projectId) {
	$('#loadingModal').modal('show');
    $.ajax({
	    url: "/spms/update/project/"+projectId+"/deleteTraitCategory/"+traitCategoryId+"/",
	    type: "GET",
	    dataType : "text",
		timeout: 15000
	})
	.done(function( json ) {
		$('#category-'+traitCategoryId+'-traits [type=checkbox]').prop('checked', false);
	    $('#loadingModal').modal('hide');
	})
	.fail(function( xhr, status, errorThrown ) {
		$('#loadingModal').modal('hide');
		$('#projectTraitsManagerModal').modal('hide');
		$('#networkErrorModal').modal('show');
	})
	.always(function( xhr, status ) {
	});
}


