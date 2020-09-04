package Controlador;

import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Comida extends JLabel implements Runnable
{
	
	boolean foodStatus=true;
	public Comida()
	{
		this.setIcon(new ImageIcon("src/Img/comida.png"));
	}

	@Override
	public void run() 
	{
		while(foodStatus)
		{
			if(this.getY()>600) break;
			else
			{
				Random r= new Random(System.currentTimeMillis());
				try {
					Thread.sleep(300+r.nextInt(80));
				} catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
				
				this.setLocation(this.getX(), this.getY()+1); //movimiento para que la comida caiga.
			}
		}
		
	}

	
	public boolean isFoodStatus() {
		return foodStatus;
	}

	public void setFoodStatus(boolean foodStatus) {
		this.foodStatus = foodStatus;
	}
	
}
