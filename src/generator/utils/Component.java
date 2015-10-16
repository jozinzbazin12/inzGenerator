package generator.utils;

import javax.swing.event.ChangeListener;

public interface Component {

	double value();

	void setValue(double value);

	boolean isListeningEnabled();

	boolean isModified();

	boolean isSilent();

	void setModified(boolean modified);

	void setSilent(boolean silent);

	void addChangeListener(ChangeListener changeListener);
}
