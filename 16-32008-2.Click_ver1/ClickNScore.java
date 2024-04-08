import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.awt.geom.*;

public class ClickNScore extends JFrame implements ActionListener, MouseListener, Runnable
{
  private JLabel LabelTitle, LabelScore, LabelScoreResult;
  private JButton Button1,Button2,Button3,Button4,Button5,Button6,Button7,Button8,Button9,Button10;
  private JPanel Panel;
  private int click=0;
  private int temp=0;
  private String score;

  ClickNScore()
 {
    super("Click and Score");
    this.setSize(500,700);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    Panel = new JPanel();
    Panel.setLayout(null);

    LabelTitle = new JLabel("Click To Score");
    LabelTitle.setBounds(150,25,100,50);
    LabelTitle.setForeground(Color.BLUE);
    LabelTitle.setOpaque(true);
    Panel.add(LabelTitle);

    LabelScore = new JLabel("Your Score is: ");
    LabelScore.setBounds(300,50,100,50);
    LabelScore.setForeground(Color.BLUE);
    LabelScore.setOpaque(true);
    Panel.add(LabelScore);

    LabelScoreResult = new JLabel(" ");
    LabelScoreResult.setBounds(380,50,100,50);

    LabelScoreResult.setOpaque(true);
    Panel.add(LabelScoreResult);

    Button1 = new JButton("1");
	Button1.setBounds(50,150,100,50);
    Button1.setBackground(Color.BLUE);
	
		Button1.addActionListener(this);
		Button1.addMouseListener(this);
		Panel.add(Button1);

		Button2 = new JButton("2");
		Button2.setBounds(160,150,100,50);
		Button2.setBackground(Color.BLUE);
		Button2.addActionListener(this);
		Button2.addMouseListener(this);
		Panel.add(Button2);

		Button3 = new JButton("3");
		Button3.setBounds(50,220,100,50);
		Button3.setBackground(Color.BLUE);
		Button3.addActionListener(this);
		Button3.addMouseListener(this);
		Panel.add(Button3);

		Button4 = new JButton("4");
		Button4.setBounds(160,220,100,50);
		Button4.setBackground(Color.BLUE);
		Button4.addActionListener(this);
		Button4.addMouseListener(this);
		Panel.add(Button4);

		Button5 = new JButton("5");
		Button5.setBounds(50,290,100,50);
		Button5.setBackground(Color.BLUE);
		Button5.addActionListener(this);
		Button5.addMouseListener(this);
		Panel.add(Button5);

		Button6 = new JButton("6");
		Button6.setBounds(160,290,100,50);
		Button6.setBackground(Color.BLUE);
		Button6.addActionListener(this);
		Button6.addMouseListener(this);
		Panel.add(Button6);

		Button7 = new JButton("7");
		Button7.setBounds(50,360,100,50);
		Button7.setBackground(Color.BLUE);
		Button7.addActionListener(this);
		Button7.addMouseListener(this);
		Panel.add(Button7);

		Button8 = new JButton("8");
		Button8.setBounds(160,360,100,50);
		Button8.setBackground(Color.BLUE);
		Button8.addActionListener(this);
		Button8.addMouseListener(this);
		Panel.add(Button8);

		Button9 = new JButton("9");
		Button9.setBounds(50,430,100,50);
		Button9.setBackground(Color.BLUE);
		Button9.addActionListener(this);
		Button9.addMouseListener(this);
		Panel.add(Button9);

		Button10 = new JButton("10");
		Button10.setBounds(160,430,100,50);
		Button10.setBackground(Color.BLUE);
		Button10.addActionListener(this);
		Button10.addMouseListener(this);
		Panel.add(Button10);

    this.add(Panel);
  }

  public void actionPerformed(ActionEvent ae)
{
    Color green = new Color(0,255,0);
    Color red   = new Color(255,0,0);
    int rgb1 = green.getRGB();
    int rgb2 = red.getRGB();
    int RGBG = green.getRGB();
    int RGBR = red.getRGB();

    int p,g,b;
	
    Random r = new Random();
	
    p = r.nextInt(255);
    g = r.nextInt(255);
    b = r.nextInt(255);
	
    LabelScoreResult.setForeground(new Color(p,g,b));
	
    if(rgb1==RGBG)
	{
      click++;
    }
    else if (rgb2 == RGBR)
	{
      click--;
    }
    temp =click;
    score = Integer.toString(temp);
    LabelScoreResult.setText(score);
	}
	public void mouseEntered(MouseEvent me)
	{

	}
	public void mouseExited(MouseEvent me)
	{

	}
	public void mouseClicked(MouseEvent me){}
	public void mousePressed(MouseEvent me){}
	public void mouseReleased(MouseEvent me){}

  public void run() 
 {
    int p;
    String str;
    Random r = new Random();
	
       System.out.println("Game has Stopped Running");
	   
       try 
	   {
          for(int i = 0; i <100; i++) 
		  {
            p = r.nextInt(10);
            str = Integer.toString(p);
            if(str.equals("1"))
			{
              Button1.setBackground(Color.green);
              Button1.setText("Click On");
              Thread.sleep(800);
              Button1.setBackground(Color.red);
            }
            else if(str.equals("2"))
			{
              Button2.setBackground(Color.green);
              Button2.setText("Click On");
              Thread.sleep(800);
              Button2.setBackground(Color.red);
            }
            else if(str.equals("3"))
			{
              Button3.setBackground(Color.green);
              Button3.setText("Click On");
              Thread.sleep(800);
              Button3.setBackground(Color.red);
            }
            else if(str.equals("4"))
			{
              Button4.setBackground(Color.green);
              Button4.setText("Click On");
              Thread.sleep(800);
              Button4.setBackground(Color.red);
            }
            else if(str.equals("5"))
			{
              Button5.setBackground(Color.green);
              Button5.setText("Click On");
              Thread.sleep(800);
              Button5.setBackground(Color.red);
            }
            else if(str.equals("6"))
			{
              Button6.setBackground(Color.green);
              Button6.setText("Click On");
              Thread.sleep(800);
              Button6.setBackground(Color.red);
            }
            else if(str.equals("7"))
			{
              Button7.setBackground(Color.green);
              Button7.setText("Click On");
              Thread.sleep(800);
              Button7.setBackground(Color.red);
            }
            else if(str.equals("8"))
			{
              Button8.setBackground(Color.green);
              Button8.setText("Click On");
              Thread.sleep(800);
              Button8.setBackground(Color.red);
            }
            else if(str.equals("9"))
			{
              Button9.setBackground(Color.green);
              Button9.setText("Click On");
              Thread.sleep(800);
              Button9.setBackground(Color.red);
            }
            else if(str.equals("10"))
			{
              Button10.setBackground(Color.green);
              Button10.setText("Click On");
              Thread.sleep(800);
              Button10.setBackground(Color.red);
            }
          }
       }
	   catch (Exception e) 
	   {
          System.out.println(e.getMessage());
       }
    }
}