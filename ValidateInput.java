public class ValidateInput {
     private static int x, y, z, gridSz;

     public static boolean validateInput(String minNeighbours, String maxNeighbours, String comeAliveNeighbours, String gridSzStr) {
          if (!checkIntString(minNeighbours, maxNeighbours, comeAliveNeighbours, gridSzStr)) return false;
          if (!validNumbers(x, y, z, gridSz)) return false;
          
          return true;
     }
     
     private static boolean checkIntString(String xStr, String yStr, String zStr, String gridSzStr) {
          try {
               x = Integer.parseInt(xStr);
               y = Integer.parseInt(yStr);
               z = Integer.parseInt(zStr);
               gridSz = Integer.parseInt(gridSzStr);
          } catch(NumberFormatException e) {
               return false;
          } catch(NullPointerException e) {
               return false;
          }
          return true;
     }

     private static boolean validNumbers(int minNeighbours, int maxNeighbours, int comeAliveNeighbours, int gridSz) {
          if (minNeighbours < 1) return false;
          if (maxNeighbours > 8) return false;
          if (minNeighbours >= maxNeighbours) return false;
          if (comeAliveNeighbours < minNeighbours && comeAliveNeighbours > maxNeighbours) return false;

          /*
           * Minimum grid size = 10
           * Maximum grid size = 50
           */
          if ((gridSz < 10) || (gridSz > 50)) return false;

          return true;
     }
}