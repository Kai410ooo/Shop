
package model;

public class DuplicateKeyException extends RuntimeException {
    public DuplicateKeyException(String message) {
        super(message); // メッセージをそのまま渡す
    }
}

