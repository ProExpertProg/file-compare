/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package file.compare;

import java.awt.Font;
import java.awt.FontMetrics;

/**
 *
 * @author Luka
 */
public class MyFunctions {

    public static void centerTitle(java.awt.Frame frame, String titleText) {
        Font f = frame.getFont();
        FontMetrics fm = frame.getFontMetrics(f);
        int x = fm.stringWidth("Hello Center");
        int y = fm.stringWidth(" ");
        int z = frame.getWidth() / 2 - (x / 2);
        int w = z / y;
        String pad = "";
        pad = String.format("%" + w + "s", pad);
        frame.setTitle(pad + titleText);
    }
    
    public static void centerTitle(java.awt.Dialog frame, String titleText) {
        Font f = frame.getFont();
        FontMetrics fm = frame.getFontMetrics(f);
        int x = fm.stringWidth("Hello Center");
        int y = fm.stringWidth(" ");
        int z = frame.getWidth() / 2 - (x / 2);
        int w = z / y;
        String pad = "";
        pad = String.format("%" + w + "s", pad);
        frame.setTitle(pad + titleText);
    }
}
