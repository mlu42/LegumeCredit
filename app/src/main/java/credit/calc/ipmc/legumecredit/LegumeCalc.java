package credit.calc.ipmc.legumecredit;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * Created by mo on 8/5/14.
 */
public class LegumeCalc extends ActionBarActivity{

    // Layout objects
    private ScrollView mainLayout;
    private TextView forageSpecies1;
    private TextView forageSpecies2;
    private TextView soil1;
    private TextView soil2;
    private TextView standDensity1;
    private TextView standDensity2;
    private TextView standDensity3;
    private TextView standDensityInfo;
    private TextView regrowth1;
    private TextView regrowth2;
    private TextView result;
    private TwoStateToggle species;
    private TwoStateToggle soilType;
    private ThreeStateToggle standDensity;
    private TwoStateToggle regrowth;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.layout_creditcalc);
        //setContentView(R.layout.activity_main);

//        FrameLayout container = (FrameLayout) findViewById(R.id.container);
//
//
//        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View menuLayout = inflater.inflate(R.layout.layout_creditcalc, container, true);


        // Assign all of the instance variables
        mainLayout = (ScrollView) findViewById(R.id.main_parent);


        forageSpecies1 = (TextView) findViewById(R.id.forage_species1);
        forageSpecies2 = (TextView) findViewById(R.id.forage_species2);
        soil1 = (TextView) findViewById(R.id.soil_type1);
        soil2 = (TextView) findViewById(R.id.soil_type2);
        standDensity1 = (TextView) findViewById(R.id.stand_density1);
        standDensity2 = (TextView) findViewById(R.id.stand_density2);
        standDensity3 = (TextView) findViewById(R.id.stand_density3);
        standDensityInfo = (TextView) findViewById(R.id.density_info);
        regrowth1 = (TextView) findViewById(R.id.regrowth1);
        regrowth2 = (TextView) findViewById(R.id.regrowth2);
        result = (TextView)findViewById(R.id.output_text);

        species = new TwoStateToggle(forageSpecies1, forageSpecies2);
        soilType = new TwoStateToggle(soil1,soil2);
        regrowth = new TwoStateToggle(regrowth1,regrowth2);
        standDensity = new ThreeStateToggle(standDensity1, standDensity2, standDensity3);




        final LegumeHelper calc = new LegumeHelper(species, soilType, standDensity, regrowth, result);

        forageSpecies1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(species.getCurrentState() != 0)
                {
                    species.setState(0);
                }
                calc.calculate();

            }
        });

        forageSpecies2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(species.getCurrentState() != 1)
                {
                    species.setState(1);

                }
                calc.calculate();

            }
        });

        soil1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(soilType.getCurrentState() != 0)
                {
                    soilType.setState(0);
                }
                calc.calculate();

            }
        });

        soil2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(soilType.getCurrentState() != 1)
                {
                    soilType.setState(1);
                }
                calc.calculate();

            }
        });

        regrowth1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (regrowth.getCurrentState() != 0) {
                    regrowth.setState(0);
                }
                calc.calculate();

            }
        });

        regrowth2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(regrowth.getCurrentState() != 1)
                {
                    regrowth.setState(1);
                }
                calc.calculate();

            }
        });

        standDensity1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(standDensity.getCurrentState() != 0)
                {
                    standDensity.setState(0);
                    standDensityInfo.setText("70%-100% forage species, >4 plants/ft"+"\u00B2");
                }
                calc.calculate();

            }
        });

        standDensity2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(standDensity.getCurrentState() != 1)
                {
                    standDensity.setState(1);
                    standDensityInfo.setText("30%-70% forage species, >1.5-4 plants/ft" +"\u00B2");
                }
                calc.calculate();

            }
        });

        standDensity3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(standDensity.getCurrentState() != 2)
                {
                    standDensity.setState(2);
                    standDensityInfo.setText("0%-30% forage species, <1.5 plants/ft"+"\u00B2");
                }
                calc.calculate();

            }
        });



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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
//
//
//    /*
//    Executed when the email button is clicked
//    */
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
