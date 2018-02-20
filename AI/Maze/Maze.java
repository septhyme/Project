import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Maze {
    static int[][] maze; 
    public static void readfile(String text) {
        File file = new File(text);
        List<String> list = new ArrayList<>();
        try {
            @SuppressWarnings("resource")
			Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                list.add(sc.nextLine().replaceAll("\\s", ""));
            }
            if (list.size() != 0) {
                maze = new int[list.size()][list.get(0).length()];
                for (int i = 0; i < list.size(); i++) {
                    char[] chars = list.get(i).toCharArray();
                    for (int j = 0; j < chars.length; j++) {
                        maze[i][j] = chars[j] - '0';
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static boolean judge(int x1, int y1, int x2, int y2) {
        int[][] maze2 = maze.clone();
        maze2[x2][y2] = 3;
        boolean result = solve(maze2, x1, y1);
        if (result == true) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }
        return result;
    }
    private static boolean solve(int[][] maze, int x, int y) {
        if (maze[x][y] == 3) return true;
        if (maze[x][y] == 1 || maze[x][y] == 2)  return false;
        maze[x][y] = 2;
        if (solve(maze, x, y + 1)) return true;
        if (solve(maze, x + 1, y)) return true;
        if (solve(maze, x, y - 1)) return true;
        if (solve(maze, x - 1, y)) return true;
        maze[x][y] = 0;
        return false;
    }
    public static void main(String[] args) {
        Maze.readfile("/Users/yingweiliu/Desktop/maze.txt");
        System.out.println("Test1");
        Maze.judge(1,75,5,79);
        Maze.judge(1,75,37,77);
        System.out.println("Test2");
        Maze.judge(1,34,15,47);
        Maze.judge(1,2,3,39);
        Maze.judge(0,0,3,77);
        Maze.judge(1,75,37,77);
        Maze.judge(1,75,39,40);
    }
}
