package ru.hse.iuturakulov.jigsawbysockets.models;

/**
 * The type Json response.
 */
public class JsonResponse {
    private String function;
    private String username;
    private String message;
    private int index;
    private String opponent;
    private String status;

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets username.
     *
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets function.
     *
     * @return the function
     */
    public String getFunction() {
        return function;
    }

    /**
     * Sets function.
     *
     * @param function the function
     */
    public void setFunction(String function) {
        this.function = function;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets message.
     *
     * @param message the message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets index.
     *
     * @return the index
     */
    public int getIndex() {
        return index;
    }

    /**
     * Sets index.
     *
     * @param index the index
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * Gets opponent.
     *
     * @return the opponent
     */
    public String getOpponent() {
        return opponent;
    }

    /**
     * Sets opponent.
     *
     * @param opponent the opponent
     */
    public void setOpponent(String opponent) {
        this.opponent = opponent;
    }
}
