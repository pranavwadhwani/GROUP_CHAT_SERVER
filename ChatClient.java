/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Pranav
 */
import java.net.*;
import java.io.*;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class ChatClient implements Runnable{
    
    Socket SOCK;
    Scanner INPUT;
    Scanner SEND = new Scanner(System.in);
    PrintWriter OUT;

    public ChatClient(Socket X) 
    {
        this.SOCK = X;
    }
    
    @Override
    public void run()
    {
        try
        {
            try
            {
                INPUT = new Scanner(SOCK.getInputStream());
                OUT = new PrintWriter(SOCK.getOutputStream());
                OUT.flush();
                CheckStream();
            }
            finally
            {
                SOCK.close();
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    
    public void DISCONNECT() throws IOException
    {
        OUT.println(ClientGui.UserName + " has disconnected.");
        OUT.flush();
        SOCK.close();
        JOptionPane.showMessageDialog(null, "You disconnected");
        System.exit(0);
    }
    
    public void CheckStream()
    {
        while(true)
        {
            RECEIVE();
        }
    }
    
    public void RECEIVE()
    {
        if(INPUT.hasNext())
        {
            String MESSAGE = INPUT.nextLine();
            
            if(MESSAGE.contains("#?!"))
            {
                String TEMP1 = MESSAGE.substring(3);
                TEMP1 = TEMP1.replace("[", "");
                TEMP1 = TEMP1.replace("]", "");
                
                String[] CurrentUsers = TEMP1.split(", ");
                
                ClientGui.JL_ONLINE.setListData(CurrentUsers);
            }
            else
            {
                ClientGui.TA_CONVERSATION.append(MESSAGE + "\n");
            }
        }
    }
    
    public void SEND(String x)
    {
        OUT.println(ClientGui.UserName + ": " + x);
        OUT.flush();
        ClientGui.TF_Message.setText("");
    }
    
    
    
}

