package src;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

class PdfCreator {

    static void createPdfDocument(ArrayList<Human> humans, ArrayList<String> columnsList)throws DocumentException,
            IOException{

            PdfPTable table = new PdfPTable(new float[] {1,1,1,0.5f,0.4f,1,0.8f,0.8f,1,1,1.5f,1,0.6f,0.6f});//new float[] {2, 2, 2, 0.8f, 0.8f, 1, 1, 1, 1.5f, 2, 2, 2, 1,1});
            table.setWidthPercentage(100);

            // BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.EMBEDDED);

            BaseFont font = BaseFont.createFont("resources/fonts/my_font.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

            //Font titleFont = FontFactory.getFont(BaseFont.TIMES_BOLDITALIC, BaseFont.CP1250, BaseFont.EMBEDDED);

            for (String nameColumn :columnsList){
                table.addCell(new PdfPCell(new Paragraph(nameColumn, new Font(font,8))));
            }
            table.setHeaderRows(1);



            PdfPCell[] cells = table.getRow(0).getCells();
            for (int j=0;j<cells.length;j++){
                cells[j].setBackgroundColor(BaseColor.GRAY);
            }

            for (Human human :humans){
                table.addCell(getPDFPcell(human.getName()));//human.getName());
                table.addCell(getPDFPcell(human.getSurname()));
                table.addCell(getPDFPcell(human.getPatronymic()));
                table.addCell(getPDFPcell(String.valueOf(human.getAge())));
                table.addCell((getPDFPcell(human.getSex() == SEX.MALE?InputParameters.getMaleString():InputParameters.getFemaleString())));
                table.addCell(getPDFPcell(human.getBithdayDate().format(DateTimeFormatter.ofPattern("dd-MM-YYYY"))));
                table.addCell(getPDFPcell(human.getInn()));
                table.addCell(getPDFPcell(String.valueOf(human.getMailIndex())));
                table.addCell(getPDFPcell(human.getCountry()));
                table.addCell(getPDFPcell(human.getRegion()));
                table.addCell(getPDFPcell(human.getTown()));
                table.addCell(getPDFPcell(human.getStreet()));
                table.addCell(getPDFPcell(human.getNumberHouse()));
                table.addCell(getPDFPcell(String.valueOf(human.getNumberFlat())));

            }

            File outFile = new File("users.pdf");
            Document document = new Document(PageSize.A4.rotate(), 10f, 10f, 10f, 0f);
            PdfWriter.getInstance(document, new FileOutputStream(outFile));
            document.open();
            document.add(table);
            document.close();
            System.out.println("PDF файл создан. Путь:" +outFile.getAbsolutePath());

    }

    private static PdfPCell getPDFPcell(String text){

        BaseFont font = null;
        try {
            font = BaseFont.createFont("resources/fonts/my_font.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
        Font myFont = new Font(font,8);
        PdfPCell p = new PdfPCell(new Paragraph(text, myFont));
        p.setNoWrap(false);
        return p;
    }
}
