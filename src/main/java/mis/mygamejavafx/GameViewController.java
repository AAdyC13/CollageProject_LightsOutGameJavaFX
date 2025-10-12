/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package mis.mygamejavafx;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author AAdy
 */
public class GameViewController implements Initializable {

    @FXML
    private AnchorPane BACKGROUND;
    @FXML
    private GridPane Table;
    @FXML
    private Button Start_game;
    @FXML
    private ChoiceBox<String> diff_sel;

    @FXML
    private Label Out_win_counter;
    @FXML
    private Label diff_show;
    @FXML
    private Label InGame_Counter;
    @FXML
    private Button close_game;

    private int InGame_Count;
    @FXML
    private Button But_def;
    private GameSave Save = new GameSave();
    @FXML
    private Label Win_Show;
    @FXML
    private Label Start_Show;
    @FXML
    private Label Stop_Show;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // 製作diff_sel
        diff_sel.getItems().addAll("3x3", "4x4", "5x5", "6x6", "7x7", "8x8", "9x9", "10x10");

    }

    private void Change_color_demo(ActionEvent event) {
        if (InGame_Count < 999) {
            InGame_Count += 1;
            InGame_Counter.setText("本局步數：" + InGame_Count);
        } else {
            InGame_Counter.setText("本局步數：999+");
        }
        But_def.setStyle("-fx-background-color: lightblue;");
        //Ckeck_win();

    }

    @FXML
    private void Change_color(ActionEvent event) {

        Map<Integer, int[]> Get_Dict = new HashMap<>();
        Button button = (Button) event.getSource();
        int get_x = GridPane.getColumnIndex(button); // 獲取按鈕的x
        int get_y = GridPane.getRowIndex(button);// 獲取按鈕的y
        System.out.printf("點選的按鈕：%d %d%n", get_x, get_y);
        Get_Dict = Save.Changer(get_x, get_y);

        for (int[] Get : Get_Dict.values()) {
            Button foundButton = (Button) Table.lookup(String.format("#%d_%d", Get[0], Get[1]));
            //System.out.printf("設定ID為：[ %d_%d ]的按鈕顏色為%d%n", Get[0], Get[1], Get[2]);
            if (Get[2] == 1) {
                foundButton.setStyle("");
            } else {
                foundButton.setStyle(
                        "-fx-background-color: lightblue;" + "-fx-border-color: black;" + "-fx-border-width: 0.5px;" + "-fx-border-radius: 3px;");
            }
        }

        if (InGame_Count < 1000) {
            InGame_Count += 1;
            InGame_Counter.setText("本局步數：" + InGame_Count);
        } else {
            InGame_Counter.setText("本局步數：999+");
        }
        Check_win();

    }
    private int Out_win_count;

    private void Check_win() {

        boolean check = Save.checkWin();
        if (check) {
            if (Out_win_count < 1000) {
                Out_win_count += 1;
                Out_win_counter.setText("連續勝利：" + Out_win_count);
            } else {
                Out_win_counter.setText("連續勝利：999+");
            }
            System.out.println("----勝利----");
            Close_Game();
            showWinning();

        }
    }

    @FXML
    private void handleChoiceBoxAction() {
        String selectedOption = diff_sel.getValue();
        //System.out.println("Selected Option: "+selectedOption);
        diff_show.setText("目前難度：" + selectedOption);
        Start_Button_ctrl(true);

    }

    private void Start_Button_ctrl(Boolean what) {
        Start_game.setDisable(!what);
    }

    private void Close_Button_ctrl(Boolean what) {
        close_game.setDisable(!what);
    }

    private void diff_list_ctrl(Boolean what) {
        diff_sel.setDisable(!what);
    }

    @FXML
    private void Start_Game(ActionEvent event) {
        Start_Button_ctrl(false);
        Close_Button_ctrl(true);
        diff_list_ctrl(false);
        String selectedOption = diff_sel.getValue();
        Map<Integer, int[]> Get_Dict = new HashMap<>();
        switch (selectedOption) {
            case "3x3":
                Get_Dict = Save.GameSaveMaker(1, 2);
                break;
            case "4x4":
                Get_Dict = Save.GameSaveMaker(1, 3);
                break;
            case "5x5":
                Get_Dict = Save.GameSaveMaker(2, 4);
                break;
            case "6x6":
                Get_Dict = Save.GameSaveMaker(2, 5);
                break;
            case "7x7":
                Get_Dict = Save.GameSaveMaker(3, 6);
                break;
            case "8x8":
                Get_Dict = Save.GameSaveMaker(3, 7);
                break;
            case "9x9":
                Get_Dict = Save.GameSaveMaker(4, 8);
                break;
            case "10x10":
                Get_Dict = Save.GameSaveMaker(4, 9);
                break;
        }
        Save.GameSave_printer();
        System.out.println();
        for (int[] Get : Get_Dict.values()) {

            Button Button_Table = new Button(But_def.getText());
            Button_Table.setId(String.format("%d_%d", Get[0], Get[1]));

            Button_Table.setPrefWidth(But_def.getPrefWidth());
            Button_Table.setPrefHeight(But_def.getPrefHeight());
            //Button_Table.setStyle();
            Button_Table.setOnAction(But_def.getOnAction());
            Table.add(Button_Table, Get[0], Get[1]);
            //System.out.printf("已創建ID為：[ %d_%d ]的按鈕%n", Get[0], Get[1]);

        }
        InGame_Counter.setText("本局步數：" + 0);
        System.out.printf("遊戲按鈕已創建，按鈕ID看 [Start_Game] %n");
        showStart();
        startColorThread();
    }

    @FXML
    private void Close_Game(ActionEvent event) {
        showStop();
        Close_Game();

    }

    private void Close_Game() {
        Table.getChildren().removeIf(node -> node instanceof Button);
        Save.Save_reset();
        //InGame_Counter.setText("本局步數：" + 0); //這裡不是失誤，我想說保留步數方便回看上一次的結果。
        InGame_Count = 0;
        Start_Button_ctrl(true);
        Close_Button_ctrl(false);
        diff_list_ctrl(true);
        stopColorThread();
        System.out.printf("Table已重製%n");
        System.out.printf("節點1%n");
    }
// 停止方法

    public void stopColorThread() {
        if (!running) {
            System.out.println("例外警告：來自ColorThread");
            return;
        }

        running = false; // 標誌為停止
        if (colorThread != null && colorThread.isAlive()) {
            colorThread.interrupt(); // 中斷線程
        }
        //System.out.println("彩虹背景：申請停止");
        BACKGROUND.setStyle(
                String.format("-fx-background-color: hsb(0, 0%%, 100%%);")
        );
    }
    private Thread colorThread; // 線程變量
    private volatile boolean running = false; // 控制線程狀態

// 啟動方法
    public void startColorThread() {
        if (running) {
            System.out.println("例外警告：來自ColorThread");
            return;
        }

        running = true; // 標誌為運行中
        colorThread = new Thread(() -> {
            double hue = 0.0; // 初始色相
            while (running) { // 檢查標誌位
                try {
                    // 使用 runLater 更新 UI
                    double finalHue = hue; // 需要變為 effectively final
                    Platform.runLater(() -> {
                        BACKGROUND.setStyle(
                                String.format("-fx-background-color: hsb(%f, 30%%, 100%%);", finalHue)
                        );
                    });

                    // 色相循環變化
                    hue += 1;
                    if (hue > 360) {
                        hue = 0; // 重置色相
                    }

                    // 控制顏色變化速度（30 毫秒變一次）
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // 恢復中斷標誌
                    break; // 結束循環
                }
            }
            System.out.println("彩虹背景：停止");
        });
        colorThread.setDaemon(true); // 設置為守護線程
        colorThread.start();
        System.out.println("彩虹背景：開啟");
    }

    public void showWinning() {
        Win_Show.setVisible(true);

        // 建立時間軸
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            Win_Show.setVisible(false);
        }));
        timeline.setCycleCount(1); // 執行一次
        timeline.play();
    }

    public void showStart() {
        Start_Show.setVisible(true);

        // 建立時間軸
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            Start_Show.setVisible(false);
        }));
        timeline.setCycleCount(1); // 執行一次
        timeline.play();
    }

    public void showStop() {
        Stop_Show.setVisible(true);

        // 建立時間軸
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            Stop_Show.setVisible(false);
        }));
        timeline.setCycleCount(1); // 執行一次
        timeline.play();
    }

}
