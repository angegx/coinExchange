import java.util.Scanner;

/**
 * @author: ange
 * @package: PACKAGE_NAME
 * @className: tets
 * @creationTime: 2024-01-08 15:37
 * @Version: v1.0
 * @description: todo
 */
public class tets {
    public static void main(String[] args) {
        //键盘输入
         Scanner scanner = new Scanner(System.in);
        var y = switch (scanner.nextInt()) {
            case 0 -> '0';
            case 1 -> 0.0F;
            case 2 -> 2L;
            case 3 -> true;
            default -> 4;
        };
        System.out.println(y);
        System.out.println(((Object) y).getClass().getName());
    }
}
