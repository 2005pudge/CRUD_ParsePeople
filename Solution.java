import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class Solution {
    public static volatile List<Person> allPeople = new ArrayList<Person>();

    static {
        allPeople.add(Person.createMale("Иванов Иван", new Date()));  //сегодня родился    id=0
        allPeople.add(Person.createMale("Петров Петр", new Date()));  //сегодня родился    id=1
    }

    public static void main(String[] args) throws ParseException {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);

        switch (args[0]) {
            case "-c": {
                // добавляем людей
                synchronized (allPeople) {
                    for (int i = 1; i < args.length; i += 3) {
                        if (args[i + 1].equals("м")) {
                            allPeople.add(Person.createMale(args[i], simpleDateFormat.parse(args[i + 2])));
                            System.out.println(allPeople.size() - 1);
                        } else if (args[i + 1].equals("ж")) {
                            allPeople.add(Person.createFemale(args[i], simpleDateFormat.parse(args[i + 2])));
                            System.out.println(allPeople.size() - 1);
                        }
                    }
                }
                break;
            }
            case "-u": {
                // обновляем людей
                synchronized (allPeople) {
                    for (int i = 1; i < args.length; i += 4) {
                        allPeople.get(Integer.parseInt(args[i])).setName(args[i + 1]);
                        allPeople.get(Integer.parseInt(args[i])).setBirthDate(simpleDateFormat.parse(args[i + 3]));
                        if (args[i + 2].equals("м")) {
                            allPeople.get(Integer.parseInt(args[i])).setSex(Sex.MALE);
                        } else allPeople.get(Integer.parseInt(args[i])).setSex(Sex.FEMALE);
                    }
                }
                break;
            }
            case "-d": {
                // удаляем людей логически
                synchronized (allPeople) {
                    for (int i = 1; i < args.length; i++) {
                        allPeople.get(Integer.parseInt(args[i])).setName(null);
                        allPeople.get(Integer.parseInt(args[i])).setSex(null);
                        allPeople.get(Integer.parseInt(args[i])).setBirthDate(null);
                    }
                }
                break;
            }
            case "-i": {
                // выводим информацию о людях
                synchronized (allPeople) {
                    for (int i = 1; i < args.length; i++) {
                        Person person = allPeople.get(Integer.parseInt(args[i]));
                        if (person.getSex() == Sex.MALE) {
                            System.out.println(person.getName() + " м " + simpleDateFormat1.format(person.getBirthDate()));
                        } else
                            System.out.println(person.getName() + " ж " + simpleDateFormat1.format(person.getBirthDate()));
                    }
                }
                break;
            }
        }
    }
}
