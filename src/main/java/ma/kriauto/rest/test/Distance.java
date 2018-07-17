package ma.kriauto.rest.test;

public class Distance {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        String att = "{distance:4.37,totalDistance:252.18,motion:true}";
        System.out.println(" distance "+att.replace(',',':'));
        System.out.println(" distance "+att.replace(',',':').split(":", 0)[1]);
        
	}

}
