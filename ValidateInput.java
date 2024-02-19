public class ValidateInput {
     private static int x, y, z;

     public static boolean validateInput(String minNeighbours, String maxNeighbours, String comeAliveNeighbours) {
          if (!checkIntString(minNeighbours, maxNeighbours, comeAliveNeighbours)) return false;
          if (!validNumbers(x, y, z)) return false;
          
          return true;
     }
     
     private static boolean checkIntString(String xStr, String yStr, String zStr) {
          try {
               x = Integer.parseInt(xStr);
               y = Integer.parseInt(yStr);
               z = Integer.parseInt(zStr);
          } catch(NumberFormatException e) {
               return false;
          } catch(NullPointerException e) {
               return false;
          }
          return true;
     }

     private static boolean validNumbers(int minNeighbours, int maxNeighbours, int comeAliveNeighbours) {
          if (minNeighbours < 1) return false;
          if (maxNeighbours > 8) return false;
          if (minNeighbours >= maxNeighbours) return false;
          if (comeAliveNeighbours < minNeighbours && comeAliveNeighbours > maxNeighbours) return false;

          return true;
     }
}