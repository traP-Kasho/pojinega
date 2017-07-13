import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
public class textfile_test{
 
    public static void main(String[] args) {
        //引数にファイル名と検索条件の文字列
    putLine("pojinega_data.txt",  "");
    }
 
  public static void putLine(String fileName, String searchString){
 
    try {
        //ファイルを読み込む
        FileReader fr = new FileReader(fileName);
        BufferedReader br = new BufferedReader(fr);
 
        //条件にあう行を画面出力する
        String line;
        while ((line = br.readLine()) != null) {
          Pattern p = Pattern.compile(searchString);
          Matcher m = p.matcher(line);
 
        if (m.find()){
          System.out.println(line);
        }else{
        }
        }
 
        br.close();
        fr.close();
        System.out.println("End");
 
    } catch (IOException ex) {
        ex.printStackTrace();
    }
  }
}