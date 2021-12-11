package mypackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import ejb1.LocalEJB1;
import ejb1.RemoteEJB1;

public class Hello extends HttpServlet {

    private static final long serialVersionUID = 2109772982434503287L;

    @EJB(lookup = "java:global/myeeapps-ear1-0.0.1/myeeapps-ejb1-0.0.1/MyEJB1")
    private LocalEJB1 localEjb1;

    @EJB
    private RemoteEJB1 remoteEjb1;

    @SuppressWarnings("unchecked")
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        writer.println("hello war1");
        writer.println();
        
        for (Class<?> c : Arrays.asList(IOUtils.class, StringUtils.class, localEjb1.getClass())) {
            writer.println(c);
            writer.println(Hello.class.getClassLoader().getResource(c.getName().replace('.', '/').concat(".class")));
            ClassLoader classLoader = c.getClassLoader();
            writer.println(System.identityHashCode(classLoader) + " " + classLoader);
            writer.println();
        }
        InputStream inputStream = Hello.class.getResourceAsStream("myresource.txt");
        if (inputStream != null) {
            try {
                writer.println(new BufferedReader(new InputStreamReader(inputStream)).readLine());
            } finally {
                inputStream.close();
            }
        }

        long[] times = new long[5000];
        long pnanoTime = System.nanoTime();
        for (int i = 0; i < times.length; i++) {
            localEjb1.helloLocal(5, 10);
            long nanoTime = System.nanoTime();
            times[i] = nanoTime - pnanoTime;
            pnanoTime = nanoTime;
        }
        StatPrinter.stat(times);

        pnanoTime = System.nanoTime();
        for (int i = 0; i < times.length; i++) {
            remoteEjb1.helloRemote(5, 10);
            long nanoTime = System.nanoTime();
            times[i] = nanoTime - pnanoTime;
            pnanoTime = nanoTime;
        }
        StatPrinter.stat(times);

        System.out.println("end of get");
    }

}
