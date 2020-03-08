package ejb1;

import javax.ejb.Remote;

@Remote
public interface RemoteEJB1 {

    int helloRemote(int a, int b);

}
