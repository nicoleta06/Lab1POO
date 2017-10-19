/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

/**
 *
 * @author Andreea
 */
public class Message {
    private static final long serialVersionUID = 1L;

  
    private String mSender;
    private String mContent;
    
    public Message(String Sender, String Content){
        mSender = Sender;
        mContent = Content;
    }
    @Override
    public String toString(){
        return mSender+ ":" + mContent;
    }
}
