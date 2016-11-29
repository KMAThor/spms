<!-- loadingModal -->
<div class="modal fade" data-backdrop="static" data-keyboard="false" id="loadingModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="text-center">
		<img src="<%=request.getContextPath()%>/resources/img/loading.gif" width="50px">
    </div>
  </div>
</div>
<!-- networkErrorModal -->
<div class="modal fade" data-backdrop="static" data-keyboard="false" id="networkErrorModal" tabindex="-1" role="dialog" >
  <div class="modal-dialog" role="document">
    <div class="text-center errorMessageBody">
		<p>Network error, please check your internet connection, reload page and try again.</p>
		<p>If error still occur contact our support team via support.spms@gmail.com</p>
    </div>
  </div>
</div>
