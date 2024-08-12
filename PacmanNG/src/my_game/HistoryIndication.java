package my_game;

import java.awt.Color;

import base.Game;
import base.GameCanvas;
import base.ShapeListener;
import my_base.MyContent;
import shapes.Image;
import shapes.Text;

public class HistoryIndication implements ShapeListener {

	public enum HistoryState {
		Recording ("resources/recording.jpg"),
		Stop ("resources/stop.jpg"),
		Play ("resources/play.jpg"),
		Replaying("resources/replaying.jpg");

		private String filename;

		private HistoryState(String filename) {
			this.filename = filename;
		}

		public String filename() {
			return this.filename;
		}
	}
	
	private MyContent content;
	private HistoryState historyState;
	private final String imageID = "historyIndication";
	private final String tooltipID = "indicationTooltip";
	private final int width = 48, height = 48;
	private Text tooltip;
	
	public HistoryIndication(MyContent content) {
		this.content = content;
		historyState = HistoryState.Recording;
	}	

	public void setHistoryState(HistoryState historyState) {
		this.historyState = historyState;
		Game.UI().canvas().changeImage(imageID, historyState.filename(), width, height);
		tooltip.setText(String.valueOf(historyState));
	}

	public HistoryState getHistoryState() {
		return historyState;
	}

	public void addToCanvas() {
		GameCanvas canvas = Game.UI().canvas();
		Image image = new Image(imageID, historyState.filename(), width,height, 200, 730);
		image.setShapeListener(this);
		image.setzOrder(3);
		canvas.addShape(image);
		tooltip = new Text(tooltipID, String.valueOf(historyState),250,735);
		tooltip.setColor(Color.white);
		tooltip.setFontSize(18);
		canvas.addShape(tooltip);
	}

	@Override
	public void shapeMoved(String shapeID, int dx, int dy) {
		//Do nothing
	}

	@Override
	public void shapeStartDrag(String shapeID) {
		// Auto-generated method stub

	}

	@Override
	public void shapeEndDrag(String shapeID) {
		// Auto-generated method stub

	}

	@Override
	public void shapeClicked(String shapeID, int x, int y) {
		switch (historyState) {
			case Recording:
				content.historyRecorder().stopRecording();
				setHistoryState(HistoryState.Play);
				break;
			case Play:
				content.historyPlayer().startPlaying();
				setHistoryState(HistoryState.Replaying);
				break;
			case Replaying:
				content.historyPlayer().stopPlaying();
				setHistoryState(HistoryState.Play);
				break;
			default:
			};

	}

	@Override
	public void shapeRightClicked(String shapeID, int x, int y) {
		
	}

	@Override
	public void mouseEnterShape(String shapeID, int x, int y) {
		if (historyState == HistoryState.Recording || historyState == HistoryState.Replaying) {
			Game.UI().canvas().changeImage(imageID, HistoryState.Stop.filename(), width, height);
			tooltip.setText(String.valueOf(HistoryState.Stop));
		}

	}

	@Override
	public void mouseExitShape(String shapeID, int x, int y) {
		if (historyState == HistoryState.Recording || historyState == HistoryState.Replaying) {
			Game.UI().canvas().changeImage(imageID, historyState.filename(), width, height);
			tooltip.setText(String.valueOf(historyState));
		}
	}
	
}
