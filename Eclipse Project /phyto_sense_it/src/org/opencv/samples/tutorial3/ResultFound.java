package org.opencv.samples.tutorial3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ResultFound extends Activity {
	
	public TextView textViewDiseasedName; //declare a TextView to display the diseased name
	public TextView textViewInfo; //Declare a TextView for the information on the disease
	public ImageView diseadeImageView;
	public Button mTryAgain;
	public int diseaseNumber;
	public TextView linkView;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.result_found);

	    
	    //TODO get the disease number from previews activity
	    diseaseNumber = 3;
	    
	    //Linking the textView and the ImageView from the layout by id
	    textViewDiseasedName = (TextView) findViewById(R.id.disease_name);
	    textViewInfo = (TextView) findViewById(R.id.disease_info);
	    diseadeImageView = (ImageView) findViewById(R.id.disease_image_view);
	    linkView = (TextView) findViewById(R.id.link_view);
	    
	    if(diseaseNumber == 1){
	    	
	    	 //set the textView and ImageView with the text from the string.xml
		    textViewDiseasedName.setText(R.string.disease1);
		    textViewInfo.setText(R.string.disease_information1);
		    
		    //change the image to disease1
		    diseadeImageView.setImageResource(R.drawable.tomato_bacterial_spot_13);
		    
		    //set the link to disease 1
		    linkView.setClickable(true);
		    linkView.setMovementMethod(LinkMovementMethod.getInstance());
		    String text = "<a href='http://www.google.com'> Link To Disease1 </a>";
		    linkView.setText(Html.fromHtml(text));
	    }
	    else if (diseaseNumber == 2){
	    	 //set the textView and ImageView with the text from the string.xml
		    textViewDiseasedName.setText(R.string.disease2);
		    textViewInfo.setText(R.string.disease_information2);
		    
		    //change the image to disease1
		    diseadeImageView.setImageResource(R.drawable.tomato_bacterial_spot_13);
		    
		  //set the link to disease 1
		    linkView.setClickable(true);
		    linkView.setMovementMethod(LinkMovementMethod.getInstance());
		    String text = "<a href='http://www.google.com'> Link To Disease2 </a>";
		    linkView.setText(Html.fromHtml(text));
		    
	    }
	    
	    else{
	    	 //set the textView and ImageView with the text from the string.xml
		    textViewDiseasedName.setText(R.string.disease3);
		    textViewInfo.setText(R.string.disease_information3);
		    
		    //change the image to disease1
		    diseadeImageView.setImageResource(R.drawable.tomato_bacterial_spot_13);
		    
		  //set the link to disease 1
		    linkView.setClickable(true);
		    linkView.setMovementMethod(LinkMovementMethod.getInstance());
		    String text = "<a href='http://www.google.com'> Link To Disease3 </a>";
		    linkView.setText(Html.fromHtml(text));
		    
	    }
	   
	    
	    mTryAgain = (Button) findViewById(R.id.again);
	    mTryAgain.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResultFound.this, HomeActivity.class));
            }    
        });
	    
	    
	    
	}

}
