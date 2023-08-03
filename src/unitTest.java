import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class unitTest {

    @Test
    void add(){
        unit obj=new unit();
        assertTrue(obj.add(1,1)==1);
    }

}