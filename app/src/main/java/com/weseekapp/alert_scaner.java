package com.weseekapp;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class alert_scaner {
    private Context context;
    private Drawable drawable;

    private TextView conformBtn, backBtn;
    private CircleImageView signup_img;
    private int[] imgs = {R.drawable.cir_img2, R.drawable.cir_img3,
                        R.drawable.cir_img4, R.drawable.cir_img5, R.drawable.cir_img6,
                        R.drawable.cir_img7, R.drawable.cir_img9};
    private int[] imgs_id = {R.id.pop_img1, R.id.pop_img2, R.id.pop_img3, R.id.pop_img4,
                        R.id.pop_img5, R.id.pop_img6, R.id.pop_img7, R.id.pop_img8,
                        R.id.pop_img9};

    public alert_scaner(Context context, Drawable drawable){
        this.context = context;
        this.drawable = drawable;
    }

        private ModifyReturnListener modifyReturnListener;

    public interface ModifyReturnListener{
        void afterModify(Drawable context);
    }
    public void setModifyReturnListener(ModifyReturnListener modifyReturnListener){
        this.modifyReturnListener = modifyReturnListener;
    }

    public void callFun(){
        final Dialog dlg = new Dialog(context);
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dlg.setContentView(R.layout.sign_up_pop);
        dlg.show();

        conformBtn = (TextView) dlg.findViewById(R.id.conformBtn);
        backBtn = (TextView) dlg.findViewById(R.id.backBtn);
        signup_img = (CircleImageView) dlg.findViewById(R.id.signup_img);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlg.onBackPressed();
            }
        });

        conformBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < imgs.length; i++){
                    if (view.getId() == imgs_id[i]){
                        Drawable ModifyedImg = signup_img.getDrawable();
                        modifyReturnListener.afterModify(ModifyedImg);
                        dlg.onBackPressed();
                    }
                }

//                Drawable ModifyedImg = signup_img.getDrawable();
//                modifyReturnListener.afterModify(ModifyedImg);
//                dlg.onBackPressed();
            }
        });

    }

}
