import java.io.IOException;
import java.util.regex.Pattern;


/**
 * Created by moyong on 16/7/4.
 */
public class PatternSpec {
    public static void main(String[] args) throws IOException {
//        ◆以多条件分割字符串时
        Pattern pattern = Pattern.compile("[, |]+");
        String[] strs = pattern.split("Java Hello World Java,Hello,,World|Sun");
        for (int i = 0; i < strs.length; i++) {
            System.out.println(strs[i]);
        }

        String[] split = "Java Hello World Java,Hello,,World|Sun".split("[ |,(Java)]");
        for (String line : split) {
            System.out.println(line);
        }

    }
}
