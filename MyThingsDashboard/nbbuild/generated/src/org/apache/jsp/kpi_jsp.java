package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class kpi_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\r\n");
      out.write("\r\n");
      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n");

	if(session.getAttribute("user") == null)
		response.sendRedirect("login.html");

      out.write("\r\n");
      out.write("<html xmlns=\"http://www.w3.org/1999/xhtml\" lang=\"en\">\r\n");
      out.write("<head>\r\n");
      out.write("\r\n");
      out.write("\t<title>KPI</title>\r\n");
      out.write("\t\r\n");
      out.write("\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\r\n");
      out.write("    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"/>\r\n");
      out.write("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"/>\r\n");
      out.write("    \r\n");
      out.write("    <link href=\"img/favicon.ico\" rel=\"icon\" type=\"image/x-icon\" />\r\n");
      out.write("    <link href=\"css/bootstrap.min.css\" rel=\"stylesheet\"/>\r\n");
      out.write("    <link href=\"css/sb-admin.css\" rel=\"stylesheet\"/>\r\n");
      out.write("    <link href=\"css/dashboard.css\" rel=\"stylesheet\"/>\r\n");
      out.write("    <link href=\"css/kpi.css\" rel=\"stylesheet\"/>\r\n");
      out.write("    <link href=\"font-awesome/css/font-awesome.min.css\" rel=\"stylesheet\" type=\"text/css\"/>\r\n");
      out.write("     <link href=\"css/c3.min.css\" rel=\"stylesheet\" type=\"text/css\"/>\r\n");
      out.write("\t<link href=\"css/kpi-graph.css\" rel=\"stylesheet\"/>\r\n");
      out.write("\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("\t<div class=\"top-message\">Message</div>\r\n");
      out.write("\t<div class=\"blackWindow\"></div>\r\n");
      out.write("\t<div class=\"loader\"></div> \r\n");
      out.write("\t<div class=\"container\">\r\n");
      out.write("\r\n");
      out.write("        <!-- Navigation -->\r\n");
      out.write("        <nav class=\"navbar navbar-inverse navbar-fixed-top\" role=\"navigation\">\r\n");
      out.write("            <!-- Brand and toggle get grouped for better mobile display -->\r\n");
      out.write("            <div class=\"navbar-header\">\r\n");
      out.write("                <button type=\"button\" class=\"navbar-toggle\" data-toggle=\"collapse\" data-target=\".navbar-ex1-collapse\">\r\n");
      out.write("                    <span class=\"sr-only\">Toggle navigation</span>\r\n");
      out.write("                    <span class=\"icon-bar\"></span>\r\n");
      out.write("                    <span class=\"icon-bar\"></span>\r\n");
      out.write("                    <span class=\"icon-bar\"></span>\r\n");
      out.write("                </button>\r\n");
      out.write("                <a class=\"navbar-brand\" href=\"kpi\">Admin <i class=\"fa fa-fw fa-dashboard\"></i> Dashboard</a>\r\n");
      out.write("            </div>\r\n");
      out.write("            <!-- Top Menu Items -->\r\n");
      out.write("            <ul class=\"nav navbar-right top-nav\">\r\n");
      out.write("                <li class=\"dropdown\">\r\n");
      out.write("                    <a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\"><i class=\"fa fa-user\"></i> Admin <b class=\"caret\"></b></a>\r\n");
      out.write("                    <ul class=\"dropdown-menu\">\r\n");
      out.write("                        <li>\r\n");
      out.write("                            <a href=\"#\"><i class=\"fa fa-fw fa-user\"></i> Profile</a>\r\n");
      out.write("                        </li>\r\n");
      out.write("                        <li>\r\n");
      out.write("                            <a href=\"#\"><i class=\"fa fa-fw fa-gear\"></i> Settings</a>\r\n");
      out.write("                        </li>\r\n");
      out.write("                        <li class=\"divider\"></li>\r\n");
      out.write("                        <li>\r\n");
      out.write("                            <a href=\"\" id=\"btn_logOut\"><i class=\"fa fa-fw fa-power-off\"></i> Log Out</a>\r\n");
      out.write("                        </li>\r\n");
      out.write("                    </ul>\r\n");
      out.write("                </li>\r\n");
      out.write("            </ul>\r\n");
      out.write("            <div class=\"collapse navbar-collapse navbar-ex1-collapse nav navbar-left top-nav\">\r\n");
      out.write("\t\t    \t<ul class=\"nav navbar-nav\">\r\n");
      out.write("\t\t        \t<li class=\"active\">\r\n");
      out.write("\t\t            \t<a href=\"kpi\"><i class=\"fa fa-bar-chart\"></i> KPI's</a>\r\n");
      out.write("\t\t            </li>\r\n");
      out.write("\t\t            <li>\r\n");
      out.write("\t\t            \t<a href=\"noBidReason\"><i class=\"fa fa-dot-circle-o\"></i> No Bid Reason</a>\r\n");
      out.write("\t\t            </li>\r\n");
      out.write("\t\t        </ul>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("        </nav>\r\n");
      out.write(" \t\r\n");
      out.write("\t\t<div class=\"row\">\r\n");
      out.write("\t\t\t<div class=\"col-xs-12\">\r\n");
      out.write("\t\t    \t<h3>Fill to show a bar chart</h3>\r\n");
      out.write("                        <input type=\"checkbox\" id=\"clicks\" checked>Clicks Line</input>\r\n");
      out.write("                        <input type=\"checkbox\" id=\"impressions\" checked>Impressions Line</input>\r\n");
      out.write("\t\t    </div>\r\n");
      out.write("\t\t    <div class=\"col-xs-12\">\r\n");
      out.write("\t\t    \t<form class=\"form-horizontal\" role=\"form\">\r\n");
      out.write("\t\t    \t\r\n");
      out.write("\t\t    \t\t<div class=\"form-group\">\r\n");
      out.write("\t\t            \t<label for=\"telnum\" class=\"col-sm-2 control-label\">Date</label>\r\n");
      out.write("\t\t                <div class=\"col-xs-12 col-sm-10 col-lg-5 margin-bottom-6\">\r\n");
      out.write("\t\t                \t<div class=\"input-group\">\r\n");
      out.write("\t\t                    \t<div class=\"input-group-addon\">From</div>\r\n");
      out.write("\t\t                        <select class=\"form-control\" id=\"fromDate\">\r\n");
      out.write("\t\t                        \t<option>select</option>\r\n");
      out.write("\t\t                        </select>\r\n");
      out.write("\t\t                    </div>\r\n");
      out.write("\t\t                </div>\r\n");
      out.write("\t\t                <div class=\"col-xs-12 col-sm-10 col-sm-offset-2 col-lg-5 col-lg-offset-0\">\r\n");
      out.write("\t\t                \t<div class=\"input-group\">\r\n");
      out.write("\t\t                    \t<div class=\"input-group-addon\">To</div>\r\n");
      out.write("\t\t                        <select class=\"form-control\" id=\"toDate\">\r\n");
      out.write("\t\t                        \t<option>select</option>\r\n");
      out.write("\t\t                        </select>\r\n");
      out.write("\t\t                    </div>\r\n");
      out.write("\t\t                </div>\r\n");
      out.write("\t\t            </div>\r\n");
      out.write("\t\t            \r\n");
      out.write("\t\t        \t<div class=\"form-group\">\r\n");
      out.write("\t\t            \t<label for=\"firstname\" class=\"col-sm-2 control-label\">Exchange</label>         \r\n");
      out.write("\t\t            \t<div class=\"col-sm-10\">\r\n");
      out.write("\t\t            \t\t<select class=\"form-control\" id=\"exch\">\r\n");
      out.write("\t\t                    \t<option>select</option>\r\n");
      out.write("\t\t\t  \t\t\t\t</select>\r\n");
      out.write("\t\t            \t</div>\r\n");
      out.write("\t\t            </div>\r\n");
      out.write("\t\t            \r\n");
      out.write("\t\t            <div class=\"form-group\">\r\n");
      out.write("\t\t            \t<label for=\"lastname\" class=\"col-sm-2 control-label\">Advertiser</label>\r\n");
      out.write("\t\t                <div class=\"col-sm-10\">\r\n");
      out.write("\t\t                \t<select class=\"form-control\" id=\"adv\">\r\n");
      out.write("\t\t\t  \t\t\t\t\t<option>select</option>\r\n");
      out.write("\t\t\t  \t\t\t\t</select>\r\n");
      out.write("\t\t                </div>\r\n");
      out.write("\t\t            </div>\r\n");
      out.write("\t\t            \r\n");
      out.write("\t\t            <div class=\"form-group\">\r\n");
      out.write("\t\t            \t<label for=\"emailid\" class=\"col-sm-2 control-label\">Campaign</label>\r\n");
      out.write("\t\t                <div class=\"col-sm-10\">\r\n");
      out.write("\t\t                \t<select class=\"form-control\" id=\"camp\">\r\n");
      out.write("\t\t\t  \t\t\t\t\t<option>select</option>\r\n");
      out.write("\t\t\t  \t\t\t\t</select>\r\n");
      out.write("\t\t                </div>\r\n");
      out.write("\t\t            </div>\r\n");
      out.write("\t\t            \r\n");
      out.write("\t\t            <div class=\"form-group\">\r\n");
      out.write("\t\t            \t<label for=\"emailid\" class=\"col-sm-2 control-label\">Group</label>\r\n");
      out.write("\t\t                <div class=\"col-sm-10\">\r\n");
      out.write("\t\t                \t<select class=\"form-control\" id=\"group\">\r\n");
      out.write("\t\t\t  \t\t\t\t\t<option>select</option>\r\n");
      out.write("\t\t\t  \t\t\t\t</select>\r\n");
      out.write("\t\t                </div>\r\n");
      out.write("\t\t            </div>\r\n");
      out.write("\t\t            \r\n");
      out.write("\t\t             <div class=\"form-group\">\r\n");
      out.write("\t\t            \t<label for=\"emailid\" class=\"col-sm-2 control-label\">Creative</label>\r\n");
      out.write("\t\t                <div class=\"col-sm-10\">\r\n");
      out.write("\t\t                \t<select class=\"form-control\" id=\"creative\">\r\n");
      out.write("\t\t\t  \t\t\t\t\t<option>select</option>\r\n");
      out.write("\t\t\t  \t\t\t\t</select>\r\n");
      out.write("\t\t                </div>\r\n");
      out.write("                                \r\n");
      out.write("\t\t            </div>\r\n");
      out.write("\t\t            \r\n");
      out.write("\t\t            <div class=\"form-group\">                        \r\n");
      out.write("\t\t            \t<div class=\"col-sm-offset-2 col-sm-10\">\r\n");
      out.write("\t\t                \t<button type=\"button\" class=\"btn btn-primary\">Send</button>\r\n");
      out.write("\t\t                </div>\r\n");
      out.write("\t\t            </div>\r\n");
      out.write("\t\t            \r\n");
      out.write("\t\t        </form>\r\n");
      out.write("\t\t    </div>\r\n");
      out.write("\t\t    <div class=\"col-sm-2 col-sm-push-10\">\r\n");
      out.write("\t\t    \t<button type=\"button\" class=\"btn btn-primary\" id=\"btnHideSelects\">Down</button>\r\n");
      out.write("\t\t    </div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t<div id=\"chart\"></div>\r\n");
      out.write("    </div>\r\n");
      out.write("\r\n");
      out.write("    <script src=\"js/jquery.min.js\"></script>\r\n");
      out.write("    <script src=\"js/dashboard.js\"></script>\r\n");
      out.write("    <script src=\"js/bootstrap.min.js\"></script>\r\n");
      out.write("    <script src=\"js/d3.v3.min.js\"></script>\r\n");
      out.write("     <script src=\"js/c3.min.js\"></script>\r\n");
      out.write("\t<script src=\"js/kpi-graph.js\"></script>\r\n");
      out.write("    <script src=\"js/kpi.js\"></script>\r\n");
      out.write("    \r\n");
      out.write("</body>\r\n");
      out.write("</html>");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
