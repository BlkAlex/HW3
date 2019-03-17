package HumanCreator;

import HumanCreator.generators.HumanGenerator;
import HumanCreator.generators.localGenerator.Generator;
import HumanCreator.generators.remoteApiGenerator.ApiReader;
import HumanCreator.generators.remoteApiGenerator.JsonParser;
import HumanCreator.model.Human;
import HumanCreator.model.UserPojo;
import HumanCreator.outCreators.ExcelCreator;
import HumanCreator.outCreators.PdfCreator;
import HumanCreator.sql.SQLHumansAdapter;
import com.itextpdf.text.DocumentException;

import java.io.IOException;
import java.util.ArrayList;

class Main {

    public static void main(String[] args) {
        SQLHumansAdapter.initDbParams();
        int countHumans = Generator.getRand(InputParameters.MIN_COUNT_USERS, InputParameters.MAX_COUNT_USERS);
        System.out.println("Запущен генератор " + countHumans + " пользователей...\nПожалуйста подождите...");
        Generator.initGlossary();
        ArrayList<Human> apiHumans;

        apiHumans = getHumansWithAPI(countHumans);
        ArrayList<Human> humans = new ArrayList<>(apiHumans);

        System.out.println("Получено пользователей  по API " + (apiHumans.size()));

        if (humans.size() != countHumans) {
            int sizeHumans = humans.size();
            humans.addAll(SQLHumansAdapter.getHumansListFromDB(countHumans - sizeHumans));
            System.out.println("Получено пользователей  из базы " + (humans.size() - sizeHumans));
        }

        if (apiHumans.size() > 0) {
            SQLHumansAdapter.putHumanListToDB(apiHumans);
        }


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
