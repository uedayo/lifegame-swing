package com.uedayo.lib.lifegame;

public class Life {

    // 現在の生死
    private boolean currentLivingState = false;
    // 次の生死
    private boolean nextLivingState = false;
    
    /**
     * 次の生死をセットする
     * 
     * @param nextLivingState 次に生きているならtrue
     */
    public void setNextLivingState(boolean nextLivingState) {
        this.nextLivingState = nextLivingState;
    }
    
    /**
     * 生死を更新する
     */
    public boolean updateLivingState() {
        currentLivingState = nextLivingState;
        return currentLivingState;
    }

    /**
     * 生死を反転する
     */
    public void reverseLivingState() {
        currentLivingState = currentLivingState ? false : true;
    }
    
    /**
     * 次の生死を確認する(テスト用)
     */
    boolean isLiveNext() {
        return nextLivingState;
    }

    /**
     * 今の生死を設定する
     */
    void setLive(boolean live) {
        this.currentLivingState = live;
    }
    
    /**
     * 今の生死を確認する
     */
    public boolean isLiving() {
        return currentLivingState;
    }
}
