public class Conf {
    // Modifier cet valeur si vous voulez modifier la taille du buffeur des transformateurs
    public static final int maxBufferSize = 10;

    // Nombre de transformateur
    public static final int nbTransfo = 8;

    // Le temps moyen (en ms) entre chaque envoie d'une requête
    public static final int timer = 20;

    // Le nombre de traitement à faire pour chaque évaluation
    public static final int nbProcess = 800;

    // Le nombre d'évaluation pour chaque stratégie
    public static final int nbRun = 50;
}