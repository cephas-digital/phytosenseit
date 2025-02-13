package org.opencv.samples.tutorial3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class NoResultFound extends Activity {
	
	private Button mTryAgainButton;
	public ImageView diseadeImageView;
	@Override
	protected void onCreate(final Bundle savedInstanceSate)
	{
		super.onCreate(savedInstanceSate);
		setContentView(R.layout.noresults);
		diseadeImageView = (ImageView) findViewById(R.id.imageView1);
		 mTryAgainButton = (Button) findViewById(R.id.button);
		 //set stock image
		 // change 'icon' to generic image
		 diseadeImageView.setImageResource(R.drawable.icon);
		 mTryAgainButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				   // Context context = getApplicationContext();
				startActivity(new Intent(NoResultFound.this, ResultFound.class));
			
			}
		});
	}
	
}
