package servlet;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;


public class ServletElTiempo extends HttpServlet{
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		resp.setContentType("text/html; charset=UTF-8");
		PrintWriter out = resp.getWriter();
		if(null!=req.getParameter("comunidad"))
		{
			if(req.getParameter("comunidad").equals("MADRID") || req.getParameter("comunidad").equals("LEGANES") || req.getParameter("comunidad").equals("PARLA"))
			{
				String comunidad = null;
	
				switch (req.getParameter("comunidad")) {
				case "MADRID": comunidad = "28079";
					break;
				case "PARLA": comunidad = "28106";
					break;
				case "LEGANES": comunidad = "28074";
				break;
			}
	
			String s_url = "http://www.aemet.es/xml/municipios/localidad_"+comunidad+".xml";
		
			//CREAMOS EL PARSER
			SAXBuilder builder = new SAXBuilder();
	
			// CONSTRUIMOS EL ARBOL DOM A PARTIR DEL FICHERO XML
			Document documentoJDOM = null;
			try
			{
				documentoJDOM = builder.build(s_url);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			//MOSTRAMOS EL DOCUMENTO
			Element raiz = documentoJDOM.getRootElement();
			
			// RECORREMOS LOS HIJOS DE LA ETIQUETA RAÍZ
			List<Element> list_temp_hijo1 = raiz.getChildren();
			
			// VARIABLE PARA IR MOSTRANDO BIEN EL DIA DE LA TEMPERATURA INDICADA
			int diaactual = 0;
			Calendar c1 = Calendar.getInstance();
			out.println("<h1>"+req.getParameter("comunidad")+"</h1>");
			out.println("<body bgcolor=\"#BBBBBB\">");
			
			for (Element hijo1 : list_temp_hijo1) 
			{
				//PARA CADA HIJO, OBTENEMOS SUS HIJOS
				List<Element> list_temp_hijo2 = hijo1.getChildren();
	
				for (Element hijo2 : list_temp_hijo2)
				{
					List<Element> list_temp_hijo3 = hijo2.getChildren();
					if (hijo2.getName().contains("dia")) 
					{
	//					out.println("Prevision del tiempo a dia: "+(c1.get(Calendar.DAY_OF_MONTH)+diaactual) +"/"+ c1.get(Calendar.MONTH) +"/"+ c1.get(Calendar.YEAR));
						out.println("<br><br>Prevision del tiempo a dia "+hijo2.getAttribute("fecha").getValue()+" :<br>");
					}
					for (Element hijo3 : list_temp_hijo3)
					{
						if(hijo3.getName() == "temperatura")
						{
							List<Element> list_temp_max_min = hijo3.getChildren();
							for (Element temp : list_temp_max_min)
							{
								if(temp.getName() == "maxima")
								{
									out.println("Maxima: "+temp.getValue()+"<br>");
								}
								if(temp.getName() == "minima")
								{
									out.println("Minima: "+temp.getValue()+"<br>");
								}
								diaactual=diaactual++;
							}
						}
					}
				}
			}
			out.println("<br><br><br><br><br><br><br><br><br><p>Datos recogido de:</p><a href=\"http://www.aemet.es/es/eltiempo\">http://www.aemet.es/es/eltiempo</a>");
			}
		
			else
			{
				resp.sendRedirect("eltiempo.html");
//				resp.sendRedirect("http://lmgtfy.com/?q=Soy+tonto+y+busco+tonterias#seen");
			}
		}
		else
		{
			resp.sendRedirect("eltiempo.html");
//			resp.sendRedirect("http://lmgtfy.com/?q=Soy+tonto+y+busco+tonterias#seen");
		}
	}	
}
