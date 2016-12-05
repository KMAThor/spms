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
	    url: getContextPath()+"/project/addTrait/",
	    type: "POST",
	    dataType : "json",
	    data: {
	    	projectId: projectId,
	    	traitId: traitId
	    },
		timeout: 15000,
	    beforeSend: function(xhr) {
            xhr.setRequestHeader(header, token);
        }
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
	    url: getContextPath()+"/project/deleteTrait/",
	    type: "POST",
	    dataType : "json",
	    data: {
	    	projectId: projectId,
	    	traitId: traitId
	    },
		timeout: 15000,
	    beforeSend: function(xhr) {
            xhr.setRequestHeader(header, token);
        }
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
	    url: getContextPath()+"/project/addTraitCategory/",
	    type: "POST",
	    dataType : "json",
	    data: {
	    	projectId: projectId,
	    	traitCategoryId: traitCategoryId
	    },
		timeout: 15000,
	    beforeSend: function(xhr) {
            xhr.setRequestHeader(header, token);
        }
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
	    url: getContextPath()+"/project/deleteTraitCategory/",
	    type: "POST",
	    dataType : "json",
	    data: {
	    	projectId: projectId,
	    	traitCategoryId: traitCategoryId
	    },
		timeout: 15000,
	    beforeSend: function(xhr) {
            xhr.setRequestHeader(header, token);
        }
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

function chooseChiefMentor(id, name) {
	$('#cheifMentorName').text(name);
	$('#cheifMentorId').val(id);
	$('#chooseChiefMentorModal').modal('hide');
}

function chooseAuthor(id, name) {
	$('#hrFeedbackAuthorName').text(name);
	$('#hrFeedbackAuthorId').val(id);
	$('#chooseAuthorModal').modal('hide');
}
