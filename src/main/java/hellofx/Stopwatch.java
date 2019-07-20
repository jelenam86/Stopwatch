package hellofx;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Stopwatch {

	@FXML
	private Button startButton;

	@FXML
	private Button stopButton;

	@FXML
	private Button resetButton;

	@FXML
	private Label days;

	@FXML
	private Label display;

	@FXML
	private Label milliseconds;

	@FXML
	private ImageView seconds;

	@FXML
	private ImageView minutes;

	@FXML
	private ImageView hours;

	private Timeline timelineSecond, timelineMinute, timelineHour, timeline;
	private int millisecond = 0;
	private int secondsElapsed = 0;
	private int minutesElapsed = 0;
	private int hoursElapsed = 0;
	private int daysPassed = 0;

	@FXML
	void resetPressed(ActionEvent event) {
		timelineHour.stop();
		timelineMinute.stop();
		timelineSecond.stop();
		timeline.stop();
		seconds.setRotate(0);
		minutes.setRotate(0);
		hours.setRotate(0);
		millisecond = secondsElapsed = minutesElapsed = hoursElapsed = daysPassed = 0;
		milliseconds.setText(": 000");
		display.setText("00 : 00 : 00");
		days.setText("");
		startButton.setDisable(false);
	}

	@FXML
	void startPressed(ActionEvent event) {

		updateDigits();
		timelineSecond = new Timeline(new KeyFrame(Duration.millis(1000), (ActionEvent ae) -> {
			seconds.setRotate(secondsElapsed += 6);
		}));
		timelineSecond.setCycleCount(Animation.INDEFINITE);
		timelineSecond.play();
		timelineMinute = new Timeline(new KeyFrame(Duration.seconds(60), (ActionEvent ae) -> {
			minutes.setRotate(minutesElapsed += 6);
		}));
		timelineMinute.setCycleCount(Animation.INDEFINITE);
		timelineMinute.play();
		timelineHour = new Timeline(new KeyFrame(Duration.minutes(60), (ActionEvent ae) -> {
			hours.setRotate(hoursElapsed += 30);
		}));
		timelineHour.setCycleCount(Animation.INDEFINITE);
		timelineHour.play();
		startButton.setDisable(true);
	}

	@FXML
	void stopPressed(ActionEvent event) {

		timelineHour.pause();
		timelineMinute.pause();
		timelineSecond.pause();
		timeline.pause();
	}

	private void updateDigits() {

		String[] partsOfTime = display.getText().split(" : ");

		timeline = new Timeline(new KeyFrame(Duration.millis(1), (ActionEvent ae) -> {
			millisecond++;
			milliseconds.setText(String.format(": %03d", Integer.parseInt(milliseconds.getText().substring(2)) + 1));
			if (millisecond == 1000) {
				milliseconds.setText(": 000");
				millisecond = 0;
				partsOfTime[2] = String.format("%02d", Integer.parseInt(partsOfTime[2]) + 1);
				if (Integer.parseInt(partsOfTime[2]) == 60) {
					partsOfTime[2] = "00";
					partsOfTime[1] = String.format("%02d", Integer.parseInt(partsOfTime[1]) + 1);
					if (Integer.parseInt(partsOfTime[1]) == 60) {
						partsOfTime[1] = "00";
						partsOfTime[0] = String.format("%02d", Integer.parseInt(partsOfTime[0]) + 1);
						if (Integer.parseInt(partsOfTime[0]) == 24) {
							partsOfTime[0] = "00";
							daysPassed++;
							days.setText("Days passed: " + daysPassed);
						}
					}
				}
				display.setText(String.format("%s : %s : %s", partsOfTime[0], partsOfTime[1], partsOfTime[2]));
			}
		}));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
	}
}
