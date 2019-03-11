package HumanCreator.outCreators;

import HumanCreator.InputParameters;
import HumanCreator.enums.Gender;
import HumanCreator.model.Human;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class PdfCreator {
    private final static String FILE_NAME = "users.pdf";
    private final static String FONT_PATH = "fonts/my_font.ttf";

    public static void createPdfDocument(ArrayList<Human> humans, ArrayList<String> columnsList) throws DocumentException,
            IOException {
        PdfPTable table = new PdfPTable(new float[]{1, 1, 1, 0.5f, 0.4f, 1, 0.8f, 0.8f, 1, 1, 1.5f, 1, 0.6f, 0.6f});
        table.setWidthPercentage(100);
        BaseFont font = BaseFont.createFont(FONT_PATH, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        for (String nameColumn : columnsList) {
            table.addCell(new PdfPCell(new Paragraph(nameColumn, new Font(font, 8))));
        }
        table.setHeaderRows(1);
        PdfPCell[] cells = table.getRow(0).getCells();
        for (PdfPCell cell : cells) {
            cell.setBackgroundColor(BaseColor.GRAY);
        }
        for (Human human : humans) {
            table.addCell(getPDFPcell(human.getName()));
            table.addCell(getPDFPcell(human.getSurname()));
            table.addCell(getPDFPcell(human.getPatronymic()));
            table.addCell(getPDFPcell(String.valueOf(human.getAge())));
            table.addCell((getPDFPcell(
                    human.getGender() == Gender.MALE ?
                            InputParameters.MALE_STRING :
                            InputParameters.FEMALE_STRING)));
            table.addCell(getPDFPcell(human.getBirthDay().format(DateTimeFormatter.ofPattern("dd-MM-YYYY"))));
            table.addCell(getPDFPcell(human.getInn()));
            table.addCell(getPDFPcell(String.valueOf(human.getMailIndex())));
            table.addCell(getPDFPcell(human.getCountry()));
            table.addCell(getPDFPcell(human.getRegion()));
            table.addCell(getPDFPcell(human.getTown()));
            table.addCell(getPDFPcell(human.getStreet()));
            table.addCell(getPDFPcell(human.getNumberHouse()));
            table.addCell(getPDFPcell(String.valueOf(human.getNumberFlat())));
        }

        File outFile = new File(FILE_NAME);
        Document document = new Document(PageSize.A4.rotate(), 10f, 10f, 10f, 0f);
        try {
            PdfWriter.getInstance(document, new FileOutputStream(outFile));
            document.open();
            document.add(table);
            document.close();
            System.out.println("PDF файл создан. Путь:" + outFile.getAbsolutePath());
        } catch (FileNotFoundException ex) {
            System.out.println(ex.toString());
        }

    }

    private static PdfPCell getPDFPcell(String text) {
        BaseFont font = null;
        try {
            font = BaseFont.createFont(FONT_PATH, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        } catch (DocumentException | IOException ex) {
            System.out.println(ex.toString());
        }
        Font myFont = new Font(font, 8);
        PdfPCell pdfCell = new PdfPCell(new Paragraph(text, myFont));
        pdfCell.setNoWrap(false);
        return pdfCell;
    }
}
