package Utils;

import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class ReportHTML {

    public static FileWriter a = null;
    public static String Values = "";
    public static String ValuesTxt = "";

    public static void IniReport(String Title,String Columnas){



            try{

                SimpleDateFormat sdf = new SimpleDateFormat("hhmmss_SSS_ddMMyyyy");
                Date curDate = new Date();
                String strDate = sdf.format(curDate);

                String FilePath = System.getProperty("user.dir") + "//src//test-output//Results//Reports//" + strDate + ".html";
                a = new FileWriter(FilePath);

                a.write("<!DOCTYPE html>");
                a.write( "<html>");
                a.write( "<head>");
                a.write( "<style>");
                a.write( "table {");
                a.write( "  table-layout: fixed;");
                a.write( "  border-collapse: collapse;");
                a.write( "  width: 100%;");
                a.write( "}");
                a.write( "th, td {");
                a.write( "border: 1px solid #ccc;");
                a.write( "  text-align: left;");
                a.write( "  padding: 1px;");
                a.write( "  width: 10%;");
                a.write( "}");
                a.write( "tr:nth-child(even){background-color: #f2f2f2}");
                a.write( "th {");
                a.write( "  background-color: #154068;");
                a.write( "  color: white;");
                a.write( "}");
                a.write( ".button {");
                a.write( "background-color: #1C4E80;" );
                a.write( "border: none;");
                a.write( "color: white;");
                a.write( "padding: 10px 20px;");
                a.write( "text-align: center;");
                a.write( "text-decoration: none;");
                a.write( "display: inline-block;");
                a.write( "font-size: 10px;");
                a.write( "margin: 0px 0px;");
                a.write( "-webkit-transition-duration: 0.1s; ");
                a.write( "transition-duration: 0.1s;");
                a.write( "cursor: pointer;");
                a.write( "}");
                a.write( ".button1:hover {");
                a.write( "background-color: #C1CCC8;");
                a.write( "color: black;");
                a.write( "}");
                a.write( ".button1 {");
                a.write( "border-radius: 15px");
                a.write( "}");
                a.write( ".modal {");
                a.write( "display: none;");
                a.write( "position: fixed;");
                a.write( "z-index: 1;");
                a.write( "padding-top: 0px;");
                a.write( "left: 0;");
                a.write( "top: 0;");
                a.write( "width: 100%;");
                a.write( "height: 100%;");
                a.write( "overflow: auto;");
                a.write( "background-color: rgb(0, 0, 0);");
                a.write( "background-color: rgba(0, 0, 0, 0.4);");
                a.write( "}");
                a.write( ".modal-content {");
                a.write( "background-color: #fefefe;");
                a.write( "margin: auto;");
                a.write( "padding: 10px;");
                a.write( "border: 0px solid #888;");
                a.write( "width: 80%;");
                a.write( "}");
                a.write( ".close {");
                a.write( "color: #aaaaaa;");
                a.write( "float: right;");
                a.write( "font-size: 28px;");
                a.write( "font-weight: bold;");
                a.write( "}");
                a.write( ".close:hover,");
                a.write( ".close:focus {");
                a.write( "color: #000;");
                a.write( "text-decoration: none;");
                a.write( "cursor: pointer;");
                a.write( "}");
                a.write( ".modal-header {");
                a.write( "display: none;");
                a.write( "}");
                a.write( "#myInput {");
                a.write( "background-position: 10px 10px;");
                a.write( "background-repeat: no-repeat;");
                a.write( "width: 10%;");
                a.write( "font-size: 16px;");
                a.write( "padding: 10px 10px 10px 35px;");
                a.write( "border: 1px solid #ddd;");
                a.write( "margin-bottom: 12px;");
                a.write( "}");

                a.write( "</style>");
                a.write( "</head>"	);
                a.write( "<meta name='viewport' content='width=device-width, initial-scale=1'>");

                a.write("<script src='"+System.getProperty("user.dir")+"//src//ResourceReport//JScript//xlsx.full.min.js"+"'></script>");
                a.write("<script src='"+System.getProperty("user.dir")+"//src//ResourceReport//JScript//FileSaver.min.js"+"'></script>");
                a.write("<script src='"+System.getProperty("user.dir")+"//src//ResourceReport//JScript//tableexport.min.js"+"'></script>");

                a.write( "<body>");
                //a.write( "<h1 align='center'><img src='"+System.getProperty("user.dir")+"//src//ResourceReport//interbank.png"+"' alt='bcp' align='left' style='width:160px;height:70px;'>");
                //a.write( "</h1>");
                //a.write( "<h2 align='center'><font color='Brown'>EVIDENCIA DE PRUEBA AUTOMATIZADA</font>");
                //a.write( "<img src='"+System.getProperty("user.dir")+"//src//ResourceReport//unnamed.gif"+"' alt='bcp' align='right' style='width:110px;height:90px;'>");
                //a.write( "</h2>");
                //a.write( "<h1 align='center'><font color='Grey'>"+Title+"</font>" );
                //a.write( "</h1>");

                a.write("<input type='text' id='myInput' onkeyup='myFunction()' placeholder='Buscar ...' title='Buscar ...'>");
                a.write("<button id='btnExportar' style='background-color:e7e7e7;color:black;width:150px;height:40px;'>Exportar</button>");
                a.write( "<table align='center' id='tabla'>");
                a.write( "<tr>");
                a.write( "<th>Nro Caso</th>");

                if (Columnas.indexOf('/')>=0 ){
                    String[] Val = Columnas.split("/");
                    for (String s : Val) {
                        a.write("<th>" + s + "</th>");
                    }
                }else {
                    a.write("<th>" + Columnas + "</th>");
                }

                a.write( "<th>Pass/Fail</th>");
                a.write( "<th>Evidencia</th>");
                a.write( "</tr>");



            } catch (IOException e) {
                e.printStackTrace();
            }


    }

    public static void AddNewCase(String RutaPdf,boolean validation,int IdCaso,String Valores){

        try{

            a.write("<tr>");

                String id = "id" + IdCaso;
                a.write("<td>"+ IdCaso+"</td>");

            if (Valores.indexOf('/')>=0 ){

                String[] Val = Valores.split("/");
                for (String s : Val) {
                    a.write("<td>" + s.toUpperCase() + "</td>");
                }

            }else {

                a.write("<td>" + Valores.toUpperCase() + "</td>");

            }



                if(validation){
                    a.write("<td align='center' style='color:green;'>PASS</td>");
                }else{
                    a.write("<td align='center' style='color:red;'>FAIL</td>");
                }

                a.write("<td>");
                    a.write("<button align='center' onclick=\"document.getElementById('"+id+"').style.display='block'\" class=\" button button1\">Ver</button>");
                    a.write("<div id=\""+id+"\" class=\"modal\" style=\"display: none;\">");
                    a.write("<div class=\"modal-content\">");
                    a.write("<span onclick=\"document.getElementById('"+id+"').style.display='none'\" class=\"close\">&times;</span>");
                    a.write("<div>");
                    a.write("<embed src=\"file:"+RutaPdf+"#zoom=125\" frameborder=\"0\" width=\"100%\" height=\"900px\" internalinstanceid=\"10\">");
                    a.write("</div>");
                    a.write("</div>");
                    a.write("</div>");
                a.write("</td>");

            a.write ("</tr>");


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static String TiempoDuracion(String IniTime, String FinTime) {


            try{
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

                Date fechaInicial = sdf.parse(IniTime);
                Date fechaFinal = sdf.parse(FinTime);

                int diferencia = (int) ((fechaFinal.getTime() - fechaInicial.getTime()) / 1000);

                int dias = 0;
                int horas = 0;
                int minutos = 0;
                if (diferencia > 86400) {
                    dias = (int) Math.floor(diferencia / 86400);
                    diferencia = diferencia - (dias * 86400);
                }
                if (diferencia > 3600) {
                    horas = (int) Math.floor(diferencia / 3600);
                    diferencia = diferencia - (horas * 3600);
                }
                if (diferencia > 60) {
                    minutos = (int) Math.floor(diferencia / 60);
                    diferencia = diferencia - (minutos * 60);
                }

                // return horas + " hr "+minutos + " min " + diferencia + " seg ";

                String TimeReturn = "";
                if (horas == 0 && minutos == 0) {
                    TimeReturn = diferencia + " seg";

                } else if (horas == 0 && minutos != 0) {
                    TimeReturn = minutos + " min " + diferencia + " seg";

                } else if (horas != 0 )  {

                    TimeReturn = horas + " hr " + minutos + " min " + diferencia + " seg";

                }
                return TimeReturn;

            }catch (ParseException e){
                return "";
            }

    }



    public static String GetFechaActual(){

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date curDate = new Date();
        return sdf.format(curDate);

    }


    public static String GetFecha(){

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date curDate = new Date();
        return sdf.format(curDate);

    }


    public static String GetFechaAnioMesDia(){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date();
        return sdf.format(curDate);

    }

    public static String GetFechaMesHora(){

        SimpleDateFormat sdf = new SimpleDateFormat("ddMMHHmm");
        Date curDate = new Date();
        return sdf.format(curDate);

    }

    public static int numeroDeDiasMes(int mes){

        int numeroDias = 0;

        switch(mes){
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                numeroDias=31;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                numeroDias=30;
                break;
            case 2:

                Date anioActual=new Date();
                if(esBisiesto(1900 + anioActual.getYear())){
                    numeroDias=29;
                }else{
                    numeroDias=28;
                }
                break;

        }

        return numeroDias;
    }

    public static boolean esBisiesto(int anio) {

        GregorianCalendar calendar = new GregorianCalendar();
        boolean esBisiesto = false;
        if (calendar.isLeapYear(anio)) {
            esBisiesto = true;
        }
        return esBisiesto;

    }




    public static void CloseReport(){

        try{

            if (a!=null){

                String[] parts = Values.split("_");
                String IniTime = parts[0];
                String ContPass = parts[1];
                String ContFail = parts[2];
                String FinTime = GetFechaActual();

                String[] f = IniTime.split(" ");
                String[] f2 = FinTime.split(" ");

                a.write("</table>");



                a.write("<script>\n" +
                        "function myFunction() {\n" +
                        "\n" +
                        "  var input, filter, table, tr, td, i, txtValue , col ;\n" +
                        "  input = document.getElementById(\"myInput\");\n" +
                        "  filter = input.value.toUpperCase();\n" +
                        "  table = document.getElementById(\"tabla\");\n" +
                        "  col = document.getElementById('tabla').rows[0].cells.length - 1\n" +
                        "  tr = table.getElementsByTagName(\"tr\");\n" +
                        "  \n" +
                        "  for (i = 0; i < tr.length; i++) {\n" +
                        "  \n" +
                        "      for (j = 0; j < col; j++) {\n" +
                        "\n" +
                        "          td = tr[i].getElementsByTagName(\"td\")[j];\n" +
                        "          if (td) {\n" +
                        "          txtValue = td.textContent || td.innerText;\n" +
                        "          \n" +
                        "          if (txtValue.toUpperCase().indexOf(filter) > -1) {\n" +
                        "            tr[i].style.display = \"\";\n" +
                        "            break;\n" +
                        "          } else {\n" +
                        "            tr[i].style.display = \"none\";\n" +
                        "          }\n" +
                        "          \n" +
                        "        }\n" +
                        " \n" +
                        "    }       \n" +
                        "  }\n" +
                        "}\n" +
                        "</script>");

                a.write("<script src='"+System.getProperty("user.dir")+"//src//ResourceReport//JScript//script.js"+"'></script>");
                a.write("<br>");
                a.write("<table style='margin-top:10px; width:30%'id='Resolucion'>");
                a.write("<tr>");
                a.write("<th colspan='2'align='center'>Resolucion de Automatizacion</th>");
                a.write("</tr>");
                a.write("<tr>");
                a.write("<td>Usuario</td>");
                a.write("<td>"+System.getProperty("user.name")+"</td>");
                a.write("</tr>");
                a.write("<tr>");
                a.write("<td>Casos Exitosos</td>");
                a.write("<td>"+ContPass+"</td>");
                a.write("</tr>");
                a.write("<tr>");
                a.write("<td>Casos Fallidos</td>");
                a.write("<td>"+ContFail+"</td>");
                a.write("<tr>");
                a.write("<td>Fecha de Ejecucion</td>");

                a.write("<td>"+f[0].replace("-","/")+"</td>");
                a.write("</tr>");
                a.write("<tr>");
                a.write("<td>Hora de Inicio</td>");
                a.write("<td>"+f[1]+"</td>");
                a.write("</tr>");
                a.write("<tr>");
                a.write("<td>Hora de Fin</td>");
                a.write("<td>"+f2[1]+"</td>");
                a.write("</tr>");
                a.write("<tr>");
                a.write("<td>Tiempo de duracion</td>");
                a.write("<td>"+TiempoDuracion(IniTime,FinTime)+"</td>");
                a.write("</tr>");
                a.write("</table>");
                a.write("<p align='center' style='color:gray;'>* POR FAVOR, INFORME INMEDIATAMENTE SI HAY ALGUNA DESVIACION EN LOS RESULTADO (S)</p>");
                a.write("</body>");
                a.write("</html>");

                a.close();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public static void CrearTXT(){



        try{

            SimpleDateFormat sdf = new SimpleDateFormat("hhmmss_ddMMyyyy");
            Date curDate = new Date();
            String strDate = sdf.format(curDate);
            String FilePath = System.getProperty("user.dir") + "//src//test-output//Results//DataOutput//" + strDate + ".txt";
            FileWriter fileWriter  = new FileWriter(FilePath);
            fileWriter.write(ValuesTxt);
            fileWriter.close();


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
