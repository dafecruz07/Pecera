package Vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Controlador.Pecera;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

public class PeceraVista extends JFrame {

	private JPanel contentPane;
	private Pecera pecera;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args, Pecera pecera) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PeceraVista frame = new PeceraVista(pecera);
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
	public PeceraVista(Pecera pecera)
	{
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1010, 698);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(1, 0, 0, 0));
		this.setLayout(null);
		contentPane.add(pecera);
		
		pecera.run(); //Se inicia la simulación.
	      
		
	}
	


}
