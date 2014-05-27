

import javax.swing.*;

import java.awt.*;

import java.awt.event.*;

import java.io.DataInputStream;

import java.io.DataOutputStream;

import java.io.IOException;

import java.net.Socket;

class Authenticate extends JFrame implements ActionListener{
			private Socket cSocket = null;
			DataOutputStream psswrchk = null;
			DataInputStream verification = null;
			String verify ="";
			JButton SUBMIT;
			JPanel panel;
			JLabel label, label1;
			String width="",height="";
			final JTextField text1;

Authenticate(Socket cSocket){
				label1=new JLabel();
				label1.setText("Password");
				text1 = new JTextField(15);
				this.cSocket = cSocket;
		
				label=new JLabel();
				label.setText("");
				this.setLayout(new BorderLayout());
		
				SUBMIT = new JButton("SUBMIT");

				panel=new JPanel(new GridLayout(2,1));
				panel.add(label1);
				panel.add(text1);
				panel.add(label);
				panel.add(SUBMIT);
				add(panel,BorderLayout.CENTER);
				SUBMIT.addActionListener(this);
				setTitle("LOGIN FORM");
				}


public void actionPerformed(ActionEvent ae){


				String value1=text1.getText();
				
				try{
				psswrchk= new DataOutputStream(cSocket.getOutputStream());
				verification= new DataInputStream(cSocket.getInputStream());
				psswrchk.writeUTF(value1);
				verify=verification.readUTF();
	
				}catch (IOException e){
				e.printStackTrace();
				}

				if(verify.equals("valid")){
				try{
				width = verification.readUTF();
				height = verification.readUTF();
		
				}catch (IOException e){
				e.printStackTrace();		
				}
				CreateFrame abc= new CreateFrame(cSocket,width,height);
				dispose();
				}
				else {
				System.out.println("enter the valid password");
				JOptionPane.showMessageDialog(this, "Incorrect  password", "Error", JOptionPane.ERROR_MESSAGE);
				dispose();
				}

				}
			
	}

