<%@include file="header.jsp"%>
<div class="container">
	<div class="row">
		<div class="col-sm-12">
			<h1>
				Traits manager
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

<%@include file="traitManagerModals.jsp"%>
<%@include file="footer.jsp"%>