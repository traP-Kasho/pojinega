import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.URL;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

public class pojinega{
    public static void main(String[] args) throws UnsupportedEncodingException{
    	
    	double positive_score[], negative_score[], neutral_score[]; 
        positive_score = new double[100]; 
        negative_score = new double[100];
        neutral_score = new double[100]; 
        int textnum = 0; 
        int accepted_textnum = 0; 
        double sum_positive = 0; 
        double sum_negative = 0;
        double sum_neutral = 0;
       
          	for(; textnum <= 2; textnum++){//textは仮に3つとしている
    		
    		//textは100個のString型配列と仮定（渡されるtext形式によって随時変更予定）
            String[] texts = new String[100];
            texts[0] = "何ですか気持ち悪い"; 
            texts[1] = "正直あなたとは関わり合いになりたくないです";
            texts[2] = "こっちにこないでください";//ここまで仮定
            
            //textは文字列と仮定し、以下はURLエンコード
            String encoding = "UTF-8";
            String lower_text = texts[textnum].toLowerCase();
            String result = URLEncoder.encode(lower_text, encoding);
             result= result.replace("*", "%2a");
             result = result.replace("-", "%2d");
             result= result.replace("_", "");
             result= result.replace("+", "%20");
             
             try {
               URL url = new URL("https://api.apitore.com/api/11/sentiment/predict?access_token=0b8bdf4e-5fbd-4acb-a00f-37167a65599f&text=" + result );
               
               HttpURLConnection connection = null;
               
               try {
                   connection = (HttpURLConnection) url.openConnection();
                   connection.setRequestMethod("GET");
                   
                   if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                       try (InputStreamReader isr = new InputStreamReader(connection.getInputStream(),
                                                                          StandardCharsets.UTF_8);
                            BufferedReader reader = new BufferedReader(isr)) {
                           String line;
                           while ((line = reader.readLine()) != null) {

                               String[] element = line.split(":",0);
                               String[] posi_element = element[11].split("}",0);
                               String[] nega_element = element[13].split("}",0);
                               String[] neut_element = element[15].split("}",0);
                               
                               positive_score[textnum] = Double.parseDouble(posi_element[0]); 
                               negative_score[textnum] = Double.parseDouble(nega_element[0]);
                               neutral_score[textnum] = Double.parseDouble(neut_element[0]);
                               
                               sum_positive += positive_score[textnum];
                               sum_negative += negative_score[textnum];
                               sum_neutral += neutral_score[textnum];  
                               accepted_textnum++;   
                           }
                       }
                   }else{
                   	    positive_score[textnum] = 0;
                        negative_score[textnum] = 0;
                        neutral_score[textnum] = 0;
                   }
               } 
               finally {
                   if (connection != null) {
                       connection.disconnect();
                   }
               }
           } catch (IOException e) {
               e.printStackTrace();
           }
       }if(accepted_textnum != 0){
    	   
    	   double ave_positive = sum_positive / accepted_textnum;
           double ave_negative = sum_negative / accepted_textnum;
    	   double ave_nutral = sum_neutral / accepted_textnum;
    	   System.out.println("ave_positive = " + ave_positive);  
           System.out.println("ave_negative = " + ave_negative);  
    	   System.out.println("ave_nutral = " + ave_nutral);   
       }else{
    	   System.out.println("ERROR:時間を空けて再検索してください。それでもダメな場合はご一報ください。");
    	   }
    }
}