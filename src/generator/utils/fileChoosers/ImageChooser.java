package generator.utils.fileChoosers;

import generator.Mediator;
import generator.utils.PropertiesKeys;
import generator.utils.filters.ImageFilter;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

public class ImageChooser extends JFileChooser {

	private static final long serialVersionUID = 309017384708176503L;
	private static final String[] BMP = { "bmp" };
	private static final String[] GIF = { "gif" };
	private static final String[] JPG = { "jpg", "jpeg" };
	private static final String[] PNG = { "png" };
	private static final String[] IMAGES = { "bmp", "gif", "png", "jpg", "jpeg", "png" };

	public ImageChooser() {
		super();
		File desktop = new File(Mediator.getLastPath());
		if (desktop != null) {
			setCurrentDirectory(desktop);
		}
		setAcceptAllFileFilterUsed(false);
		setFileFilter(new ImageFilter(IMAGES, Mediator.getMessage(PropertiesKeys.IMAGE_FILES)));
		addChoosableFileFilter(new ImageFilter(BMP, Mediator.getMessage(PropertiesKeys.BMP_FILES)));
		addChoosableFileFilter(new ImageFilter(GIF, Mediator.getMessage(PropertiesKeys.GIF_FILES)));
		addChoosableFileFilter(new ImageFilter(JPG, Mediator.getMessage(PropertiesKeys.JPEG_FILES)));
		addChoosableFileFilter(new ImageFilter(PNG, Mediator.getMessage(PropertiesKeys.PNG_FILES)));
		addChoosableFileFilter(new FileFilter() {

			@Override
			public String getDescription() {
				return Mediator.getMessage(PropertiesKeys.ALL_FILES);
			}

			@Override
			public boolean accept(File f) {
				return true;
			}
		});
	}

}
