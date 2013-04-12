<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ page import="com.copyright.mediadelivery.framework.MdsVersionData"%>
<%@ page import="com.copyright.srs.core.SrsCoreVersionData"%>
<%@ page import="com.copyright.srs.connectors.SrsVersionData"%>
<%@ page import="com.copyright.svc.searchRetrieval.SearchRetrievalServiceBuildData"%>
<%@ page import="com.copyright.svc.searchRetrieval.api.SearchRetrievalServiceConfiguration"%>
<%@ page import="com.copyright.svc.indexSearcher.IndexSearcherServiceBuildData"%>
<%@ taglib prefix="cfg" uri="http://xmlns.copyright.com/tld/config.tld" %>
<%@ taglib prefix="svcCommon" uri="http://xmlns.copyright.com/tld/ccc-svc-common.tld" %>
<%@ taglib prefix="crs" uri="http://xmlns.copyright.com/tld/crs.tld" %>
	
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>Search Retrieval Service Configuration</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
  </head>
  
  <body>
  
	<h1 align="center"> <u>Search Retrieval Service Configuration</u></h1>
  
	<h2><u>Build Version Data</u></h2>
	<cfg:buildData buildData="<%= SearchRetrievalServiceBuildData.getInstance() %>" header="svc-searchRetrieval" tableAttr="border=1 cellpadding=1 width=80%"/>
	<cfg:versionData versionData="<%= SrsCoreVersionData.getInstance() %>" header="srs-core" tableAttr="border=1 cellpadding=1 width=80%"/>
	<cfg:versionData versionData="<%= SrsVersionData.getInstance() %>" header="srs-connectors" tableAttr="border=1 cellpadding=1 width=80%"/>
	<cfg:versionData versionData="<%= MdsVersionData.getInstance() %>" header="media-delivery-system" tableAttr="border=1 cellpadding=1 width=80%"/>
	<cfg:buildData buildData="<%= IndexSearcherServiceBuildData.getInstance() %>" header="svc-indexSearcher" tableAttr="border=1 cellpadding=1 width=80%"/>
	<cfg:solrExt tableAttr="border=1 cellpadding=1 width=80%" />
	<br/>
	<br/>
	<h2><u>Config Properties</u></h2>
	<cfg:properties properties="<%= SearchRetrievalServiceConfiguration.getInstance().getProperties() %>" header="SearchRetrievalService.properties" tableAttr="border=1 cellpadding=1 width=80%"/>
	<br/>
	<br/>
	<crs:configurationView module="SRS" versionData="com/copyright/svc/searchRetrieval/versiondata.txt" tableAttr="border=1 cellpadding=3"/>	<h2><u>Service Dependencies</u></h2>
	<br/>
	<br/>
	<h2><u>CCC Shared Components</u></h2>
	<cfg:cccBase tableAttr="border=1 cellpadding=1  width=80%"/>
	<cfg:appIntegrity tableAttr="border=1 cellpadding=1  width=80%"/>
	<cfg:svcCommon tableAttr="border=1 cellpadding=1 width=80%"/>
	<br/>
	<br/>
	<h2><u>JBoss</u></h2>
	<cfg:jboss tableAttr="border=1 cellpadding=1 width=80%"/>
	<br/>
	<br/>
	<h2><u>JVM</u></h2>
	<cfg:jvm tableAttr="border=1 cellpadding=1 width=80%"/>
	<br/>
	<br/>
	<h2><u>Deployed Applications</u></h2>
	<cfg:deployedApps header="Apps" generateConfigLinks="true" tableAttr="border=1 cellpadding=1  width=80%"/>
	<br/>
	<br/>
	<h2><u>SearchRetrievalService Interface XML Description</u></h2>
	<table width="80%" border="0">
  	<tr><td> 
	<svcCommon:burlapXml includeResponse="true" header="Burlap XML" tableAttr="border=1 cellpadding=1"/>    </td>
  	</tr>
  	</table>
  </body>
</html>
