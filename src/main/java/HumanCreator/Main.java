package HumanCreator;

import HumanCreator.generators.HumanGenerator;
import HumanCreator.generators.localGenerator.Generator;
import HumanCreator.generators.remoteApiGenerator.ApiReader;
import HumanCreator.generators.remoteApiGenerator.JsonParser;
import HumanCreator.model.Human;
import HumanCreator.model.UserPojo;
import HumanCreator.outCreators.ExcelCreator;
import HumanCreator.outCreators.PdfCreator;
import HumanCreator.sql.SqlHelper;
import com.itextpdf.text.DocumentException;

import java.io.IOException;
import java.util.ArrayList;

class Main {

    public static void main(String[] args) {
        SqlHelper.testStart();
        int countHumans = Generator.getRand(InputParameters.MIN_COUNT_USERS, InputParameters.MAX_COUNT_USERS);
        System.out.println("Запущен генератор " + countHumans + " пользователей...\nПожалуйста подождите...");
        Generator.initGlossary();
        ArrayList<Human> humans;

        humans = getHumansWithAPI(countHumans);
        if (humans.size() > 0)
            SqlHelper.putHumanListToDB(humans);
        else
            humans = SqlHelper.getHumansListFromDB();

        System.out.println("Получено пользователей  " + (humans.size()));
        int countNotAddedHumans = countHumans - humans.size();
        for (int i = 0; i < countNotAddedHumans; i++) {
            humans.add(HumanGenerator.getHuman());
        }
        System.out.println("Сгенерировано пользователей  " + (countNotAddedHumans));
        ExcelCreator.createExcelTable(humans, InputParameters.getListNamesColumn());

        try {
            PdfCreator.createPdfDocument(humans, InputParameters.getListNamesColumn());
        } catch (DocumentException | IOException ex) {
            System.out.println(ex.toString());
        }
    }

    private static ArrayList<Human> getHumansWithAPI(int countHumans) {
        ArrayList<Human> humans = new ArrayList<>();
        for (int i = 0; i < countHumans; i++) {
            String response;
            try {
                response = ApiReader.get();
            } catch (IOException ex) {
                System.out.println(ex.toString());
                break;
            }
            UserPojo upj;
            try {
                upj = JsonParser.getHuman(response);
            } catch (IOException ex) {
                System.out.println(ex.toString());
                continue;
            }
            humans.add(HumanGenerator.getHumanFromUserPojo(upj));
        }
        return humans;
    }


}
