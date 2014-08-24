package credit.calc.ipmc.legumecredit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

public class Email extends Activity {

//    private EditText subjectInput;
//    private EditText textInput;
    private Button legume_btn;
    private Button manure_btn;
//    private Button send_btn;


    public static String resultLegumeE = "";
    public static String speciesLegumeE = "";
    public static String soilTypeLegumeE = "";
    public static String regrowthLegumeE = "";
    public static String standDensityLegumeE = "";

    public static String creditN_E = "";
    public static String typeManureE = "";
    public static String incorpTimeE = "";
    public static String sourceManureE = "";
    public static String counterManureE = "";
    public static String unitManureE = "";
    public static String creditP_E = "";
    public static String creditK_E = "";
    public static String creditS_E = "";


    private static String urlemail = "EmailInfo.json";

    @Override
    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_email);

//        subjectInput = (EditText) findViewById(R.id.subject_input);
//        textInput = (EditText) findViewById(R.id.text_input);

        legume_btn = (Button) findViewById(R.id.legume_button);
        manure_btn = (Button) findViewById(R.id.manure_button);
//        send_btn = (Button) findViewById(R.id.send_button);


        // Sets the text of the inputs
 //       subjectInput.setText(getSubjectTime());


        legume_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                FileInputStream fin = null;
                try {
                    fin = openFileInput("EmailLegume");

                    int c;
                    String temp = "";
                    while ((c = fin.read()) != -1) {
                        temp = temp + Character.toString((char) c);
                    }
                    //string temp contains all the data of the file.
                    fin.close();
                    //Log.v("EmailOutupt echke",temp );



                    if (temp.length() > 0) {
                        JSONObject jobj = new JSONObject(temp.toString());
                        Log.v("checkE", jobj.toString());
                        resultLegumeE = jobj.getString("lCredit");
                        if (!resultLegumeE.equals("000")) {
                            //parse the JSON JSON object
                            speciesLegumeE = jobj.getString("species");
                            soilTypeLegumeE = jobj.getString("soil");
                            standDensityLegumeE = jobj.getString("density");
                            regrowthLegumeE = jobj.getString("regrowth");

                            emailReportLegume();
                            return;
                        }

                    }

                    Context context = getApplicationContext();
                    CharSequence text = "Please perform a Legume calculation first";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        manure_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FileInputStream fin = null;
                try {
                    fin = openFileInput("EmailManure");

                    int c;
                    String temp = "";
                    while ((c = fin.read()) != -1) {
                        temp = temp + Character.toString((char) c);
                    }
                    //string temp contains all the data of the file.
                    fin.close();
                    //Log.v("EmailOutupt echke",temp );



                    if (temp.length() > 0) {

                        JSONObject jobj = new JSONObject(temp.toString());
                        Log.v("checkE", jobj.toString());
                        creditN_E = jobj.getString("MCreditN");
                        if (!creditN_E.equals("00")) {
                            //parse the JSON JSON object
                            typeManureE = jobj.getString("type");
                            incorpTimeE = jobj.getString("time");
                            sourceManureE = jobj.getString("source");
                            counterManureE = jobj.getString("count");
                            creditK_E = jobj.getString("MCreditK");

                            creditP_E = jobj.getString("MCreditP");
                            creditS_E = jobj.getString("MCreditS");
                            emailReportManure();
                            return;
                        }

                    }

                    Context context = getApplicationContext();
                    CharSequence text = "Please perform a Manure calculation first";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }




            }
        });


    }


    /*
         * Gets the subject line of the email, which consists of getting the date and time
         */
    private String getSubjectTime() {
        Calendar cal = Calendar.getInstance(Locale.getDefault());

        int month = cal.get(Calendar.MONTH);

        String day = month + 1 + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.YEAR);

        return  day;
    }


    /*
    Executed when the email button is clicked
    */
    private void emailReportLegume() {


        Intent intent = new Intent(Intent.ACTION_SEND);                                // Sets the intent to be an email intent
        intent.setType("plain/text");                                                // I don't know what this does but it's necessary
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{""});                    // The email address to send to. We don't know who the user will want to send it to.
        intent.putExtra(Intent.EXTRA_SUBJECT, "Legume Credit Report: " + getSubjectTime());                // The subject line

        // Build the body text string
        String bodyText = "";
        bodyText += "Forage Species: " + speciesLegumeE + "\nSoil Type: " + soilTypeLegumeE + "\n";
        bodyText += "Amount of growth: " + regrowthLegumeE  + "\nStand Density: " +
                standDensityLegumeE + "\nNitrogen Credit: "+ resultLegumeE +" (lb N/acre)²"+"\n\n\n\n";

        bodyText += "This email generated by N Credit Calculators, an Android app by the University of Wisconsin-Madison's NPM program\n";
        bodyText += "http://ipcm.wisc.edu/apps/";

        intent.putExtra(Intent.EXTRA_TEXT, bodyText);            // The body text
        startActivity(Intent.createChooser(intent, ""));        // Starts the email activity, passing the given data with it

    }

    private void emailReportManure() {


        Intent intent = new Intent(Intent.ACTION_SEND);                                // Sets the intent to be an email intent
        intent.setType("plain/text");                                                // I don't know what this does but it's necessary
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{""});                    // The email address to send to. We don't know who the user will want to send it to.
        intent.putExtra(Intent.EXTRA_SUBJECT, "Manure Credit Report: " + getSubjectTime());                // The subject line

        // Build the body text string
        String bodyText = "";
        String time = "";

        if(typeManureE.equals("Solid")){
            unitManureE = "ton/acre";
        }
        else {
            unitManureE = "gal/acre";
        }

        if(incorpTimeE.equals("<")){
            time = ">3 days";
        }
        else if(incorpTimeE.equals("-")){
            time = "1 hour - 3 days";
        }
        else {
            time = "< 1 hour";

        }



        bodyText += "Manure Type: " + typeManureE +
                "\n\nTime to Incorporation: " + time + "\n\n";

        bodyText += "Manure Source: " + sourceManureE + "\n\nAmount to add: " + counterManureE + " " + unitManureE +
                "\n\nManure Credit: " + "N - " + creditN_E +
                "\n                           " + "P₂O₅ - " + creditP_E +
                "\n                           " + "K₂O - " + creditK_E +
                "\n                           " + "S - " + creditS_E + "\n\n";

        bodyText += "This email generated by N Credit Calculators, an Android app by the University of Wisconsin-Madison's NPM program\n";
        bodyText += "http://ipcm.wisc.edu/apps/";

        intent.putExtra(Intent.EXTRA_TEXT, bodyText);            // The body text
        startActivity(Intent.createChooser(intent, ""));        // Starts the email activity, passing the given data with it

    }
}
