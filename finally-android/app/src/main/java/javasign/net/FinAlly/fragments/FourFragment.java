package javasign.net.FinAlly.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import javasign.net.FinAlly.R;
import javasign.net.FinAlly.activities.Login;
import javasign.net.FinAlly.activities.MainActivity;
import javasign.net.FinAlly.activities.Setting;
import javasign.net.FinAlly.storage.SharedPrefManager;


public class FourFragment extends Fragment {

    public FourFragment() {
        // Required empty public constructor
    }

    TextView tv_username, tv_email, tv_setting, tv_keluar;
    ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_four, container, false);

        tv_username = (TextView) rootView.findViewById(R.id.tv_username);
        tv_email = (TextView) rootView.findViewById(R.id.tv_email);
        tv_setting = (TextView) rootView.findViewById(R.id.tv_setting);
        tv_keluar = (TextView) rootView.findViewById(R.id.tv_keluar);


        tv_username.setText(SharedPrefManager.getInstance(getActivity()).getUser().getUsername());
        tv_email.setText(SharedPrefManager.getInstance(getActivity()).getUser().getEmail());

        tv_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Setting.class);
                startActivity(intent);
            }
        });

        tv_keluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage("Logout...");
                progressDialog.show();

                SharedPrefManager.getInstance(getActivity()).clear();
                Intent intent = new Intent(getActivity(), Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });

        return rootView;
    }

}
