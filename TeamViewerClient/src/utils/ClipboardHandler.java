package utils;


import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.FlavorListener;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dell
 */
public class ClipboardHandler implements ClipboardOwner{
    private Clipboard clipboard = null;
    private DataFlavor currDataFlavor = null;
    private ClipboardListener pc = null;
    public ClipboardHandler(Clipboard clipboard, ClipboardListener pc){
        this.clipboard = clipboard;
        this.pc = pc;
        currDataFlavor = getDataFlavorForClipboardContent();
        System.out.println("currDataFlavor" + currDataFlavor);
        regainOwnerShip();
    }
    private void setClipboardContent(Transferable content){
        clipboard.setContents(content, this);
        currDataFlavor = getDataFlavorForClipboardContent();
        System.out.println("currDataFlavor" + currDataFlavor);
    }
    public Object getClipboardContent(DataFlavor flavor) throws UnsupportedFlavorException, IOException{
        return clipboard.getData(flavor);
    }
    public void setStringOnClipboard(String content){
        setClipboardContent(new StringSelection(content));
    }
    public void addFlavorListener(FlavorListener fl){
        clipboard.addFlavorListener(fl);
    }
    private void regainOwnerShip(){
        setClipboardContent(clipboard.getContents(this));
    }
    public DataFlavor getDataFlavorForClipboardContent(){
        if(clipboard.getContents(this).isDataFlavorSupported(DataFlavor.stringFlavor)){
            return DataFlavor.stringFlavor;
        }
        else if(clipboard.getContents(this).isDataFlavorSupported(DataFlavor.imageFlavor)){
            return DataFlavor.imageFlavor;
        }
        else if(clipboard.getContents(this).isDataFlavorSupported(DataFlavor.javaFileListFlavor)){
            return DataFlavor.javaFileListFlavor;
        }
        else if(clipboard.getContents(this).isDataFlavorSupported(DataFlavor.allHtmlFlavor)){
            return DataFlavor.allHtmlFlavor;
        }
        return null;
    }
    public DataFlavor getCurrDataFlavor(){
        return currDataFlavor;
    }

    @Override
    public void lostOwnership(Clipboard clipboard, Transferable contents) {
        try {
            Thread.sleep(100);
        } catch (Exception e) {
            System.out.println("e" + e);
        }
        regainOwnerShip();
        pc.contentChanged();
    }
}