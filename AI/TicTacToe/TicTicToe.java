
import java.util.Random;
import java.util.Scanner;


public class TicTicToe {
    int[][] chess={{0,0,0},{0,0,0},{0,0,0}};
    static int error = -1;
    static int none = 0;
    static int round = 1;
    static int fork = 2;
    public static void main(String[] args){
    		TicTicToe t=new TicTicToe();
        t.choose(none,null);
        double s=0;
        double a=1;
        while(true){
            if(s>=a){
                break;
            }
            int p=error;
            p=t.choose(round,t.result(round,fork));
            if(p!=error){
                if(p!=none){
                    t.showWin(round);
                }
                else{
                    t.showWin(none);
                }
                s++;
                continue;
            }
            p=t.choose(fork,t.result(fork,round));
            if(p!=error){
                if(p!=none){
                    t.showWin(fork);
                }
                else{
                    t.showWin(none);
                }
                s++;
                continue;
            }
        }
    }
    public int choose(int type,int[] nc){
        int p=error;
        if(type!=none){
            System.out.println(symbol(type)+"'s turn :");
            put(nc[0],nc[1],type);
            p=check();
        }
        System.out.println(show());
        return p;
    }
    public int[] getIn(){
        @SuppressWarnings("resource")
		Scanner in=new Scanner(System.in);
        String[] c=in.nextLine().split(",");
        int nc[]={Integer.parseInt(c[0]),Integer.parseInt(c[1])};
        return nc;
    }
    public int[] result(int myself,int rival){
        int[] c={0,0};
        for(int i=1;i<=3;i++){
            for(int j=1;j<=3;j++){
                int p=get(i,j);
                if(p==rival){
                    if(get(i+1,j)==rival&&get(i+2,j)==none){
                        c[0]=i+2;
                        c[1]=j;
                        return c;
                    }
                    if(get(i+2,j)==rival&&get(i+1,j)==none){
                        c[0]=i+1;
                        c[1]=j;
                        return c;
                    }
                    if(get(i-1,j)==rival&&get(i-2,j)==none){
                        c[0]=i-2;
                        c[1]=j;
                        return c;
                    }
                    if(get(i-2,j)==rival&&get(i-1,j)==none){
                        c[0]=i-1;
                        c[1]=j;
                        return c;
                    }
                    if(get(i,j+1)==rival&&get(i,j+2)==none){
                        c[0]=i;
                        c[1]=j+2;
                        return c;
                    }
                    if(get(i,j+2)==rival&&get(i,j+1)==none){
                        c[0]=i;
                        c[1]=j+1;
                    }
                    if(get(i,j-1)==rival&&get(i,j-2)==none){
                        c[0]=i;
                        c[1]=j-2;
                        return c;
                    }
                    if(get(i,j-2)==rival&&get(i,j-1)==none){
                        c[0]=i;
                        c[1]=j-1;
                        return c;
                    }
                    if(get(i+1,j-1)==rival&&get(i+2,j-2)==none){
                        c[0]=i+2;
                        c[1]=j-2;
                        return c;
                    }
                    if(get(i+2,j-2)==rival&&get(i+1,j-1)==none){
                        c[0]=i+1;
                        c[1]=j-1;
                        return c;
                    }
                    if(get(i-1,j+1)==rival&&get(i-2,j+2)==none){
                        c[0]=i-2;
                        c[1]=j+2;
                        return c;
                    }
                    if(get(i-2,j+2)==rival&&get(i-1,j+1)==none){
                        c[0]=i-1;
                        c[1]=j+1;
                        return c;
                    }
                    if(get(i+1,j+1)==rival&&get(i+2,j+2)==none){
                        c[0]=i+2;
                        c[1]=j+2;
                        return c;
                    }
                    else if(get(i+2,j+2)==rival&&get(i+1,j+1)==none){
                        c[0]=i+1;
                        c[1]=j+1;
                        return c;
                    }
                    if(get(i-1,j-1)==rival&&get(i-2,j-2)==none){
                        c[0]=i-2;
                        c[1]=j-2;
                        return c;
                    }
                    if(get(i-2,j-2)==rival&&get(i-1,j-1)==none){
                        c[0]=i-1;
                        c[1]=j-1;
                        return c;
                    }
                }
                if(p==myself){
                    if(get(i+1,j)==myself&&get(i+2,j)==none){
                        c[0]=i+2;
                        c[1]=j;
                    }
                    if(get(i+2,j)==myself&&get(i+1,j)==none){
                        c[0]=i+1;
                        c[1]=j;
                    }
                    if(get(i-1,j)==myself&&get(i-2,j)==none){
                        c[0]=i-2;
                        c[1]=j;
                    }
                    if(get(i-2,j)==myself&&get(i-1,j)==none){
                        c[0]=i-1;
                        c[1]=j;
                    }
                    if(get(i,j+1)==myself&&get(i,j+2)==none){
                        c[0]=i;
                        c[1]=j+2;
                    }
                    if(get(i,j+2)==myself&&get(i,j+1)==none){
                        c[0]=i;
                        c[1]=j+1;
                    }
                    if(get(i,j-1)==myself&&get(i,j-2)==none){
                        c[0]=i;
                        c[1]=j-2;
                    }
                    if(get(i,j-2)==myself&&get(i,j-1)==none){
                        c[0]=i;
                        c[1]=j-1;
                    }
                    if(get(i+1,j-1)==myself&&get(i+2,j-2)==none){
                        c[0]=i+2;
                        c[1]=j-2;
                    }
                    if(get(i+2,j-2)==myself&&get(i+1,j-1)==none){
                        c[0]=i+1;
                        c[1]=j-1;
                    }
                    if(get(i-1,j+1)==myself&&get(i-2,j+2)==none){
                        c[0]=i-2;
                        c[1]=j+2;
                    }
                    if(get(i-2,j+2)==myself&&get(i-1,j+1)==none){
                        c[0]=i-1;
                        c[1]=j+1;
                    }
                    if(get(i+1,j+1)==myself&&get(i+2,j+2)==none){
                        c[0]=i+2;
                        c[1]=j+2;
                    }
                    if(get(i+2,j+2)==myself&&get(i+1,j+1)==none){
                        c[0]=i+1;
                        c[1]=j+1;
                    }
                    if(get(i-1,j-1)==myself&&get(i-2,j-2)==none){
                        c[0]=i-2;
                        c[1]=j-2;
                    }
                    if(get(i-2,j-2)==myself&&get(i-1,j-1)==none){
                        c[0]=i-1;
                        c[1]=j-1;
                    }
                }
                else{
                    Random r=new Random();
                    while(true){
                        int x=r.nextInt(3)+1;
                        int y=r.nextInt(3)+1;
                        if(get(x,y)==none){
                            c[0]=x;
                            c[1]=y;
                            break;
                        }
                    }
                }
            }
        }
        return c;
    }
    public void showWin(int type){
        String p=symbol(type);
        System.out.println(show());
        if (p =="■"){
            System.out.println("Draw");
        }else{
            System.out.println(p + "Win");
        }
        chess[0][0]=none;
        chess[0][1]=none;
        chess[0][2]=none;
        chess[1][0]=none;
        chess[1][1]=none;
        chess[1][2]=none;
        chess[2][0]=none;
        chess[2][1]=none;
        chess[2][2]=none;
    }
    public void put(int x,int y,int type){
        if(chess[y-1][x-1]==none){
            chess[y-1][x-1]=type;
        }
        else{
            System.out.println(show());
            System.exit(0);
        }
    }
    public int get(int x,int y){
        if(x<1||x>3||y<1||y>3){
            return error;
        }
        return chess[y-1][x-1];

    }
    public int check(){
        int n11=chess[0][0];
        int n12=chess[1][0];
        int n13=chess[2][0];
        int n21=chess[0][1];
        int n22=chess[1][1];
        int n23=chess[2][1];
        int n31=chess[0][2];
        int n32=chess[1][2];
        int n33=chess[2][2];
        if(n11==n12&&n12==n13&&n11!=none) return n11;
        if(n21==n22&&n22==n23&&n21!=none) return n21;
        if(n31==n32&&n32==n33&&n31!=none) return n31;
        if(n11==n21&&n21==n31&&n11!=none) return n11;
        if(n12==n22&&n22==n32&&n12!=none) return n12;
        if(n13==n23&&n23==n33&&n13!=none) return n13;
        if(n11==n22&&n22==n33&&n11!=none) return n11;
        if(n13==n22&&n22==n31&&n13!=none) return n13;
        if(n11!=none&&n12!=none&&n13!=none&&n21!=none&&n22!=none&&n23!=none&&n31!=none&&n32!=none&&n33!=none){
            return none;
        }
        return error;
    }
    public String symbol(int type){
        String s="";
        if(type==none) s="■";
        if(type==round) s="O";
        if(type==fork) s="X";
        return s;
    }
    public String show(){
        String s11=symbol(chess[0][0]);
        String s12=symbol(chess[1][0]);
        String s13=symbol(chess[2][0]);
        String s21=symbol(chess[0][1]);
        String s22=symbol(chess[1][1]);
        String s23=symbol(chess[2][1]);
        String s31=symbol(chess[0][2]);
        String s32=symbol(chess[1][2]);
        String s33=symbol(chess[2][2]);
        return s11+" "+s12+" "+s13+"\n"+s21+" "+s22+" "+s23+"\n"+s31+" "+s32+" "+s33+"\n";
    }
}