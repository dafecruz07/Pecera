package Controlador;

import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Pez extends JLabel implements Runnable
{
	private Pecera pecera; 
	private int tipo = 0; //0: macho, 1: hembra
	private int tiempoVida;
	private int maxReproducciones;
	private int esperaReproduccion;
	private int aumentoX;
	private int aumentoY;
	private int speed =31;
	boolean fishStatus=true;

		
	public Pez(Pecera pecera, int tipo, int tiempoVida, int maxReproducciones, int esperaReproduccion)
	{
		this.pecera=pecera;
		this.tipo= tipo;
		this.tiempoVida= tiempoVida;
		this.maxReproducciones= maxReproducciones;
		this.esperaReproduccion= esperaReproduccion;
		aumentoX= 1;
		aumentoY= 1;
		
		if(tipo==0)
		{
			this.setIcon(new ImageIcon("src/Img/pezM.png"));
		}
		else
		{
			this.setIcon(new ImageIcon("src/Img/pezH.png"));
		}	
	}
	
	@Override
	public void run() 
	{	
		double tiempoInicio= System.currentTimeMillis();
		double tiempoInicioReproduccion = System.currentTimeMillis();
		int contador= 20; //Se programa un contador para que la dirección cambie después de 20 iteraciones.
		while(fishStatus)
		{
			if(tiempoInicio+(tiempoVida*1000)<= System.currentTimeMillis())
			{
				break; //Si ha pasado el tiempo de vida asignado al pez, este se queda quieto (muere).
			}
			else
			{
				try {
					Thread.sleep(speed);
				} catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
				
				int myPositionX=this.getLocation().x;
				int myPositionY=this.getLocation().y;
				
				Tiburon shark = searchShark(myPositionX, myPositionY);
				
				if(shark != null) // Tiburon dentro del radar de 200 unidades => Movimiento para alejarme de él
				{	
					Hashtable<String, Integer> movement = farMovement(shark, myPositionX, myPositionY);
					this.setLocation(movement.get("x"), movement.get("y"));
				}
				else
				{	
					Comida food = searchFood(myPositionX,myPositionY);
					if(food!=null) // Comida dentro del radar de 300 unidades => Movimiento linea recta hasta la comida
					{
						Hashtable<String, Integer> movement = equationOfLine(myPositionX,myPositionY,food.getLocation().x,food.getLocation().y);
						int movementX = movement.get("x");
						int movementY = movement.get("y");
						if(pecera.getComponentAt(movementX,movementY).equals(food))
						{
							food.setFoodStatus(false);
							pecera.getComida().remove(food);
							pecera.remove(food);
						}
						this.setLocation(movement.get("x"), movement.get("y"));
					}
					else
					{
						if(maxReproducciones > 0 && tiempoInicioReproduccion+(esperaReproduccion*1000)<= System.currentTimeMillis())
						{
							Pez couple = searchCouple(myPositionX, myPositionY, this.getTipo());
							if(couple!=null)
							{
								Hashtable<String, Integer> movement = equationOfLine(myPositionX,myPositionY,couple.getLocation().x,couple.getLocation().y);
								int movementX = movement.get("x");
								int movementY = movement.get("y");
								if(pecera.getComponentAt(movementX,movementY).equals(couple))
								{	
									int type = (int) (Math.floor(Math.random()*(1-0+ 1))+0);
									Random random= new Random(System.currentTimeMillis());
									Pez nuevo= new Pez(pecera,type,pecera.getTiempoVidaPeces()-(random.nextInt(10)-5),pecera.getCantidadReproducciones(), pecera.getTiempoReproduccion());
									if(type==0) 
									{
										pecera.getPecesMacho().add(nuevo);
									}
									else
									{
										pecera.getPecesHembra().add(nuevo);
									}
									nuevo.setBounds(movementX, movementY, 28, 61);
									pecera.getPeces().add(nuevo);	
									pecera.add(nuevo);
									new Thread(nuevo).start();
									
									tiempoInicioReproduccion = System.currentTimeMillis();
									maxReproducciones-=1;
								}
								
								this.setLocation(movement.get("x"), movement.get("y"));	
							}
							else 
							{
								Random random= new Random(System.currentTimeMillis());
								if(this.getX()<=1) aumentoX= 1; //Estas 4 líneas garantizan que el pez no se salga de la interfaz.
								if(this.getX()>=932) aumentoX= -1;
								if(this.getY()<=1) aumentoY= 1;
								if(this.getY()>=503) aumentoY= -1;
								if(contador==20) //Se cambia la dirección
								{
									int aleatorio= random.nextInt(); //Cada pez cambia de dirección de manera aleatoria.
									if(aleatorio%3==0) aumentoX*=-1;
									if(aleatorio%4==0) aumentoY*=-1;
									if(aleatorio%5==0) aumentoX=2;
									if(aleatorio%6==0) aumentoY=3;
									if(aleatorio%7==0) aumentoX=-3;
									if(aleatorio%8==0) aumentoY=-2;
									contador= 0;
								}
								this.setLocation(this.getLocation().x+aumentoX, this.getLocation().y+aumentoY);
								contador++;
							}
						
						}
						else // No encontro comida dentro del radar => movimiento aleatorio
						{
							Random random= new Random(System.currentTimeMillis());
							if(this.getX()<=1) aumentoX= 1; //Estas 4 líneas garantizan que el pez no se salga de la interfaz.
							if(this.getX()>=932) aumentoX= -1;
							if(this.getY()<=1) aumentoY= 1;
							if(this.getY()>=503) aumentoY= -1;
							if(contador==20) //Se cambia la dirección
							{
								int aleatorio= random.nextInt(); //Cada pez cambia de dirección de manera aleatoria.
								if(aleatorio%3==0) aumentoX*=-1;
								if(aleatorio%4==0) aumentoY*=-1;
								if(aleatorio%5==0) aumentoX=2;
								if(aleatorio%6==0) aumentoY=3;
								if(aleatorio%7==0) aumentoX=-3;
								if(aleatorio%8==0) aumentoY=-2;
								contador= 0;
							}
							this.setLocation(this.getLocation().x+aumentoX, this.getLocation().y+aumentoY);
							contador++;
						}
					}
					
				}					
			}
		}
	}
	
	public Tiburon searchShark(int x, int y)
	{
		int myPositionX=x;
		int myPositionY=y;
		int positionSharkX=0;
		int positionSharkY=0;
		
		double distanceSharkFish=1000;
		double distanceMinShark=300; // Radar de distancia de 200 unidades
		Tiburon closerShark = null;
		
		synchronized (pecera.getTiburones()) 
		{
			for(Tiburon shark : pecera.getTiburones())
			{
				positionSharkX=shark.getLocation().x;
				positionSharkY=shark.getLocation().y;
				
				distanceSharkFish = Math.min(distanceMinShark, distance(myPositionX,myPositionY,positionSharkX,positionSharkY));
				if(distanceSharkFish < distanceMinShark)
				{
					distanceMinShark = distanceSharkFish;
					closerShark=shark;
				}
			}	
		}
		return closerShark;
	}
	
	public Hashtable<String, Integer> farMovement(Tiburon shark, int myPositionX, int myPositionY)
	{
		double Fardistance = 0;
		Fardistance = distance(shark.getLocation().x,shark.getLocation().y,myPositionX+1,myPositionY);
		int aumentoX =1;
		int aumentoY=0;
		
		double distance = distance(shark.getLocation().x,shark.getLocation().y,myPositionX+1,myPositionY+1);
		if (distance > Fardistance) 
		{
			Fardistance = distance;
			aumentoY=1;
			aumentoX=1;
		}
		else
		{
			distance = distance(shark.getLocation().x,shark.getLocation().y,myPositionX,myPositionY+1);
			if (distance > Fardistance) 
			{
				Fardistance = distance;
				aumentoX=0; 
				aumentoY=1;
			}
			else
			{
				distance = distance(shark.getLocation().x,shark.getLocation().y,myPositionX-1,myPositionY+1);
				if (distance > Fardistance) 
				{
					Fardistance = distance;
					aumentoX=-1;
					aumentoY=1;
				}
				else
				{
					distance = distance(shark.getLocation().x,shark.getLocation().y,myPositionX-1,myPositionY);
					if (distance > Fardistance) 
					{
						Fardistance = distance;
						aumentoX=-1;
						aumentoY=0;
					}
					else
					{
						distance = distance(shark.getLocation().x,shark.getLocation().y,myPositionX-1,myPositionY-1);
						if (distance > Fardistance) 
						{
							Fardistance = distance;
							aumentoX=-1;
							aumentoY=-1;
						}
						else
						{
							distance = distance(shark.getLocation().x,shark.getLocation().y,myPositionX,myPositionY-1);
							if (distance > Fardistance) 
							{
								Fardistance = distance;
								aumentoX=0;
								aumentoY=-1;
							}
							else
							{
								distance = distance(shark.getLocation().x,shark.getLocation().y,myPositionX+1,myPositionY-1);
								if (distance > Fardistance) 
								{
									Fardistance = distance;
									aumentoX=1;
									aumentoY=-1;
								}
							}
						}
					}
				}
			}

			
		}
			
		Hashtable<String, Integer> movement = new Hashtable<String, Integer>();
		if(this.getX()<=1) aumentoX= 1; //Estas 4 líneas garantizan que el pez no se salga de la interfaz.
		if(this.getX()>=932) aumentoX= -1;
		if(this.getY()<=1) aumentoY= 1;
		if(this.getY()>=503) aumentoY= -1;
		movement.put("x", myPositionX+aumentoX);
		movement.put("y", myPositionY+aumentoY);
		
		return movement;
	}
	
	public Comida searchFood(int x, int y)
	{
		int myPositionX=x;
		int myPositionY=y;
		int positionFoodX=0;
		int positionFoodY=0;
		
		double distanceFoodFish=1000;
		double distanceMinFood=300; // Radar de distancia de 150 unidades
		Comida closerFood = null;
		
		synchronized (pecera.getComida()) 
		{
			for(Comida comida : pecera.getComida())
			{
				positionFoodX=comida.getLocation().x;
				positionFoodY=comida.getLocation().y;
				
				distanceFoodFish = Math.min(distanceMinFood, distance(myPositionX,myPositionY,positionFoodX,positionFoodY));
				if(distanceFoodFish < distanceMinFood)
				{
					distanceMinFood = distanceFoodFish;
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
	
	
	public Pez searchCouple(int x, int y, int type)
	{
		int myPositionX=x;
		int myPositionY=y;
		int positionCoupleX=0;
		int positionCoupleY=0;
		
		double distanceCoupleFish=1000;
		double distanceMinCouple=300; // Radar de distancia de 150 unidades
		Pez closerCouple = null;
		
		Vector<Pez> couples;
		if(type==0)
		{
			couples =pecera.getPecesHembra();
		}
		else
		{
			couples = pecera.getPecesMacho();
		}
		synchronized (couples) 
		{
			for(Pez couple : couples)
			{
				positionCoupleX=couple.getLocation().x;
				positionCoupleY=couple.getLocation().y;
				
				distanceCoupleFish = Math.min(distanceMinCouple, distance(myPositionX,myPositionY,positionCoupleX,positionCoupleY));
				if(distanceCoupleFish < distanceMinCouple && couple.fishStatus && couple.getMaxReproducciones() > 0)
				{
					distanceMinCouple = distanceCoupleFish;
					closerCouple=couple;
				}
			}
		}
		
		return closerCouple;	
	}
	
	public double distance(int x1, int y1, int x2, int y2)
	{
		return Math.hypot(x2-x1, y2-y1);
	}


	/**
	 * Metodo Get de la variable tipo
	 * @return El valor de tipo
	 */
	public int getTipo() {
		return tipo;
	}

	/**
	 * Metodo Set de la variable tipo
	 * @param tipo El valor de tipo que sera asignado
	 */
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	
	public boolean isFishStatus() {
		return fishStatus;
	}

	public void setFishStatus(boolean fishStatus) {
		this.fishStatus = fishStatus;
	}

	public int getMaxReproducciones() {
		return maxReproducciones;
	}

	public void setMaxReproducciones(int maxReproducciones) {
		this.maxReproducciones = maxReproducciones;
	}

	public int getEsperaReproduccion() {
		return esperaReproduccion;
	}

	public void setEsperaReproduccion(int esperaReproduccion) {
		this.esperaReproduccion = esperaReproduccion;
	}
}
