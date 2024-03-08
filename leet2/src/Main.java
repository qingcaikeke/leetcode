import java.util.Scanner;

// 注意类名必须为 Main, 不要有任何 package xxx 信息
public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        while (in.hasNextInt()) { // 注意 while 处理多个 case
            int n = in.nextInt();
            double[] origin = new double[n];
            double[] discount = new double[n];
            for(int i=0;i<n;i++){
                origin[i] = in.nextDouble();
                discount[i] = in.nextDouble();
                if(origin[i]<=0||discount[i]<=0||origin[i]<discount[i]) {
                    System.out.println("error");
                    return;
                }
            }
            int x = in.nextInt();
            int y = in.nextInt();
            if(x<=0||y<=0||x<y){
                System.out.println("error");
                return;
            }
            double res = count(n, origin, discount, x, y);
            System.out.println(String.format("%.2f",res));
        }
    }
    public static double count(int n,double[] origin,double[] discount,int x,int y){
        double res=Double.MAX_VALUE;
        double originSum = 0;
        for(int i=0;i<n;i++){
            originSum+=origin[i];
        }
        if(originSum>=x) res = originSum-y;
        double afterDiscout = 0;
        for(int i=0;i<n;i++){
            afterDiscout += discount[i];
        }
        return Math.min(afterDiscout, res);
    }
}
