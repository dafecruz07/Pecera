package Controlador;

import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Tiburon extends JLabel implements Runnable 
{
	private Pecera pecera; 
	private int tiempoEspera;
	boolean sharkStatus=true;
	private int aumentoX;
	private int aumentoY;
	
	
	public Tiburon(Pecera pecera, int tiempoEspera)
	{
		this.pecera=pecera;
		aumentoX= 1;
		aumentoY= 1;
		this.tiempoEspera= tiempoEspera;
		this.setIcon(new ImageIcon("src/Img/tiburon.png"));
	}

	@Override
	public void run() 
	{
		double tiempoInicio= System.currentTimeMillis();
		int contador= 35; //Se programa un contador para que la dirección cambie después de 20 iteraciones.
		int velocidad= 41;
		boolean encontroComida= false;
		
		while(sharkStatus)
		{
			if(tiempoInicio+(tiempoEspera*1000)<= System.currentTimeMillis() && !encontroComida)
			{
				velocidad=11; //Si ha pasado el tiempo de espera y no ha encontrado comida, el tiburón acelera.
				encontroComida= true;
			}
			else
			{
				try {
					Thread.sleep(velocidad);
				} catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
				
				int myPositionX=this.getLocation().x;
				int myPositionY=this.getLocation().y;
				
				Pez food = searchFood(myPositionX,myPositionY);
				if(food!=null) // Comida dentro del radar de 150 unidades => Movimiento linea recta hasta la comida
				{
					Hashtable<String, Integer> movement = equationOfLine(myPositionX,myPositionY,food.getLocation().x,food.getLocation().y);
					int movementX = movement.get("x");
					int movementY = movement.get("y");
					if(pecera.getComponentAt(movementX,movementY).equals(food))
					{
						if(food.getTipo()==0) 
						{
							pecera.getPecesMacho().remove(food);
						}
						else
						{
							pecera.getPecesHembra().remove(food);
						}
						pecera.getPeces().remove(food);
						pecera.remove(food);
						food.setFishStatus(false);
					}
					this.setLocation(movement.get("x"), movement.get("y"));
				}
				else
				{
					Random random= new Random(System.currentTimeMillis());
					if(this.getX()<=1) aumentoX= 1; //Estas 4 líneas garantizan que el tiburón no se salga de la interfaz.
					if(this.getX()>=850) aumentoX= -1;
					if(this.getY()<=1) aumentoY= 1;
					if(this.getY()>=473) aumentoY= -1;
					if(contador==35) //Se cambia la dirección
					{
						int aleatorio= random.nextInt(); //Cada tiburón cambia de dirección de manera aleatoria.
						if(aleatorio%3==0) aumentoX*=-1;
						if(aleatorio%4==0) aumentoY*=-1;
						if(aleatorio%5==0) aumentoX=1;
						if(aleatorio%6==0) aumentoY=2;
						if(aleatorio%7==0) aumentoX=-2;
						if(aleatorio%8==0) aumentoY=-1;
						contador= 0;
					}
					this.setLocation(this.getX()+aumentoX, this.getY()+aumentoY);
					contador++;
				}				
			}
		}

		
	}
	
	public Pez searchFood(int x, int y)
	{
		int myPositionX=x;
		int myPositionY=y;
		int positionFoodX=0;
		int positionFoodY=0;
		
		double distanceFoodShark=1000;
		double distanceMinFood=300; // Radar de distancia de 300 unidades
		Pez closerFood = null;
		
		synchronized (pecera.getPeces()) 
		{
			for(Pez comida : pecera.getPeces())
			{
				positionFoodX=comida.getLocation().x;
				positionFoodY=comida.getLocation().y;
				
				distanceFoodShark = Math.min(distanceMinFood, distance(myPositionX,myPositionY,positionFoodX,positionFoodY));
				if(distanceFoodShark < distanceMinFood)
				{
					distanceMinFood = distanceFoodShark;
					closerFood=comida;
				}
			}	
		}
		
		return closerFood;	
	}
	
	public Hashtable<String, Integer> equationOfLine(int x0, int y0, int x1, int y1)
	{
		  int dx = x1 - x0;
		  int dy = y1 - y0;
		  Hashtable<String, Integer> movement = new Hashtable<String, Integer>();
		  
		  if (dx == 0)
		  {
			  if(dy<0)
	               dy =  -1;
	           else
	               dy =  1;
			  
			  y0 += dy;
			  movement.put("x", x0);
			  movement.put("y", y0);			  
		  }
		  else if(dy==0)
		  {
			  if(dx<0)
	               dx =  -1;
	           else
	               dx =  1;
			  
			  x0 += dx;
			  movement.put("x", x0);
			  movement.put("y", y0);	 
		  }
		  else
		  {
		    double m = (double) dy / (double) dx;
		    double b = y0 - m*x0;
		   
    		if(dx<0)
		      dx = -1; 
		    else
		      dx = 1;
		    
		    x0 += dx;
		    y0 = (int) Math.round(m*x0 + b);
		    movement.put("x", x0);
		    movement.put("y", y0);	  	
		 }
		  
		  return movement;
	}
	
	public double distance(int x1, int y1, int x2, int y2)
	{
		return Math.hypot(x2-x1, y2-y1);
	}
	
	public boolean isSharkStatus() {
		return sharkStatus;
	}

	public void setSharkStatus(boolean sharkStatus) {
		this.sharkStatus = sharkStatus;
	}
	

}
