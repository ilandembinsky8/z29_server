<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
    <meta name="description" content=""/>
    <meta name="author" content=""/>

	<link href="img/favicon.ico" rel="icon" type="image/x-icon" />
    <link href="css/bootstrap.min.css" rel="stylesheet"/>
    <link href="css/sb-admin.css" rel="stylesheet"/>
    <link href="css/dashboard.css" rel="stylesheet"/>
    <!-------------Css for multiSelect-------------->
    <link rel="stylesheet" href="css/kendo.common.min.css" />
    <link rel="stylesheet" href="css/kendo.default.min.css" />
    <link rel="stylesheet" href="css/kendo.default.mobile.min.css" />
    
    <link rel="stylesheet" href="css/kendo.common-material.min.css" />
    <link rel="stylesheet" href="css/kendo.material.min.css" />
    <link rel="stylesheet" href="css/kendo.material.mobile.min.css" />
   
    <link href="font-awesome/css/font-awesome.min.css" rel="stylesheet"/>
    <link href="css/no-bid-reason.css" rel="stylesheet"/>
     
</head>
<body>
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
                <a class="navbar-brand" href="kpi.html">Admin <i class="fa fa-fw fa-dashboard"></i> Dashboard</a>
            </div>
            <!-- Top Menu Items -->
            <ul class="nav navbar-right top-nav">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user"></i> Admin <b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li>
                            <a href="#"><i class="fa fa-fw fa-user"></i> Profile</a>
                        </li>
                        <li>
                            <a href="#"><i class="fa fa-fw fa-gear"></i> Settings</a>
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
			            <div class="col-xs-12 col-sm-2 margin-bottom-6">
			            	<label class="control-label">Reference</label>
			            </div>
			            <div class="col-xs-12 col-sm-10">
			            	<div class="input-group col-sm-3">
			            		<div class="input-group-addon">Date</div>
			                    <input class="form-control date" type="text" id="referDate"/>
			                </div>
			                <div class="input-group col-sm-3">
			            		<div class="input-group-addon">Time</div>
			            		<input type="number" min="0" max="23" step="1" value="0" class="form-control" id="referTime"/>
			                </div>
			            </div>
			           	<div class="col-xs-12 col-sm-2 margin-bottom-6">
			            	<label class="control-label">Compare To</label>
			            </div>
			            <div class="col-xs-12 col-sm-10">
			            	<div class="input-group col-sm-3">
			            		<div class="input-group-addon">Date</div>
			                    <input class="form-control date" type="text" id="compareDate"/>
			                </div>
			                <div class="input-group col-sm-3">
			            		<div class="input-group-addon">Time</div>
			                    <input type="number" min="0" max="23" step="1" value="0" class="form-control" id="compareTime"/>
			                </div>
			            </div>
			        </div>
			        
			        <div class="form-group">
			        	<h4 class="col-xs-12">Filter</h4> 
			        	<div class="col-xs-12">      
					        <div class="input-group col-sm-4">
					        	<div class="input-group-addon">Exchange</div>
					          	<select class="form-control" id="exSelect">
					               	<option>select</option>
						  		</select>
					        </div>    
				          	<div class="input-group col-xs-12 col-sm-4" id="noBid"></div>
			        	</div>
			      	    <div class="input-group col-xs-12 col-sm-4" id="btn_send"></div>
			        </div> 
			           
			    </form>
		    </div>
		    <div class="col-sm-2 col-sm-push-10">
				<button type="button" class="btn btn-primary" id="btnHideSelects">Down</button>
			</div>
		</div>
    </div>
    <div id="tree-container"></div>   

  	<script src="js/jquery.min.js"></script>
    <script src="js/dashboard.js"></script>
    <!---------	js for multiSelect-------->
    <script src="js/kendo.all.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/d3.v3.min.js"></script>
    <script src="js/tree.js"></script>
    <script src="js/noBidReasons.js"></script>
    
</body>
</html>