package vista.User;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.MouseListener;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.border.BevelBorder;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;

import controlador.DateToEdad;
import modelo.Episodio;
import modelo.Serie;
import modelo.Usuario;



public class VistaUserSiendoUsuario extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JTextField textoBusqueda;
	private JTable table;
	private JButton btnBuscar;
	private JList listaSeries;
	private JButton btnVerInformacion;
	private JLabel labelUsuario;
	private JLabel foto;
	private JLabel labelEdad;
	private JComboBox comboBox;
	private JButton btnMarcarComoVisto;
	private JButton btnInformacion;
	
	public VistaUserSiendoUsuario(){
		//setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{632, 0};
		gridBagLayout.rowHeights = new int[]{346, 346, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
	
	 

		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		getContentPane().add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{316, 0, 316, 0};
		gbl_panel.rowHeights = new int[]{173, 173, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		this.foto = new JLabel();
		GridBagConstraints gbc_foto = new GridBagConstraints();
		gbc_foto.gridheight = 2;
		gbc_foto.fill = GridBagConstraints.BOTH;
		gbc_foto.insets = new Insets(0, 0, 0, 5);
		gbc_foto.gridx = 0;
		gbc_foto.gridy = 0;
		panel.add(foto, gbc_foto);
		
		labelUsuario = new JLabel("Usuario");
		GridBagConstraints gbc_labelUsuario = new GridBagConstraints();
		gbc_labelUsuario.fill = GridBagConstraints.BOTH;
		gbc_labelUsuario.insets = new Insets(0, 0, 5, 0);
		gbc_labelUsuario.gridx = 2;
		gbc_labelUsuario.gridy = 0;
		panel.add(labelUsuario, gbc_labelUsuario);
		labelUsuario.setText("");
		
		labelEdad = new JLabel("Edad");
		GridBagConstraints gbc_labelEdad = new GridBagConstraints();
		gbc_labelEdad.fill = GridBagConstraints.BOTH;
		gbc_labelEdad.gridx = 2;
		gbc_labelEdad.gridy = 1;
		panel.add(labelEdad, gbc_labelEdad);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 0;
		gbc_tabbedPane.gridy = 1;
		getContentPane().add(tabbedPane, gbc_tabbedPane);
		
		JPanel pestanaBusca = new JPanel();
		tabbedPane.addTab("Buscar Series", null, pestanaBusca, null);
		GridBagLayout gbl_pestanaBusca = new GridBagLayout();
		gbl_pestanaBusca.columnWidths = new int[]{270, 86, 0, 0};
		gbl_pestanaBusca.rowHeights = new int[]{10, 0, -13, 0};
		gbl_pestanaBusca.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_pestanaBusca.rowWeights = new double[]{0.0, 1.0, 1.0, Double.MIN_VALUE};
		pestanaBusca.setLayout(gbl_pestanaBusca);
		
		JLabel lblBuscarSerie = new JLabel("Buscar Serie:");
		lblBuscarSerie.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblBuscarSerie = new GridBagConstraints();
		gbc_lblBuscarSerie.insets = new Insets(0, 0, 5, 5);
		gbc_lblBuscarSerie.gridx = 0;
		gbc_lblBuscarSerie.gridy = 0;
		pestanaBusca.add(lblBuscarSerie, gbc_lblBuscarSerie);
		
		textoBusqueda = new JTextField();
		GridBagConstraints gbc_textoBusqueda = new GridBagConstraints();
		gbc_textoBusqueda.insets = new Insets(0, 0, 5, 5);
		gbc_textoBusqueda.gridx = 1;
		gbc_textoBusqueda.gridy = 0;
		pestanaBusca.add(textoBusqueda, gbc_textoBusqueda);
		textoBusqueda.setColumns(10);
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.setActionCommand("buscar");
		GridBagConstraints gbc_btnBuscar = new GridBagConstraints();
		gbc_btnBuscar.insets = new Insets(0, 0, 5, 0);
		gbc_btnBuscar.gridx = 2;
		gbc_btnBuscar.gridy = 0;
		pestanaBusca.add(btnBuscar, gbc_btnBuscar);
		
		listaSeries = new JList();
		listaSeries.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		GridBagConstraints gbc_listaSeries = new GridBagConstraints();
		gbc_listaSeries.gridwidth = 3;
		gbc_listaSeries.insets = new Insets(0, 0, 5, 0);
		gbc_listaSeries.fill = GridBagConstraints.BOTH;
		gbc_listaSeries.gridx = 0;
		gbc_listaSeries.gridy = 1;
		pestanaBusca.add(listaSeries, gbc_listaSeries);
		
		btnVerInformacion = new JButton("Ver informaci\u00F3n");
		btnVerInformacion.setActionCommand("verInfoSerie");
		GridBagConstraints gbc_btnVerInformacion = new GridBagConstraints();
		gbc_btnVerInformacion.gridwidth = 3;
		gbc_btnVerInformacion.gridx = 0;
		gbc_btnVerInformacion.gridy = 2;
		pestanaBusca.add(btnVerInformacion, gbc_btnVerInformacion);
		
		JPanel pestanaSeries = new JPanel();
		tabbedPane.addTab("Mis series", null, pestanaSeries, null);
		GridBagLayout gbl_pestanaSeries = new GridBagLayout();
		gbl_pestanaSeries.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_pestanaSeries.rowHeights = new int[]{0, 0, 111, 0, 22, 0, 0};
		gbl_pestanaSeries.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_pestanaSeries.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		pestanaSeries.setLayout(gbl_pestanaSeries);
		
		JLabel lblNewLabel = new JLabel("Serie:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		pestanaSeries.add(lblNewLabel, gbc_lblNewLabel);
		
		comboBox = new JComboBox();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.weightx = 1.0;
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 0;
		pestanaSeries.add(comboBox, gbc_comboBox);
		comboBox.setActionCommand("combo");
		
		JLabel lblEpisodiosNoVistos = new JLabel("Episodios no vistos");
		lblEpisodiosNoVistos.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblEpisodiosNoVistos = new GridBagConstraints();
		gbc_lblEpisodiosNoVistos.insets = new Insets(0, 0, 5, 5);
		gbc_lblEpisodiosNoVistos.gridx = 0;
		gbc_lblEpisodiosNoVistos.gridy = 1;
		pestanaSeries.add(lblEpisodiosNoVistos, gbc_lblEpisodiosNoVistos);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 2;
		pestanaSeries.add(scrollPane, gbc_scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
			},
			new String[] {
				"Num", "Temp", "Titulo", "Fecha"
			}
		));
		scrollPane.setViewportView(table);
		
		btnInformacion = new JButton("Informacion");
		btnInformacion.setActionCommand("infoEpisodio");
		GridBagConstraints gbc_btnInformacion = new GridBagConstraints();
		gbc_btnInformacion.insets = new Insets(0, 0, 5, 5);
		gbc_btnInformacion.gridx = 0;
		gbc_btnInformacion.gridy = 3;
		pestanaSeries.add(btnInformacion, gbc_btnInformacion);
		
		btnMarcarComoVisto = new JButton("Marcar como visto");
		btnMarcarComoVisto.setActionCommand("visto");
		GridBagConstraints gbc_btnMarcarComoVisto = new GridBagConstraints();
		gbc_btnMarcarComoVisto.insets = new Insets(0, 0, 5, 5);
		gbc_btnMarcarComoVisto.gridx = 1;
		gbc_btnMarcarComoVisto.gridy = 3;
		pestanaSeries.add(btnMarcarComoVisto, gbc_btnMarcarComoVisto);
		
		//this.setLocationRelativeTo(null);
		this.setSize(500, 650);
	}
	
	public JList getListaSeries() {
		return listaSeries;
	}
	
	public void setListaSeries(JList listaSeries) {
		this.listaSeries = listaSeries;
	}

	public void setLabelEdad(Integer i){
		this.labelEdad.setText(i.toString() + " AÑOS.");
	}
	
	public String getLabelEdad(){
		return this.labelEdad.getText();
	}
	
	public JLabel getLabelUsuario() {
		return labelUsuario;
	}

	public void setLabelUsuario(String string) {
		this.labelUsuario.setText(string);
	}

	public void setTextoBusqueda(JTextField textoBusqueda) {
		this.textoBusqueda = textoBusqueda;
	}

	public String getTextoBusqueda(){
		return this.textoBusqueda.getText();
	}
	
	public void addUserListener(ActionListener al){
		this.btnBuscar.addActionListener(al);
		this.btnVerInformacion.addActionListener(al);
		this.comboBox.addActionListener(al);
		this.btnMarcarComoVisto.addActionListener(al);
		this.btnInformacion.addActionListener(al);
		
	}


	public void addMouseListener(MouseListener l){
		this.foto.addMouseListener(l);
	
	}
	
	public void updateVista(Usuario user){
		this.setLabelUsuario(user.getNick());
		if(user.getImagen()!=null){
			this.setFoto(user.getImagen());
			
		}
		DateToEdad conv = new DateToEdad();
		this.setLabelEdad(conv.calcularEdad(user.getNacimiento()));
	}
	
	public int getSelectedIndex(){
		return this.listaSeries.getSelectedIndex();
	}
	
	public void muestraSeries(ArrayList<Serie> lista){
		this.listaSeries.removeAll();
		Iterator<Serie> it = lista.iterator();
		DefaultListModel modelo = new DefaultListModel();
		while(it.hasNext()){
			Serie serie = it.next();
			modelo.addElement(serie);
		}
		this.listaSeries.setModel(modelo);
	}
	
	public void muestraMisSeries(ArrayList<Serie> lista){
		
		this.comboBox.removeAllItems();
		Iterator<Serie> it = lista.iterator();
		while(it.hasNext()){
			Serie serie = it.next();
			this.comboBox.addItem(serie);
		}
	}

	public Object getSelectedSerie(){
		return this.listaSeries.getSelectedValue();	
	}
	
	public void setFoto(byte[] imagen){
		this.foto.setText("");
		ImageIcon img = new ImageIcon(imagen);
		Image newimg =  img.getImage().getScaledInstance(150, 150,  java.awt.Image.SCALE_SMOOTH ) ;
		img=new ImageIcon(newimg);
		this.foto.setIcon(img);
	}
	
	public void setEpisodios(ArrayList<Episodio> lista){
	    
    	Object[][] obj = new Object[lista.size()][4];
        table.setModel(new javax.swing.table.DefaultTableModel(
               obj,
                new String [] {
                    "Num", "Temp", "Titulo", "Fecha"
                }
            ));
    	
    	Iterator<Episodio> it = lista.iterator();
    	String[][] info = new String[lista.size()][4];
    	int i = 0;
    	
    	while(it.hasNext()){
    		Episodio epi = it.next();
    		info[i][0] = epi.getNumOrden().toString();
    		this.table.setValueAt(info[i][0], i, 0);
    		info[i][1] = epi.getNumTemporada().toString();
    		this.table.setValueAt(info[i][1], i, 1);
    		info[i][2] = epi.toString();
    		this.table.setValueAt(epi, i, 2);
    		info[i][3] = epi.getEstreno().toString();
    		this.table.setValueAt(info[i][3], i, 3);
    		i++;
    	}

    }
	
	public void addItemListener(ItemListener it){
		this.comboBox.addItemListener(it);
	}
	

    public Episodio getSelectedEpisodio(){
    	Episodio episodio = null;
    	if(this.table.getSelectedRow()!=-1)
    	episodio = (Episodio) this.table.getValueAt(
    			this.table.getSelectedRow(), 2);
    	return episodio;
    }
    
	
	public Object getSelectedSerieCombo(){
		return this.comboBox.getSelectedItem();
	}
	
	public void sinFoto(){
		this.foto.setText("Sin Foto");
	}

	public JComboBox getComboBox() {
		// TODO Auto-generated method stub
		return this.comboBox;
	}
	
}
