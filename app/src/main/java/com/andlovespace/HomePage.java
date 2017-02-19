package com.andlovespace;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HomePage extends AppCompatActivity {
    String name,mail,dob;
    String some;


    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);


        Intent intent=getIntent();
       some=intent.getStringExtra("Phone");
    }



    public void saveDetails(View view) {
        //Toast.makeText(this, "Saving Details...", Toast.LENGTH_SHORT).show();
        EditText m_email=(EditText)findViewById(R.id.email);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(m_email.getText().toString());
        if(matcher.find()){
            Toast.makeText(this, "Valid Email", Toast.LENGTH_SHORT).show();
            Background background=new Background();
            EditText m_name=(EditText)findViewById(R.id.name);

            EditText m_dob=(EditText)findViewById(R.id.dob);
            name=m_name.getText().toString();
            mail=m_email.getText().toString();
            dob=m_dob.getText().toString();
            Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, mail, Toast.LENGTH_SHORT).show();
            background.execute(name,mail,dob);
        }
        else
            Toast.makeText(this,"Invalid Email",Toast.LENGTH_LONG).show();

    }
    class Background extends AsyncTask<String,String,String>{
        String url1;
        @Override
        protected void onPreExecute() {


            url1 = "http://192.168.0.4/test/sample.php";
        }

        @Override
        protected String doInBackground(String... inputs) {
            String para_name,para_mail,para_dob;
            para_name=inputs[0];
            para_mail=inputs[1];
            para_dob=inputs[2];

            try {
                URL url = new URL(url1);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                OutputStream out = conn.getOutputStream();
                BufferedWriter buff = new BufferedWriter(new OutputStreamWriter(out,"UTF-8"));
                String data_string = URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(para_name,"UTF-8")+"&"+
                        URLEncoder.encode("mail","UTF-8")+"="+URLEncoder.encode(para_mail,"UTF-8")+"&"+
                        URLEncoder.encode("dob","UTF-8")+"="+URLEncoder.encode(para_dob,"UTF-8")+"&"+
                        URLEncoder.encode("phone","UTF-8")+"="+URLEncoder.encode(some,"UTF-8");
                buff.write(data_string);
                buff.flush();
                buff.close();
                out.close();
                InputStream in = conn.getInputStream();
                in.close();
                conn.disconnect();
                return "One row of data is Inserted";

            } catch (MalformedURLException e) {

                Log.e("Mal","mal");
            } catch (IOException e) {
                //Toast.makeText(HomePage.this, "IO", Toast.LENGTH_SHORT).show();
                Log.e("IO",e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String random) {
            Toast.makeText(HomePage.this, random, Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }
    }
}
