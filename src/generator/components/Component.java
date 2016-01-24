package generator.components;

import javax.swing.event.ChangeListener;

public interface Component {

	void addChangeListener(ChangeListener changeListener);

	boolean isListeningEnabled();

	boolean isModified();

	boolean isSilent();

	void setModified(boolean modified);

	void setSilent(boolean silent);

	void setValue(double value);

	double value();
	
	void setDisplayChange(boolean value);
}
