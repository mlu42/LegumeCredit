package credit.calc.ipmc.legumecredit;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * Created by mo on 8/5/14.
 * Perform the Manure Calculator Activity
 */
public class ManureCalc extends Activity {
    private ScrollView mainLayout;

    private TextView manure_type1;
    private TextView manure_type2;
    private TextView incorpTime_title;
    private TextView manure_source_title;
    private TextView incorpTime1;
    private TextView incorpTime2;
    private TextView incorpTime3;
    private TextView analysis1;
    private TextView analysis2;
    private TwoStateToggle manureType;
    private ThreeStateToggle incorpTime;
    private TwoStateToggle analysisOption;

    private TextView inputN_title;
    private EditText inputN;
    private TextView inputP205_title;
    private EditText inputP205;
    private TextView inputK2O_title;
    private EditText inputK2O;
    private TextView inputS_title;
    private EditText inputS;



    private TextView decrease;
    private TextView increase;
    private TextView countRes;
    private int incrementor = 1;
    private TwoStateToggle counter;


    private Spinner spinner;

    private TextView resultN;
    private TextView resultP;
    private TextView resultK;
    private TextView resultS;

    private static String urlmanure = "ManureCredits.json";
    private static String urlemail = "EmailInfo.json";


    private String manureSpecies_Tag;
    private String manureType_Tag;
    private String incorptime_Tag;

    final Handler handler = new Handler();

    final int upperbound = 50;
    final int lowerbound = 1;
    final String unitSolid = " ton/acre";
    final String unitLiquid = " gal/acre";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_manure);
//        setContentView(R.layout.activity_main);
//        FrameLayout container = (FrameLayout) findViewById(R.id.container);
//        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View menuLayout = inflater.inflate(R.layout.layout_creditcalc, container, true);


        // Assign all of the instance variables
        mainLayout = (ScrollView) findViewById(R.id.main_parent);

        manure_source_title =  (TextView) findViewById(R.id.manure_item_header);
        incorpTime_title = (TextView) findViewById(R.id.incorp_time_title);

        manure_type1 = (TextView) findViewById(R.id.manure_type1);
        manure_type2 = (TextView) findViewById(R.id.manure_type2);
        incorpTime1 = (TextView) findViewById(R.id.incorp_time1);
        incorpTime2 = (TextView) findViewById(R.id.incorp_time2);
        incorpTime3 = (TextView) findViewById(R.id.incorp_time3);

        analysis1 = (TextView) findViewById(R.id.analysis1);
        analysis2 = (TextView) findViewById(R.id.analysis2);

        inputN_title = (TextView) findViewById(R.id.N_input_title);
        inputN = (EditText) findViewById(R.id.N_input_edit);
        inputP205_title = (TextView) findViewById(R.id.P_input_title);
        inputP205 = (EditText) findViewById(R.id.P_input_edit);
        inputK2O_title = (TextView) findViewById(R.id.K_input_title);
        inputK2O = (EditText) findViewById(R.id.K_input_edit);
        inputS_title = (TextView) findViewById(R.id.S_input_title);
        inputS = (EditText) findViewById(R.id.S_input_edit);




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
        analysisOption = new TwoStateToggle(analysis1, analysis2);


        //read the jason file
        final JSONObject obj = getJSONfile(urlmanure);


        ///////////////Set the spinner adapter ///////////////////
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


        analyzedView(View.GONE);
        nonanalyzedView(View.VISIBLE);





        manure_type1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (manureType.getCurrentState() != 0) {
                    manureType.setState(0);
                    spinner.clearAnimation();
                    spinner.setAdapter(adapter);
                    countRes.setText(changeRateUnit());

                }

                calculate(obj);
            }

        });

        manure_type2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (manureType.getCurrentState() != 1) {
                    manureType.setState(1);
                    spinner.clearAnimation();
                    spinner.setAdapter(adapter2);
                    countRes.setText(changeRateUnit());

                }

                calculate(obj);
            }

        });



        //choose analysis type
        analysis1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (analysisOption.getCurrentState() != 0) {
                    analysisOption.setState(0);
                    analyzedView(View.GONE);
                    nonanalyzedView(View.VISIBLE);
                    cleanResultFields();


                }

                //calculate(obj);
            }


        });

        analysis2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (analysisOption.getCurrentState() != 1) {
                    analysisOption.setState(1);

                    analyzedView(View.VISIBLE);
                    nonanalyzedView(View.GONE);
                    cleanResultFields();

                }

                //calculate(obj);
            }


        });



        inputN.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {
                calculateNonAnalyzedResult();
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });
        inputK2O.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {
                calculateNonAnalyzedResult();
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });
        inputP205.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {
                calculateNonAnalyzedResult();
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });
        inputS.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {
                calculateNonAnalyzedResult();
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });


        incorpTime1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (incorpTime.getCurrentState() != 0) {
                    incorpTime.setState(0);

                }
                calculate(obj);

            }
        });

        incorpTime2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (incorpTime.getCurrentState() != 1) {
                    incorpTime.setState(1);
                }
                calculate(obj);

            }
        });

        incorpTime3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (incorpTime.getCurrentState() != 2) {
                    incorpTime.setState(2);
                }
                calculate(obj);

            }
        });


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                calculate(obj);

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
                if (incrementor > lowerbound) {
                    incrementor--;

                    countRes.setText(changeRateUnit());

                }


                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        counter.reSetState();
                    }
                }, 200);
                if(analysisOption.getCurrentState()==1) {
                    calculate(obj);
                }else{
                    calculateNonAnalyzedResult();
                }

            }
        });

        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                counter.setState(1);
                if (incrementor < upperbound) {
                    incrementor++;

                    countRes.setText(changeRateUnit());
                }


                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        counter.reSetState();
                    }
                }, 200);

                if(analysisOption.getCurrentState()==1) {
                    calculate(obj);
                }else{
                    calculateNonAnalyzedResult();
                }


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
                    handler.postAtTime(mUpdateTaskdown, SystemClock.uptimeMillis() + 100);
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


    private void cleanResultFields(){
        resultN.setText("00");
        resultP.setText("00");
        resultK.setText("00");
        resultS.setText("00");
    }

    private void analyzedView(int v){
        incorpTime_title.setVisibility(v);
        incorpTime1.setVisibility(v);
        incorpTime2.setVisibility(v);
        incorpTime3.setVisibility(v);

        manure_source_title.setVisibility(v);
        spinner.setVisibility(v);




    }

    private void nonanalyzedView(int v){

        inputN_title.setVisibility(v);
        inputN.setVisibility(v);
        inputP205_title.setVisibility(v);
        inputP205.setVisibility(v);
        inputK2O_title.setVisibility(v);
        inputK2O.setVisibility(v);
        inputS_title.setVisibility(v);
        inputS.setVisibility(v);
    }



    /*
    * accelerate the increment of decrement
    * */
    private Runnable mUpdateTaskup = new Runnable() {
        public void run() {
            if (incrementor < upperbound) {
                incrementor++;
                String temp;

                countRes.setText(String.valueOf(changeRateUnit()));
                Log.i("repeatBtn", "repeat click");
            }
            handler.postAtTime(this, SystemClock.uptimeMillis() + 100);

        }//end run
    };// end runnable

    /*
    * accelerate the increment of decrement
    * */
    private Runnable mUpdateTaskdown = new Runnable() {
        public void run() {
            if (incrementor > lowerbound) {
                incrementor--;


                countRes.setText(String.valueOf(changeRateUnit()));
                Log.i("repeatBtn", "repeat click");
            }
            handler.postAtTime(this, SystemClock.uptimeMillis() + 100);

        }//end run
    };// end Runnable


    /*
     * help to change rate unit when appropriate
     */
    private String changeRateUnit() {
        String temp;
        if (manureType.getCurrentState() == 0) {
            temp = Integer.toString(incrementor) + unitSolid;
        } else {
            temp = Integer.toString(incrementor * 1000) + unitLiquid;
        }
        return temp;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



    /*
    * When the activity is set aside, store the data in an internal file
    */

    @Override
    protected void onPause() {

        try {
            // Creating JSONObject from String

            JSONObject aJson = new JSONObject();
            aJson.put("type", manureType_Tag);
            aJson.put("time", incorptime_Tag);
            aJson.put("source", manureSpecies_Tag);
            aJson.put("count", incrementor);
            aJson.put("MCreditN", resultN.getText());
            aJson.put("MCreditP", resultP.getText());
            aJson.put("MCreditK", resultK.getText());
            //aJson.put("MCreditS", resultS.getText());

            //JSONObject jManure = new JSONObject();
            //jManure.put("Manure", aJson);


            String FILENAME = "EmailManure";
            String string = aJson.toString();

            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(string.getBytes());
            fos.close();

            Log.v("checkM", aJson.toString());


        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Calls the parent onPause() method
        super.onPause();


    }


    /*
    * call when need to calculate the result.
    */
    public void calculate(JSONObject obj) {

        if (manureType.getCurrentState() > -1 && incorpTime.getCurrentState() > -1) {
            switch (manureType.getCurrentState()) {
                case 0:
                    manureType_Tag = "Solid";
                    break;
                case 1:
                    manureType_Tag = "Liquid";
                    break;
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
            parseJASON(obj);

        }


    }


    /*
    * get the jason file
    * */
    private JSONObject getJSONfile(String url) {

        JSONObject jObj = null;
        String myjsonstring = "";

        StringBuffer sb = new StringBuffer();
        BufferedReader br = null;

        try {
            br = new BufferedReader(new InputStreamReader(getAssets().open(url)));
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

    /*
    * parse the jason object into text and set them into the text view
    * */
    private void parseJASON(JSONObject jObj) {

        String aJasonrsltN = "";
        String aJasonrsltP = "";
        String aJasonrsltK = "";
        String aJasonrsltS = "";

        int rsltN;
        int rsltP;
        int rsltK;
        int rsltS;

        int tempincr = incrementor;


        try {
            // Creating JSONObject from String
            JSONObject aJsonManure = jObj.getJSONObject(manureType_Tag);

            JSONObject aJasonAnimal = aJsonManure.getJSONObject(manureSpecies_Tag);


            JSONObject aJasonN = aJasonAnimal.getJSONObject("N");

            aJasonrsltN = aJasonN.getString(incorptime_Tag);

            aJasonrsltK = aJasonAnimal.getString("K");
            aJasonrsltP = aJasonAnimal.getString("P");
            aJasonrsltS = aJasonAnimal.getString("S");


            rsltN = Integer.parseInt(aJasonrsltN) * tempincr;
            rsltP = Integer.parseInt(aJasonrsltP) * tempincr;
            rsltK = Integer.parseInt(aJasonrsltK) * tempincr;
            rsltS = Integer.parseInt(aJasonrsltS) * tempincr;

            adjustTextSize(rsltN, resultN);
            adjustTextSize(rsltK, resultK);
            adjustTextSize(rsltP, resultP);
            adjustTextSize(rsltS, resultS);

            resultN.setText(Integer.toString(rsltN));
            resultP.setText(Integer.toString(rsltP));
            resultK.setText(Integer.toString(rsltK));
            resultS.setText(Integer.toString(rsltS));


        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }


    private boolean calculateNonAnalyzedResult(){

        if(!isEmpty(inputN) && !isEmpty(inputP205) && !isEmpty(inputK2O) && !isEmpty(inputS)){
            //Log.v("EditText", " in the loop");
            int rsltN = Integer.parseInt(inputN.getText().toString()) * incrementor;
            int rsltP = Integer.parseInt(inputP205.getText().toString()) * incrementor;
            int rsltK = Integer.parseInt(inputK2O.getText().toString()) * incrementor;
            int rsltS = Integer.parseInt(inputS.getText().toString()) * incrementor;


            adjustTextSize(rsltN, resultN);
            adjustTextSize(rsltK, resultK);
            adjustTextSize(rsltP, resultP);
            adjustTextSize(rsltS, resultS);



            resultN.setText(Integer.toString(rsltN));
            resultP.setText(Integer.toString(rsltP));
            resultK.setText(Integer.toString(rsltK));
            resultS.setText(Integer.toString(rsltS));

            return true;
        }

        //Log.v("EditText", "Did not end in the loop");
        return false;
    }

    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0) {
            return false;
        } else {
            return true;
        }
    }
    private void adjustTextSize(int n, TextView v){
        if(n<100){
            v.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 50);
        }else {
            v.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 35);
        }

    }





}



