package com.gestioncommandes.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.gestioncommandes.beans.Client;

public class CreationClientForm {
	
    public static final String CHAMP_NOM       = "nomClient";
    public static final String CHAMP_PRENOM    = "prenomClient";
    public static final String CHAMP_ADRESSE   = "adresseClient";
    public static final String CHAMP_TELEPHONE = "telephoneClient";
    public static final String CHAMP_EMAIL     = "emailClient";
    
    protected String resultat;
    protected Map<String, String> erreurs = new HashMap<String, String>();
    
    public Client creerClient (HttpServletRequest request) {
    	
    	 String nom = getValeurChamp(request, CHAMP_NOM );
         String prenom = getValeurChamp(request, CHAMP_PRENOM );
         String adresse = getValeurChamp(request, CHAMP_ADRESSE );
         String telephone = getValeurChamp(request, CHAMP_TELEPHONE );
         String email = getValeurChamp(request, CHAMP_EMAIL );

         Client client = new Client();
         
         try {
             validationNom(nom);
         } catch ( Exception e ) {
             setErreur( CHAMP_NOM, e.getMessage() );
         }
         client.setNom(nom);
         
         try {
             validationPrenom(prenom);
         } catch ( Exception e ) {
             setErreur( CHAMP_PRENOM, e.getMessage() );
         }
         client.setPrenom(prenom);
         
         try {
             validationAdresse(adresse);
         } catch ( Exception e ) {
             setErreur( CHAMP_ADRESSE, e.getMessage() );
         }
         client.setAdresse(adresse);
         
         try {
             validationTelephone(telephone);
         } catch ( Exception e ) {
             setErreur( CHAMP_TELEPHONE, e.getMessage() );
         }
         client.setTelephone(telephone);
         
         try {
             validationEmail(email);
         } catch ( Exception e ) {
             setErreur( CHAMP_EMAIL, e.getMessage() );
         }
         client.setEmail(email);
         
         if ( erreurs.isEmpty() ) {
             resultat = "Client créé avec succès !";
         } else {
             resultat = "Échec de la création du client.";
         }
         
         return client;
    }
    
    private void validationEmail( String email ) throws Exception {        
        if ((email != null) && (!email.matches( "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" ))) {
            throw new Exception( "Merci de saisir une adresse mail valide." );
        }
    }

    private void validationAdresse( String adresse ) throws Exception {
        if ( adresse != null ) {
            if ( adresse.length() < 10 ) {
                throw new Exception( "L'adresse de livraison doit contenir au moins 10 caractères." );
            }
        } else {
            throw new Exception( "Merci d'entrer une adresse de livraison." );
        }
    }

    private void validationTelephone( String telephone ) throws Exception {
        if ( telephone != null ) {
            if ( !telephone.matches( "^\\d+$" ) ) {
                throw new Exception( "Le numéro de téléphone doit uniquement contenir des chiffres." );
            } else if ( telephone.length() < 4 ) {
                throw new Exception( "Le numéro de téléphone doit contenir au moins 4 chiffres." );
            }
        } else {
            throw new Exception( "Merci d'entrer un numéro de téléphone." );
        }
    }

	private void validationPrenom(String prenom) throws Exception  {
		if ( prenom != null && prenom.length() < 2 ) {
            throw new Exception( "Le prenom doit contenir au moins 2 caractères." );
        }	
	}
	
	private void validationNom( String nom ) throws Exception {
		if ( nom != null ) {
		    if ( nom.length() < 2 ) {
		        throw new Exception( "Le nom d'utilisateur doit contenir au moins 2 caractères." );
		    }
		} else {
		    throw new Exception( "Merci d'entrer un nom d'utilisateur." );
		}
	}

	public String getResultat() {
        return resultat;
    }

    public Map<String, String> getErreurs() {
        return erreurs;
    }
    
    public void setErreur( String champ, String message ) {
        erreurs.put( champ, message );
    }
    
    public String getValeurChamp( HttpServletRequest request, String nomChamp ) {
        String valeur = request.getParameter( nomChamp );
        if ( valeur == null || valeur.trim().length() == 0 ) {
            return null;
        } else {
            return valeur.trim();
        }
    }
}
