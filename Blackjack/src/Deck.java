public class Deck{
    private Carte[] deck;
    private String[] valeurs;
    private String[] enseignes;

    public Deck(){
        this.valeurs = new String[13];
        this.valeurs[0] = "As";
        this.valeurs[1] = "2";
        this.valeurs[2] = "3";
        this.valeurs[3] = "4";
        this.valeurs[4] = "5";
        this.valeurs[5] = "6";
        this.valeurs[6] = "7";
        this.valeurs[7] = "8";
        this.valeurs[8] = "9";
        this.valeurs[9] = "10";
        this.valeurs[10] = "Valet";
        this.valeurs[11] = "Dame";
        this.valeurs[12] = "Roi";

        this.enseignes = new String[4];
        this.enseignes[0] = "Pique";
        this.enseignes[1] = "Trèfle";
        this.enseignes[2] = "Coeur";
        this.enseignes[3] = "Carreau";

        this.deck = new Carte[52];
        
        int index = 0;
        for (int i = 0; i < this.valeurs.length; i++){
            for (int j = 0; j < this.enseignes.length; j++){
                deck[index] = new Carte(this.valeurs[i], this.enseignes[j]);
                index++;
            }            
        }
    }

    public Deck(Carte[] jeu){
        if(jeu == null || jeu.length != 52){
            throw new IllegalArgumentException("Le tableau entré est incorrect");
        }

        this.deck = new Carte[52];
        for (int i = 0; i < 52; i ++){
            this.deck[i] = jeu[i];
        }
    }

    public Deck melange() throws RuntimeException{
        Deck deck = new Deck();
        Carte[] jeu = new Carte[52];

        for (int i = 0; i < 52; i++){
            int carteRandom = (int) (Math.random() * 52);
            Carte newCarte = deck.deck[carteRandom];

            if (newCarte == null){
                boolean exit = false;

                while(!exit){
                    carteRandom = (int) (Math.random() * 52);
                    newCarte = deck.deck[carteRandom];
                    if (newCarte != null){
                        exit = true;
                    }
                }
            }    
            
            jeu[i] = newCarte;
            deck.deck[carteRandom] = null;
        }

        Deck deckMelange = new Deck(jeu);

        if(!deckMelange.checkDeck()){
            throw new RuntimeException("Le nouvel ordonnancement n'est pas correct");
        }

        return deckMelange;
    }

    /**
     * Checks if the deck contains the same card twice
     * @return true if the deck doesn't, so if the deck is correct
     */
    public boolean checkDeck(){
        boolean ret = true;

        for (int i = 0; i < this.deck.length; i++){
            Carte compare = this.deck[i];
            for (int j = i + 1; j < this.deck.length; j++){
                Carte comparedTo = this.deck[j];
                if(compare.equals(comparedTo)){
                    ret = false;
                }
            }
        }

        return ret;
    }

    public Carte tirer(){
        Carte ret;

        int carteRandom = (int) (Math.random() * 52);
        ret = this.deck[carteRandom];

        if (ret == null){
                boolean exit = false;

                while(!exit){
                    carteRandom = (int) (Math.random() * 52);
                    ret = this.deck[carteRandom];
                    if (ret != null){
                        exit = true;
                    }
                }
            }

        this.deck[carteRandom] = null;

        return ret;
    }

    @Override
    public String toString(){
        String ret = "Ordre actuel : \n\n";

        for (int i = 0; i < 52; i++){
            ret += this.deck[i].toString() + "\n";
        }

        return ret;
    }
}