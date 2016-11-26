<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
	if(session.getAttribute("user") == null)
		response.sendRedirect("login.html");
%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
<head>

	<title>NoBidReason</title>
	
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>

	<link href="img/favicon.ico" rel="icon" type="image/x-icon" />
    <link href="css/bootstrap.min.css" rel="stylesheet"/>
    <link href="css/sb-admin.css" rel="stylesheet"/>
    <link href="css/dashboard.css" rel="stylesheet"/>
    <link href="css/jquery-ui-calendar.css" rel="stylesheet" />
    <link href="font-awesome/css/font-awesome.min.css" rel="stylesheet"/>
    <link href="css/no-bid-reason.css" rel="stylesheet"/>
     
</head>
<body>
	<div class="top-message">Message</div>
	<div class="blackWindow"></div>
	<div class="loader"></div>
	<div class="container">

        <!-- Navigation -->
        <nav class="navbar navbar-inverse navbar-fixed-top">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="kpi">Admin <i class="fa fa-fw fa-dashboard"></i> Dashboard</a>
            </div>
            <!-- Top Menu Items -->
            <ul class="nav navbar-right top-nav">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user"></i> Admin <b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li>
                            <a href="profile.jsp"><i class="fa fa-fw fa-user"></i> Profile</a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a href="" id="btn_logOut"><i class="fa fa-fw fa-power-off"></i> Log Out</a>
                        </li>
                    </ul>
                </li>
            </ul>
		    <div class="collapse navbar-collapse navbar-ex1-collapse nav navbar-left top-nav">
		    	<ul class="nav navbar-nav">
		        	<li>
		            	<a href="kpi"><i class="fa fa-bar-chart"></i> KPI's</a>
		            </li>
		            <li class="active">
		            	<a href="noBidReason"><i class="fa fa-dot-circle-o"></i> No Bid Reason</a>
		            </li>
		        </ul>
			</div>
        </nav>
       
		<div class="row">
			<div class="col-xs-12">
		    	<h3>Fill to show a Tree</h3> 
		    </div>    	
			<div class="col-xs-12">
				<form class="form-horizontal">
										
			    	<div class="form-group">
			        	<h4 class="col-xs-12">Date &amp; Time</h4>
			            <div class="col-xs-12 col-sm-2 margin-bottom-6 text-align">
			            	<label class="control-label">Reference</label>
			            </div>
			            <div class="col-xs-12 col-sm-10">
			            	<div class="input-group col-sm-3">
			            		<div class="input-group-addon">Date</div>
			                    <input class="form-control date" type="text" id="referDate" placeholder="mm/dd/yyyy"/>
			                </div>
			                <div class="input-group col-sm-3">
			            		<div class="input-group-addon">Time</div>
			            		<select class="form-control" id="referTime"></select>
			                </div>
			            </div>
			           	<div class="col-xs-12 col-sm-2 margin-bottom-6 text-align">
			            	<label class="control-label">Compare To</label>
			            </div>
			            <div class="col-xs-12 col-sm-10">
			            	<div class="input-group col-sm-3">
			            		<div class="input-group-addon">Date</div>
			                    <input class="form-control date" type="text" id="compareDate" placeholder="mm/dd/yyyy"/>
			                </div>
			                <div class="input-group col-sm-3">
			            		<div class="input-group-addon">Time</div>
			                    <select class="form-control" id="compareTime"></select>
			                </div>
			            </div>
			        </div>
			        
			        <div class="form-group">
			        	<h4 class="col-xs-12">Filters</h4>	
						<div class="col-xs-12 col-sm-7">
							<div class="input-group">
							    <label class="checkbox-inline">
									<input type="checkbox" disabled checked> Exchange
								</label>
								<label class="checkbox-inline">
								  	<input type="checkbox" disabled checked> No Bid Reasons
								</label>
								<label class="checkbox-inline">
								  	<input type="checkbox" id="chkboxAdv"> Advertisers
								</label>
			           		</div>
			           	</div>
			         </div>
			         
			         <hr>
			         
				     <div class="form-group">
				       	<div class="col-xs-12 col-sm-7">      
							<div class="input-group">
						    	<div class="input-group-addon">Exchanges</div>
						        <select class="form-control" id="selectExch">
						        	<option>select</option>
						        </select>
						    </div>    
				        </div>
				     </div>
				       
				     <div class="form-group">
				       	<div class="col-xs-12 col-sm-7">      
					    	<div class="input-group">
						    	<div class="input-group-addon">Reasons</div>
						        <select class="form-control" id="selectReasons" multiple></select>
								<div class="input-group-addon">Multi-select</div>
							</div>    
				     	</div>
				     </div>
				     <div id="filters"></div>
			         <button id="btn_send" type="button" class="btn btn-primary">Submit</button>
			    </form>
		    </div>
		    <div class="col-xs-2 col-xs-push-10">
				<button type="button" class="btn btn-primary" id="btnHideSelects">Down</button>
			</div>
		</div>
    </div>
    <div id="tree-container"></div>   

  	<script src="js/jquery.min.js"></script>
  	<script src="js/jquery-ui-calendar.js"></script>
    <script src="js/dashboard.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/d3.v3.min.js"></script>
    <script src="js/tree.js"></script>
    <script src="js/noBidReasons.js"></script>
    
</body>
</html>