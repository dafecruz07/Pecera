package Controlador;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;


public class Pecera extends JLabel implements Runnable
{
	private Vector<Pez> peces;
	private Vector<Pez> pecesHembra;
	private Vector<Pez> pecesMacho;
	private Vector<Tiburon> tiburones;
	private Vector<Comida> comida;
	private int cantidadFish;
	private int tiempoComida;
	int tiempoVidaPeces;
	int tiempoReproduccion;
	int cantidadReproducciones;
				
	
	public Pecera()
	{
		this.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		this.setBounds(0, 0, 1000, 564);
		this.setIcon(new ImageIcon("src/Img/Fondo.png"));
		this.setOpaque(true);
		this.setLayout(null);	
	}
	

	public void crearPeces(int cantidadPeces, int cantidadReproduciones, int tiempoVidaPeces, int tiempoReproduccion, int cantidadTiburones, int tiempoTiburones, int tiempoComida)
	{
		this.tiempoVidaPeces=tiempoVidaPeces;
		this.tiempoReproduccion=tiempoReproduccion;
		this.cantidadReproducciones=cantidadReproduciones;
		
		this.cantidadFish= cantidadPeces;
		this.tiempoComida= tiempoComida;
	//---------------------------------
				
		this.peces= new Vector<Pez>(0,1);
		this.pecesHembra = new Vector<Pez>(0,1);
		this.pecesMacho = new Vector<Pez>(0,1);
		
		for(int i= 0; i<cantidadPeces; i++)
		{
			int tipo= 0;
			if((i%2)==0) tipo=1;
				
			
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Random random= new Random(System.currentTimeMillis());
			Pez nuevo= new Pez(this, tipo, tiempoVidaPeces-(random.nextInt(10)-5), cantidadReproduciones, tiempoReproduccion); //Se crean los peces con 5 segundos de desviación.
			int x= random.nextInt(932); //Se asigna aleatoriamente la posición de los peces.
			int y= random.nextInt(503);
			if(tipo==0) 
			{
				pecesMacho.add(nuevo);
			}
			else
			{
				pecesHembra.add(nuevo);
			}
			nuevo.setBounds(x, y, 28, 61);
			peces.add(nuevo);	
			this.add(nuevo);
		}		
	}
	
	public void crearTiburones(int cantidadTiburones, int tiempoTiburones)
	{		
		tiburones= new Vector<Tiburon>(0,1);

		for(int i= 0; i<cantidadTiburones; i++)
		{
				
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Random random= new Random(System.currentTimeMillis());
			Tiburon nuevo= new Tiburon(this,tiempoTiburones); //Se crea el tiburón con su tiempo de espera.
			int x= random.nextInt(850); //Se asigna aleatoriamente la posición de los peces.
			int y= random.nextInt(473);
			nuevo.setBounds(x, y, 150, 91);
			tiburones.add(i,nuevo);
			this.add(nuevo);

		}		
	}
	
	public void crearComida()
	{
		comida= new Vector<Comida>(0,1);

		for(int i= 0; i<(cantidadFish/4)*3; i++) 
		{
			try {
				Thread.sleep(1);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			
			Random random= new Random(System.currentTimeMillis());
			Comida nuevo= new Comida();
			int x=random.nextInt(975);
			int y=random.nextInt(525);
			nuevo.setBounds(x, y, 20, 31); //Se asigna aleatoriamente la posicion de la comida.
			comida.add(nuevo);
			this.add(nuevo);
		}
		
	}
	
	public void correrPeces()
	{
		for (int i = 0; i < peces.size(); i++) 
		{
			new Thread(peces.elementAt(i)).start();
		}
	}
	
	public void correrTiburones()
	{
		for (int i = 0; i < tiburones.size(); i++) 
		{
			new Thread(tiburones.elementAt(i)).start();
		}
	}
	
	public void correrComida()
	{
		for (int i = 0; i < comida.size(); i++) 
		{
			new Thread(comida.elementAt(i)).start();
		}
	}
	
	public int getTiempoComida()
	{
		return tiempoComida;
	}


	@Override
	public void run() 
	{
		Timer timer = new Timer(); 	
		
	      TimerTask timerTaskPeces = new TimerTask() //Se programa el hilo independiente para los peces.
	      { 
	          public void run()  
	          { 
	        	  correrPeces();	        	 
	          } 
	      };
	      TimerTask timerTaskComida = new TimerTask() //Se programa el hilo independiente para la comida.
	      { 
	          public void run()  
	          { 
	        	  correrComida();	        	 
	          } 
	      };
	      TimerTask timerTaskTiburones = new TimerTask() //Se programa el hilo independiente para la comida.
	      { 
	    	  public void run()
              {	           
	        	  correrTiburones();	        	 
	          } 
	      };
			      
	      
	      timer.schedule(timerTaskPeces, 100);
	      timer.schedule(timerTaskTiburones, 100);
	      timer.schedule(timerTaskComida, 100);
 	}


	public Vector<Pez> getPeces() {
		return peces;
	}


	public void setPeces(Vector<Pez> peces) {
		this.peces = peces;
	}


	public Vector<Tiburon> getTiburones() {
		return tiburones;
	}


	public void setTiburones(Vector<Tiburon> tiburones) {
		this.tiburones = tiburones;
	}


	public Vector<Comida> getComida() {
		return comida;
	}


	public void setComida(Vector<Comida> comida) {
		this.comida = comida;
	}


	public int getCantidadFish() {
		return cantidadFish;
	}


	public void setCantidadFish(int cantidadFish) {
		this.cantidadFish = cantidadFish;
	}


	public void setTiempoComida(int tiempoComida) {
		this.tiempoComida = tiempoComida;
	}


	public Vector<Pez> getPecesHembra() {
		return pecesHembra;
	}


	public void setPecesHembra(Vector<Pez> pecesHembra) {
		this.pecesHembra = pecesHembra;
	}


	public Vector<Pez> getPecesMacho() {
		return pecesMacho;
	}


	public void setPecesMacho(Vector<Pez> pecesMacho) {
		this.pecesMacho = pecesMacho;
	}


	public int getTiempoVidaPeces() {
		return tiempoVidaPeces;
	}


	public void setTiempoVidaPeces(int tiempoVidaPeces) {
		this.tiempoVidaPeces = tiempoVidaPeces;
	}


	public int getTiempoReproduccion() {
		return tiempoReproduccion;
	}


	public void setTiempoReproduccion(int tiempoReproduccion) {
		this.tiempoReproduccion = tiempoReproduccion;
	}


	public int getCantidadReproducciones() {
		return cantidadReproducciones;
	}


	public void setCantidadReproducciones(int cantidadReproducciones) {
		this.cantidadReproducciones = cantidadReproducciones;
	}
	
	
	

}
