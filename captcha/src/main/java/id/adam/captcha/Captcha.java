package id.adam.captcha;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.nfc.Tag;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Random;

import id.adam.captcha.adapter.AdapterCaptcha;
import id.adam.captcha.interfaces.CaptchaItemClick;
import id.adam.captcha.interfaces.EventCaptcha;

public class Captcha {
    private Dialog dialog;
    private int[] list_captcha = {
            R.drawable.ic_pencil,
            R.drawable.ic_register,
            R.drawable.ic_book,
            R.drawable.ic_globe,
            R.drawable.ic_trash,
            R.drawable.ic_school_material,
            R.drawable.ic_scissor
    };

    private ArrayList<Integer> list_item_captcha = new ArrayList<>();
    private int item_answer=-1;
    private int item_wrong=-1;
    private Activity context;

    private RelativeLayout header_dialog;
    private RecyclerView recyclerView;
    private ImageButton btn_close;
    private int def_header = R.color.header_dialog_bg_default;

    private EventCaptcha EventCaptcha;

    public Captcha(Activity activity, EventCaptcha EventCaptcha) {
        this.context = activity;
        this.EventCaptcha = EventCaptcha;
    }

    private void showDialogs(){
        dialog = new Dialog(context);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_captcha);

        recyclerView = dialog.findViewById(R.id.rv_captcha);
        header_dialog = dialog.findViewById(R.id.header_dialog_captcha);

        AdapterCaptcha AdapterCaptcha = new AdapterCaptcha(context, list_item_captcha, new CaptchaItemClick() {
            public void itemClick(int value) {
                if (value == list_captcha[item_answer]) {
                    EventCaptcha.Response(true);
                } else {
                    EventCaptcha.Response(false);
                }
                close_reset();
            }
        });

        recyclerView.setAdapter(AdapterCaptcha);
        recyclerView.setLayoutManager(new GridLayoutManager(context,2));
        btn_close = dialog.findViewById(R.id.btn_close_captcha);
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                close_reset();
            }
        });
        dialog.show();
    }

    public void setHeaderBackground(String hex_color){
        try {
            header_dialog.setBackgroundColor(Color.parseColor(hex_color));
        } catch (IllegalArgumentException iae) {
            Log.e(context.getClass().getName(),"invalid hex color");
            header_dialog.setBackgroundColor(Color.parseColor("#358038"));
        }
    }
    public void Generate_ReCaptcha(){
        try {
            if (list_captcha.length >= 2) {
                int item_true = new Random().nextInt(list_captcha.length);
                int item_false = new Random().nextInt(list_captcha.length);

                if (item_true != item_false) {
                    item_answer = item_true;
                    item_wrong = item_false;

                    for (int i = 1; i <= 3; i++) {
                        list_item_captcha.add(list_captcha[item_wrong]);
                    }
                    list_item_captcha.add(list_captcha[item_answer]);
                    showDialogs();
                } else {
                    Generate_ReCaptcha();
                }
            } else {
                if (list_captcha.length == 0) {
                    EventCaptcha.Error("list kosong");
                } else {
                    EventCaptcha.Error("kesalahan sistem");
                }
            }
        }catch (Exception e){
            EventCaptcha.Error(e.getMessage());
        }
    }

    private void close_reset(){
        list_item_captcha.clear();
        item_answer=-1;
        item_wrong=-1;
        dialog.dismiss();
    }
}
