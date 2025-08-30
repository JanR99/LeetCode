import java.util.*;

public class SolutionMedium {

    public List<String> validStrings(int n) {
        List<StringBuilder> strings = new LinkedList<>();
        strings.add(new StringBuilder(n).append('0'));
        strings.add(new StringBuilder(n).append('1'));
        for (int i = 1; i < n; i++) {
            List<StringBuilder> nextString = new LinkedList<>();
            for (StringBuilder sb : strings) {
                if (sb.charAt(sb.length() - 1) == '0') {
                    nextString.add(sb.append('1'));
                } else {
                    nextString.add(new StringBuilder(sb).append('0'));
                    nextString.add(sb.append('1'));
                }
            }
            strings = nextString;
        }
        return strings.stream().map(StringBuilder::toString).toList();
    }
}
