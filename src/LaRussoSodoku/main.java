/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LaRussoSodoku;

/**
 *
 * @author Nikon
 */
public class main
{

    public static void main(String[] args)
    {
        String input = "435" + "269" + "781"+
                       "682" + "571" + "493"+
                       "197" + "834" + "562"+
                
                       "826" + "195" + "347"+
                       "374" + "682" + "915"+
                       "951" + "743" + "628"+
                
                       "519" + "326" + "874"+
                       "248" + "957" + "136"+
                       "763" + "418" + "259";
        
        String input2 ="700" + "000" + "009"+
                       "000" + "200" + "600"+
                       "020" + "095" + "040"+
                
                       "300" + "400" + "100"+
                       "016" + "000" + "200"+
                       "000" + "006" + "050"+
                
                       "050" + "040" + "000"+
                       "008" + "020" + "900"+
                       "900" + "003" + "004";
        
        sudoBoard temp = new sudoBoard(input2);
       
        //temp.bruteSolve();
        temp.smartSolve();
       // System.out.println(temp.smartSolve());
        //System.out.printf(temp.toString());
    }

}
