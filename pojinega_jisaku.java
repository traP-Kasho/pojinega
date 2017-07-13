import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.URL;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

public class pojinega_jisaku {
    public static void main(String[] args)throws UnsupportedEncodingException {
    	
    	int textnum = 0;
    	
    	for(; textnum <= 0; textnum++){//textは仮に3つとしている
    	
    	//textは100個のString型配列と仮定（渡されるtext形式によって随時変更予定）
        String[] texts = new String[100];
        texts[0] = "MSMさん気持ち悪いです。近寄らないでください。"; 
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
    	
    
        System.out.println("===== HTTP GET Start =====");
        try {
        
            URL url = new URL("https://jlp.yahooapis.jp/MAService/V1/parse?appid=dj00aiZpPWVveWhyVUx3S0t3eCZzPWNvbnN1bWVyc2VjcmV0Jng9YzM-&results=ma,uniq&uniq_filter=9%7C10&sentence=" + result);

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
                            //System.out.println(line);
                        	
                        	 String[] element = line.split("<word><surface>",0);
                        	 
                        	 int num = 0;
                        	 for(; num < element.length; num++){
                        	if(num != 0){
                        		String[] word = element[num].split("</surface>",0);
                           	 System.out.println(word[0]);
                        	 }
                         }
                        	 
                       }
                        	 
                    }
                }
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("===== HTTP GET End =====");
    
    	}
      
    }   
}
   