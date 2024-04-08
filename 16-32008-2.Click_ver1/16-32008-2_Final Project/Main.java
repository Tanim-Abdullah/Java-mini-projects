import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.TitledBorder;
//import javax.swing.event.ChangeEvent;
//import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.ListIterator;



public class Main extends JFrame implements MouseListener
{
	private static final long serialVersionUID = 1L;
	private static King wk,bk;
	private static Queen wq,bq; 
	private static Rook wr1,wr2,br1,br2;
	private static Knight wk1,wk2,bk1,bk2;
	private static Bishop wb1,wb2,bb1,bb2;
	private static Pawn wp[],bp[]; /// we need 8 for white and black so total 16 
	private Cell c,previous;
	private int chance=0; /// for check movements
	private Cell boardState[][];
	private ArrayList<Cell> destinationlist = new ArrayList<Cell>();
	private Player White=null,Black=null;
	private JPanel board=new JPanel(new GridLayout(8,8));
	private JPanel wdetails=new JPanel(new GridLayout(3,3));
	private JPanel bdetails=new JPanel(new GridLayout(3,3));
	private JPanel wcombopanel=new JPanel(); // combo panel
	private JPanel bcombopanel=new JPanel(); // combo panel
	private JPanel controlPanel,WhitePlayer,BlackPlayer,temp,displayTime,showPlayer,time;
	private JSplitPane split;
	private JLabel headtitle,label,mov;
	private static JLabel CHNC;
	private Time timer;
	public static Main Mainboard;
	private boolean selected=false,end=false;
	private Container content;
	private ArrayList<Player> wplayer,bplayer;
	private ArrayList<String> Wnames=new ArrayList<String>();
	private ArrayList<String> Bnames=new ArrayList<String>();
	private JComboBox<String> wcombo,bcombo;
	private String wname=null,bname=null,winner=null;
	static String move;
	private Player tempPlayer;
	private JScrollPane wscroll,bscroll;
	private String[] WNames={},BNames={};
	private JSlider timeSlider;
	private BufferedImage image;
	private Button start,wselect,bselect,WNewPlayer,BNewPlayer;
	public static int timeRemaining=60;
	
	
	public static void main(String[] args)
{
	wr1=new Rook("WR1","White_Rook.png",0);
	wr2=new Rook("WR2","White_Rook.png",0);
	br1=new Rook("BR1","Black_Rook.png",1);
	br2=new Rook("BR2","Black_Rook.png",1);
	wk1=new Knight("WK1","White_Knight.png",0);
	wk2=new Knight("WK2","White_Knight.png",0);
	bk1=new Knight("BK1","Black_Knight.png",1);
	bk2=new Knight("BK2","Black_Knight.png",1);
	wb1=new Bishop("WB1","White_Bishop.png",0);
	wb2=new Bishop("WB2","White_Bishop.png",0);
	bb1=new Bishop("BB1","Black_Bishop.png",1);
	bb2=new Bishop("BB2","Black_Bishop.png",1);
	wq=new Queen("WQ","White_Queen.png",0);
	bq=new Queen("BQ","Black_Queen.png",1);
	wk=new King("WK","White_King.png",0,7,3);
	bk=new King("BK","Black_King.png",1,0,3);
	wp=new Pawn[8];
	bp=new Pawn[8];
	
	
	for(int i=0;i<8;i++)
	{
		wp[i]=new Pawn("WP0"+(i+1),"White_Pawn.png",0);
		bp[i]=new Pawn("BP0"+(i+1),"Black_Pawn.png",1);
	}
	
	Mainboard = new Main();
	Mainboard.setVisible(true);	
	Mainboard.setResizable(false);
	}
	
	private Main()
    {
		timeRemaining=60;
		timeSlider = new JSlider();
		move="White";
		wname=null;
		bname=null;
		winner=null;
		board=new JPanel(new GridLayout(8,8));
		wdetails=new JPanel(new GridLayout(3,3));
		bdetails=new JPanel(new GridLayout(3,3));
		bcombopanel=new JPanel(); // combo panel
		wcombopanel=new JPanel(); // combo panel
		Wnames=new ArrayList<String>();
		Bnames=new ArrayList<String>();
		board.setMinimumSize(new Dimension(800,700));
		ImageIcon img = new ImageIcon(this.getClass().getResource("icon.png"));
		this.setIconImage(img.getImage());
		
		timeSlider.setMinimum(1);
		timeSlider.setMaximum(15);
		timeSlider.setValue(1);
		timeSlider.setMajorTickSpacing(2);
		timeSlider.setPaintLabels(true);
		timeSlider.setPaintTicks(true);
		timeSlider.addChangeListener(new TimeChange());
		
		wplayer= Player.fetch_players();
		Iterator<Player> witr=wplayer.iterator();
		while(witr.hasNext())
			Wnames.add(witr.next().name());
				
		bplayer= Player.fetch_players();
		Iterator<Player> bitr=bplayer.iterator();
		while(bitr.hasNext())
			Bnames.add(bitr.next().name());
	    WNames=Wnames.toArray(WNames);	
		BNames=Bnames.toArray(BNames);
		
		Cell cell;
		board.setBorder(BorderFactory.createLoweredBevelBorder());
		Piece P;
		content=getContentPane();
		setSize(1110,700);
		setTitle("ZARIF and TANIM PROJECT Game CHESS ");
		content.setBackground(Color.red);
		controlPanel=new JPanel();
		
		WhitePlayer=new JPanel();
		WhitePlayer.setBorder(BorderFactory.createTitledBorder(null, "WHITE", TitledBorder.RIGHT,TitledBorder.CENTER, new Font("Times New Roman",Font.BOLD,18), Color.BLUE));
		WhitePlayer.setLayout(new BorderLayout());
		
		BlackPlayer=new JPanel();
		BlackPlayer.setBorder(BorderFactory.createTitledBorder(null, "BLACK", TitledBorder.LEFT,TitledBorder.CENTER, new Font("Times New Roman",Font.BOLD,18), Color.BLUE));
	    BlackPlayer.setLayout(new BorderLayout());
		
	    JPanel whitestats=new JPanel(new GridLayout(3,3));  /// player access
		JPanel blackstats=new JPanel(new GridLayout(3,3)); /// player access
		//wcombo=new JComboBox<String>(WNames);
		//bcombo=new JComboBox<String>(BNames);
		//wscroll=new JScrollPane(wcombo); // combo panel scroll
		//bscroll=new JScrollPane(bcombo); // combo panel scroll
		//wcombopanel.setLayout(new FlowLayout()); // combo panel layout
		//bcombopanel.setLayout(new FlowLayout()); // combo panel layout
		wselect=new Button("SELECT");
		bselect=new Button("SELECT");
		wselect.addActionListener(new SelectHandler(0));
		bselect.addActionListener(new SelectHandler(1));
		WNewPlayer=new Button("NEW PLAYER");
		BNewPlayer=new Button("NEW PLAYER");
		WNewPlayer.addActionListener(new Handler(0));
		BNewPlayer.addActionListener(new Handler(1));
		//wcombopanel.add(wscroll);
		//wcombopanel.add(wselect);
		wcombopanel.add(WNewPlayer);
		//bcombopanel.add(bscroll);
		//bcombopanel.add(bselect);
		bcombopanel.add(BNewPlayer);
		WhitePlayer.add(wcombopanel,BorderLayout.NORTH);
		BlackPlayer.add(bcombopanel,BorderLayout.NORTH);
		whitestats.add(new JLabel("NAME   :"));
		//whitestats.add(new JLabel("PLAYED :"));
		//whitestats.add(new JLabel("WON    :"));
		blackstats.add(new JLabel("NAME   :"));
		//blackstats.add(new JLabel("PLAYED :"));
		//blackstats.add(new JLabel("WON    :"));
		//WhitePlayer.add(whitestats,BorderLayout.WEST);
		//BlackPlayer.add(blackstats,BorderLayout.WEST);
		controlPanel.add(WhitePlayer);
		controlPanel.add(BlackPlayer);
		
		boardState=new Cell[8][8]; /// cell combinations 
		for(int i=0;i<8;i++)
			for(int j=0;j<8;j++)
			{	
				P=null;
				if(i==0&&j==0)
					P=br1;
				else if(i==0&&j==7)
					P=br2;
				else if(i==7&&j==0)
					P=wr1;
				else if(i==7&&j==7)
					P=wr2;
				else if(i==0&&j==1)
					P=bk1;
				else if (i==0&&j==6)
					P=bk2;
				else if(i==7&&j==1)
					P=wk1;
				else if (i==7&&j==6)
					P=wk2;
				else if(i==0&&j==2)
					P=bb1;
				else if (i==0&&j==5)
					P=bb2;
				else if(i==7&&j==2)
					P=wb1;
				else if(i==7&&j==5)
					P=wb2;
				else if(i==0&&j==3)
					P=bk;
				else if(i==0&&j==4)
					P=bq;
				else if(i==7&&j==3)
					P=wk;
				else if(i==7&&j==4)
					P=wq;
				else if(i==1)
				P=bp[j];
				else if(i==6)
					P=wp[j];
				cell=new Cell(i,j,P);
				cell.addMouseListener(this);
				board.add(cell);
				boardState[i][j]=cell;
			}
			
		showPlayer=new JPanel(new FlowLayout());  
		showPlayer.add(timeSlider);
		JLabel setTime=new JLabel("SET TIMER"); 
		start=new Button("START");
		start.setBackground(Color.GREEN);
		start.setForeground(Color.BLUE);
		start.setFont(new Font("Times New Roman",Font.BOLD,16));
	    start.addActionListener(new START());
		start.setPreferredSize(new Dimension(120,40));
		setTime.setFont(new Font("Times New Roman",Font.BOLD,16));
		label = new JLabel("Times New Roman", JLabel.CENTER);
		  label.setFont(new Font("Times New Roman", Font.BOLD, 30));
	      displayTime=new JPanel(new FlowLayout());
	      time=new JPanel(new GridLayout(3,3));
	      time.add(setTime);
	      time.add(showPlayer);
	      displayTime.add(start);
	      time.add(displayTime);
	      controlPanel.add(time);
		board.setMinimumSize(new Dimension(800,700));
		
		
		temp=new JPanel(){
			private static final long serialVersionUID = 1L;
			     
			@Override
		    public void paintComponent(Graphics g) 
			{
				  try 
				{
			          image = ImageIO.read(this.getClass().getResource("clash.jpg"));
			    } catch (IOException ex) 
				{
			            System.out.println("NOT Found");
			    }
			   
				g.drawImage(image, 0, 0, null);
			}         
	    };

		temp.setMinimumSize(new Dimension(800,700));
		controlPanel.setMinimumSize(new Dimension(285,700));
		split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,temp, controlPanel);
		
	    content.add(split);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
	
	
	public void changechance() /// turns for white and black and switch 
	{
		if (boardState[getKing(chance).getx()][getKing(chance).gety()].ischeck())
		{
			chance^=1;
			gameend();
		}
		if(destinationlist.isEmpty()==false) 
			cleandestinations(destinationlist);
		if(previous!=null)
			previous.deselect();
		previous=null;
		chance^=1;
		if(!end && timer!=null)
		{
			timer.reset();
			timer.start();
			showPlayer.remove(CHNC);
			if(Main.move=="White")
				Main.move="Black";
			else
				Main.move="White";
			CHNC.setText(Main.move);
			showPlayer.add(CHNC);
		}
	}
	
	
	private King getKing(int color)
	{
		if (color==0)
			return wk;
		else
			return bk;
	}
	
	
    private void cleandestinations(ArrayList<Cell> destlist)      
    {
    	ListIterator<Cell> it = destlist.listIterator();
    	while(it.hasNext())
    		it.next().removepossibledestination();
    }
    
    
    private void highlightdestinations(ArrayList<Cell> destlist)
    {
    	ListIterator<Cell> it = destlist.listIterator();
    	while(it.hasNext())
    		it.next().setpossibledestination();
    }
    
    
  
    private boolean willkingbeindanger(Cell fromcell,Cell tocell)
    {
    	Cell newboardstate[][] = new Cell[8][8];
    	for(int i=0;i<8;i++)
    		for(int j=0;j<8;j++)
    		{	try { newboardstate[i][j] = new Cell(boardState[i][j]);} catch (CloneNotSupportedException e){e.printStackTrace(); System.out.println("There is a problem with cloning !!"); }}
    	
    	if(newboardstate[tocell.x][tocell.y].getpiece()!=null)
			newboardstate[tocell.x][tocell.y].removePiece();
    	
		newboardstate[tocell.x][tocell.y].setPiece(newboardstate[fromcell.x][fromcell.y].getpiece());
		if(newboardstate[tocell.x][tocell.y].getpiece() instanceof King)
		{
			((King)(newboardstate[tocell.x][tocell.y].getpiece())).setx(tocell.x);
			((King)(newboardstate[tocell.x][tocell.y].getpiece())).sety(tocell.y);
		}
		newboardstate[fromcell.x][fromcell.y].removePiece();
		if (((King)(newboardstate[getKing(chance).getx()][getKing(chance).gety()].getpiece())).isindanger(newboardstate)==true)
			return true;
		else
			return false;
    }
    
    
    private ArrayList<Cell> filterdestination (ArrayList<Cell> destlist, Cell fromcell)
    {
    	ArrayList<Cell> newlist = new ArrayList<Cell>();
    	Cell newboardstate[][] = new Cell[8][8];
    	ListIterator<Cell> it = destlist.listIterator();
    	int x,y;
    	while (it.hasNext())
    	{
    		for(int i=0;i<8;i++)
        		for(int j=0;j<8;j++)
        		{	try { newboardstate[i][j] = new Cell(boardState[i][j]);} catch (CloneNotSupportedException e){e.printStackTrace();}}
    		
    		Cell tempc = it.next();
    		if(newboardstate[tempc.x][tempc.y].getpiece()!=null)
    			newboardstate[tempc.x][tempc.y].removePiece();
    		newboardstate[tempc.x][tempc.y].setPiece(newboardstate[fromcell.x][fromcell.y].getpiece());
    		x=getKing(chance).getx();
    		y=getKing(chance).gety();
    		if(newboardstate[fromcell.x][fromcell.y].getpiece() instanceof King)
    		{
    			((King)(newboardstate[tempc.x][tempc.y].getpiece())).setx(tempc.x);
    			((King)(newboardstate[tempc.x][tempc.y].getpiece())).sety(tempc.y);
    			x=tempc.x;
    			y=tempc.y;
    		}
    		newboardstate[fromcell.x][fromcell.y].removePiece();
    		if ((((King)(newboardstate[x][y].getpiece())).isindanger(newboardstate)==false))
    			newlist.add(tempc);
    	}
    	return newlist;
    }
    
     
    private ArrayList<Cell> incheckfilter (ArrayList<Cell> destlist, Cell fromcell, int color)
    {
    	ArrayList<Cell> newlist = new ArrayList<Cell>();
    	Cell newboardstate[][] = new Cell[8][8];
    	ListIterator<Cell> it = destlist.listIterator();
    	int x,y;
    	while (it.hasNext())
    	{
    		for(int i=0;i<8;i++)
        		for(int j=0;j<8;j++)
        		{	try { newboardstate[i][j] = new Cell(boardState[i][j]);} catch (CloneNotSupportedException e){e.printStackTrace();}}
    		Cell tempc = it.next();
    		if(newboardstate[tempc.x][tempc.y].getpiece()!=null)
    			newboardstate[tempc.x][tempc.y].removePiece();
    		newboardstate[tempc.x][tempc.y].setPiece(newboardstate[fromcell.x][fromcell.y].getpiece());
    		x=getKing(color).getx();
    		y=getKing(color).gety();
    		if(newboardstate[tempc.x][tempc.y].getpiece() instanceof King)
    		{
    			((King)(newboardstate[tempc.x][tempc.y].getpiece())).setx(tempc.x);
    			((King)(newboardstate[tempc.x][tempc.y].getpiece())).sety(tempc.y);
    			x=tempc.x;
    			y=tempc.y;
    		}
    		newboardstate[fromcell.x][fromcell.y].removePiece();
    		if ((((King)(newboardstate[x][y].getpiece())).isindanger(newboardstate)==false))
    			newlist.add(tempc);
    	}
    	return newlist;
    }
    
    
    public boolean checkmate(int color)  /// finishing the game (winning)
    {
    	ArrayList<Cell> dlist = new ArrayList<Cell>();
    	for(int i=0;i<8;i++)
    	{
    		for(int j=0;j<8;j++)
    		{
    			if (boardState[i][j].getpiece()!=null && boardState[i][j].getpiece().getcolor()==color)
    			{
    				dlist.clear();
    				dlist=boardState[i][j].getpiece().move(boardState, i, j);
    				dlist=incheckfilter(dlist,boardState[i][j],color);
    				if(dlist.size()!=0)
    					return false;
    			}
    		}
    	}
    	return true;
    }
	
    
    @SuppressWarnings("deprecation") /// for overwriting 
	private void gameend()
    {
    	cleandestinations(destinationlist);
    	displayTime.disable();
    	timer.countdownTimer.stop();
    	if(previous!=null)
    		previous.removePiece();
    	if(chance==0)
		{	//White.updateGamesWon();
			//White.Update_Player();
			winner=White.name();
		}
		else
		{
			//Black.updateGamesWon();
			//Black.Update_Player();
			winner=Black.name();
		}
		JOptionPane.showMessageDialog(board,"Checkmate!!!\n"+winner+" wins");
		WhitePlayer.remove(wdetails);
		BlackPlayer.remove(bdetails);
		displayTime.remove(label);
		
		displayTime.add(start);
		showPlayer.remove(mov);
		showPlayer.remove(CHNC);
		showPlayer.revalidate();
		showPlayer.add(timeSlider);
		
		split.remove(board);
		split.add(temp);
		WNewPlayer.enable();
		BNewPlayer.enable();
		wselect.enable();
		bselect.enable();
		end=true;
		Mainboard.disable();
		Mainboard.dispose();
		Mainboard = new Main();
		Mainboard.setVisible(true);
		Mainboard.setResizable(false);
    }
    
    
	@Override
	public void mouseClicked(MouseEvent arg0){
		
		c=(Cell)arg0.getSource();
		if (previous==null)
		{
			if(c.getpiece()!=null)
			{
				if(c.getpiece().getcolor()!=chance)
					return;
				c.select();
				previous=c;
				destinationlist.clear();
				destinationlist=c.getpiece().move(boardState, c.x, c.y);
				if(c.getpiece() instanceof King)
					destinationlist=filterdestination(destinationlist,c);
				else
				{
					if(boardState[getKing(chance).getx()][getKing(chance).gety()].ischeck())
						destinationlist = new ArrayList<Cell>(filterdestination(destinationlist,c));
					else if(destinationlist.isEmpty()==false && willkingbeindanger(c,destinationlist.get(0)))
						destinationlist.clear();
				}
				highlightdestinations(destinationlist);
			}
		}
		else
		{
			if(c.x==previous.x && c.y==previous.y)
			{
				c.deselect();
				cleandestinations(destinationlist);
				destinationlist.clear();
				previous=null;
			}
			else if(c.getpiece()==null||previous.getpiece().getcolor()!=c.getpiece().getcolor())
			{
				if(c.ispossibledestination())
				{
					if(c.getpiece()!=null)
						c.removePiece();
					c.setPiece(previous.getpiece());
					if (previous.ischeck())
						previous.removecheck();
					previous.removePiece();
					if(getKing(chance^1).isindanger(boardState))
					{
						boardState[getKing(chance^1).getx()][getKing(chance^1).gety()].setcheck();
						if (checkmate(getKing(chance^1).getcolor()))
						{
							previous.deselect();
							if(previous.getpiece()!=null)
								previous.removePiece();
							gameend();
						}
					}
					if(getKing(chance).isindanger(boardState)==false)
						boardState[getKing(chance).getx()][getKing(chance).gety()].removecheck();
					if(c.getpiece() instanceof King)
					{
						((King)c.getpiece()).setx(c.x);
						((King)c.getpiece()).sety(c.y);
					}
					changechance();
					if(!end)
					{
						timer.reset();
						timer.start();
					}
				}
				if(previous!=null)
				{
					previous.deselect();
					previous=null;
				}
				cleandestinations(destinationlist);
				destinationlist.clear();
			}
			else if(previous.getpiece().getcolor()==c.getpiece().getcolor())
			{
				previous.deselect();
				cleandestinations(destinationlist);
				destinationlist.clear();
				c.select();
				previous=c;
				destinationlist=c.getpiece().move(boardState, c.x, c.y);
				if(c.getpiece() instanceof King)
					destinationlist=filterdestination(destinationlist,c);
				else
				{
					if(boardState[getKing(chance).getx()][getKing(chance).gety()].ischeck())
						destinationlist = new ArrayList<Cell>(filterdestination(destinationlist,c));
					else if(destinationlist.isEmpty()==false && willkingbeindanger(c,destinationlist.get(0)))
						destinationlist.clear();
				}
				highlightdestinations(destinationlist);
			}
		}
		if(c.getpiece()!=null && c.getpiece() instanceof King)
		{
			((King)c.getpiece()).setx(c.x);
			((King)c.getpiece()).sety(c.y);
		}
	}
    
    
	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
			
	}
	
	
	class START implements ActionListener
	{

	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		/*
		if(White==null||Black==null)
			{JOptionPane.showMessageDialog(controlPanel, "Fill in the Details");
			return;}
			*/
		//White.updateGamesPlayed();
		//White.Update_Player();
		//Black.updateGamesPlayed();
		//Black.Update_Player();
		WNewPlayer.disable();
		BNewPlayer.disable();
		wselect.disable();
		bselect.disable();
		split.remove(temp);
		split.add(board);
		showPlayer.remove(timeSlider);
		mov=new JLabel("Move:");
		mov.setFont(new Font("Times New Roman",Font.PLAIN,20));
		mov.setForeground(Color.red);
		showPlayer.add(mov);
		CHNC=new JLabel(move);
		CHNC.setFont(new Font("Times New Roman",Font.BOLD,20));
		CHNC.setForeground(Color.blue);
		showPlayer.add(CHNC);
		displayTime.remove(start);
		displayTime.add(label);
		timer=new Time(label);
		timer.start();
	}
	}
	
	class TimeChange implements ChangeListener
	{
		@Override
		public void stateChanged(ChangeEvent arg0)
		{
			timeRemaining=timeSlider.getValue()*60;
		}
	}
	
	
	class SelectHandler implements ActionListener
	{
		private int color;
		
		SelectHandler(int i)
		{
			color=i;
		}
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				
				tempPlayer=null;
				String n=(color==0)?wname:bname;
				JComboBox<String> jc=(color==0)?wcombo:bcombo;
				JComboBox<String> ojc=(color==0)?bcombo:wcombo;
				ArrayList<Player> pl=(color==0)?wplayer:bplayer;
				
				/*
				ArrayList<Player> opl=Player.fetch_players();
				if(opl.isEmpty())
					return;
				JPanel det=(color==0)?wdetails:bdetails;
				JPanel PL=(color==0)?WhitePlayer:BlackPlayer; 
				if(selected==true)
					det.removeAll();
				n=(String)jc.getSelectedItem();
				Iterator<Player> it=pl.iterator();
				Iterator<Player> oit=opl.iterator();
				while(it.hasNext())
				{	
					Player p=it.next();
					if(p.name().equals(n))
						{tempPlayer=p;
						break;}
				}
				while(oit.hasNext())
				{	
					Player p=oit.next();
					if(p.name().equals(n))
						{opl.remove(p);
						break;}
				}
				
				if(tempPlayer==null)
					return;
				if(color==0)
					White=tempPlayer;
				else
					Black=tempPlayer;
				bplayer=opl;
				ojc.removeAllItems();
				for (Player s:opl)
					ojc.addItem(s.name());
				det.add(new JLabel(" "+tempPlayer.name()));
				//det.add(new JLabel(" "+tempPlayer.gamesplayed()));
				//det.add(new JLabel(" "+tempPlayer.gameswon()));
				
				PL.revalidate();
				PL.repaint();
				PL.add(det);
				*/
				selected=true;
			}
			
		}
		
		
		
		class Handler implements ActionListener{
			private int color;
			Handler(int i)
			{
				color=i;
			}
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String n=(color==0)?wname:bname;
				JPanel j=(color==0)?WhitePlayer:BlackPlayer;
				//ArrayList<Player> N=Player.fetch_players();
				//Iterator<Player> it=N.iterator();
				JPanel det=(color==0)?wdetails:bdetails;
				n=JOptionPane.showInputDialog(j,"ENTER YOUR NAME");
				
					if(n!=null)
					{
					/*
					while(it.hasNext())
					{
						if(it.next().name().equals(n))
						{JOptionPane.showMessageDialog(j,"Player Exists");
						return;}
					}
					*/
						if(n.length()!=0)
						{Player tem=new Player(n);
						//tem.Update_Player();
						if(color==0)
							White=tem;
						else
							Black=tem;
						}
						else return;
					}
				else
					return;
				det.removeAll();
				det.add(new JLabel(" "+n));
				//det.add(new JLabel(" 0"));
				//det.add(new JLabel(" 0"));
				j.revalidate();
				j.repaint();
				j.add(det);
				selected=true;
			}
			}	 
}