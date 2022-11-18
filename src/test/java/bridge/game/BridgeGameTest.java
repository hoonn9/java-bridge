package bridge.game;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.util.Lists.newArrayList;

import bridge.BridgeMaker;
import bridge.BridgeMove;
import bridge.domain.TestNumberGenerator;
import bridge.domain.bridge.Bridge;
import bridge.domain.game.BridgeGame;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class BridgeGameTest {
    
    BridgeGame bridgeGame;
    List<Integer> generateNumbers;
    
    @BeforeEach
    void setUp() {
        generateNumbers = newArrayList(
                BridgeMove.DOWN.getGenerateNumber(),
                BridgeMove.UP.getGenerateNumber(),
                BridgeMove.UP.getGenerateNumber(),
                BridgeMove.UP.getGenerateNumber(),
                BridgeMove.DOWN.getGenerateNumber()
        );
        Bridge bridge =
                new Bridge(
                        new BridgeMaker(new TestNumberGenerator(generateNumbers)).makeBridge(generateNumbers.size()));
        bridgeGame = new BridgeGame(bridge);
    }
    
    @DisplayName("인스턴스가 생성되었을 때 currentPosition은 -1이고 tryCount는 1이다.")
    @Test
    void currentPosition() {
        assertThat(bridgeGame.getCurrentPosition()).isEqualTo(-1);
        assertThat(bridgeGame.getTryCount()).isEqualTo(1);
    }
    
    @DisplayName("move 메소드 실행 시 currentPosition이 1증가한다.")
    @ValueSource(ints = {1, 2, 3, 4})
    @ParameterizedTest
    void moveByTimes(int moveCount) {
        for (int i = 0; i < moveCount; i++) {
            bridgeGame.move(BridgeMove.DOWN);
        }
        
        assertThat(bridgeGame.getCurrentPosition()).isEqualTo(-1 + moveCount);
    }
    
    @DisplayName("move 메소드 실행 시 끝 포지션에 도착했을 경우 currentPosition을 증가시키지 않는다.")
    @Test
    void moveByOverTimes() {
        for (int i = 0; i < 1000; i++) {
            bridgeGame.move(BridgeMove.DOWN);
        }
        assertThat(bridgeGame.getCurrentPosition()).isEqualTo(4);
    }
    
    
    @DisplayName("retryByValid 메소드는 currentPosition은 -1로 tryCount는 1을 증가시킨다.")
    @Test
    void retryByValid() {
        bridgeGame.move(BridgeMove.DOWN);
        bridgeGame.retry();
        
        assertThat(bridgeGame.getCurrentPosition()).isEqualTo(-1);
        assertThat(bridgeGame.getTryCount()).isEqualTo(2);
        
        bridgeGame.move(BridgeMove.DOWN);
        bridgeGame.retry();
        
        assertThat(bridgeGame.getCurrentPosition()).isEqualTo(-1);
        assertThat(bridgeGame.getTryCount()).isEqualTo(3);
    }
    
    @DisplayName("isReachedLastPosition 메소드는 다리 끝에 도달했으면 true를 반환한다.")
    @Test
    void isReachedLastPositionByReached() {
        for (int i = 0; i < 5; i++) {
            bridgeGame.move(BridgeMove.DOWN);
        }
        assertThat(bridgeGame.isReachedLastPosition()).isEqualTo(true);
    }
    
    @DisplayName("isReachedLastPosition 메소드는 다리 끝에 도달하지 않았으면 false를 반환한다.")
    @Test
    void isReachedLastPositionByNotReached() {
        assertThat(bridgeGame.isReachedLastPosition()).isEqualTo(false);
    }
    
    @DisplayName("canMoveToNextPosition 메소드는 이동할 방향이 주어지고 다음 포지션으로 이동할 수 있으면 true를 반환한다.")
    @Test
    void canMoveToNextPositionByValid() {
        assertThat(bridgeGame.canMoveToNextPosition(BridgeMove.DOWN)).isEqualTo(true);
        bridgeGame.move(BridgeMove.DOWN);
        assertThat(bridgeGame.canMoveToNextPosition(BridgeMove.UP)).isEqualTo(true);
        assertThat(bridgeGame.canMoveToNextPosition(BridgeMove.UP)).isEqualTo(true);
        bridgeGame.move(BridgeMove.UP);
        bridgeGame.move(BridgeMove.UP);
        assertThat(bridgeGame.canMoveToNextPosition(BridgeMove.UP)).isEqualTo(true);
    }
    
    @DisplayName("canMoveToNextPosition 메소드는 이동할 방향이 주어지고 다음 포지션으로 이동할 수 없으면 false를 반환한다.")
    @Test
    void canMoveToNextPositionByInvalid() {
        assertThat(bridgeGame.canMoveToNextPosition(BridgeMove.UP)).isEqualTo(false);
        assertThat(bridgeGame.canMoveToNextPosition(BridgeMove.DOWN)).isEqualTo(true);
        bridgeGame.move(BridgeMove.DOWN);
        assertThat(bridgeGame.canMoveToNextPosition(BridgeMove.DOWN)).isEqualTo(false);
    }
}
