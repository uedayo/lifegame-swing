
package com.uedayo.lib.lifegame;

import java.util.EventListener;

/**
 * Lifeの状態管理を行うクラス
 */
public class LifeManager {

    Life life;
    boolean nextLivingState;

    /**
     * コンストラクタ
     * 
     * @param button
     * @param life
     */
    public LifeManager() {
        life = new Life();
    }

    /**
     * 現在の生死を返す
     */
    public boolean isLiving() {
        return life.isLiving();
    }

    /**
     * 次の生死をセットする
     * 
     * @param liveNum 現在の周囲の生きているLifeの数
     */
    public void setNextLivingState(int liveNum) {
        switch (liveNum) {
        // 0,1の場合、過疎により死滅
            case 0:
            case 1:
                nextLivingState = false;
                break;
            // 2の場合、現在の状態が継続
            case 2:
                nextLivingState = life.isLiving();
                break;
            // 3の場合、死んでいる場合でも新たに生成
            case 3:
                nextLivingState = true;
                break;
            // 4以上の場合、過密により死滅
            default:
                nextLivingState = false;
                break;
        }
        life.setNextLivingState(nextLivingState);
    }

    /**
     * 次の状態に遷移する
     */
    public void update() {
        life.updateLivingState();
    }

    /**
     * 現在の生死をランダムに設定する
     */
    public void random() {
        int random = (int) Math.round(Math.random());
        boolean isLiving = random == 1 ? true : false;
        life.setLive(isLiving);
    }

    /**
     * 現在の生死をリセットする(死に設定する)
     */
    public void reset() {
        life.setLive(false);
    }

    /**
     * 生死を反転する
     */
    public void reverseLivingState() {
        life.reverseLivingState();
    }

    /**
     * 次に生きるか死ぬかを返す
     * 
     * @return 生きる場合はtrue
     */
    public boolean isLiveNext() {
        return life.isLiveNext();
    }

    /**
     * 今の生死を設定する
     * 
     * @param live
     */
    public void setLive(boolean live) {
        life.setLive(live);
    }
}
