package filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FiltroEntradas implements Filter{

	private final Logger log = LogManager.getRootLogger();
	
	@Override
	public void destroy() 
	{
	}

	@Override
	public void doFilter(ServletRequest servletrequest, ServletResponse servletresponse, FilterChain filterchain) throws IOException, ServletException 
	{
		HttpServletRequest httpservletrequest = (HttpServletRequest)servletrequest;
		HttpServletResponse httpservletresponse = (HttpServletResponse)servletresponse;
		
		httpservletresponse.setContentType("text/html; charset=UTF-8");
		PrintWriter out = httpservletresponse.getWriter();
		
		Cookie[] cookies = httpservletrequest.getCookies();
		
		if(null!=cookies)
		{
			boolean hay_cookie_entrada = false;
			for (int posicion=0;posicion<cookies.length;posicion++) 
			{
				if(cookies[posicion].getName().equals("entradas"))
				{
					hay_cookie_entrada = true;
					if(cookies[posicion].getValue().equals("3"))
					{
						out.println("No puedes acceder mas de 3 veces por día.");
					}
					else
					{
						Integer valueactualizado = Integer.parseInt(cookies[posicion].getValue())+1;
						cookies[posicion].setValue(valueactualizado.toString());
						log.info("Cookie entradas - registramos como entradas: "+cookies[posicion].getValue());
						httpservletresponse.addCookie(cookies[posicion]);
						filterchain.doFilter(httpservletrequest, httpservletresponse);
					}
				}
				if(posicion==cookies.length-1 && hay_cookie_entrada==false)
				{
					Cookie cookie_no_en_array = new Cookie("entradas", "0");
					cookie_no_en_array.setMaxAge(60*60);
					cookie_no_en_array.setValue("1");
					log.info("Cookie entradas - registramos como entradas: "+cookie_no_en_array.getValue());
					httpservletresponse.addCookie(cookie_no_en_array);
					filterchain.doFilter(httpservletrequest, httpservletresponse);
				}
			}
		}
		else
		{
			Cookie cookie_array_cookie_null = new Cookie("entradas", "0");
			cookie_array_cookie_null.setMaxAge(60*60);
			cookie_array_cookie_null.setValue("1");
			log.info("Cookie entradas - registramos como entradas: "+cookie_array_cookie_null.getValue());
			httpservletresponse.addCookie(cookie_array_cookie_null);
			filterchain.doFilter(httpservletrequest, httpservletresponse);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException 
	{
	}
}
