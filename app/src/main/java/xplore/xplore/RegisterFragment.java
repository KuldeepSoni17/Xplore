package xplore.xplore;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {
    EditText name, password, email, cpassword;
    Button register;
    String sname, spassword, scpassword, semail;
    String msg = "This can't be empty";


    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        name = (EditText) view.findViewById(R.id.username);
        password = (EditText) view.findViewById(R.id.password);
        email = (EditText) view.findViewById(R.id.email);
        cpassword = (EditText) view.findViewById(R.id.confirmpassword);
        register = (Button) view.findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (new ConnectionDetector(getActivity()).isConnectingToInternet()) {
                    if (name.getText().toString().equalsIgnoreCase("")) {
                        name.setError(msg);
                    } else if (email.getText().toString().equalsIgnoreCase("")) {
                        email.setError(msg);
                    } else if (password.getText().toString().equalsIgnoreCase("")) {
                        password.setError(msg);
                    } else if (cpassword.getText().toString().equalsIgnoreCase("")) {
                        cpassword.setError(msg);
                    } else {
                        sname = name.getText().toString();
                        semail = email.getText().toString();
                        spassword = password.getText().toString();
                        scpassword = cpassword.getText().toString();
                        if (spassword.length() > 11) {
                            Toast.makeText(getActivity(), "password length must be less than 11", Toast.LENGTH_SHORT).show();
                        } else if (spassword.equals(scpassword)) {
                            new insertdata().execute();
                        } else {
                            Toast.makeText(getActivity(), "password must be match", Toast.LENGTH_SHORT).show();
                        }

                    }

                } else {
                    new ConnectionDetector(getActivity()).connectiondetect();
                }
            }
        });
        return view;
    }

    private class insertdata extends AsyncTask<String, String, String> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(getActivity());
            pd.setMessage("Loading!!");
            pd.show();

        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("name", sname);
            hashMap.put("email", semail);
            hashMap.put("password", spassword);
            hashMap.put("cpassword", scpassword);
            return new MakeServiceCall().getPostData("http://192.168.1.7/json/xplore_register.php", hashMap);

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (pd.isShowing()) {
                pd.dismiss();
                cpassword.setText("");
                password.setText("");
                email.setText("");
                name.setText("");
            }
            try {
                JSONObject object = new JSONObject(s);
                if (object.getString("Status").equalsIgnoreCase("True")) {
                    Snackbar.make(getView(), object.getString("Message"), Snackbar.LENGTH_LONG).show();
                    int c = 0;
                    if (c == 0){
                        ((MainActivity)getActivity()).changePosition(0);
                        return;
                    }

                    //Toast.makeText(getActivity().getApplicationContext(), object.getString("Message"), Toast.LENGTH_SHORT).show();
                } else {
                    Snackbar.make(getView(), object.getString("Message"), Snackbar.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "555", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
