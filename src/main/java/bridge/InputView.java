package bridge;

import bridge.domain.game.BridgeGameCommand;
import camp.nextstep.edu.missionutils.Console;

/**
 * 사용자로부터 입력을 받는 역할을 한다.
 */
public class InputView {

    /**
     * 다리의 길이를 입력받는다.
     */
    public int readBridgeSize() {
        int numberInput = getNumber();
        validateBridgeSize(numberInput);
        return numberInput;
    }
    
    private void validateBridgeSize(int input) {
        if (input < 1) {
            throw new IllegalArgumentException("다리 길이는 1 이상이여야 합니다.");
        }
    }

    /**
     * 사용자가 이동할 칸을 입력받는다.
     */
    public BridgeMove readMoving() {
        String input = Console.readLine();
        validateReadMovingInput(input);
        return BridgeMove.findByDirection(input).orElseThrow();
    }
    
    private void validateReadMovingInput(String input) {
        if (!BridgeMove.isContain(input)) {
            throw new IllegalArgumentException("잘못된 입력입니다. 다시 시도하세요.");
        }
    }
    
    /**
     * 사용자가 게임을 다시 시도할지 종료할지 여부를 입력받는다.
     */
    public BridgeGameCommand readGameCommand() {
        String input = Console.readLine();
        validateReadGameCommandInput(input);
        return BridgeGameCommand.findByCommand(input).orElseThrow();
    }
    
    private void validateReadGameCommandInput(String input) {
        if (!BridgeGameCommand.isContain(input)) {
            throw new IllegalArgumentException("잘못된 입력입니다. 다시 시도하세요.");
        }
    }
    
    public int getNumber() throws IllegalArgumentException {
        String line = Console.readLine();
        validateNumberString(line);
        return Integer.parseInt(line);
    }
    
    private void validateNumberString(String line) {
        for (char c : line.toCharArray()) {
            if (!Character.isDigit(c)) {
                throw new IllegalArgumentException("잘못된 입력입니다. 다시 시도하세요.");
            }
        }
    }
}
