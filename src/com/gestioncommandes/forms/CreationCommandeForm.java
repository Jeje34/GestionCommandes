package com.gestioncommandes.forms;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.gestioncommandes.beans.Client;
import com.gestioncommandes.beans.Commande;

public class CreationCommandeForm extends CreationClientForm {
	
    public static final String CHAMP_DATE             = "dateCommande";
    public static final String CHAMP_MONTANT          = "montantCommande";
    public static final String CHAMP_MODE_PAIEMENT    = "modePaiementCommande";
    public static final String CHAMP_STATUT_PAIEMENT  = "statutPaiementCommande";
    public static final String CHAMP_MODE_LIVRAISON   = "modeLivraisonCommande";
    public static final String CHAMP_STATUT_LIVRAISON = "statutLivraisonCommande";
    
    public static final String FORMAT_DATE            = "dd/MM/yyyy HH:mm:ss";
    
    public Commande creerCommande(HttpServletRequest request) {
    	
    	Commande commande = new Commande();
    	
    	Client client = creerClient(request);
    	commande.setClient(client);
    	
    	DateTime dt = new DateTime();
    	DateTimeFormatter formatter = DateTimeFormat.forPattern( FORMAT_DATE );
    	String date = dt.toString( formatter );
    	commande.setDate(date);
    	
    	String montant = getValeurChamp(request, CHAMP_MONTANT);    	
        String modePaiement = getValeurChamp(request, CHAMP_MODE_PAIEMENT );
        String statutPaiement = getValeurChamp(request, CHAMP_STATUT_PAIEMENT );
        String modeLivraison = getValeurChamp(request, CHAMP_MODE_LIVRAISON );
        String statutLivraison = getValeurChamp(request, CHAMP_STATUT_LIVRAISON );
        
        double valeurMontant = -1;
        try {
            valeurMontant = validationMontant(montant);
        } catch ( Exception e ) {
            setErreur(CHAMP_MONTANT, e.getMessage());
        }
        commande.setMontant( valeurMontant );
        
        try {
            validationModePaiement(modePaiement);
        } catch ( Exception e ) {
            setErreur( CHAMP_MODE_PAIEMENT, e.getMessage() );
        }
        commande.setModePaiement(modePaiement);
        
        try {
            validationStatutPaiement(statutPaiement);
        } catch ( Exception e ) {
            setErreur( CHAMP_STATUT_PAIEMENT, e.getMessage() );
        }
        commande.setStatutPaiement(statutPaiement);
        
        try {
            validationModeLivraison(modeLivraison);
        } catch ( Exception e ) {
            setErreur( CHAMP_MODE_LIVRAISON, e.getMessage() );
        }
        commande.setModeLivraison(modeLivraison);
        
        try {
            validationStatutLivraison(statutLivraison);
        } catch ( Exception e ) {
            setErreur( CHAMP_STATUT_LIVRAISON, e.getMessage() );
        }
        commande.setStatutLivraison(statutLivraison);
        
        if ( erreurs.isEmpty() ) {
            resultat = "Commande crée avec succès !";
        } else {
            resultat = "Échec de la création de la commande.";
        }
        
        return commande;
    }
    
    private double validationMontant( String montant ) throws Exception {
        double valeurMontant;
        if (montant != null) {
            try {
            	valeurMontant = Double.parseDouble(montant);
                if (valeurMontant < 0) {
                    throw new Exception("Le montant doit être un nombre positif.");
                }
            } catch (NumberFormatException e) {
            	valeurMontant = -1;
                throw new Exception("Le montant doit être un nombre.");
            }
        } else {
        	valeurMontant = -1;
            throw new Exception("Merci d'entrer un montant.");
        }
        return valeurMontant;
    }

	private void validationMontant(Double montant) throws Exception {
    	if (montant != null) {
    		if (montant < 0) {
    			throw new Exception("Le montant doit être un nombre positif."); 
    		}	   		
    	}		
    	else throw new Exception("Veuillez saisir un montant.");    	
	}

    private void validationModePaiement( String modePaiement ) throws Exception {
        if ( modePaiement != null ) {
            if ( modePaiement.length() < 2 ) {
                throw new Exception( "Le mode de paiement doit contenir au moins 2 caractères." );
            }
        } else {
            throw new Exception( "Merci d'entrer un mode de paiement." );
        }
    }

	private void validationStatutPaiement(String statutPaiement) throws Exception  {
		if ( statutPaiement != null && statutPaiement.length() < 2 ) {
            throw new Exception( "Le statut du paiement doit contenir au moins 2 caractères." );
        }		
	}

	private void validationModeLivraison( String modeLivraison ) throws Exception {
        if ( modeLivraison != null ) {
            if ( modeLivraison.length() < 2 ) {
                throw new Exception( "Le mode de livraison doit contenir au moins 2 caractères." );
            }
        } else {
            throw new Exception( "Merci d'entrer un mode de livraison." );
        }
    }

	private void validationStatutLivraison(String statutLivraison) throws Exception {
		if ( statutLivraison != null && statutLivraison.length() < 2 ) {
            throw new Exception( "Le statut de la livraison doit contenir au moins 2 caractères." );
        }		
	}
}
