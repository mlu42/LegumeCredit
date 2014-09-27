package credit.calc.ipmc.legumecredit;

import android.content.res.Resources;
import android.graphics.Color;
import android.widget.TextView;

/*
 * A three state toggle button class for use in the
 * Calculator activity of this app. I will program
 * this class specifically for TextViews as that is
 * what I'll be using it for. If it needs to be
 * programmed more generically, that can be done.
 */

public class TwoStateToggle {
	
	// Instance Variables
	private TextView left;		// The first TextView
	private TextView right;		// The last TextView
	private TextView header;
	private boolean leftSet;	// True if the left switch is set
	private boolean rightSet;	// True if the right switch is set
	private int leftImage;		// The drawable for the left switch
	private int rightImage;		// The drawable for the right switch
	private int set;			// An int (0,1) that indicates which toggle is selected

    private int unselectedColor;
    private int selectedColor;
    // The empty constructor
    public TwoStateToggle()
    {
        left = null;
        right = null;
        leftSet = false;
        rightSet = false;
        leftImage = -1;
        rightImage = -1;
        set = -1;
    }

    // Takes three TextView arguments and sets the left switch to
    // be on by default.
    public TwoStateToggle(TextView a, TextView b)
    {
        left = a;
        right = b;

        leftSet = false;
        rightSet = false;
        leftImage = R.drawable.toggle_left_button_unselected;
        rightImage = R.drawable.toggle_right_button_unselected;
        left.setBackgroundResource(leftImage);
        right.setBackgroundResource(rightImage);

        unselectedColor = Color.parseColor("#007F00");
        selectedColor = Color.parseColor("#C8FFCA");

        set = -1;
    }
	
	// Takes three TextView arguments and an int argument which indicates
	// which switch should be set (0, 1, 2 for left, middle, right)
	public TwoStateToggle(TextView a, TextView b, int set)
	{
		left = a;
		right = b;
		
		// Decide which switch to set and which image to use
		switch(set)
		{
			case 0:	leftSet = true;
					rightSet = false;
					leftImage = R.drawable.toggle_left_button_selected;
					left.setBackgroundResource(leftImage);
					rightImage = R.drawable.toggle_right_button_unselected;
					right.setBackgroundResource(rightImage);
					set = 0;
					break;
					
			case 1: leftSet = false;
					rightSet = true;
					leftImage = R.drawable.toggle_left_button_unselected;
					left.setBackgroundResource(leftImage);
					rightImage = R.drawable.toggle_right_button_selected;
					right.setBackgroundResource(rightImage);
					set = 1;
					break;
		}
		
	}
	
	// Setters
	public void setLeftView(TextView a)
	{
		left = a;
	}
	
	public void setRightView(TextView a)
	{
		right = a;
	}
	
	public void setLeft(boolean a)
	{
		leftSet = a;
	}
	
	public void setRight(boolean a)
	{
		rightSet = a;
	}
	
	public void setLeftImage(int a)
	{
		leftImage = a;
		left.setBackgroundResource(leftImage);
	}

	public void setRightImage(int a)
	{
		rightImage = a;
		right.setBackgroundResource(rightImage);
	}
	
	/*
	 * Changes the state of the toggle switch by changing the colors of the
	 * TextViews and changing local instance variables
	 * 
	 * @param a the index of the toggle that should now be selected (0,1,2)
	 */
	public void setState(int a)
	{
		switch(a)
		{
			case 0:	leftSet = true;
					rightSet = false;
					setLeftImage(R.drawable.toggle_left_button_selected);
					setRightImage(R.drawable.toggle_right_button_unselected);
                    //change text color
                    left.setTextColor(selectedColor);
                    right.setTextColor(unselectedColor);


					set = 0;					
					break;
			
			case 1:	leftSet = false;
					rightSet = true;
					setLeftImage(R.drawable.toggle_left_button_unselected);
					setRightImage(R.drawable.toggle_right_button_selected);
                    //change the text color
                    left.setTextColor(unselectedColor);
                    right.setTextColor(selectedColor);

					set = 1;
					break;
		}
	}

    public void reSetState()
    {
        leftSet = false;
        rightSet = false;
        setLeftImage(R.drawable.toggle_left_button_unselected);
        setRightImage(R.drawable.toggle_right_button_unselected);
        //change text color
        left.setTextColor(unselectedColor);
        right.setTextColor(unselectedColor);

        set = 0;

    }


	// Getters
	public TextView getLeftView()
	{
		return left;
	}
	
	public TextView getRightView()
	{
		return right;
	}
	
	public boolean isLeftSet()
	{
		return leftSet;
	}
	
	public boolean isRightSet()
	{
		return rightSet;
	}
	
	public int getLeftImage()
	{
		return leftImage;
	}
	
	public int getRightImage()
	{
		return rightImage;
	}
	
	public int getCurrentState()
	{
		return set;
	}
	
	// Public methods
	
	/*
	 * A slightly redundanct method (like setState(int) that draws the 
	 * TextViews according to an integer parameter.
	 */
	public void drawViews(Resources res, int a)
	{
		set = a;
		
		switch(set)
		{
			case 0:	setLeftImage(R.drawable.toggle_left_button_selected);
					setRightImage(R.drawable.toggle_right_button_unselected);
					break;
					
			case 1:	setLeftImage(R.drawable.toggle_left_button_unselected);
					setRightImage(R.drawable.toggle_right_button_selected);
					break;
		}
	}

    public String getText(){
        String returnValue = "";

        switch (set){
            case 0:
                returnValue = left.getText().toString();
                break;

            case 1:
                returnValue = right.getText().toString();
                break;
        }
        return returnValue;

    }

}
