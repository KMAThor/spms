var spmsSelectedCategoryId;
function selectTraitCategory(id) {
	spmsSelectedCategoryId = id;
}

function createTraitCategory() {
	var name = $('#newCategoryName').val();

	if(name === ''){
		alert('Name cannot be empty!');
		return;
	}

	$('#createTraitCategoryModal').modal('hide');
	$('#loadingModal').modal('show');

	$.ajax({
		url : "/spms/create/traitCategory/",
		data : {
			name : name
		},
		type : "POST",
		dataType : "json",
		timeout: 15000
	})
	.done(function(category) {
		$( "#categoriesPanelGroup" ).append( '<div id="category-'+category.id+'" class="panel panel-default">\
					    <div class="panel-heading clickable" data-toggle="collapse" href="#category-'+category.id+'-traits">\
					    	<div class="row">\
								<div class="col-sm-7">\
							      <h3 id="traitCategory-'+category.id+'-name" class="categoryTitle" >'+category.name+'</h3>\
							    </div>\
							    <div class="col-sm-5 text-right" >\
							      	<div class="btn-group btn-group-xs" role="group" aria-label="..."  >\
									  <button type="button" class="btn btn-success"\
									  		  data-toggle="modal" data-target="#createTraitModal"\
									  		  onclick="selectTraitCategory('+category.id+');">\
									  	<i class="fa fa-plus-circle" aria-hidden="true"></i>\
									  	Add Trait\
									  </button>\
									  <button type="button" class="btn btn-warning"\
									  		  data-toggle="modal" data-target="#editTraitCategoryModal"\
									  		  onclick="editTraitCategory('+category.id+');">\
									  	<i class="fa fa-pencil" aria-hidden="true"></i>\
									  	Edit\
									  </button>\
									  <button type="button" class="btn btn-danger"\
									  		 onclick="deleteTraitCategory('+category.id+');">\
									  	<i class="fa fa-trash" aria-hidden="true"></i>\
									  	Delete\
									  	</button>\
									</div>\
								</div>\
							</div>  \
				    	</div>\
					    <div id="category-'+category.id+'-traits" class="panel-collapse collapse">\
					  	</div>\
					</div> ');
		$('#loadingModal').modal('hide');
	})
	.fail(function(xhr, status, errorThrown) {
		$('#loadingModal').modal('hide');
		$('#networkErrorModal').modal('show');
	})
	.always(function( xhr, status ) {
	});
	$('#newCategoryName').val('');
}

function createTrait() {
	var name = $('#newTraitName').val();
	var categoryId = spmsSelectedCategoryId;

	if(name === ''){
		alert('Name cannot be empty!');
		return;
	}

	$('#createTraitModal').modal('hide');
	$('#loadingModal').modal('show');
	
	$.ajax({
		url : "/spms/create/trait/",
		data : {
			name : name,
			categoryId: categoryId
		},
		type : "POST",
		dataType : "json",
		timeout: 15000
	})
	.done(function(trait) {
		$('#category-'+trait.traitCategory.id+'-traits').append('<ul id="trait-'+trait.id+'" class="list-group traits-list">\
				<li class="list-group-item">\
				<div class="row">\
					<div class="col-sm-9">\
						<p id="trait-'+trait.id+'-name">'+trait.name+'</p>\
					</div>\
					<div class="col-sm-3 text-right" >\
				      	<div class="btn-group btn-group-xs" role="group" aria-label="..."  >\
						  <button type="button" class="btn btn-warning"\
						  		  data-toggle="modal" data-target="#editTraitModal"\
						  		  onclick="editTrait('+trait.id+', \''+trait.name+'\', '+trait.traitCategory.id+');">\
						  	<i class="fa fa-pencil" aria-hidden="true"></i>\
						  	Edit\
						  </button>\
						  <button type="button" class="btn btn-danger"\
						  		  onclick="deleteTrait('+trait.id+');">\
						  	<i class="fa fa-trash" aria-hidden="true"></i>\
						  	Delete\
						  	</button>\
						</div>\
					</div>\
				</div>\
			</li>\
      	</ul>');

		$('#loadingModal').modal('hide');
	})
	.fail(function(xhr, status, errorThrown) {
		$('#loadingModal').modal('hide');
		$('#networkErrorModal').modal('show');
	})
	.always(function( xhr, status ) {
	});
	$('#newTraitName').val('');
}

function editTraitCategory(id) {
	$('#updatedCategoryId').val(id);
	name = $('#traitCategory-'+id+'-name').text();
	$('#updatedCategoryName').val(name);
}

function updateTraitCategory() {

	var id =	$('#updatedCategoryId').val();
	var name = $('#updatedCategoryName').val();
	if(name === ''){
		alert('Name cannot be empty!');
		return;
	}	

	$('#editTraitCategoryModal').modal('hide');
	$('#loadingModal').modal('show');

	$.ajax({
	    url: "/spms/update/traitCategory/",
	    data: {
	        id: id,
	        name: name
	    },
	    type: "POST",
	    dataType : "text",
		timeout: 15000
	})
	.done(function(message) {
		$('#traitCategory-'+id+'-name').text(name);
	    $('#loadingModal').modal('hide');
	})
	.fail(function( xhr, status, errorThrown ) {
		$('#loadingModal').modal('hide');
		$('#networkErrorModal').modal('show');
	})
	.always(function( xhr, status ) {
	});
}

function editTrait(id, name, category_id) {
	$('#updatedTraitId').val(id);
	name = $('#trait-'+id+'-name').text();
	$('#updatedTraitName').val(name);
	$('#updatedTraitCategoryId').val(category_id);
}

function updateTrait() {

	var id =	$('#updatedTraitId').val();
	var name = $('#updatedTraitName').val();
	var categoryId = $('#updatedTraitCategoryId').val();
	if(name === ''){
		alert('Name cannot be empty!');
		return;
	}	

	$('#editTraitModal').modal('hide');
	$('#loadingModal').modal('show');

	$.ajax({
	    url: "/spms/update/trait/",
	    data: {
	        id: id,
	        name: name,
	        categoryId: categoryId
	    },
	    type: "POST",
	    dataType : "text",
		timeout: 15000
	})
	.done(function(message) {
		$('#trait-'+id+'-name').text(name);
	    $('#loadingModal').modal('hide');
	})
	.fail(function( xhr, status, errorThrown ) {
		$('#loadingModal').modal('hide');
		$('#networkErrorModal').modal('show');
	})
	.always(function( xhr, status ) {
	});
}

function deleteTraitCategory(id) {
	
	$.ajax({
	    url: "/spms/delete/traitCategory/",
	    data: {
	        id: id
	    },
	    type: "POST",
	    dataType : "text",
		timeout: 15000
	})
	.done(function( json ) {
		$('#category-'+ id).remove();
	    $('#loadingModal').modal('hide');
	})
	.fail(function( xhr, status, errorThrown ) {
		$('#loadingModal').modal('hide');
		$('#networkErrorModal').modal('show');
	})
	.always(function( xhr, status ) {
	});
}

function deleteTrait(id) {
	$('#loadingModal').modal('show');

	$.ajax({
	    url: "/spms/delete/trait/",
	    data: {
	        id: id
	    },
	    type: "POST",
	    dataType : "text",
		timeout: 15000
	})
	.done(function( json ) {
		$('#trait-'+ id).remove();
	    $('#loadingModal').modal('hide');
	})
	.fail(function( xhr, status, errorThrown ) {
		$('#loadingModal').modal('hide');
		$('#networkErrorModal').modal('show');
	})
	.always(function( xhr, status ) {
	});
}