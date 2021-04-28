package 实验二;

import java.util.ArrayList;
import java.util.List;

/**
 * @version v1.0
 * @ClassName: ElementCollection
 * @Description: 元素集合类
 * @Author: ChenQ
 * @Date: 2021/4/18 on 19:21
 */
public class ElementCollection {
    static char[] firstElement ={'(',')','*','+'};

    public static boolean isFirstElement(char ch){
        for (int i=0;i<firstElement.length;i++){
            if (ch == firstElement[i]){
                return true;
            }
        }
        return false;
    }
}
