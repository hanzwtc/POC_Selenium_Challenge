package Utils;


import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;


public class WordHelper {

    public static XWPFDocument document = null;
    public static XWPFParagraph paragraphTitle = null;
    public static XWPFRun runTitle = null;

    public static XWPFParagraph paragraphNormal = null;
    public static XWPFRun runNormal = null;

    public static void CreateWord() {

            try{

                 document = new XWPFDocument();
                 paragraphTitle = document.createParagraph();
                 runTitle = paragraphTitle.createRun();


                runTitle.setText("EVIDENCIA DE PRUEBA AUTOMATIZADA - INTERSEGURO VEHICULAR");
                runTitle.setFontFamily("Arial Black");
                runTitle.setUnderline(UnderlinePatterns.DOUBLE);
                paragraphTitle.setAlignment(ParagraphAlignment.CENTER);

                runTitle.addBreak();
                runTitle.addBreak();

            }catch(Exception e){
                e.printStackTrace();
            }

    }



    public static void WordWriter(String Message,String Color,boolean Screenshot) {

        try{

             paragraphNormal = document.createParagraph();
             runNormal = paragraphNormal.createRun();

            paragraphNormal.setNumID(BigInteger.ONE);
            runNormal.setText(Message);
            switch (Color.trim().toUpperCase()) {
                case "R":
                    runNormal.setColor("ff0000");
                    break;
                case "G":
                    runNormal.setColor("00ff45");
                    break;
                case "B":
                    runNormal.setColor("0900ff");
                    break;
                default:
                    runNormal.setColor("000000");
                    break;
            }

            runNormal.addBreak();
        if (Screenshot){

            String imgFile = SeleniumHelper.takeScreenshot(Message);
            int format = XWPFDocument.PICTURE_TYPE_PNG;
            runNormal.addPicture(new FileInputStream(imgFile),format,imgFile,Units.toEMU(450), Units.toEMU(230));
            //runNormal.addPicture(new FileInputStream(imgFile),format,imgFile, Units.toEMU(150), Units.toEMU(250));

            runNormal.addBreak();
            runNormal.addBreak();

            paragraphNormal.setAlignment(ParagraphAlignment.LEFT);

        }


        }catch(Exception e){
            e.printStackTrace();
        }

    }


    public static void CloseWord(){
        try {

            SimpleDateFormat sdf = new SimpleDateFormat("ddMMYYYY_hhmmss");
            Date curDate = new Date();
            String strDate = sdf.format(curDate);

            String FilePath = System.getProperty("user.dir") + "//src//test-output//Results//EvidenciaWord//" + strDate + ".docx";

            //String FilePath = System.getProperty("user.dir")+"//src//test-output//Results//EvidenciaWord//"+strDate+".pdf";
            FileOutputStream output = new FileOutputStream(FilePath);
            document.write(output);
            output.close();
            document.close();
            document = null;
/////////////////////////////////////////////////

/////////////////////////////////////////////////


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
