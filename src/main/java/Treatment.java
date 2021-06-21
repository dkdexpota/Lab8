import javafx.scene.control.Label;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.*;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Treatment {
    public final static int SERVICE_PORT = 27913;
    public static String username;
    public static String userpass;
    private static DatagramSocket clientSocket;

    static {
        try {
            clientSocket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    static String[] exc = new String[] {
            "Неизвестная команда, для получения списка команд используйте help.",
            "Число должно быть положительным.",
            "В качестве аргумента введите целое положительное число.",
            "Ввод параметров не завершен.",
            "Неизвестная команда, для получения списка команд используйте help.",
            "Не удается найти указанный файл.",
            "В качестве аргумента введите путь до файла.",
            "Из файла не может быть вызван другой файл"
//            "Unknow comand, use help",
//            "The number must be positive",
//            "Enter a positive integer as an argument",
//            "Parameter entry not completed",
//            "Unknown command, to get a list of commands, use help иаоауо",
//            "The specified file cannot be found",
//            "Enter the path to the file as an argument",
//            "No other file can be called from a file"
    };

    public Treatment() throws SocketException {
    }

    public static SendPack noArg(String[] comand) {
        SendPack sp = null;
        if(comand.length == 1) {
            sp = new SendPack(Comand.valueOf(comand[0]), null, null, username, userpass);
        } else {
            System.out.println(exc[0]);
        }
        return sp;
    }
    public static SendPack byNum(String comand, String id, Label errLabel) {
        SendPack sp = null;
        errLabel.setVisible(true);
        try {
            if (Integer.parseInt(id) > 0) {
                sp = new SendPack(Comand.valueOf(comand), id, null, username, userpass);
            } else {
                errLabel.setText(exc[1]);
            }
        } catch (NumberFormatException e) {
            errLabel.setText(exc[2]);
        }
        return sp;
    }
    public static SendPack elem(String[] comand) {
        SendPack sp = null;
        if(comand.length == 1) {
            try {
//                sp = new SendPack(Comand.valueOf(comand[0]), null, MakeElement.makeElement(-1), username, userpass);
            } catch (Exception e) {
                System.out.println(exc[3]);
            }
        } else {
            System.out.println(exc[0]);
        }
        return sp;
    }
    public static SendPack upd(String[] comand) {
        SendPack sp = null;
        if(comand.length == 2) {
            try {
                if (Integer.parseInt(comand[1]) > 0) {
                    try {
//                        sp = new SendPack(Comand.valueOf(comand[0]), null, MakeElement.makeElement(Integer.parseInt(comand[1])), username, userpass);
                    } catch (Exception e) {
                        System.out.println(exc[3]);
                    }
                } else {
                    System.out.println(exc[1]);
                }
            } catch (NumberFormatException e) {
                System.out.println(exc[2]);
            }
        } else {
            System.out.println(exc[0]);
        }
        return sp;
    }
    public static Scanner execute_script(Scanner ex_scan, String[] comand) {
        if (ex_scan != null) {
            if (comand.length == 2) {
                try {
                    ex_scan = new Scanner(new File(comand[1]));
                } catch (FileNotFoundException e) {
                    System.out.println(exc[5]);
                }
            } else {
                System.out.println(exc[6]);
            }
        } else {
            System.out.println(exc[7]);
            ex_scan = null;
        }
        return ex_scan;
    }

    private static Authorization signIn (String comand) {
        Scanner in = new Scanner(System.in);
        String login = "";
        String password = "";
        while (login.length() == 0) {
            System.out.print("login: ");
            login = in.nextLine();
        }
        while (password.length() == 0) {
            System.out.print("password: ");
            password = in.nextLine();
        }
        username = login;
        userpass = password;
        return new Authorization(comand, login, password);
    }
    public static StatusAuth authorization (Authorization auth) throws SocketException, UnknownHostException {
        DatagramSocket clientSocket = new DatagramSocket();
        clientSocket.setReceiveBufferSize(2048);
        clientSocket.setSoTimeout(8000);
//        Scanner in = new Scanner(System.in);
        StatusAuth sa = null;
//        String comand = "";
//        Authorization auth = null;
//        while (!status) {
//            while (true) {
//                try {
//                    System.out.print("sign in/sign up/exit: ");
//                    comand = in.nextLine();
//                    break;
//                } catch (NoSuchElementException e) {
//                    in = new Scanner(System.in);
//                    break;
//                } catch (NullPointerException e) {
//                    in = new Scanner(System.in);
//                }
//            }
//            switch (comand) {
//                case ("sign in"):
//                case ("sign up"):
//                    auth = sign(comand);
//                    break;
//                case ("exit"):
//                    System.exit(0);
//                    break;
//                default:
//                    auth = null;
//                    break;
//            }
//
//        }
        if (auth!=null) {
            byte[] st = Sending.sanding(auth, SERVICE_PORT, clientSocket);
            if (st!=null) {
                sa = Deserialize.deserializest(st);
                username = auth.getLogin();
                userpass = auth.getPassword();
            }
        }
        clientSocket.close();
        return sa;
    }

    public static synchronized ReturnPack treatment (SendPack sp) throws SocketException, UnknownHostException {
        Lock lock = new ReentrantLock();
        clientSocket.setReceiveBufferSize(2048);
        clientSocket.setSoTimeout(8000);
        byte[] rp = null;
//        while (work) {
//            sp = null;
//            if (ex_scan != null) {
//                if (ex_scan.hasNextLine()) {
//                    comand = ex_scan.nextLine().split("\\s");
//                } else {
//                    ex_scan = null;
//                    comand = in.nextLine().split("\\s");
//                }
//            } else {
//                while (true) {
//                    try {
//                        comand = in.nextLine().split("\\s");
//                        break;
//                    } catch (NoSuchElementException e) {
//                        in = new Scanner(System.in);
//                        break;
//                    } catch (NullPointerException e) {
//                        in = new Scanner(System.in);
//                    }
//                }
//            }
//
//            switch (comand[0]) {
//                case ("exit"):
//                    work = false;
//                    in.close();
//                    break;
//                case ("help"):
//                case ("head"):
//                case ("show"):
//                case ("clear"):
//                case ("sum_of_health"):
//                case ("info"):
//                    sp = Treatment.noArg(comand);
//                    break;
//                case ("count_by_health"):
//                case ("filter_by_health"):
//                case ("remove_by_id"):
//                    sp = Treatment.byNum(comand);
//                    break;
//                case ("add"):
//                case ("add_if_min"):
//                case ("remove_greater"):
//                    sp = Treatment.elem(comand);
//                    break;
//                case ("update"):
//                    sp = Treatment.upd(comand);
//                    break;
//                case ("execute_script"):
//                    ex_scan = Treatment.execute_script(ex_scan, comand);
//                    break;
//                default:
//                    System.out.println(exc[4]);
//                    break;
//            }
//
//            if (sp != null) {
//                byte[] rp = Sending.sanding(sp, SERVICE_PORT, clientSocket);
//                if (rp!=null) {
//                    ReturnTreat.treatment(Objects.requireNonNull(Deserialize.deserialize(rp)));
//                }
//            }
//        }

        lock.lock();
        if (sp != null) {
            rp = Sending.sanding(sp, SERVICE_PORT, clientSocket);
            if (rp!=null) {
                return Deserialize.deserialize(rp);
            }
        }
        lock.unlock();
        return null;
    }
}
