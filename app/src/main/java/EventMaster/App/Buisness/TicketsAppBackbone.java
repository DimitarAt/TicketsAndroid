package EventMaster.App.Buisness;

import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TicketsAppBackbone {


    private static String JwtToken;
    private static HashMap<String,String> choiceData;
    private static List<String> dataList;
    private static ArrayList<SeatsDTO> dataSource;
    private static ArrayList<Object> objectList;
    private static ArrayList<MessageDTO> messageDTOS;
    private static String unreadMessages;


    public static String getUnreadMessages() {
        return unreadMessages;
    }

    public static void setUnreadMessages(String unreadMessages) {
        TicketsAppBackbone.unreadMessages = unreadMessages;
    }

    public static ArrayList<MessageDTO> getMessageDTOS() {
        return messageDTOS;
    }

    public static void setMessageDTOS(ArrayList<MessageDTO> messageDTOS) {
        TicketsAppBackbone.messageDTOS = messageDTOS;
    }

    public static ArrayList<Object> getObjectList() {
        return objectList;
    }

    public static void setObjectList(ArrayList<Object> objectList) {
        TicketsAppBackbone.objectList = objectList;
    }

    public static ArrayList<SeatsDTO> getDataSource() {
        return dataSource;
    }

    public static void setDataSource(ArrayList<SeatsDTO> dataSource) {
        TicketsAppBackbone.dataSource = dataSource;
    }

    private static ArrayList<recyclerViewAdminData> adminD= new ArrayList<>();

    public static void setAdminD(ArrayList<recyclerViewAdminData> adminD) {
        TicketsAppBackbone.adminD = adminD;
    }

    public static List<String> getDataList() {
        return new ArrayList<String>(choiceData.keySet());
    }


    public static ArrayList<recyclerViewAdminData> getAdminD() {
        return adminD;
    }

     static final String Url="http://192.168.0.149:8080";//"http://192.168.1.28:8080/

    public static String getUrl (){return  Url;}


    public static String getJwtToken() {
        return JwtToken;
    }

    public static void setJwtToken(String jwtToken) {
        JwtToken = jwtToken;
    }

    public static HashMap<String, String> getChoiceData() {
        return choiceData;
    }

    public static void setChoiceData(HashMap<String, String> choiceData) {
        TicketsAppBackbone.choiceData = choiceData;
    }

    public boolean CheckIfInputIsValid (String username, String password){
        boolean usernameIsValid =username!=null&&username.trim()!="";
        boolean passwordIsValid =password!=null&&username.trim()!="";
        return usernameIsValid&&passwordIsValid;
    }






}
