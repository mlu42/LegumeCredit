package credit.calc.ipmc.legumecredit;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;
import android.widget.TabHost;


public class Main extends TabActivity {



    // Global variables that can be accessed from anywhere
    public static String applicationRate = "";	// The application rate
    public static String range = "";			// The application rate range
    public static String soilType = "";			// The soil type the user has selected
    public static String prevCrop = "";			// The previous crop the user has selected
    public static String fertPrice = "";		// The fertilizer price per ton
    public static String nPercent = "";			// The nitrogen concentration
    public static String cornPrice = "";		// The corn price per bushel
    public static String nPrice = "";			// The nitrogen price calculated
    public static String ratio = "";			// The N:Corn ratio calculated

    /*
     * Called when the activity is first created
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);


        setContentView(R.layout.activity_main); 			// Sets the layout to draw from main.xml in the res/layout directory

        Resources res = this.getResources(); // Resource object to get Drawables
        TabHost tabHost = getTabHost(); // Gets the TabHost from the TabActivity
        tabHost.setup();				// The activity TabHost
        TabHost.TabSpec spec;  			// Resusable TabSpec for each tab
        Intent intent;  				// Reusable Intent for each tab

        // Create an Intent to launch an Activity for the tab (to be reused)
        intent = new Intent().setClass(this, LegumeCalc.class);							// Sets the intent to launch the Activity defined by Calculator.java

        // Initialize a TabSpec for each tab and add it to the TabHost
        spec = tabHost.newTabSpec("Legume"); 										// Sets the "tag" used to keep track of this tab
        spec.setIndicator("Legume"); 	// Sets the title and icon
        spec.setContent(intent); 														// Sets the action to take when selected
        tabHost.addTab(spec); 															// Adds the tab to the

        // Do the same for the other tabs
        intent = new Intent().setClass(this, ManureCalc.class);
        spec = tabHost.newTabSpec("Manure");
        spec.setIndicator("Manure");
        spec.setContent(intent);
        tabHost.addTab(spec);

//        intent = new Intent().setClass(this, Credits.class);
//        spec = tabHost.newTabSpec("credits");
//        spec.setIndicator("N Credits");
//        spec.setContent(intent);
//        tabHost.addTab(spec);
//
//        intent = new Intent().setClass(this, Email.class);
//
//        spec = tabHost.newTabSpec("email");
//        spec.setIndicator("Email");
//        spec.setContent(intent);
//        tabHost.addTab(spec);



        // Commented out because it was getting cramped and I wanted to try adding this to the context menu
        /*intent = new Intent().setClass(this, Info.class);
        spec = tabHost.newTabSpec("info");
        spec.setIndicator("Info", res.getDrawable(R.drawable.info_icon));
        spec.setContent(intent);
        tabHost.addTab(spec);*/

        tabHost.setCurrentTab(0); // Sets the tab view to be focused on the calculator activity initially



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
//                //emailReport();
//            return true;
//            case R.id.action_settings2:
//
//                //helpinfo("file:///android_asset/legume_help.html");
//
//             return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//        return true;
//
////        return super.onOptionsItemSelected(item);
//    }



}
