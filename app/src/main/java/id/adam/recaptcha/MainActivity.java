package id.adam.recaptcha;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import id.adam.captcha.Captcha;
import id.adam.captcha.interfaces.EventCaptcha;

public class MainActivity extends AppCompatActivity implements EventCaptcha {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button dialogBtn = findViewById(R.id.btn_open);
        dialogBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Captcha reCaptcha = new Captcha(MainActivity.this,MainActivity.this);
                reCaptcha.Generate_ReCaptcha();
            }
        });
    }

    public void Response(boolean status) {
        if(status){
            Toast.makeText(this, "berhasil", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "gagal", Toast.LENGTH_SHORT).show();
        }
    }

    public void Error(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
