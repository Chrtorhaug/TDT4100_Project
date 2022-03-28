package Blackjack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class BlackjackController { 

    private BlackjackPlayer player;
    private CardDeck deck;
    private BlackJackDealer dealer;
    private int currentHand;
    private FileHandler fileHandler = new FileHandler();
    private List<ImageView> firstHandImageViews = new ArrayList<>();
    private List<ImageView> dealerHandImageViews = new ArrayList<>();
    private List<ImageView> secondHandImageViews = new ArrayList<>();
    private List<ImageView> thirdHandImageViews = new ArrayList<>();

    @FXML
    private Label PlayerScore1, PlayerScore2, PlayerScore3, DealerScore, WelcomeSign, ShowBalance;

    @FXML
    private Text PlayerScoreText1, PlayerScoreText2, PlayerScoreText3, DealerScoreText, DealerCardsText;
    
    @FXML
    private Button LoginButton, RegisterButton, NewGameButton, BetButton, HitButton, HoldButton, SplitButton;

    @FXML
    private TextField NameField, PasswordField, BetField;

    @FXML
    private ListView<String> TopPlayersListView;

    @FXML
    private ImageView DealerPicture11, DealerPicture12, DealerPicture13, DealerPicture14, DealerPicture15, DealerPicture16, DealerPicture17, DealerPicture18,
                    CardPicture11, CardPicture12, CardPicture13, CardPicture14, CardPicture15, CardPicture16, CardPicture17, CardPicture18,
                    CardPicture21, CardPicture22, CardPicture23, CardPicture24, CardPicture25, CardPicture26, CardPicture27, CardPicture28, 
                    CardPicture31, CardPicture32, CardPicture33, CardPicture34, CardPicture35, CardPicture36, CardPicture37, CardPicture38;

    @FXML
    public void initialize() {
        this.deck = new CardDeck(5);
        this.dealer = new BlackJackDealer(deck);
        updateTableTopPlayers();
        PlayerScore1.setVisible(false);
        PlayerScore2.setVisible(false);
        PlayerScore3.setVisible(false);
        DealerScore.setVisible(false);
        DealerCardsText.setVisible(false);
        ShowBalance.setVisible(true);
        PlayerScoreText1.setVisible(false);
        PlayerScoreText2.setVisible(false);
        PlayerScoreText3.setVisible(false);
        DealerScoreText.setVisible(false);
        WelcomeSign.setVisible(false);

        NewGameButton.setDisable(true);
        BetButton.setDisable(true);
        HitButton.setDisable(true);
        HoldButton.setDisable(true);
        SplitButton.setDisable(true);
        BetField.setDisable(true);
    }

    private void updateCardHandPictures(List<ImageView> view, ActionEvent event) {
        List<List<ImageView>> hands = Arrays.asList(firstHandImageViews, secondHandImageViews, thirdHandImageViews, dealerHandImageViews);
        int handIndex = hands.indexOf(view);
        if (view.equals(dealerHandImageViews)) {
            if (event.getSource().equals(NewGameButton)) {
                for (int i = 0; i < dealer.getHand(0).size(); i++) {
                    view.get(i).imageProperty().set(null);  
                }
                dealer.newHand(deck);
                DealerPicture11.setImage(dealer.getCard(0, 0).getCardPicture());
            }
            else {
                for (int i = 0; i < dealer.getHand(0).size(); i++) {
                    view.get(i).setImage(dealer.getCard(0, i).getCardPicture());
                }
            }
        }
        else {
            if (event.getSource().equals(NewGameButton)) {
                for (int i = 0; i < player.getHand(handIndex).size(); i++) {
                    view.get(i).imageProperty().set(null);  
                }
                if (view.equals(firstHandImageViews)) {
                    player.newHand(deck);
                }
                else return;
            } 
            if (event.getSource().equals(HitButton)) {
                player.addCard(deck, handIndex);
            } 
            if (event.getSource().equals(SplitButton)) {
                view.get(1).imageProperty().set(null);
                hands.get(handIndex).get(0).imageProperty().set(player.getCard(handIndex, 0).getCardPicture());
                hands.get(handIndex + 1).get(0).imageProperty().set(player.getCard(handIndex + 1, 0).getCardPicture());
            }
            for (int i = 0; i < player.getHand(hands.indexOf(view)).size(); i++) {
                view.get(i).setImage(player.getCard(handIndex, i).getCardPicture());
            }
        }
    }

    private void updateLabel(Label lb, PlayerInterface pl) {
        if (pl.getClass().equals(BlackJackDealer.class) && player.checkPlaying()) { // Hvis runden ikke er ferdig skal Dealer bare vise scoren til første kort 
            lb.setText(String.valueOf(pl.getCard(0, 0).getValue()));
        }
        else {
            if (lb.equals(PlayerScore2)) {
                lb.setText(String.valueOf(pl.getScore(1)));
                PlayerScoreText2.setVisible(true);
                PlayerScore2.setVisible(true);
            }
            if (lb.equals(PlayerScore3)) {
                lb.setText(String.valueOf(pl.getScore(2)));
                PlayerScoreText3.setVisible(true);
                PlayerScore3.setVisible(true);
            }
            else lb.setText(String.valueOf(pl.getScore(0)));
        }
    }

    @FXML
    public void handleNewGame(ActionEvent newGameEvent) {
        if (deck.getSize() < 50) {
            this.deck = new CardDeck(5);
        }
        updateCardHandPictures(secondHandImageViews, newGameEvent);
        updateCardHandPictures(thirdHandImageViews, newGameEvent);
        updateCardHandPictures(firstHandImageViews, newGameEvent);
        updateCardHandPictures(dealerHandImageViews, newGameEvent);
        updateLabel(PlayerScore1, player);
        updateLabel(DealerScore, dealer);

        NewGameButton.setDisable(true);
        HitButton.setDisable(false);
        DealerScore.setVisible(true);
        DealerCardsText.setVisible(true);
        PlayerScore1.setVisible(true);
        HoldButton.setDisable(false);
        PlayerScoreText1.setVisible(true);
        PlayerScoreText2.setVisible(false);
        PlayerScoreText3.setVisible(false);
        DealerScoreText.setVisible(true);
        PlayerScore2.setVisible(false);
        PlayerScore3.setVisible(false);

        if (player.canSplit(player.getHand(0)) || player.canSplit(player.getHand(1))) {
            SplitButton.setDisable(false);
        }

        if (player.getScore(0) == 21) {
            HitButton.setDisable(true);
            SplitButton.setDisable(true);
        }
    }

    @FXML
    public void handleHit(ActionEvent hitEvent) {
        List<List<ImageView>> playerHands = Arrays.asList(firstHandImageViews, secondHandImageViews, thirdHandImageViews);
        List<Label> playerScores = Arrays.asList(PlayerScore1, PlayerScore2, PlayerScore3);

        updateCardHandPictures(playerHands.get(currentHand), hitEvent);
        updateLabel(playerScores.get(currentHand), player);

        if (player.getScore(currentHand) >= 21 && currentHand != player.getHands().size() - 1 && player.getHand(currentHand + 1).size() == 0) {
            HitButton.setDisable(true);
            SplitButton.setDisable(true);
        }
    }

    @FXML
    public void handleHold(ActionEvent holdEvent) {
        if (currentHand == player.getHands().size() - 1 || player.getHand(currentHand + 1).size() == 0) {
            player.hold();
            currentHand = 0;
            updateCardHandPictures(dealerHandImageViews, holdEvent);
            updateLabel(DealerScore, dealer);
            player.findWinner(new HandComparator(), dealer);
            ShowBalance.setText(player.getBalance() + "$");
            fileHandler.UpdateBalance(player.getName(), player.getBalance());

            HoldButton.setDisable(true);
            HitButton.setDisable(true);
            SplitButton.setDisable(true);
            BetButton.setDisable(false);
            BetField.setDisable(false);
        }
        else if (currentHand == 0) {
            currentHand++;
        }
    }

    @FXML
    public void handleSplit(ActionEvent splitEvent) {
        if (player.getHand(1).size() == 0) {
            player.split(player.getHand(0));
            updateCardHandPictures(firstHandImageViews, splitEvent);
            updateLabel(PlayerScore1, player);
            updateLabel(PlayerScore2, player);
            SplitButton.setDisable(true);
        }
        else if (player.getHand(1).size() != 0 && player.getHand(2).size() == 0) {
            player.split(player.getHand(1));
            updateCardHandPictures(secondHandImageViews, splitEvent);
            updateLabel(PlayerScore2, player);
            updateLabel(PlayerScore3, player);
            SplitButton.setDisable(true);
        }
    }

    @FXML
    public void handleBet() {
        if (player.setBet(BetField.getText()).equals("money")) {
            BetField.clear();
            BetField.setPromptText("Invalid amount of money");
        }
        else if (player.setBet(BetField.getText()).equals("double")) {
            BetField.clear();
            BetField.setPromptText("Not a Double");
        }
        else {
            if (BetField.getText().isBlank()) {
                player.setBet(String.valueOf(player.getStandardBet()));
            }
            else player.setBet(BetField.getText());

            BetField.setPromptText("Enter Bet Amount:");
            NewGameButton.setDisable(false);
            BetButton.setDisable(true);
            BetField.setDisable(true);
        }
    }

    @FXML
    public void handleRegister() {
        RegisterOrLogin("Register");   
    }

    @FXML
    public void handleLogin() {
        RegisterOrLogin("Login");
    }

    private void RegisterOrLogin(String start) {
        dealerHandImageViews = Arrays.asList(DealerPicture11, DealerPicture12, DealerPicture13, DealerPicture14, DealerPicture15, DealerPicture16, DealerPicture17, DealerPicture18);
        firstHandImageViews = Arrays.asList(CardPicture11, CardPicture12, CardPicture13, CardPicture14, CardPicture15, CardPicture16, CardPicture17, CardPicture18);
        secondHandImageViews = Arrays.asList(CardPicture21, CardPicture22, CardPicture23, CardPicture24, CardPicture25, CardPicture26, CardPicture27, CardPicture28); 
        thirdHandImageViews = Arrays.asList(CardPicture31, CardPicture32, CardPicture33, CardPicture34, CardPicture35, CardPicture36, CardPicture37, CardPicture38);
        
        if (fileHandler.CheckRegisterOrLogin(start, NameField.getText(), PasswordField.getText())){
            if (start == "Login") {
                this.player = new BlackjackPlayer(Double.parseDouble(fileHandler.getBalance(NameField.getText())), NameField.getText(), deck);
            }
            else this.player = new BlackjackPlayer(100, NameField.getText(), deck);

            NameField.clear();
            PasswordField.clear();
            WelcomeSign.setText("Welcome " + player.getName());
            ShowBalance.setText(player.getBalance() + "$");
            
            WelcomeSign.setVisible(true);
            LoginButton.setVisible(false);
            RegisterButton.setVisible(false);
            NameField.setDisable(true);
            BetButton.setDisable(false);
            BetField.setDisable(false);
            NameField.setDisable(true);
            PasswordField.setDisable(true);
        }
        else {
            NameField.clear();
            PasswordField.clear();
            NameField.setPromptText("Invalid Username");
            PasswordField.setPromptText("Invalid password");
        }
    }

    public void updateTableTopPlayers() {
        ObservableList<String> topPlayers = fileHandler.updateTopPlayers();

        TopPlayersListView.getItems().addAll(topPlayers);
    }
}