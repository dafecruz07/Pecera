package Vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import Controlador.Pecera;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import javax.swing.border.EtchedBorder;
import javax.swing.JSeparator;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaPpal extends JFrame {

	private JPanel contentPane;
	private JLabel panelPecera;
	private JPanel panelConfiguracion;
	private JSpinner spinnerPecesCantidad;
	private JSpinner spinnerPecesReprodCant;
	private JSpinner spinnerPecesVida;
	private JSpinner spinnerPecesReprodTime;
	private JSpinner spinnerTiburonCantidad;
	private JSpinner spinnerTiburonTiempoEspera;
	private JSpinner spinnerComidaTiempo;
	private JButton btnPrincipal;
	private JButton btnInfo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaPpal frame = new VentanaPpal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VentanaPpal() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaPpal.class.getResource("/Img/Icon.png")));
		setTitle("Pecera");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 269, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panelConfiguracion = new JPanel();
		panelConfiguracion.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		panelConfiguracion.setBounds(10, 11, 243, 549);
		contentPane.add(panelConfiguracion);
		panelConfiguracion.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Configuración Pecera");
		lblNewLabel.setFont(new Font("Calibri", Font.BOLD, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 11, 223, 36);
		panelConfiguracion.add(lblNewLabel);
		
		JLabel lblCantPeces = new JLabel("Cantidad:");
		lblCantPeces.setFont(new Font("Calibri", Font.PLAIN, 16));
		lblCantPeces.setBounds(20, 90, 157, 20);
		panelConfiguracion.add(lblCantPeces);
		
		JLabel lblMaximoReproducciones = new JLabel("Max reproducciones:");
		lblMaximoReproducciones.setFont(new Font("Calibri", Font.PLAIN, 16));
		lblMaximoReproducciones.setBounds(20, 121, 157, 20);
		panelConfiguracion.add(lblMaximoReproducciones);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 53, 223, 2);
		panelConfiguracion.add(separator);
		
		JLabel lblPeces = new JLabel("Peces");
		lblPeces.setHorizontalAlignment(SwingConstants.CENTER);
		lblPeces.setFont(new Font("Calibri", Font.BOLD, 19));
		lblPeces.setBounds(20, 59, 213, 20);
		panelConfiguracion.add(lblPeces);
		
		JLabel lblTiempoVida = new JLabel("Tiempo vida:");
		lblTiempoVida.setFont(new Font("Calibri", Font.PLAIN, 16));
		lblTiempoVida.setBounds(20, 152, 157, 20);
		panelConfiguracion.add(lblTiempoVida);
		
		JLabel lblEsperaReproduccin = new JLabel("Espera reproducci\u00F3n:");
		lblEsperaReproduccin.setFont(new Font("Calibri", Font.PLAIN, 16));
		lblEsperaReproduccin.setBounds(20, 183, 157, 20);
		panelConfiguracion.add(lblEsperaReproduccin);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 214, 223, 2);
		panelConfiguracion.add(separator_1);
		
		JLabel lblTiburones = new JLabel("Tiburones");
		lblTiburones.setHorizontalAlignment(SwingConstants.CENTER);
		lblTiburones.setFont(new Font("Calibri", Font.BOLD, 19));
		lblTiburones.setBounds(20, 224, 213, 20);
		panelConfiguracion.add(lblTiburones);
		
		JLabel label = new JLabel("Cantidad:");
		label.setFont(new Font("Calibri", Font.PLAIN, 16));
		label.setBounds(20, 255, 157, 20);
		panelConfiguracion.add(label);
		
		JLabel lblTiempoEspera = new JLabel("Tiempo espera:");
		lblTiempoEspera.setFont(new Font("Calibri", Font.PLAIN, 16));
		lblTiempoEspera.setBounds(20, 286, 157, 20);
		panelConfiguracion.add(lblTiempoEspera);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(10, 317, 223, 2);
		panelConfiguracion.add(separator_2);
		
		JLabel lblComida = new JLabel("Comida");
		lblComida.setHorizontalAlignment(SwingConstants.CENTER);
		lblComida.setFont(new Font("Calibri", Font.BOLD, 19));
		lblComida.setBounds(20, 330, 213, 20);
		panelConfiguracion.add(lblComida);
		
		JLabel lblTiempoGeneracin = new JLabel("Tiempo generaci\u00F3n:");
		lblTiempoGeneracin.setFont(new Font("Calibri", Font.PLAIN, 16));
		lblTiempoGeneracin.setBounds(20, 361, 157, 20);
		panelConfiguracion.add(lblTiempoGeneracin);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(10, 392, 223, 2);
		panelConfiguracion.add(separator_3);
		
		btnPrincipal = new JButton("Iniciar");
		
		btnPrincipal.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) //Cuando se oprime el botón iniciar, arranca la simulación.
			{
				Pecera peceraNueva= new Pecera();
				peceraNueva.crearPeces((int) spinnerPecesCantidad.getValue(), (int) spinnerPecesReprodCant.getValue(), (int) spinnerPecesVida.getValue(), (int) spinnerPecesReprodTime.getValue(),
						(int) spinnerTiburonCantidad.getValue(), (int) spinnerTiburonTiempoEspera.getValue(), (int) spinnerComidaTiempo.getValue());
				peceraNueva.crearTiburones((int) spinnerTiburonCantidad.getValue(), (int) spinnerTiburonTiempoEspera.getValue());
				peceraNueva.crearComida();
				PeceraVista pecera=  new PeceraVista(peceraNueva);
				pecera.main(null, peceraNueva);
				
					
			}
		});
		btnPrincipal.setFont(new Font("Calibri", Font.BOLD, 30));
		btnPrincipal.setBounds(33, 418, 177, 77);
		panelConfiguracion.add(btnPrincipal);
		
		btnInfo = new JButton("Info");
		btnInfo.setBounds(73, 515, 89, 23);
		panelConfiguracion.add(btnInfo);
		
		spinnerPecesCantidad = new JSpinner();
		spinnerPecesCantidad.setBounds(168, 90, 48, 20);
		SpinnerNumberModel cantModel = new SpinnerNumberModel();
		cantModel.setMinimum(2);
		cantModel.setMaximum(50);
		cantModel.setStepSize(2);
		spinnerPecesCantidad.setModel(cantModel);
		spinnerPecesCantidad.setValue(2);
		// Deshabilitar edicion por teclado
	    JFormattedTextField tf = ((JSpinner.DefaultEditor) spinnerPecesCantidad.getEditor()).getTextField();
	    tf.setBackground(Color.white);
	    tf.setEditable(false);
		// Deshabilitar edicion por teclado
		panelConfiguracion.add(spinnerPecesCantidad);
		
		spinnerPecesReprodCant = new JSpinner();
		spinnerPecesReprodCant.setBounds(168, 121, 48, 20);
		SpinnerNumberModel repCantModel = new SpinnerNumberModel();
		repCantModel.setMinimum(1);
		repCantModel.setMaximum(60);
		spinnerPecesReprodCant.setModel(repCantModel);
		spinnerPecesReprodCant.setValue(1);
		// Deshabilitar edicion por teclado
	    JFormattedTextField tf2 = ((JSpinner.DefaultEditor) spinnerPecesReprodCant.getEditor()).getTextField();
	    tf2.setBackground(Color.white);
	    tf2.setEditable(false);
		// Deshabilitar edicion por teclado
		panelConfiguracion.add(spinnerPecesReprodCant);
		
		spinnerPecesVida = new JSpinner();
		spinnerPecesVida.setBounds(168, 152, 48, 20);
		SpinnerNumberModel vidaModel = new SpinnerNumberModel();
		vidaModel.setMinimum(10);
		vidaModel.setMaximum(90);
		spinnerPecesVida.setModel(vidaModel);
		spinnerPecesVida.setValue(10);
		// Deshabilitar edicion por teclado
	    JFormattedTextField tf3 = ((JSpinner.DefaultEditor) spinnerPecesVida.getEditor()).getTextField();
	    tf3.setBackground(Color.white);
	    tf3.setEditable(false);
		// Deshabilitar edicion por teclado
		panelConfiguracion.add(spinnerPecesVida);
		
		spinnerPecesReprodTime = new JSpinner();
		spinnerPecesReprodTime.setBounds(168, 183, 48, 20);
		SpinnerNumberModel repTimeModel = new SpinnerNumberModel();
		repTimeModel.setMinimum(1);
		repTimeModel.setMaximum(60);
		spinnerPecesReprodTime.setModel(repTimeModel);
		spinnerPecesReprodTime.setValue(1);
		// Deshabilitar edicion por teclado
	    JFormattedTextField tf4 = ((JSpinner.DefaultEditor) spinnerPecesReprodTime.getEditor()).getTextField();
	    tf4.setBackground(Color.white);
	    tf4.setEditable(false);
		// Deshabilitar edicion por teclado
		panelConfiguracion.add(spinnerPecesReprodTime);
		
		spinnerTiburonCantidad = new JSpinner();
		spinnerTiburonCantidad.setBounds(168, 255, 48, 20);
		SpinnerNumberModel tibCantModel = new SpinnerNumberModel();
		tibCantModel.setMinimum(1);
		tibCantModel.setMaximum(60);
		spinnerTiburonCantidad.setModel(tibCantModel);
		spinnerTiburonCantidad.setValue(1);
		// Deshabilitar edicion por teclado
	    JFormattedTextField tf5 = ((JSpinner.DefaultEditor) spinnerTiburonCantidad.getEditor()).getTextField();
	    tf5.setBackground(Color.white);
	    tf5.setEditable(false);
		// Deshabilitar edicion por teclado
		panelConfiguracion.add(spinnerTiburonCantidad);
		
		spinnerTiburonTiempoEspera = new JSpinner();
		spinnerTiburonTiempoEspera.setBounds(168, 286, 48, 20);
		SpinnerNumberModel tibTimeEspModel = new SpinnerNumberModel();
		tibTimeEspModel.setMinimum(1);
		tibTimeEspModel.setMaximum(60);
		spinnerTiburonTiempoEspera.setModel(tibTimeEspModel);
		spinnerTiburonTiempoEspera.setValue(1);
		// Deshabilitar edicion por teclado
	    JFormattedTextField tf6 = ((JSpinner.DefaultEditor) spinnerTiburonTiempoEspera.getEditor()).getTextField();
	    tf6.setBackground(Color.white);
	    tf6.setEditable(false);
		// Deshabilitar edicion por teclado
		panelConfiguracion.add(spinnerTiburonTiempoEspera);
		
		spinnerComidaTiempo = new JSpinner();
		spinnerComidaTiempo.setBounds(168, 361, 48, 20);
		SpinnerNumberModel comidaTimeModel = new SpinnerNumberModel();
		comidaTimeModel.setMinimum(1);
		comidaTimeModel.setMaximum(60);
		spinnerComidaTiempo.setModel(comidaTimeModel);
		spinnerComidaTiempo.setValue(1);
		// Deshabilitar edicion por teclado
	    JFormattedTextField tf7 = ((JSpinner.DefaultEditor) spinnerComidaTiempo.getEditor()).getTextField();
	    tf7.setBackground(Color.white);
	    tf7.setEditable(false);
		// Deshabilitar edicion por teclado
		panelConfiguracion.add(spinnerComidaTiempo);
	}

}
