package credit.calc.ipmc.legumecredit;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * Created by mo on 8/5/14.
 */
public class ManureCalc extends Activity {
    private ScrollView mainLayout;

    private TextView manure_type1;
    private TextView manure_type2;
    private TextView incorpTime1;
    private TextView incorpTime2;
    private TextView incorpTime3;
    private TwoStateToggle manureType;
    private ThreeStateToggle incorpTime;

    private TextView decrease;
    private TextView increase;
    private TextView countRes;
    private int incrementor = 1;
    private  TwoStateToggle counter;
    private boolean mDown;

    private Spinner spinner;

    private TextView resultN;
    private TextView resultP;
    private TextView resultK;
    private TextView resultS;

    static JSONObject jObj = null;
    private String myjsonstring = "";
    private static String url = "file:///android_asset/ManureCredit.json";


    private String manureSpecies_Tag;
    private String manureType_Tag;
    private String incorptime_Tag;

    final Handler handler = new Handler();

    final int upperbound = 500;
    final int lowerbound = 1;
    final String unitSolid = " ton/acre";
    final String unitLiquid = " gal/acre";



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_manure);
        //setContentView(R.layout.activity_main);

//        FrameLayout container = (FrameLayout) findViewById(R.id.container);
//        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View menuLayout = inflater.inflate(R.layout.layout_creditcalc, container, true);


        // Assign all of the instance variables
        mainLayout = (ScrollView) findViewById(R.id.main_parent);


        manure_type1 = (TextView) findViewById(R.id.manure_type1);
        manure_type2 = (TextView) findViewById(R.id.manure_type2);
        incorpTime1 = (TextView) findViewById(R.id.incorp_time1);
        incorpTime2 = (TextView) findViewById(R.id.incorp_time2);
        incorpTime3 = (TextView) findViewById(R.id.incorp_time3);

        decrease = (TextView) findViewById(R.id.minus);
        increase = (TextView) findViewById(R.id.plus);
        counter = new TwoStateToggle(decrease, increase);
        countRes = (TextView) findViewById(R.id.manure_input);

        resultN = (TextView) findViewById(R.id.manure_output0);
        resultP = (TextView) findViewById(R.id.manure_output1);
        resultK = (TextView) findViewById(R.id.manure_output2);
        resultS = (TextView) findViewById(R.id.manure_output3);

        manureType = new TwoStateToggle(manure_type1, manure_type2);
        incorpTime = new ThreeStateToggle(incorpTime1, incorpTime2, incorpTime3);




        //read the jason file
        getJSONfile(url);



        //Set spinner adapter
        spinner = (Spinner) findViewById(R.id.manure_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.solid_manure, android.R.layout.simple_spinner_item);
        adapter.notifyDataSetChanged();
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner

        final ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.liquid_manure, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        spinner.setAdapter(adapter);




        manure_type1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (manureType.getCurrentState() != 0) {
                    manureType.setState(0);
                    spinner.setAdapter(adapter);
                }

                calculate();
            }


        });

        manure_type2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(manureType.getCurrentState() != 1)
                {
                    manureType.setState(1);
                    spinner.clearAnimation();
                    spinner.setAdapter(adapter2);

                }

                    calculate();
            }

        });

        incorpTime1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(incorpTime.getCurrentState() != 0)
                {
                    incorpTime.setState(0);

                }
                calculate();

            }
        });

        incorpTime2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(incorpTime.getCurrentState() != 1)
                {
                    incorpTime.setState(1);
                }
                calculate();

            }
        });

        incorpTime3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (incorpTime.getCurrentState() != 2) {
                    incorpTime.setState(2);
                }
                calculate();

            }
        });




        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            String selected;
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                // An item was selected. You can retrieve the selected item using
                // parent.getItemAtPosition(pos)

                selected = parent.getItemAtPosition(pos).toString();

//                Context context = getApplicationContext();
//                CharSequence text = selected;
//                int duration = Toast.LENGTH_SHORT;
//                Toast toast = Toast.makeText(context, text, duration);
//                toast.show();

                manureSpecies_Tag = spinner.getSelectedItem().toString();
                calculate();

            }
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
                selected = "";
            }


        });





        decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                counter.setState(0);
                if(incrementor > lowerbound){
                    incrementor --;
                    String temp;
                    if(manureType.getCurrentState() == 0){
                        temp = Integer.toString(incrementor) + unitSolid;
                    }else{
                        temp = Integer.toString(incrementor) + unitLiquid;
                    }

                    countRes.setText(temp);

                }


                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                       counter.reSetState();
                    }
                }, 200);
                calculate();

            }
        });

        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                counter.setState(1);
                if(incrementor < upperbound){
                    incrementor++;
                    String temp;
                    if(manureType.getCurrentState() == 0){
                        temp = Integer.toString(incrementor) + unitSolid;
                    }else{
                        temp = Integer.toString(incrementor) + unitLiquid;
                    }
                    countRes.setText(temp);
                }


                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        counter.reSetState();
                    }
                }, 200);
                calculate();



            }
        });

//use to handle long touch and accelerate the increment of decrement
        decrease.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionevent) {
                int action = motionevent.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    counter.setState(0);
                    Log.i("repeatBtn", "MotionEvent.ACTION_DOWN");
                    handler.removeCallbacks(mUpdateTaskdown);
                    handler.postAtTime(mUpdateTaskdown,SystemClock.uptimeMillis() + 100);
                } else if (action == MotionEvent.ACTION_UP) {
                    Log.i("repeatBtn", "MotionEvent.ACTION_UP");
                    handler.removeCallbacks(mUpdateTaskdown);
                    counter.reSetState();
                }//end else
                return false;
            }//end on touch

        });//end b other button


        increase.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionevent) {

                int action = motionevent.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    counter.setState(1);
                    Log.i("repeatBtn", "MotionEvent.ACTION_DOWN");
                    handler.removeCallbacks(mUpdateTaskup);
                    handler.postAtTime(mUpdateTaskup, SystemClock.uptimeMillis() + 100);
                } else if (action == MotionEvent.ACTION_UP) {
                    Log.i("repeatBtn", "MotionEvent.ACTION_UP");
                    handler.removeCallbacks(mUpdateTaskup);
                    counter.reSetState();
                }//end else
                return false;
            } //end onTouch
        }); //end b my button






    }

    //accelerate the increment of decrement
    private Runnable mUpdateTaskup = new Runnable() {
        public void run() {
            if(incrementor < upperbound) {
                incrementor++;
                String temp;
                if (manureType.getCurrentState() == 0) {
                    temp = Integer.toString(incrementor) + unitSolid;
                } else {
                    temp = Integer.toString(incrementor) + unitLiquid;
                }
                countRes.setText(String.valueOf(temp));
                Log.i("repeatBtn", "repeat click");
            }
                handler.postAtTime(this, SystemClock.uptimeMillis() + 100);

        }//end run
    };// end runnable

    //accelerate the increment of decrement
    private Runnable mUpdateTaskdown = new Runnable() {
        public void run() {
            if(incrementor > lowerbound) {
                incrementor--;
                String temp;
                if (manureType.getCurrentState() == 0) {
                    temp = Integer.toString(incrementor) + unitSolid;
                } else {
                    temp = Integer.toString(incrementor) + unitLiquid;
                }
                countRes.setText(String.valueOf(temp));
                Log.i("repeatBtn", "repeat click");
            }
                handler.postAtTime(this, SystemClock.uptimeMillis() + 100);

        }//end run
    };// end Runnable






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }




    public void calculate() {

    if(manureType.getCurrentState()>-1 && incorpTime.getCurrentState()>-1 ) {
        if (manureType.getCurrentState() == 0) {
            manureType_Tag = "Solid";
        } else {
            manureType_Tag = "Liquid";
        }

        switch (incorpTime.getCurrentState()) {
            case 0:
                incorptime_Tag = ">";
                break;
            case 1:
                incorptime_Tag = "-";
                break;
            case 2:
                incorptime_Tag = "<";
                break;

        }
        parseJASON();

    }


    }


//get the jason file
    public JSONObject getJSONfile (String url){
        StringBuffer sb = new StringBuffer();
        BufferedReader br = null;

        try {
            br = new BufferedReader(new InputStreamReader(getAssets().open("ManureCredits.json")));
            String temp;
            while ((temp = br.readLine()) != null) {
                sb.append(temp);
            }

            myjsonstring = sb.toString();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close(); // stop reading
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        // try parse the string to a JSON object
        try {
            jObj = new JSONObject(myjsonstring);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        return jObj;
    }

//parse the jason object into text and set them into the text view
    public void parseJASON(){

        String aJasonrsltN= "";
        String aJasonrsltP= "";
        String aJasonrsltK= "";
        String aJasonrsltS= "";

        int rsltN;
        int rsltP;
        int rsltK;
        int rsltS;



        try {
            // Creating JSONObject from String
            JSONObject aJsonManure = jObj.getJSONObject(manureType_Tag);

            JSONObject aJasonAnimal= aJsonManure.getJSONObject(manureSpecies_Tag);


            JSONObject aJasonN = aJasonAnimal.getJSONObject("N");

            aJasonrsltN = aJasonN.getString(incorptime_Tag);

            aJasonrsltK = aJasonAnimal.getString("K");
            aJasonrsltP = aJasonAnimal.getString("P");
            aJasonrsltS = aJasonAnimal.getString("S");


            rsltN = Integer.parseInt(aJasonrsltN) * incrementor;
            rsltP = Integer.parseInt(aJasonrsltP) * incrementor;
            rsltK = Integer.parseInt(aJasonrsltK) * incrementor;
            rsltS = Integer.parseInt(aJasonrsltS) * incrementor;


            resultN.setText(Integer.toString(rsltN));
            resultP.setText(Integer.toString(rsltP));
            resultK.setText(Integer.toString(rsltK));
            resultS.setText(Integer.toString(rsltS));



        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }










//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        switch (id) {
//            case R.id.action_settings1:
//                emailReport();
//                return true;
//            case R.id.action_settings2:
//                helpinfo("file:///android_asset/legume_help.html");
//
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//
////        return super.onOptionsItemSelected(item);
//    }


    /*
    Executed when the email button is clicked
    */
//    private void emailReport(){
//
//        if(result.getText().equals("000")){
//            Toast toast = Toast.makeText(this, "Perform a calculation first", Toast.LENGTH_SHORT);
//            toast.show();
//            return;
//        }
//
//        Intent intent = new Intent(Intent.ACTION_SEND);								// Sets the intent to be an email intent
//        intent.setType("plain/text");												// I don't know what this does but it's necessary
//        intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "" });					// The email address to send to. We don't know who the user will want to send it to.
//        intent.putExtra(Intent.EXTRA_SUBJECT, "Legume Credit Report");	    // The subject line
//
//        // Build the body text string
//        String bodyText = "";
//        bodyText += "Forage Species: " + species.getText() + "\nSoil Type: " + soilType.getText() + "\n";
//        bodyText += "Amount of growth: " + regrowth.getText() + "\nStand Density: " + standDensity.getText() + "\nNitrogen Credit: "+ result.getText() +" (lb N/acre)²"+"\n\n";
//
//        bodyText += "This email generated by Legume Credit Calculators, an Android app by the University of Wisconsin-Madison's NPM program\n";
//        bodyText += "http://ipcm.wisc.edu/apps/";
//
//        intent.putExtra(Intent.EXTRA_TEXT, bodyText);			// The body text
//        startActivity(Intent.createChooser(intent, ""));		// Starts the email activity, passing the given data with it
//
//    }
//    private void helpinfo(String curURL) {
//
//        AlertDialog.Builder alert = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AppTheme));
//        alert.setTitle("Help Info");
//
//        WebView wv = new WebView(this);
//        wv.getSettings().setJavaScriptEnabled(true);
//        wv.loadUrl(curURL);
//        wv.setWebViewClient(new WebViewClient() {
//
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                //view.getSettings().setJavaScriptEnabled(true);
//                view.loadUrl(url);
//
//                return true;
//            }
//        });
//
//        alert.setNegativeButton("Close", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int id) {
//            }
//        });
//        Dialog d = alert.setView(wv).create();
//        d.show();
//        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//        lp.copyFrom(d.getWindow().getAttributes());
//
//        //lp.width = WindowManager.LayoutParams.FILL_PARENT;
//        //lp.height = WindowManager.LayoutParams.FILL_PARENT;
//        d.getWindow().setAttributes(lp);
//
//
//
//    }
}



