
package com.uedayo.swing.lifegame;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.sun.xml.internal.ws.org.objectweb.asm.Label;

/**
 * メイン処理
 */
public class LifeGameMain implements ActionListener {
    // 定数
    final int ROW_NUM = 6;
    final int COLUMN_NUM = 6;

    // レイアウト
    Container contentPanel;
    JFrame mainFrame;

    // テキスト
    JTextField textField;
    JTextArea textArea;

    // ラベル
    JLabel label;

    // ライフマップパネル
    JPanel lifeMapPanel;

    // ボタンパネル
    JPanel buttonPanel;

    // ボタン
    JButton startStopButton;
    JButton stepButton;
    JButton randomButton;
    JButton resetButton;

    public static void main(String[] args) {
        new LifeGameMain();
    }

    /**
     * メイン処理
     */
    public LifeGameMain() {
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
        label = new JLabel("ボタンを押してね。");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(label, BorderLayout.NORTH);
    }

    /**
     * ライフマップの初期化
     */
    private void initializeLifeMap(int rowNum, int colNum) {
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
        JButton[] lifeMapBtns = new JButton[maxColumn];
        for (int cal = 0; cal < maxColumn; cal++) {
            lifeMapBtns[cal] = new JButton();
            String text = "[" + currentRow + ", " + cal + "]";
            lifeMapBtns[cal].setText(text);
            lifeMapPanel.add(lifeMapBtns[cal]);
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

        if (e.getSource() == startStopButton) {
            label.setText("開始しました。");
        }

        if (e.getSource() == stepButton) {
            label.setText("ステップ実行しました。");
        }

        if (e.getSource() == randomButton) {
            label.setText("状態をランダムに設定しました。");
        }

        if (e.getSource() == resetButton) {
            label.setText("状態をリセットしました。");
        }
    }
}
