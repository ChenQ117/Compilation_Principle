package 实验一;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @version v1.0
 * @ClassName: Test
 * @Description:
 * @Author: ChenQ
 * @Date: 2021/3/31 on 8:37
 */
public class One {
    public HashMap<String, String> keyword_map = new HashMap<>();
    public HashMap<String, String> operatorword_map = new HashMap<>();
    public HashMap<String, String> boundaryword_map = new HashMap<>();
    public HashMap<String, String> identifyword_map = new HashMap<>();
    public HashMap<String, String> numberword_map = new HashMap<>();
    Set<String> set = new HashSet<>();
    int identifyflag = 0;
    int numberflag = 0;

    public One() {
        init();
    }

    public void init() {
        readFile("Txt\\KeyWord.txt", keyword_map);
        readFile("Txt\\OperatorWord.txt", operatorword_map);
        readFile("Txt\\BoundarySymbolWord.txt", boundaryword_map);
        readFile("Txt\\IdentifyWord.txt", identifyword_map);
        readFile("Txt\\NumberWord.txt", numberword_map);
    }

    //将文件中的数据读取到map集合中
    public void readFile(String path, Map map) {
        try {
            BufferedReader bfr = new BufferedReader(new FileReader(path));
            String str = "";
            while ((str = bfr.readLine()) != null) {
                String[] strs = str.split("\\s+");
                if (strs.length>=2){
                    map.put(strs[0], strs[1]);
                    if (map.equals(operatorword_map) || map.equals(boundaryword_map)) {
                        set.add(strs[1]);//将key存储到集合中
                    }
                    if (map.equals(identifyword_map)) {
                        identifyflag++;
                    }
                    if (map.equals(numberword_map)) {
                        numberflag++;
                    }
                }

            }
            bfr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //判断该字符是否存在文件中
    public String isWord(String word, Map<String, String> map) {
        for (String key : map.keySet()) {
            if (map.get(key).equals(word)) {
                return key;
            }
        }
        return "-1";
    }


    //判断是否是标识符
    public String isIdentifyWord(String word) {
        boolean b = Pattern.matches("^[a-zA-Z]+(\\d*[a-zA-Z]*)*", word);
        if (b) {
            String key = isWord(word, identifyword_map);
            if (!key.equals("-1")) {
                return key;
            } else {
                identifyword_map.put(String.valueOf(++identifyflag), word);
                writeToFile("Txt\\IdentifyWord.txt", identifyflag, word);
                return String.valueOf(identifyflag);
            }
        } else {
            return "-1";
        }
    }

    public String isNumberWord(String word) {
        boolean b = Pattern.matches("^\\d+\\.?\\d*", word);
        if (b) {
            String key = isWord(word, numberword_map);
            if (!key.equals("-1")) {
                return key;
            } else {
                numberword_map.put(String.valueOf(++numberflag), word);
                writeToFile("Txt\\NumberWord.txt", numberflag, word);
                return String.valueOf(numberflag);
            }
        } else {
            return "-1";
        }
    }

    public void writeToFile(String path, int flag, String word) {
        try {
            BufferedWriter bfw = new BufferedWriter(new FileWriter(path, true));
            bfw.write(flag + " " + word + "\r");
            bfw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //读取测试文件
    public String[] readTxt(String path) {
        String[] strings = null;
        try {
            StringBuilder sb = new StringBuilder();
            BufferedReader bfr = null;
            bfr = new BufferedReader(new FileReader(path));
            String s = "";
            while ((s = bfr.readLine()) != null) {
                sb.append(s+"\n");
            }
            s = "" + sb;
            strings = mySplit(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strings;
    }

    public String[] mySplit(String content) {
        String[] s = null;
        String con = content;
        content = contract(content, boundaryword_map);
        content = contract(content, operatorword_map);
        if (con.endsWith(";")){
            content += " ;";
        }
        s = content.split("\\s+");
        for (int i = 1;i<s.length;i++){
            if (s[i].equals("=")){
                if ((s[i-1].equals("<"))||s[i-1].equals(">")||s[i-1].equals(":")){
                    s[i-1] = s[i-1]+"=";
                    s[i] = "";
                }

            }
            if (s[i].equals("+")){
                if (s[i-1].equals("+")){
                    s[i-1] = s[i-1]+"+";
                    s[i] = "";
                }
            }
        }
        return s;
    }

    //在字符串的界符和操作符前后加上空格，重新拼接为字符串
    public String contract (String content,Map<String,String> map){
        String[] s;
        for(String key :map.keySet()){
            String regex;
            if (map.get(key).length()==1){
                regex = "["+map.get(key)+"]";
            }else {
                regex = map.get(key);
                if (map.get(key).equals("++")){
                    regex = "//++";
                }
            }
            s = content.split(regex);
            if (s.length>=1)
                content = "";
                for (int i = 0;i<s.length;i++){
                    if (i!=s.length-1){
                        content += (s[i] + " "+map.get(key)+" ");//最后一次分割将生成两个字符串，此时就不需要加分割符了
                    }else {
                        content += (s[i]);
                    }

                }
        }
        return content;
    }
}
