import java.util.*;

public class Blackjack{
    
    private String[] deck;

    public static void main(String[] args){
        System.out.println("Jeu de blackjack");
        
        Deck deck = new Deck();

        double balance = 0d;
        boolean continu = false;
        while (!continu){
            Scanner scMise = new Scanner(System.in);
            System.out.print("Quelle est votre quantité d'argent de départ ? : ");
            String departStr = scMise.nextLine();
            try{
                balance = Double.parseDouble(departStr);
                if (balance != 0d){
                    continu = true;
                } else {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e){
                System.out.print("Mauvaise valeur entrée, veuillez réessayer avec une valeur correcte : ");
            } finally {
                System.out.println("");
            }
        }

        boolean rejouer = true;
        
        while(rejouer){
            balance = jeu(deck, balance);
            if (balance > 0d){
                Scanner sc = new Scanner(System.in);
                System.out.print("Voulez vous rejouer ? Y/N : ");
                String answer = sc.nextLine();
                while (!answer.equals("y") && !answer.equals("Y") && !answer.equals("n") && !answer.equals("N")){
                    System.out.println("Mauvaise entrée, veuillez entrer Y ou N : ");
                    answer = sc.nextLine();
                }
                if (answer.equals("n") || answer.equals("N")){
                    rejouer = false;
                    sc.close();
                } else if(answer.equals("y") || answer.equals("Y")){
                    rejouer = true;
                }
                System.out.println("");
            } else {
                System.out.println("Vous n'avez plus d'argent à miser, fin de la partie");
                rejouer = false;
            }
        }
    }

    public static double jeu(Deck deck1, double balance){
        ArrayList<Carte> mainJ = new ArrayList<>();
        ArrayList<Carte> mainC = new ArrayList<>();

        Deck deck = deck1.melange();
        
        double mise = 0d;

        boolean continu = false;
        while (!continu){
            Scanner scMise2 = new Scanner(System.in);
            System.out.print("Votre mise : ");
            String miseStr = scMise2.nextLine();
            try{
                mise = Double.parseDouble(miseStr);
                if(mise <= balance && mise > 0){
                    continu = true;
                } else {
                    throw new NumberFormatException();
                }                
            } catch (NumberFormatException e){
                System.out.print("Mauvaise valeur entrée, veuillez réessayer avec une valeur correcte : ");
            } finally {
                System.out.println("");
            }
        }

        balance -= mise;
        double gain = 0d;
        System.out.println("Balance actuelle : " + balance + " euros");

        // Distribution
        mainJ.add(deck.tirer());
        System.out.println("Le croupier vous a tiré : " + mainJ.get(0));

        mainC.add(deck.tirer());
        System.out.println("Le croupier a tiré : " + mainC.get(0));

        mainJ.add(deck.tirer());
        System.out.println("Le croupier vous a tiré : " + mainJ.get(1));

        mainC.add(deck.tirer());
        System.out.println("Le croupier a tiré sa carte cachée");

        boolean blackjackC = false;
        boolean blackjackJ = false;

        if (valueOfHand(mainC) == 21 && valueOfHand(mainJ) == 21){
            System.out.println("Le croupier et vous avez un blackjack, égalité");
            blackjackC = true;
            blackjackJ = true;
            gain += mise;
            balance += gain;
            System.out.println("Balance : " + balance);
        } else if(valueOfHand(mainC) == 21 && valueOfHand(mainJ) != 21){
            System.out.println("Le croupier a un blackjack, vous avez perdu...");
            blackjackC = true;
            System.out.println("Balance : " + balance);
        } else if(valueOfHand(mainJ) == 21 && valueOfHand(mainC) != 21){
            System.out.println("Vous avez un blackjack, vous avez gagné !");
            blackjackJ = true;
            gain = mise * 2.5;
            balance += gain;
            System.out.println("Balance : " + balance);
        }

        if(!blackjackC && !blackjackJ){
            System.out.println("Valeur de la main : " + valueOfHand(mainJ));

            Scanner sc = new Scanner(System.in);
            System.out.print("\nVoulez-vous tirer (T) ou rester (R) ? : ");
            String answer = sc.nextLine();
            while (!answer.equals("t") && !answer.equals("T") && !answer.equals("r") && !answer.equals("R")){
                System.out.print("Mauvaise entrée, veuillez entrer T ou R : ");
                answer = sc.nextLine();
            }

            boolean tourC = false;
            boolean perdu = false;
            while(!tourC && !perdu){
                if (answer.equals("t") || answer.equals("T")){
                    Carte newCard = deck.tirer();
                    System.out.println("Vous avez tiré : " + newCard);
                    mainJ.add(newCard);
                    System.out.println("Valeur de la main : " + valueOfHand(mainJ));
                    if(valueOfHand(mainJ) > 21){
                        System.out.println("Vous avez brûlé...");
                        perdu = true;
                    } else if (valueOfHand(mainJ) != 21){
                        System.out.print("\nVoulez-vous tirer (T) ou rester (R) ? : ");
                        answer = sc.nextLine();
                        while (!answer.equals("t") && !answer.equals("T") && !answer.equals("r") && !answer.equals("R")){
                            System.out.println("Mauvaise entrée, veuillez entrer T ou R : ");
                            answer = sc.nextLine();
                        }
                    } else {
                        tourC = true;
                    }               
                } else if (answer.equals("r") || answer.equals("R")){
                    tourC = true;
                }
            }
            
            if(!perdu){
                System.out.println("La carte cachée du croupier est : " + mainC.get(1));
                System.out.println("Valeur de la main du croupier : " + valueOfHand(mainC));
                while(valueOfHand(mainC) < 17){
                    Carte newCard = deck.tirer();
                    System.out.println("Le croupier a tiré : " + newCard);
                    mainC.add(newCard);
                    System.out.println("Valeur de la main du croupier : " + valueOfHand(mainC));
                }
                if(valueOfHand(mainC) > 21){
                    System.out.println("Le croupier a brûlé, vous avez gagné !");
                    gain += mise * 2;
                    balance += gain;
                } else if (valueOfHand(mainC) > valueOfHand(mainJ)){
                    System.out.println("Le croupier gagne...");
                } else if (valueOfHand(mainC) < valueOfHand(mainJ)){
                    System.out.println("Vous avez gagné !");
                    gain += mise * 2;
                    balance += gain;
                } else if (valueOfHand(mainC) == valueOfHand(mainJ)){
                    System.out.println("Egalité");
                    gain += mise;
                    balance += gain;
                }
            }
            System.out.println("Balance : " + balance);
        }
        return balance;
    }

    public static int valueOfHand(ArrayList<Carte> main) throws RuntimeException{
        int ret = 0;
        int as = 0;
        ArrayList<Integer> indexAs = new ArrayList<>();

        for (int i = 0; i < main.size(); i++){
            Carte carte = main.get(i);

            if(carte.getValeur().equals("2")){
                ret += 2;
                if (indexAs.size() > 0){
                    if (ret > 21){
                        ret -= 10;
                        indexAs.remove(0);
                    }
                }
            } else if(carte.getValeur().equals("3")){
                ret += 3;
                if (indexAs.size() > 0){
                    if (ret > 21){
                        ret -= 10;
                        indexAs.remove(0);
                    }
                }
            } else if(carte.getValeur().equals("4")){
                ret += 4;
                if (indexAs.size() > 0){
                    if (ret > 21){
                        ret -= 10;
                        indexAs.remove(0);
                    }
                }
            } else if(carte.getValeur().equals("5")){
                ret += 5;
                if (indexAs.size() > 0){
                    if (ret > 21){
                        ret -= 10;
                        indexAs.remove(0);
                    }
                }
            } else if(carte.getValeur().equals("6")){
                ret += 6;
                if (indexAs.size() > 0){
                    if (ret > 21){
                        ret -= 10;
                        indexAs.remove(0);
                    }
                }
            } else if(carte.getValeur().equals("7")){
                ret += 7;
                if (indexAs.size() > 0){
                    if (ret > 21){
                        ret -= 10;
                        indexAs.remove(0);
                    }
                }
            } else if(carte.getValeur().equals("8")){
                ret += 8;
                if (indexAs.size() > 0){
                    if (ret > 21){
                        ret -= 10;
                        indexAs.remove(0);
                    }
                }
            } else if(carte.getValeur().equals("9")){
                ret += 9;
                if (indexAs.size() > 0){
                    if (ret > 21){
                        ret -= 10;
                        indexAs.remove(0);
                    }
                }
            } else if(carte.getValeur().equals("10") || carte.getValeur().equals("Valet")
            || carte.getValeur().equals("Dame") || carte.getValeur().equals("Roi")){
                ret += 10;
                if (indexAs.size() > 0){
                    if (ret > 21){
                        ret -= 10;
                        indexAs.remove(0);
                    }
                }
            } else if(carte.getValeur().equals("As")){
                indexAs.add(i);
                ret += 11;
                if (indexAs.size() > 0 && ret > 21){
                    ret -= 10;
                    indexAs.remove(0);
                }
            } else {
                throw new RuntimeException("Valeur impossible");
            }
        }
        
        return ret;
    }

    /*
     * Algo de Fisher-Yates
     * Gestion des As
     * Créer interface graphique
     * Tester apprentissage par renforcement
     */
}