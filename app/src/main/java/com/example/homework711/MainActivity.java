package com.example.homework711;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
	private RadioGroup timeRadioGroup;
	private RadioButton autoRadioButton;
	private Button syncButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initViews();

		autoRadioButton.setOnCheckedChangeListener((v, ch) -> autoTune());
		syncButton.setOnClickListener(v -> sync());
		autoTune();
	}

	private void initViews() {
		timeRadioGroup = findViewById(R.id.timeRadioGroup);
		autoRadioButton = findViewById(R.id.autoRadioButton);
		syncButton = findViewById(R.id.syncButton);
	}

	private void autoTune() {
		int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);

		if (hour >= 6 && hour < 14) // 06:00-13:59
			timeRadioGroup.check(R.id.morningRadioButton);
		else if (hour == 14) // 14:00-14:59
			timeRadioGroup.check(R.id.afternoonRadioButton);
		else // 15:00-05:59 the next day
			timeRadioGroup.check(R.id.eveningRadioButton);

		autoRadioButton.setChecked(false);
	}

	private void sync() {
		Intent intent = new Intent(Intent.ACTION_SYNC);
		switch (timeRadioGroup.getCheckedRadioButtonId()) {
			case R.id.morningRadioButton:
				intent.setData(Uri.parse("http://morning"));
				break;
			case R.id.afternoonRadioButton:
				intent.setData(Uri.parse("http://afternoon"));
				break;
			case R.id.eveningRadioButton:
				intent.setData(Uri.parse("http://evening"));
				break;
			default:
				return;
		}
		startActivity(intent);
	}
}
