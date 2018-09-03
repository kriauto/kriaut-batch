package ma.kriauto.rest.test;

public class GeoFence {
	
	public static boolean isInPolygone(double lat, double lon) {
		int j=0;
        boolean inBound = false;
        double x = lon;
        double y = lat;
        double ceuta[][]  = {{35.912384,-5.382088},{35.882143,-5.374969},{35.869003,-5.344977},{35.892861,-5.314985}};
        for (int i=0; i < 4 ; i++) {
          j++;
          if (j == 4) {j = 0;}
          if (((ceuta[i][0] < y) && (ceuta[j][0]  >= y)) || ((ceuta[j][0] < y) && (ceuta[i][0] >= y))) {
            if ( ceuta[i][1] + (y - ceuta[i][0])/(ceuta[j][0]-ceuta[i][0])*(ceuta[j][1] - ceuta[i][1])<x ) 
               {
            	inBound = !inBound;
               }
            }
        }
	    return inBound;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("  GeoFence Out "+isInPolygone(35.886120,-5.388161));
		System.out.println("  GeoFence In "+isInPolygone(35.889219, -5.373704));
	}

}
