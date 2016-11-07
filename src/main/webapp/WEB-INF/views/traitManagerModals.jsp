
	<!-- createTraitCategoryModal -->
	<div class="modal fade" id="createTraitCategoryModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="myModalLabel">Create Trait Category</h4>
	      </div>
	      <div class="modal-body">
	        <form>
				<div id="createCategoryForm" class="form-group">
					<label for="newCategoryName">Category name:</label>
				    <input type="text" class="form-control" id="newCategoryName" placeholder="Enter new category name">
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
	<div class="modal fade" id="createTraitModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
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

		<!-- loadingModal -->
	<div class="modal fade" data-backdrop="static" data-keyboard="false" id="loadingModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog" role="document">
	    <div class="text-center">
			<img src="/spms/resources/img/loading.gif" width="50px">
	    </div>
	  </div>
	</div>

	<div class="modal fade" data-backdrop="static" data-keyboard="false" id="networkErrorModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog" role="document">
	    <div class="text-center">
			<p class="errorMessageInModal">Network error, please check your internet connection, reload page and try again.</p>
			<p class="errorMessageInModal">If error still occur contact our support team via support.spms@gmail.com</p>
	    </div>
	  </div>
	</div>

	<!-- editTraitCategoryModal -->
	<div class="modal fade" id="editTraitCategoryModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
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
	<div class="modal fade" id="editTraitModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
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
</div>

