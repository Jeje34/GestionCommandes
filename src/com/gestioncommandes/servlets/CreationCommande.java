package com.gestioncommandes.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.gestioncommandes.beans.Client;
import com.gestioncommandes.beans.Commande;
import com.gestioncommandes.forms.CreationClientForm;
import com.gestioncommandes.forms.CreationCommandeForm;

public class CreationCommande extends HttpServlet {
	
 
    public static final String ATT_COMMANDE  = "commande";
    public static final String ATT_FORM = "form";
 
    public static final String VUE_AFFICHER_COMMANDE = "/WEB-INF/afficherCommande.jsp";
    public static final String VUE_CREER_COMMANDE = "/WEB-INF/creerCommande.jsp";
    
    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
        this.getServletContext().getRequestDispatcher( VUE_CREER_COMMANDE ).forward( request, response );
    }

    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {

    	CreationCommandeForm form = new CreationCommandeForm();
        Commande commande = form.creerCommande(request);

        request.setAttribute( ATT_COMMANDE, commande );
        request.setAttribute( ATT_FORM, form );

        if ( form.getErreurs().isEmpty() ) {
            this.getServletContext().getRequestDispatcher( VUE_AFFICHER_COMMANDE ).forward( request, response );
        } else {
            /* Sinon, ré-affichage du formulaire de création avec les erreurs */
            this.getServletContext().getRequestDispatcher( VUE_CREER_COMMANDE ).forward( request, response );
        }
    }
}
