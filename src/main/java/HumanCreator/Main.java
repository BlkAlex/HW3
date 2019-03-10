package HumanCreator;

import HumanCreator.generators.Generator;
import HumanCreator.generators.HumanGenerator;
import HumanCreator.generators.remoteApiGenerator.ApiReader;
import HumanCreator.generators.remoteApiGenerator.JsonParser;
import HumanCreator.model.Human;
import HumanCreator.model.UserPojo;
import com.itextpdf.text.DocumentException;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

class Main {

    public static void main(String[] args) {

        int countHumans = Generator.getRand(InputParameters.MIN_COUNT_USERS, InputParameters.MAX_COUNT_USERS);
        System.out.println("Запущен генератор " + countHumans + " пользователей...\nПожалуйста подождите");
        Generator.initGlossary();
        ArrayList<Human> humans;
        humans = getHumansWithAPI(countHumans);

        System.out.println("Получено пользователей  " + (humans.size()));
        int countNotAddedHumans = countHumans - humans.size();
        for (int i = 0; i < countNotAddedHumans; i++) {
            humans.add(HumanGenerator.getHuman());
        }
        System.out.println("Сгенерировано пользователей  " + (countNotAddedHumans));
        ExcelCreator.createExcelTable(humans, InputParameters.getListNamesColumn());

        try {
            PdfCreator.createPdfDocument(humans, InputParameters.getListNamesColumn());
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    private static ArrayList<Human> getHumansWithAPI(int countHumans) {
        ArrayList<Human> humans = new ArrayList<>();
        for (int i = 0; i < countHumans; i++) {
            String response;
            try {
                response = ApiReader.get();
            } catch (IOException ex) {
                if (ex.getClass() == SocketException.class) {
                    System.out.println("Ошибка сокета. ");
                    ex.printStackTrace();
                    break;
                }
                if (ex.getClass() == SocketTimeoutException.class) {
                    System.out.println("Ошибка таймаута сокета.");
                    ex.printStackTrace();
                    break;
                } else {
                    System.out.println("Неизвестная ошибка сети.");
                    ex.printStackTrace();
                    break;
                }
            }
            UserPojo upj;
            try {
                upj = JsonParser.getHuman(response);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
            humans.add(HumanGenerator.getHumanFromUserPojo(upj));
        }
        return humans;
    }


}
