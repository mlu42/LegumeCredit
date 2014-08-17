package credit.calc.ipmc.legumecredit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Locale;

public class Email extends Activity{

	private EditText subjectInput;
	private EditText textInput;
	
	@Override
	protected void onResume()
	{
		subjectInput.setText(getSubjectLine());
	    textInput.setText(getEmailText());
	    
	    super.onResume();
	}
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.email);		// Loads the layout from email.xml
        
        /*
         * Layout code
         */
        
        // The wrapper  layout
        LinearLayout emailLayout = (LinearLayout) findViewById(R.id.email_layout);
        
        // Gets the current width and height of the display to use for layout
        Display display = getWindowManager().getDefaultDisplay();
        final int width = display.getWidth();
        final int height = display.getHeight();
        
        // Sets the margins of the  main layout. Creates space around the white box.
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.topMargin = width/32;
	    params.leftMargin = width/32;
	    params.rightMargin = width/32;
	    params.bottomMargin = width/32;
	    
	    emailLayout.setLayoutParams(params);
	    emailLayout.setPadding(width/16, width/16, width/16, width/16);			// Sets padding on the inside of the white box
	    
	    // The linear layout for the subject line (TextView and EditText)
	    LinearLayout subjectLine = (LinearLayout) findViewById(R.id.subject_line_layout);
	    
	    // Gives the subject row space on the bottom
	    params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
	    params.bottomMargin = width/32;
	    
	    subjectLine.setLayoutParams(params);
	    
	    // The TextView that says "Subject:"
	    TextView subjectHeader = (TextView) findViewById(R.id.subject_header);
	    
	    subjectHeader.setPadding(0, 0, width/32, 0);		// Gives the view padding on the right
	    
	    // The input for the subject line
	    subjectInput = (EditText) findViewById(R.id.subject_input);
	    
	    // The input for the body of the email
	    textInput = (EditText) findViewById(R.id.text_input);
	    
	    params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
	    
	    textInput.setLayoutParams(params);
	    
	    // Sets the text of the inputs
	    subjectInput.setText(getSubjectLine());
	    textInput.setText(getEmailText());
	    
	    textInput.setScroller(new Scroller(this)); 
	    //textInput.setMaxLines(1); 
	    textInput.setVerticalScrollBarEnabled(true); 
	    
	    // The "OK" button
	    Button sendButton = (Button) findViewById(R.id.send_button);
	    
	    // Set padding/margins
	    params = new LinearLayout.LayoutParams(width/4, ViewGroup.LayoutParams.WRAP_CONTENT);
	    params.bottomMargin = width/24;
	    
	    sendButton.setLayoutParams(params);
	    
	    // Sets the action to take the the "OK" button is clicked
	    sendButton.setOnClickListener(new OnClickListener(){
	    	
	    	public void onClick(View v)
	    	{
	    		Intent intent = new Intent(Intent.ACTION_SEND);								// Sets the intent to be an email intent
	    		intent.setType("plain/text");												// I don't know what this does but it's necessary
	    		intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "" });					// The email address to send to. We don't know who the user will want to send it to.
	    		intent.putExtra(Intent.EXTRA_SUBJECT, subjectInput.getText().toString());	// The subject line
	    		intent.putExtra(Intent.EXTRA_TEXT, textInput.getText().toString());			// The body text
	    		startActivity(Intent.createChooser(intent, ""));							// Starts the email activity, passing the given data with it
	    	}
	    	
	    });
	    
	    
    }
    
    /*
     * Gets the subject line of the email, which consists of getting the date and time
     */
    private String getSubjectLine()
    {
    	Calendar cal = Calendar.getInstance(Locale.getDefault());
    	
    	int month = cal.get(Calendar.MONTH);
    	
    	String day = month + 1 + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.YEAR);
    	
    	return "MRTN Report: " + day;
    }
    
    /*
     * Gets the text of the email using the global variables defined in Main.java
     */
    private String getEmailText()
    {
    	return 	"N application rate: " + Main.applicationRate + " lbs N/acre\n" +
    			"MRTN range: " + Main.range + "lbs N/acre\n\n" +
    			"Soil type: " + Main.soilType + "\n" +
    			"Previous crop: " + Main.prevCrop + "\n\n" +
    			"Fertilizer price: $" + Main.fertPrice + "/ton\n" +
    			"Percent Nitrogen: " + Main.nPercent + "%\n" +
    			"Corn price: $" + Main.cornPrice + "/bushel\n\n" + 
    			"Nitrogren price: " + Main.nPrice + "/lb\n" + 
    			"N:Corn ratio: " + Main.ratio + "\n\n" +
    			"This email was generated by the Corn N Rate Calculator app.\n" +
    			"ipcm.wisc.edu/apps/";    			
    }
    
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        View view = getCurrentFocus();
        boolean ret = super.dispatchTouchEvent(event);

        if (view instanceof EditText) {
            View w = getCurrentFocus();
            int scrcoords[] = new int[2];
            w.getLocationOnScreen(scrcoords);
            float x = event.getRawX() + w.getLeft() - scrcoords[0];
            float y = event.getRawY() + w.getTop() - scrcoords[1];
            
            if (event.getAction() == MotionEvent.ACTION_UP 
            			&& (x < w.getLeft() || x >= w.getRight() 
            			|| y < w.getTop() || y > w.getBottom()) ) { 
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
            }
        }
     return ret;
    }
}
