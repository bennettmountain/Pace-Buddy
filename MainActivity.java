package bmountain.hmc.edu.firsty;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button calculatePaceButton = (Button) findViewById(R.id.calculatePaceButton);
        calculatePaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //instantiate everything
                EditText distanceEditText = findViewById(R.id.distanceEditText);
                EditText hoursEditText = findViewById(R.id.hoursEditText);
                EditText minutesEditText = findViewById(R.id.minutesEditText);
                EditText secondsEditText = findViewById(R.id.secondsEditText);
                TextView paceTextView = findViewById(R.id.paceTextView);

                //make all the inputs into their respective data types
                double distanceDouble = Double.parseDouble(distanceEditText.getText().toString());
                int hoursInteger = Integer.parseInt(hoursEditText.getText().toString());
                int minutesInteger = Integer.parseInt(minutesEditText.getText().toString());
                int secondsInteger = Integer.parseInt(secondsEditText.getText().toString());

                //convert the hours and the minutes inputs to seconds and add them to the seconds input
                int totalSeconds = (hoursInteger*3600) + (minutesInteger*60) + secondsInteger;
                // calculate the seconds per mile
                double secondsPerMile = totalSeconds / distanceDouble;
                //calculate the minutes per mile
                double minutesPerMile = secondsPerMile / 60;

                // truncate minutesPerMile to get rid of decimal places
                int minutesPerMileInteger = (int) Math.floor(minutesPerMile);

                // truncate minutesPerMile to get keep the decimals. This is the "minute fraction" of running pace, the seconds portion
                NumberFormat onlyDecimal = new DecimalFormat(".####");
                double minuteFraction = Double.parseDouble(onlyDecimal.format(minutesPerMile));

                //convert the minute fraction into seconds, ie 0.5 minutes --> 30 seconds
                double secondPortionOfPace = minuteFraction * 60;
                //truncate to only one decimal place
                //one decimal place gives the user more accuracy than normal calculators for running pace, but more than one decimal place is unnecessary
                NumberFormat cleanSecondPortion = new DecimalFormat(("##.#"));
                double cleanSecondPortionofPace = Double.parseDouble(cleanSecondPortion.format(secondPortionOfPace));

                //cleanSecondPortionofPace outputs the total seconds per mile, ie 360 seconds = 6 minutes
                //we only want to tell the user the remaining seconds, ie 370 seconds = 6 minutes and 10 seconds
                //actualSecondPortionOfPace gets those remaining seconds
                double actualSecondPortionOfPace = cleanSecondPortionofPace - minutesPerMileInteger * 60;

                //display the output running pace to the user!
                paceTextView.setText(minutesPerMileInteger + getString(R.string.minutes) + actualSecondPortionOfPace + getString(R.string.seconds_per_mile) );
            }
        });
    }
}