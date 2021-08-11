package elementosSwing;

import java.awt.Container;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class JPanelBoxFactory extends JPanel {
	
	public JPanelBoxFactory(Boolean vertical) {
		super();
		this.setLayout(new BoxLayout(this, (vertical)? BoxLayout.PAGE_AXIS : BoxLayout.LINE_AXIS));
	}
	
}
 