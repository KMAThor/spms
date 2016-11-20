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
	    url: "/spms/project/update/"+projectId+"/addTrait/"+traitId+"/",
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
	    url: "/spms/project/update/"+projectId+"/deleteTrait/"+traitId+"/",
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
	    url: "/spms/project/update/"+projectId+"/addTraitCategory/"+traitCategoryId+"/",
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
	    url: "/spms/project/update/"+projectId+"/deleteTraitCategory/"+traitCategoryId+"/",
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

// Team
function createTeam() {
	
	$('#createTeamModal').modal('hide');
	$('#loadingModal').modal('show');
	
	var name = $('#newTeamName').val();
	var project_id = $('#project_id').val();

	$.ajax({
		url: "/spms/create/team/"	,
	    data: {
	    	project_id: project_id,
	        name: name
	    },
	    type: "POST",
	    dataType : "text",
		timeout: 15000
	})
	.done(function(message) {
	    $('#loadingModal').modal('hide');
	    window.location = message.substring(1, message.length - 1);
	})
	.fail(function( xhr, status, errorThrown ) {
		$('#loadingModal').modal('hide');
		$('#networkErrorModal').modal('show');
	})
	.always(function( xhr, status ) {
	});
}

function updateTeamName(id) {
	
	$('#editTeamNameModal').modal('hide');
	$('#loadingModal').modal('show');
	
	var name = $('#newTeamName').val();

	$.ajax({
	    url: "/spms/update/team/",
	    data: {
	    	id: id,
	        name: name
	    },
	    type: "POST",
	    dataType : "text",
		timeout: 15000
	})
	.done(function(message) {
		$('#teamName').text(name);
	    $('#loadingModal').modal('hide');
	})
	.fail(function( xhr, status, errorThrown ) {
		$('#loadingModal').modal('hide');
		$('#networkErrorModal').modal('show');
	})
	.always(function( xhr, status ) {
	});;
}

function deleteTeam(id) {
	
	$('#deleteTeamModal').modal('hide');
	$('#loadingModal').modal('show');
	
	var project_id = $('#project_id').val();

	$.ajax({
	    url: "/spms/delete/team/",
	    data: {
	        id: id
	    },
	    type: "POST",
	    dataType : "text",
		timeout: 15000
	})
	.done(function( json ) {
	    $('#loadingModal').modal('hide');
	    window.location = '/spms/project/view/' + project_id + '/';
	})
	.fail(function( xhr, status, errorThrown ) {
		$('#loadingModal').modal('hide');
		$('#networkErrorModal').modal('show');
	})
	.always(function( xhr, status ) {
	});
}

function createMeeting() {
	
	$('#createMeetingModal').modal('hide');
	$('#loadingModal').modal('show');
	
	var team_id = $('#team_id').val();
	var topic = $('#topic').val();
	var start_date = $('#meetingStartDate').val();

	$.ajax({
		url: "/spms/create/meeting/"	,
	    data: {
	    	team_id: team_id,
	    	topic: topic,
	    	start_date: start_date
	    },
	    type: "POST",
	    dataType : "text",
		timeout: 15000
	})
	.done(function(message) {
	    $('#loadingModal').modal('hide');
	    window.location = message.substring(1, message.length - 1);
	})
	.fail(function( xhr, status, errorThrown ) {
		$('#loadingModal').modal('hide');
		$('#networkErrorModal').modal('show');
	})
	.always(function( xhr, status ) {
	});
}

function deleteMeeting() {
	
	$('#deleteMeetingModal').modal('hide');
	$('#loadingModal').modal('show');
	
	var id = idDelMeet;

	$.ajax({
	    url: "/spms/delete/meeting/",
	    data: {
	        id: id
	    },
	    type: "POST",
	    dataType : "text",
		timeout: 15000
	})
	.done(function( json ) {
		meetingsTable.row('.tr-' + id).remove().draw( false );
	    $('#loadingModal').modal('hide');
	})
	.fail(function( xhr, status, errorThrown ) {
		$('#loadingModal').modal('hide');
		$('#networkErrorModal').modal('show');
	})
	.always(function( xhr, status ) {
	});
}

function attachIdMeet(id) {
	idDelMeet = id;
}

function attachIdFile(id) {
	idDelFile = id;
}
