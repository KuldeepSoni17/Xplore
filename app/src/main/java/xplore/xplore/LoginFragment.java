package xplore.xplore;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
public class LoginFragment extends Fragment {

    EditText username1, password1;
    Button login;
    String susername1, spassword1;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        username1 = (EditText) view.findViewById(R.id.username1);
        password1 = (EditText) view.findViewById(R.id.password1);
        login = (Button) view.findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (new ConnectionDetector(getActivity()).isConnectingToInternet()) {
                    susername1 = username1.getText().toString();
                    spassword1 = password1.getText().toString();
                    SharedPreferences preferences = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("username",susername1);
                    editor.commit();
                    new logindata().execute();
                } else {
                    new ConnectionDetector(getActivity()).connectiondetect();
                }
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    private class logindata extends AsyncTask<String, String, String> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(getActivity());
            pd.setMessage("loading...!! ");
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> hashmap = new HashMap<>();
            hashmap.put("name", susername1);
            hashmap.put("password", spassword1);
            return new MakeServiceCall().getPostData("http://192.168.43.178/json/xplore_login.php", hashmap);

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (pd.isShowing()) {
                pd.dismiss();
                password1.setText("");
                username1.setText("");
            }
            try {
                JSONObject object = new JSONObject(s);
                if (object.getString("Status").equalsIgnoreCase("True")) {
                    Snackbar.make(getView(), object.getString("Message"), Snackbar.LENGTH_LONG).show();
                    startActivity(new Intent(getActivity(), ListActivity.class));
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

