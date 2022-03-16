package Blackjack;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class BlackjackController { 

    private BlackjackPlayer player;
    private CardDeck deck;
    private BlackJackDealer dealer;
    private HandComparator handComp;
    
    @FXML
    private ListView<Card> PlayerCards, DealerCards, SplitCards;

    @FXML
    private Label PlayerScore, DealerScore, WelcomeSign, ShowBalance;
    
    @FXML
    private Button LoginButton, RegisterButton, NewGameButton, BetButton, HitButton, HoldButton, SplitButton;

    @FXML
    private TextField NameField, PasswordField, BetField;

    @FXML
    public void initialize() {
        this.deck = new CardDeck(5);
        this.dealer = new BlackJackDealer(deck);
        handComp = new HandComparator();

        PlayerScore.setText(" ");
        DealerScore.setText(" ");
        ShowBalance.setText(" ");
        WelcomeSign.setVisible(false);
        SplitCards.setVisible(false);

        NewGameButton.setDisable(true);
        BetButton.setDisable(true);
        HitButton.setDisable(true);
        HoldButton.setDisable(true);
        SplitButton.setDisable(true);
        BetField.setDisable(true);
    }

    private void updateListView(ListView<Card> lv, PlayerInterface pl) {
        lv.getItems().clear();

        if (pl.getName() == "Dealer") {
            if (!player.checkPlaying()) { //Hvis runden er ferdig skal Dealer vise alle kortene sine
                lv.getItems().addAll(pl.getHand(0));
            }
            else {
                pl.newHand(deck);
                lv.getItems().add(pl.getHand(0).get(0)); // Hvis runden ikke er ferdig skal Dealer bare vise første kort
            }
        }
        else {
            pl.newHand(deck);
            lv.getItems().addAll(pl.getHand(0));
        }
    }

    private void updateLabel(Label lb, PlayerInterface pl) {
        if (pl.getName() == "Dealer" && player.checkPlaying()) { // Hvis runden ikke er ferdig skal Dealer bare vise scoren til første kort 
            lb.setText(String.valueOf(pl.getHand(0).get(0).getValue()));
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

        NewGameButton.setDisable(true);
        HitButton.setDisable(false);
        HoldButton.setDisable(false);
        SplitCards.setVisible(false);

        if (player.canSplit(player.getHand(0))) {
            SplitButton.setDisable(false);
        }

        if (player.getScore() == 21) {
            HitButton.setDisable(true);
            SplitButton.setDisable(true);
        }
    }

    @FXML
    public void handleHit() {
        player.addCard(deck);
        PlayerCards.getItems().clear();
        PlayerCards.getItems().addAll(player.getHand(0));
        updateLabel(PlayerScore, player); 

        if (player.getScore() >= 21) {
            HitButton.setDisable(true);
            SplitButton.setDisable(true);
        }
    }

    @FXML
    public void handleHold() {
        player.hold();
        updateListView(DealerCards, dealer);
        updateLabel(DealerScore, dealer);
        player.findWinner(handComp, dealer);
        ShowBalance.setText(player.getBalance() + "$");

        HoldButton.setDisable(true);
        HitButton.setDisable(true);
        SplitButton.setDisable(true);
        BetButton.setDisable(false);
        BetField.setDisable(false);
    }

    @FXML
    public void handleSplit() {
        player.split(player.getHand(0));
        SplitCards.setVisible(true);
        PlayerCards.getItems().clear();
        PlayerCards.getItems().addAll(player.getHand(0));
        SplitCards.getItems().addAll(player.getHand(1));
        SplitButton.setDisable(true);
    }

    @FXML
    public void handleBet() {
        NewGameButton.setDisable(false);
        BetButton.setDisable(true);
        BetField.setDisable(true);

        if (BetField.getText().isBlank()) {
            player.setBet("1.0");
        }
        else player.setBet(BetField.getText());
    }

    @FXML
    public void handleRegister() {
        System.out.println(NameField.getText());
        //Forslag til kodeendring - lage klasse som enten validerer: sjekker at brukernavn ikke er tatt,
        // at passord følger visse regler, og skriver personens informasjon og balanse til fil. dette oppdateres enten
        // etter hvert spill eller når programmet avsluttes
        // Den andre metoden til klassen kalles ved loginButton, sammenkobler brukernavn til passord og henter ut balanse
        // fra fil, samt tidligere spillhistorikk?
        // CredentialsCheck check = new CredentialsCheck();
        //  if (check.validate(NameField.getText(),PasswordField.getText())) 
        //          utføre kode nedenfor
        // else {NameField.setPromptText("Invalid");
        //       PasswordField.setPromptText("Invalid");}
        this.player = new BlackjackPlayer(100, NameField.getText(), deck);
        NameField.clear();
        WelcomeSign.setText("Welcome " + player.getName());
        ShowBalance.setText(player.getBalance() + "$");
        
        WelcomeSign.setVisible(true);;
        LoginButton.setVisible(false);
        RegisterButton.setVisible(false);
        NameField.setDisable(true);
        BetButton.setDisable(false);
        BetField.setDisable(false);
    }
}