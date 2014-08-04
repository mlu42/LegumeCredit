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

public class ThreeStateToggle {
	
	// Instance Variables
	private TextView left;		// The first TextView 
	private TextView middle;	// The middle TextView
	private TextView right;		// The last TextView

	private TextView info;	// The header for the previous crop toggle
	private boolean leftSet;	// True if the left switch is set
	private boolean middleSet;	// True if the middle switch is set
	private boolean rightSet;	// True if the right switch is set
	private int leftImage;		// The drawable for the left switch
	private int middleImage;	// The drawable for the middle switch
	private int rightImage;		// The drawable for the right switch
	private int set;			// An int (0,1,2) that tells which switch is currently set
    private int unselectedColor;
    private int selectedColor;

    // The empty constructor
    public ThreeStateToggle()
    {
        left = null;
        middle = null;
        right = null;
        leftSet = false;
        middleSet = false;
        rightSet = false;
        leftImage = -1;
        middleImage = -1;
        rightImage = -1;
        set = -1;
    }

    // Takes three TextView arguments and sets the left switch to
    // be on by default.
    public ThreeStateToggle(TextView a, TextView b, TextView c)
    {
        left = a;
        middle = b;
        right = c;

        leftSet = false;
        middleSet = false;
        rightSet = false;
        leftImage = R.drawable.toggle_left_button_unselected;
        middleImage = R.drawable.toggle_middle_button_unselected;
        rightImage = R.drawable.toggle_right_button_unselected;

        left.setBackgroundResource(leftImage);
        middle.setBackgroundResource(middleImage);
        right.setBackgroundResource(rightImage);




        unselectedColor=Color.parseColor("#198c19");
        selectedColor = Color.parseColor("#7fbf7f");


        set = -1;
    }
	
	// Takes three TextView arguments and an int argument which indicates
	// which switch should be set (0, 1, 2 for left, middle, right)
	public ThreeStateToggle(TextView a, TextView b, TextView c, int set)
	{
		left = a;
		middle = b;
		right = c;
        set = -1;
		
		// Decide which switch to set and which image to use
		switch(set)
		{
			case 0:	leftSet = true;
					middleSet = false;
					rightSet = false;
					leftImage = R.drawable.toggle_left_button_unselected;
					left.setBackgroundResource(leftImage);
					middleImage = R.drawable.toggle_middle_button_unselected;
					middle.setBackgroundResource(middleImage);
					rightImage = R.drawable.toggle_right_button_unselected;
					right.setBackgroundResource(rightImage);
					//info.setText("70%-100% forage species, >4 plants/ft\\u221a");
					//set = 0;
					break;
					
			case 1: leftSet = false;
					middleSet = true;
					rightSet = false;
					leftImage = R.drawable.toggle_left_button_unselected;
					left.setBackgroundResource(leftImage);
					middleImage = R.drawable.toggle_middle_button_unselected;
					middle.setBackgroundResource(middleImage);
					rightImage = R.drawable.toggle_right_button_unselected;
					right.setBackgroundResource(rightImage);
					//info.setText("30%-70% forage species, >1.5-4 plants/ft\\u221a");
					//set = 1;
					break;
					
			case 2: leftSet = false;
					middleSet = false;
					rightSet = true;
					leftImage = R.drawable.toggle_left_button_unselected;
					left.setBackgroundResource(leftImage);
					middleImage = R.drawable.toggle_middle_button_unselected;
					middle.setBackgroundResource(middleImage);
					rightImage = R.drawable.toggle_right_button_unselected;
					right.setBackgroundResource(rightImage);

					//info.setText("0%-30% forage species, <1.5 plants/ft\\u221a");
					//set = 2;
					break;

		}
		
	}
	
	// Setters
	public void setLeftView(TextView a)
	{
		left = a;
	}
	
	public void setMiddleView(TextView a)
	{
		middle = a;
	}
	
	public void setRightView(TextView a)
	{
		right = a;
	}
	
	public void setLeft(boolean a)
	{
		leftSet = a;
	}
	
	public void setMiddle(boolean a)
	{
		middleSet = a;
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
	
	public void setMiddleImage(int a)
	{
		middleImage = a;
		middle.setBackgroundResource(middleImage);
	}

	public void setRightImage(int a)
	{
		rightImage = a;
		right.setBackgroundResource(rightImage);
	}
	
	/*
	 * Performs all the operations (drawing views, changing colors,
	 * changing instance variables) associated with changing the
	 * selection of the ThreeStateToggle object
	 * 
	 * @param the new state of the toggle switch
	 */
	public void setState(int a)
	{
		switch(a)
		{
			case 0:	leftSet = true;
					middleSet = false;
					rightSet = false;
					setLeftImage(R.drawable.toggle_left_button_selected);
					setMiddleImage(R.drawable.toggle_middle_button_unselected);
					setRightImage(R.drawable.toggle_right_button_unselected);

                    left.setTextColor(selectedColor);
					middle.setTextColor(unselectedColor);
					right.setTextColor(unselectedColor);


                    //info.setText("70%-100% forage species, >4 plants/ft\\u221a");

					set = 0;					
					break;
			
			case 1:	leftSet = false;
					middleSet = true;
					rightSet = false;
					setLeftImage(R.drawable.toggle_left_button_unselected);
					setMiddleImage(R.drawable.toggle_middle_button_selected);
					setRightImage(R.drawable.toggle_right_button_unselected);
					left.setTextColor(unselectedColor);
					middle.setTextColor(selectedColor);
					right.setTextColor(unselectedColor);
                    //info.setText("70%-100% forage species, >4 plants/ft\\u221a");


					set = 1;
					break;
			
			case 2:	leftSet = false;
					middleSet = false;
					rightSet = true;
					setLeftImage(R.drawable.toggle_left_button_unselected);
					setMiddleImage(R.drawable.toggle_middle_button_unselected);
					setRightImage(R.drawable.toggle_right_button_selected);
					left.setTextColor(unselectedColor);
					middle.setTextColor(unselectedColor);
					right.setTextColor(selectedColor);
                    //info.setText("70%-100% forage species, >4 plants/ft\\u221a");


					set = 2;
					break;
		}
	}
	
	// Getters
	public TextView getLeftView()
	{
		return left;
	}
	
	public TextView getMiddleView()
	{
		return middle;
	}
	
	public TextView getRightView()
	{
		return right;
	}
	
	public boolean isLeftSet()
	{
		return leftSet;
	}
	
	public boolean isMiddleSet()
	{
		return middleSet;
	}
	
	public boolean isRightSet()
	{
		return rightSet;
	}
	
	public int getLeftImage()
	{
		return leftImage;
	}
	
	public int getMiddleImage()
	{
		return middleImage;
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
	 * A slightly redundant method (see setState(int)) that draws the views in
	 * accordance with a given state specified by the parameter sent
	 */
	public void drawViews(Resources res, int a)
	{
		set = a;
		
		switch(set)
		{
			case 0:	//left.setTextColor(res.getColor(R.color.toggle_background_unselected));
					//middle.setTextColor(res.getColor(R.color.toggle_background_selected));
					//right.setTextColor(res.getColor(R.color.toggle_background_selected));
					setLeftImage(R.drawable.toggle_left_button_selected);
					setMiddleImage(R.drawable.toggle_middle_button_unselected);
					setRightImage(R.drawable.toggle_right_button_unselected);



					break;
					
			case 1:	//left.setTextColor(res.getColor(R.color.toggle_background_selected));
					//middle.setTextColor(res.getColor(R.color.toggle_background_unselected));
					//right.setTextColor(res.getColor(R.color.toggle_background_selected));
					setLeftImage(R.drawable.toggle_left_button_unselected);
					setMiddleImage(R.drawable.toggle_middle_button_selected);
					setRightImage(R.drawable.toggle_right_button_unselected);

					break;
			
			case 2:	//left.setTextColor(res.getColor(R.color.toggle_background_selected));
					//middle.setTextColor(res.getColor(R.color.toggle_background_selected));
					//right.setTextColor(res.getColor(R.color.toggle_background_unselected));
					setLeftImage(R.drawable.toggle_left_button_unselected);
					setMiddleImage(R.drawable.toggle_middle_button_unselected);
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
                returnValue = middle.getText().toString();
            break;
            case 2:
                returnValue = right.getText().toString();
            break;
        }
        return returnValue;

    }
}
