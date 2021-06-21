import java.io.Serializable;
import java.util.Dictionary;
import java.util.HashMap;

public class ReturnPack implements Serializable {
    private static final long serialVersionUID = 2L;
    private String[] info;
    private String exc;
    private SpaceMarine[] sp;
    private HashMap<Integer, String> str;

    public ReturnPack (String[] info, String exc, SpaceMarine[] sp, HashMap<Integer, String> str) {
        this.info = info;
        this.exc = exc;
        this.sp = sp;
        this.str = str;
    }

    public String[] getInfo() { return info; }
    public String getExc() { return exc; }
    public SpaceMarine[] getSp() { return sp; }
    public HashMap<Integer, String> getStr() { return str; }
}