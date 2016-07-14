package com.gestioncommandes.servlets;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gestioncommandes.beans.Client;
import com.gestioncommandes.forms.CreationClientForm;

public class CreationClient extends HttpServlet {
	
    public static final String VUE_AFFICHER_CLIENT = "/WEB-INF/afficherClient.jsp";
    public static final String VUE_CREER_CLIENT = "/WEB-INF/creerClient.jsp";
    
    public static final String ATT_CLIENT = "client";
    public static final String ATT_FORM = "form";
 
    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
        this.getServletContext().getRequestDispatcher( VUE_CREER_CLIENT ).forward( request, response );
    }
    
    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
       
    	CreationClientForm form = new CreationClientForm();
        Client client = form.creerClient(request);

        request.setAttribute( ATT_CLIENT, client );
        request.setAttribute( ATT_FORM, form );
        
        if ( form.getErreurs().isEmpty() ) {
            this.getServletContext().getRequestDispatcher( VUE_AFFICHER_CLIENT ).forward( request, response );
        } else {
            /* Sinon, ré-affichage du formulaire de création avec les erreurs */
            this.getServletContext().getRequestDispatcher( VUE_CREER_CLIENT ).forward( request, response );
        }
    }
}