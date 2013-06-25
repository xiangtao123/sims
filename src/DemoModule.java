import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import org.jb2011.lnf.beautyeye.widget.N9ComponentFactory;

public class DemoModule extends JApplet {

	public static int PREFERRED_WIDTH = 780;

	public static int PREFERRED_HEIGHT = 600;

	Border loweredBorder = // new CompoundBorder(new
							// SoftBevelBorder(SoftBevelBorder.LOWERED),
	new EmptyBorder(15, 10, 5, 10);// );

	public static Dimension HGAP2 = new Dimension(2, 1);

	public static Dimension VGAP2 = new Dimension(1, 2);

	public static Dimension HGAP5 = new Dimension(5, 1);

	public static Dimension VGAP5 = new Dimension(1, 5);

	public static Dimension HGAP10 = new Dimension(10, 1);

	public static Dimension VGAP10 = new Dimension(1, 10);

	public static Dimension HGAP15 = new Dimension(15, 1);

	public static Dimension VGAP15 = new Dimension(1, 15);

	public static Dimension HGAP20 = new Dimension(20, 1);

	public static Dimension VGAP20 = new Dimension(1, 20);

	public static Dimension HGAP25 = new Dimension(25, 1);

	public static Dimension VGAP25 = new Dimension(1, 25);

	public static Dimension HGAP30 = new Dimension(30, 1);

	public static Dimension VGAP30 = new Dimension(1, 30);

	private SwingSet2 swingset = null;

	private JPanel panel = null;

	private String resourceName = null;

	private String iconPath = null;

	private String sourceCode = null;

	private ResourceBundle bundle = null;

	public DemoModule(SwingSet2 swingset) {
		this(swingset, null, null);
	}

	public DemoModule(SwingSet2 swingset, String resourceName, String iconPath) {
		UIManager.put("swing.boldMetal", Boolean.FALSE);
		panel = new JPanel();

		panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
		panel.setLayout(new BorderLayout());

		this.resourceName = resourceName;
		this.iconPath = iconPath;
		this.swingset = swingset;

		loadSourceCode();
	}

	public String getResourceName() {
		return resourceName;
	}

	public JPanel getDemoPanel() {
		return panel;
	}

	public SwingSet2 getSwingSet2() {
		return swingset;
	}

	public String getString(String key) {
		String value = "nada";
		if (bundle == null) {
			if (getSwingSet2() != null) {
				bundle = getSwingSet2().getResourceBundle();
			} else {
				bundle = ResourceBundle.getBundle("resources.swingset");
			}
		}
		try {
			value = bundle.getString(key);
		} catch (MissingResourceException e) {
			System.out
					.println("java.util.MissingResourceException: Couldn't find value for: "
							+ key);
		}
		return value;
	}

	public char getMnemonic(String key) {
		return (getString(key)).charAt(0);
	}

	public ImageIcon createImageIcon(String filename, String description) {
		if (getSwingSet2() != null) {
			return getSwingSet2().createImageIcon(filename, description);
		} else {
			String path = "/resources/images/" + filename;
			return new ImageIcon(getClass().getResource(path), description);
		}
	}

	public String getSourceCode() {
		return sourceCode;
	}

	public void loadSourceCode() {
		if (getResourceName() != null) {
			String filename = getResourceName() + ".java";
			sourceCode = new String("<html><body bgcolor=\"#ffffff\"><pre>");
			InputStream is;
			InputStreamReader isr;
			CodeViewer cv = new CodeViewer();
			URL url;

			try {
				url = getClass().getResource(filename);
				is = url.openStream();
				isr = new InputStreamReader(is, "UTF-8");
				BufferedReader reader = new BufferedReader(isr);

				// Read one line at a time, htmlize using super-spiffy
				// html java code formating utility from www.CoolServlets.com
				String line = reader.readLine();
				while (line != null) {
					sourceCode += cv.syntaxHighlight(line) + " \n ";
					line = reader.readLine();
				}
				sourceCode += new String("</pre></body></html>");
			} catch (Exception ex) {
				sourceCode = "Could not load file: " + filename;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Component#getName()
	 */
	public String getName() {
		return getString(getResourceName() + ".name");
	};

	/**
	 * Gets the icon.
	 * 
	 * @return the icon
	 */
	public Icon getIcon() {
		return createImageIcon(iconPath, getResourceName() + ".name");
	};

	/**
	 * Gets the tool tip.
	 * 
	 * @return the tool tip
	 */
	public String getToolTip() {
		return getString(getResourceName() + ".tooltip");
	};

	/**
	 * Main impl.
	 */
	public void mainImpl() {
		JFrame frame = new JFrame(getName());
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(getDemoPanel(), BorderLayout.CENTER);
		getDemoPanel().setPreferredSize(
				new Dimension(PREFERRED_WIDTH, PREFERRED_HEIGHT));
		frame.pack();
		frame.show();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * Creates the horizontal panel.
	 * 
	 * @param threeD
	 *            the three d
	 * @return the j panel
	 */
	public JPanel createHorizontalPanel(boolean threeD) {
		JPanel p = N9ComponentFactory.createPanel_style1(null)
				.setDrawBg(threeD);// modified by jb2011
		p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
		p.setAlignmentY(TOP_ALIGNMENT);
		p.setAlignmentX(LEFT_ALIGNMENT);
		if (threeD) {
			p.setBorder(loweredBorder);
		}
		p.setOpaque(false);
		return p;
	}

	/**
	 * Creates the vertical panel.
	 * 
	 * @param threeD
	 *            the three d
	 * @return the j panel
	 */
	public JPanel createVerticalPanel(boolean threeD) {
		JPanel p = N9ComponentFactory.createPanel_style1(null)
				.setDrawBg(threeD);
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		p.setAlignmentY(TOP_ALIGNMENT);
		p.setAlignmentX(LEFT_ALIGNMENT);
		if (threeD) {
			p.setBorder(loweredBorder);
		}
		return p;
	}

	public static void main(String[] args) {
		DemoModule demo = new DemoModule(null);
		demo.mainImpl();
	}

	public void init() {
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(getDemoPanel(), BorderLayout.CENTER);
	}

	/**
	 * Update drag enabled.
	 * 
	 * @param dragEnabled
	 *            the drag enabled
	 */
	void updateDragEnabled(boolean dragEnabled) {
	}

}
