package id.adam.captcha.interfaces;

public interface EventCaptcha {
    void Response(boolean status);
    void Error(String message);
}
