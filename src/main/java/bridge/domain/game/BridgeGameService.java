package bridge.domain.game;

import bridge.BridgeMove;

public class BridgeGameService {
    
    private final BridgeGame game;
    
    public BridgeGameService(BridgeGame game) {
        this.game = game;
    }
    
    public boolean tryMove(BridgeMove selectMove) {
        if (game.canMoveToNextPosition(selectMove)) {
            game.move(selectMove);
            return true;
        }
        return false;
    }
    
    public void retry() {
        game.retry();
    }
    
    public boolean isFinish() {
        return game.isReachedLastPosition();
    }
    
}
