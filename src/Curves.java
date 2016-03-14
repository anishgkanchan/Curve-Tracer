import java.awt.*;
import java.awt.event.*;
import java.applet.*;
public class Curves extends Applet implements Runnable,ActionListener
{
	int p=0,q=0;
	double coeff=1;
	boolean reset=false;
	double sum=0;
	double temp=0;
	double consta=0;
	double prevopd=1;   //previous operand
	char prevopr=0;	    //previous operator
	String s;
	TextField t1=new TextField(20);
	Thread t;
	Button b1=new Button("submit");
	Button b2=new Button("clear");
	public void run()
	{
		for(int x=-400;x<=400;x++)
		{
			for(int y=-400;y<=400;y++)
			{
				for(int i=0;i<s.length()-2;i++)
				{																												
						if(s.charAt(i)=='*')
						{
							prevopr='*';														
						}
						else if(s.charAt(i)=='+')
						{																							
							sum+=temp;						
							prevopd=1;
							consta=0;
							prevopr='+';
							temp=0;																							
						}
						else if(s.charAt(i)=='-')
						{							
							sum+=temp;
							prevopd=-1;
							consta=0;
							prevopr='-';							
							temp=0;																																								
						}
						else if(s.charAt(i)=='x')
						{
							if(prevopr==0|prevopr=='+'|prevopr=='-')
							{								
								prevopd=prevopd*x*coeff;
								coeff=1;
								temp=prevopd;		
									prevopd=1;															
							}
							if(prevopr=='*')
							{
								temp=temp*x*coeff;
								coeff=1;																		
							}
							else
							if(prevopr=='/')
								temp/=x;
																					
						}	
						else if(s.charAt(i)=='y')
						{
						if(prevopr==0|prevopr=='+'|prevopr=='-'|coeff!=1)
							{								
								prevopd=prevopd*y*coeff;
								coeff=1;
								temp=prevopd;
								prevopd=1;																			
							}
							if(prevopr=='*')
							{
								temp=temp*y*coeff;
								coeff=1;																		
							}
							else
							if(prevopr=='/')
								temp/=y;																											
						}
						else if(s.charAt(i)>='0'|s.charAt(i)<='9')
						{
							consta=consta*10+s.charAt(i)-48;															
							if(s.charAt(i+1)<'0'|s.charAt(i+1)>'9')
							{	
								if(prevopr=='*'|s.charAt(i+1)=='*')
								{
									if(s.charAt(i+1)=='*'&prevopr=='-')										
									{consta*=prevopd;
									prevopd=1;}																													
									coeff=consta;
									if(prevopr=='*')
										temp*=coeff;
									else
										temp=coeff;
									coeff=1;
									consta=0;																																																
								}																																						
								else{																
								sum=sum+consta*prevopd;	
								consta=0;
								}																																													
							}																																							
						}
				}						
				sum+=temp;		
				if(sum==0.0)
				{
					p=x;
					q=y;	
					repaint();
					try{
					Thread.sleep(10);
						}
					catch(Exception e)
					{
					}					
				}
				sum=0;
				prevopd=1;
				temp=0;
				consta=0;
				prevopr=0;
				coeff=1;			
			}
		}
	}
	public void	actionPerformed(ActionEvent e)
	{
		if(e.getSource()==b1)
		{		
		s=t1.getText();
		t=new Thread(this);
		reset=false;
		t.start();
		}
		else
		{
			reset=true;
			t=new Thread(this);
			t.start();
			t1.setText("");
		}
	}
	public void init()
	{
			add(t1);
			add(b1);
			add(b2);
			b1.addActionListener(this);
			b2.addActionListener(this);
			
	}
	public void update(Graphics g)
	{
		paint(g);	
	}
	public void paint(Graphics g)
	{
		if(reset)
		g.setColor(Color.white);
		g.fillOval(500+p,500+q,2,2);
		g.setColor(Color.black);
		g.drawLine(250,500,750,500);
		g.drawLine(500,250,500,750);	
		g.drawString("X",750,490);
		g.drawString("-X",250,490);	
		g.drawString("Y",510,250);
		g.drawString("-Y",510,750);
		g.drawString("(0,0)",510,520);
		Font f=new Font("serif",Font.BOLD,32);
		g.setFont(f);
		g.drawString("Graph Plotter",410,200);
	}
}
