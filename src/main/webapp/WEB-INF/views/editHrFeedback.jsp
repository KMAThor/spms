<%@include file="header.jsp"%>
<div class="row">
	<div class="col-sm-offset-1 col-sm-10">
		<h1>Edit Feedback</h1>
	</div>
</div>
<form name="createMeetingFeedback" 
	  action="<%=request.getContextPath()%>/meetingFeedback/update/${meetingFeedback.id}/" method="post">
	  <input type="hidden" name="studentId" value="${meetingFeedback.student.id}">
	  <input type="hidden" name="meetingId" value="${meetingFeedback.meeting.id}">
<div class="row">
	<div class="col-sm-offset-1 col-sm-10">

		<c:forEach var="traitCategory" items="${traitCategories}">
			<div class="row">
				<h2> ${traitCategory.name} </h2>
					<hr/>
				<div class="col-sm-offset-1 col-sm-11">
					
					<c:forEach var="trait" items="${traitCategory.traits}">
					 	<c:set var="traitFeedbackId" value="0" />
					    <c:set var="score" value="0" />
					    <c:set var="comment" value="0" />
						<c:forEach var="traitFeedback" items="${meetingFeedback.traitFeedbacks}">
						  	<c:if test="${traitFeedback.trait.id eq trait.id}">
						  		<c:set var="traitFeedbackId" value="${traitFeedback.id}" />
						    	<c:set var="score" value="${traitFeedback.score}" />
					    		<c:set var="comment" value="${traitFeedback.comment}" />
						  </c:if>
						</c:forEach>
						<div class="row traitFeedbackContainer">
							<h3> ${trait.name} </h3>
							<hr/>
							<div class="col-sm-3">
								<h4 ><strong> Grade </strong></h4>
								<div class="col-sm-2 sliderContainer">
									<input type="hidden" name="traitFeedbackIds" value="${traitFeedbackId}">
									<input type="hidden" name="traitIds" value="${trait.id}">
									<input name="scores" class="gradeSlider" type="text"
										data-provide="slider" data-slider-min="0" data-slider-max="5"
										data-slider-step="1" data-slider-value="${score}"
										data-slider-reversed="true" data-slider-orientation="vertical"
										data-slider-tooltip-position="left"
										data-slider-tooltip="always"/>
								</div>
								<div class="col-sm-10 gradesDescriptionBlock">
									<p>
										Excellent
										<img class="gradeIcon" src="<c:url value="/resources/img/thor_hammer_icon.png"/>">
									</p>
									<p>Good</p>
									<p>Average results</p>
									<p>Below average</p>
									<p>Bad</p>
									<p>Can not evaluate</p>
								</div>
								
							</div>
							<div class="col-sm-9">
								<h4 ><strong> Comment</strong></h4>
								<textarea name="comments" class="gradeComment form-control" placeholder="This field is optional" 
									>${comment}</textarea>
							</div>

						</div>
					</c:forEach>
				</div>
			</div>
		</c:forEach>
	</div>
</div>
<div class="row">
	<div class="col-sm-offset-1 col-sm-10">
		<h2>Summary</h2>
		<textarea name="summary" class="form-control" placeholder="This field is optional">${meetingFeedback.summary}</textarea>
	</div>
</div>
<div class="row">
	<div class="col-sm-offset-9 col-sm-2">
		<br>
		<button type="submit" value="Submit" class="form-control btn btn-success" >Submit Changes</button>
	</div>
</div>
</form>
<%@include file="footer.jsp"%>