/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package file.compare;

import java.util.logging.Logger;

/**
 *
 * @author Luka
 */
public class Settings {

    /**
     *
     * @param acfc
     * alwaysCheckFileContent
     */
    public Settings(boolean acfc) {
        alwaysCheckFileContent = acfc;
    }
    public boolean alwaysCheckFileContent;

    public Settings() {
        alwaysCheckFileContent = false;
    }

    @Override
    public String toString() {
        return "Settings{" + "alwaysCheckFileContent=" + alwaysCheckFileContent + '}';
    }
    
    
    
}
