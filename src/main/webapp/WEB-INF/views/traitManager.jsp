<%@include file="header.jsp"%>
<div class="row">
	<div class="col-sm-12">
		<h1>
			Trait manager
			<button type="button" class="btn btn-success"
					data-toggle="modal" data-target="#createTraitCategoryModal">
				<i class="fa fa-plus-circle" aria-hidden="true"></i>
				Add Trait Category
				</button>
			</h1>
	</div>
</div>
<div class="row">
	<div class="col-sm-offset-1 col-sm-10">
		<div id="categoriesPanelGroup" class="panel-group">
			<c:forEach var="traitCategory" items="${traitCategories}">
			  	<div id="category-${traitCategory.id}" class="panel panel-default">
				    <div class="panel-heading clickable" data-toggle="collapse" href="#category-${traitCategory.id}-traits">
				    	<div class="row">
							<div class="col-sm-7">
						      <h3 id="traitCategory-${traitCategory.id}-name" class="categoryTitle">${traitCategory.name}</h3>
						    </div>
						    <div class="col-sm-5 text-right" >
						      	<div class="btn-group btn-group-xs" role="group" aria-label="..."  >
								  <button type="button" class="btn btn-success"
								  		  data-toggle="modal" data-target="#createTraitModal"
								  		  onclick="selectTraitCategory(${traitCategory.id});">
								  	<i class="fa fa-plus-circle" aria-hidden="true"></i>
								  	Add Trait
								  </button>
								  <button type="button" class="btn btn-warning"
								  		  data-toggle="modal" data-target="#editTraitCategoryModal"
								  		  onclick="editTraitCategory(${traitCategory.id}, '${traitCategory.name}');">
								  	<i class="fa fa-pencil" aria-hidden="true"></i>
								  	Edit
								  </button>
								  <button type="button" class="btn btn-danger"
								  		 onclick="deleteTraitCategory(${traitCategory.id});">
								  	<i class="fa fa-trash" aria-hidden="true"></i>
								  	Delete
								  	</button>
								</div>
							</div>
						</div>  
			    	</div>

				    <div id="category-${traitCategory.id}-traits" class="panel-collapse collapse">
				    	<c:forEach var="trait" items="${traitCategory.traits}">
					      	<ul id="trait-${trait.id}" class="list-group traits-list">
								<li class="list-group-item">
									<div class="row">
										<div class="col-sm-9">
											<p id="trait-${trait.id}-name">${trait.name}</p>
										</div>
										<div class="col-sm-3 text-right" >
									      	<div class="btn-group btn-group-xs" role="group" aria-label="..."  >
											  <button type="button" class="btn btn-warning"
											  		  data-toggle="modal" data-target="#editTraitModal"
											  		  onclick="editTrait(${trait.id}, '${trait.name}', ${traitCategory.id});">
											  	<i class="fa fa-pencil" aria-hidden="true"></i>
											  	Edit
											  </button>
											  <button type="button" class="btn btn-danger"
											  		  onclick="deleteTrait(${trait.id});">
											  	<i class="fa fa-trash" aria-hidden="true"></i>
											  	Delete
											  	</button>
											</div>
										</div>
									</div>
								</li>
					      	</ul>
				   		</c:forEach>
				  	</div>
				</div>
			</c:forEach>
		</div>
	</div>
</div>

<!-- createTraitCategoryModal -->
<div id="createTraitCategoryModal" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">
        	<span aria-hidden="true">&times;</span>
        </button>
        <h4 class="modal-title" id="myModalLabel">Create Trait Category</h4>
      </div>
      <div class="modal-body">
        <form>
			<div id="createCategoryForm" class="form-group">
				<label for="newCategoryName">Category name:</label>
			    <input id="newCategoryName" class="form-control" type="text" placeholder="Enter new category name">
			</div>
		</form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
        <button type="button" class="btn btn-primary" onclick="createTraitCategory();">Create</button>
      </div>
    </div>
  </div>
</div>

<!-- createTraitModal -->
<div class="modal fade" id="createTraitModal" tabindex="-1" role="dialog">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">
        	<span aria-hidden="true">&times;</span>
        </button>
        <h4 class="modal-title" id="myModalLabel">Create Trait</h4>
      </div>
      <div class="modal-body">
        <form>
			<div id="createCategoryForm" class="form-group">
				<label for="newTraitName">Trait name:</label>
			    <input type="text" class="form-control" id="newTraitName" placeholder="Enter new trait name">
			</div>
		</form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
        <button type="button" class="btn btn-primary" onclick="createTrait();">Add</button>
      </div>
    </div>
  </div>
</div>

<!-- editTraitCategoryModal -->
<div class="modal fade" id="editTraitCategoryModal" tabindex="-1" role="dialog">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
        	<span aria-hidden="true">&times;</span>
        </button>
        <h4 class="modal-title" id="myModalLabel">Edit Trait Category</h4>
      </div>
      <div class="modal-body">
        <form>
			<div id="createCategoryForm" class="form-group">
				<label for="updatedCategoryName">Category name:</label>
			    <input type="text" class="form-control" id="updatedCategoryName" placeholder="">
			    <input type="hidden" id="updatedCategoryId">
			</div>
		</form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
        <button type="button" class="btn btn-primary" onclick="updateTraitCategory();">Update</button>
      </div>
    </div>
  </div>
</div>

<!-- editTraitModal -->
<div class="modal fade" id="editTraitModal" tabindex="-1" role="dialog">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
        	<span aria-hidden="true">&times;</span>
        </button>
        <h4 class="modal-title" id="myModalLabel">Edit Trait</h4>
      </div>
      <div class="modal-body">
        <form>
			<div id="createCategoryForm" class="form-group">
				<label for="updatedTraitName">Trait name:</label>
			    <input type="text" class="form-control" id="updatedTraitName" placeholder="">

			    <input type="hidden" id="updatedTraitId">
			    <input type="hidden" id="updatedTraitCategoryId">
			</div>
		</form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
        <button type="button" class="btn btn-primary" onclick="updateTrait();">Update</button>
      </div>
    </div>
  </div>
</div>

<script src="/spms/resources/js/traitManager.js"></script>
<%@include file="commonModalPopUps.jsp"%>
<%@include file="footer.jsp"%>