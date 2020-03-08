package ejb1;

import javax.ejb.Local;

@Local
public interface LocalEJB1 {

    int helloLocal(int a, int b);

}
