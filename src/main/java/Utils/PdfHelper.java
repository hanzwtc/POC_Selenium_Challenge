package Utils;

import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.parser.PdfImageObject;
import org.openqa.selenium.ScreenOrientation;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PdfHelper {
    public static float FACTOR = 10f;
    public static Document doc = null;
    private static String FilePath ="";
    public static ConfigFileReader configFileReader;


    public static void CreatePDF(String Titulo){

        try{

                SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy_hhmmssSSS");
                Date curDate = new Date();
                String strDate = sdf.format(curDate);

                FilePath = System.getProperty("user.dir") + "//src//test-output//Results//EvidenciaPDF//" + strDate + ".pdf";

                FileOutputStream archivo = new FileOutputStream(FilePath);
                doc = new Document(PageSize.A4);
                PdfWriter.getInstance(doc, archivo);
                doc.open();
                doc.addTitle(Titulo);
                doc.add(Title(Titulo));
                doc.add(Chunk.NEWLINE);



        }catch (FileNotFoundException | DocumentException e) {
            e.printStackTrace();
        }

    }

    public static void PDFWriter(String Message,String Color,Boolean Screenshot){

        try{
            String imgFile = "";
            configFileReader = new ConfigFileReader();
            String browserName = configFileReader.getBrowserName();

            doc.add(SendText(Message.replaceAll("\\r\\n", " "),Color));

            doc.add(Chunk.NEWLINE);

            if (Screenshot){

                if (SeleniumDriver.getDriver()!=null){
                    imgFile = SeleniumHelper.takeScreenshot(Message);

                }else if (AppiumDriver.getAndroidDriver()!=null){
                    imgFile = AppiumHelper.takeScreenshotAndroid(Message);

                }else if (AppiumDriver.getIosDriver()!=null){
                    imgFile = AppiumHelper.takeScreenshotIos(Message);
                }


                Image screen = Image.getInstance(imgFile);
                if(browserName.equalsIgnoreCase("chrome")){

                    if (configFileReader.getMobileEmulation().equalsIgnoreCase("true")){
                        screen.scaleAbsolute(160,280);
                    }else {
                        screen.scaleAbsolute(550,275);
                    }

                }else {

                    if (AppiumDriver.getAndroidDriver()!=null){

                        ScreenOrientation orientation = AppiumDriver.getAndroidDriver().getOrientation();
                        if (orientation.toString().equals("PORTRAIT")){
                            screen.scaleAbsolute(160,280);
                        }else {
                            screen.scaleAbsolute(550,275);
                        }

                    }else {

                        ScreenOrientation orientation = AppiumDriver.getIosDriver().getOrientation();
                        if (orientation.toString().equals("PORTRAIT")){
                            screen.scaleAbsolute(160,280);
                        }else {
                            screen.scaleAbsolute(550,275);

                        }

                    }

                }

                screen.setAlignment(Element.ALIGN_CENTER);
                doc.add(screen);
                doc.add(Chunk.NEXTPAGE);
                DeleteScreenshot(imgFile);

            }


        }catch(Exception e){
            e.printStackTrace();
        }


    }

    public static void PDFWriterAndroid(String Message,String Color,Boolean Screenshot){

        try{

            String imgFile = "";
            configFileReader = new ConfigFileReader();
            String browserName = configFileReader.getBrowserName();

            doc.add(SendText(Message.replaceAll("\\r\\n", " "),Color));
            doc.add(Chunk.NEWLINE);

            if (Screenshot){

                if (AppiumDriver.getAndroidDriver()!=null){

                    imgFile = AppiumHelper.takeScreenshotAndroid(Message);
                    Image screen = Image.getInstance(imgFile);
                    ScreenOrientation orientation = AppiumDriver.getAndroidDriver().getOrientation();

                    if (orientation.toString().equals("PORTRAIT")){
                        screen.scaleAbsolute(160,280);
                    }else {
                        screen.scaleAbsolute(550,275);
                    }

                    screen.setAlignment(Element.ALIGN_CENTER);
                    doc.add(screen);
                    doc.add(Chunk.NEXTPAGE);
                    DeleteScreenshot(imgFile);

                }

            }


        } catch (DocumentException | IOException malformedURLException) {

            malformedURLException.printStackTrace();

        }

    }

    public static void PDFWriterChrome(String Message,String Color,Boolean Screenshot){

        try{

            String imgFile = "";
            String browserName = new ConfigFileReader().getBrowserName();
            configFileReader = new ConfigFileReader();
            String MobileEmulation = configFileReader.getMobileEmulation().toUpperCase();
            String AutomationHost = configFileReader.getAutomationHost().toUpperCase();

            doc.add(SendText(Message.replaceAll("\\r\\n", " "),Color));

            doc.add(Chunk.NEWLINE);

            if (Screenshot){

                if (SeleniumDriver.getDriver()!=null){
                    imgFile = SeleniumHelper.takeScreenshot(Message);
                }


                    Image screen = Image.getInstance(imgFile);

                    if (MobileEmulation.equalsIgnoreCase("true") || (AutomationHost.contains("SELENIUMBROWSERSTACK") &&(browserName.equalsIgnoreCase("android") || browserName.equalsIgnoreCase("ios")) ) ){

                        screen.scaleAbsolute(160,280);

                    }else {

                        screen.scaleAbsolute(550,275);


                    }

                    screen.setAlignment(Element.ALIGN_CENTER);
                    doc.add(screen);
                    doc.add(Chunk.NEXTPAGE);
                    DeleteScreenshot(imgFile);

            }

        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

        public static void  SendtTextPDF(String Message){

        try{

            Paragraph p = new Paragraph();
            Chunk c = new Chunk();

            p.setAlignment(Element.ALIGN_LEFT);
            c.setFont(new Font(Font.FontFamily.COURIER,8,Font.NORMAL,BaseColor.BLACK));
            p.add(c);

            doc.add(p);

        }catch(Exception e){
            e.printStackTrace();
        }

    }


    public static  Paragraph SendText(String Message,String Color){

        Paragraph p = new Paragraph();
        Chunk c = new Chunk();
        p.setAlignment(Element.ALIGN_TOP);
        c.append(Message);

        switch (Color.trim().toUpperCase()) {
            case "R":
                c.setFont(new Font(Font.FontFamily.COURIER,12,Font.NORMAL,BaseColor.RED));
                break;
            case "G":
                c.setFont(new Font(Font.FontFamily.COURIER,12,Font.NORMAL,BaseColor.GREEN));
                break;
            case "B":
                c.setFont(new Font(Font.FontFamily.COURIER,12,Font.NORMAL,BaseColor.BLUE));
                break;
            default:
                c.setFont(new Font(Font.FontFamily.COURIER,12,Font.NORMAL,BaseColor.BLACK));
                break;
        }

        p.add(c);

        return  p;
    }



    public static  Paragraph Title(String Message){

        Paragraph p = new Paragraph();
        Chunk c = new Chunk();
        p.setAlignment(Element.ALIGN_CENTER);
        c.append(Message);
        c.setFont(new Font(Font.FontFamily.COURIER,15,Font.NORMAL,BaseColor.BLACK));
        p.add(c);
        return  p;

    }

    public static void DeleteScreenshot(String Path){
        try
        {
            Files.deleteIfExists(Paths.get(Path));
        }
        catch(NoSuchFileException e)
        {
            System.out.println("No such file/directory exists");
        }
        catch(DirectoryNotEmptyException e)
        {
            System.out.println("Directory is not empty.");
        }
        catch(IOException e)
        {
            System.out.println("Invalid permissions.");
        }

    }



    public static String ClosePDF(){
        try {

            if (doc!=null){
                doc.close();
                doc = null;
            }

            return FilePath ;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    public static void manipulatePdf(String src, String dest) throws IOException, DocumentException {
        PdfName key = new PdfName("ITXT_SpecialId");
        PdfName value = new PdfName("123456789");
        // Read the file
        PdfReader reader = new PdfReader(src);
        int n = reader.getXrefSize();
        PdfObject object;
        PRStream stream;
        // Look for image and manipulate image stream
        for (int i = 0; i < n; i++) {
            object = reader.getPdfObject(i);
            if (object == null || !object.isStream())
                continue;
            stream = (PRStream)object;
            // if (value.equals(stream.get(key))) {
            PdfObject pdfsubtype = stream.get(PdfName.SUBTYPE);
            System.out.println(stream.type());
            if (pdfsubtype != null && pdfsubtype.toString().equals(PdfName.IMAGE.toString())) {
                PdfImageObject image = new PdfImageObject(stream);
                BufferedImage bi = image.getBufferedImage();
                if (bi == null) continue;
                int width = (int)(bi.getWidth() * FACTOR);
                int height = (int)(bi.getHeight() * FACTOR);
                BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                AffineTransform at = AffineTransform.getScaleInstance(FACTOR, FACTOR);
                Graphics2D g = img.createGraphics();
                g.drawRenderedImage(bi, at);
                ByteArrayOutputStream imgBytes = new ByteArrayOutputStream();
                ImageIO.write(img, "JPG", imgBytes);
                stream.clear();
                stream.setData(imgBytes.toByteArray(), false, PRStream.BEST_COMPRESSION);
                stream.put(PdfName.TYPE, PdfName.XOBJECT);
                stream.put(PdfName.SUBTYPE, PdfName.IMAGE);
                stream.put(key, value);
                stream.put(PdfName.FILTER, PdfName.DCTDECODE);
                stream.put(PdfName.WIDTH, new PdfNumber(width));
                stream.put(PdfName.HEIGHT, new PdfNumber(height));
                stream.put(PdfName.BITSPERCOMPONENT, new PdfNumber(8));
                stream.put(PdfName.COLORSPACE, PdfName.DEVICERGB);
            }
        }
        // Save altered PDF
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
        stamper.close();
        reader.close();
    }



}
