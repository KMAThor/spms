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

// Team
function createTeam() {
	
	$('#createTeamModal').modal('hide');
	$('#loadingModal').modal('show');
	
	var name = $('#newTeamName').val();
	var project_id = $('#project_id').val();

	$.ajax({
		url: "/spms/team/create/"	,
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
	    url: "/spms/team/update/",
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
	    url: "/spms/team/delete/",
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
		url: "/spms/meeting/create/"	,
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

function deleteMeetingT() {
	
	$('#deleteMeetingModal').modal('hide');
	$('#loadingModal').modal('show');
	
	var id = idDelMeet;

	$.ajax({
	    url: "/spms/meeting/delete/",
	    data: {
	        id: id
	    },
	    type: "POST",
	    dataType : "text",
		timeout: 15000
	})
	.done(function(message) {
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

function editMeeting(id) {
	
	$('#editMeetingModal').modal('hide');
	$('#loadingModal').modal('show');
	
	var topic = $('#topic').val();
	alert(topic);
	var start_date = $("#datetimepicker").find("input").val();
	alert(start_date);
	
	$.ajax({
	    url: "/spms/update/meeting/",
	    data: {
	    	id: id,
	    	topic: topic,
	        start_date: start_date
	    },
	    type: "POST",
	    dataType : "text",
		timeout: 15000
	})
	.done(function(message) {
		$('#meetingTopic').text(topic);
		$('#meetingStartDate').text(start_date);
	    $('#loadingModal').modal('hide');
	})
	.fail(function( xhr, status, errorThrown ) {
		$('#loadingModal').modal('hide');
		$('#networkErrorModal').modal('show');
	})
	.always(function( xhr, status ) {
	});;
}

function deleteMeetingM(id) {
	
	$('#deleteMeetingModal').modal('hide');
	$('#loadingModal').modal('show');

	var team_id = $('#team_id').val();
	
	$.ajax({
	    url: "/spms/meeting/delete/",
	    data: {
	        id: id
	    },
	    type: "POST",
	    dataType : "text",
		timeout: 15000
	})
	.done(function(message) {
	    $('#loadingModal').modal('hide');
	    window.location = '/spms/team/view/' + team_id + '/';
	})
	.fail(function( xhr, status, errorThrown ) {
		$('#loadingModal').modal('hide');
		$('#networkErrorModal').modal('show');
	})
	.always(function( xhr, status ) {
	});
}