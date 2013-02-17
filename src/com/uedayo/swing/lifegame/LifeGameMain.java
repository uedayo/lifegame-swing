
package com.uedayo.swing.lifegame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.sun.net.ssl.internal.www.protocol.https.Handler;
import com.uedayo.lib.lifegame.LifeMap;
import com.uedayo.lib.lifegame.LifeMap.RefreshListener;

/**
 * メイン処理
 */
public class LifeGameMain implements ActionListener, RefreshListener {
    // 定数
    final int ROW_NUM = 10;
    final int COLUMN_NUM = 10;
    final String LIFE = "生";
    final String DEATH = "死";

    // レイアウト
    Container contentPanel;
    JFrame mainFrame;

    // テキスト
    JTextField textField;
    JTextArea textArea;

    // ラベル
    JLabel label;

    // ライフマップ
    LifeMap lifeMap;
    JPanel lifeMapPanel;
    JButton[][] lifeMapBtns;

    // ボタンパネル
    JPanel buttonPanel;

    // ボタン
    JButton startStopButton;
    JButton stepButton;
    JButton randomButton;
    JButton resetButton;

    // タイマー
    Timer timer;
    TimerTask task;
    Handler handler;

    boolean isStarted;

    public static void main(String[] args) {
        new LifeGameMain();
    }

    /**
     * メイン処理
     */
    public LifeGameMain() {
        handler = new Handler();

        initializeMainFrame();
        initializeLabel();
        initializeLifeMap(ROW_NUM, COLUMN_NUM);
        initializeButton();
        showMainFrame();
    }

    /**
     * メインフレームの初期化
     */
    private void initializeMainFrame() {
        mainFrame = new JFrame("LifeGame");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);

        contentPanel = mainFrame.getContentPane();
    }

    /**
     * ラベルの初期化
     */
    private void initializeLabel() {
        label = new JLabel("ボタンを押して!!");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(label, BorderLayout.NORTH);
    }

    /**
     * ライフマップの初期化
     */
    private void initializeLifeMap(int rowNum, int colNum) {
        lifeMap = new LifeMap(rowNum, colNum);
        lifeMap.init((RefreshListener) LifeGameMain.this);
        lifeMapPanel = new JPanel();
        lifeMapPanel.setLayout(new GridLayout(rowNum, colNum));
        initializeLifeMapRows(rowNum, colNum);
    }

    /**
     * LifeMapの中に複数の行を作成する
     * 
     * @param maxRow
     * @param maxColumn
     */
    private void initializeLifeMapRows(int maxRow, int maxColumn) {
        lifeMapBtns = new JButton[maxRow][maxColumn];
        for (int row = 0; row < maxRow; row++) {
            initializeLifeMapRow(row, maxColumn);
            contentPanel.add(lifeMapPanel, BorderLayout.CENTER);
        }
    }

    /**
     * 行の中に複数のボタンを作成する
     * 
     * @param currentRow
     * @param maxColumn
     */
    private void initializeLifeMapRow(int currentRow, int maxColumn) {
        for (int cal = 0; cal < maxColumn; cal++) {
            lifeMapBtns[currentRow][cal] = new JButton();
            String text = DEATH;
            lifeMapBtns[currentRow][cal].setText(text);
            lifeMapBtns[currentRow][cal].setForeground(Color.RED);
            lifeMapBtns[currentRow][cal].addActionListener(this);
            lifeMapPanel.add(lifeMapBtns[currentRow][cal]);
        }
    }

    /**
     * ボタンの初期化
     */
    private void initializeButton() {

        buttonPanel = new JPanel();
        startStopButton = new JButton("スタート");
        stepButton = new JButton("ステップ実行");
        randomButton = new JButton("ランダム");
        resetButton = new JButton("リセット");

        startStopButton.addActionListener(this);
        stepButton.addActionListener(this);
        randomButton.addActionListener(this);
        resetButton.addActionListener(this);

        buttonPanel.add(startStopButton);
        buttonPanel.add(stepButton);
        buttonPanel.add(randomButton);
        buttonPanel.add(resetButton);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * メインフレームの表示
     */
    private void showMainFrame() {
        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    /**
     * ボタンが押された際の処理
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getSource());

        if (e.getSource() == startStopButton) {
            label.setText("開始!!");
            // タイマー処理
            startLifecycle();
        }

        if (e.getSource() == stepButton) {
            label.setText("ステップ実行!!");
            // 次の状態に遷移
            lifeMap.setNextLivingState();
            // アップデート
            lifeMap.updateLivingStatus();
        }

        if (e.getSource() == randomButton) {
            label.setText("状態をランダムに設定!!");
            lifeMap.random();
        }

        if (e.getSource() == resetButton) {
            label.setText("状態をリセット!!");
            lifeMap.reset();
        }

        checkLifeMapAction(e);
    }

    /**
     * ライフサイクルの開始
     */
    private void startLifecycle() {
        timer = new Timer();
        task = new TimerTask() {

            @Override
            public void run() {
                // Swing でのタイマー処理を学習

            }
        };
        long delay = 200;
        long period = 200;
        timer.schedule(task, delay, period);
    }

    /**
     * LifeMapのボタンが押された場合の処理
     * 
     * @param e
     */
    private void checkLifeMapAction(ActionEvent e) {
        for (int row = 0; row < ROW_NUM; row++) {
            checkLifeMapRowAction(e, row);
        }
    }

    /**
     * ある行について調べる
     * 
     * @param e
     * @param row
     */
    private void checkLifeMapRowAction(ActionEvent e, int row) {
        for (int column = 0; column < COLUMN_NUM; column++) {
            checkLifeActionPerfomed(e, row, column);
        }
    }

    /**
     * 各Lifeボタンが押されていれば生死を反転
     * 
     * @param e
     * @param row
     * @param column
     */
    private void checkLifeActionPerfomed(ActionEvent e, int row, int column) {
        if (e.getSource() == lifeMapBtns[row][column]) {
            label.setText("[" + row + ", " + column + "]の" + LIFE + DEATH + "が反転!!");
            lifeMap.reverseLivingState(row, column);
        }
    }

    /**
     * 生死の表示を更新(全体)
     */
    @Override
    public void refreshLifes() {
        for (int row = 0; row < ROW_NUM; row++) {
            refreshLifeRow(row);
        }
    }

    /**
     * 生死の表示を更新(行)
     * 
     * @param row
     */
    private void refreshLifeRow(int row) {
        for (int column = 0; column < COLUMN_NUM; column++) {
            JButton lifeButton = lifeMapBtns[row][column];
            boolean live = lifeMap.isLiving(row, column);
            refreshLife(lifeButton, live);
        }
    }

    /**
     * 生死の表示を更新(ボタン)
     * 
     * @param live
     */
    private void refreshLife(JButton lifeButton, boolean live) {
        String string = live ? LIFE : DEATH;
        Color color = live ? Color.BLUE : Color.RED;
        lifeButton.setText(string);
        lifeButton.setForeground(color);
    }
}
