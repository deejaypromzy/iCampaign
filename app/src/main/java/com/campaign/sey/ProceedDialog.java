package com.campaign.sey;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class ProceedDialog extends Dialog implements
        View.OnClickListener {
    private final Activity c;
    public Dialog d;
    public TextView yes, no ,pk, dr,appdistance;
    private NavController navController;
    private PrefManager prefManager;

    public ProceedDialog(Activity context) {
        super(context);
        this.c=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);
        navController = Navigation.findNavController(c, R.id.nav_host_fragment);
        prefManager = new PrefManager(c);


        pk = findViewById(R.id.pk);
        dr = findViewById(R.id.dr);
        appdistance = findViewById(R.id.appdistance);

        pk.setText(prefManager.getPickLocation());
        dr.setText(prefManager.getDropLocation());

        yes =  findViewById(R.id.proceed);
        no =  findViewById(R.id.cancel);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.proceed:
//                navController.navigate(R.id.startFragment);
//                break;
//            case R.id.cancel:
//                dismiss();
//                break;
//            default:
//                break;
//        }
        dismiss();
    }
}