package servleterror;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class ServletError extends HttpServlet{
	
	final private Logger log = LogManager.getRootLogger();
	
	@Override
	protected void doGet(HttpServletRequest servletrequest, HttpServletResponse servletresponse) throws ServletException, IOException 
	{
		HttpServletRequest httpservletrequest = (HttpServletRequest)servletrequest;
		HttpServletResponse httpservletresponse = (HttpServletResponse)servletresponse;
		
		Throwable excepcion = (Throwable) httpservletrequest.getAttribute("javax.servlet.error.exception");
		Integer codigoHTTP = (Integer) httpservletrequest.getAttribute("javax.servlet.error.status_code");
		String nombreServlet = (String) httpservletrequest.getAttribute("javax.servlet.error.servlet_name");
		String detallefallo = (String) httpservletrequest.getAttribute("javax.servlet.error.message");
		if (null == nombreServlet )
		{
			nombreServlet = "Desconocido";
		} 
		String uriPedida = (String) httpservletrequest.getAttribute("javax.servlet.error.request_uri");
		if (null== uriPedida )
		{
			uriPedida = "Desconocida"; 
		}

		
//		log.info("Excepcion: "+excepción.toString());
		log.info("Codigo Error: "+codigoHTTP.toString());
		log.info("Servlet Error: "+nombreServlet.toString());
		log.info("URI Pedida Error: "+uriPedida.toString());
		log.info("Detalle Error: "+detallefallo.toString());
		
		httpservletresponse.setContentType("text/html; charset=UTF-8");
		PrintWriter out = httpservletresponse.getWriter();
		
		out.println("<h1 aling=\"center\">ERROR</h1>");
		out.println("<table border=\"1\">");
		out.println("<tr><td><p>Codigo de error</p></td><td><p>"+codigoHTTP.toString()+"</p></td></tr>");
		out.println("<tr><td><p>URI pedida</p></td><td><p>"+uriPedida.toString()+"</p></td></tr>");
		out.println("<tr><td><p>Servlet error</p></td><td><p>"+nombreServlet.toString()+"</p></td></tr>");
		out.println("<tr><td><p>Detalle Error</p></td><td><p>"+detallefallo.toString()+"</p></td></tr>");
		out.println("</table>");
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		super.doPost(req, resp);
	}

}
