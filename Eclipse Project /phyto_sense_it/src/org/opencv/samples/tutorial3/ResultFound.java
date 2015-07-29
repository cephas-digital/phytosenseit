package org.opencv.samples.tutorial3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ResultFound extends Activity {
	
	public TextView textViewDiseasedName; //creating a TextView to display the diseased name
	public TextView textViewInfo;
	public ImageView diseadeImageView;
	public Button mTryAgain;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.result_found);
	    // TODO Auto-generated method stub
	    
	    //Linking the textView and the ImageView from the layout by id
	    textViewDiseasedName = (TextView) findViewById(R.id.disease_name);
	    textViewInfo = (TextView) findViewById(R.id.disease_info);
	    diseadeImageView = (ImageView) findViewById(R.id.disease_image_view);
	    
	    //set the textView and ImageView with the text from the string.xml
	    textViewDiseasedName.setText(R.string.disease2);
	    textViewInfo.setText(R.string.disease_information1);
	    diseadeImageView.setImageResource(R.drawable.tomato_bacterial_spot_13);
	    
	    mTryAgain = (Button) findViewById(R.id.again);
	    mTryAgain.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResultFound.this, HomeActivity.class));
            }    
        });
	    
	    
	    
	}

}
