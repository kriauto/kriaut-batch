package ma.kriauto.rest.util;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

@Component
public class Constant {
	
	private static Map labels = new HashMap();

	public static Map getLabels() {
		return labels;
	}

	public static void setLabels(Map labels) {
		Constant.labels = labels;
	}

	@PostConstruct
	public void initConstants() {
		labels.put("LOGIN_REQUIRED", "Le login est obligatoire");
		labels.put("PASSWORD_REQUIRED", "le mot de passe est obligatoire.");
		labels.put("LOGIN_NOT_FOUND", "le login n'existe pas.");
		labels.put("PASSWORD_FAILED", "le mot de passe est incorrect.");
		labels.put("LOGIN_SUCCES", "Vous êtes bien connectes.");
		labels.put("MAIL_REQUIRED", "l'email est obligatoire.");
		labels.put("MAIL_NOT_FOUND", "l'email n'existe pas.");
		labels.put("PASSWORD_SEND", "le nouveau mot de passe vient d'être envoyer");
		labels.put("LOGOUT_SUCCES", "vous êtes déconnectés");
		labels.put("STOP_CAR", "la demande d'arret est encours, veuillez attendre 2 minutes.");
		labels.put("START_CAR","la demande de démarrage est encours, veuillez attendre 2 minutes.");
		labels.put("GEOFENCE_SUCCES", "Les coordonnées sont enregistrées avec succès");
		labels.put("ACTION_FAILED", "cette action n'est pas autorisée");
		labels.put("TECHDATE_FAILED", "Veuillez saisir une date de controle technique valide Ex : 2000-01-31");
		labels.put("EMPKILO_FAILED", "Veuillez saisir un kilométrage pour vidange valide Ex : 5000");
		labels.put("INSDATE_FAILED", "Veuillez saisir une date de fin d'assurance valide Ex : 2000-01-31");
		labels.put("CIRDATE_FAILED", "Veuillez saisir une date de fin d'autorisation de circulation valide Ex : 2000-01-31");
		labels.put("SPEED_FAILED", "Veuillez saisir une vitesse maximale valide Ex : 120");
		labels.put("COURSE_FAILED", "Veuillez saisir une distance maximale valide Ex : 120");
		labels.put("FUEL_FAILED", "Veuillez saisir un niveau de carburant minimel valide Ex : 10");
		labels.put("TEMENGINE_FAILED", "Veuillez saisir une température du moteur maximale valide Ex : 100");
		labels.put("FRIDGEMIN_FAILED", "Veuillez saisir une température du frigot minimale valide Ex : -10");
		labels.put("FRIDGEMAX_FAILED", "Veuillez saisir une température du frigot maximale valide Ex : 25");
		labels.put("CAR_NOTFOUND", "Voiture n'existe pas");
	}
}
