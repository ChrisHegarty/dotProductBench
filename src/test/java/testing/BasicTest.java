package testing;

import org.junit.jupiter.api.Test;

public class BasicTest {

    @Test
    public void testDotProduct() {
        DotProduct test = new DotProduct();
        test.size = 1024;
        test.init();

        System.out.println(test.dotProductOld());
        System.out.println(test.dotProductNew());
    }
}
