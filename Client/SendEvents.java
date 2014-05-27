

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JPanel;


class SendEvents implements KeyListener, MouseMotionListener, MouseListener{
	private Socket cSocket = null;
	private JPanel cPanel = null;
	private PrintWriter writer = null;
	String width = "", height = "";
	double w;
	double h;

	SendEvents(Socket s, JPanel p, String width, String height){
		cSocket = s;
		cPanel = p;
		this.width = width;
		this.height = height;
		w = Double.valueOf(width.trim()).doubleValue();
		h = Double.valueOf(width.trim()).doubleValue();

		//Associate event listeners to the panel

		cPanel.addKeyListener(this);
		cPanel.addMouseListener(this);
		cPanel.addMouseMotionListener(this);

		try{
			//Prepare PrintWriter which will be used to send commands to the client
			writer = new PrintWriter(cSocket.getOutputStream());
			} catch(IOException ex) {
			ex.printStackTrace();
		}
	}

	public void mouseDragged(MouseEvent e){
	}

	public void mouseMoved(MouseEvent e){
		double xScale = (double)w/cPanel.getWidth();
		double yScale = (double)h/cPanel.getHeight();
		writer.println(Commands.MOVE_MOUSE.getAbbrev());
		writer.println((int)(e.getX()*xScale));
		writer.println((int)(e.getY()*yScale));
		writer.flush();
	}

	public void mouseClicked(MouseEvent e){
	}

	public void mousePressed(MouseEvent e){
		writer.println(Commands.PRESS_MOUSE.getAbbrev());
		int button = e.getButton();
		int xButton = 16;
		if(button==3){
			xButton = 4;
		}
		writer.println(xButton);
		writer.flush();
	}

	public void mouseReleased(MouseEvent e){
		writer.println(Commands.RELEASE_MOUSE.getAbbrev());
		int button = e.getButton();
		int xButton = 16;
		if(button==3){
			xButton = 4;
		}
		writer.println(xButton);
		writer.flush();
	}

	public void mouseEntered(MouseEvent e){
	}

	public void mouseExited(MouseEvent e){
	}

	public void keyTyped(KeyEvent e){
	}

	public void keyPressed(KeyEvent e){
		writer.println(Commands.PRESS_KEY.getAbbrev());
		writer.println(e.getKeyCode());
		writer.flush();
	}

	public void keyReleased(KeyEvent e){
		writer.println(Commands.RELEASE_KEY.getAbbrev());
		writer.println(e.getKeyCode());
		writer.flush();
	}
}
