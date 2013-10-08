<%@ page 
language="java"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
import="java.util.*,com.callrite.cbs.*"
%>
<TABLE class=menuTable WIDTH=100%>
	<TR>
		<TD class=menuItem nowrap><A HREF="activeCall.jsp" target="_TOP">ACTIVE CALLS</A></TD><TD>|</TD>
		<TD class=menuItem><A HREF="login.jsp?command=logout" target="_TOP">LOGOUT</A></TD><TD>|</TD>
		<TD WIDTH=100% align="right" class="version">Version: <%= new VIPVersion().getVersion().getVersion() %>&nbsp;&nbsp;Build Date: <%= new VIPVersion().getVersion().getBuildDate() %></TD>
	</TR>
</TABLE><BR>