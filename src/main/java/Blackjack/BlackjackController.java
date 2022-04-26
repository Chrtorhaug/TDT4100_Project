package Blackjack;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class BlackjackController { 

    private BlackjackPlayer player;
    private CardDeck deck;
    private BlackJackDealer dealer;
    private FileHandler fileHandler;
    private List<ImageView> firstHandImageViews, secondHandImageViews, thirdHandImageViews, dealerHandImageViews = new ArrayList<>();
    private List<Label> playerScores;
    private List<List<ImageView>> playerHands;

    @FXML
    private Label PlayerScore1, PlayerScore2, PlayerScore3, DealerScore, WelcomeSign, ShowBalance, ShowCurrentBet, ShowStandardBet, ShowSessionMoney;

    @FXML
    private Text PlayerScoreText1, PlayerScoreText2, PlayerScoreText3, DealerScoreText;
    
    @FXML
    private Button LoginButton, RegisterButton, NewGameButton, BetButton, HitButton, HoldButton, SplitButton, LogOutButton;

    @FXML
    private TextField NameField, PasswordField, BetField;

    @FXML
    private ListView<String> TopPlayersListView, TopPlayersBalanceListView, RankListView;

    @FXML
    private Rectangle PlayerHandFrame1, PlayerHandFrame2, PlayerHandFrame3;

    @FXML
    private ImageView BlackjackBoard, DealerPicture11, DealerPicture12, DealerPicture13, DealerPicture14, DealerPicture15, DealerPicture16, DealerPicture17, DealerPicture18,
                    CardPicture11, CardPicture12, CardPicture13, CardPicture14, CardPicture15, CardPicture16, CardPicture17, CardPicture18,
                    CardPicture21, CardPicture22, CardPicture23, CardPicture24, CardPicture25, CardPicture26, CardPicture27, CardPicture28, 
                    CardPicture31, CardPicture32, CardPicture33, CardPicture34, CardPicture35, CardPicture36, CardPicture37, CardPicture38;

    @FXML
    public void initialize() {
        this.deck = new CardDeck(5);
        this.dealer = new BlackJackDealer(deck);
        this.fileHandler = new FileHandler();
        BlackjackBoard.setImage(new Image(new File("src/main/resources/BlackjackTable.jpg").toURI().toString()));
        updateTableTopPlayers();
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
                DealerPicture11.setImage(getCardPicture(dealer.getCard(0, 0)));
            }
            else {
                for (int i = 0; i < dealer.getHand(0).size(); i++) {
                    view.get(i).setImage(getCardPicture(dealer.getCard(0, i)));
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
                player.setAceToOne(player.getHand(player.getCurrentHandIndex()), handIndex); //Checking if you have an Ace and if it needs to be changed  
            } 
            if (event.getSource().equals(SplitButton)) {
                hands.stream().limit(handIndex + 1).forEach(v -> v.get(1).imageProperty().set(null));
                if (player.getHand(2).size() == 0 && handIndex == 0) {
                    hands.get(handIndex + 1).get(0).imageProperty().set(getCardPicture(player.getCard(handIndex + 1, 0)));
                }
                else hands.get(2).get(0).imageProperty().set(getCardPicture(player.getCard(2, 0)));
            }
            for (int i = 0; i < player.getHand(hands.indexOf(view)).size(); i++) {
                view.get(i).setImage(getCardPicture(player.getCard(handIndex, i)));
            }
        }
    }

    private Image getCardPicture(Card card) {
        return new Image(new File("src/main/resources/Carddeck/" + card.toString() + ".png").toURI().toString());
    }

    private void updateLabel(Label lb, PlayerInterface pl) {
        if (pl.getClass().equals(BlackJackDealer.class) && player.checkPlaying()) { // If the round isn't finished, Dealer should only show one card
            lb.setText(String.valueOf(pl.getCard(0, 0).getValue()));
        }
        else {
            if (lb.equals(PlayerScore2)) {
                lb.setText(String.valueOf(pl.getScore(1)));
                Arrays.asList(PlayerScoreText2, PlayerScore2).forEach(s -> s.setVisible(true));
            }
            else if (lb.equals(PlayerScore3)) {
                lb.setText(String.valueOf(pl.getScore(2)));
                Arrays.asList(PlayerScoreText3, PlayerScore3).forEach(s -> s.setVisible(true));
            }
            else lb.setText(String.valueOf(pl.getScore(0)));
        }
    }

    private void showPlayingFrame() {
        List<Rectangle> frames = Arrays.asList(PlayerHandFrame1, PlayerHandFrame2, PlayerHandFrame3);
        frames.forEach(frame -> frame.setVisible(false));
        frames.get(player.getCurrentHandIndex()).setVisible(true);
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
        showPlayingFrame();

        Arrays.asList(DealerScore, PlayerScore1, PlayerScoreText1, DealerScoreText).forEach(b -> b.setVisible(true));
        Arrays.asList(PlayerScoreText2, PlayerScoreText3, PlayerScore2, PlayerScore3).forEach(b -> b.setVisible(false));
        Arrays.asList(HitButton, HoldButton).forEach(b -> b.setDisable(false));
        NewGameButton.setDisable(true);

        if (player.canSplit(player.getHand(0))) {
            SplitButton.setDisable(false);
        }

        if (player.getScore(0) == 21) {
            Arrays.asList(HitButton, SplitButton).forEach(b -> b.setDisable(true));
        }
    }

    @FXML
    public void handleHit(ActionEvent hitEvent) {
        int currentHand = player.getCurrentHandIndex();
        updateCardHandPictures(playerHands.get(currentHand), hitEvent);
        updateLabel(playerScores.get(currentHand), player);

        if (player.canSplit(player.getHand(currentHand)) && player.getHand(2).size() == 0) {
            SplitButton.setDisable(false);
        }

        if (player.getScore(currentHand) >= 21 && currentHand != player.getHands().size() - 1 && player.getHand(currentHand + 1).size() == 0) {
            Arrays.asList(HitButton, SplitButton).forEach(b -> b.setDisable(true));
        }
    }

    @FXML
    public void handleHold(ActionEvent holdEvent) throws FileNotFoundException {
        player.hold();
        if (! player.checkPlaying()) { //currentHand == player.getHands().size() - 1 || player.getHand(currentHand + 1).size() == 0

            updateCardHandPictures(dealerHandImageViews, holdEvent);
            updateLabel(DealerScore, dealer);
            player.findWinner(new HandComparator(), dealer);
            fileHandler.UpdateBalance(player.getName(), player.getBalance());

            if (player.getMoneyChange() > 0) { // If a player is up this session show the earned money in green
                ShowSessionMoney.setText("+ " + (player.getMoneyChange() + " $"));
                ShowSessionMoney.setTextFill(Color.GREEN);
            }
            else if (player.getMoneyChange() < 0) { // If a player is down this session show the lost money in red
                ShowSessionMoney.setText("- " + (Math.abs(player.getMoneyChange()) + " $"));
                ShowSessionMoney.setTextFill(Color.RED);
            }
            else { // If the player is even show 0 in white 
                ShowSessionMoney.setText("0 $"); 
                ShowSessionMoney.setTextFill(Color.WHITE);
            }

            Arrays.asList(HoldButton, HitButton, SplitButton).forEach(b -> b.setDisable(true));
            Arrays.asList(BetButton, BetField).forEach(b -> b.setDisable(false));

            if (player.rebuy()) { //Checks if a player is broke and rebuys money if necessary 
                fileHandler.UpdateBalance(player.getName(), 100);
            }
            updateTableTopPlayers();
            ShowBalance.setText(player.getBalance() + " $");
        }
        else {
            showPlayingFrame();

            if (player.canSplit(player.getHand(player.getCurrentHandIndex()))) {
                SplitButton.setDisable(false);
            }
        }
    }

    @FXML
    public void handleSplit(ActionEvent splitEvent) {
        int currentHand = player.getCurrentHandIndex();
        if (player.getHand(currentHand + 1).size() == 0) {
            player.split(player.getHand(currentHand));
            updateCardHandPictures(playerHands.get(currentHand), splitEvent);
            updateLabel(playerScores.get(currentHand), player);
            updateLabel(playerScores.get(currentHand + 1), player);
            SplitButton.setDisable(true);
        }
        else if (player.getHand(2).size() == 0) {
            player.split(player.getHand(0));
            updateCardHandPictures(secondHandImageViews, splitEvent);
            updateLabel(PlayerScore1, player);
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
            if (BetField.getText().isEmpty()) {
                player.setBet(String.valueOf(player.getStandardBet()));
            }
            else player.setBet(BetField.getText());
            
            ShowCurrentBet.setText(player.getBet() + " $");
            BetField.setPromptText("Enter Bet Amount:");
            NewGameButton.setDisable(false);
            Arrays.asList(BetButton, BetField).forEach(b -> b.setDisable(true));
        }
    }

    @FXML
    public void handleRegister(ActionEvent registerEvent) {
        RegisterOrLogin("Register", registerEvent);   
    }

    @FXML
    public void handleLogin(ActionEvent loginEvent) {
        RegisterOrLogin("Login", loginEvent);
    }

    private void RegisterOrLogin(String start, ActionEvent event) {
        dealerHandImageViews = Arrays.asList(DealerPicture11, DealerPicture12, DealerPicture13, DealerPicture14, DealerPicture15, DealerPicture16, DealerPicture17, DealerPicture18);
        firstHandImageViews = Arrays.asList(CardPicture11, CardPicture12, CardPicture13, CardPicture14, CardPicture15, CardPicture16, CardPicture17, CardPicture18);
        secondHandImageViews = Arrays.asList(CardPicture21, CardPicture22, CardPicture23, CardPicture24, CardPicture25, CardPicture26, CardPicture27, CardPicture28); 
        thirdHandImageViews = Arrays.asList(CardPicture31, CardPicture32, CardPicture33, CardPicture34, CardPicture35, CardPicture36, CardPicture37, CardPicture38);
        playerHands = Arrays.asList(firstHandImageViews, secondHandImageViews, thirdHandImageViews);
        playerScores = Arrays.asList(PlayerScore1, PlayerScore2, PlayerScore3);
        
        if (fileHandler.CheckRegisterOrLogin(start, NameField.getText(), PasswordField.getText())) {
            if (start == "Login") {
                this.player = new BlackjackPlayer(Double.parseDouble(fileHandler.getBalance(NameField.getText())), NameField.getText(), deck);
            }
            else this.player = new BlackjackPlayer(100, NameField.getText(), deck);

            Arrays.asList(NameField, PasswordField).forEach(f -> f.clear());
            WelcomeSign.setText("Welcome " + player.getName());
            ShowBalance.setText(player.getBalance() + "$");
            ShowStandardBet.setText(player.getStandardBet() + "$");
            
            Arrays.asList(WelcomeSign, LogOutButton).forEach(b -> b.setVisible(true));
            Arrays.asList(NameField, PasswordField).forEach(b -> b.setDisable(true));
            Arrays.asList(BetButton, BetField).forEach(b -> b.setDisable(false));
            Arrays.asList(PlayerHandFrame1, PlayerHandFrame2, PlayerHandFrame3, PlayerScoreText1, PlayerScoreText2, PlayerScoreText3, DealerScore, DealerScoreText, LoginButton, RegisterButton).forEach(f -> f.setVisible(false));

            dealerHandImageViews.forEach(i -> i.imageProperty().set(null));
            playerHands.stream().forEach(l -> l.forEach(i -> i.imageProperty().set(null)));
            playerScores.stream().forEach(s -> s.setVisible(false));
            Arrays.asList(ShowSessionMoney, ShowCurrentBet).forEach(s -> s.setText("0 $"));
            ShowSessionMoney.setTextFill(Color.WHITE);
        }
        else {
            NameField.clear();
            PasswordField.clear();
            NameField.setPromptText("Invalid Username");
            if (event.getSource().equals(LoginButton)) {
                PasswordField.setPromptText("Invalid password");
            }
            else PasswordField.setPromptText("Password needs min: 10 chars, 1 number");
        }
    }

    @FXML
    private void handleLogOut() {
        initialize();
        Arrays.asList(LogOutButton, WelcomeSign).forEach(b -> b.setVisible(false));
        Arrays.asList(NameField, PasswordField).forEach(b -> b.setDisable(false));
        Arrays.asList(RegisterButton, LoginButton).forEach(b -> b.setVisible(true));
        updateTableTopPlayers();
    }

    private void updateTableTopPlayers() {
        List<String> topPlayers = fileHandler.updateTopPlayers();
        Arrays.asList(RankListView, TopPlayersListView, TopPlayersBalanceListView).forEach(l -> l.getItems().clear());

        for (String string : topPlayers) {
            String[] info = string.split(",");
            RankListView.getItems().add(topPlayers.indexOf(string) + 1 + "");
            TopPlayersListView.getItems().add(info[0]);
            TopPlayersBalanceListView.getItems().add(info[1] + " $");
        }
    }
}