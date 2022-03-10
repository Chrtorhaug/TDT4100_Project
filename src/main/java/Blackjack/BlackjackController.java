package Blackjack;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class BlackjackController { 

    private BlackjackPlayer player;
    private CardDeck deck;
    private BlackJackDealer dealer;
    
    @FXML
    private ListView<Card> PlayerCards, DealerCards;

    @FXML
    private Label PlayerScore = new Label("0");

    @FXML
    private Label DealerScore = new Label("0"); 

    @FXML
    public void initialize() {
        this.deck = new CardDeck(5);
        this.dealer = new BlackJackDealer(deck);
        this.player = new BlackjackPlayer(5, "Jens", deck);
    }

    private void updateListView(ListView<Card> lv, PlayerInterface pl) {
        lv.getItems().clear();

        if (pl.getName() == "Dealer") {
            if (!player.checkPlaying()) { //Hvis runden er ferdig skal Dealer vise alle kortene sine
                lv.getItems().addAll(pl.getHand());
            }
            else lv.getItems().addAll(pl.getHand().get(0)); // Hvis runden ikke er ferdig skal Dealer bare vise første kort
        }
        else {
            pl.newHand(deck);
            lv.getItems().addAll(pl.getHand());
        }
    }

    private void updateLabel(Label lb, PlayerInterface pl) {
        if (pl.getName() == "Dealer" && player.checkPlaying()) { // Hvis runden ikke er ferdig skal Dealer bare vise scoren til første kort 
            lb.setText(String.valueOf(pl.getHand().get(0).getValue()));
        }
        else lb.setText(String.valueOf(pl.getScore()));
    }

    @FXML
    public void handleNewGame() {
        if (deck.getSize() < 50) {
            this.deck = new CardDeck(5);
        }
        updateListView(PlayerCards, player);
        updateListView(DealerCards, dealer);
        updateLabel(PlayerScore, player);
        updateLabel(DealerScore, dealer);
    }

    @FXML
    public void handleHit() {
        player.addCard(deck);
        PlayerCards.getItems().clear();
        PlayerCards.getItems().addAll(player.getHand());
        updateLabel(PlayerScore, player); 
    }

    @FXML
    public void handleHold() {
        player.hold();
        updateListView(DealerCards, dealer);
        updateLabel(DealerScore, dealer);
    }
}