/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.47
 * Generated at: 2019-09-11 03:59:28 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF.views;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class withdraw_002dbank_002derror_002dchecking_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(2);
    _jspx_dependants.put("/WEB-INF/views/common/footer-common.jspf", Long.valueOf(1567044191000L));
    _jspx_dependants.put("/WEB-INF/views/common/header-common.jspf", Long.valueOf(1568151456000L));
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fspring_005furl_0026_005fvar_005fvalue_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005foverdraftform_005fform_0026_005fmethod_005faction;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fmoney_005fformatNumber_0026_005fvalue_005ftype_005fpattern_005fminFractionDigits_005fnobody;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fspring_005furl_0026_005fvar_005fvalue_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005foverdraftform_005fform_0026_005fmethod_005faction = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fmoney_005fformatNumber_0026_005fvalue_005ftype_005fpattern_005fminFractionDigits_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fspring_005furl_0026_005fvar_005fvalue_005fnobody.release();
    _005fjspx_005ftagPool_005foverdraftform_005fform_0026_005fmethod_005faction.release();
    _005fjspx_005ftagPool_005fmoney_005fformatNumber_0026_005fvalue_005ftype_005fpattern_005fminFractionDigits_005fnobody.release();
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


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

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("<head>\n");
      out.write("\t<link href=\"webjars/bootstrap/3.3.6/css/bootstrap.min.css\" rel=\"stylesheet\">\n");
      out.write("\t");
      if (_jspx_meth_spring_005furl_005f0(_jspx_page_context))
        return;
      out.write('\n');
      out.write('	');
      if (_jspx_meth_spring_005furl_005f1(_jspx_page_context))
        return;
      out.write('\n');
      out.write('	');
      if (_jspx_meth_spring_005furl_005f2(_jspx_page_context))
        return;
      out.write("\n");
      out.write("\t<meta charset=\"UTF-8\">\n");
      out.write("\t<title>Withdraw</title>\n");
      out.write("\t<link rel=\"stylesheet\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${mainCss}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("\" />\n");
      out.write("</head>\n");
      out.write("\n");
      out.write("<body>\n");
      out.write("\t<div class=\"container\">\n");
      out.write("\t\t");
      out.write("<header>\n");
      out.write("\t<h2>GCU Online Bank<img src=\"/resources/images/gculogo.png\"/></h2>\n");
      out.write("</header>\n");
      out.write("<nav>\n");
      out.write("\t<div><a href=\"/deposit-bank\">Deposit</a></div>\n");
      out.write("\t<div><a href=\"/withdraw-bank\">Withdraw</a></div>\n");
      out.write("\t<div><a href=\"/transfer-bank\">Transfer</a></div>\n");
      out.write("\t<div><a href=\"/statements-bank\">Transactions</a></div>\n");
      out.write("\t<div><a href=\"/dashboard\">Dashboard</a></div>\n");
      out.write("\t<div><a href=\"/customer-settings\">Settings</a></div>\n");
      out.write("\t<div><a href=\"/logout\">Logout</a></div>\n");
      out.write("</nav>");
      out.write("\n");
      out.write("\t\t<section>\n");
      out.write("\t\t\t<h1>Checking OVERDRAFT Notice</h1>\n");
      out.write("\t\t\t");
      if (_jspx_meth_overdraftform_005fform_005f0(_jspx_page_context))
        return;
      out.write("\n");
      out.write("\t\t</section>\n");
      out.write("\t\t");
      out.write("<footer>\n");
      out.write("\t<p>\n");
      out.write("\t\t<a href=\"/about-bank\">About</a> | <a href=\"/logout\">Log Out</a>\n");
      out.write("\t</p>\n");
      out.write("</footer>");
      out.write("\n");
      out.write("\t</div>\n");
      out.write("\t<script src=\"webjars/jquery/1.9.1/jquery.min.js\"></script>\n");
      out.write("    <script src=\"webjars/bootstrap/3.3.6/js/bootstrap.min.js\"></script>\n");
      out.write("</body>\n");
      out.write("\n");
      out.write("</html>");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }

  private boolean _jspx_meth_spring_005furl_005f0(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  spring:url
    org.springframework.web.servlet.tags.UrlTag _jspx_th_spring_005furl_005f0 = (org.springframework.web.servlet.tags.UrlTag) _005fjspx_005ftagPool_005fspring_005furl_0026_005fvar_005fvalue_005fnobody.get(org.springframework.web.servlet.tags.UrlTag.class);
    _jspx_th_spring_005furl_005f0.setPageContext(_jspx_page_context);
    _jspx_th_spring_005furl_005f0.setParent(null);
    // /WEB-INF/views/withdraw-bank-error-checking.jsp(9,1) name = value type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_spring_005furl_005f0.setValue("/resources/css/style.css");
    // /WEB-INF/views/withdraw-bank-error-checking.jsp(9,1) name = var type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_spring_005furl_005f0.setVar("mainCss");
    int[] _jspx_push_body_count_spring_005furl_005f0 = new int[] { 0 };
    try {
      int _jspx_eval_spring_005furl_005f0 = _jspx_th_spring_005furl_005f0.doStartTag();
      if (_jspx_th_spring_005furl_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
    } catch (java.lang.Throwable _jspx_exception) {
      while (_jspx_push_body_count_spring_005furl_005f0[0]-- > 0)
        out = _jspx_page_context.popBody();
      _jspx_th_spring_005furl_005f0.doCatch(_jspx_exception);
    } finally {
      _jspx_th_spring_005furl_005f0.doFinally();
      _005fjspx_005ftagPool_005fspring_005furl_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_spring_005furl_005f0);
    }
    return false;
  }

  private boolean _jspx_meth_spring_005furl_005f1(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  spring:url
    org.springframework.web.servlet.tags.UrlTag _jspx_th_spring_005furl_005f1 = (org.springframework.web.servlet.tags.UrlTag) _005fjspx_005ftagPool_005fspring_005furl_0026_005fvar_005fvalue_005fnobody.get(org.springframework.web.servlet.tags.UrlTag.class);
    _jspx_th_spring_005furl_005f1.setPageContext(_jspx_page_context);
    _jspx_th_spring_005furl_005f1.setParent(null);
    // /WEB-INF/views/withdraw-bank-error-checking.jsp(10,1) name = value type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_spring_005furl_005f1.setValue("/resources/images/header.jpg");
    // /WEB-INF/views/withdraw-bank-error-checking.jsp(10,1) name = var type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_spring_005furl_005f1.setVar("headerImg");
    int[] _jspx_push_body_count_spring_005furl_005f1 = new int[] { 0 };
    try {
      int _jspx_eval_spring_005furl_005f1 = _jspx_th_spring_005furl_005f1.doStartTag();
      if (_jspx_th_spring_005furl_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
    } catch (java.lang.Throwable _jspx_exception) {
      while (_jspx_push_body_count_spring_005furl_005f1[0]-- > 0)
        out = _jspx_page_context.popBody();
      _jspx_th_spring_005furl_005f1.doCatch(_jspx_exception);
    } finally {
      _jspx_th_spring_005furl_005f1.doFinally();
      _005fjspx_005ftagPool_005fspring_005furl_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_spring_005furl_005f1);
    }
    return false;
  }

  private boolean _jspx_meth_spring_005furl_005f2(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  spring:url
    org.springframework.web.servlet.tags.UrlTag _jspx_th_spring_005furl_005f2 = (org.springframework.web.servlet.tags.UrlTag) _005fjspx_005ftagPool_005fspring_005furl_0026_005fvar_005fvalue_005fnobody.get(org.springframework.web.servlet.tags.UrlTag.class);
    _jspx_th_spring_005furl_005f2.setPageContext(_jspx_page_context);
    _jspx_th_spring_005furl_005f2.setParent(null);
    // /WEB-INF/views/withdraw-bank-error-checking.jsp(11,1) name = value type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_spring_005furl_005f2.setValue("/resources/images/footer.jpg");
    // /WEB-INF/views/withdraw-bank-error-checking.jsp(11,1) name = var type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_spring_005furl_005f2.setVar("footerImg");
    int[] _jspx_push_body_count_spring_005furl_005f2 = new int[] { 0 };
    try {
      int _jspx_eval_spring_005furl_005f2 = _jspx_th_spring_005furl_005f2.doStartTag();
      if (_jspx_th_spring_005furl_005f2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
    } catch (java.lang.Throwable _jspx_exception) {
      while (_jspx_push_body_count_spring_005furl_005f2[0]-- > 0)
        out = _jspx_page_context.popBody();
      _jspx_th_spring_005furl_005f2.doCatch(_jspx_exception);
    } finally {
      _jspx_th_spring_005furl_005f2.doFinally();
      _005fjspx_005ftagPool_005fspring_005furl_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_spring_005furl_005f2);
    }
    return false;
  }

  private boolean _jspx_meth_overdraftform_005fform_005f0(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  overdraftform:form
    org.springframework.web.servlet.tags.form.FormTag _jspx_th_overdraftform_005fform_005f0 = (org.springframework.web.servlet.tags.form.FormTag) _005fjspx_005ftagPool_005foverdraftform_005fform_0026_005fmethod_005faction.get(org.springframework.web.servlet.tags.form.FormTag.class);
    _jspx_th_overdraftform_005fform_005f0.setPageContext(_jspx_page_context);
    _jspx_th_overdraftform_005fform_005f0.setParent(null);
    // /WEB-INF/views/withdraw-bank-error-checking.jsp(22,3) name = action type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_overdraftform_005fform_005f0.setAction((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("/withdraw-bank-error-checking?amount=${reqamount}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
    // /WEB-INF/views/withdraw-bank-error-checking.jsp(22,3) name = method type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_overdraftform_005fform_005f0.setMethod("POST");
    int[] _jspx_push_body_count_overdraftform_005fform_005f0 = new int[] { 0 };
    try {
      int _jspx_eval_overdraftform_005fform_005f0 = _jspx_th_overdraftform_005fform_005f0.doStartTag();
      if (_jspx_eval_overdraftform_005fform_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\n");
          out.write("\t\t\t\t<p>\n");
          out.write(" \t\t\t\t\tA withdrawal of\n");
          out.write(" \t\t\t\t\t");
          if (_jspx_meth_money_005fformatNumber_005f0(_jspx_th_overdraftform_005fform_005f0, _jspx_page_context, _jspx_push_body_count_overdraftform_005fform_005f0))
            return true;
          out.write("\n");
          out.write(" \t\t\t\t\texceeds your available checking balance of\n");
          out.write(" \t\t\t\t\t");
          if (_jspx_meth_money_005fformatNumber_005f1(_jspx_th_overdraftform_005fform_005f0, _jspx_page_context, _jspx_push_body_count_overdraftform_005fform_005f0))
            return true;
          out.write(".<br>\n");
          out.write(" \t\t\t\t\tIf you proceed, your account will receive an overdraft charge of\n");
          out.write(" \t\t\t\t\t");
          if (_jspx_meth_money_005fformatNumber_005f2(_jspx_th_overdraftform_005fform_005f0, _jspx_page_context, _jspx_push_body_count_overdraftform_005fform_005f0))
            return true;
          out.write(".<br>\n");
          out.write(" \t\t\t\t\tClick <strong>Proceed</strong> to proceed with the withdrawal and receive the charge.<br>\n");
          out.write(" \t\t\t\t\tClick <strong>Cancel</strong> to cancel the transaction and return to the withdrawal page.<br>\n");
          out.write("\t\t\t\t\t<input class=\"btn btn-success\" type=\"submit\" name=\"reqamount\" value=\"Proceed\">\n");
          out.write("\t\t\t\t\t<a class=\"btn btn-primary\" href=\"/withdraw-bank\">Cancel</a>\n");
          out.write("\t\t\t\t</p>\n");
          out.write("\t\t\t");
          int evalDoAfterBody = _jspx_th_overdraftform_005fform_005f0.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_overdraftform_005fform_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
    } catch (java.lang.Throwable _jspx_exception) {
      while (_jspx_push_body_count_overdraftform_005fform_005f0[0]-- > 0)
        out = _jspx_page_context.popBody();
      _jspx_th_overdraftform_005fform_005f0.doCatch(_jspx_exception);
    } finally {
      _jspx_th_overdraftform_005fform_005f0.doFinally();
      _005fjspx_005ftagPool_005foverdraftform_005fform_0026_005fmethod_005faction.reuse(_jspx_th_overdraftform_005fform_005f0);
    }
    return false;
  }

  private boolean _jspx_meth_money_005fformatNumber_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_overdraftform_005fform_005f0, javax.servlet.jsp.PageContext _jspx_page_context, int[] _jspx_push_body_count_overdraftform_005fform_005f0)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  money:formatNumber
    org.apache.taglibs.standard.tag.rt.fmt.FormatNumberTag _jspx_th_money_005fformatNumber_005f0 = (org.apache.taglibs.standard.tag.rt.fmt.FormatNumberTag) _005fjspx_005ftagPool_005fmoney_005fformatNumber_0026_005fvalue_005ftype_005fpattern_005fminFractionDigits_005fnobody.get(org.apache.taglibs.standard.tag.rt.fmt.FormatNumberTag.class);
    _jspx_th_money_005fformatNumber_005f0.setPageContext(_jspx_page_context);
    _jspx_th_money_005fformatNumber_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_overdraftform_005fform_005f0);
    // /WEB-INF/views/withdraw-bank-error-checking.jsp(25,6) name = value type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_money_005fformatNumber_005f0.setValue((java.lang.Object) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${reqamount}", java.lang.Object.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
    // /WEB-INF/views/withdraw-bank-error-checking.jsp(25,6) name = type type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_money_005fformatNumber_005f0.setType("currency");
    // /WEB-INF/views/withdraw-bank-error-checking.jsp(25,6) name = pattern type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_money_005fformatNumber_005f0.setPattern("$#,##0.00;($#,##0.00)");
    // /WEB-INF/views/withdraw-bank-error-checking.jsp(25,6) name = minFractionDigits type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_money_005fformatNumber_005f0.setMinFractionDigits(2);
    int _jspx_eval_money_005fformatNumber_005f0 = _jspx_th_money_005fformatNumber_005f0.doStartTag();
    if (_jspx_th_money_005fformatNumber_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fmoney_005fformatNumber_0026_005fvalue_005ftype_005fpattern_005fminFractionDigits_005fnobody.reuse(_jspx_th_money_005fformatNumber_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fmoney_005fformatNumber_0026_005fvalue_005ftype_005fpattern_005fminFractionDigits_005fnobody.reuse(_jspx_th_money_005fformatNumber_005f0);
    return false;
  }

  private boolean _jspx_meth_money_005fformatNumber_005f1(javax.servlet.jsp.tagext.JspTag _jspx_th_overdraftform_005fform_005f0, javax.servlet.jsp.PageContext _jspx_page_context, int[] _jspx_push_body_count_overdraftform_005fform_005f0)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  money:formatNumber
    org.apache.taglibs.standard.tag.rt.fmt.FormatNumberTag _jspx_th_money_005fformatNumber_005f1 = (org.apache.taglibs.standard.tag.rt.fmt.FormatNumberTag) _005fjspx_005ftagPool_005fmoney_005fformatNumber_0026_005fvalue_005ftype_005fpattern_005fminFractionDigits_005fnobody.get(org.apache.taglibs.standard.tag.rt.fmt.FormatNumberTag.class);
    _jspx_th_money_005fformatNumber_005f1.setPageContext(_jspx_page_context);
    _jspx_th_money_005fformatNumber_005f1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_overdraftform_005fform_005f0);
    // /WEB-INF/views/withdraw-bank-error-checking.jsp(27,6) name = value type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_money_005fformatNumber_005f1.setValue((java.lang.Object) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${balance}", java.lang.Object.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
    // /WEB-INF/views/withdraw-bank-error-checking.jsp(27,6) name = type type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_money_005fformatNumber_005f1.setType("currency");
    // /WEB-INF/views/withdraw-bank-error-checking.jsp(27,6) name = pattern type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_money_005fformatNumber_005f1.setPattern("$#,##0.00;($#,##0.00)");
    // /WEB-INF/views/withdraw-bank-error-checking.jsp(27,6) name = minFractionDigits type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_money_005fformatNumber_005f1.setMinFractionDigits(2);
    int _jspx_eval_money_005fformatNumber_005f1 = _jspx_th_money_005fformatNumber_005f1.doStartTag();
    if (_jspx_th_money_005fformatNumber_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fmoney_005fformatNumber_0026_005fvalue_005ftype_005fpattern_005fminFractionDigits_005fnobody.reuse(_jspx_th_money_005fformatNumber_005f1);
      return true;
    }
    _005fjspx_005ftagPool_005fmoney_005fformatNumber_0026_005fvalue_005ftype_005fpattern_005fminFractionDigits_005fnobody.reuse(_jspx_th_money_005fformatNumber_005f1);
    return false;
  }

  private boolean _jspx_meth_money_005fformatNumber_005f2(javax.servlet.jsp.tagext.JspTag _jspx_th_overdraftform_005fform_005f0, javax.servlet.jsp.PageContext _jspx_page_context, int[] _jspx_push_body_count_overdraftform_005fform_005f0)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  money:formatNumber
    org.apache.taglibs.standard.tag.rt.fmt.FormatNumberTag _jspx_th_money_005fformatNumber_005f2 = (org.apache.taglibs.standard.tag.rt.fmt.FormatNumberTag) _005fjspx_005ftagPool_005fmoney_005fformatNumber_0026_005fvalue_005ftype_005fpattern_005fminFractionDigits_005fnobody.get(org.apache.taglibs.standard.tag.rt.fmt.FormatNumberTag.class);
    _jspx_th_money_005fformatNumber_005f2.setPageContext(_jspx_page_context);
    _jspx_th_money_005fformatNumber_005f2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_overdraftform_005fform_005f0);
    // /WEB-INF/views/withdraw-bank-error-checking.jsp(29,6) name = value type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_money_005fformatNumber_005f2.setValue((java.lang.Object) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${overdraft}", java.lang.Object.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
    // /WEB-INF/views/withdraw-bank-error-checking.jsp(29,6) name = type type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_money_005fformatNumber_005f2.setType("currency");
    // /WEB-INF/views/withdraw-bank-error-checking.jsp(29,6) name = pattern type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_money_005fformatNumber_005f2.setPattern("$#,##0.00;($#,##0.00)");
    // /WEB-INF/views/withdraw-bank-error-checking.jsp(29,6) name = minFractionDigits type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_money_005fformatNumber_005f2.setMinFractionDigits(2);
    int _jspx_eval_money_005fformatNumber_005f2 = _jspx_th_money_005fformatNumber_005f2.doStartTag();
    if (_jspx_th_money_005fformatNumber_005f2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fmoney_005fformatNumber_0026_005fvalue_005ftype_005fpattern_005fminFractionDigits_005fnobody.reuse(_jspx_th_money_005fformatNumber_005f2);
      return true;
    }
    _005fjspx_005ftagPool_005fmoney_005fformatNumber_0026_005fvalue_005ftype_005fpattern_005fminFractionDigits_005fnobody.reuse(_jspx_th_money_005fformatNumber_005f2);
    return false;
  }
}
