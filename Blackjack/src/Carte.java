public class Carte{
    
    private String valeur;
    private String enseigne;

    public Carte(String valeur, String enseigne){
        if (!checkValeur(valeur)){
            throw new IllegalArgumentException("Mauvaise valeur de carte");
        }
        if (!checkEnseigne(enseigne)){
            throw new IllegalArgumentException("Mauvaise enseigne de carte");
        }
        this.valeur = valeur;
        this.enseigne = enseigne;
    }

    public String getValeur(){
        String ret = new String(this.valeur);
        return ret;
    }

    public boolean checkValeur(String valeur){
        boolean ret = true;

        if(!valeur.equals("As") && !valeur.equals("2") && !valeur.equals("3") && !valeur.equals("4")
        && !valeur.equals("5") && !valeur.equals("6") && !valeur.equals("7") && !valeur.equals("8")
        && !valeur.equals("9") && !valeur.equals("10") && !valeur.equals("Valet") 
        && !valeur.equals("Dame") && !valeur.equals("Roi")){
            ret = false;
        }

        return ret;
    }

    public boolean checkEnseigne(String enseigne){
        boolean ret = true;

        if(!enseigne.equals("Pique") && !enseigne.equals("Trèfle") && !enseigne.equals("Coeur")
        && !enseigne.equals("Carreau")){
            ret = false;
        }

        return ret;
    }

    /**
     * Checks if the other object is a card and if it has the same value and colour as the current card
     * @return true if it is a card with the same value and colour
     */
    @Override
    public boolean equals(Object obj){
        boolean ret = false;

        if(obj != null){
            if(obj instanceof Carte){
                Carte objet = (Carte) obj;
                if (this.valeur.equals(objet.valeur) && this.enseigne.equals(objet.enseigne)){
                    ret = true;
                }
            }
        }

        return ret;
    }

    @Override
    public String toString(){
        String ret = valeur + " de " + enseigne;
        return ret;
    }
}