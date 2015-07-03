package filter;

import java.io.IOException;

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

public class FiltroHistorial implements Filter{

	
	private final Logger log = LogManager.getRootLogger();

	@Override
	public void destroy() 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest servletrequest, ServletResponse servletresponse, FilterChain filterchain) throws IOException, ServletException 
	{
		HttpServletRequest httpservletrequest = (HttpServletRequest)servletrequest;
		HttpServletResponse httpservletresponse = (HttpServletResponse)servletresponse;
		
		Cookie[] cookies = httpservletrequest.getCookies();
		if(null!=cookies)
		{
			boolean hay_cookie_historial = false;
			for (int posicion=0;posicion<cookies.length;posicion++) 
			{
				if(cookies[posicion].getName().equals("historial"))
				{
					hay_cookie_historial = true;
					cookies[posicion].setValue(httpservletrequest.getRequestURI());
					log.info("Cookie historial - registramos como ultimo sitio: "+cookies[posicion].getValue());
					httpservletresponse.addCookie(cookies[posicion]);
				}
				if(posicion==cookies.length-1 && hay_cookie_historial==false)
				{
					Cookie cookie_no_en_array = new Cookie("historial", "vacio");
					cookie_no_en_array.setMaxAge(60*60);
					cookie_no_en_array.setValue(httpservletrequest.getRequestURI());
					log.info("Cookie historial - registramos como ultimo sitio: "+cookie_no_en_array.getValue());
					httpservletresponse.addCookie(cookie_no_en_array);
				}
			}
		}
		else
		{
			Cookie cookie_array_cookie_null = new Cookie("historial", "vacio");
			cookie_array_cookie_null.setMaxAge(60*60);
			cookie_array_cookie_null.setValue(httpservletrequest.getRequestURI());
			log.info("Cookie historial - registramos como ultimo sitio: "+cookie_array_cookie_null.getValue());
			httpservletresponse.addCookie(cookie_array_cookie_null);
		}
		filterchain.doFilter(httpservletrequest, httpservletresponse);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException 
	{
		// TODO Auto-generated method stub
		
	}

}
