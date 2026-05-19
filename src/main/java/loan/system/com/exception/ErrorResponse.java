package loan.system.com.exception;

import java.time.LocalDateTime;

public class ErrorResponse {
    private int status;
    private String menssagem;
    private LocalDateTime timestamp;

    public ErrorResponse(String menssagem, int status) {
        this.menssagem = menssagem;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMenssagem() {
        return menssagem;
    }

    public void setMenssagem(String menssagem) {
        this.menssagem = menssagem;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
