package tests;

import files.payload;
import io.restassured.path.json.JsonPath;
import org.junit.Assert;
import org.junit.Test;

public class ComplexJsonParse {

    @Test
    public  void testCourses(){
        JsonPath js  = new JsonPath(payload.CoursePrice());

        int count = js.getInt("courses.size()");
        System.out.println(count);

        Assert.assertEquals(3,count);

        //Total purchase amount is 910

        int purchaseAmount = js.getInt("dashboard.purchaseAmount");
        System.out.println(purchaseAmount);
        Assert.assertEquals(910,purchaseAmount);

        // title for the first course
        String title = js.get("courses[0].title");
        System.out.println(title);
        Assert.assertEquals("Selenium Python",title);

        for(int i=0;i<count;i++){
            String s = js.get("courses["+i+"].title");
            System.out.println(s);
        }
        int s =0;
        for(int i=0;i<count;i++){
           s += js.getInt("courses["+i+"].price") * js.getInt("courses["+i+"].copies") ;

        }
        System.out.println(s);
        Assert.assertEquals(purchaseAmount,s);


    }
}
