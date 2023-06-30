package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import business.AppConfig;
import business.ListOfValuesFactory;
import dao.ProblemaDatosException;
import pojo.ListOfValuesParameters;
import util.ListOfValuesItem;

public class ListOfValuesWindow extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel jpFinder = null;
	private JPanel jpList = null;
	private JPanel jpButtons = null;
	private JLabel jlFind = null;
	private JTextField jtFind = null;
	private JScrollPane jspList = null;
	private JList jlsList = null;
	private JButton jbFind = null;
	private JButton jbChoose = null;
	private JButton jbDiscard = null;

	private ListOfValuesParameters lovItem;
	private ListOfValuesModel listOfValues;
	private String codeValue;
	private long identifierValue;
	private String descriptionValue;

	// look and feel
	private AppLookAndFeel appLook;
	private OvalBorder lineBorder = new OvalBorder();
	
	/**
	 * @param owner
	 */
	//public listOfValues(  String entidad, String titulo, String filtro1, String filtro2, String filtro3, boolean autoMostrar )  {
	public ListOfValuesWindow( ListOfValuesParameters i)  {
		super();
		// set application theme
		appLook = new AppLookAndFeel("BLUE-SKY");
		//appLook = new AppLookAndFeel("GREEN-FOREST");
		this.lovItem = i;
    	if (lovItem.isAutoDisplay() == true) {
    	    setData(i);
    	}

    	if (listOfValues == null) {
    		JOptionPane.showMessageDialog(null, "No existen datos para esta lista de valores", "Error 1 - Punto de Ventas", JOptionPane.ERROR_MESSAGE);
			setCodeValue(null);
			setIdentifierValue(-1);
			setDescriptionValue(null);
     		setVisible(false);
			dispose();    		
    	} else {
    	    initialize(lovItem.getTitle());
    	
    	    getRootPane().registerKeyboardAction(acceptAction, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
    	    getRootPane().registerKeyboardAction(cancelAction, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);

    	    setVisible(true);
    	    jlsList.requestFocusInWindow();
    	    jlsList.setSelectedIndex(0);
    	}
	}
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize ( String title ) {
		this.setTitle(title);
		this.setResizable(false);
		this.setSize(700, 475);
		this.setLocation(200, 200);
    	this.setModal(true);
		this.setResizable(false);
		this.setLayout(new BorderLayout());
		this.setContentPane(new JLabel(new ImageIcon(AppConfig.iconsFolder + "main-bg.jpg")));
		//
		this.add(getjpFinder(), null);
		this.add(getjpList(), null);
		this.add(getJpButtons(), null);
	}

	/**
	 * This method initializes jpFinder	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getjpFinder() {
		if (jpFinder == null) {
			jlFind = new JLabel();
			jlFind.setSize(new Dimension(137, 16));
			jlFind.setLocation(new Point(12, 17));
			jlFind.setName("jlFind");
			jlFind.setFont(new Font("Dialog", Font.BOLD, 16));
			jlFind.setText("Buscar");
			jlFind.setHorizontalAlignment(SwingConstants.RIGHT);
			jlFind.setForeground(appLook.getBodyFg());
			jlFind.setFont(appLook.getRegularFont());
			//
			jpFinder = new JPanel();
			jpFinder.setLayout(null);
			jpFinder.setSize(new Dimension(700, 50));
			jpFinder.setLocation(new Point(0, 0));
			jpFinder.setBackground(appLook.getBodyBg());
			jpFinder.add(jlFind, null);
			jpFinder.add(getjtFind(), null);
		}
		return jpFinder;
	}

	/**
	 * This method initializes jtFind	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getjtFind() {
		if (jtFind == null) {
			jtFind = new JTextField();
			jtFind.setSize(new Dimension(420, 35));
			jtFind.setLocation(new Point(160, 10));
			jtFind.setBackground(appLook.getTxtFieldBg());
			jtFind.setForeground(appLook.getTxtFieldFg());
			jtFind.setFont(appLook.getRegularFont());
			jtFind.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.lightGray));
			jtFind.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jtFind.getText().length() > 0) {
						lovItem.setSearchString(jtFind.getText());
						resetList(getLovItem());
					}
				}
			});
			jtFind.addFocusListener(new java.awt.event.FocusAdapter() {
				public void focusGained(java.awt.event.FocusEvent e) {
					jtFind.selectAll();
				}
			});
			jtFind.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_DOWN) {
						jlsList.requestFocusInWindow();
					} 	
				}
			});
		}
		return jtFind;
	}
	
	/**
	 * This method initializes jpList	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getjpList() {
		if (jpList == null) {
			jpList = new JPanel();
			jpList.setLayout(null);
			jpList.setSize(new Dimension(700, 320));
			jpList.setLocation(new Point(0, 50));
			jpList.setOpaque(false);
			jpList.add(getjspList(), null);
		}
		return jpList;
	}
	
	/**
	 * This method initializes jspList	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getjspList() {
		if (jspList == null) {
			jspList = new JScrollPane();
			jspList.setSize(new Dimension(660, 280));
			jspList.setLocation(new Point(20, 20));
			jspList.setViewportView(getjlsList());
		}
		return jspList;
	}

	/**
	 * This method initializes jlsList	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getjlsList() {
		if (jlsList == null) {
			jlsList = new JList();
			jlsList.setPrototypeCellValue("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
			jlsList.setLayoutOrientation(JList.VERTICAL);		
			jlsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			jlsList.setModel(listOfValues);
			TabListCellRenderer renderer = new TabListCellRenderer();
			renderer.setTabs(new int[] {50, 200});
			jlsList.setCellRenderer(renderer);
			jlsList.setFixedCellHeight(40);
			jlsList.setFont(new Font("Courier", Font.PLAIN, 16));
            jlsList.addMouseListener(mouseListener);
            jlsList.addFocusListener(new java.awt.event.FocusAdapter() {
            	public void focusGained(java.awt.event.FocusEvent e) {
            		if ( jlsList.isSelectionEmpty() ) {
            			jlsList.setSelectedIndex(0);
            		}
            	}
            });
		}
		return jlsList;
	}

	/**
	 * This method initializes jpButtons	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJpButtons() {
		if (jpButtons == null) {
			jpButtons = new JPanel();
			jpButtons.setLayout(null);
			jpButtons.setSize(new Dimension(700, 80));
			jpButtons.setLocation(new Point(0, 370));
			jpButtons.setBackground(appLook.getFooterBg());
			jpButtons.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.lightGray));
			jpButtons.add(getjbFind(), null);
			jpButtons.add(getjbChoose(), null);
			jpButtons.add(getjbDiscard(), null);
		}
		return jpButtons;
	}		
	
	/**
	 * This method initializes jbFind	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getjbFind() {
		if (jbFind == null) {
			jbFind = new JButton();
			jbFind.setPreferredSize(new Dimension(150, 50));
			jbFind.setSize(new Dimension(150, 50));
			jbFind.setName("jbFind");
			jbFind.setText("Buscar");
			jbFind.setBackground(appLook.getSecondaryBg());
			jbFind.setForeground(appLook.getSecondaryFg());
			jbFind.setFont(appLook.getRegularFont());
			jbFind.setLocation(new Point(10, 15));
			jbFind.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jtFind.getText().length() > 0)
						resetList(getLovItem());
				}
			});
			jbFind.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						if (jtFind.getText().length() > 0)
							resetList(getLovItem());
					} 	
				}
			});
		}
		return jbFind;
	}

	/**
	 * This method initializes jbChoose	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getjbChoose() {
		if (jbChoose == null) {
			jbChoose = new JButton();
			jbChoose.setSize(new Dimension(150, 50));
			jbChoose.setLocation(new Point(275, 15));
			jbChoose.setText("Aceptar");
			jbChoose.setName("jbChoose");
			jbChoose.setMnemonic(KeyEvent.VK_UNDEFINED);
			jbChoose.setBackground(appLook.getPrimaryBg());
			jbChoose.setForeground(appLook.getPrimaryFg());
			jbChoose.setFont(appLook.getRegularFont());
			jbChoose.addActionListener(acceptAction);
		}
		return jbChoose;
	}

	/**
	 * This method initializes jbDiscard	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getjbDiscard() {
		if (jbDiscard == null) {
			jbDiscard = new JButton();
			jbDiscard.setName("jbDiscard");
			jbDiscard.setSize(new Dimension(150, 50));
			jbDiscard.setLocation(new Point(540, 13));
			jbDiscard.setText("Cancelar");
			jbDiscard.setMnemonic(KeyEvent.VK_UNDEFINED);
			jbDiscard.setBackground(appLook.getSecondaryBg());
			jbDiscard.setForeground(appLook.getSecondaryFg());
			jbDiscard.setFont(appLook.getRegularFont());
			jbDiscard.addActionListener(cancelAction);
		}
		return jbDiscard;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ListOfValuesParameters p = new ListOfValuesParameters();
        ListOfValuesWindow aplicacion = new ListOfValuesWindow(p);
    	    aplicacion.setDefaultCloseOperation( JDialog.DISPOSE_ON_CLOSE );
	}

    public void setData ( ListOfValuesParameters p ) {
    	    ListOfValuesFactory f = new ListOfValuesFactory();
    	try {
    		//System.out.println(p.getFilters()[0] + " - " + p.getFilters()[1] + " - " + p.getFilters()[2]);
    		listOfValues = f.getListOfValues(p);
			//listOfValues = new ListOfValuesModel(MonedaDAO.listaMonedas(filter));
		} catch (ProblemaDatosException e) {
			e.printStackTrace();
			listOfValues = null;
		} catch (Exception e) {
			e.printStackTrace();
			listOfValues = null;
		}    	
    }

	MouseListener mouseListener = new MouseAdapter() {
	     public void mouseClicked(MouseEvent e) {
	    	 if (e.getClickCount() == 2) {
	             int index = jlsList.locationToIndex(e.getPoint());
				 ListOfValuesItem itm = new ListOfValuesItem();
	             itm = (ListOfValuesItem) jlsList.getModel().getElementAt(index);
	             System.out.println("******** en el lov *******");
	             System.out.println("id: " + itm.getIdentifier() + " cod.: " + itm.getCode() + " descripcion: " + itm.getDescription());
	     		 setCodeValue(itm.getCode());
	     		 setIdentifierValue(itm.getIdentifier());
	     		 setDescriptionValue(itm.getDescription());
				 dispose();
	          }
	     }
	 };

	ActionListener acceptAction = new ActionListener() {
		public void actionPerformed(java.awt.event.ActionEvent e) {
			if (!jlsList.isSelectionEmpty()) {
				ListOfValuesItem itm = new ListOfValuesItem(); 
				itm = (ListOfValuesItem)jlsList.getSelectedValue();
	    		setCodeValue(itm.getCode());
	     		setIdentifierValue(itm.getIdentifier());
	     		setDescriptionValue(itm.getDescription());
	     		setVisible(false);
				dispose();
			} else {
				//JOptionPane.showMessageDialog(null, "No ha seleccionado ningun item", "Error 1 - Punto de Ventas", JOptionPane.ERROR_MESSAGE);
				System.out.println("No ha seleccionado ningun item");
			}
		}
   };
		
	ActionListener cancelAction = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				setCodeValue(null);
				setIdentifierValue(-1);
	     		setDescriptionValue(null);
	     		setVisible(false);
				dispose();
			}
		};	 
	
	public void resetList( ListOfValuesParameters p) {
		ListOfValuesModel lv = listOfValues;
		setData(p);
		if ( listOfValues != null ) {
		    jlsList.setModel(listOfValues);
		} else {
			JOptionPane.showMessageDialog(null, "No se encontraron datos para el criterio de busqueda ingresado", "Error 1 - Punto de Ventas", JOptionPane.ERROR_MESSAGE);
			jlsList.setModel(lv);
		}
		jlsList.repaint();
		jtFind.requestFocusInWindow();
	}

	public String getCodeValue() {
		if (codeValue != null) {
		    return codeValue.trim();
		} else {
			return null;
		}
	}

	public void setCodeValue(String valor) {
		codeValue = valor;
	}

	public long getIdentifierValue() {
		return identifierValue;
	}

	public void setIdentifierValue(long valor) {
		identifierValue = valor;
	}

	public String getDescriptionValue() {
		return descriptionValue;
	}

	public void setDescriptionValue(String descriptionValue) {
		this.descriptionValue = descriptionValue;
	}
	
	public ListOfValuesParameters getLovItem() {
		return lovItem;
	}

	public void setLovItem(ListOfValuesParameters lovItem) {
		this.lovItem = lovItem;
	}
	
	
}
