package id.adam.captcha.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import id.adam.captcha.R;
import id.adam.captcha.interfaces.CaptchaItemClick;

public class AdapterCaptcha extends RecyclerView.Adapter<AdapterCaptcha.MyViewHolder> {
    private static ArrayList<Integer> list_item_recaptcha;
    private Context context;
    private CaptchaItemClick itemClick;

    public AdapterCaptcha(Activity context, ArrayList<Integer> list_item_recaptcha, CaptchaItemClick itemClick) {
        this.context = context;
        AdapterCaptcha.list_item_recaptcha = list_item_recaptcha;
        this.itemClick = itemClick;
    }

    @NonNull
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_row_captcha, parent, false);
        return new MyViewHolder(view,itemClick);
    }

    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.img.setImageDrawable(context.getResources().getDrawable(list_item_recaptcha.get(position)));
    }

    public int getItemCount() {
        return list_item_recaptcha.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView img;
        CaptchaItemClick itemClick;

        MyViewHolder(View itemView, CaptchaItemClick itemClick) {
            super(itemView);
            img = itemView.findViewById(R.id.img_item_row_captcha);
            this.itemClick = itemClick;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClick.itemClick(list_item_recaptcha.get(getAdapterPosition()));
        }
    }
}