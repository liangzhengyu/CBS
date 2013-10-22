<%@ page 
language="java"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
import="java.util.*,com.callrite.cbs.web.*,com.callrite.cbs.*,com.callrite.cbs.util.*,com.callrite.cbs.telephony.plivo.*"
%>

<%

	String command = request.getParameter("command") ;
	if (command == null || command.length() <= 0) {
		command = "display" ;
	}
	
	String refreshInterval = request.getParameter("interval") ;
	if (refreshInterval == null || refreshInterval.trim().length() == 0 ) {
	    refreshInterval = "5" ;
	}

    Vector calls = PlivoService.getTodayCalls();
    String errorMsg = "";
    
    String[][] intervals = new String[][] { {"2 seconds", "2"}, {"5 seconds", "5"}, {"10 seconds", "10"}, {"15 seconds", "15"}, {"30 seconds", "30"}, {"60 seconds", "60"}, {"Never", "0"} };
%>
	
<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="Eclipse">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK href="theme/Master.css" rel="stylesheet"
	type="text/css">
<TITLE>Plivo Calls</TITLE>
<SCRIPT LANGUAGE="JavaScript">
  function selectInterval(form) {
		form.submit();
		return false;
  }
  
  function onInit() {
        <%
        int timeoutInterval = 5000;
        try {
           timeoutInterval = Integer.parseInt(refreshInterval)*1000;
        }catch(Exception e){};
        %>
		<% if (timeoutInterval > 0) { %>
			setTimeout("reloadPage();",<%=timeoutInterval%>);
		<% } %>
  }
  
  function reloadPage() {
    parent.location="plivo_calls.jsp?interval=<%=refreshInterval%>";
  }
  
</SCRIPT>
</HEAD>
<BODY onload="onInit()">
	<jsp:include page="menu.jsp" />
		
	<TABLE WIDTH="100%" cellpadding=10 cellspacing=10 border=0>
	
		<%
		if (errorMsg != null && errorMsg.length() > 0) {
	    %>
			<tr><td>
				<TABLE WIDTH=100% cellpadding=10 cellspacing=10 border=0>
	    			<tr><td><span class="errorMsg"><%= errorMsg %></span></td></tr>
		   		</TABLE>
	   		</td></tr>
	    <%
		}
		%>

		<TR><TD COLSPAN=10 class=sectionHeader><A href="reload_plivo.jsp">Reload VIP Properties File</A></TD></TR>
					
		<TR><TD>
			<TABLE cellpadding=0 cellspacing=0 border=0 WIDTH="100%">
				<FORM action="plivo_calls.jsp" METHOD=post>
					<TR>
						<TD COLSPAN="3" align="right">
							<TABLE width="100%" cellpadding=0 cellspacing=0 border=0>
								<TR>
									<TD class=sectionHeader>Today Incoming Calls</TD>
									<TD nowrap class="smallText"  align="right">Refresh Interval: 
										<select name="interval" onchange="javascript:return selectInterval(this.form)">
										  <%for (int i=0; i<intervals.length; i++) {
										  	 String selected = "";
										     if ( intervals[i][1].equals(refreshInterval) ) {
										     	selected = "selected";
										     }
										  %>
										  <option value="<%=intervals[i][1]%>" <%=selected%>><%=intervals[i][0]%></option>   
										  <% } %>
										</select>
									</TD>						
								</TR>
							</TABLE>
						</TD>
					</TR>
				</FORM>
			<TR>
				<TD colspan="3">
				<TABLE class=infoTable width="100%">
					<TR>
						<TD class=tableHeader>Call ID</TD>
						<TD class=tableHeader>Date/Time</TD>
						<TD class=tableHeader>Call Status</TD>
						<TD class=tableHeader>ANI</TD>
						<TD class=tableHeader>DNIS</TD>
						<TD class=tableHeader>Caller Name</TD>
						<TD class=tableHeader>Direction</TD>
						<TD class=tableHeader>Disposition</TD>
					</TR>
					<%
					    for (int x=0; x<calls.size(); x++) {
											    PlivoCall[] callRequests = (PlivoCall[]) calls.get(x) ;
											    
											    for(int y=0; y<callRequests.length; y++) {
											    	PlivoCall call = callRequests[y];	
											    	String callID = call.getCallID(); 
											    	if ( y > 0 ) {
												    	callID = "";
												    }
					%>
							<TR>
								<TD class=tableCellData><%=callID%></TD>
								<TD class=tableCellData><%=new Date(call.getTimestamp())%></TD>
								<TD class=tableCellData>
									<%
									    String status = "Not Started";
																		  switch ( call.getStatus() ) {
																		  	case PlivoCall.STATUS_STARTED:
																		  	  status = "Ringing";
																		  	  break;
																		  	case PlivoCall.STATUS_ACTIVE:
																		  	  status = "Answered";
																		  	  break;  
																		  	case PlivoCall.STATUS_COMPLETED:
																		  	  status = "Completed";
																		  	  break;
																		  	default:
																		  	  status = "Unknown";
																		  	  break;    
																		  }
									%>
									<%=status%> 
								</TD>
								<TD class=tableCellData><%=call.getANI()==null?"":call.getANI()%></TD>
								<TD class=tableCellData><%=call.getDNIS()==null?"":call.getDNIS()%></TD>
								<TD class=tableCellData><%=call.getCallerName()==null?"":call.getCallerName()%></TD>
								<TD class=tableCellData>
									<%
									    String direction = "Unknown";
																		  switch ( call.getDirection() ) {
																		  	case PlivoCall.DIRECTION_INBOUND:
																		  	  direction = "Inbound";
																		  	  break;
																		  	case PlivoCall.DIRECTION_OUTBOUND:
																		  	  direction = "Outbound";
																		  	  break;  
																		  	default:
																		  	  direction = "Unknown";
																		  	  break;    
																		  }
									%>
									<%=direction%> 
								</TD>
								<TD class=tableCellData>
									<%
									    String disposition = "";
																		  switch ( call.getDisposition() ) {
																		  	case PlivoCall.DISPOSITION_CALLER_HANGUP:
																		  	  disposition = "Caller Hangup";
																		  	  break;
																		  	case PlivoCall.DISPOSITION_CALLEE_HANGUP:
																		  	  disposition = "Callee Hangup";
																		  	  break;
																		  	case PlivoCall.DISPOSITION_SYSTEM_HANGUP:
																		  	  disposition = "System Hangup";
																		  	  break;
																		  	case PlivoCall.DISPOSITION_ERROR_HANGUP:
																		  	  disposition = "Error Hangup";
																		  	  break;  
																		  	default:
																		  	  disposition = "";
																		  	  break;    
																		  }
									%>
									<%=disposition%> 
								</TD>
							</TR>
						<%			
							}				
						}
					%>
				</TABLE>
				</TD>
			</TR>
			</TABLE>
		</TD></TR>

	
			
	</TABLE>

</BODY>
</HTML>
