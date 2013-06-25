import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.print.PrinterException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.accessibility.Accessible;
import javax.accessibility.AccessibleContext;
import javax.accessibility.AccessibleRelation;
import javax.accessibility.AccessibleRelationSet;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

@SuppressWarnings("serial")
public class TableDemo extends DemoModule {
    
    JTable      tableView;
    
    JScrollPane scrollpane;
    
    JCheckBox   showHorizontalLinesCheckBox;
    
    JCheckBox   showVerticalLinesCheckBox;

    JLabel      rowHeightLabel;

    JSlider     rowHeightSlider;

    JLabel selectionModeLabel = null;
    
    JComboBox	selectionModeComboBox = null;
    
    JComboBox	resizeModeComboBox = null;

    JLabel      headerLabel;
    
    JLabel      footerLabel;

    JTextField  headerTextField;
    
    JTextField  footerTextField;

    JCheckBox   fitWidth;
    
    JButton     printButton;

    JPanel      controlPanel;
    
    JPanel      formPanel;
    
    JScrollPane tableAggregate;

    private JLabel studentidLabel = null;
    private JLabel nameLabel =  null;
    private JLabel sexLabel =  null;
    private JLabel ageLabel =  null;
    private JLabel birthdayLabel = null;
    private JLabel addressLabel =  null;
    private JLabel classLabel = null;
    private JLabel specialLabel = null;
    private JLabel phonenumberLabel =  null;
    
    
    private JTextField studentidText = null;
    private JTextField  nameText =  null;
    private JComboBox<String>  sexText =  null;
    private JSlider  ageSlider =  null;
    private JTextField  birthdayText =  null;
    private JComboBox<String>  addressText =  null;
    private JTextField  classText = null;
    private JComboBox<String>  specialText =  null;
    private JTextField  phonenumberText =  null;
    
    private int ageMin = 1;
    
    private int ageMax = 150;
    
    /**
     * 列表容器
     */
    JPanel tttp = null;
    
    final int INITIAL_ROWHEIGHT = 35;
    Object[][] data = null;
    final String DATA_SPLITOR=  ",";
    
    public static void main(String[] args)  {
    	
    	try {
    		UIManager.put("RootPane.setupButtonVisible", false);
    		if (BeautyEyeLNFHelper.isSurportedTranslucency()){
        		BeautyEyeLNFHelper.debug = false;
    			BeautyEyeLNFHelper.launchBeautyEyeLNF();
    		}
		} catch (Exception e) {
			System.err.println(" 发生错误："+e.getMessage());
		}
		TableDemo demo = new TableDemo( null );
		demo.mainImpl();
    }
    
    @Override 
    public String getName() {
    	return getString("TableDemo.name");
    };

    public TableDemo(SwingSet2 swingset) {
    	super(swingset, "TableDemo", "toolbar/JTable.gif");
    	
    	getDemoPanel().setLayout(new BorderLayout());
    	
    	controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.X_AXIS));
        
        Vector relatedComponents = new Vector();
        
    	showHorizontalLinesCheckBox = new JCheckBox(getString("TableDemo.horz_lines"), true);
        showHorizontalLinesCheckBox.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        	boolean flag = ((JCheckBox)e.getSource()).isSelected();
	                tableView.setShowHorizontalLines(flag); ;
	                tableView.repaint();
		    }
        });
        
    	showVerticalLinesCheckBox = new JCheckBox(getString("TableDemo.vert_lines"), false);
        showVerticalLinesCheckBox.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
	        	boolean flag = ((JCheckBox)e.getSource()).isSelected();
                tableView.setShowVerticalLines(flag); ;
                tableView.repaint();
	    	}
        });

		relatedComponents.removeAllElements();
		relatedComponents.add(showHorizontalLinesCheckBox);
		relatedComponents.add(showVerticalLinesCheckBox);
		buildAccessibleGroup(relatedComponents);

        relatedComponents.removeAllElements();
        buildAccessibleGroup(relatedComponents);

        rowHeightLabel = new JLabel(getString("TableDemo.row_height_colon"));
    	rowHeightSlider = new JSlider(JSlider.HORIZONTAL, 25, 100, INITIAL_ROWHEIGHT);
		rowHeightSlider.getAccessibleContext().setAccessibleName(getString("TableDemo.row_height"));
		rowHeightLabel.setLabelFor(rowHeightSlider);
        rowHeightSlider.addChangeListener(new ChangeListener() {
		    public void stateChanged(ChangeEvent e) {
		        int height = ((JSlider)e.getSource()).getValue();
	                tableView.setRowHeight(height);
	                tableView.repaint();
		    }
        });
		// Show that spacing controls are related
		relatedComponents.removeAllElements();
		relatedComponents.add(rowHeightSlider);
		buildAccessibleGroup(relatedComponents);


        // Create the table.
        tableAggregate = createTable();
        tttp = new JPanel(new BorderLayout());
        tttp.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 5));
        tttp.add(tableAggregate, BorderLayout.CENTER);
        // END
        getDemoPanel().add(tttp, BorderLayout.CENTER);
        selectionModeLabel = new JLabel(getString("TableDemo.selection_mode"));
        selectionModeComboBox = new JComboBox() {
            public Dimension getMaximumSize() {
                return getPreferredSize();
            }
        };
        selectionModeComboBox.addItem(getString("TableDemo.single"));
        selectionModeComboBox.addItem(getString("TableDemo.one_range"));
        selectionModeComboBox.addItem(getString("TableDemo.multiple_ranges"));
        selectionModeComboBox.setSelectedIndex(tableView.getSelectionModel().getSelectionMode());
        selectionModeComboBox.addItemListener(new ItemListener() {
		    public void itemStateChanged(ItemEvent e) {
		        JComboBox source = (JComboBox)e.getSource();
	                tableView.setSelectionMode(source.getSelectedIndex());
		    }
        });

        resizeModeComboBox = new JComboBox() {
            public Dimension getMaximumSize() {
                return getPreferredSize();
            }
        };
        resizeModeComboBox.addItem(getString("TableDemo.off"));
        resizeModeComboBox.addItem(getString("TableDemo.column_boundaries"));
        resizeModeComboBox.addItem(getString("TableDemo.subsequent_columns"));
        resizeModeComboBox.addItem(getString("TableDemo.last_column"));
        resizeModeComboBox.addItem(getString("TableDemo.all_columns"));
        resizeModeComboBox.setSelectedIndex(tableView.getAutoResizeMode());
        resizeModeComboBox.addItemListener(new ItemListener() {
		    public void itemStateChanged(ItemEvent e) {
		        JComboBox source = (JComboBox)e.getSource();
	            tableView.setAutoResizeMode(source.getSelectedIndex());
		    }
        });

        JPanel printPanel = new JPanel(new GridLayout(3, 2));
        // print panel
        printPanel.setBorder(new TitledBorder(getString("TableDemo.printing")));
        headerLabel = new JLabel(getString("TableDemo.header"));
        footerLabel = new JLabel(getString("TableDemo.footer"));
        headerTextField = new JTextField(getString("TableDemo.headerText"),15);
        footerTextField = new JTextField(getString("TableDemo.footerText"),15);
        fitWidth = new JCheckBox(getString("TableDemo.fitWidth"), true);
        printButton = new JButton(getString("TableDemo.print"));
        printButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                printTable();
            }
        });
        
        printPanel.add(headerLabel);
        printPanel.add(headerTextField);
        printPanel.add(footerLabel);
        printPanel.add(footerTextField);
        
        JPanel buttons = new JPanel();
        buttons.add(fitWidth);
        buttons.add(printButton);

        printPanel.add(buttons);

        // Show that printing controls are related
        relatedComponents.removeAllElements();
        relatedComponents.add(headerTextField);
        relatedComponents.add(footerTextField);
        relatedComponents.add(printButton);
        buildAccessibleGroup(relatedComponents);

        JPanel toolbar = new JPanel();
        toolbar.setBorder(new TitledBorder(getString("TableDemo.autosytle")));
        toolbar.add(showHorizontalLinesCheckBox);
        toolbar.add(showVerticalLinesCheckBox);
        toolbar.add(rowHeightLabel);
        toolbar.add(rowHeightSlider);
        toolbar.add(selectionModeLabel);
        toolbar.add(selectionModeComboBox);
        // add everything
        controlPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 2, 0));
        controlPanel.add(printPanel);
        
        //add form area
        formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(14,2));
        formPanel.setBorder(new TitledBorder(getString("TableDemo.inputForm")));
        formPanel.setSize(100, 200);
	    studentidLabel = new JLabel(getString("TableDemo.studentid"));
	    nameLabel = new JLabel(getString("TableDemo.studentname"));
	    sexLabel = new JLabel(getString("TableDemo.sex"));
	    ageLabel = new JLabel(getString("TableDemo.age"));
	    birthdayLabel = new JLabel(getString("TableDemo.birthday"));
	    addressLabel = new JLabel(getString("TableDemo.address"));
	    classLabel = new JLabel(getString("TableDemo.class"));
	    specialLabel = new JLabel(getString("TableDemo.special"));
	    phonenumberLabel = new JLabel(getString("TableDemo.phonenumber"));
	    
	    studentidText = new JTextField(15);
	    nameText = new JTextField(15);
	    sexText = new JComboBox<String>();
	    birthdayText = new JTextField(15);
	    addressText = new JComboBox<String>();
	    classText = new JTextField(15);
	    specialText = new JComboBox<String>();
	    phonenumberText = new JTextField(15);
	    
	    ageSlider = new JSlider(ageMin,ageMax,18);
	    ageLabel.setText(getString("TableDemo.age")+": 18");

	    ageSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int value = ((JSlider)e.getSource()).getValue();
				ageLabel.setText(getString("TableDemo.age")+": "+value);
			}
		});
	    sexText.addItem(getString("TableDemo.sex.male"));
	    sexText.addItem(getString("TableDemo.sex.fmale"));
	    
	    
	    specialText.addItem(getString("TableDemo.special.apply"));
	    specialText.addItem(getString("TableDemo.special.ntt"));
	    specialText.addItem(getString("TableDemo.special.mba"));
	    specialText.addItem(getString("TableDemo.special.mt"));
	    
	    addressText.addItem(getString("TableDemo.address.beijing"));
	    addressText.addItem(getString("TableDemo.address.shanxi"));
	    addressText.addItem(getString("TableDemo.address.shandong"));
	    addressText.addItem(getString("TableDemo.address.hunan"));
	    addressText.addItem(getString("TableDemo.address.guangdong"));
	    addressText.addItem(getString("TableDemo.address.hainan"));
	    addressText.addItem(getString("TableDemo.address.zhejiang"));
	    
	    
	    
        JButton saveBtn = new JButton(getString("TableDemo.saveBtn"));
        saveBtn.addActionListener(new ActionListener() {//click save button event.
			@Override
			public void actionPerformed(ActionEvent e) {
				saveOrUpdate();
			}
		});
        
		JButton removeCurrentRow=new JButton("删除选中行"); 
        removeCurrentRow.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                int currentRow=tableView.getSelectedRow();
                if(currentRow<0){
                	currentRow=0;
                } else {
                	deleteRow(currentRow);
                }
            }
        });
        
        formPanel.add(studentidLabel);
        formPanel.add(studentidText);
        formPanel.add(nameLabel);
        formPanel.add(nameText);
        formPanel.add(sexLabel);
        formPanel.add(sexText);
        
        formPanel.add(ageLabel);
        formPanel.add(ageSlider);
        
        formPanel.add(birthdayLabel);
        formPanel.add(birthdayText);
        formPanel.add(addressLabel);
        formPanel.add(addressText);
        formPanel.add(classLabel);
        formPanel.add(classText);
        formPanel.add(specialLabel);
        formPanel.add(specialText);
        formPanel.add(phonenumberLabel);
        formPanel.add(phonenumberText);

        formPanel.add(saveBtn);
        formPanel.add(removeCurrentRow);
        
        JPanel top = new JPanel(new GridLayout(1, 2));
        top.add(toolbar);
        top.add(controlPanel);
        getDemoPanel().add(top,BorderLayout.NORTH);
        getDemoPanel().add(formPanel,BorderLayout.EAST);
        setTableControllers(); // Set accessibility information
        getDemoPanel().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke("ctrl P"), "print");
        getDemoPanel().getActionMap().put("print", new AbstractAction() {
            public void actionPerformed(ActionEvent ae) {
                printTable();
            }
        });
    } 
	void buildAccessibleGroup(Vector components) {
		AccessibleContext context = null;
		int numComponents = components.size();
		Object[] group = components.toArray();
		Object object = null;
		for (int i = 0; i < numComponents; ++i) {
		    object = components.elementAt(i);
		    if (object instanceof Accessible) {
		        context = ((Accessible)components.elementAt(i)).getAccessibleContext();
		        context.getAccessibleRelationSet().add(new AccessibleRelation(AccessibleRelation.MEMBER_OF, group));
		    }
		}
    }
    private void setTableControllers() {
		setAccessibleController(showHorizontalLinesCheckBox, tableAggregate);
		setAccessibleController(showVerticalLinesCheckBox, tableAggregate);
		setAccessibleController(rowHeightSlider, tableAggregate);
		setAccessibleController(selectionModeComboBox, tableAggregate);
		setAccessibleController(resizeModeComboBox, tableAggregate);
    } 
    private void setAccessibleController(JComponent controller, JComponent target) {
		AccessibleRelationSet controllerRelations = controller.getAccessibleContext().getAccessibleRelationSet();
		AccessibleRelationSet targetRelations = target.getAccessibleContext().getAccessibleRelationSet();
		controllerRelations.add(new AccessibleRelation(AccessibleRelation.CONTROLLER_FOR, target));
		targetRelations.add(new AccessibleRelation(AccessibleRelation.CONTROLLED_BY, controller));
    } 

    private void printTable() {
        MessageFormat headerFmt;
        MessageFormat footerFmt;
        JTable.PrintMode printMode = fitWidth.isSelected() ?
                                     JTable.PrintMode.FIT_WIDTH :
                                     JTable.PrintMode.NORMAL;
        String text = headerTextField.getText();
        if (text != null && text.length() > 0) {
            headerFmt = new MessageFormat(text);
        } else {
            headerFmt = null;
        }
        text = footerTextField.getText();
        if (text != null && text.length() > 0) {
            footerFmt = new MessageFormat(text);
        } else {
            footerFmt = null;
        }
        try {
            boolean status = tableView.print(printMode, headerFmt, footerFmt);
            if (status) {
                JOptionPane.showMessageDialog(tableView.getParent(),
                                              getString("TableDemo.printingComplete"),
                                              getString("TableDemo.printingResult"),
                                              JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(tableView.getParent(),
                                              getString("TableDemo.printingCancelled"),
                                              getString("TableDemo.printingResult"),
                                              JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (PrinterException pe) {
            String errorMessage = MessageFormat.format(getString("TableDemo.printingFailed"),
                                                       new Object[] {pe.getMessage()});
            JOptionPane.showMessageDialog(tableView.getParent(),
                                          errorMessage,
                                          getString("TableDemo.printingResult"),
                                          JOptionPane.ERROR_MESSAGE);
        } catch (SecurityException se) {
            String errorMessage = MessageFormat.format(getString("TableDemo.printingFailed"),
                                                       new Object[] {se.getMessage()});
            JOptionPane.showMessageDialog(tableView.getParent(),
                                          errorMessage,
                                          getString("TableDemo.printingResult"),
                                          JOptionPane.ERROR_MESSAGE);
        }
    }
    void updateDragEnabled(boolean dragEnabled) {
        tableView.setDragEnabled(dragEnabled);
        headerTextField.setDragEnabled(dragEnabled);
        footerTextField.setDragEnabled(dragEnabled);
    }
    
    /**
     * Creates the table.
     * @return the j scroll pane
     */
    public JScrollPane createTable() {
    	TableModel dataModel = loadDataModel();
    	tableView = new JTable(dataModel);
    	tableView.setRowSelectionAllowed(true);
    	tableView.getTableHeader().setReorderingAllowed(true);
    	tableView.setRowHeight(INITIAL_ROWHEIGHT);
    	scrollpane = new JScrollPane(tableView);
    	return scrollpane;
    }
   
    
 /*******************************************begin:处理数据-方法************************************************/
    
    
    
    /**
     * 保存-新增数据
     */
    protected void saveOrUpdate() {
    	StringBuilder row = new StringBuilder("\n");
		row.append(studentidText.getText()).append(DATA_SPLITOR);
		row.append(nameText.getText()).append(DATA_SPLITOR);
		row.append(sexText.getSelectedItem()).append(DATA_SPLITOR);;
		row.append(ageSlider.getValue()).append(DATA_SPLITOR);
		row.append(birthdayText.getText()).append(DATA_SPLITOR);
		row.append(addressText.getSelectedItem()).append(DATA_SPLITOR);
		row.append(classText.getText()).append(DATA_SPLITOR);
		row.append(specialText.getSelectedItem()).append(DATA_SPLITOR);
		row.append(phonenumberText.getText()).append(DATA_SPLITOR);
		row.append("end");
//		System.out.println("row=["+row.toString()+"]");
		File file = new File(getString("TableDemo.dataFilePath"));
		FileWriter fileWriter = null;
		try {
			if (!file.exists())
				file.createNewFile();
			fileWriter = new FileWriter(file,true);
			fileWriter.write(row.toString());
		} catch (IOException e) {
			System.out.println("初始化数据文件失败.");
		} finally{
			if (fileWriter != null){
				try {
					fileWriter.flush();
					fileWriter.close();
//					TableDemo demo = new TableDemo( null );
//					demo.mainImpl();
					reloadTable();
				} catch (IOException e) {
					System.out.println("关闭写出流失败.");
				}
			}
		}
	}
    /**
     * 删除-所选的数据 delete selected row.
     * @param currentRow
     */
    protected void deleteRow(int currentRow) {
    	File file = new File(getString("TableDemo.dataFilePath"));
    	FileWriter  fileWriter = null;
    	if (file.exists()){
    		try {
				FileReader fileReader = new FileReader(file);
				BufferedReader reader = new BufferedReader(fileReader);
				String crow = reader.readLine();
				StringBuilder rows = new StringBuilder();
				int index  = 0;
				while( (crow = reader.readLine() ) != null){
//					System.out.println("删除：currentRow="+(currentRow)+",index="+index+",crow=["+crow+"]");
					if (currentRow != index && crow.trim().length() > 0){
						rows.append("\n").append(crow);
					}
					index++;
				}
				fileWriter = new FileWriter(file,false);
				fileWriter.write(rows.toString());
			} catch (FileNotFoundException e1) {
			} catch (IOException e2) {
			}finally{
				try {
					if (fileWriter != null ){
						fileWriter.flush();
						fileWriter.close();
						reloadTable();
					}
				} catch (IOException e1) {
				}
			}
    	}
	}
    
    /**
     * 刷新-重新加载数据：删除/新增后 - refresh list gird data.
     */
    protected void reloadTable() {
    	TableModel dataModel = loadDataModel();
        tableView.setModel(dataModel);
        tableView.revalidate();
	}
    
    /**
     * 加载数据 并构建table数据模型 - load data model
     * @return
     */
    public TableModel loadDataModel(){
    	 final String[] names = {
   			  getString("TableDemo.studentid"),
   			  getString("TableDemo.studentname"),
   			  getString("TableDemo.sex"),
   			  getString("TableDemo.age"),
   			  getString("TableDemo.address"),
   			  getString("TableDemo.class"),
   			  getString("TableDemo.special"),
   			  getString("TableDemo.phonenumber")
   		};
        loadDatas();
	    TableModel dataModel = new AbstractTableModel() {
	           public int getColumnCount() { return names.length; }
	           public int getRowCount() { return data.length;}
	           public Object getValueAt(int row, int col) {return data[row][col];}
	           public String getColumnName(int column) {return names[column];}
	           public boolean isCellEditable(int row, int col) {return false;}
	           public void setValueAt(Object aValue, int row, int column) { data[row][column] = aValue; }
	    };
	    return dataModel;
    }
    
    /**
     * 加载已经保存的数据-load data
     * @param data
     */
    private void loadDatas() {
    	try {
			File file = new File(getString("TableDemo.dataFilePath"));
			if (!file.exists()){
				file.createNewFile();
			}
			Reader in = new FileReader(file);
			BufferedReader reader = new BufferedReader(in);
			String row = reader.readLine();
			int index = 0;
			List<String> rows = new ArrayList<String>();
			while((row = reader.readLine()) != null){
				rows.add(row);
				index++;
			}
			data = new Object[index][9];
			index = 0;
			for (String record : rows){
				String[] cols = record.split(",");
				if (cols.length < 9)
					continue;
				data[index][0] = cols[0];
				data[index][1] = cols[1];
				data[index][2] = cols[2];
				data[index][3] = cols[3];
				data[index][4] = cols[4];
				data[index][5] = cols[5];
				data[index][6] = cols[6];
				data[index][7] = cols[7];
				data[index][8] = cols[8];
				index++;
			}
		} catch (FileNotFoundException e) {
			System.out.println("数据文件无法访问.");
		} catch (IOException e) {
			System.out.println("初始化数据文件失败.");
		}
	}
    
    /*******************************************end:处理数据-方法************************************************/
}
