package ejb1;

import javax.ejb.Stateless;

@Stateless
public class MyEJB1 implements LocalEJB1, RemoteEJB1 {

    public int helloRemote(int a, int b) {
        return a + b;
    }

    public int helloLocal(int a, int b) {
        return a + b;
    }

}
