
package com.uedayo.lib.lifegame;

import java.util.EventListener;

/**
 * LifeMap
 */
public class LifeMap {

    // 行列数を格納
    int row;
    int column;

    // LifeManagerを格納する配列
    LifeManager[][] lifeManagers;
    // 周囲の生きているLifeの数を格納する配列
    int[][] lifeCounter;

    // Refreshを通知するリスナー
    RefreshListener listener = null;

    /**
     * コンストラクタ
     */
    public LifeMap(int row, int column) {
        this.row = row;
        this.column = column;
        lifeManagers = new LifeManager[row][column];
        lifeCounter = new int[row][column];
    }

    /**
     * 全LifeManagerの初期化
     */
    public void init(RefreshListener listener) {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                lifeManagers[i][j] = new LifeManager();
                setListener(listener);
            }
        }
    }

    /**
     * 次の生死をセットする
     */
    public void setNextLivingState() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                updateLifeCounter(i, j);
                lifeManagers[i][j].setNextLivingState(lifeCounter[i][j]);
            }
        }
    }

    /**
     * 次の状態に移る
     */
    public void updateLivingStatus() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                lifeManagers[i][j].update();
            }
        }
        listener.refreshLifes();
    }

    /**
     * 周りの生きているLifeの数を加算する
     * 
     * @param i
     * @param j
     */
    private void updateLifeCounter(int i, int j) {
        lifeCounter[i][j] = 0;
        addTopLeft(i, j);
        addTop(i, j);
        addTopRight(i, j);
        addLeft(i, j);
        addRight(i, j);
        addBottomLeft(i, j);
        addBottom(i, j);
        addBottomRight(i, j);
    }

    /**
     * 左上が生きていればカウント
     * 
     * @param i
     * @param j
     */
    private void addTopLeft(int i, int j) {
        // 1行目、1列目の左上に他のLifeは無い
        if (i != 0 && j != 0) {
            lifeCounter[i][j] += lifeManagers[i - 1][j - 1].isLiving() ? 1 : 0;
        }
    }

    /**
     * 真上が生きていればカウント
     * 
     * @param i
     * @param j
     */
    private void addTop(int i, int j) {
        // 1行目の上に他のLifeは無い
        if (i != 0) {
            lifeCounter[i][j] += lifeManagers[i - 1][j].isLiving() ? 1 : 0;
        }
    }

    /**
     * 右上が生きていればカウント
     * 
     * @param i
     * @param j
     */
    private void addTopRight(int i, int j) {
        // 1行目、末尾の列の右上に他のLifeは無い
        if (i != 0 && j != column - 1) {
            lifeCounter[i][j] += lifeManagers[i - 1][j + 1].isLiving() ? 1 : 0;
        }
    }

    /**
     * 真左が生きていればカウント
     * 
     * @param i
     * @param j
     */
    private void addLeft(int i, int j) {
        // 1列目の左に他のLifeは無い
        if (j != 0) {
            lifeCounter[i][j] += lifeManagers[i][j - 1].isLiving() ? 1 : 0;
        }
    }

    /**
     * 真右が生きていればカウント
     * 
     * @param i
     * @param j
     */
    private void addRight(int i, int j) {
        // 末尾の列の右に他のLifeは無い
        if (j != column - 1) {
            lifeCounter[i][j] += lifeManagers[i][j + 1].isLiving() ? 1 : 0;
        }
    }

    /**
     * 左下が生きていればカウント
     * 
     * @param i
     * @param j
     */
    private void addBottomLeft(int i, int j) {
        // 末尾の行、1列目の左下に他のLifeは無い
        if (i != (row - 1) && j != 0) {
            lifeCounter[i][j] += lifeManagers[i + 1][j - 1].isLiving() ? 1 : 0;
        }
    }

    /**
     * 真下が生きていればカウント
     * 
     * @param i
     * @param j
     */
    private void addBottom(int i, int j) {
        // 末尾の行の下に他のLifeは無い
        if (i != (row - 1)) {
            lifeCounter[i][j] += lifeManagers[i + 1][j].isLiving() ? 1 : 0;
        }
    }

    /**
     * 右下が生きていればカウント
     * 
     * @param i
     * @param j
     */
    private void addBottomRight(int i, int j) {
        // 末尾の行、末尾の列の右下に他のLifeは無い
        if (i != (row - 1) && j != (column - 1)) {
            lifeCounter[i][j] += lifeManagers[i + 1][j + 1].isLiving() ? 1 : 0;
        }
    }

    /**
     * 生死を反転する
     * 
     * @param row
     * @param column
     */
    public void reverseLivingState(int row, int column) {
        lifeManagers[row][column].reverseLivingState();
        listener.refreshLifes();
    }

    /**
     * すべてのLifeの生死をランダムに設定する
     */
    public void random() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                lifeManagers[i][j].random();
            }
        }
        listener.refreshLifes();
    }

    /**
     * すべてのLifeを初期状態にする
     */
    public void reset() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                lifeManagers[i][j].reset();
            }
        }
        listener.refreshLifes();
    }

    /**
     * 生きている数をカウントする
     * 
     * @return 生きている数
     */
    public int getNumLife() {
        int livingLifeNum = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                livingLifeNum += lifeManagers[i][j].isLiving() ? 1 : 0;
            }
        }
        return livingLifeNum;
    }

    /**
     * 生死を確認する
     * 
     * @param row
     * @param column
     * @return 生きていればtrue
     */
    public boolean isLiving(int row, int column) {
        return lifeManagers[row][column].isLiving();
    }
    
    /**
     * Lifeの状態更新のリスナー
     */
    public interface RefreshListener extends EventListener {
        
        /**
         * 更新の要求を通知する
         */
        public void refreshLifes();
    }
    
    /**
     * リスナーを追加する
     * @param listener
     */
    public void setListener(RefreshListener listener) {
        this.listener = listener;
    }

    /**
     * リスナーを削除する
     * @param listener
     */
    public void removeListener(RefreshListener listener) {
        this.listener = null;
    }
}
